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

package org.kuali.mobility.maps.service;
/**
 * Copyright 2011-2014 The Kuali Foundation Licensed under the
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
import org.kuali.mobility.maps.dao.MapsDao;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.LocationSort;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.kuali.mobility.maps.util.MapsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.net.URL;
import java.util.*;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class MapsServiceImpl implements MapsService {

	private static final Logger LOG = LoggerFactory.getLogger(MapsServiceImpl.class);

	private String kmlUrl;
	private String arcGisUrl;

	private Map<String, MapsGroup> mapsGroups;
	private Map<String, Location> locations;

	private boolean dataLoaded = false;

	@Resource(name = "mapsDao")
	private MapsDao dao;

	public MapsServiceImpl() {
		mapsGroups = new HashMap<String, MapsGroup>();
		locations = new HashMap<String, Location>();
	}

	@GET
	@Path("/search")
	@Override
	public List<Location> search(
			@QueryParam(value = "searchText") String searchText,
			@QueryParam(value = "campus") String searchGroupId) {
		Map<String, String> query = new HashMap<String, String>();
		query.put(MapsConstants.SEARCH_TEXT, searchText);
		query.put(MapsConstants.GROUP_ID, searchGroupId);

		List<Location> locations = getDao().search(query);

		Collections.sort(locations, new LocationSort());
		return locations;
	}

	@Override
	@Deprecated
	public void loadKml() {
		try {
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

	@GET
	@Path("/group/search")
	@Override
	public MapsGroup getMapsGroupById(@QueryParam(value = "id") String id) {
		return getDao().getMapGroupById(id);
	}

	@GET
	@Path("/location/search")
	@Override
	public Location getLocationById(@QueryParam(value = "id") String id) {
		return getDao().getLocationById(id);
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
					mapsGroup.getMapsGroupChildren().add(parseFolder((Folder) feature));
				} else if (feature instanceof Placemark) {
					mapsGroup.getMapsLocations().add(parseLocation((Placemark) feature));
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
				AddressLines addressLines = placemark.getXalAddressDetails().getAddressLines();
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
			getLocations().put(location.getId(), location);
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

	/**
	 * @return the mapsGroups
	 */
	public Map<String, MapsGroup> getMapsGroups() {
		return mapsGroups;
	}

	/**
	 * @param mapsGroups the mapsGroups to set
	 */
	public void setMapsGroups(Map<String, MapsGroup> mapsGroups) {
		this.mapsGroups = mapsGroups;
	}

	/**
	 * @return the locations
	 */
	public Map<String, Location> getLocations() {
		return locations;
	}

	/**
	 * @param locations the locations to set
	 */
	public void setLocations(Map<String, Location> locations) {
		this.locations = locations;
	}

	public MapsDao getDao() {
		return dao;
	}

	public void setDao(MapsDao dao) {
		this.dao = dao;
	}
}
