/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.shared.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/campus")
public class CampusController {

	private static final Logger LOG = LoggerFactory.getLogger(CampusController.class);

	@Autowired
	private CampusService campusService;

	@Autowired
	private AdminService adminService;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@RequestMapping(method = RequestMethod.GET)
	public String getList(HttpServletRequest request, Model uiModel,
						  @RequestParam(required = true) String toolName) {
		List<Campus> campuses = getCampusService().findCampusesByTool(toolName);
		List<String> homeToolsList = null;
		String viewName = null;
		homeToolsList = getToolsList();
		if (homeToolsList.contains(toolName)) {
			if (campuses == null || campuses.isEmpty()) {
				User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
				user.setViewCampus("ALL");
				return "redirect:/" + toolName;
			} else if (campuses.size() == 1) {
				User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
				user.setViewCampus(campuses.get(0).getCode());
				return "redirect:/" + toolName;
			} else if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
				uiModel.addAttribute("campuses", campuses);
				uiModel.addAttribute("toolName", toolName);
				viewName = "ui3/home/campus";
			} else {
				uiModel.addAttribute("campuses", campuses);
				uiModel.addAttribute("toolName", toolName);
				viewName = "campus";
			}
			return viewName;
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "/select", method = RequestMethod.GET)
	public String selectCampus(HttpServletRequest request,
							   HttpServletResponse response, Model uiModel,
							   @RequestParam(required = true) String campus,
							   @RequestParam(required = true) String toolName) {
		List<String> homeToolsList = null;
		homeToolsList = getToolsList();
		if (homeToolsList.contains(toolName)) {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			user.setViewCampus(campus);

			boolean useSecureCookie = Boolean.parseBoolean(getKmeProperties().getProperty("kme.secure.cookie", "false"));
			Cookie cookie = new Cookie("campusSelection", campus);
			int cookieMaxAge = Integer.parseInt(getKmeProperties().getProperty("cookie.max.age", "3600"));
			cookie.setMaxAge(cookieMaxAge); // default one hour, should implement in kme.config.properties.
			cookie.setPath(request.getContextPath());
			cookie.setSecure(useSecureCookie);
			response.addCookie(cookie);
			return "redirect:/" + toolName;
		} else {
			return "redirect:/home";
		}
	}

	@RequestMapping(value = "/templates/{key}")
	public String getAngularTemplates(
			@PathVariable("key") String key,
			HttpServletRequest request,
			Model uiModel) {
		return "ui3/home/templates/" + key;
	}

	@RequestMapping(value = "/js/campus.js")
	public String getJavaScript(Model uiModel, HttpServletRequest request) {
		return "ui3/home/js/campus";
	}


	/**
	 * @deprecated This is not generic at all. Use an other method
	 * to detect tools that are configured with KME
	 */
	@Deprecated
	public List<String> getToolsList() {
		List<String> toolsList = new ArrayList<String>();

		for (Tool tool : getAdminService().getAllTools()) {
			toolsList.add(tool.getAlias());
		}

		return toolsList;
	}

	public CampusService getCampusService() {
		return campusService;
	}

	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
