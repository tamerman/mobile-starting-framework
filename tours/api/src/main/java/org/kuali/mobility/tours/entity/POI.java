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

package org.kuali.mobility.tours.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.mobility.tours.service.ToursService;

@Entity(name="POI")
@Table(name="TOUR_POI_T")
public class POI implements Comparable<POI> {

	@Id
    //@SequenceGenerator(name="poi_sequence", sequenceName="SEQ_TOUR_POI_T", initialValue=1000, allocationSize=1)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="poi_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="POI_ID")
	private Long poiId;

	@Basic
    @Column(name="TOUR_ID", insertable=false, updatable=false)
	private Long tourId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TOUR_ID", nullable=true)
	protected Tour tour;

    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="poiId")
	private Set<POIPermission> permissions;

    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="poi")
	private Set<POIPhoneNumber> phoneNumbers = new HashSet<POIPhoneNumber>();

    @OneToMany(fetch=FetchType.EAGER, cascade={CascadeType.ALL}, mappedBy="poi")
	private Set<POIEmailAddress> emailAddresses = new HashSet<POIEmailAddress>();

	@Column(name="NAME")
	private String name;

	@Column(name="OFF_NAME")
	private String officialName;

	@Column(name="POI_TYPE")
	private String type;

	@Column(name="LOCATION_ID")
	private String locationId;

	@Column(name="LAT")
	private float latitude;

	@Column(name="LNG")
	private float longitude;

	@Column(name="MEDIA")
	private String media;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="ST_DESCRIPTION")
	private String shortDescription;

	@Column(name="ORDR")
	private Integer order;

	@Column(name="DIST_TO_NXT")
	private Long distanceToNextPoi;

	@Column(name="URL")
	private String url;

	@Column(name="IMG_URL")
	private String thumbnailUrl;

	@Column(name="FB_LIKE_URL")
	private String fbLikeUrl;

	@Column(name="FB_LIKE_EN")
	private String fbLikeButtonEnabled;

	@Version
    @Column(name="VER_NBR")
    protected Long versionNumber;

	public POI copy(boolean includeIds) {
		POI copy = new POI();
		if (includeIds) {
			copy.setPoiId(new Long(poiId));
			copy.setVersionNumber(new Long(versionNumber));
		}
		if (tourId != null) {
			copy.setTourId(new Long(tourId));
		}
		if (name != null) {
			copy.setName(new String(name));
		}
		if (officialName != null) {
			copy.setOfficialName(new String(officialName));
		}
		if (type != null) {
			copy.setType(new String(type));
		}
		if (locationId != null) {
			copy.setLocationId(new String(locationId));
		}
		copy.setLatitude(latitude);
		copy.setLongitude(longitude);
		if (media != null) {
			copy.setMedia(new String(media));
		}
		if (description != null) {
			copy.setDescription(new String(description));
		}
		if (shortDescription != null) {
			copy.setShortDescription(new String(shortDescription));
		}
		if (order != null) {
			copy.setOrder(new Integer(order));
		}
		if (distanceToNextPoi != null) {
			copy.setDistanceToNextPoi(new Long(distanceToNextPoi));
		}
		if (url != null) {
			copy.setUrl(new String(url));
		}
		if (thumbnailUrl != null) {
			copy.setThumbnailUrl(new String(thumbnailUrl));
		}
		if (fbLikeUrl != null) {
			copy.setFbLikeUrl(new String(fbLikeUrl));
		}
		if (fbLikeButtonEnabled != null) {
			copy.setFbLikeButtonEnabled(new String(fbLikeButtonEnabled));
		}
		if (permissions != null && !permissions.isEmpty()){
			Set<POIPermission> permissionListCopy = new HashSet<POIPermission>();
			for (POIPermission permission : permissions) {
				POIPermission permissionCopy = permission.copy(includeIds);
				permissionCopy.setPoi(copy);
				permissionListCopy.add(permissionCopy);
			}
			copy.setPermissions(permissionListCopy);
		} else {
			copy.setPermissions(new HashSet<POIPermission>());
		}

		if (phoneNumbers != null && !phoneNumbers.isEmpty()){
			Set<POIPhoneNumber> phoneNumberListCopy = new HashSet<POIPhoneNumber>();
			for (POIPhoneNumber phoneNumber : phoneNumbers) {
				POIPhoneNumber phoneNumberCopy = phoneNumber.copy(includeIds);
				phoneNumberCopy.setPoi(copy);
				phoneNumberListCopy.add(phoneNumberCopy);
			}
			copy.setPhoneNumbers(phoneNumberListCopy);
		} else {
			copy.setPhoneNumbers(new HashSet<POIPhoneNumber>());
		}
		return copy;
	}

	public List<String> getViewGroups() {
		List<String> groups = new ArrayList<String>();
		for (POIPermission tp : permissions) {
			if (ToursService.PERMISSION_TYPE_VIEW.equals(tp.getType())){
				groups.add(tp.getGroupName());
			}
		}
		return groups;
	}

	public List<String> getEditGroups() {
		List<String> groups = new ArrayList<String>();
		for (POIPermission tp : permissions) {
			if (ToursService.PERMISSION_TYPE_EDIT.equals(tp.getType())){
				groups.add(tp.getGroupName());
			}
		}
		return groups;
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Long getDistanceToNextPoi() {
		return distanceToNextPoi;
	}

	public void setDistanceToNextPoi(Long distanceToNextPoi) {
		this.distanceToNextPoi = distanceToNextPoi;
	}

	@Override
	public int compareTo(POI arg0) {
		if (order == null) return -1;
		if (arg0.order == null) return 1;
		return order - arg0.order;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getFbLikeUrl() {
		return fbLikeUrl;
	}

	public void setFbLikeUrl(String fbLikeUrl) {
		this.fbLikeUrl = fbLikeUrl;
	}

	public String getFbLikeButtonEnabled() {
		return fbLikeButtonEnabled;
	}

	public void setFbLikeButtonEnabled(String fbLikeButtonEnabled) {
		this.fbLikeButtonEnabled = fbLikeButtonEnabled;
	}

	@XmlElement(name = "permissions")
	public Set<POIPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<POIPermission> permissions) {
		this.permissions = permissions;
	}

	@XmlElement(name = "phoneNumbers")
	public Set<POIPhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<POIPhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	@XmlElement(name = "emailAddresses")
	public Set<POIEmailAddress> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(Set<POIEmailAddress> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public String getOfficialName() {
		return officialName;
	}

	public void setOfficialName(String officialName) {
		this.officialName = officialName;
	}
}
