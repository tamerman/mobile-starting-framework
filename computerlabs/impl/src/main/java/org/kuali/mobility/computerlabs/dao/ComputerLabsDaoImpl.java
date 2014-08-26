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

package org.kuali.mobility.computerlabs.dao;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.entity.LabGroupImpl;
import org.kuali.mobility.computerlabs.entity.Location;
import org.kuali.mobility.computerlabs.util.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComputerLabsDaoImpl implements ComputerLabsDao, ApplicationContextAware {
	private static final Logger LOG = LoggerFactory.getLogger(ComputerLabsDaoImpl.class);

	private ApplicationContext applicationContext;
	private List<? extends LabGroup> labGroups;

	@Override
	public Lab getLab(String labUid) {
		Lab myLab = null;
		for (LabGroup g : getLabGroups()) {
			for (Location l : g.getLocations()) {
				Collection<? extends Lab> myLabs = CollectionUtils.select(l.getLabs(), new LabPredicate(null, labUid));
				if (null != myLabs && myLabs.size() > 0) {
					myLab = (Lab) (myLabs.toArray())[0];
					break;
				}
			}
		}
		if (myLab == null) {
			LOG.error("Lab not found for UID " + labUid);
		}
		return myLab;
	}

	@Override
	public List<? extends Lab> getLabs(String locationId, String buildingCode) {
		List<? extends Lab> myLabs = new ArrayList<Lab>();
		for (LabGroup g : getLabGroups()) {
			Collection<? extends Location> myLocations = CollectionUtils.select(g.getLocations(), new LocationPredicate(locationId));
			for (Location l : myLocations) {
				myLabs.addAll(CollectionUtils.select(l.getLabs(), new LabPredicate(buildingCode, null)));
			}
		}
		return myLabs;
	}

	@Override
	public List<? extends Location> getLocations(String groupId) {
		List<? extends Location> myLocations;
		if (null == groupId) {
			myLocations = new ArrayList<Location>();
			for (LabGroup group : getLabGroups()) {
				myLocations.addAll(CollectionUtils.collect(group.getLocations(), new LocationTransform()));
			}
		} else {
			LabGroup myGroup;
			myGroup = getLabGroup(groupId);
			myLocations = myGroup.getLocations();
		}
		return myLocations;
	}

	@Override
	public List<? extends LabGroup> getLabGroups() {
		if (labGroups == null) {
			List<LabGroupImpl> myLabGroups = new ArrayList<LabGroupImpl>();
			labGroups = myLabGroups;
		}
		return labGroups;
	}

	@Override
	public LabGroup getLabGroup(String groupId) {
		LabGroup myGroup = null;
		Collection<? extends LabGroup> myGroups = CollectionUtils.select(getLabGroups(), new LabGroupPredicate(groupId));

		if (myGroups != null) {
			if (myGroups.size() > 1) {
				LOG.debug("Multiple groups found for id. This shouldn't happen.");
			} else {
				LabGroupTransform transform = new LabGroupTransform();
				for (Object obj : myGroups) {
					myGroup = transform.transform(obj);
				}
			}
		}
		return myGroup;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Override
	public void setLabGroups(List<? extends LabGroup> labGroups) {
		this.labGroups = labGroups;
	}

	public void retrieveAndSaveSpreadsheetDataAsXML(String feedURL) {
	}

}
