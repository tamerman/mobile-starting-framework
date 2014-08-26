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

package org.kuali.mobility.calendars.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.kuali.mobility.calendars.service.Event;
import org.kuali.mobility.calendars.service.CalendarsEventsService;
import org.kuali.mobility.calendars.entity.GoogleCalendarSynchronizationStatus;
import org.kuali.mobility.calendars.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calendarsCalender")
public class CalendarsCalendarController {

	@Autowired
	@Qualifier("calendarEventService")
	private CalendarsEventsService calendarsEventsService;

	@RequestMapping(method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String date) throws Exception {
		return week(request, uiModel, date);
		//return month(request, uiModel, date);
	}

	@RequestMapping(value = "/week", method = RequestMethod.GET)
	public String week(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String date) {
		return "calendars/week";
	}

	@RequestMapping(value = "/month", method = RequestMethod.GET)
	public String month(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String date) {
		return "calendars/month";
	}

	@RequestMapping(value = "/calendarsAddEvent", method = RequestMethod.POST)
	public void addEvent(Model uiModel, @ModelAttribute("event") Event event, BindingResult result, HttpServletRequest request) {
		String user = getUser();
		String startTime = event.getStartTime();
		String endTime = event.getEndTime();
		String estStartTime = getESTTimeFromLocalTime(startTime);
		String estEndTime = getESTTimeFromLocalTime(endTime);

		String timeZoneId = getTimeZoneID();
		calendarsEventsService.addEvent(estStartTime, estEndTime,
				event.getTitle(), event.getEventBody(), estStartTime, user, timeZoneId);
	}

	@RequestMapping(value = "/calendarsUpdateEvent", method = RequestMethod.POST)
	public void updateEvent(Model uiModel, @ModelAttribute("event") Event event, BindingResult result, HttpServletRequest request) {
		String user = getUser();
		String startTime = event.getStartTime();
		String endTime = event.getEndTime();
		String estStartTime = getESTTimeFromLocalTime(startTime);
		String estEndTime = getESTTimeFromLocalTime(endTime);
		calendarsEventsService.updateEvent(event.getEventId(), estStartTime, estEndTime,
				event.getTitle(), event.getEventBody(), estStartTime, user);
	}

	@RequestMapping(value = "/calendarsDeleteEvent", method = RequestMethod.POST)
	public void deleteEvent(Model uiModel, @ModelAttribute("event") Event event, BindingResult result, HttpServletRequest request) {
		calendarsEventsService.deleteEvent(event.getEventId());
	}

	@RequestMapping(value = "/calendarsGetEvents", method = RequestMethod.POST)
	public String getEvents(Model uiModel, @ModelAttribute("event") Event event, BindingResult result, HttpServletRequest request) {
		String startDateStr = request.getParameter("startdate");
		String endDateStr = request.getParameter("enddate");
		List eventList = calendarsEventsService.getEvents(startDateStr, endDateStr);
		JSONArray arr = new JSONArray(eventList);
		uiModel.addAttribute("eventList", arr);
		return "calendar/getevent";
	}

	@RequestMapping(value = "/calendarsSynchronizeWithGoogleeAccount", method = RequestMethod.POST)
	public String synchronizeWithGoogleeAccount(Model uiModel, @ModelAttribute("userAccount") UserAccount userAccount,
												BindingResult result, HttpServletRequest request) {
		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext("CalendarsSpringBeans.xml");
			String synchronizationStatus = calendarsEventsService.synchronizeWithGoogleAccount(userAccount.getEmailId(), userAccount.getPassword());
			//The eventsService.synchronizeWithGoogleeAccount method returns the synchronization status.
			//To display an appropriate message (about success or failure) on the screen,
			//read the message resource bundle, using the synchronizationStatus as the key
			String synchronizationStatusMessage = ctx.getMessage(synchronizationStatus, null, " ", Locale.ENGLISH);
			uiModel.addAttribute("synchronizationStatus", synchronizationStatusMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return "redirect:/calendar";
		return "calendars/week";
	}

	public String getUser() {
		String user = "nurul";
		return user;
	}

	public String getESTTimeFromLocalTime(String time) {
		String strFirst = time.substring(0, 28);
		String strLast = time.substring(28, 33);
		String startTime = strFirst + " " + strLast;

		final String OLD_FORMAT = "EEE MMM dd yyyy HH:mm:ss z Z";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date eventDate = null;
		try {
			eventDate = sdf.parse(startTime);
		} catch (ParseException ex) {
//            LoggerFactory.getLogger(CalendarsCalendarController.class.getName()).log(Level.SEVERE, null, ex);
		}
		DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
		formatter.setTimeZone(TimeZone.getTimeZone("EST"));
		return formatter.format(eventDate);
	}

	public String getTimeZoneID() {
		DateFormat estDf = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		estDf.setTimeZone(TimeZone.getTimeZone("EST"));
		return estDf.getTimeZone().getID();
	}
}
