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

package org.kuali.mobility.bus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.kuali.mobility.bus.service.BusService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.mockito.Mock;
import org.springframework.context.ApplicationContext;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration( locations={"classpath:/BusSpringBeans.xml"} )
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class BusControllerTest {

    private static final Logger LOG = LoggerFactory.getLogger( BusControllerTest.class );

    private static final String USER = "fauxUser";
    private static final String REDIRECT_HOME = "redirect:/campus?toolName=bus";
    private static final String[] VIEW_CAMPUS = {"ALL","BL","CO","TEST"};
    
    private static MockServletContext servletContext;
    private BusController controller;
    @Mock
    private static ApplicationContext applicationContext;
    @Mock
    private BusService busService;
    @Mock
    private Properties busProperties;
    @Mock
    private Properties kmeProperties;
    private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    	servletContext = new MockServletContext();
    }
    
    @Before
    public void preTest() {
    	this.setController(new BusController());
    	this.getController().setApplicationContext(applicationContext);
    	this.getController().setService(this.getBusService());
    	this.getController().setBusProperties(this.getBusProperties());
        this.getController().setKmeProperties(getKmeProperties());
    	this.setRequest(new MockHttpServletRequest(servletContext));
    	this.setResponse(new MockHttpServletResponse());
    	this.setUiModel(new ExtendedModelMap());
    	this.getRequest().setSession(new MockHttpSession());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Test
    public void testIndex() {
    	when(busProperties.getProperty(any(String.class))).thenReturn("map,stops");
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	String viewName = getController().index(getRequest(), getUiModel());
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	Properties properties = new Properties();
    	properties.setProperty("maps.center.lat", "1");
    	properties.setProperty("maps.center.lon", "-1");
    	when(applicationContext.getBean(any(String.class))).thenReturn(properties);
        when(getKmeProperties().getProperty("kme.uiVersion","classic")).thenReturn("classic");
    	viewName = getController().index(getRequest(), getUiModel());
    	assertTrue("uiModel initialLatitude was not set to 1!", getUiModel().asMap().get("initialLatitude").equals("1"));
    	assertTrue("uiModel initialLongitude was not set to -1!", getUiModel().asMap().get("initialLongitude").equals("-1"));
    	assertTrue("did not return \"bus/viewBusTracking\" but instead " + viewName, "bus/index".equalsIgnoreCase(viewName));
    }

    @Test
    public void testViewStops() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	when(busProperties.getProperty(any(String.class))).thenReturn("map,stops");
		String viewName = getController().viewStops(getRequest(), getUiModel());
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	List<BusRoute> busRoutes = new ArrayList<BusRoute>();
    	BusRoute busRoute = new BusRoute();
    	busRoute.setId(0);
    	busRoute.setColor(null);
    	busRoute.setName("testRoute");
    	busRoute.setPath(null);
    	busRoute.setStops(null);
    	busRoutes.add(busRoute);
    	List<BusStop> busStops = new ArrayList<BusStop>();
    	BusStop busStop = new BusStop();
    	busStops.add(busStop);
    	doReturn(busRoutes).when(busService).getRoutes(any(String.class));
    	doReturn(busStops).when(busService).getStops(any(String.class));
    	user.setViewCampus(VIEW_CAMPUS[1]);    	
    	String viewName2 = getController().viewStops(getRequest(), getUiModel());
    	assertTrue("did not return \"bus/viewStops\" but instead " + viewName2, "bus/viewStops".equalsIgnoreCase(viewName2));
    	assertTrue("uiModel campus was not set to BL!", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    	assertTrue("uiModel stops was not set!", getUiModel().asMap().get("stops") != null);
    }
    
    @Test
    public void testViewBus() {
    	when(busProperties.getProperty(any(String.class))).thenReturn("map,stops");
    	BusRoute busRoute = new BusRoute();
    	busRoute.setName("testRoute");
    	when(busService.getRoute(any(String.class), any(Long.class))).thenReturn(busRoute);
    	String viewName = getController().viewBus(getRequest(), getUiModel(), "0", "0", VIEW_CAMPUS[1]);
    	assertTrue("did not return \"bus/viewBus\" but instead " + viewName, "bus/viewBus".equalsIgnoreCase(viewName));
    	assertTrue("uiModel busId was not set to 0", getUiModel().asMap().get("busId").equals("0"));
    	assertTrue("uiModel route was not set", getUiModel().asMap().get("route") != null);
    }
    
    @Test
    public void testViewNearByStops() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	when(busProperties.getProperty(any(String.class))).thenReturn("map,stops");
		String viewName = getController().viewNearByStops(getRequest(), getUiModel(), "47", "-30", "0.9");
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	String expectedString = "bus/viewNearByStops";
    	
    	//when(busService.getNearbyStops(any(Double.class), any(Double.class), any(Double.class))).thenReturn(null);
    	List<BusStop> busStops = new ArrayList<BusStop>();
    	BusStop busStop = new BusStop();
    	busStops.add(busStop);
    	doReturn(busStops).when(busService).getStops(any(String.class));
    	
    	String viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), null, null, null);
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), null, "-30", "0.9");
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), null, null, "0.9");
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), "47", "-30", null);
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), "47", null, null);
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	viewNameHugeIfCheck = getController().viewNearByStops(getRequest(), getUiModel(), "47", "-30", "0.9");
    	assertTrue("did not return \"" + expectedString + "\"", viewNameHugeIfCheck.equalsIgnoreCase(expectedString));
    	assertTrue("uiModel route was not set", getUiModel().asMap().get("stopsnear") != null);
    }
    
    @Test
    public void testViewNearByStops2() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	doReturn(null).when(busService).getStops(any(String.class));
    	String viewName = getController().viewNearByStops(getRequest(), getUiModel(), "47", "-30", "0.9");
    	String expectedString = "bus/viewNearByStops";
    	assertTrue("did not return \"" + expectedString + "\"", viewName.equalsIgnoreCase(expectedString));
    }
    
    @Test
    public void testViewRoute() {
    	when(busService.getRoute(any(String.class), any(Long.class))).thenReturn(null);
    	String viewName = getController().viewRoute(getRequest(), getUiModel(), "0", VIEW_CAMPUS[1]);
    	assertTrue("campus was not set", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    	assertTrue("the return string is not \"bus/viewRoute\"", "bus/viewRoute".equalsIgnoreCase(viewName));
    }
    
    @Test
    public void testViewFavoriteStops() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	String viewName = getController().viewFavoriteStops(getRequest(), getUiModel());
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	viewName = getController().viewFavoriteStops(getRequest(), getUiModel());
    	assertTrue("campus was not set", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    	assertTrue("the return string is not \"bus/favoriteStops\"", "bus/favoriteStops".equalsIgnoreCase(viewName));
    }
    
    @Test
    public void testViewStop() {
    	when(busProperties.getProperty(any(String.class))).thenReturn("map,stops");
    	List<BusRoute> busRoutes = new ArrayList<BusRoute>();
    	BusStop busStop = new BusStop();
    	doReturn(busRoutes).when(busService).getRoutes(any(String.class));
    	when(busService.getStop(any(String.class), any(Long.class))).thenReturn(busStop);
    	String viewName = getController().viewStop(getRequest(), getUiModel(), "0", "1", VIEW_CAMPUS[1]);
    	assertTrue("campus was not set", "bus/viewStop".equalsIgnoreCase(viewName));
    	assertTrue("campus was not set", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    	assertTrue("campus was not set", getUiModel().asMap().get("routes") != null);
    	assertTrue("campus was not set", getUiModel().asMap().get("routeId").equals("0"));
    	assertTrue("campus was not set", getUiModel().asMap().get("stop") != null);
    }
    
    @Test
    public void testViewRouteTracking() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	String viewName = getController().viewRouteTracking(getRequest(), getUiModel());
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	viewName = getController().viewRouteTracking(getRequest(), getUiModel());
    	assertTrue("did not return \"bus/viewRouteTracking\" but instead " + viewName, "bus/viewRouteTracking".equalsIgnoreCase(viewName));
    	assertTrue("uiModel campus was not set to BL!", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    }
    
    @Test
    public void testbusTracking() {
    	User user = new UserImpl();
    	user.setLoginName(USER);
    	this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY,user);
    	String viewName = getController().viewBusTracking(getRequest(), getUiModel(), "0", "0");
    	assertTrue("did not return \"redirect:/campus?toolName=bus\" but instead " + viewName, REDIRECT_HOME.equalsIgnoreCase(viewName));
    	user.setViewCampus(VIEW_CAMPUS[1]);
    	Properties properties = new Properties();
    	properties.setProperty("maps.center.lat", "1");
    	properties.setProperty("maps.center.lon", "-1");
    	when(applicationContext.getBean(any(String.class))).thenReturn(properties);
    	viewName = getController().viewBusTracking(getRequest(), getUiModel(), "0", "0");
    	assertTrue("did not return \"bus/viewBusTracking\" but instead " + viewName, "bus/viewBusTracking".equalsIgnoreCase(viewName));
    	assertTrue("uiModel campus was not set to BL!", getUiModel().asMap().get("campus").equals(VIEW_CAMPUS[1]));
    	assertTrue("uiModel initialLatitude was not set to 1!", getUiModel().asMap().get("initialLatitude").equals("1"));
    	assertTrue("uiModel initialLongitude was not set to -1!", getUiModel().asMap().get("initialLongitude").equals("-1"));
    	assertTrue("uiModel routeId was not set to 0!", getUiModel().asMap().get("routeId").equals("0"));
    	viewName = getController().viewBusTracking(getRequest(), getUiModel(), null, null);
    	assertTrue("uiModel routeId was not set to null!", getUiModel().asMap().get("routeId") == null);
    }
    
    @Test
    public void testBusETAs() {
    	List<BusStop> busStops = new ArrayList<BusStop>();
    	doReturn(busStops).when(busService).getStops(any(String.class));
    	String jsonString = getController().busstop(0, getUiModel());
    	assertTrue("null was not returned", jsonString == null);
    }
    
    @Test
    public void testBusETAs2() {
    	BusStop busStop1 = new BusStop();
    	busStop1.setId(1);
    	BusStop busStop2 = new BusStop();
    	busStop2.setId(0);
    	List<BusStop> busStops = new ArrayList<BusStop>();
    	busStops.add(busStop1);
    	busStops.add(busStop2);
    	doReturn(busStops).when(busService).getStops(any(String.class));
    	String jsonString = getController().busstop(0, getUiModel());
    	assertTrue("null was returned", jsonString != null);
    }

	public BusController getController() {
		return controller;
	}

	public void setController(BusController controller) {
		this.controller = controller;
	}

	public BusService getBusService() {
		return busService;
	}

	public void setBusService(BusService busService) {
		this.busService = busService;
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

	public Properties getBusProperties() {
		return busProperties;
	}

	public void setBusProperties(Properties busProperties) {
		this.busProperties = busProperties;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }

//    @Test
//    public void testViewStop() {
//        assertTrue( "Failed to find application context.", null != getApplicationContext() );
//        BusController controller = (BusController)getApplicationContext().getBean("busController");
//
//        String viewName = controller.viewStop( null, new ExtendedModelMap(), "1", "2063021881", "ALL" );
//        assertTrue( "Failed to find view.", "bus/viewStop".equalsIgnoreCase(viewName));
//    }
}
