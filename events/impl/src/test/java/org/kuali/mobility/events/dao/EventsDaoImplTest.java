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

package org.kuali.mobility.events.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The backing class for the Page JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:SpringBeans.xml")
public class EventsDaoImplTest {

	@Resource(name = "eventDao")
	private EventsDao dao;
	@Resource(name = "eventInitBean")
	private EventInitBean initBean;

	@Test
	public void testInitBean() {
		assertTrue("Categories is not null and should be.", getDao().getCategories() == null);

		assertTrue("Event list is not null and should be.", getDao().getEvents() == null);

		getInitBean().loadData();

		assertTrue("Failed to load categories.", getDao().getCategories() != null && getDao().getCategories().size() > 0);
		assertFalse("Failed to load events1.", getDao().getEvents() == null || getDao().getEvents().isEmpty());

		int eventCount = getDao().getEvents().size();
		getDao().initData(null, "8");

		assertTrue("Failed to load events2.", getDao().getEvents() != null && getDao().getEvents().size() > 0);
		assertTrue("Event list improperly removed duplicates.", getDao().getEvents().size() == eventCount);

	}

	public EventsDao getDao() {
		return dao;
	}

	public void setDao(EventsDao dao) {
		this.dao = dao;
	}

	public EventInitBean getInitBean() {
		return initBean;
	}

	public void setInitBean(EventInitBean initBean) {
		this.initBean = initBean;
	}
}
