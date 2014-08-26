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

package org.kuali.mobility.calendars.dao;

import com.google.gdata.client.calendar.CalendarQuery;
import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.OtherContent;
import com.google.gdata.data.TextContent;
import com.google.gdata.data.calendar.CalendarEventEntry;
import com.google.gdata.data.calendar.CalendarEventFeed;
import com.google.gdata.data.extensions.When;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;
import net.fortuna.ical4j.model.TimeZone;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.kuali.mobility.calendars.entity.GoogleCalendarSynchronizationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Nurul Haque Murshed <nurul.murshed@htcindia.com>
 */
public class CalendarsEventsDaoHTCImpl implements CalendarsEventsDao, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(CalendarsEventsDaoHTCImpl.class);
	private ApplicationContext applicationContext;

	/**
	 * /**
	 * This is HTC's test implementation for storing the events in a MySQL table
	 * Since this issue is for a Proof of Concept, we have used a rudimentary DB
	 * implementation
	 * <p/>
	 * The addEvent method adds an event into the Calendar.
	 *
	 * @param startTime       start time for calendar's event
	 * @param endTime         end time for calendar's event
	 * @param eventTitle      title for the calendar's event
	 * @param eventBody       brief description about calendar's event
	 * @param eventDateString actual date of event
	 * @param user            username of the calendar's user
	 */
	public void addEvent(String startTime, String endTime, String eventTitle, String eventBody, String eventDateString, String user, String timeZoneId) {

		Connection connection = null;
		Statement statement = null;
		final String OLD_FORMAT = "EEE MMM dd yyyy HH:mm:ss z Z";
		final String NEW_FORMAT = "yyyy/MM/dd";
		int eventId = getEventId() + 1;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date eventDate = sdf.parse(eventDateString);
			sdf.applyPattern(NEW_FORMAT);
			String actualEventDate = sdf.format(eventDate);
			connection = getDatabaseConnection();
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO CALENDAREVENTS(EVENT_ID,START_TIME,END_TIME,TITLE,EVENT_BODY,EVENT_DATE,USER,TIMEZONE) "
					+ "VALUES ( '" + eventId + "', "
					+ "'" + startTime + "' , '" + endTime + "' , '" + eventTitle + "', '" + eventBody + "', '" + actualEventDate + "', '" + user + "', '" + timeZoneId + "');");


		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} catch (ParseException pe) {
			LOG.error(pe.toString());
		} finally {
			try {
				if (statement != null && connection != null) {
					statement.close();
					connection.close();
				}
			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			}
		}

	}

	public static String getESTTimeForGoogleEvents(String time) {

		String timeFirst = time.substring(0, 10);
		String timeLast = time.substring(11, 23);
		String timeMiddle = time.substring(24, 28);
		String exactTime = timeFirst + " " + timeMiddle + " " + timeLast + "+0530";

		final String OLD_FORMAT = "EEE MMM dd yyyy HH:mm:ss zZ";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date eventDate = null;
		try {
			eventDate = sdf.parse(exactTime);
		} catch (ParseException pe) {
			LOG.error(pe.toString());
		}
		DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
		formatter.setTimeZone(java.util.TimeZone.getTimeZone("EST"));

		return formatter.format(eventDate);
	}

	/**
	 * By default the getEventsData method returns list of events into the
	 * Calendar for the current week.
	 *
	 * @param startDate start date of calendar's current week
	 * @param endDate   end date of calendar's current week
	 * @return the list of events
	 */
	public List getEvents(String startDate, String endDate) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		List eventList = new ArrayList();
		int eventId;
		Date eventDate;
		String startTime;
		String endTime;
		String eventTitle;
		String eventBody;
		String eventSource;
		try {
			connection = getDatabaseConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT * FROM CALENDAREVENTS WHERE EVENT_DATE BETWEEN '" + startDate + "'"
					+ " and '" + endDate + "' AND USER='nurul' AND DELETEFLAG='N';");
			while (rs.next()) {
				JSONObject item = new JSONObject();
				eventId = rs.getInt(1);
				startTime = rs.getString(2);
				endTime = rs.getString(3);
				eventTitle = rs.getString(4);
				eventBody = rs.getString(5);
				eventDate = rs.getDate(6);
				eventSource = rs.getString("Source");

				String localStarTime = getLocalTimeFromEST(startTime);
				String localEndTime = getLocalTimeFromEST(endTime);

				String exactStartTime = localStarTime.substring(0, 24);
				String exactEndTime = localEndTime.substring(0, 24);

				item.put("id", eventId);
				item.put("start", exactStartTime);
				item.put("end", exactEndTime);
				item.put("title", eventTitle);
				item.put("eventBody", eventBody);
				item.put("source", eventSource);
				eventList.add(item);
			}
		} catch (JSONException jsonex) {
			LOG.error(jsonex.toString());
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} finally {
			try {
				if (statement != null && connection != null) {
					statement.close();
					connection.close();
				}
			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			}
		}
		return eventList;
	}

	public String getLocalTimeFromEST(String time) {
		final String OLD_FORMAT = "EEE MMM dd yyyy HH:mm:ss zZ";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		Date eventDate = null;
		try {
			eventDate = sdf.parse(time);
		} catch (ParseException pe) {
			LOG.error(pe.toString());
		}
		DateFormat formatter = new SimpleDateFormat(OLD_FORMAT);
		formatter.setTimeZone(getLocalTimeZone());
		return formatter.format(eventDate);
	}

	public java.util.TimeZone getLocalTimeZone() {
		return TimeZone.getTimeZone("Asia/Calcutta");
	}

	/**
	 * The updateEvent method modifies the existing event.
	 *
	 * @param eventId         unique id of one event
	 * @param startTime       start time for calendar's event
	 * @param endTime         end time for calendar's event
	 * @param eventTitle      title for the calendar's event
	 * @param eventBody       brief description about calendar's event
	 * @param eventDateString actual date of event
	 */
	public void updateEvent(int eventId, String startTime, String endTime, String eventTitle, String eventBody, String eventDateString,
							String user) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		final String OLD_FORMAT = "EEE MMM dd yyyy HH:mm:ss z Z";
		final String NEW_FORMAT = "yyyy/MM/dd";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
			Date eventDate = sdf.parse(eventDateString);
			sdf.applyPattern(NEW_FORMAT);
			String actualEventDate = sdf.format(eventDate);
			connection = getDatabaseConnection();
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE CALENDAREVENTS SET START_TIME='" + startTime + "',END_TIME='" + endTime + "', TITLE='" + eventTitle + "', EVENT_BODY='" + eventBody + "' WHERE EVENT_ID='" + eventId + "';");

		} catch (ParseException pe) {
			LOG.error(pe.toString());
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} finally {
			try {
				if (statement != null && connection != null) {
					statement.close();
					connection.close();
				}
			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			}
		}

	}

	/**
	 * The deleteEvent method removes one existing event from the calendar
	 *
	 * @param eventId unique id for one event
	 */
	public void deleteEvent(int eventId) {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			connection = getDatabaseConnection();
			statement = connection.createStatement();
			statement.executeUpdate("UPDATE CALENDAREVENTS SET DELETEFLAG='Y' WHERE EVENT_ID='" + eventId + "';");
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} finally {
			try {
				if (statement != null && connection != null) {
					statement.close();
					connection.close();
				}
			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			}
		}

	}

	/**
	 * The getDatabaseConnection method provides a connection between Java class
	 * and the database
	 *
	 * @return the connection
	 */
	public Connection getDatabaseConnection() {
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/test";
		String userName = "root";
		String password = "root";
		Statement statement = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} catch (ClassNotFoundException cnfex) {
			LOG.error(cnfex.toString());
		} catch (InstantiationException iex) {
			LOG.error(iex.toString());
		} catch (IllegalAccessException ilex) {
			LOG.error(ilex.toString());
		}
		return connection;
	}

	/**
	 * The synchronizeWithGoogleAccount method gets all the events (within the
	 * specified date range) from user's Google Calendar. And inserts all the
	 * events into KME calendar.
	 *
	 * @param emailId  unique emailId for Google user.
	 * @param password password for specific Google Email Account.
	 */
	public String synchronizeWithGoogleAccount(String emailId, String password) {
		String synchronizationStatus = GoogleCalendarSynchronizationStatus.SUCCESS.toString();
		CalendarEventFeed resultFeed = null;
		try {
			URL feedUrl = new URL("https://www.google.com/calendar/feeds/" + emailId + "/private/full");
			CalendarService myService = new CalendarService("userlogin");
			if (emailId != null && password != null) {
				myService.setUserCredentials(emailId, password);
				CalendarQuery query = new CalendarQuery(feedUrl);

				resultFeed = myService.query(query, CalendarEventFeed.class);
			}
		} catch (MalformedURLException malfurlex) {
			synchronizationStatus = GoogleCalendarSynchronizationStatus.MALFORMED_URL_EXCEPTION.toString();
			LOG.error(malfurlex.toString());
		} catch (IOException ioe) {
			synchronizationStatus = GoogleCalendarSynchronizationStatus.IOEXCEPTION.toString();
			LOG.error(ioe.toString());
		} catch (AuthenticationException ae) {
			synchronizationStatus = GoogleCalendarSynchronizationStatus.AUTHENTICATION_EXCEPTION.toString();
			LOG.error(ae.toString());
		} catch (ServiceException se) {
			synchronizationStatus = GoogleCalendarSynchronizationStatus.SERVICE_EXCEPTION.toString();
			LOG.error(se.toString());
		}

		List<CalendarEventEntry> entries = null;
		if (resultFeed != null) {
			LOG.debug("Result Feed: " + resultFeed);
			entries = resultFeed.getEntries();
			storeGoogleEventsIntoTable(entries);
		}
		return synchronizationStatus;
	}

	/**
	 * This is HTC's test implementation for storing the events in a MySQL table
	 * Since this issue is for a Proof of Concept, we have used a rudimentary DB
	 * implementation\
	 * <p/>
	 * storeGoogleEventsIntoTable method will insert the Google Calendar events
	 * into MySQL table.
	 */
	public void storeGoogleEventsIntoTable(List<CalendarEventEntry> entries) {
		String startTime = "";
		String endTime = "";
		String actualStartTime = "";
		String actualEndTime = "";
		String actualEventDate = "";
		Date eventDate = null;
		String eventTitle = "";
		final String OLD_FORMAT = "EEE MMM dd HH:mm:ss z yyyy";
		final String NEW_FORMAT = "yyyy/MM/dd";
		Connection connection = null;
		Connection conn = null;
		Statement statement = null;
		Statement stmt = null;
		int eventId;
		String source = "google";

		conn = getDatabaseConnection();
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("DELETE FROM CALENDAREVENTS WHERE SOURCE='google'");
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		}


		for (CalendarEventEntry entry : entries) {
			eventTitle = entry.getTitle().getPlainText();

			eventId = getEventId() + 1;

			TextContent content = (TextContent) entry.getContent();

			OtherContent otherContent = new OtherContent();

			String eventBody = content.getContent().getPlainText();

			for (When w : entry.getTimes()) {
				long start = w.getStartTime().getValue();
				long end = w.getEndTime().getValue();

				if (w.getStartTime() != null) {
					startTime = new Date(start).toString();
					actualStartTime = getESTTimeForGoogleEvents(startTime);
					LOG.debug("Start Time: " + actualStartTime);
					SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
					try {
						eventDate = sdf.parse(startTime);
					} catch (ParseException ex) {
//                        LoggerFactory.getLogger(CalendarsEventsDaoHTCImpl.class.getName()).log(Level.SEVERE, null, ex);
					}
					sdf.applyPattern(NEW_FORMAT);
					actualEventDate = sdf.format(eventDate);
				}
				if (w.getEndTime() != null) {
					endTime = new Date(end).toString();
					actualEndTime = getESTTimeForGoogleEvents(endTime);
					LOG.debug("End Time: " + actualEndTime);
				}
			}

			try {
				connection = getDatabaseConnection();
				statement = connection.createStatement();
				statement.executeUpdate("INSERT INTO CALENDAREVENTS(EVENT_ID,START_TIME,END_TIME,TITLE,EVENT_BODY,EVENT_DATE,USER,SOURCE) "
						+ "VALUES ( '" + eventId + "', "
						+ "'" + actualStartTime + "' , '" + actualEndTime + "' , '" + eventTitle + "', '" + eventBody + "', '" + actualEventDate + "', '" + "nurul" + "', '" + source + "');");

			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			} finally {
				try {
					if (statement != null && connection != null) {
						statement.close();
						connection.close();
					}
				} catch (SQLException sqlex) {
					LOG.error(sqlex.toString());
				}
			}


		}
	}

	/**
	 * getEventId method decides what will be the unique id for next event.
	 *
	 * @return the id for event
	 */
	public int getEventId() {
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		int eventId = 0;
		try {
			connection = getDatabaseConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery("SELECT MAX(EVENT_ID) FROM TEST.CALENDAREVENTS");
			if (rs.next()) {
				eventId = rs.getInt(1);
			}
			statement.close();
			connection.close();
		} catch (SQLException sqlex) {
			LOG.error(sqlex.toString());
		} finally {
			try {
				if (statement != null && connection != null) {
					statement.close();
					connection.close();
				}
			} catch (SQLException sqlex) {
				LOG.error(sqlex.toString());
			}
		}
		return eventId;
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
