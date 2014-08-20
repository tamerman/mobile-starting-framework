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

package org.kuali.mobility.events.controllers;

import java.util.ArrayList;
import java.util.List;
import org.kuali.mobility.events.entity.Event;

public class MobileDayOfMonth {

	private int day;

	private boolean hasEvents;

	private boolean currentMonth;

	private String monthYear;

	private int dayOfWeek;

	private boolean beforeCurrentMonth;

	private List<Event> events;

	public MobileDayOfMonth(int day) {
		this.day = day;
		events = new ArrayList<Event>();
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public boolean isHasEvents() {
		return hasEvents;
	}

	public void setHasEvents(boolean hasEvents) {
		this.hasEvents = hasEvents;
	}

	public boolean isCurrentMonth() {
		return currentMonth;
	}

	public void setCurrentMonth(boolean currentMonth) {
		this.currentMonth = currentMonth;
	}

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public boolean isBeforeCurrentMonth() {
		return beforeCurrentMonth;
	}

	public void setBeforeCurrentMonth(boolean beforeCurrentMonth) {
		this.beforeCurrentMonth = beforeCurrentMonth;
	}

	public String getMonthYear() {
		return monthYear;
	}

	public void setMonthYear(String monthYear) {
		this.monthYear = monthYear;
	}
	
}
