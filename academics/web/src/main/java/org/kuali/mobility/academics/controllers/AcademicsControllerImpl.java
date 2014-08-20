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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.entity.*;
import org.kuali.mobility.academics.service.AcademicsService;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.academics.util.CareerPredicate;
import org.kuali.mobility.academics.util.CatalogNumberPredicate;
import org.kuali.mobility.academics.util.SubjectPredicate;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/academics")
public class AcademicsControllerImpl {

	private static final Logger LOG = LoggerFactory.getLogger(AcademicsControllerImpl.class);
	@Resource(name="academicsService")
	private AcademicsService service;

	@Resource(name="academicsProperties")
	private Properties academicsProperties;

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    /**
     * A reference to the locale resolver
     */
    @Resource(name="localeResolver")
    private LocaleResolver localeResolver;

    @Resource(name="messageSource")
    private MessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
		HttpSession session = request.getSession();
		session.removeAttribute("currentTerm");
		session.removeAttribute("currentCareer");
		session.removeAttribute("currentSection");
		session.removeAttribute("currentCatalogNumber");
		session.removeAttribute("sectionsAll");
		session.removeAttribute(AcademicsConstants.TERM_ID);
		session.removeAttribute(AcademicsConstants.TERM);
		session.removeAttribute(AcademicsConstants.CAREER_ID);
		session.removeAttribute(AcademicsConstants.CAREER);
		session.removeAttribute(AcademicsConstants.SUBJECT_ID);
		session.removeAttribute(AcademicsConstants.SUBJECT);
		session.removeAttribute(AcademicsConstants.CATALOG_NUMBER);
		session.removeAttribute(AcademicsConstants.CATALOG_DESCRIPTION);
		session.removeAttribute(AcademicsConstants.CLASS_NUMBER);
		session.removeAttribute(AcademicsConstants.CLASS_SECTION);
		session.removeAttribute(AcademicsConstants.COURSE_ID);
		session.removeAttribute(AcademicsConstants.COURSE_OFFER_NBR);
		session.removeAttribute(AcademicsConstants.SECTIONS);
		session.removeAttribute(AcademicsConstants.TERM_DESCRIPTION);

		uiModel.addAttribute( AcademicsConstants.HOME_SCREEN_TOOLS, this.getHomeScreenOrder() );
		
        Cookie cks[] = request.getCookies();
        if (cks != null) {
            for (Cookie c : cks) {
                //LOG.info("---Cookies: " + c.getName() + " " + c.getValue());
                if (c.getName().equals("platform")) {
                    uiModel.addAttribute("platform",c.getValue());
                } else if (session.getAttribute("session_platform") != null) {
                    uiModel.addAttribute("platform",(String)session.getAttribute("session_platform"));
                } else {
                    uiModel.addAttribute("platform","none");
                }
            }
        }
        if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
        	return "ui3/academics/index";
        } else {
        	return "academics/index";
        }
	}

	@RequestMapping(value = "/terms")
	public String getTerms(HttpServletRequest request, Model uiModel) {
        String viewName = null;
        if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/academics/scheduleOfClasses";
        } else {
            HttpSession session = request.getSession();

            session.setAttribute(AcademicsConstants.TERM, getService().getTerms());

            //uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics/");
            viewName = "academics/terms";
        }
        return viewName;
	}

	@RequestMapping(value = "/careers")
	public String getCareers(HttpServletRequest request, Model uiModel,
			@RequestParam(required = true) final String termId) {
		HttpSession session = request.getSession();
		if (termId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.TERM_ID))) {
			// use cached data
			uiModel.addAttribute(AcademicsConstants.TITLE, ((Term)session.getAttribute("currentTerm")).getDescription());
		} else {
			Term term = getService().getTerm(termId);
			session.setAttribute(AcademicsConstants.TERM_ID, termId);
			session.setAttribute("currentTerm", term);
			List<? extends Career> lCareers = getService().getCareers(termId);
			session.setAttribute(AcademicsConstants.CAREER, lCareers);
			session.setAttribute("watermark",  getLocalisedString(request, "academics.searchClasses", term.getDescription()));
			uiModel.addAttribute(AcademicsConstants.TITLE, ((Term)session.getAttribute("currentTerm")).getDescription());
		}

		//uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics/terms");
		return "academics/careers";
	}

	@RequestMapping(value = "/subjects")
	public String getCareers(HttpServletRequest request, Model uiModel,
			@RequestParam(required = true) final String termId,
			@RequestParam(required = true) final String careerId) {
		HttpSession session = request.getSession();
		if (termId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.TERM_ID))
				&& careerId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CAREER_ID))) {
			// Use cached data.
//			uiModel.addAttribute(AcademicsConstants.TITLE, ((Career)session.getAttribute("currentCareer")).getShortDescription());
			uiModel.addAttribute(AcademicsConstants.TITLE, "Subjects" );
		} else {
			Term term = getService().getTerm(termId);
			session.setAttribute(AcademicsConstants.TERM_ID, term.getId());
			session.setAttribute("currentTerm", term);

			session.setAttribute(AcademicsConstants.CAREER_ID, careerId);
			Career career = (Career) CollectionUtils.find((List<Career>) session.getAttribute(AcademicsConstants.CAREER), new CareerPredicate(careerId));
			session.setAttribute("currentCareer", career);

			List<? extends Subject> lSubjects = getService().getSubjects(termId, careerId);
			session.setAttribute(AcademicsConstants.SUBJECT, lSubjects);
//			uiModel.addAttribute(AcademicsConstants.TITLE, ((Career)session.getAttribute("currentCareer")).getShortDescription());
			uiModel.addAttribute(AcademicsConstants.TITLE, "Subjects" );
		}
		//uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics/careers?termId=" + session.getAttribute(AcademicsConstants.TERM_ID));
		return "academics/subjects";
	}

	@RequestMapping(value = "/catalogNumbers")
	public String getCareers(HttpServletRequest request, Model uiModel,
			@RequestParam(required = true)
			final String termId,
			@RequestParam(required = true)
			final String careerId,
			@RequestParam(required = true)
			final String subjectId) {
		HttpSession session = request.getSession();
		if (termId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.TERM_ID))
				&& careerId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CAREER_ID))
				&& subjectId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.SUBJECT_ID))) {
			// Use cached data.
			uiModel.addAttribute(AcademicsConstants.TITLE, ((Subject)session.getAttribute("currentSubject")).getDescription());
		} else {
			Term term = getService().getTerm(termId);
			session.setAttribute(AcademicsConstants.TERM_ID, term.getId());
			session.setAttribute("currentTerm", term);
			session.setAttribute(AcademicsConstants.CAREER_ID, careerId);
			Career career = (Career) CollectionUtils.find((List<Career>) session.getAttribute(AcademicsConstants.CAREER), new CareerPredicate(careerId));
			session.setAttribute("currentCareer", career);
			session.setAttribute(AcademicsConstants.SUBJECT_ID, subjectId);
			Subject subject = (Subject)CollectionUtils.find((List<Subject>) session.getAttribute(AcademicsConstants.SUBJECT), new SubjectPredicate(subjectId));
			session.setAttribute("currentSubject", subject);
			List<? extends CatalogNumber> lCatalogNumbers = getService().getCatalogNumbers(termId, subjectId);
			session.setAttribute("catalogNumbers", lCatalogNumbers);

			uiModel.addAttribute(AcademicsConstants.TITLE, ((Subject)session.getAttribute("currentSubject")).getDescription());
		}
		//uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics/subjects?termId=" + session.getAttribute(AcademicsConstants.TERM_ID) + "&careerId=" + session.getAttribute(AcademicsConstants.CAREER_ID));
		return "academics/catalogNumbers";
	}

	@RequestMapping(value = "/browseClasses")
	public String browseClasses(HttpServletRequest request, Model uiModel,
			@RequestParam(required = false) String stage,
			@RequestParam(required = false)
			final String termId,
			@RequestParam(required = false)
			final String subjectId,
			@RequestParam(required = false)
			final String subject,
			@RequestParam(required = false)
			final String careerId,
			@RequestParam(required = false)
			final String catalogNumber,
			@RequestParam(required = false)
			final String catalogDescription) {
		HttpSession session = request.getSession();
		if (stage != null) {
			session.setAttribute(AcademicsConstants.STAGE, stage);
		} else {
			stage = AcademicsConstants.TERM;
		}
		if (termId != null && termId.length() > 0) {
			session.setAttribute(AcademicsConstants.TERM_ID, termId);
		}
		if (subjectId != null && subjectId.length() > 0) {
			session.setAttribute(AcademicsConstants.SUBJECT_ID, subjectId);
		}
		if (subject != null && subject.length() > 0) {
			session.setAttribute(AcademicsConstants.SUBJECT, subject);
		}
		if (careerId != null && careerId.length() > 0) {
			session.setAttribute(AcademicsConstants.CAREER_ID, careerId);
		}
		if (catalogNumber != null && catalogNumber.length() > 0) {
			session.setAttribute(AcademicsConstants.CATALOG_NUMBER, catalogNumber);
		}
		uiModel.addAttribute(AcademicsConstants.STAGE, stage);
		uiModel.addAttribute(AcademicsConstants.TERM_ID, session.getAttribute(AcademicsConstants.TERM_ID));
		uiModel.addAttribute(AcademicsConstants.SUBJECT_ID, session.getAttribute(AcademicsConstants.SUBJECT_ID));
		uiModel.addAttribute(AcademicsConstants.CAREER_ID, session.getAttribute(AcademicsConstants.CAREER_ID));
		uiModel.addAttribute(AcademicsConstants.CATALOG_NUMBER, session.getAttribute(AcademicsConstants.CATALOG_NUMBER));
		uiModel.addAttribute(AcademicsConstants.SUBJECT, session.getAttribute(AcademicsConstants.SUBJECT));
		uiModel.addAttribute("toolContext", "academics");
		if (stage.equalsIgnoreCase(AcademicsConstants.CATALOG_NUMBER)) {
			uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, "browseClasses?stage=" + AcademicsConstants.SUBJECT);
		} else if (stage.equalsIgnoreCase(AcademicsConstants.SUBJECT)) {
			uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, "browseClasses?stage=" + AcademicsConstants.CAREER);
		} else if (stage.equalsIgnoreCase(AcademicsConstants.CAREER)) {
			uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, "browseClasses?stage=" + AcademicsConstants.TERM);
		}

//		Enumeration<String> e = session.getAttributeNames();
//		while (e.hasMoreElements()) {
//			String key = e.nextElement();
//			LOG.debug("Session contains: [" + key + "] [" + session.getAttribute(key) + "]");
//		}

		return "academics/browseClasses";
	}

	@RequestMapping(value = "/sections")
	public String getSections(HttpServletRequest request, Model uiModel,
			@RequestParam(required = false)
			final String termId,
			@RequestParam(required = false)
			final String subjectId,
			@RequestParam(required = false)
			final String catalogNumber,
			@RequestParam(required = false)
			final String careerId) {
		HttpSession session = request.getSession();
		if (termId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.TERM_ID))
				&& careerId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CAREER_ID))
				&& subjectId.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CAREER_ID))
				&& catalogNumber.equalsIgnoreCase((String) session.getAttribute(AcademicsConstants.CATALOG_NUMBER))) {
			uiModel.addAttribute(AcademicsConstants.TITLE, session.getAttribute(AcademicsConstants.SUBJECT_ID) + " " + session.getAttribute(AcademicsConstants.CATALOG_NUMBER));
		} else {
			List<Section> lSections = (List<Section>) getService().getSections(termId, subjectId, catalogNumber, careerId);
                        CatalogNumber number = (CatalogNumber) CollectionUtils.find((List<CatalogNumber>) session.getAttribute("catalogNumbers"), new CatalogNumberPredicate(catalogNumber));
                        for (Section s : lSections) {
                            if (null==s.getCourseTitle() || s.getCourseTitle().trim().isEmpty()) {
                                s.setCourseTitle(number.getDescription());
                            } 
                        }
                        
			session.setAttribute("sectionsAll", lSections);
			session.setAttribute(AcademicsConstants.CATALOG_NUMBER, catalogNumber);
			session.setAttribute(AcademicsConstants.SUBJECT_ID, subjectId);
			uiModel.addAttribute("sectionsAll", lSections);
			uiModel.addAttribute(AcademicsConstants.TITLE, session.getAttribute(AcademicsConstants.SUBJECT_ID) + " " + session.getAttribute(AcademicsConstants.CATALOG_NUMBER));
		}
		uiModel.addAttribute("toolContext", "academics");
		//uiModel.addAttribute(AcademicsConstants.BACKBUTTON_URL, request.getContextPath() + "/academics/catalogNumbers?termId=" + termId + "&careerId=" + careerId + "&subjectId=" + subjectId);
		return "academics/sections";
	}

	@RequestMapping(value = "/sectionsDetail")
	public String getSectionsDetail(HttpServletRequest request, Model uiModel,
			@RequestParam(required = false)
			final String catalogDescription,
			@RequestParam(required = true)
			final String sectionUID) {
		HttpSession session = request.getSession();
		Section sectionDetail = null;
		LOG.debug("Entering getSectionsDetail looking for sectionUID [" + sectionUID + "]");
		if (null != session.getAttribute(AcademicsConstants.SECTIONS)) {
			LOG.debug("Sections are stored in the session.");
			List<Section> allSections = (List<Section>) session.getAttribute(AcademicsConstants.SECTIONS);
			for (Section thisSection : allSections) {
				LOG.debug("Testing section [" + thisSection.getSectionUID() + "]");
				if (sectionUID.equalsIgnoreCase(thisSection.getSectionUID())) {
					LOG.debug("Matching section found, loading remaining details.");
					sectionDetail = getService().getSectionDetail(thisSection);
					break;
				}
			}
		}
//		uiModel.addAttribute(AcademicsConstants.CATALOG_DESCRIPTION, session.getAttribute(AcademicsConstants.CATALOG_DESCRIPTION));
		uiModel.addAttribute(AcademicsConstants.TITLE, sectionDetail.getSubjectId() + " " + sectionDetail.getCatalogNumber());
		uiModel.addAttribute("thisSection", sectionDetail);
		uiModel.addAttribute("toolContext", "academics");
		//LOG.debug("BACKBUTTON_URL from request for sectionDetail: -" + session.getAttribute("backButtonURL"));
//        String backurl = (String) session.getAttribute("backButtonURL");
//        if(backurl.equalsIgnoreCase("search"))
//        {
//           uiModel.addAttribute("backButtonURL","academics/classSearch");
//        }
//        else
//        {
//           uiModel.addAttribute("backButtonURL","academics/sections?termId="+session.getAttribute(AcademicsConstants.TERM_ID)+"&careerId="+session.getAttribute(AcademicsConstants.CAREER_ID)+"&subjectId="+session.getAttribute(AcademicsConstants.SUBJECT_ID)+"&catalogNumber="+session.getAttribute(AcademicsConstants.CATALOG_NUMBER)+"&catalogDescription="+session.getAttribute(AcademicsConstants.CATALOG_DESCRIPTION));
//        }
//		uiModel.addAttribute("backButtonURL", request.getHeader("referer"));
		return "academics/sectionsDetail";
	}

	@RequestMapping(value = "/search")
	public String searchForm(Model uiModel) {
		uiModel.addAttribute(AcademicsConstants.TERM_ID, getService().getTerms());
		uiModel.addAttribute(AcademicsConstants.CAREER_ID, getService().getCareers("ALL"));
		uiModel.addAttribute(AcademicsConstants.SUBJECT_ID, getService().getSubjects("ALL", "ALL"));
		uiModel.addAttribute("toolContext", "academics");
		if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
        	return "ui3/academics/search";
        } else {
        	return "academics/search";
        }
	}

        @RequestMapping(value = "/classSearch", method = RequestMethod.POST)
        public String getSearchResults(HttpServletRequest request, Model uiModel) {
            Map<String, String[]> inputMap = request.getParameterMap();

            if ( LOG.isDebugEnabled() ) {
                LOG.debug("Search request parameter map:");
                Iterator<Map.Entry<String, String[]>> mapIterator = inputMap.entrySet().iterator();
                while (mapIterator.hasNext()) {
                    Map.Entry<String, String[]> entry = mapIterator.next();
                    LOG.debug(entry.getKey() + ": " + Arrays.toString(entry.getValue()));
                }
            }
            
            SearchResult result = getService().getSearchResults(inputMap);
            List<Section> lSections = (List<Section>)result.getSections();
            LOG.debug("Sections classSearch POST  " + lSections.size() + " items long.");
            HttpSession session = request.getSession();
            session.setAttribute(AcademicsConstants.SECTIONS, lSections);
            session.setAttribute(AcademicsConstants.SEARCH_RESULT,result);
            session.setAttribute("toolContext", "academics");

            uiModel.addAttribute("searchResult", result);
            uiModel.addAttribute("toolContext", "academics");

            return "academics/searchResult";
        }

	@RequestMapping(value = "/classSearch", method = RequestMethod.GET)
	public String searchResults(HttpServletRequest request, Model uiModel) {
		HttpSession session = request.getSession();
		//uiModel.addAttribute(AcademicsConstants.TITLE, "Search Results");
		//uiModel.addAttribute("sectionsAll", session.getAttribute("sectionsAll"));
                uiModel.addAttribute("searchResult", session.getAttribute(AcademicsConstants.SEARCH_RESULT));
		uiModel.addAttribute("toolContext", session.getAttribute("toolContext"));
		//uiModel.addAttribute("backButtonURL", session.getAttribute("backButtonURL"));
		return "academics/searchResult";
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
        if ("academics".equalsIgnoreCase(key)) {
            uiModel.addAttribute( AcademicsConstants.HOME_SCREEN_TOOLS, this.getHomeScreenOrder() );
        }
        return "ui3/academics/js/"+key;
    }

    private List<String> getHomeScreenOrder() {
		List<String> toolOrder = new ArrayList<String>();
		if( null == getAcademicsProperties() ) {
			LOG.error( "Failed to load academics properties" );
		}
		LOG.debug( "Home screen order is: "+getAcademicsProperties().getProperty("academics.homescreenorder") );
		for( String s : getAcademicsProperties().getProperty("academics.homescreenorder").split(",") ) {
			if( "true".equalsIgnoreCase( getAcademicsProperties().getProperty("academics."+s+".enabled", "false"))) {
				toolOrder.add(s);
			}
		}
		return toolOrder;
	}

    private String getLocalisedString(HttpServletRequest request, String code, String...params){
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(code, params, locale);
    }

	/**
	 * @return the service
	 */
	public AcademicsService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(AcademicsService service) {
		this.service = service;
	}

	/**
	 * @return the academicsProperties
	 */
	public Properties getAcademicsProperties() {
		return academicsProperties;
	}

	/**
	 * @param academicsProperties the academicsProperties to set
	 */
	public void setAcademicsProperties(Properties academicsProperties) {
		this.academicsProperties = academicsProperties;
	}

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }
}
