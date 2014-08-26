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

package org.kuali.mobility.tours.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kuali.mobility.tours.service.ToursService;

@XmlRootElement(name = "tour")
@Entity(name = "Tour")
@Table(name = "TOUR_T")
public class Tour {

	@Id
	//@SequenceGenerator(name="tour_sequence", sequenceName="SEQ_TOUR_T", initialValue=1000, allocationSize=1)
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tour_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "TOUR_ID")
	private Long tourId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "PATH")
	private String path;

	@Column(name = "DIST") // in meters
	private Long distance;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "tourId")
	private List<POI> pointsOfInterest;

	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, mappedBy = "tourId")
	private Set<TourPermission> permissions;

	@Column(name = "TWT_TXT_1")
	private String tweetText1;

	@Column(name = "TWT_TXT_2")
	private String tweetText2;

	@Column(name = "FB_TXT_1")
	private String fbText1;

	@Column(name = "FB_TXT_2")
	private String fbText2;

	@Column(name = "TWT_TXT_1_EN")
	private String tweetText1Enabled;

	@Column(name = "TWT_TXT_2_EN")
	private String tweetText2Enabled;

	@Column(name = "FB_TXT_1_EN")
	private String fbText1Enabled;

	@Column(name = "FB_TXT_2_EN")
	private String fbText2Enabled;

	@Column(name = "IMG_URL")
	private String imageUrl;

	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public List<String> getViewGroups() {
		List<String> groups = new ArrayList<String>();
		for (TourPermission tp : permissions) {
			if (ToursService.PERMISSION_TYPE_VIEW.equals(tp.getType())) {
				groups.add(tp.getGroupName());
			}
		}
		return groups;
	}

	public List<String> getEditGroups() {
		List<String> groups = new ArrayList<String>();
		for (TourPermission tp : permissions) {
			if (ToursService.PERMISSION_TYPE_EDIT.equals(tp.getType())) {
				groups.add(tp.getGroupName());
			}
		}
		return groups;
	}

	public Tour copy(boolean includeIds) {
		Tour copy = new Tour();
		if (includeIds) {
			copy.setTourId(new Long(tourId));
			copy.setVersionNumber(new Long(versionNumber));
		}
		if (description != null) {
			copy.setDescription(new String(description));
		}
		if (path != null) {
			copy.setPath(new String(path));
		}
		if (name != null) {
			copy.setName(new String(name));
		}
		if (distance != null) {
			copy.setDistance(new Long(distance));
		}
		if (tweetText1 != null) {
			copy.setTweetText1(new String(tweetText1));
		}
		if (tweetText2 != null) {
			copy.setTweetText2(new String(tweetText2));
		}
		if (fbText1 != null) {
			copy.setFbText1(new String(fbText1));
		}
		if (fbText2 != null) {
			copy.setFbText2(new String(fbText2));
		}
		if (tweetText1Enabled != null) {
			copy.setTweetText1Enabled(new String(tweetText1Enabled));
		}
		if (tweetText2Enabled != null) {
			copy.setTweetText2Enabled(new String(tweetText2Enabled));
		}
		if (fbText1Enabled != null) {
			copy.setFbText1Enabled(new String(fbText1Enabled));
		}
		if (fbText2Enabled != null) {
			copy.setFbText2Enabled(new String(fbText2Enabled));
		}

		if (pointsOfInterest != null && !pointsOfInterest.isEmpty()) {
			List<POI> poiListCopy = new ArrayList<POI>();
			for (POI poi : pointsOfInterest) {
				POI poiCopy = poi.copy(includeIds);
				poiCopy.setTour(copy);
				poiListCopy.add(poiCopy);
			}
			copy.setPointsOfInterest(poiListCopy);
		} else {
			copy.setPointsOfInterest(new ArrayList<POI>());
		}

		if (permissions != null && !permissions.isEmpty()) {
			Set<TourPermission> permissionListCopy = new HashSet<TourPermission>();
			for (TourPermission permission : permissions) {
				TourPermission permissionCopy = permission.copy(includeIds);
				permissionCopy.setTour(copy);
				permissionListCopy.add(permissionCopy);
			}
			copy.setPermissions(permissionListCopy);
		} else {
			copy.setPermissions(new HashSet<TourPermission>());
		}

		return copy;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@XmlElement(name = "pointsOfInterest")
	public List<POI> getPointsOfInterest() {
		return pointsOfInterest;
	}

	public void setPointsOfInterest(List<POI> pointsOfInterest) {
		this.pointsOfInterest = pointsOfInterest;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public String getTweetText1() {
		return tweetText1;
	}

	public void setTweetText1(String tweetText1) {
		this.tweetText1 = tweetText1;
	}

	public String getTweetText2() {
		return tweetText2;
	}

	public void setTweetText2(String tweetText2) {
		this.tweetText2 = tweetText2;
	}

	public String getFbText1() {
		return fbText1;
	}

	public void setFbText1(String fbText1) {
		this.fbText1 = fbText1;
	}

	public String getFbText2() {
		return fbText2;
	}

	public void setFbText2(String fbText2) {
		this.fbText2 = fbText2;
	}

	public String getTweetText1Enabled() {
		return tweetText1Enabled;
	}

	public void setTweetText1Enabled(String tweetText1Enabled) {
		this.tweetText1Enabled = tweetText1Enabled;
	}

	public String getTweetText2Enabled() {
		return tweetText2Enabled;
	}

	public void setTweetText2Enabled(String tweetText2Enabled) {
		this.tweetText2Enabled = tweetText2Enabled;
	}

	public String getFbText1Enabled() {
		return fbText1Enabled;
	}

	public void setFbText1Enabled(String fbText1Enabled) {
		this.fbText1Enabled = fbText1Enabled;
	}

	public String getFbText2Enabled() {
		return fbText2Enabled;
	}

	public void setFbText2Enabled(String fbText2Enabled) {
		this.fbText2Enabled = fbText2Enabled;
	}

	@XmlElement(name = "permissions")
	public Set<TourPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<TourPermission> permissions) {
		this.permissions = permissions;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
