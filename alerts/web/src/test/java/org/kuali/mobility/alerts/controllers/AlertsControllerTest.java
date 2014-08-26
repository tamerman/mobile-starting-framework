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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class AlertsControllerTest {

	private static final String USER = "fauxUser";
	private static final String REDIRECT_HOME = "redirect:/campus?toolName=alerts";

	private static MockServletContext servletContext;
	private AlertsController controller;
	@Mock
	private AlertsService alertsService;
	@Mock
	private CampusService campusService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setController(new AlertsController());
		this.getController().setAlertsService(this.getAlertsService());
		this.getController().setCampusService(campusService);
		this.setRequest(new MockHttpServletRequest(servletContext));
		this.setResponse(new MockHttpServletResponse());
		this.setUiModel(new ExtendedModelMap());
		this.getRequest().setSession(new MockHttpSession());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testGetList() {
		User user = new UserImpl();
		user.setViewCampus(null);
		this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY, user);
//    	String viewName = getController().getList(getRequest(), getUiModel());
//    	assertTrue("alerts/list was not returned", "alerts/list".equalsIgnoreCase(viewName));
//    	user.setViewCampus("testCampus");
//    	when(campusService.needToSelectDifferentCampusForTool(any(String.class), any(String.class))).thenReturn(false).thenReturn(true);
//    	viewName = getController().getList(getRequest(), getUiModel());
//    	assertTrue("alerts/list was not returned", "alerts/list".equalsIgnoreCase(viewName));
//    	viewName = getController().getList(getRequest(), getUiModel());
//    	assertTrue("redirect:/campus?toolName=alerts was not returned", "redirect:/campus?toolName=alerts".equalsIgnoreCase(viewName));
	}

	public AlertsController getController() {
		return controller;
	}

	public void setController(AlertsController controller) {
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

	public AlertsService getAlertsService() {
		return alertsService;
	}

	public void setAlertsService(AlertsService alertsService) {
		this.alertsService = alertsService;
	}

	public Model getUiModel() {
		return uiModel;
	}

	public void setUiModel(Model uiModel) {
		this.uiModel = uiModel;
	}
}
