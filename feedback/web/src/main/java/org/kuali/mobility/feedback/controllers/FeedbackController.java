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

package org.kuali.mobility.feedback.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.feedback.entity.Feedback;
import org.kuali.mobility.feedback.entity.FeedbackDeviceType;
import org.kuali.mobility.feedback.entity.FeedbackSubject;
import org.kuali.mobility.feedback.service.FeedbackDeviceTypeService;
import org.kuali.mobility.feedback.service.FeedbackService;
import org.kuali.mobility.feedback.service.FeedbackSubjectService;
import org.kuali.mobility.security.authz.entity.AclExpression;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

	private static final Logger LOG = LoggerFactory
			.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;

	@Autowired
	private FeedbackSubjectService feedbackSubjectService;

	@Autowired
	private FeedbackDeviceTypeService feedbackDeviceTypeService;

	@Autowired
	private AdminService adminService;

	/**
     */
	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	/**
	 * A reference to the Spring Locale Resolver
	 */
	@Resource(name = "localeResolver")
	private LocaleResolver localeResolver;

	private static final Map<String, String> deviceTypes;
	private Map<String, String> subjectList;
	private Map<String, String> deviceTypesList;
	static {
		deviceTypes = new LinkedHashMap<String, String>();
		deviceTypes.put("feedback.default", "feedback.default");
		deviceTypes.put("feedback.computer", "feedback.computer");
		deviceTypes.put("feedback.android", "feedback.android");
		deviceTypes.put("feedback.blackberry", "feedback.blackberry");
		deviceTypes.put("feedback.ipad", "feedback.ipad");
		deviceTypes.put("feedback.iphone", "feedback.iphone");
		deviceTypes.put("feedback.ipod", "feedback.ipod");
		deviceTypes.put("feedback.windowsMobile", "feedback.windowsMobile");
		deviceTypes.put("feedback.other", "feedback.other");
	}

	private void getFeedbackSubjectList() {
		List<FeedbackSubject> feedbacksubjects = feedbackSubjectService
				.getFeedbackSubjects();
		subjectList = new LinkedHashMap<String, String>();
		for (FeedbackSubject feedbackSubject : feedbacksubjects) {
			subjectList.put(feedbackSubject.getSubjectKey(),
					feedbackSubject.getSubjectValue());
		}
	}

	private void getFeedbackDeviceTypeList() {
		List<FeedbackDeviceType> feedbackDevices = feedbackDeviceTypeService
				.getFeedbackDviceType();
		deviceTypesList = new LinkedHashMap<String, String>();
		for (FeedbackDeviceType feedbackDeviceType : feedbackDevices) {
			deviceTypesList.put(feedbackDeviceType.getSubjectDeviceKey(),
					feedbackDeviceType.getSubjectDeviceValue());
		}

	}

	@RequestMapping(value = "/js/feedback.js")
	public String getJavaScript(Model uiModel) {
		return "ui3/feedback/js/feedback";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getList(Model uiModel, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				Constants.KME_USER_KEY);
		getFeedbackSubjectList();
		getFeedbackDeviceTypeList();
		uiModel.addAttribute("subjectList", subjectList);
		uiModel.addAttribute("deviceTypes", deviceTypesList);
		Feedback feedback = new Feedback();
		if (user != null && user.getEmail() != null) { // pre populate email
			feedback.setEmail(user.getEmail());
		}
		uiModel.addAttribute("feedback", feedback);

		uiModel.addAttribute("tools", getHomeToolList(request));

		if ("3".equalsIgnoreCase(getKmeProperties().getProperty(
				"kme.uiVersion", "classic"))) {
			return "ui3/feedback/index";
		}

		return "feedback/form";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submitFeedback(Model uiModel,
			@ModelAttribute("feedback") Feedback feedback,
			BindingResult result, HttpServletRequest request) {
		// feedback.setPostedTimestamp(new
		// Timestamp(System.currentTimeMillis()));
		Locale locale = this.localeResolver.resolveLocale(request);

		User user = (User) request.getSession().getAttribute(
				Constants.KME_USER_KEY);
		if (user != null) {
			if (user.getLoginName() != null)
				feedback.setUserId(user.getLoginName());
			if (feedback.getCampus() == null
					|| feedback.getCampus().trim().isEmpty())
				feedback.setCampus(user.getViewCampus());
		}

		if (isValidFeedback(feedback, result)) {
			feedbackService.saveFeedback(feedback, locale);

			if ("3".equalsIgnoreCase(getKmeProperties().getProperty(
					"kme.uiVersion", "classic"))) {
				return "ui3/feedback/index";
			}

			return "feedback/thanks";
		} else {
			getFeedbackSubjectList();
			getFeedbackDeviceTypeList();
			uiModel.addAttribute("subjectList", subjectList);
			uiModel.addAttribute("deviceTypes", deviceTypesList);

			if ("3".equalsIgnoreCase(getKmeProperties().getProperty(
					"kme.uiVersion", "classic"))) {
				return "ui3/feedback/index";
			}

			return "feedback/form";
		}
	}

	private boolean isValidFeedback(Feedback f, BindingResult result) {
		boolean hasErrors = false;
		Errors errors = ((Errors) result);
		if (f.getNoteText() == null || "".equals(f.getNoteText().trim())) {
			errors.rejectValue("noteText", "",
					"Please type some feedback into the input box.");
			hasErrors = true;
		} else if (f.getNoteText().length() > 2000) {
			errors.rejectValue("noteText", "",
					"Error: Feedback must be less than 2000 characters.");
			hasErrors = true;
		}
		if (f.getDeviceType() == null || f.getDeviceType().equals("")) {
			errors.rejectValue("deviceType", "", "Please select a device type.");
			hasErrors = true;
		} else if (deviceTypes.get(f.getDeviceType()) == null) {
			errors.rejectValue("deviceType", "",
					"Error: Please select a valid device type.");
			hasErrors = true;
		}
		return !hasErrors;
	}

	private List<HomeTool> getHomeToolList(HttpServletRequest request) {
		List<HomeTool> userTools = new ArrayList<HomeTool>();

		int myState = 0;
		Cookie cks[] = request.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("native")) {
					if ("yes".equals(c.getValue())) {
						myState |= Tool.NATIVE;
					} else {
						myState |= Tool.NON_NATIVE;
					}
				}
				if (c.getName().equals("platform")) {
					if ("iOS".equals(c.getValue())) {
						myState |= Tool.IOS;
					} else if ("Android".equals(c.getValue())) {
						myState |= Tool.ANDROID;
					} else if ("WindowsMobile".equals(c.getValue())) {
						myState |= Tool.WINDOWS_PHONE;
					} else if ("Blackberry".equals(c.getValue())) {
						myState |= Tool.BLACKBERRY;
					}
				}
			}
		}

		User user = (User) request.getSession().getAttribute(
				Constants.KME_USER_KEY);

		String alias = "PUBLIC";
		if (request.getParameter("alias") != null) {
			alias = request.getParameter("alias").toUpperCase();
		}
		if (user != null && user.getViewCampus() != null) {
			alias = user.getViewCampus();
		}

		HomeScreen home = getAdminService().getHomeScreenByAlias(alias);
		if (home == null) {
			LOG.debug("Home screen was null, getting PUBLIC screen.");
			home = getAdminService().getHomeScreenByAlias("PUBLIC");
		}

		List<HomeTool> copy;
		if (home == null) {
			LOG.error("Home screen object still null when it should not be.");
			copy = new ArrayList<HomeTool>();
		} else {
			copy = new ArrayList<HomeTool>(home.getHomeTools());
		}
		Collections.sort(copy);

		for (HomeTool homeTool : copy) {
			Tool tool = homeTool.getTool();

			AclExpression viewingPermission = tool.getViewingPermission();
			if (viewingPermission != null
					&& viewingPermission.getParsedExpression() != null
					&& !viewingPermission.getParsedExpression().evaluate(user)) {
				continue;
			}
			// If a tools requisites is unset it will be treated as available to
			// any, same as Tool.ANY.
			if ((tool.getRequisites() & myState) == myState
					|| (tool.getRequisites() == Tool.UNDEFINED_REQUISITES)) {
				userTools.add(homeTool);
			} else {
				// LOG.info("--- HIDE TOOL: " + tool.getAlias());
			}

		}

		return userTools;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

}
