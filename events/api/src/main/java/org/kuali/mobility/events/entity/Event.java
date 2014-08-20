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

package org.kuali.mobility.events.entity;

import java.util.Date;
import java.util.List;

public interface Event {

	public abstract List<? extends List<String>> getOtherInfo();

	public abstract void setOtherInfo(List<? extends List<String>> otherInfo);

	public abstract List<? extends EventContact> getContact();

	public abstract void setContact(List<? extends EventContact> contact);

	public abstract String getCost();

	public abstract void setCost(String cost);

	public abstract Date getEndDate();

	public abstract void setEndDate(Date endDate);

	public abstract String getDisplayStartTime();

	public abstract void setDisplayStartTime(String displayStartTime);

	public abstract String getDisplayEndTime();

	public abstract void setDisplayEndTime(String displayEndTime);

	public abstract List<String> getDescription();

	public abstract void setDescription(List<String> description);

	public abstract String getDisplayEndDate();

	public abstract void setDisplayEndDate(String displayEndDate);

	public abstract String getLocation();

	public abstract void setLocation(String location);

	public abstract String getLink();

	public abstract void setLink(String link);

	public abstract String getDisplayStartDate();

	public abstract void setDisplayStartDate(String displayStartDate);

	public abstract boolean isAllDay();

	public abstract void setAllDay(boolean allDay);

	public abstract String getEventId();

	public abstract void setEventId(String eventId);

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract Date getStartDate();

	public abstract void setStartDate(Date startDate);

	/**
	 * @param category the category to set
	 */
	public abstract void setCategory(Category category);

	/**
	 * @return the category
	 */
	public abstract Category getCategory();

	public abstract boolean equals(Object o);

	public abstract int hashCode();
        
        public abstract String getType();
        
        public abstract void setType(String eType);

}
