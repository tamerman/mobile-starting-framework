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
import org.junit.runner.RunWith;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.push.entity.Sender;
import org.kuali.mobility.push.service.SenderService;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.CoreService;
import org.kuali.mobility.shared.entity.Backdoor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class HomeControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(HomeControllerTest.class);

	private static ApplicationContext applicationContext;

	private static MockServletContext servletContext;
	private HomeController controller;

	private Properties kmeProperties;

	@Mock
	private AdminService adminService;

	@Mock
	private AlertsService alertsService;

	@Mock
	private ConfigParamService configParamService;

	@Mock
	private CoreService coreService;

	@Mock
	private CampusService campusService;

	@Mock
	private SenderService senderService;

	private static List<String> supportedLanguages;

	private static final String CONTEXT_PATH = "/mdot";

	private static final String TEST_USER = "corwin";
	private static final String ADMIN_CONFIG_PARAM = "Admin.Group.Name";
	private static final String ADMIN_GROUP = "TEST_ADMIN_GROUP";
	private static final String GROUP = "TEST_GROUP";
	private static final String EMPTY = "";
	private static final String YES = "yes";
	private static final String NO = "no";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String COOKIE_NATIVE = "native";
	private static final String COOKIE_PLATFORM = "platform";
	private static final String APPLE = "iOS";
	private static final String ANDROID = "Android";
	private static final String BLACKBERRY = "Blackberry";
	private static final String WINDOWS = "WindowsMobile";
	private static final String ALIAS = "alias";

	private static final String APP_TITLE = "App Title";
	private static final Long APP_ID = Long.getLong("42");

	private static final String[] CAMPUS = {"ALL", "C1", "C2"};

	private static final String PREFERENCES = "preferences";
	private static final String INDEX = "index";
	private static final String CACHE_MANIFEST = "cacheManifest";
	private static final String LOGOUT = "logout";
	private static final String HOME_REDIRECT = "redirect:/home";
	private static final String ABOUT = "about";

	private static final String LAYOUT_PARAMETER = "homeLayout";
	private static final String LAYOUT_LIST = "list";
	private static final String LAYOUT_TILES = "tiles";

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
		setSupportedLanguages(new ArrayList());
		getSupportedLanguages().add("en");
		getSupportedLanguages().add("af");
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		this.setController(new HomeController());

		setKmeProperties(new Properties());
		getKmeProperties().setProperty("campus.default", CAMPUS[0]);
		getKmeProperties().setProperty("home.about.enabled", FALSE);
		getKmeProperties().setProperty("appcache.display.update", FALSE);

		getController().setCampusService(getCampusService());
		getController().setKmeProperties(getKmeProperties());
		getController().setAdminService(getAdminService());
		getController().setAlertsService(getAlertsService());
		getController().setConfigParamService(getConfigParamService());
		getController().setCoreService(getCoreService());
		getController().setSupportedLanguages(getSupportedLanguages());
		getController().setSenderService(getSenderService());

		when(getAdminService().getHomeScreenByAlias(CAMPUS[0])).thenAnswer(new FakeHomeScreenAnswer());
		when(getConfigParamService().findValueByName(ADMIN_CONFIG_PARAM)).thenReturn(ADMIN_GROUP);
		when(getSenderService().findAllUnhiddenSenders()).thenReturn(new ArrayList<Sender>());
	}

	@Test
	public void testInjectionSucceeded() {
		assertTrue("Failed to find a campus service", getController().getCampusService() == getCampusService());
		assertTrue("Failed to find a admin service", getController().getAdminService() == getAdminService());
		assertTrue("Failed to find a alerts service", getController().getAlertsService() == getAlertsService());
		assertTrue("Failed to find a config param service", getController().getConfigParamService() == getConfigParamService());
		assertTrue("Failed to find a core service", getController().getCoreService() == getCoreService());
		assertTrue("Failed to get home screen from admin service", null != getAdminService().getHomeScreenByAlias(CAMPUS[0]));
	}

	@Test
	public void testHome() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithNullViewCampus() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setViewCampus(null);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		getController().setKmeProperties(this.getKmeProperties());

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected when user's viewCampus is null.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithNullKmeProperties() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		getController().setKmeProperties(null);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected when kme properties is null.", null == viewName);
	}

	@Test
	public void testHomeWithAdminUser() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(ADMIN_GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithNonAdminUser() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setContextPath(CONTEXT_PATH);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

//	@Test
//	public void testHomeWithNullUser() {
//		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
//		Model uiModel = new ExtendedModelMap();
//
//		MockHttpSession session = new MockHttpSession();
//		session.setAttribute(Constants.KME_USER_KEY,null);
//
//		request.setSession(session);
//
//		String viewName;
//		try {
//			viewName = getController().home(null, request, uiModel);
//		} catch( Exception e ) {
//			LOG.error(e.getLocalizedMessage(),e);
//			viewName = null;
//		}
//		LOG.debug("Found view "+viewName);
//
//		assertTrue("View not what was expected with null user.", INDEX.equals(viewName));
//	}

	@Test
	public void testHomeWithNoNative() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(COOKIE_NATIVE, NO);
		cookieMonster[1] = new Cookie(COOKIE_PLATFORM, NO);

		request.setCookies(cookieMonster);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithiOS() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(COOKIE_NATIVE, YES);
		cookieMonster[1] = new Cookie(COOKIE_PLATFORM, APPLE);

		request.setCookies(cookieMonster);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithAndroid() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);
		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(COOKIE_NATIVE, YES);
		cookieMonster[1] = new Cookie(COOKIE_PLATFORM, ANDROID);

		request.setCookies(cookieMonster);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithWindows() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);
		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(COOKIE_NATIVE, YES);
		cookieMonster[1] = new Cookie(COOKIE_PLATFORM, WINDOWS);

		request.setCookies(cookieMonster);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithBlackberry() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);
		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		Cookie[] cookieMonster = new Cookie[2];
		cookieMonster[0] = new Cookie(COOKIE_NATIVE, YES);
		cookieMonster[1] = new Cookie(COOKIE_PLATFORM, BLACKBERRY);

		request.setCookies(cookieMonster);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithAliasAndBackdoor() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);
		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		request.addParameter(ALIAS, CAMPUS[1]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(user.getLoginName());
		session.setAttribute(Constants.KME_BACKDOOR_USER_KEY, backdoor);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testHomeWithAlertCount() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		request.setContextPath(CONTEXT_PATH);

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);
		Group group = new GroupImpl();
		group.setId(new Long(55));
		group.setName(GROUP);

		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		user.setGroups(groups);

		request.addParameter(ALIAS, CAMPUS[1]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY, user);

		request.setSession(session);

		when(getAlertsService().findAlertCountByCampus(CAMPUS[0])).thenReturn(5);

		String viewName;
		try {
			viewName = getController().home(null, request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		LOG.debug("Found view " + viewName);

		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testPreferences() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		String viewName;
		try {
			viewName = getController().preferences(null, null, request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected.", PREFERENCES.equals(viewName));
	}

	@Test
	public void testPreferencesWithEditableLayout() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		getKmeProperties().setProperty("home.layout.userEditable", FALSE);

		String viewName;
		try {
			viewName = getController().preferences(null, null, request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for preferences with editable layout.", PREFERENCES.equals(viewName));
	}

	@Test
	public void testPreferencesWithNullProperties() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		getController().setKmeProperties(null);

		String viewName;
		try {
			viewName = getController().preferences(null, null, request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View should be null and was not.", null == viewName);
	}

	@Test
	public void testPreferencesWithEmptyRequestParam() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		String viewName;
		try {
			viewName = getController().preferences(null, EMPTY, request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for preferences with empty request param.", PREFERENCES.equals(viewName));
	}

	@Test
	public void testPreferencesWithRequestParam() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpSession session = new MockHttpSession();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(TEST_USER);
		user.setViewCampus(CAMPUS[0]);

		session.setAttribute(Constants.KME_USER_KEY, user);
		request.setSession(session);
		request.setContextPath(CONTEXT_PATH);

		String viewName;
		try {
			viewName = getController().preferences(null, LAYOUT_TILES, request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for preferences with empty request param.", PREFERENCES.equals(viewName));
	}

	@Test
	public void testCacheManifest() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		String viewName;
		try {
			viewName = getController().cachemanifest(request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for cachemanifest.", CACHE_MANIFEST.equals(viewName));
	}

	@Test
	public void tesCacheManifestWithNullProperties() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		getController().setKmeProperties(null);

		String viewName;
		try {
			viewName = getController().cachemanifest(request, response, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for cachemanifest with null properties.", CACHE_MANIFEST.equals(viewName));
	}

	@Test
	public void testLogout() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		String viewName;
		try {
			viewName = getController().logout(request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for logout.", LOGOUT.equals(viewName));
	}

	@Test
	public void testLogoutYes() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		String viewName;
		try {
			viewName = getController().logoutYes(request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for logout.", HOME_REDIRECT.equals(viewName));
	}

	@Test
	public void testAbout() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		String viewName;
		try {
			viewName = getController().about(request, uiModel);
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
			viewName = null;
		}
		assertTrue("View not what was expected for logout.", ABOUT.equals(viewName));
	}

	public SenderService getSenderService() {
		return senderService;
	}

	public void setSenderService(SenderService senderService) {
		this.senderService = senderService;
	}

	private static class FakeHomeScreenAnswer implements Answer {
		public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
			return getTestHomeScreen();
		}

		private HomeScreen getTestHomeScreen() {
			HomeScreen home = new HomeScreen();
			home.setTitle(APP_TITLE);
			home.setAlias(CAMPUS[0]);
			home.setHomeScreenId(APP_ID);
			List<HomeTool> homeTools = new ArrayList<HomeTool>();
			// News Tool
			HomeTool tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(1));
			tool.setOrder(1);
			Tool iAmA = new Tool();
			iAmA.setTitle("News");
			iAmA.setAlias("news");
			iAmA.setDescription("The news live at 10!");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(1));
			iAmA.setUrl("news");
			tool.setTool(iAmA);
			homeTools.add(tool);
			// Events Tool
			tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(2));
			tool.setOrder(2);
			iAmA = new Tool();
			iAmA.setTitle("Events");
			iAmA.setAlias("events");
			iAmA.setDescription("View the event calendar!");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(2));
			iAmA.setUrl("events");
			tool.setTool(iAmA);
			homeTools.add(tool);
			// Alerts Tool
			tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(3));
			tool.setOrder(4);
			iAmA = new Tool();
			iAmA.setTitle("Alerts");
			iAmA.setAlias("alerts");
			iAmA.setDescription("View campus alerts!");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(3));
			iAmA.setUrl("alerts");
			tool.setTool(iAmA);
			homeTools.add(tool);
			// Incident Tool
			tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(4));
			tool.setOrder(3);
			iAmA = new Tool();
			iAmA.setTitle("Incidents");
			iAmA.setAlias("incident");
			iAmA.setDescription("Report abberant behavior on campus.");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(4));
			iAmA.setUrl("incident");
			tool.setTool(iAmA);
			homeTools.add(tool);
			// Reporting Admin Tool
			tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(5));
			tool.setOrder(5);
			iAmA = new Tool();
			iAmA.setTitle("Reporting Admin");
			iAmA.setAlias("reportingadmin");
			iAmA.setDescription("Administer Reporting.");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(5));
			iAmA.setUrl("reportingadmin");
			tool.setTool(iAmA);
			homeTools.add(tool);
			// Backdoor Tool
			tool = new HomeTool();
			tool.setHomeScreenId(APP_ID);
			tool.setHomeScreen(home);
			tool.setHomeToolId(new Long(6));
			tool.setOrder(6);
			iAmA = new Tool();
			iAmA.setTitle("Backdoor Tool");
			iAmA.setAlias("backdoor");
			iAmA.setDescription("Back door to adventure!");
			iAmA.setIconUrl("url/to/image.png");
			iAmA.setToolId(new Long(6));
			iAmA.setUrl("backdoor");
			tool.setTool(iAmA);
			homeTools.add(tool);

			home.setHomeTools(homeTools);

			return home;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		HomeControllerTest.applicationContext = applicationContext;
	}

	public static List<String> getSupportedLanguages() {
		return supportedLanguages;
	}

	public static void setSupportedLanguages(List<String> supportedLanguages) {
		HomeControllerTest.supportedLanguages = supportedLanguages;
	}

	public static MockServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(MockServletContext servletContext) {
		HomeControllerTest.servletContext = servletContext;
	}

	public HomeController getController() {
		return controller;
	}

	public void setController(HomeController controller) {
		this.controller = controller;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public AlertsService getAlertsService() {
		return alertsService;
	}

	public void setAlertsService(AlertsService alertsService) {
		this.alertsService = alertsService;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}

	public CoreService getCoreService() {
		return coreService;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
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
