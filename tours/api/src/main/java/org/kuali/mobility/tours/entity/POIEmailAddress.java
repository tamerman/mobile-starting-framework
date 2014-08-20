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

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "emailAddress")
@Entity(name="POIEmailAddress")
@Table(name="TOUR_POI_EML_ADD_T")
public class POIEmailAddress {

	@Id
    //@SequenceGenerator(name="tour_poi_eml_sequence", sequenceName="SEQ_TOUR_POI_EML_ADD_T", initialValue=1000, allocationSize=1)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tour_poi_eml_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="EML_ADD_ID")
	private Long id;
	
	@Basic
    @Column(name="POI_ID", insertable=false, updatable=false)
	private Long poiId;

	@ManyToOne
	@JoinColumn(name="POI_ID", nullable=false)
	private POI poi;
    
    @Column(name="NM")
	private String name;
    
    @Column(name="ADDRESS")
	private String address;

	public POIEmailAddress copy(boolean includeIds) {
		POIEmailAddress copy = new POIEmailAddress();
		if (includeIds) {
			copy.setId(new Long(id));
		}
		copy.setPoiId(new Long(poiId));
		copy.setName(new String(name));
		copy.setAddress(new String(address));
		return copy;
	}

	public POI getPoi() {
		return poi;
	}

	public void setPoi(POI poi) {
		this.poi = poi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
