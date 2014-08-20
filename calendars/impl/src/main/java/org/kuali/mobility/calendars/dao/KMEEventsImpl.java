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

package org.kuali.mobility.calendars.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;

/**
 * KMEEventsImpl class provides method for adding a new event, displaying
 * existing events, updating and deleting event on calendar.
 *
 * @author Nurul Haque Murshed <nurul.murshed@htcindia.com>
 */
public class KMEEventsImpl implements CalendarsEventsDao, ApplicationContextAware {
    
    private static final Logger LOG = LoggerFactory.getLogger(KMEEventsImpl.class);
    private ApplicationContext applicationContext;

    /**
     * The addEvent method adds an event into the Calendar.
     *
     * @param startTime start time for calendar's event
     * @param endTime end time for calendar's event
     * @param eventTitle title for the calendar's event
     * @param eventBody brief description about calendar's event
     * @param eventDateString actual date of event
     * @param user username of the calendar's user
     */
    public void addEvent(String startTime, String endTime, String eventTitle, String eventBody, String eventDateString, String user, String timeZoneId) {
    	
    }

    /**
     * By default the getEventsData method returns list of events into the
     * Calendar for the current week.
     *
     * @param startDate start date of calendar's current week
     * @param endDate end date of calendar's current week
     */
    public List getEvents(String startDate, String endDate) {
        List eventList = new ArrayList();

        return eventList;
    }

    /**
     * The updateEvent method modifies the existing event.
     *
     * @param eventId unique id for event
     * @param startTime start time for calendar's event
     * @param endTime end time for calendar's event
     * @param eventTitle title for the calendar's event
     * @param eventBody brief description about calendar's event
     * @param eventDateString actual date for event
     */
    public void updateEvent(int eventId, String startTime, String endTime, String eventTitle, String eventBody, String eventDateString,
            String user) {
    }

    /**
     * deleteEvent method removes one existing event from calendar
     *
     * @param eventId unique id of event
     */
    public void deleteEvent(int eventId) {
    }

    public void getCALDAVEvents() {
    }

    public String synchronizeWithGoogleAccount(String emailId, String password) {
        return "";
    }

    /**
     * @return the applicationContext
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
