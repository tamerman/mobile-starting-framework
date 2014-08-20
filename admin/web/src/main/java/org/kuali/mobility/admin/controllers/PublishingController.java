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

package org.kuali.mobility.admin.controllers;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.l10n.service.LocalisationService;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.entity.NewsSourceDBImpl;
import org.kuali.mobility.news.service.NewsService;
import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.service.NotificationService;
import org.kuali.mobility.security.authz.entity.AclExpression;
import org.kuali.mobility.shared.controllers.AbstractMobilityController;
import org.kuali.mobility.util.LocalisationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for performing publishing actions
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Controller
@RequestMapping("/publishing")
public class PublishingController extends AbstractMobilityController {

	/**
	 * A reference to the <code>AdminService</code>.
	 */
	@Autowired
	private AdminService adminService;

	/**
	 * A reference to the <code>NotificationService</code>
	 */
	@Autowired
	private NotificationService notificationService;

	/**
	 * A reference to the <code>NewsService</code>
	 */
	@Autowired
	private NewsService newsService;

	/**
	 * A reference to the <code>LocalisationUtil</code>
	 */
	@Autowired
	@Qualifier("localisationUtil")
	private LocalisationUtil localisationUtil;

	/**
	 * A reference to the <code>LocalisationService</code>
	 */
	@Autowired
	@Qualifier("localisationService")
	private LocalisationService localisationService;

	/**
	 * The main entry point for publishing. Provides links to more specific
	 * publishing tools.
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String publishingHome(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			viewName = "publishing/index";
		}
		return viewName;
	}

	/**
	 * The main entry point for publishing. Provides links to more specific
	 * publishing tools.
	 * 
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			viewName = "publishing/index";
		}
		return viewName;
	}

	// ----------------Tools------------------

	/**
	 * Entry point for publishing Tools. Lists currently defined Tools.
	 * 
	 * @param uiModel
	 * @return the tool publishing entry page
	 */
	@RequestMapping(value = "tool", method = RequestMethod.GET)
	public String tool(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			List<Tool> tools = adminService.getAllTools();
			Collections.sort(tools);
			uiModel.addAttribute("tools", tools);
			viewName = "publishing/tool";
		}
		return viewName;

	}

	/**
	 * Create a new Tool
	 * 
	 * @param uiModel
	 * @return the edit tool page
	 */
	@RequestMapping(value = "tool/new", method = RequestMethod.GET)
	public String newTool(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Tool tool = new Tool();
			tool.setPublishingPermission(new AclExpression());
			tool.setViewingPermission(new AclExpression());
			uiModel.addAttribute("tool", tool);
			viewName = "publishing/editTool";
		}
		return viewName;
	}

	/**
	 * Edit an existing Tool
	 * 
	 * @param uiModel
	 * @param toolId
	 *            id of the Tool to edit
	 * @return the edit tool page
	 */
	@RequestMapping(value = "tool/edit/{toolId}", method = RequestMethod.GET)
	public String editTool(HttpServletRequest request, Model uiModel,
			@PathVariable("toolId") long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Tool tool = adminService.getToolById(toolId);
			uiModel.addAttribute("tool", tool);
			viewName = "publishing/editTool";
		}
		return viewName;
	}

	/**
	 * Delete a Tool
	 * 
	 * @param uiModel
	 * @param toolId
	 *            id of the Tool to delete
	 * @return back to the tool publishing entry page
	 */
	@RequestMapping(value = "tool/delete/{toolId}", method = RequestMethod.GET)
	public String deleteTool(HttpServletRequest request, Model uiModel,
			@PathVariable("toolId") long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			adminService.deleteToolById(toolId);
			viewName = tool(request, uiModel);
		}
		return viewName;
	}

	/**
	 * Save a Tool
	 * 
	 * @param uiModel
	 * @param tool
	 *            the Tool object to save
	 * @param result
	 *            binding validation result
	 * @return back to the tool publishing entry page
	 */
	@RequestMapping(value = "tool/edit", method = RequestMethod.POST)
	public String editTool(Model uiModel, @ModelAttribute("tool") Tool tool,
			BindingResult result, HttpServletRequest request) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			String fieldCode;
			Map<String, String> localisedStrings;

			// Persist the title of the tool
			fieldCode = localisationUtil.getLocalisedStringCode("title",
					request);
			localisedStrings = localisationUtil.getLocalisedString("title",
					request);
			localisationService
					.saveLocalisedString(fieldCode, localisedStrings);
			tool.setTitle(fieldCode);

			// Persist the description of the tool
			fieldCode = localisationUtil.getLocalisedStringCode("description",
					request);
			localisedStrings = localisationUtil.getLocalisedString(
					"description", request);
			localisationService
					.saveLocalisedString(fieldCode, localisedStrings);
			tool.setDescription(fieldCode);

			// Clear out empty Publishing expression
			if (tool.getPublishingPermission() != null
					&& StringUtils.isEmpty(tool.getPublishingPermission()
							.getExpression())) {
				tool.setPublishingPermission(null);
				tool.setAclPublishingId(null);
			}
			// Clear out empty Viewing expression
			if (tool.getViewingPermission() != null
					&& StringUtils.isEmpty(tool.getViewingPermission()
							.getExpression())) {
				tool.setViewingPermission(null);
				tool.setAclViewingId(null);
			}

			adminService.saveTool(tool);
			viewName = tool(request, uiModel);
		}
		return viewName;
	}

	// ----------------Layouts------------------

	/**
	 * The entry point for publishing HomeScreen layouts
	 * 
	 * @param uiModel
	 * @return the home screen publishing entry page
	 */
	@RequestMapping(value = "layout", method = RequestMethod.GET)
	public String layout(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			uiModel.addAttribute("layouts", adminService.getAllHomeScreens());
			viewName = "publishing/layout";
		}
		return viewName;
	}

	/**
	 * Create a new HomeScreen
	 * 
	 * @param uiModel
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/new", method = RequestMethod.GET)
	public String newLayout(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			uiModel.addAttribute("layout", new HomeScreen());
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;

	}

	/**
	 * Edit an existing HomeScreen
	 * 
	 * @param uiModel
	 * @param layoutId
	 *            the id of the HomeScreen to edit
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/edit/{layoutId}", method = RequestMethod.GET)
	public String editLayout(HttpServletRequest request, Model uiModel,
			@PathVariable("layoutId") long layoutId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			HomeScreen layout = adminService.getHomeScreenById(layoutId);
			Collections.sort(layout.getHomeTools());
			uiModel.addAttribute("layout", layout);
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;
	}

	/**
	 * Save a HomeScreen
	 * 
	 * @param uiModel
	 * @param homeScreen
	 *            the HomeScreen to save
	 * @param result
	 *            binding validation result
	 * @return the home screen publishing entry page
	 */
	@RequestMapping(value = "layout/edit", method = RequestMethod.POST)
	public String editLayout(HttpServletRequest request, Model uiModel,
			@ModelAttribute("layout") HomeScreen homeScreen,
			BindingResult result) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			int index = 0;
			for (HomeTool ht : homeScreen.getHomeTools()) {
				ht.setOrder(index++);
			}
			adminService.saveHomeScreen(homeScreen);
			viewName = layout(request, uiModel);
		}
		return viewName;
	}

	/**
	 * Delete a HomeScreen
	 * 
	 * @param uiModel
	 * @param layoutId
	 *            the id of the HomeScren to delete
	 * @return the home screen publishing entry page
	 */
	@RequestMapping(value = "layout/delete/{layoutId}", method = RequestMethod.GET)
	public String deleteLayout(HttpServletRequest request, Model uiModel,
			@PathVariable("layoutId") long layoutId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			adminService.deleteHomeScreenById(layoutId);
			viewName = layout(request, uiModel);
		}
		return viewName;
	}

	/**
	 * Associate a Tool with a HomeScreen if it isn't already associated
	 * 
	 * @param uiModel
	 * @param homeScreen
	 *            the HomeScreen to which to add the Tool
	 * @param result
	 *            binding validation result for the HomeScreen
	 * @param toolId
	 *            the id of the Tool to associate with the HomeSreen
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/edit", method = RequestMethod.POST, params = "add")
	public String addTool(HttpServletRequest request, Model uiModel,
			@ModelAttribute("layout") HomeScreen homeScreen,
			BindingResult result, @RequestParam("toolToAdd") Long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Tool tool = adminService.getToolById(toolId);
			boolean found = false;
			for (HomeTool homeTool : homeScreen.getHomeTools()) {
				if (homeTool.getToolId().equals(toolId)) {
					found = true;
					break;
				}
			}
			if (!found) {
				HomeTool homeTool = new HomeTool(homeScreen, tool, homeScreen
						.getHomeTools().size());
				homeScreen.getHomeTools().add(homeTool);
			}
			Collections.sort(homeScreen.getHomeTools());
			uiModel.addAttribute("layout", homeScreen);
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;
	}

	/**
	 * Remove a Tool's association with a HomeScreen
	 * 
	 * @param uiModel
	 * @param homeScreen
	 *            the HomeScreen from which to remove the Tool association
	 * @param result
	 *            the binding validation result for the HomeScreen
	 * @param toolId
	 *            the id of the Tool to remove
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/edit", method = RequestMethod.POST, params = "remove")
	public String removeTool(HttpServletRequest request, Model uiModel,
			@ModelAttribute("layout") HomeScreen homeScreen,
			BindingResult result, @RequestParam("removeId") Long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Integer removedOrder = null;
			for (Iterator<HomeTool> iter = homeScreen.getHomeTools().iterator(); iter
					.hasNext();) {
				HomeTool homeTool = iter.next();
				if (homeTool.getToolId().equals(toolId)) {
					removedOrder = homeTool.getOrder();
					iter.remove();
					break;
				}
			}
			if (removedOrder != null) {
				int index = 0;
				for (HomeTool ht : homeScreen.getHomeTools()) {
					ht.setOrder(index++);
				}
			}
			Collections.sort(homeScreen.getHomeTools());
			uiModel.addAttribute("layout", homeScreen);
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;
	}

	/**
	 * Move a tool up in the Tool list display order
	 * 
	 * @param uiModel
	 * @param homeScreen
	 *            the HomeScreen to edit
	 * @param result
	 *            binding validation result for the HomeScreen
	 * @param toolId
	 *            the id of the Tool to move
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/edit", method = RequestMethod.POST, params = "up")
	public String moveToolUp(HttpServletRequest request, Model uiModel,
			@ModelAttribute("layout") HomeScreen homeScreen,
			BindingResult result, @RequestParam("removeId") Long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			HomeTool selectedHomeTool = null;

			for (HomeTool homeTool : homeScreen.getHomeTools()) {
				if (homeTool.getToolId().equals(toolId)) {
					selectedHomeTool = homeTool;
					break;
				}
			}
			if (selectedHomeTool != null && selectedHomeTool.getOrder() > 0) {
				for (HomeTool ht : homeScreen.getHomeTools()) {
					if (ht.getOrder() == selectedHomeTool.getOrder() - 1) {
						int swap = ht.getOrder();
						ht.setOrder(selectedHomeTool.getOrder());
						selectedHomeTool.setOrder(swap);
						break;
					}
				}
			}
			Collections.sort(homeScreen.getHomeTools());
			uiModel.addAttribute("layout", homeScreen);
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;
	}

	/**
	 * Move a tool down in the Tool list display order
	 * 
	 * @param uiModel
	 * @param homeScreen
	 *            the HomeScreen to edit
	 * @param result
	 *            binding validation result for the HomeScreen
	 * @param toolId
	 *            the id of the Tool to move
	 * @return the home screen editing page
	 */
	@RequestMapping(value = "layout/edit", method = RequestMethod.POST, params = "down")
	public String moveToolDown(HttpServletRequest request, Model uiModel,
			@ModelAttribute("layout") HomeScreen homeScreen,
			BindingResult result, @RequestParam("removeId") Long toolId) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			HomeTool selectedHomeTool = null;

			for (HomeTool homeTool : homeScreen.getHomeTools()) {
				if (homeTool.getToolId().equals(toolId)) {
					selectedHomeTool = homeTool;
					break;
				}
			}
			if (selectedHomeTool != null) {
				for (HomeTool ht : homeScreen.getHomeTools()) {
					if (ht.getOrder() == selectedHomeTool.getOrder() + 1) {
						int swap = ht.getOrder();
						ht.setOrder(selectedHomeTool.getOrder());
						selectedHomeTool.setOrder(swap);
						break;
					}
				}
			}
			Collections.sort(homeScreen.getHomeTools());
			uiModel.addAttribute("layout", homeScreen);
			uiModel.addAttribute("availableTools", adminService.getAllTools());
			viewName = "publishing/editLayout";
		}
		return viewName;

	}

	// ----------------Notifications------------------

	/**
	 * the main entry point for notifications
	 * 
	 * @param uiModel
	 * @return the notifications entry page
	 */
	@RequestMapping(value = "notifications", method = RequestMethod.GET)
	public String notifications(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			uiModel.addAttribute("notifications",
					notificationService.findAllNotifications());
			viewName = "publishing/notifications";
		}
		return viewName;
	}

	/**
	 * Create or edit a notification
	 * 
	 * @param id
	 *            (optional) the id of the notification to edit
	 * @param uiModel
	 * @return the notification edit form
	 */
	@RequestMapping(value = "notificationForm", method = RequestMethod.GET)
	public String notificationForm(
			@RequestParam(value = "id", required = false) Long id,
			HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Notification n = new Notification();
			if (id != null) {
				n = notificationService.findNotificationById(id);
			}
			uiModel.addAttribute("notification", n);
			viewName = "publishing/notificationForm";
		}
		return viewName;
	}

	/**
	 * Edit a notification
	 * 
	 * @param id
	 *            the id of the notification to edit
	 * @param uiModel
	 * @return the notification edit form
	 */
	@RequestMapping(value = "editNotification", method = RequestMethod.GET)
	public String editNotification(
			@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			Notification n = notificationService.findNotificationById(id);
			uiModel.addAttribute("notification", n);
			viewName = "publishing/notificationForm";
		}
		return viewName;
	}

	/**
	 * Delete a notification
	 * 
	 * @param id
	 *            the id of the notification to delete
	 * @param uiModel
	 * @return the notifications entry page
	 */
	@RequestMapping(value = "deleteNotification", method = RequestMethod.GET)
	public String deleteNotification(
			@RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			notificationService.deleteNotificationById(id);
			viewName = "redirect:/publishing/notifications";
		}
		return viewName;
	}

	/**
	 * Save a notification
	 * 
	 * @param request
	 * @param uiModel
	 * @param notification
	 *            the Notification to save
	 * @param result
	 *            binding validation result
	 * @return the notifications entry page
	 */
	@RequestMapping(value = "notificationSubmit", method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model uiModel,
			@ModelAttribute("notification") Notification notification,
			BindingResult result) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			if (isValidNotification(notification, result)) {
				notificationService.saveNotification(notification);
				viewName = "redirect:/publishing/notifications";
			} else {
				viewName = "publishing/notificationForm";
			}
		}
		return viewName;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	/**
	 * Validate a Notification
	 * 
	 * @param notification
	 *            the Notification to validate
	 * @param result
	 * @return true if valid
	 */
	private boolean isValidNotification(Notification notification,
			BindingResult result) {
		// TODO Localisation!!
		boolean hasErrors = false;
		Errors errors = ((Errors) result);
		if (errors.hasFieldErrors("startDate")) {
			errors.rejectValue("startDate", "",
					"Please enter a valid start date (empty or YYYY-MM-DD)");
			hasErrors = true;
		}
		if (errors.hasFieldErrors("endDate")) {
			errors.rejectValue("endDate", "",
					"Please enter a valid end date (empty or YYYY-MM-DD)");
			hasErrors = true;
		}
		if (notification.getMessage() == null
				|| "".equals(notification.getMessage().trim())) {
			errors.rejectValue("message", "", "Please enter a message");
			hasErrors = true;
		}
		if (notification.getTitle() == null
				|| "".equals(notification.getTitle().trim())) {
			errors.rejectValue("title", "", "Please enter a title");
			hasErrors = true;
		}
		if (notification.getNotificationType() == null) {
			notification.setNotificationType(new Long(1));
		}
		return !hasErrors;
	}

	// ----------------News------------------
	/**
	 * The main entry point for News publishing
	 * 
	 * @param uiModel
	 * @return the news entry page
	 */
	@RequestMapping(value = "news", method = RequestMethod.GET)
	public String news(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			uiModel.addAttribute("sources", newsService.getAllNewsSources());
			viewName="publishing/news";
		}
		return viewName;
	}

	/**
	 * Create a new NewsSource
	 * 
	 * @param uiModel
	 * @return the news source editing page
	 */
	@RequestMapping(value = "news/add", method = RequestMethod.GET)
	public String editNews(HttpServletRequest request, Model uiModel) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			NewsSource source = new NewsSourceDBImpl();
			source.setOrder(newsService.getAllNewsSources().size());
			source.setActive(true);
			uiModel.addAttribute("source", source);
			viewName="publishing/editNews";
		}
		return viewName;
	}

	/**
	 * Edit an existing NewsSource
	 * 
	 * @param uiModel
	 * @param id
	 *            the id of the NewsSource to edit
	 * @return the news source editing page
	 */
	@RequestMapping(value = "news/edit/{id}", method = RequestMethod.GET)
	public String editNews(HttpServletRequest request, Model uiModel,
			@PathVariable("id") long id) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATOR", request)) {
			viewName = "redirect:/errors/404";
		} else {
			NewsSource newsSource = newsService.getNewsSourceById(id);
			uiModel.addAttribute("source", newsSource);
			viewName="publishing/editNews";
		}
		return viewName;
	}

	// /**
	// * Delete a NewsSource
	// * @param uiModel
	// * @param id the id of the NewsSource to delete
	// * @return the news entry page
	// */
	// @RequestMapping(value = "news/delete/{id}", method = RequestMethod.GET)
	// public String deleteNewsSource(Model uiModel, @PathVariable("id") long
	// id) {
	// newsService.deleteNewsSourcebyId(id);
	// return news(uiModel);
	// }
	//
	// /**
	// * Save a NewsSource
	// * @param uiModel
	// * @param source the NewsSource to save
	// * @param result the binding validation result
	// * @return the news entry page
	// */
	// @RequestMapping(value = "news/edit", method = RequestMethod.POST)
	// public String editNewsSource(Model uiModel, @ModelAttribute("source")
	// NewsSource source, BindingResult result) {
	// if ("".equals(source.getUrl().trim())) {
	// Errors errors = (Errors)result;
	// errors.rejectValue("url", "",
	// "Please enter a Url to an RSS or Atom feed.");
	// return "publishing/editNews";
	// }
	// source.setUrl(source.getUrl().trim());
	// newsService.saveNewsSource(source);
	// return news(uiModel);
	// }
	//
	// /**
	// * Move a news feed up in the display order
	// * @param uiModel
	// * @param id the id of the NewsSource to move
	// * @return the news entry page
	// */
	// @RequestMapping(value = "news/up/{id}", method = RequestMethod.GET)
	// public String moveUp(Model uiModel, @PathVariable("id") long id) {
	// newsService.moveNewsSourceUp(id);
	// return news(uiModel);
	// }
	//
	// /**
	// * Move a news feed down in the display order
	// * @param uiModel
	// * @param id the id of the NewsSource to move
	// * @return the news entry page
	// */
	// @RequestMapping(value = "news/down/{id}", method = RequestMethod.GET)
	// public String moveDown(Model uiModel, @PathVariable("id") long id) {
	// newsService.moveNewsSourceDown(id);
	// return news(uiModel);
	// }
}
