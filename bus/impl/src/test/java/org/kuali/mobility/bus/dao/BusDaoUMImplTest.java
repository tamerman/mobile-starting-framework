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

package org.kuali.mobility.bus.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.bus.entity.Bus;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class BusDaoUMImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(BusDaoUMImplTest.class);

	private static ApplicationContext applicationContext;

	/**
	 * @return the applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param aApplicationContext the applicationContext to set
	 */
	public static void setApplicationContext(ApplicationContext aApplicationContext) {
		applicationContext = aApplicationContext;
	}

	public BusDaoUMImplTest() {
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		BusDaoUMImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/BusSpringBeans.xml"};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testLoadRoutes() {
		BusDaoUMImpl dao = (BusDaoUMImpl) getApplicationContext().getBean("busDao");
		dao.loadRoutes();

		assertTrue("Failed to find bus routes.", dao.getBusRoutes() != null && !dao.getBusRoutes().isEmpty());

		BusRoute route = dao.getBusRoutes().get(0);

		assertTrue("Failed to find a route in the list.", route != null);
		assertTrue("Failed to find any stops in the route.", route.getStops() != null && !route.getStops().isEmpty());

		BusStop stop = route.getStops().get(0);
		assertTrue("Failed to find a stop in the route.", stop != null);
		assertTrue("Bus stop has no scheduled stops in it and should.", stop.getScheduledStop() != null && !stop.getScheduledStop().isEmpty());

	}

	@Test
	public void testLoadBusLocations() {
		BusDaoUMImpl dao = (BusDaoUMImpl) getApplicationContext().getBean("busDao");
		dao.loadBusLocations();

		if (dao.getBuses() != null) {
			for (Bus b : dao.getBuses()) {
				LOG.debug("Bus: " + b.getId() + " : " + b.getRouteName() + " loaded.");
			}
		}

		assertTrue("Failed to find bus routes.", dao.getBuses() != null && !dao.getBuses().isEmpty());
	}
}
