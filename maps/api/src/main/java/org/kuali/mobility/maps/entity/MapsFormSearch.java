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

 package org.kuali.mobility.maps.entity;

public class MapsFormSearch {

	private String searchText;
	private String searchLatitude;
	private String searchLongitude;
	private String searchBuilding;
	private String searchCampus;
	private String venueId;
	private String locationName;

	public String getSearchCampus() {
		return searchCampus;
	}

	public void setSearchCampus(String searchCampus) {
		this.searchCampus = searchCampus;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getVenueId() {
		return venueId;
	}

	public void setVenueId(String venueId) {
		this.venueId = venueId;
	}

	public String getSearchLatitude() {
		return searchLatitude;
	}

	public void setSearchLatitude(String searchLatitude) {
		this.searchLatitude = searchLatitude;
	}

	public String getSearchLongitude() {
		return searchLongitude;
	}

	public void setSearchLongitude(String searchLongitude) {
		this.searchLongitude = searchLongitude;
	}

	public String getSearchBuilding() {
		return searchBuilding;
	}

	public void setSearchBuilding(String searchBuilding) {
		this.searchBuilding = searchBuilding;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}
