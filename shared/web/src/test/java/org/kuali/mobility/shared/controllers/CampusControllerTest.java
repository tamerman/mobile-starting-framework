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

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class CampusControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(CampusControllerTest.class);

	private static ApplicationContext applicationContext;

	private static MockServletContext servletContext;
	
	@Mock
	private AdminService adminService;
	
	private CampusController controller;
	private Properties kmeProperties;

	private static final String REDIRECT_HOME = "redirect:/home";
	private static final String REDIRECT_DINING = "redirect:/dining";
	private static final String REDIRECT_LABS = "redirect:/computerlabs";
	private static final String REDIRECT_GRADES = "redirect:/grades";
	private static final String REDIRECT_CAMPUS = "campus";
	private static final String USER = "fauxUser";
	private static final String[] VIEW_CAMPUS = {"ALL","BL","CO","TEST"};
	private static final String[] TOOL_NAMES = {"home","dining","computerlabs","",null,"TEST_TOOL","grades","maps"};

	private static String[] getConfigLocations() {
		return new String[] { "classpath:/CampusSpringBeans.xml" };
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		CampusControllerTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
		servletContext = new MockServletContext();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		this.setController((CampusController)getApplicationContext().getBean("campusController"));
		this.getController().setAdminService(getAdminService());

		setKmeProperties(new Properties());
		getKmeProperties().setProperty("kme.secure.cookie", "false");
		getController().setKmeProperties(this.getKmeProperties());
		
		when(getAdminService().getAllTools()).thenReturn(getMockToolsList());
	}

	private List<Tool> getMockToolsList() {
		ArrayList<Tool> toolsList = new ArrayList<Tool>();
		
		Tool dining = new Tool();		
		dining.setToolId(4L);
		dining.setAlias("dining");
		dining.setDescription("dining.home.description");
		dining.setIconUrl("images/service-icons/srvc-dining.png");
		dining.setRequisites(0);
		dining.setTitle("dining.home.title");
		dining.setUrl("dining");
		dining.setVersionNumber(0L);
		toolsList.add(dining);
		
		Tool computerLabs = new Tool();		
		computerLabs.setToolId(5L);
		computerLabs.setAlias("computerlabs");
		computerLabs.setDescription("computerlabs.home.description");
		computerLabs.setIconUrl("images/service-icons/srvc-stc.png");
		computerLabs.setRequisites(0);
		computerLabs.setTitle("computerlabs.home.title");
		computerLabs.setUrl("computerlabs");
		computerLabs.setVersionNumber(0L);
		toolsList.add(computerLabs);
		
		Tool grades = new Tool();
		grades.setToolId(7L);
		grades.setAlias("grades");
		grades.setDescription("grades.home.description");
		grades.setIconUrl("images/service-icons/srvc-grades.png");
		grades.setRequisites(0);
		grades.setTitle("grades.home.title");
		grades.setUrl("grades");
		grades.setVersionNumber(0L);
		toolsList.add(grades);
		
		Tool maps = new Tool();
		maps.setToolId(10L);
		maps.setAlias("maps");
		maps.setDescription("maps.home.description");
		maps.setIconUrl("images/service-icons/srvc-maps.png");
		maps.setRequisites(0);
		maps.setTitle("maps.home.title");
		maps.setUrl("maps");
		maps.setVersionNumber(0L);
		toolsList.add(maps);		
		
		return toolsList;
	}

	@Test
	public void testGetList() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[0]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName = getController().getList(request,uiModel,TOOL_NAMES[0]);

		assertTrue("View name is incorrect.", REDIRECT_HOME.equals(viewName));
	}

	@Test
	public void testGetListB() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[0]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().getList(request,uiModel,TOOL_NAMES[1]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect.", REDIRECT_DINING.equals(viewName));
	}

	@Test
	public void testGetListC() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[3]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().getList(request,uiModel,TOOL_NAMES[5]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect: Expected " + REDIRECT_HOME + " and found " + viewName, REDIRECT_HOME.equals(viewName));
	}

	@Test
	public void testGetListD() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[3]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().getList(request,uiModel,TOOL_NAMES[6]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect: Expected "+REDIRECT_GRADES+" and found "+viewName, REDIRECT_GRADES.equals(viewName));
	}

	@Test
	public void testGetListE() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[3]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().getList(request,uiModel,TOOL_NAMES[7]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect: Expected "+REDIRECT_CAMPUS+" and found "+viewName, REDIRECT_CAMPUS.equals(viewName));
	}

	@Test
	public void testSelectCampus() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[3]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		String viewName;
		try {
			viewName = getController().selectCampus(request,response,uiModel,VIEW_CAMPUS[3],TOOL_NAMES[2]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect: Expected "+REDIRECT_LABS+" and found "+viewName, REDIRECT_LABS.equals(viewName));
	}

	@Test
	public void testSelectCampusB() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		Model uiModel = new ExtendedModelMap();

		User user = new UserImpl();
		user.setLoginName(USER);
		user.setViewCampus(VIEW_CAMPUS[3]);

		MockHttpSession session = new MockHttpSession();
		session.setAttribute(Constants.KME_USER_KEY,user);

		request.setSession(session);

		getKmeProperties().setProperty("kme.secure.cookie", "true");

		String viewName;
		try {
			viewName = getController().selectCampus(request,response,uiModel,VIEW_CAMPUS[3],TOOL_NAMES[5]);
		} catch(NullPointerException npe) {
			LOG.error(npe.getLocalizedMessage(),npe);
			viewName=null;
		}

		assertTrue("View name is incorrect: Expected "+REDIRECT_HOME+" and found "+viewName, REDIRECT_HOME.equals(viewName));
	}

	@Test
	public void testGetService() {
		CampusService testService = getController().getCampusService();
		assertTrue("Failed to find campus service.",testService!=null && testService.equals((CampusService)getApplicationContext().getBean("campusService")));
		assertTrue("Failed to find a admin service",getController().getAdminService()==getAdminService());
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		CampusControllerTest.applicationContext = applicationContext;
	}

	public static MockServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(MockServletContext servletContext) {
		CampusControllerTest.servletContext = servletContext;
	}

	public CampusController getController() {
		return controller;
	}

	public void setController(CampusController controller) {
		this.controller = controller;
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
