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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.LoginService;
import org.kuali.mobility.shared.entity.MockUser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class MockLoginControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(MockLoginControllerTest.class);

	private static ApplicationContext applicationContext;

	private static MockServletContext servletContext;
	private MockLoginController controller;

	private static final String LOGIN_VIEW = "mocklogin";
	private static final String REDIRECT_VIEW = "redirect:/home";
	private static final String TEST_USER = "nurul";
	private static final String EMPTY = "";
	private static final String TEST_PASSWORD = "6968a2c57c3a4fee8fadc79a80355e4d";
	private static final String INVALID = "1234567890";

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/MockLoginControllerSpringBeans.xml"};
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		MockLoginControllerTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
		servletContext = new MockServletContext();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		this.setController((MockLoginController) getApplicationContext().getBean("loginController"));
	}

	@Test
	public void testMockLoginNoUser() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		String viewName = getController().mockLogin(request, response, uiModel);

		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testMockLoginWithUser() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(TEST_USER);
		user.setPassword(TEST_PASSWORD);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		String viewName = getController().mockLogin(request, response, uiModel);

		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithValidUser() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(TEST_USER);
		user.setPassword(TEST_PASSWORD);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", REDIRECT_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithInvalidPassword() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(TEST_USER);
		user.setPassword(INVALID);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithNullPassword() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(TEST_USER);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithEmptyPassword() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(TEST_USER);
		user.setPassword(EMPTY);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithNullUserId() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(null);
		user.setPassword(TEST_PASSWORD);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testSubmitWithEmptyUserId() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		MockUser user = (MockUser) getApplicationContext().getBean("user");
		user.setUserId(EMPTY);
		user.setPassword(TEST_PASSWORD);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_MOCK_USER_KEY, user);

		request.setSession(session);

		BindingResult result = new MapBindingResult(new HashMap<String, String>(), new String());

		String viewName;
		try {
			viewName = getController().submit(request, response, uiModel, user, result);
		} catch (NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(), npe);
			viewName = null;
		}
		assertTrue("View name is incorrect.", LOGIN_VIEW.equals(viewName));
	}

	@Test
	public void testCalculateHash() {
		String expected = "2a7ee8b03046524ae8b6fcdc88345fa5";
		try {
			String response = MockLoginController.calculateHash(MessageDigest.getInstance("MD5"), TEST_PASSWORD);
			assertTrue("Expected " + expected + " and got " + response, expected.equals(response));
		} catch (NoSuchAlgorithmException nsae) {
			LOG.error(nsae.getLocalizedMessage(), nsae);
			fail("Class being tested threw an exception.");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			fail("Class being tested threw an exception.");
		}
	}

	@Test
	public void testGetService() {
		LoginService testService = getController().getLoginService();
		assertTrue("Failed to find login service.", testService != null && testService.equals((LoginService) getApplicationContext().getBean("loginService")));
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		MockLoginControllerTest.applicationContext = applicationContext;
	}

	public MockLoginController getController() {
		return controller;
	}

	public void setController(MockLoginController controller) {
		this.controller = controller;
	}
}
