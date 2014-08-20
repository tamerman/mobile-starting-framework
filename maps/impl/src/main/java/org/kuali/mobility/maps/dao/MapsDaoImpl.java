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

package org.kuali.mobility.maps.dao;

/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

import de.micromata.opengis.kml.v_2_2_0.*;
import de.micromata.opengis.kml.v_2_2_0.xal.AddressLine;
import de.micromata.opengis.kml.v_2_2_0.xal.AddressLines;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.kuali.mobility.maps.util.MapsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.*;

/**
 * 
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class MapsDaoImpl implements MapsDao {
	private static final Logger LOG = LoggerFactory.getLogger(MapsDaoImpl.class);

	private String kmlUrl;
	private String arcGisUrl;
	private String baseUrl;

	private Map<String, MapsGroup> mapGroups;
	private Map<String, Location> locationMap;

	private boolean dataLoaded = false;

	public MapsDaoImpl() {
		mapGroups = new HashMap<String, MapsGroup>();
		locationMap = new HashMap<String, Location>();
	}

	public List<Location> search(Map<String, String> criteria) {
		List<Location> locations = new ArrayList<Location>();
		String searchGroupId = null;
		String searchText = null;
		LOG.debug("Entering MapsDao search()");
		if (!dataLoaded) {
			loadKml();
		}
		if (criteria != null) {
			LOG.debug("criteria is not null in search()");
			searchGroupId = criteria.get(MapsConstants.GROUP_ID);
			searchText = criteria.get(MapsConstants.SEARCH_TEXT);
			try {
				if (null == searchGroupId
						|| MapsConstants.ALL_GROUPS
								.equalsIgnoreCase(searchGroupId)) {
					LOG.debug("Searching all maps for a match to ["
							+ searchText + "]");
					Collection<MapsGroup> groups = getMapsGroups().values();
					for (MapsGroup group : groups) {
						locations.addAll(searchInMapGroups(group, searchText));
					}
				} else {
					LOG.debug("Constraining search to group matching ["
							+ searchGroupId + "]");
					MapsGroup group = getMapGroupById(searchGroupId);
					if (group != null) {
						LOG.debug("Group found, searching for matches to ["
								+ searchText + "]");
						locations.addAll(searchInMapGroups(group, searchText));
					} else {
						LOG.debug("Group not found, aborting search.");
					}
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}

		return locations;
	}

	private List<Location> searchInMapGroups(MapsGroup group, String searchText) {
		List<Location> locations = new ArrayList<Location>();
		Set<Location> locationSet = group.getMapsLocations();
		for (Location location : locationSet) {
			try {
				if (location.getLatitude().doubleValue() == 0.0
						&& location.getLongitude().doubleValue() == 0.0) {
					LOG.error("Location " + location.getId()
							+ " has no lat/lon and should.");
				} else if (location.getId().equalsIgnoreCase(searchText)
						|| location.getName().toLowerCase()
								.indexOf(searchText.toLowerCase()) > -1
						|| location.getDescription().toLowerCase()
								.indexOf(searchText.toLowerCase()) > -1) {
					LOG.debug("Found a match for group id [" + group.getId()
							+ "] location id [" + location.getId() + "]");
					locations.add(location);
				}
			} catch (Exception e) {
				LOG.error(e.getLocalizedMessage(), e);
			}
		}
		return locations;
	}

	public MapsGroup getMapGroupById(String id) {
		if (!dataLoaded)
			loadKml();
		return getMapsGroups().get(id);
	}

	public MapsGroup getMapGroupByName(String name) {
		MapsGroup group = null;
		if (name != null && getMapsGroups() != null
				&& !getMapsGroups().isEmpty()) {
			for (MapsGroup g : getMapsGroups().values()) {
				if (name.equalsIgnoreCase(g.getName())) {
					group = g;
					break;
				}
			}
		}
		return group;
	}

	public List<MapsGroup> getMapGroups() {
		List<MapsGroup> groups = new ArrayList<MapsGroup>();
		groups.addAll(getMapsGroups().values());
		return groups;
	}

	public Location getLocationById(String id) {
		if (!dataLoaded)
			loadKml();
		return getLocationMap().get(id);
	}

	public List<Location> getLocationByName(String name) {
		List<Location> locations = new ArrayList<Location>();
		if (null != name && null != getLocations()) {
			for (Location l : getLocations()) {
				if (name.equalsIgnoreCase(l.getName())) {
					locations.add(l);
				}
			}
		}
		return locations;
	}

	public List<Location> getLocations() {
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(getLocationMap().values());
		return locations;
	}

	private void loadKml() {
		try {
			//When test case is executed it will grab kml url as file://...so not updating it when it is executed from test cases
			if (!kmlUrl.startsWith("file:")) {
				if (!kmlUrl.startsWith("http")) {
					kmlUrl = baseUrl + kmlUrl;
				}
			}
			URL url = new URL(kmlUrl);
			final Kml kml = Kml.unmarshal(url.openStream());
			final Document document = (Document) kml.getFeature();
			List<Feature> features = document.getFeature();
			for (Feature feature : features) {
				if (feature instanceof Folder) {
					parseFolder((Folder) feature);
				}
			}
			dataLoaded = true;
		} catch (Exception e) {
			LOG.error("Error reading maps kml data", e);
		}
	}

	private MapsGroup parseFolder(Folder folder) {
		if (folder.getId() != null) {
			MapsGroup mapsGroup = new MapsGroup();
			mapsGroup.setId(folder.getId());
			mapsGroup.setActive(true);
			mapsGroup.setName(folder.getName());
			List<Feature> features = folder.getFeature();
			for (Feature feature : features) {
				if (feature instanceof Folder) {
					mapsGroup.getMapsGroupChildren().add(
							parseFolder((Folder) feature));
				} else if (feature instanceof Placemark) {
					mapsGroup.getMapsLocations().add(
							parseLocation((Placemark) feature));
				}
			}
			getMapsGroups().put(mapsGroup.getId(), mapsGroup);
			return mapsGroup;
		}
		return null;
	}

	private Location parseLocation(Placemark placemark) {
		if (placemark.getId() != null) {
			Location location = new Location();
			location.setActive(true);
			location.setId(placemark.getId());
			location.setName(placemark.getName());
			location.setDescription(placemark.getDescription());
			if (placemark.getGeometry() instanceof Point) {
				Point point = (Point) placemark.getGeometry();
				Coordinate coordinates = point.getCoordinates().get(0);
				location.setLatitude(coordinates.getLatitude());
				location.setLongitude(coordinates.getLongitude());
			}
			if (placemark.getXalAddressDetails() != null) {
				AddressLines addressLines = placemark.getXalAddressDetails()
						.getAddressLines();
				for (AddressLine line : addressLines.getAddressLine()) {
					if (line.getUnderscore().equals("Street")) {
						location.setStreet(line.getContent());
					} else if (line.getUnderscore().equals("City")) {
						location.setCity(line.getContent());
					} else if (line.getUnderscore().equals("State")) {
						location.setState(line.getContent());
					} else if (line.getUnderscore().equals("Post Code")) {
						location.setZip(line.getContent());
					}
				}
			}
			getLocationMap().put(location.getId(), location);
			return location;
		}
		return null;
	}

	public String getKmlUrl() {
		return kmlUrl;
	}

	public void setKmlUrl(String kmlUrl) {
		this.kmlUrl = kmlUrl;
	}

	public String getArcGisUrl() {
		return arcGisUrl;
	}

	public void setArcGisUrl(String arcGisUrl) {
		this.arcGisUrl = arcGisUrl;
	}

	public Map<String, MapsGroup> getMapsGroups() {
		return mapGroups;
	}

	public void setMapsGroups(Map<String, MapsGroup> mapsGroups) {
		this.mapGroups = mapsGroups;
	}

	public boolean isDataLoaded() {
		return dataLoaded;
	}

	public void setDataLoaded(boolean dataLoaded) {
		this.dataLoaded = dataLoaded;
	}

	public Map<String, Location> getLocationMap() {
		return locationMap;
	}

	public void setLocationMap(Map<String, Location> locationMap) {
		this.locationMap = locationMap;
	}

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl
	 *            the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
}
