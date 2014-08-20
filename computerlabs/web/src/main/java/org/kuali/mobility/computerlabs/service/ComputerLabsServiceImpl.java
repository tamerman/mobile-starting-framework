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

package org.kuali.mobility.computerlabs.service;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.computerlabs.dao.ComputerLabsDao;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.entity.LabGroupImpl;
import org.kuali.mobility.computerlabs.entity.LabImpl;
import org.kuali.mobility.computerlabs.entity.LocationImpl;
import org.kuali.mobility.computerlabs.util.LabGroupTransform;
import org.kuali.mobility.computerlabs.util.LabTransform;
import org.kuali.mobility.computerlabs.util.LocationTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

public class ComputerLabsServiceImpl implements ComputerLabsService {

	private static final Logger LOG = LoggerFactory.getLogger(ComputerLabsServiceImpl.class);
	private ComputerLabsDao dao;

	public ComputerLabsDao getDao() {
		return dao;
	}

	public void setDao(ComputerLabsDao dao) {
		this.dao = dao;
	}

	@GET
	@Path("/getLabGroups")
	@Override
	public List<LabGroupImpl> getLabGroups() {
		List<LabGroupImpl> labGroups = new ArrayList<LabGroupImpl>();
		CollectionUtils.collect( getDao().getLabGroups(), new LabGroupTransform(), labGroups);
		return labGroups;
	}

	@GET
	@Path("/getLabGroup")
	@Override
	public LabGroupImpl getLabGroup(@QueryParam(value="groupId") final String groupId) {
		LabGroup myGroup = getDao().getLabGroup(groupId);
		LabGroupTransform transform = new LabGroupTransform();
		return transform.transform(myGroup);
	}

	@GET
	@Path("/getLocations")
	@Override
	public List<LocationImpl> getLocations(@QueryParam(value="groupId") final String groupId) {
		List<LocationImpl> locations = new ArrayList<LocationImpl>();
		CollectionUtils.collect(getDao().getLocations(groupId), new LocationTransform(), locations);
		return locations;
	}

//	@GET
//	@Path("/getLocation")
//	@Override
//	public LocationImpl getLocation(@QueryParam(value = "locationId") final String locationId) {
//		LocationImpl location = null;
//		if (null != locationId) {
//			for (Location l : getDao().getLocations()) {
//				if ( locationId.equalsIgnoreCase( l.getName() ) ) {
//					LocationTransform transform = new LocationTransform();
//					location = transform.transform(l);
//				}
//			}
//		}
//		return location;
//	}

	@GET
	@Path("/getLabs")
	@Override
	public List<LabImpl> getLabs(
			@QueryParam(value = "locationId") final String locationId,
			@QueryParam(value = "buildingCode") final String buildingCode) {
		List<LabImpl> myLabs = new ArrayList<LabImpl>();
		CollectionUtils.collect(getDao().getLabs(locationId, buildingCode), new LabTransform(), myLabs);
		return myLabs;
	}

	@GET
	@Path("/getLab")
	@Override
	public LabImpl getLab(@QueryParam(value = "labUID") final String labUid) {
		LabTransform transform = new LabTransform();
		return transform.transform(getDao().getLab(labUid));
	}
	
	public void retrieveAndSaveSpreadsheetDataAsXML(String feedURL) {
        getDao().retrieveAndSaveSpreadsheetDataAsXML(feedURL);
	}
	
}
