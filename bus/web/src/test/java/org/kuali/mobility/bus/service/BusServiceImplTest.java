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

package org.kuali.mobility.bus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.kuali.mobility.bus.entity.ScheduledStop;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:BusSpringBeans.xml")
public class BusServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(BusServiceImplTest.class);

	@Resource(name = "busService")
	private BusService service;

	public BusServiceImplTest() {
	}

	@Before
	public void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testGetRoutes() {
		List<BusRoute> routes = getService().getRoutes(null);
		assertFalse("Bus routes is null and should not be.", routes == null);
		assertFalse("Bus routes is empty and should not be.", routes.isEmpty());
		assertTrue("Expected 10 routes and found only " + routes.size(), routes.size() == 10);
	}

	@Test
	public void testGetRoute() {
		BusRoute foundRoute = getService().getRoute(null, 0);
		assertTrue("Route id 0 not found", "Commuter Southbound".equalsIgnoreCase(foundRoute.getName()));
		//this will also test the routes being empty at the start
		BusRoute foundRoute2 = getService().getRoute(null, 1);
		assertTrue("Route id 1 not found", "Commuter Northbound".equalsIgnoreCase(foundRoute2.getName()));

		BusRoute foundRoute3 = getService().getRoute(null, 9999);
		assertTrue("Route id 9999 found and should not have been.", foundRoute3 == null);
	}

	@Test
	public void testGetStops() {
		List<BusStop> stops = getService().getStops(null);
		assertFalse("Stops is null and shouldn't be.", stops == null);
		assertFalse("Stops is empty and shouldn't be.", stops.isEmpty());
		assertTrue("Expected 94 stops and found only " + stops.size(), 94 == stops.size());
	}

	@Test
	public void testGetRoutesWithDistance() {
		double lat1 = 42.2693773;
		double lon1 = -83.7460591;
		double radius = 0.25; //in km or 500 meters
		List<? extends BusRoute> routes = getService().getRoutesWithDistance(lat1, lon1, radius);
		assertFalse("Routes is null and shouldn't be.", routes == null);
		assertFalse("Routes is empty and shouldn't be.", routes.isEmpty());
		assertTrue("Expected 10 routes and got " + routes.size(), 10 == routes.size());
	}

	@Test
	public void testGetNearbyStops() {
		double lat1 = 42.269239;
		double lon1 = -83.747079;
		double radius = 500.0; //in meters
		List<BusStop> stops = getService().getNearbyStops(lat1, lon1, radius);
		assertFalse("Stops is null and shouldn't be.", stops == null);
		assertFalse("Stops is empty and shouldn't be.", stops.isEmpty());
		assertTrue("Expected 6 stops and found " + stops.size(), stops.size() == 6);
	}

//	@Test
//	public void testGetStop() {
//		BusStop stop = getService().getStop(null, 1);
//		assertTrue("Stops ids do not match", busStop2.getId() == stop.getId());
//		BusStop stop2 = getService().getStop(null, 0);
//		assertTrue("Found a stop when it should not have", stop2 == null);
//	}

	@Test
	public void testGetStopByName() {
		BusStop stop = getService().getStopByName("IM Building (E)", null);
		assertFalse("Failed to find stop for IM Building (E)", stop == null);
	}

//	@Test
//	public void testBusStopDistanceUtil() {
//		BusStop stop = new BusStop();
//		stop.setLatitude("42.2693733");
//		stop.setLongitude("-83.7460491");
//		// 927605132
//		double lat1 = 42.2693773;
//		double lon1 = -83.7460591;
//		LOG.debug("Busstop 927605132");
//		//
//		if (stop != null) {
//			BusStopDistanceUtil bsu = new BusStopDistanceUtil();
//			double distance = bsu.calculateDistance(stop, lat1, lon1);
//			stop.setDistance(distance);
//			LOG.debug("BusStop ID: " + stop.getId() + " ,name :" + stop.getName() + ", calculated distance:" + distance);
//		}
//		assertTrue("Failed to calculate bus distance.", stop.getDistance() > 0);
//	}

	@Test
	public void testGetArrivalData() {
		List<ScheduledStop> nullRoutesArrivals = getService().getArrivalData("3", "1", "ALL");
		assertTrue("found scheduled stops when it should not have", nullRoutesArrivals.size() == 0);
		List<ScheduledStop> noStopsArrivals = getService().getArrivalData("0", "-1624422455", "ALL");
		assertFalse("Did not find scheduled stops and should have for route 0.", noStopsArrivals.isEmpty());
		assertTrue("Expected 24 scheduled stops and found " + noStopsArrivals.size(), noStopsArrivals.size() == 24);
	}

	public BusService getService() {
		return service;
	}

	public void setService(BusService service) {
		this.service = service;
	}
}
