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

import flexjson.JSONSerializer;
import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.entity.UserNotification;
import org.kuali.mobility.notification.service.NotificationService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

//    @Autowired 
//    private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String notifications(HttpServletRequest request, @RequestParam("deviceId") String deviceId, Model uiModel) {
		List<Notification> notifications = getNotificationService().findAllValidNotifications(new Date());
		if (notifications == null) {
			return "[]";
		}

		List<Notification> newNotifications = new ArrayList<Notification>();
		for (Notification notification : notifications) {
			UserNotification un = getNotificationService().findUserNotificationByNotificationId(notification.getNotificationId());
			if (un == null) {
//				User user = userService.findUserByDeviceId(deviceId);
//				if (user != null) {
//					if (notification.getPrimaryCampus() == null || notification.getPrimaryCampus().equals(user.getPrimaryCampus())) {
				newNotifications.add(notification);
				UserNotification userNotification = new UserNotification();
				userNotification.setDeviceId(deviceId);
				userNotification.setNotifyDate(new Timestamp(new Date().getTime()));
//						userNotification.setPersonId(user.getPrincipalId());
				userNotification.setNotificationId(notification.getNotificationId());
				//notificationService.saveUserNotification(userNotification);
//					}
//				}
			}
		}

		return new JSONSerializer().exclude("*.class", "notificationId", "startDate", "endDate", "notificationType", "versionNumber").serialize(newNotifications);
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, Model uiModel, @ModelAttribute("command") Object command, BindingResult result) {
		User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		String service = user.getRequestURL();
		user.setRequestURL(null);
//    	user.setUserAttribute("acked", "true");
		return "redirect:" + service;
	}

	public NotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}
}
