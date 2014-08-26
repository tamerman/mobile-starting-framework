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

package org.kuali.mobility.calendars.entity;

/**
 * @author Nurul Haque Murshed <nurul.murshed@htcindia.com>
 *         <p/>
 *         GoogleCalendarSynchronizationStatus is an enumeration of possible
 *         synchronization statuses.
 */
public enum GoogleCalendarSynchronizationStatus {

	SUCCESS("events.googlecalendarsynchronization.success"),
	MALFORMED_URL_EXCEPTION("events.googlecalendarsynchronization.malformedurl_exception"),
	IOEXCEPTION("events.googlecalendarsynchronization.ioexception"),
	AUTHENTICATION_EXCEPTION("events.googlecalendarsynchronization.authentication_exception"),
	SERVICE_EXCEPTION("events.googlecalendarsynchronization.service_exception");
	private String status;

	private GoogleCalendarSynchronizationStatus(String status) {
		this.status = status;
	}

	public String toString() {
		return status;
	}
}
