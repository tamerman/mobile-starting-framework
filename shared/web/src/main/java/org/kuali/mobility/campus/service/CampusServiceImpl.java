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

import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.campus.entity.Campus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 1.0.0
 */
@Service
public class CampusServiceImpl implements CampusService {

	/**
	 * List of configured campuses
	 */
	private List<Campus> campuses;

	/**
	 * Map of campus code to campus object
	 */
	private Map<String, Campus> campusCodeMap = new HashMap<String, Campus>();

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.campus.service.CampusService#findCampusesByTool(java.lang.String)
	 */
	public List<Campus> findCampusesByTool(String toolName) {
		List<Campus> toolCampuses = new ArrayList<Campus>();

		for (Iterator<Campus> iterator = campuses.iterator(); iterator.hasNext(); ) {
			Campus campus = iterator.next();
			if (campus.getTools().contains(toolName)) {
				toolCampuses.add(campus);
			}
		}
		return toolCampuses;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.campus.service.CampusService#needToSelectDifferentCampusForTool(java.lang.String, java.lang.String)
	 */
	public boolean needToSelectDifferentCampusForTool(String tool, String campus) {
		List<Campus> campuses = findCampusesByTool(tool);
		boolean needDifferentCampus = true;
		if (campuses != null && !campuses.isEmpty()) {
			for (Campus foundCampus : campuses) {
				if (foundCampus.getCode().equals(campus)) {
					needDifferentCampus = false;
				}
			}
		}
		return needDifferentCampus;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.campus.service.CampusService#setCampuses(java.util.List)
	 */
	public void setCampuses(List<Campus> campuses) {
		this.campuses = campuses;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.campus.service.CampusService#getCampuses()
	 */
	@GET
	@Path("/campuses")
	public List<Campus> getCampuses() {
		return this.campuses;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.campus.service.CampusService#getCampusByCode(java.lang.String)
	 */
	@Override
	public Campus getCampusByCode(String campusCode) {

		// If empty campus code, return null
		// TODO or PUBLIC campus??
		if (StringUtils.isEmpty(campusCode)) {
			return null;
		}

		// If there is no campuses
		if (this.campuses == null || this.campuses.size() == 0) {
			return null;
		}

		// Find in a cached map first
		if (campusCodeMap.containsKey(campusCode)) {
			return campusCodeMap.get(campusCode);
		}

		// Find the entry in the list and cache in a map
		for (Campus campus : this.campuses) {
			// ! We already checked campusCode is not null
			if (campusCode.equals(campus.getCode())) {
				this.campusCodeMap.put(campusCode, campus);
				return campus;
			}
		}
		// We could not find such a campus
		return null;
	}
}
