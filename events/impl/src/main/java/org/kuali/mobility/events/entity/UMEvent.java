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

package org.kuali.mobility.events.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("event")
public class UMEvent implements Serializable {

	private static final long serialVersionUID = -7064576922502928297L;
	private String id;
	private String title;
	private String subtitle;
	private String description;
	private String type;
	private String typeId;
	private String guid;
	private String occurenceNotes;
	private String timeBegin;
	private String timeEnd;
	private String dateBegin;
	private String dateEnd;
	private String lastModified;
	private String permanentUrl;
	private String buildingId;
	private String buildingName;
	private String room;
	private String url;
	
	private String tsBegin;
	private String tsEnd;

	@XStreamOmitField
	private List<String> tags;
	@XStreamAlias("sponsors")
	@XStreamImplicit
	private List<UMSponsor> sponsors;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getOccurenceNotes() {
		return occurenceNotes;
	}

	public void setOccurenceNotes(String occurenceNotes) {
		this.occurenceNotes = occurenceNotes;
	}

	public String getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}

	public String getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getPermanentUrl() {
		return permanentUrl;
	}

	public void setPermanentUrl(String permanentUrl) {
		this.permanentUrl = permanentUrl;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getTags() {
		return tags;
	}

	public void addTag (String aTag) {
		if (tags==null) {
			tags = new ArrayList<String>();
		}
		tags.add(aTag);
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<UMSponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<UMSponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public void addSponsor(UMSponsor aSponsor) {
		if (sponsors==null) {
			sponsors = new ArrayList<UMSponsor>();
		}
		sponsors.add(aSponsor);
	}

	@Override
	public boolean equals(Object arg0) {
		boolean isEqual = super.equals(arg0);
		if (arg0 instanceof UMEvent) {
			if (this.getId()!=null && this.getId().equals(((UMEvent) arg0).getId()) )
				isEqual = true;
		}
		return isEqual;
	}

	public String getTsBegin() {
		return tsBegin;
	}

	public void setTsBegin(String tsBegin) {
		this.tsBegin = tsBegin;
	}

	public String getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(String tsEnd) {
		this.tsEnd = tsEnd;
	}
}
