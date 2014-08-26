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

package org.kuali.mobility.calendars.service;

import org.kuali.mobility.calendars.dao.CalendarsEventsDao;
import org.kuali.mobility.calendars.entity.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@Service
public class CalendarsEventsServiceImpl implements CalendarsEventsService {

	private static final Logger LOG = LoggerFactory.getLogger(CalendarsEventsServiceImpl.class);
	private ApplicationContext applicationContext;
	private CalendarsEventsDao calendarsEventsDao;

	public void addEvent(String eventDate, String startTime, String endTime, String title, String eventBody, String user, String timeZoneId) {
		getCalendarsEventsDao().addEvent(eventDate, startTime, endTime, title, eventBody, user, timeZoneId);
	}

	public List getEvents(String start, String end) {
		return getCalendarsEventsDao().getEvents(start, end);
	}

	public void updateEvent(int eventId, String startTime, String endTime, String title, String eventBody, String eventDate, String user) {
		getCalendarsEventsDao().updateEvent(eventId, startTime, endTime, title, eventBody, eventDate, user);
	}

	public void deleteEvent(int eventId) {
		getCalendarsEventsDao().deleteEvent(eventId);
	}

	public String synchronizeWithGoogleAccount(String emailId, String password) {
		return getCalendarsEventsDao().synchronizeWithGoogleAccount(emailId, password);
	}

	@POST
	@Path("/event")
	public void event(
			@FormParam("eventDate") final String eventDate,
			@FormParam("startTime") final String startTime,
			@FormParam("endTime") final String endTime,
			@FormParam("title") final String title,
			@FormParam("eventBody") final String eventBody) {
		Event event = new Event();
	}

	@POST
	@Path("/userAccount")
	public void userAccount(
			@FormParam("emailId") final String emailId,
			@FormParam("password") final String password) {
		UserAccount userAccount = new UserAccount();
	}

	/**
	 * @return the eventsDao
	 */
	public CalendarsEventsDao getCalendarsEventsDao() {
		return calendarsEventsDao;
	}

	/**
	 * @param eventsDao the eventsDao to set
	 */
	public void setCalendarsEventsDao(CalendarsEventsDao eventsDao) {
		this.calendarsEventsDao = eventsDao;
	}
}
