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

package org.kuali.mobility.events.util;

import org.apache.commons.collections.Predicate;
import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;

public final class EventPredicate implements Predicate {

	private String campus;
	private String categoryId;
	private String eventId;

	public EventPredicate(final String campus, final String categoryId, final String eventId) {
		this.setCampus(campus);
		this.setCategoryId(categoryId);
		this.setEventId(eventId);
	}

	@Override
	public boolean evaluate(Object obj) {
		boolean campusMatch = false;
		boolean categoryMatch = false;
		boolean eventMatch = false;

		if (obj instanceof Event) {
			if (getCampus() == null) {
				campusMatch = true;
			}
			if (getCampus() != null && getCampus().equalsIgnoreCase((((Event) obj).getCategory()).getCampus())) {
				campusMatch = true;
			}
			if (getCategoryId() == null) {
				categoryMatch = true;
			}
			if (getCategoryId() != null && getCategoryId().equalsIgnoreCase((((Event) obj).getCategory()).getCategoryId())) {
				categoryMatch = true;
			}
			if (getEventId() == null) {
				eventMatch = true;
			}
			if (getEventId() != null && getEventId().equalsIgnoreCase(((Event) obj).getEventId())) {
				eventMatch = true;
			}
		}
		return campusMatch && categoryMatch && eventMatch;
	}

	/**
	 * @return the campus
	 */
	public String getCampus() {
		return campus;
	}

	/**
	 * @param campus the campus to set
	 */
	public void setCampus(String campus) {
		this.campus = campus;
	}

	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
}
