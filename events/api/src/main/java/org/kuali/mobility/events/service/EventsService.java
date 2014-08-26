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

package org.kuali.mobility.events.service;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventImpl;

import javax.jws.WebService;
import java.util.Date;
import java.util.List;

@WebService
public interface EventsService {

	public List<? extends Event> getAllEvents(String campus, String categoryId);

	public List<? extends Category> getCategoriesByCampus(String campus);

	public Event getEvent(String campus, String categoryId, String eventId);

	public Category getCategory(String campus, String categoryId);

	public String getEventJson(String eventId);

	//Saket's contribution
	//Not used currently. Reserved for future. Might have few bugs.

	//Getting the list of all events taking place that date
	public List<? extends Event> getAllEventsByDateCurrent(String campus, String categoryId, Date dateCurrent);

	//Getting the list of the events from one date to the other date
	public List<? extends Event> getAllEventsByDateFromTo(String campus, String categoryId, Date from, Date to);

	//Getting the list of all events on a particular date
	public List<? extends Event> getAllEventsByDateSpecific(String campus, String categoryId, String specific);

	public List<EventImpl> getEventsForDateRange(String fromDate, String toDate);

	public List<EventImpl> getEventsForCurrentDate(String date);
}
