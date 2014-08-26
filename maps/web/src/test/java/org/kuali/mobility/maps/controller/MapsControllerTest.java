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

package org.kuali.mobility.maps.controller;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.maps.controllers.MapsController;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsFormSearchResultContainer;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.kuali.mobility.maps.service.MapsService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class MapsControllerTest {

	private static final String USER = "fauxUser";
	private static final String REDIRECT_HOME = "redirect:/campus?toolName=maps";
	private static final String[] VIEW_CAMPUS = {"ALL", "BL", "CO", "TEST"};

	private static MockServletContext servletContext;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;
	private MapsController mapsController;
	@Mock
	private static ApplicationContext applicationContext;
	@Mock
	private MapsService mapsService;
	@Mock
	private Properties kmeProperties;
	@Mock
	private ConfigParamService configParamService;

	@BeforeClass
	public static void setUpClass() throws Exception {
		setServletContext(new MockServletContext());
	}

	@Before
	public void preTest() {
		this.setMapsController(new MapsController());
		this.getMapsController().setApplicationContext(applicationContext);
		this.getMapsController().setMapsService(this.getMapsService());
		this.getMapsController().setKmeProperties(kmeProperties);
		this.getMapsController().setConfigParamService(getConfigParamService());
		this.setRequest(new MockHttpServletRequest(servletContext));
		this.setResponse(new MockHttpServletResponse());
		this.setUiModel(new ExtendedModelMap());
		this.getRequest().setSession(new MockHttpSession());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testGetHome() {
		User user = new UserImpl();
		user.setLoginName(USER);
		this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY, user);
		String viewName = getMapsController().getHome(getUiModel(), getRequest(), "", "", "", "", "");
		assertTrue("did not return " + REDIRECT_HOME + " but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));

		user.setViewCampus(VIEW_CAMPUS[1]);
		Properties properties = new Properties();
		properties.setProperty("maps.center.lat", "1");
		properties.setProperty("maps.center.lon", "-1");
		properties.setProperty("maps.useCampusBounds", "true");
		when(kmeProperties.getProperty("maps.api", "google")).thenReturn("mapquest");
		when(kmeProperties.getProperty("kme.uiVersion", "classic")).thenReturn("classic");
		when(applicationContext.getBean(any(String.class))).thenReturn(null).thenReturn(properties);
		viewName = getMapsController().getHome(getUiModel(), getRequest(), "", "", "", "", "");
		assertTrue("uiModel initialLatitude was not set to 0.0!", getUiModel().asMap().get("initialLatitude").equals("0.0"));
		assertTrue("uiModel initialLongitude was not set to 0.0!", getUiModel().asMap().get("initialLongitude").equals("0.0"));
		assertTrue("view name did not return correct string, expected maps/mapquest and got " + viewName, "maps/mapquest".equalsIgnoreCase(viewName));
		when(kmeProperties.getProperty("maps.api", "google")).thenReturn("google");
		viewName = getMapsController().getHome(getUiModel(), getRequest(), "", "", "", "", "");
		assertTrue("uiModel initialLatitude was not set to 1!", getUiModel().asMap().get("initialLatitude").equals("1"));
		assertTrue("uiModel initialLongitude was not set to -1!", getUiModel().asMap().get("initialLongitude").equals("-1"));
		assertTrue("view name did not return correct string, expected maps/home and got " + viewName, "maps/home".equalsIgnoreCase(viewName));
	}

	@Test
	public void testGetBuildings() {
		MapsGroup group = new MapsGroup();
		group.setId("1");
		when(mapsService.getMapsGroupById(any(String.class))).thenReturn(null).thenReturn(group);
		Object viewObject = getMapsController().getBuildings("0");
		assertTrue("returned class does not match", viewObject.getClass().equals(new ResponseEntity<String>(HttpStatus.NOT_FOUND).getClass()));
		viewObject = getMapsController().getBuildings("1");
		assertTrue("returned object does not match test object", viewObject.equals(group.toJson()));
	}

	@Test
	public void testGet() {
		Location location = new Location();
		location.setId("1");
		when(mapsService.getLocationById(any(String.class))).thenReturn(null).thenReturn(location);
		Object viewObject = getMapsController().get("0");
		assertTrue("returned class does not match", viewObject.getClass().equals(new ResponseEntity<String>(HttpStatus.NOT_FOUND).getClass()));
		viewObject = getMapsController().get("1");
		assertTrue("returned object does not match test object", viewObject.equals(location.toJson()));
	}

	@Test
	public void testGet2() {
		Location location = new Location();
		location.setId("0");
		when(mapsService.getLocationById(any(String.class))).thenReturn(null).thenReturn(location);
		Object viewObject = getMapsController().get(getUiModel(), "0");
		assertTrue("string maps/building was not returned", "maps/building".equalsIgnoreCase(viewObject.toString()));
		viewObject = getMapsController().get(getUiModel(), "0");
		assertTrue("location Id does not match test location Id", getUiModel().asMap().get("id").equals(location.getId()));
		assertTrue("string maps/building was not returned", "maps/building".equalsIgnoreCase(viewObject.toString()));
	}

	@Test
	public void testSearchBuildings() {
		Location location1 = new Location();
		location1.setLatitude(1.0);
		location1.setLongitude(1.1);
		location1.setName("testLocation");
		location1.setId("testId");
		Location location2 = new Location();
		location2.setLatitude(0.0);
		location2.setLongitude(0.0);
		location2.setName("testLocation");
		location2.setId("testId");
		Set<Location> locationSet = new HashSet<Location>();
		locationSet.add(location1);
		locationSet.add(location2);
		MapsGroup group = new MapsGroup();
		group.setMapsLocations(locationSet);
		when(mapsService.getMapsGroupById(any(String.class))).thenReturn(null).thenReturn(group);
		String viewName = getMapsController().searchBuildings(getUiModel(), "searchString", "searchGroupId");
		assertTrue("searchResults were found", 0 == ((MapsFormSearchResultContainer) getUiModel().asMap().get("container")).getResults().size());
		viewName = getMapsController().searchBuildings(getUiModel(), "searchString", "searchGroupId");
		assertTrue("did not return the correct string maps/search", "maps/search".equalsIgnoreCase(viewName));
		assertTrue("Only one test location should be returned", 1 == ((MapsFormSearchResultContainer) getUiModel().asMap().get("container")).getResults().size());
	}

	public static MockServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(MockServletContext servletContext) {
		MapsControllerTest.servletContext = servletContext;
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

	public MapsController getMapsController() {
		return mapsController;
	}

	public void setMapsController(MapsController mapsController) {
		this.mapsController = mapsController;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		MapsControllerTest.applicationContext = applicationContext;
	}

	public MapsService getMapsService() {
		return mapsService;
	}

	public void setMapsService(MapsService mapsService) {
		this.mapsService = mapsService;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
}
