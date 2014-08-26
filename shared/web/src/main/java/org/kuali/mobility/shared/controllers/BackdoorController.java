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

import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.entity.Backdoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Controller
@RequestMapping("/backdoor")
public class BackdoorController {

	@Autowired
	@Qualifier("kmeGroupDao")
	private GroupDao groupDao;

	@Autowired
	private ConfigParamService configParamService;

	@RequestMapping(method = RequestMethod.GET)
	public String backdoor(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);
		if (backdoor != null) {
			uiModel.addAttribute("backdoor", backdoor);
		} else {
			uiModel.addAttribute("backdoor", new Backdoor());
		}
		return "backdoor";
	}

	@RequestMapping(value = "remove", method = RequestMethod.GET)
	public String removeBackdoor(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);
		if (backdoor != null) {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			if (user != null && backdoor.getActualUser() != null) {
				request.getSession().setAttribute(Constants.KME_USER_KEY, backdoor.getActualUser());
			}
			request.getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY, null);
		}
		return "redirect:/home";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, HttpServletResponse response, Model uiModel, @ModelAttribute("backdoor") Backdoor backdoor, BindingResult result) {
		if (isValidQuery(backdoor, result)) {
			User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
			backdoor.setActualUser(user);
			user = new UserImpl();
			user.setLoginName(backdoor.getUserId());

			Group group = getGroupDao().getGroup(getConfigParamService().findValueByName("Backdoor.Group.Name"));
			user.getGroups().add(group);

			request.getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY, backdoor);
			request.getSession().setAttribute(Constants.KME_USER_KEY, user);
			return "redirect:/home";
		} else {
			return "backdoor";
		}
	}

	private boolean isValidQuery(Backdoor backdoor, BindingResult result) {
		boolean hasErrors = false;
		Errors errors = ((Errors) result);
		if (backdoor.getUserId() == null || "".equals(backdoor.getUserId().trim())) {
			errors.rejectValue("userId", "", "Please enter a username");
			hasErrors = true;
		}
		return !hasErrors;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
}
