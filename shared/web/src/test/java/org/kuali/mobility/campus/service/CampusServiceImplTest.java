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

package org.kuali.mobility.campus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.campus.entity.Campus;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class CampusServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(CampusServiceImplTest.class);

	private static ApplicationContext applicationContext;

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/CampusSpringBeans.xml"};
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		CampusServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testFindCampusesByTool() {
		CampusService service = (CampusService) getApplicationContext().getBean("campusService");

		assertTrue("Campus service failed to load.", service != null);

		List<Campus> campuses = service.findCampusesByTool("news");
		assertTrue("No campus found for IUPUI.", campuses != null && campuses.size() > 1);
	}

	@Test
	public void testNeedToSelectDifferentCampusForTool() {
		CampusService service = (CampusService) getApplicationContext().getBean("campusService");

		assertTrue("Campus service failed to load.", service != null);

		boolean result = service.needToSelectDifferentCampusForTool("computerlabs", "BL");
		assertTrue("Received wrong response for Tool: computerlabs, Campus: IU Bloomington", !result);

		result = service.needToSelectDifferentCampusForTool("computerlabs", "IU Kokomo");
		assertTrue("Received wrong response for Tool: computerlabs, Campus: IU Kokomo", result);

		result = service.needToSelectDifferentCampusForTool("news", "");
		assertTrue("Received a response for empty string campus value and should not have.", result);
	}

	@Test
	public void testEmptyCampusList() {
		CampusService service = (CampusService) getApplicationContext().getBean("campusService");
		List<Campus> cachedCampuses = service.getCampuses();
		service.setCampuses(new ArrayList<Campus>());
		boolean result = service.needToSelectDifferentCampusForTool("computerlabs", "IU Kokomo");
		assertTrue("Empty campus list in service should result in true response and did not.", result);

		List<Campus> foundCampuses = service.findCampusesByTool(null);
		assertTrue("Campuses found when none should have existed.", foundCampuses == null || foundCampuses.isEmpty());

		service.setCampuses(cachedCampuses);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		CampusServiceImplTest.applicationContext = applicationContext;
	}
}
