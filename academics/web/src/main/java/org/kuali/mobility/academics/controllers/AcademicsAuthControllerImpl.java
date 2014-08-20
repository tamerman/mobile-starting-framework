/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.academics.controllers;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.academics.entity.Career;
import org.kuali.mobility.academics.entity.Section;
import org.kuali.mobility.academics.entity.Term;
import org.kuali.mobility.academics.service.AcademicsAuthService;
import org.kuali.mobility.academics.service.AcademicsService;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.academics.util.TermPredicate;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.service.DeviceService;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserAttribute;
import org.kuali.mobility.security.user.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("myAcademics")
public class AcademicsAuthControllerImpl implements ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(AcademicsAuthControllerImpl.class);
    private ApplicationContext applicationContext;

    @Resource(name="academicsAuthService")
    private AcademicsAuthService service;

    @Resource(name="kmeUserDao")
    private UserDao userDao;

    @Autowired
    private DeviceService deviceService;

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    @Resource(name="academicsProperties")
    private Properties academicsProperties;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model uiModel) {
        String viewName;
        if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            uiModel.addAttribute("academics.meetingDisplayLimit",getAcademicsProperties().getProperty("academics.meetingDisplayLimit","3"));
            viewName = "ui3/academics/mySchedule";
        } else {
            List<Term> terms = (List<Term>) service.getTerms(request);
            //List<Term> terms = (List<Term>) service.getTerms();

            HttpSession session = request.getSession();
            session.setAttribute(AcademicsConstants.TERM, terms);
            uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics");

            // check if only single career within single term registered
            if (terms != null && terms.size() == 1) {
                Term term = terms.get(0);
                if (term != null && term.getId() != null && !term.getId().isEmpty()) {
                    String termIda = term.getId();
                    List<Career> careers = (List<Career>) term.getCareers();
                    if (careers != null && careers.size() == 1 && null != careers.get(0)) {
                        String careerIda = careers.get(0).getId();
                        viewName = getMySections(request, uiModel, termIda, careerIda);
                    }
                }
            }
            if( request != null ) { updateDeviceLinking(request); }
            viewName = "academics/mySchedule";
        }
        return viewName;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/gradeAlerts", method = RequestMethod.GET)
    public String gradeAlerts(HttpServletRequest request, Model uiModel) {
        if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            return "ui3/academics/gradeAlerts";
        }

        LOG.debug("REMOTE_USER is " + request.getRemoteUser());
        HttpSession session = request.getSession();
        if (session == null) {
            LOG.error("HttpSession is NULL!");
        } else {
            User user = (User) session.getAttribute(AuthenticationConstants.KME_USER_KEY);
            if (user != null) {
                LOG.debug("gradeAlerts: User is [" + user.getLoginName() + "] and should be [" + request.getRemoteUser() + "]");
                LOG.debug("gradeAlerts: User's opt-in status is:" + user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT));
            } else {
                LOG.debug("gradeAlerts: User is null.");
            }
            String optInStatus;
            List<UserAttribute> attributes = user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
            if (attributes == null || attributes.isEmpty()) {
                optInStatus = "off";
            } else if (attributes.size() == 1) {
                optInStatus = attributes.get(0).getAttributeValue();
            } else {
                // This case shouldn't ever happen.  There should never be more than one
                // attribute for this particular key.
                optInStatus = attributes.get(0).getAttributeValue();
                user.clearAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
                getUserDao().saveUser(user);
                user.addAttribute(AcademicsConstants.USER_ATTR_GRADEALERT, optInStatus);
                getUserDao().saveUser(user);
            }
            uiModel.addAttribute("gradeAlertOpt", optInStatus);
            uiModel.addAttribute("toolContext", "myAcademics");
            uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics");

            // for test push notification
            //NativeCookieInterceptor.SESSION_PLATFORM
            LOG.debug("platform = " + session.getAttribute("session_platform"));
            LOG.debug("native = " + session.getAttribute("session_native"));
            uiModel.addAttribute("platform", session.getAttribute("session_platform"));
            uiModel.addAttribute("native", session.getAttribute("session_native"));

        }
        if( request != null ) { updateDeviceLinking(request); }

        return "academics/gradeAlerts";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/mySections", method = RequestMethod.GET)
    public String getMySections(HttpServletRequest request, Model uiModel,
                                @RequestParam(required = false) final String termId,
                                @RequestParam(required = false) final String careerId) {
        HttpSession session = request.getSession();
        if (termId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.TERM_ID))
                && careerId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CAREER_ID))) {
            Term term = (Term) session.getAttribute("currentTerm");
            uiModel.addAttribute("title", term.getShortDescription());
        } else {
            List<? extends Section> sections = getService().getClassSchedule(request, termId, careerId);
            if (sections.isEmpty()) {
                LOG.debug("No sections found for user [" + request.getRemoteUser() + "]");
            } else {
                Term term = (Term) CollectionUtils.find((List<Term>) session.getAttribute(AcademicsConstants.TERM), new TermPredicate(termId));
                if (term != null) {
                    session.setAttribute("currentTerm", term);
                    session.setAttribute("currentCareer", term.getCareers().get(0));
                    uiModel.addAttribute("title", term.getShortDescription());
                } else {
                    uiModel.addAttribute("title", "My Schedule");
                }
                uiModel.addAttribute("detailsections", sections);
                session.setAttribute(AcademicsConstants.SECTIONS, sections);
                session.setAttribute(AcademicsConstants.TERM_ID, termId);
                session.setAttribute(AcademicsConstants.CAREER_ID, careerId);
            }
        }
        uiModel.addAttribute("catalogDescription", null);
        uiModel.addAttribute("toolContext", "myAcademics");
        //uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/myAcademics/");
        return "academics/sections";
    }

    @RequestMapping(value = "/sectionsDetail")
    public String getSectionsDetail(HttpServletRequest request, Model uiModel,
                                    @RequestParam(required = false) final String catalogDescription,
                                    @RequestParam(required = true) final String sectionUID) {
        HttpSession session = request.getSession();
        Section sectionDetail = null;

        LOG.debug("Entering getSectionsDetail looking for sectionUID [" + sectionUID + "]");
        if (null != session.getAttribute(AcademicsConstants.SECTIONS)) {
            AcademicsService nonAuthService = (AcademicsService) getApplicationContext().getBean("academicsService");
            LOG.debug("Sections are stored in the session.");
            List<Section> allSections = (List<Section>) session.getAttribute(AcademicsConstants.SECTIONS);
            for (Section thisSection : allSections) {
                LOG.debug("Testing section [" + thisSection.getSectionUID() + "]");
                if (sectionUID.equalsIgnoreCase(thisSection.getSectionUID())) {
                    LOG.debug("Matching section found, loading remaining details.");
                    sectionDetail = thisSection;
                    break;
                }
            }
        }
        uiModel.addAttribute(AcademicsConstants.TITLE, sectionDetail.getSubjectId() + "-" + sectionDetail.getCatalogNumber());
        uiModel.addAttribute("thisSection", sectionDetail);
        uiModel.addAttribute("toolContext", "myAcademics");
        //uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getHeader("referer"));

        LOG.debug("Section contains " + (sectionDetail.getMeetings() == null ? "NULL" : sectionDetail.getMeetings().size()) + " meetings.");

        return "academics/sectionsDetail";
    }

    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
            @PathVariable("key") String key,
            HttpServletRequest request,
            Model uiModel ) {
        return "ui3/academics/templates/"+key;
    }

    @RequestMapping(value = "/js/{key}.js")
    public String getJavaScript(
            @PathVariable("key") String key,
            Model uiModel) {
        uiModel.addAttribute("meetingDisplayLimit",getAcademicsProperties().getProperty("academics.meetingDisplayLimit","3"));
        return "ui3/academics/js/"+key;
    }

    private void updateDeviceLinking(HttpServletRequest request) {
        User user = (User)request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
        String deviceId = (String)request.getSession().getAttribute(AuthenticationConstants.DEVICE_ID);
        Device device = deviceService.findDeviceByDeviceId(deviceId);

        if( user != null && device != null && !user.isPublicUser() ) {
            device.setUsername(user.getLoginName());
            deviceService.saveDevice(device);
        }
    }

    /**
     * @return the service
     */
    public AcademicsAuthService getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(AcademicsAuthService service) {
        this.service = service;
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }

    public Properties getAcademicsProperties() {
        return academicsProperties;
    }

    public void setAcademicsProperties(Properties academicsProperties) {
        this.academicsProperties = academicsProperties;
    }
}
