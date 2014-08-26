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

package org.kuali.mobility.dining.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.dining.dao.DiningInitBean;
import org.kuali.mobility.dining.entity.DiningHallGroup;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class DiningServiceTest {
	private static final Logger LOG = LoggerFactory.getLogger(DiningServiceTest.class);

	@Resource(name = "diningService")
	private DiningService diningService;

	@Resource(name = "diningInitBean")
	private DiningInitBean diningInitBean;

	@Before
	public void preTest() {
		getDiningInitBean().loadData();
	}

	@Test
	public void testGetDiningHallGroups() {
		List<DiningHallGroup> diningHallGroups = null;
		diningHallGroups = getDiningService().getDiningHallGroups();
		assertFalse("Dining Halls are null and should not be.", diningHallGroups == null);
		assertFalse("Dining Halls is empty and should not be.", diningHallGroups.isEmpty());
		assertTrue("Expected 2 dining halls and found otherwise.", diningHallGroups.size() == 2);
	}

	@Test
	public void testGetDiningHallGroupsByCampus() {
		List<DiningHallGroup> diningHallGroups = null;
		diningHallGroups = getDiningService().getDiningHallGroupsByCampus("main");
		assertFalse("Dining Hall Groups are null and should not be.", diningHallGroups == null);
		assertTrue("Dining Hall Group is empty and should not be.", diningHallGroups.isEmpty());
		assertFalse("Expected 2 dining hall groups and found otherwise.", diningHallGroups.size() == 2);
	}

	@Test
	public void testGetDiningHallsByBuilding() {
		List<DiningHallGroup> diningHallGroups = null;
		diningHallGroups = getDiningService().getDiningHallGroupsByBuilding("Building B");
		assertFalse("Dining Halls are null and should not be.", diningHallGroups == null);
		assertTrue("Dining Halls is empty and should not be.", diningHallGroups.isEmpty());
		assertFalse("Expected 1 dining hall and found otherwise.", diningHallGroups.size() == 1);
	}

	public DiningService getDiningService() {
		return diningService;
	}

	public void setDiningService(DiningService diningService) {
		this.diningService = diningService;
	}

	public DiningInitBean getDiningInitBean() {
		return diningInitBean;
	}

	public void setDiningInitBean(DiningInitBean diningInitBean) {
		this.diningInitBean = diningInitBean;
	}
}
