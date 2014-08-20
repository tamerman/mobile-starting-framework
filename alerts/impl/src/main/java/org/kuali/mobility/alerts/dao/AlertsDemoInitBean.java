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

import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.shared.InitBean;
import org.kuali.mobility.util.mapper.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: joseswan
 * Date: 5/27/14
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlertsDemoInitBean implements InitBean {

	private static final Logger LOG = LoggerFactory.getLogger(AlertsDemoInitBean.class);

	@Resource(name = "alertsDao")
	private AlertsDemoDaoImpl alertsDao;

	private Map<String, List<String>> alertUrls;

	@Autowired
	private DataMapper dataMapper;

	@Override
	public void loadData() {
		Calendar calendar = GregorianCalendar.getInstance();
		if (getAlertUrls() != null) {
			for (String campus : getAlertUrls().keySet()) {
				List<Alert> alerts = new ArrayList<Alert>();
				for (String sourceUrl : getAlertUrls().get(campus)) {
					LOG.debug("Attempting to load alerts for "+campus+" ["+sourceUrl+"]");
					try {
						List<Alert> mappedAlerts = new ArrayList<Alert>();
						mappedAlerts = getDataMapper().mapData(alerts, sourceUrl, "alertMapping.xml");
						for( Alert a : mappedAlerts ) {
							if( campus.equalsIgnoreCase(a.getCampus()) ) {
								if( a.getTimeIssued() == null ) {
									a.setTimeIssued(calendar.getTime());
								}
								alerts.add(a);
							}
						}
					} catch (Exception e) {
						LOG.error(e.getLocalizedMessage(), e);
					}
				}
				LOG.debug("============ "+campus+" "+alerts.size()+" ============");
				getAlertsDao().setAlertsForCampus(campus, alerts);
			}
		} else {
			LOG.error("Unable to load data since no URLs were provided.");
		}
	}

	public Map<String, List<String>> getAlertUrls() {
		return alertUrls;
	}

	public void setAlertUrls(Map<String, List<String>> alertUrls) {
		this.alertUrls = alertUrls;
	}

	public AlertsDemoDaoImpl getAlertsDao() {
		return alertsDao;
	}

	public void setAlertsDao(AlertsDemoDaoImpl alertsDao) {
		this.alertsDao = alertsDao;
	}

	public DataMapper getDataMapper() {
		return dataMapper;
	}

	public void setDataMapper(DataMapper dataMapper) {
		this.dataMapper = dataMapper;
	}
}
