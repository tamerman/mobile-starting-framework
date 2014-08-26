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

package org.kuali.mobility.alerts.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.alerts.dao.AlertsDao;
import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.alerts.entity.LastAlertDate;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.ArrayList;
import java.util.List;

public class AlertsServiceImpl implements AlertsService {

	private static org.slf4j.Logger LOG = LoggerFactory.getLogger(AlertsServiceImpl.class);

	@Autowired
	private CampusService campusService;

	@Resource(name = "alertsDao")
	private AlertsDao alertsDao;

	@Override
	@GET
	@Path("/byCampus/{campusId}")
	public List<Alert> findAlertsByCampus(@PathParam(value = "campusId") String campus) {
		String selectedCampus = "ALL";
		if (StringUtils.isNotBlank(campus)) {
			selectedCampus = campus;
		}
		List<Alert> campusStatuses = getAlertsDao().getAlertsByCampus(selectedCampus);
		if (CollectionUtils.isNotEmpty(campusStatuses)) {
			List<Alert> filteredStatuses = new ArrayList<Alert>();
			for (Alert a : campusStatuses) {
				if (isAlertToReport(a) && !filteredStatuses.contains(a)) {
					filteredStatuses.add(a);
				}
			}
			campusStatuses = filteredStatuses;
		}
		if (CollectionUtils.isEmpty(campusStatuses)) {
			List<Alert> alerts = new ArrayList<Alert>();
			Alert alert = new Alert();
			alert.setCampus("ALL");
			alert.setKey(-1);
			alert.setMobileText("Status is normal.");
			alert.setPriority("1");
			alert.setTitle("Normal");
			alert.setType("normal");
			alert.setUrl("");
			alerts.add(alert);
			campusStatuses = alerts;
		}
		return campusStatuses;
	}

	@Override
	public int findAlertCountByCampus(String campus) {
		List<Alert> alertList = findAlertsByCampus(campus);
		int size = 0;
		if (!CollectionUtils.isEmpty(alertList)) {
			size = alertList.size();
		}
		return size;
	}

	@Override
	@GET
	@Path("/newest/{campusId}")
	public LastAlertDate getLastAlertDate(final String campusCode) {
		LastAlertDate lastAlertDate = new LastAlertDate();

		List<Alert> alerts = getAlertsDao().getAlertsByCampus(campusCode);
		if (alerts != null && !alerts.isEmpty()) {
			Alert lastAlert = alerts.get(0);
			for (Alert a : alerts) {
				if (a.getTimeIssued().after(lastAlert.getTimeIssued())) {
					lastAlert = a;
				}
			}
			lastAlertDate.setDate(lastAlert.getTimeIssued());
		}

		return lastAlertDate;
	}

	private List<Alert> getAlertsByCode(String campus) {
		List<Alert> alerts = new ArrayList<Alert>();
		if ("ALL".equals(campus)) {
			for (Campus toolCampus : getCampusService().findCampusesByTool("alerts")) {
				alerts.addAll(getAlerts(toolCampus.getCode()));
			}
		} else {
			alerts = getAlerts(campus);
		}
		return alerts;
	}

	private List<Alert> getAlerts(String campus) {
		return getAlertsDao().getAlerts();
	}

	private boolean isAlertToReport(Alert alert) {
		return alert.getType() != null && !alert.getType().equalsIgnoreCase("normal");
	}

	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	public CampusService getCampusService() {
		return campusService;
	}

	public AlertsDao getAlertsDao() {
		return alertsDao;
	}

	public void setAlertsDao(AlertsDao alertsDao) {
		this.alertsDao = alertsDao;
	}
}
