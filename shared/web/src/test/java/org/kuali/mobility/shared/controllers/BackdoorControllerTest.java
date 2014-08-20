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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.entity.Backdoor;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class BackdoorControllerTest {
	private static final String[] USER = {"oberon",""};
	private static final String[] VIEWS = {"backdoor","redirect:/home"};
	private static final String BACKDOOR_ATTRIBUTE = "backdoor";
	private static final String BACKDOOR_GROUP = "KME-BACKDOOR";
	private static final String EMPTY = "";

	private static MockServletContext servletContext;
	private BackdoorController controller;
	private Properties kmeProperties;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;

	@Mock
	private ConfigParamService configParamService;
	@Mock
	private GroupDao groupDao;

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setController(new BackdoorController());
		getController().setConfigParamService(this.getConfigParamService());
		getController().setGroupDao(this.getGroupDao());
		this.setRequest(new MockHttpServletRequest(servletContext));
		this.setResponse(new MockHttpServletResponse());
		this.setUiModel(new ExtendedModelMap());
		getRequest().setSession(new MockHttpSession(servletContext));
	}

	@Test
	public void testBackdoor() {
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY, backdoor);

		getController().backdoor(getRequest(), getResponse(), getUiModel());

		assertTrue("Failed to find original backdoor in model.", backdoor.equals((Backdoor) (getUiModel().asMap().get(BACKDOOR_ATTRIBUTE))));
	}

	@Test
	public void testBackdoorWhenNull() {
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY, null);

		getController().backdoor(getRequest(), getResponse(), getUiModel());

		assertFalse("Failed to find original backdoor in model.", backdoor.getUserId().equals((Backdoor) (getUiModel().asMap().get(BACKDOOR_ATTRIBUTE))));
	}

	@Test
	public void testRemoveBackdoor() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY, user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		backdoor.setActualUser(user);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		String viewName = getController().removeBackdoor(getRequest(),getResponse(),getUiModel());
		assertTrue("Failed to find proper view name.", VIEWS[1].equals(viewName));
	}

	@Test
	public void testRemoveBackdoorWhenNull() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		backdoor.setActualUser(user);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,null);

		String viewName = getController().removeBackdoor(getRequest(),getResponse(),getUiModel());
		assertTrue("Failed to find proper view name.", VIEWS[1].equals(viewName));
	}

	@Test
	public void testRemoveBackdoorWhenUserNull() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,null);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		backdoor.setActualUser(user);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		String viewName = getController().removeBackdoor(getRequest(),getResponse(),getUiModel());
		assertTrue("Failed to find proper view name.", VIEWS[1].equals(viewName));
	}

	@Test
	public void testRemoveBackdoorWhenActualUserNull() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		backdoor.setActualUser(null);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		String viewName = getController().removeBackdoor(getRequest(), getResponse(), getUiModel());
		assertTrue("Failed to find proper view name.",VIEWS[1].equals(viewName));
	}

	@Test
	public void testSubmit() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(USER[0]);
		backdoor.setActualUser(null);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		Group group = new GroupImpl();
		group.setName(BACKDOOR_GROUP);
		group.setId(new Long(87));

		when(getController().getConfigParamService().findValueByName(any(String.class))).thenReturn(BACKDOOR_GROUP);
		when(getController().getGroupDao().getGroup(BACKDOOR_GROUP)).thenReturn(group);

		BindingResult bindingResult = new MapBindingResult(new HashMap<String,String>(),new String());

		String viewName = getController().submit(getRequest(),getResponse(),getUiModel(),backdoor,bindingResult);
		assertTrue("Failed to find proper view name.",VIEWS[1].equals(viewName));

		User altUser = (User)request.getSession().getAttribute(Constants.KME_USER_KEY);
		assertTrue("Newly created user could not be retrieved from the session.",altUser != null);
		assertTrue("Group KME-BACKDOOR not found on user",altUser.isMember(BACKDOOR_GROUP));
	}

	@Test
	public void testSubmitWithEmptyBackdoorUser() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(EMPTY);
		backdoor.setActualUser(user);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		BindingResult bindingResult = new MapBindingResult(new HashMap<String,String>(),new String());

		String viewName = getController().submit(getRequest(),getResponse(),getUiModel(),backdoor,bindingResult);
		assertTrue("Failed to find proper view name.",VIEWS[0].equals(viewName));
		assertTrue("Binding result did not contain the expected error.",bindingResult.hasErrors()&&bindingResult.hasFieldErrors("userId"));
	}

	@Test
	public void testSubmitWithNullBackdoorUser() {
		User user = new UserImpl();
		user.setLoginName(USER[0]);
		getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
		Backdoor backdoor = new Backdoor();
		backdoor.setUserId(null);
		backdoor.setActualUser(null);
		getRequest().getSession().setAttribute(Constants.KME_BACKDOOR_USER_KEY,backdoor);

		BindingResult bindingResult = new MapBindingResult(new HashMap<String,String>(),new String());

		String viewName = getController().submit(getRequest(),getResponse(),getUiModel(),backdoor,bindingResult);
		assertTrue("Failed to find proper view name.",VIEWS[0].equals(viewName));
		assertTrue("Binding result did not contain the expected error.",bindingResult.hasErrors()&&bindingResult.hasFieldErrors("userId"));
	}

	public BackdoorController getController() {
		return controller;
	}

	public void setController(BackdoorController controller) {
		this.controller = controller;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
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

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
