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

public class DayImpl implements Serializable, Comparable<Day>, Day {

	private static final long serialVersionUID = -1626066050656327429L;

	private Date date;

	private List<EventImpl> events;

	public DayImpl() {
		events = new ArrayList<EventImpl>();
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Day#compareTo(org.kuali.mobility.events.entity.DayImpl)
	 */
	public int compareTo(Day that) {
		if (getDate() != null && that.getDate() != null) {
			if (getDate().equals(that.getDate())) {
				return 0;
			}
			return getDate().before(that.getDate()) ? -1 : 1;
		}
		return -1;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Day#getDate()
	 */
	@Override
	public Date getDate() {
		return date;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Day#setDate(java.util.Date)
	 */
	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public List<EventImpl> getEvents() {
		return events;
	}

	@Override
	public void setEvents(List<? extends Event> events) {
		this.events = (List<EventImpl>) (List<?>) events;
	}

}
