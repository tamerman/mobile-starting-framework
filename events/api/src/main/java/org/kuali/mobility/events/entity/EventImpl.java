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

package org.kuali.mobility.events.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "event")
public class EventImpl implements Serializable, Event {

	private static final long serialVersionUID = -2196031917411001051L;
	private String eventId;
	private boolean allDay;
	private String title;
	private Date startDate;
	private Date endDate;
	private String displayStartDate;
	private String displayEndDate;
	private String displayStartTime;
	private String displayEndTime;
	private String location;
	private List<String> description;
	private String link;
	private List<EventContactImpl> contact;
	private String cost;
	private List<ArrayList<String>> otherInfo;
	private CategoryImpl category;
	private String type;


	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getOtherInfo()
	 */
	@XmlElement(name = "otherInfo")
	@Override
	public List<ArrayList<String>> getOtherInfo() {
		return otherInfo;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setOtherInfo(java.util.List)
	 */
	@Override
	public void setOtherInfo(List<? extends List<String>> otherInfo) {
		this.otherInfo = (List<ArrayList<String>>) otherInfo;
	}


	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getCost()
	 */
	@Override
	public String getCost() {
		return cost;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setCost(java.lang.String)
	 */
	@Override
	public void setCost(String cost) {
		this.cost = cost;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getEndDate()
	 */
	@Override
	public Date getEndDate() {
		return endDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setEndDate(java.util.Date)
	 */
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getDisplayStartTime()
	 */
	@Override
	public String getDisplayStartTime() {
		return displayStartTime;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setDisplayStartTime(java.lang.String)
	 */
	@Override
	public void setDisplayStartTime(String displayStartTime) {
		this.displayStartTime = displayStartTime;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getDisplayEndTime()
	 */
	@Override
	public String getDisplayEndTime() {
		return displayEndTime;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setDisplayEndTime(java.lang.String)
	 */
	@Override
	public void setDisplayEndTime(String displayEndTime) {
		this.displayEndTime = displayEndTime;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getDescription()
	 */
	@XmlElement(name = "description")
	@Override
	public List<String> getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setDescription(java.util.List)
	 */
	@Override
	public void setDescription(List<String> description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getDisplayEndDate()
	 */
	@Override
	public String getDisplayEndDate() {
		return displayEndDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setDisplayEndDate(java.lang.String)
	 */
	@Override
	public void setDisplayEndDate(String displayEndDate) {
		this.displayEndDate = displayEndDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getLocation()
	 */
	@Override
	public String getLocation() {
		return location;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setLocation(java.lang.String)
	 */
	@Override
	public void setLocation(String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getLink()
	 */
	@Override
	public String getLink() {
		return link;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setLink(java.lang.String)
	 */
	@Override
	public void setLink(String link) {
		this.link = link;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getDisplayStartDate()
	 */
	@Override
	public String getDisplayStartDate() {
		return displayStartDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setDisplayStartDate(java.lang.String)
	 */
	@Override
	public void setDisplayStartDate(String displayStartDate) {
		this.displayStartDate = displayStartDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#isAllDay()
	 */
	@Override
	public boolean isAllDay() {
		return allDay;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setAllDay(boolean)
	 */
	@Override
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getEventId()
	 */
	@Override
	public String getEventId() {
		return eventId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setEventId(java.lang.String)
	 */
	@Override
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getStartDate()
	 */
	@Override
	public Date getStartDate() {
		return startDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setStartDate(java.util.Date)
	 */
	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#setCategory(org.kuali.mobility.events.entity.Category)
	 */
	@Override
	public void setCategory(Category category) {
		this.category = (CategoryImpl) category;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#getCategory()
	 */
	@Override
	public CategoryImpl getCategory() {
		return category;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		boolean isEqual = false;

		if (o != null && o instanceof Event) {
			if (getEventId() != null && getEventId().equalsIgnoreCase(((Event) o).getEventId())) {
				isEqual = true;
			}
		}

		return isEqual;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Event#hashCode()
	 */
	@Override
	public int hashCode() {
		return (41 + Integer.parseInt(getEventId().substring(5)));
	}

	@XmlElement(name = "contact")
	public List<EventContactImpl> getContact() {
		return contact;
	}

	public void setContact(List<? extends EventContact> contact) {
		this.contact = (List<EventContactImpl>) (List<?>) contact;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String eType) {
		this.type = eType;
	}
}
