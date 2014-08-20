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

package org.kuali.mobility.alerts.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.alerts.dao.AlertsDemoInitBean;
import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.campus.service.CampusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AlertsServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(AlertsServiceImplTest.class);

	@Resource(name = "alertsService")
	private AlertsServiceImpl service;

	@Resource(name = "alertsInitBean")
	private AlertsDemoInitBean initBean;

	@Autowired
	private
	CampusService campusService;

	@Before
	public void preTest() {
		getInitBean().loadData();
	}

	@Test
	public void testFindAlertsByCampus() {
		List<Alert> alerts = getService().findAlertsByCampus("IU");
		assertFalse("Alert list is null and should not be.",alerts==null);
		assertFalse("Alert list is empty and should not be.",alerts.isEmpty());
		assertTrue("Alert list should contain 3 items for campus IU and does not ("+alerts.size()+").",alerts.size()==3);
		List<Alert> BLAlerts = getService().findAlertsByCampus("BL");
		assertTrue("Should have found 3 alerts and did not.", BLAlerts.size() == 3);
		List<Alert> ZZAlerts = getService().findAlertsByCampus("ZZ");
		assertTrue("Found campus ZZ and should have found ALL", ZZAlerts.get(0).getCampus().equalsIgnoreCase("ALL"));
		List<Alert> ALLAlerts = getService().findAlertsByCampus("");
		assertTrue("Expected 30 alerts and found a different number", ALLAlerts.size() == 30);
	}

	@Test
	public void testDoesFilterNormalAlerts() {
		List<Alert> alerts = getService().findAlertsByCampus("BL");
		boolean foundNormal = false;
		for( Alert a : alerts ) {
			if( "normal".equalsIgnoreCase(a.getType()) ) {
				foundNormal = true;
			}
		}
		assertFalse("Found an alert of type 'normal' and should not have.", foundNormal);
	}

	@Test
	public void testNoAlerts() {
		List<Alert> alerts = getService().findAlertsByCampus("ABCD");
		boolean foundNormal = false;
		for( Alert a : alerts ) {
			if( "normal".equalsIgnoreCase(a.getType()) ) {
				foundNormal = true;
			}
		}
		assertTrue("Failed to find an alert of type 'normal' and should have.", foundNormal);
	}

	@Test
	public void testFindCountByCampus() {
		int BLCount = getService().findAlertCountByCampus("BL");
		assertTrue("Did not find two BL alerts", BLCount == 3);
		int ZZCount = getService().findAlertCountByCampus("ZZ");
		assertTrue("Found a campus ZZ", ZZCount == 1);
		int ALLCount = getService().findAlertCountByCampus("");
		assertTrue("Did not find the 30 not-normal Alerts", ALLCount == 30);
	}

	public AlertsServiceImpl getService() {
		return service;
	}

	public void setService(AlertsServiceImpl service) {
		this.service = service;
	}

	public AlertsDemoInitBean getInitBean() {
		return initBean;
	}

	public void setInitBean(AlertsDemoInitBean initBean) {
		this.initBean = initBean;
	}

	public CampusService getCampusService() {
		return campusService;
	}

	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}
}
