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

package org.kuali.mobility.alerts.controllers;

import flexjson.JSONSerializer;
import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("/alerts")
public class AlertsController {

	@Autowired
	private AlertsService alertsService;

	@Autowired
	private CampusService campusService;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@RequestMapping(method = RequestMethod.GET)
	public String getList(HttpServletRequest request, Model uiModel) {
		String viewName = "alerts/list";
		String campusCode = "ALL";

		User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		if (user.getViewCampus() != null) {
			if (campusService.needToSelectDifferentCampusForTool("alerts", user.getViewCampus())) {
				return "redirect:/campus?toolName=alerts";
			}
		} else {
			campusCode = user.getViewCampus();
		}

		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			viewName = "ui3/alerts/index";
		}

		uiModel.addAttribute("campus", campusCode);
		return viewName;
	}

	@RequestMapping(value = "/js/alerts.js")
	public String getJavaScript(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		String campusCode = "ALL";
		if (user != null && user.getViewCampus() != null) {
			campusCode = user.getViewCampus();
		}
		uiModel.addAttribute("campus", campusCode);

		return "ui3/alerts/js/alerts";
	}

	@Deprecated
	@RequestMapping(method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String getListJson(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		List<Alert> alerts = alertsService.findAlertsByCampus(user.getViewCampus());

		return new JSONSerializer().exclude("*.class").deepSerialize(alerts);
	}

	public AlertsService getAlertsService() {
		return alertsService;
	}

	public void setAlertsService(AlertsService alertsService) {
		this.alertsService = alertsService;
	}

	public CampusService getCampusService() {
		return campusService;
	}

	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
