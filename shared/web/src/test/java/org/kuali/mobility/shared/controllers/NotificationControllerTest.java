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

package org.kuali.mobility.shared.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.entity.UserNotification;
import org.kuali.mobility.notification.service.NotificationService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class NotificationControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationControllerTest.class);

	private static final String[] CAMPUS = {"ALL","C1","C2","C3"};
	private static final String TITLE = "Faux Title";
	private static final String MESSAGE = "Faux message";
	private static final String[] DEVICE_ID = {"1234567890","0987654321"};
	private static final String EMPTY_JSON = "[]";
	private static final String FULL_JSON = "[{\"message\":\"Faux message\",\"primaryCampus\":\"ALL\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C1\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C2\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C3\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"ALL\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C1\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C2\",\"title\":\"Faux Title\"},{\"message\":\"Faux message\",\"primaryCampus\":\"C3\",\"title\":\"Faux Title\"}]";
	private static final String FILTERED_JSON = "[{\"message\":\"Faux message\",\"primaryCampus\":\"ALL\",\"title\":\"Faux Title\"}]";
	private static final String REQUEST_URL = "/woot";
	private static final String VIEW_NAME = "redirect:"+REQUEST_URL;

	private static MockServletContext servletContext;
	private NotificationController controller;
	@Mock
	private NotificationService service;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setController(new NotificationController());
		this.getController().setNotificationService(this.getService());
		this.setRequest(new MockHttpServletRequest(servletContext));
		this.setResponse(new MockHttpServletResponse());
		this.setUiModel(new ExtendedModelMap());
		this.getRequest().setSession(new MockHttpSession(servletContext));
	}

	@Test
	public void testNotificationsWithNullUserNotifications() {
		when(getService().findAllValidNotifications(any(Date.class))).thenReturn(getNotificationList());
		when(getService().findUserNotificationByNotificationId(any(Long.class))).thenReturn(null);
		String json = getController().notifications(getRequest(),null,getUiModel());
		assertTrue("Did not received notifications and should have.", FULL_JSON.equals(json));
	}

	@Test
	public void testNotificationsWithNullNotifications() {
		when(getService().findAllValidNotifications(any(Date.class))).thenReturn(null);
		String json = getController().notifications(getRequest(),null,getUiModel());
		assertTrue("Received notifications and should not have.", EMPTY_JSON.equals(json));
	}

	@Test
	public void testNotificationsWithUserNotifications() {
		when(getService().findAllValidNotifications(any(Date.class))).thenReturn(getNotificationList());
		when(getService().findUserNotificationByNotificationId(any(Long.class))).thenAnswer(
			new Answer<UserNotification>() {
				boolean didAnswer = false;
				@Override
				public UserNotification answer(InvocationOnMock invocationOnMock) throws Throwable {
					if(didAnswer) {
						return new UserNotification();
					} else {
						didAnswer = true;
						return null;
					}
				}
		});
		String json = getController().notifications(getRequest(),DEVICE_ID[0],getUiModel());
		assertTrue("Did not receive notifications and should have.", FILTERED_JSON.equals(json));
	}

	@Test
	public void testSubmit() {
		User user = new UserImpl();
		user.setRequestURL(REQUEST_URL);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		String viewName = getController().submit(getRequest(),getUiModel(),null,null);
		assertTrue("Failed to find proper view name.",VIEW_NAME.equals(viewName));
	}

	protected List<Notification> getNotificationList() {
		List<Notification> notices = new ArrayList<Notification>();

		// Create 8 notices, 2 per campus
		// One should start today and end tomorrow
		// The other should start tomorrow and end the following day
		int i = 0;
		Notification notice;
		for(String s : CAMPUS) {
			i++;
			notice = new Notification();
			notice.setPrimaryCampus(s);
			notice.setTitle(TITLE);
			notice.setMessage(MESSAGE);
			Calendar cal = Calendar.getInstance();
			notice.setStartDate(new Timestamp( cal.getTimeInMillis()));
			cal.add(Calendar.DATE,1);
			notice.setEndDate(new Timestamp(cal.getTimeInMillis()));
			notice.setNotificationId(new Long(i));
			notice.setNotificationType(new Long(42));
			notices.add(notice);
		}
		for(String s : CAMPUS) {
			i++;
			notice = new Notification();
			notice.setPrimaryCampus(s);
			notice.setTitle(TITLE);
			notice.setMessage(MESSAGE);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE,1);
			notice.setStartDate(new Timestamp( cal.getTimeInMillis()));
			cal.add(Calendar.DATE,1);
			notice.setEndDate(new Timestamp(cal.getTimeInMillis()));
			notice.setNotificationId(new Long(i));
			notice.setNotificationType(new Long(53));
			notices.add(notice);
		}
		return notices;
	}

	public NotificationController getController() {
		return controller;
	}

	public void setController(NotificationController controller) {
		this.controller = controller;
	}

	public MockHttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(MockHttpServletRequest request) {
		this.request = request;
	}

	public MockHttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(MockHttpServletResponse response) {
		this.response = response;
	}

	public Model getUiModel() {
		return uiModel;
	}

	public void setUiModel(Model uiModel) {
		this.uiModel = uiModel;
	}

	public NotificationService getService() {
		return service;
	}

	public void setService(NotificationService service) {
		this.service = service;
	}
}
