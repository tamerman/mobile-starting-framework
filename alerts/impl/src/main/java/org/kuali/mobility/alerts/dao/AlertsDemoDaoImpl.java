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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created with IntelliJ IDEA.
 * User: joseswan
 * Date: 5/27/14
 * Time: 4:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AlertsDemoDaoImpl implements AlertsDao {
	private static final Logger LOG = LoggerFactory.getLogger(AlertsDemoDaoImpl.class);

	private ConcurrentMap<String, List<Alert>> cachedAlerts;

	public AlertsDemoDaoImpl() {
		setCachedAlerts( new ConcurrentHashMap<String, List<Alert>>() );
	}

	public List<Alert> getAlerts() {
		List<Alert> alerts = new ArrayList<Alert>();

		for( String key : getCachedAlerts().keySet() ) {
			alerts.addAll(getCachedAlerts().get(key));
		}

		return alerts;
	}

	public List<Alert> getAlertsByCampus(final String campusCode) {
		List<Alert> alerts = new ArrayList<Alert>();
		if( getCachedAlerts().containsKey(campusCode) ) {
			alerts = getCachedAlerts().get(campusCode);
		} else if( "".equalsIgnoreCase(campusCode) || "ALL".equalsIgnoreCase(campusCode) ) {
			for( String key : getCachedAlerts().keySet() ) {
				alerts.addAll(getCachedAlerts().get(key));
			}
		}
		return alerts;
	}

	public void setAlertsForCampus(final String campusCode, final List<Alert> alerts) {
		if(getCachedAlerts().containsKey(campusCode)) {
			getCachedAlerts().replace(campusCode,alerts);
		} else {
			getCachedAlerts().put(campusCode,alerts);
		}
	}

	public ConcurrentMap<String, List<Alert>> getCachedAlerts() {
		return cachedAlerts;
	}

	public void setCachedAlerts(ConcurrentMap<String, List<Alert>> cachedAlerts) {
		this.cachedAlerts = cachedAlerts;
	}
}
