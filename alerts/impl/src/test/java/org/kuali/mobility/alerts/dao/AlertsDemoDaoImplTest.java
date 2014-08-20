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

package org.kuali.mobility.alerts.dao;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.campus.service.CampusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AlertsDemoDaoImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(AlertsDemoDaoImplTest.class);

	@Resource(name = "alertsDao")
	private AlertsDemoDaoImpl dao;

	@Resource(name = "alertsInitBean")
	private AlertsDemoInitBean initBean;

	@Before
	public void preTest() {
		getInitBean().loadData();
	}

	@Test
	public void testFindAlertsByCampus() {
		List<Alert> BLAlerts = getDao().getAlertsByCampus("BL");
		assertTrue("Should have found 4 alerts and did not.", BLAlerts.size() == 4);
		List<Alert> ZZAlerts = getDao().getAlertsByCampus("ZZ");
		assertTrue("Found alerts and should not have found any.", ZZAlerts.size()==0);
		List<Alert> ALLAlerts = getDao().getAlertsByCampus("");
		assertTrue("Expected 40 alerts and found a different number", ALLAlerts.size() == 32);
	}

	public AlertsDemoDaoImpl getDao() {
		return dao;
	}

	public void setDao(AlertsDemoDaoImpl dao) {
		this.dao = dao;
	}

	public AlertsDemoInitBean getInitBean() {
		return initBean;
	}

	public void setInitBean(AlertsDemoInitBean initBean) {
		this.initBean = initBean;
	}
}
