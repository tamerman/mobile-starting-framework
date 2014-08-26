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

package org.kuali.mobility.bus.dao.helper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertTrue;


public class BusStopNameUtilTest {
	private static final Logger LOG = LoggerFactory.getLogger(BusStopNameUtilTest.class);

	private static ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BusStopNameUtilTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/BusSpringBeans.xml"};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testloadStopNames() {
		BusStopNameUtil busStopNameMapper = (BusStopNameUtil) getApplicationContext().getBean("busStopNameMapper");

		List<UMBusStopName> busStopNames = busStopNameMapper.getBusStopNames();
		assertTrue("Failed to find nearby bus stops.", busStopNames != null);
	}

	@Test
	public void testupdateBusStopName() {
		BusStopNameUtil busStopNameMapper = (BusStopNameUtil) getApplicationContext().getBean("busStopNameMapper");

		String me = busStopNameMapper.updateBusStopName("Biomedical Science Research");
		assertTrue("Failed to find nearby bus stops.", me != null);
		String me1 = busStopNameMapper.updateBusStopName("Zina Pitcher Place (at BSR)");
		assertTrue("Failed to find nearby bus stops.", me1 != null);

	}

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
}
