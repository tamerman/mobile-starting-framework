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

package org.kuali.mobility.library.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * A class representing an hour range which the library is open
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@Entity
@Table(name = "LIBRARY_HOUR")
public class LibraryHour {

	public static final int DAY_MONDAY = 1;
	public static final int DAY_TUESDAY = 2;
	public static final int DAY_WEDNESDAY = 3;
	public static final int DAY_THURSDAY = 4;
	public static final int DAY_FRIDAY = 5;
	public static final int DAY_SATURDAY = 6;
	public static final int DAY_SUNDAY = 7;
	public static final int DAY_PUBLIC_HOLIDAY = 8;

	/**
	 * Id of the Library
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private long id;

	/**
	 * Day of week this hour applies to
	 * 1 - 7 = Monday to Sunday
	 * 8 = Public holiday
	 */
	@Column(name = "DAY_OF_WEEK")
	private int dayOfWeek;

	/**
	 * Starting time
	 */
	@Column(name = "FROM_TIME")
	private Date fromTime;

	/**
	 * Ending time
	 */
	@Column(name = "TO_TIME")
	private Date toTime;

	/**
	 * Hour set this hour is placed in
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "HOUR_SET_ID", nullable = false, updatable = false)
	private LibraryHourSet libraryHourSet;

	/**
	 * Label to display on the hours page
	 * This field is not persisted
	 */
	@Transient
	private transient String displayLabel;


	/**
	 * Gets the id for this <code>LibraryHour</code>.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>LibraryHour</code>.
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the dayOfWeek for this <code>LibraryHour</code>.
	 *
	 * @return the dayOfWeek
	 */
	public int getDayOfWeek() {
		return dayOfWeek;
	}

	/**
	 * Sets the dayOfWeek for this <code>LibraryHour</code>.
	 *
	 * @param dayOfWeek the dayOfWeek to set
	 */
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/**
	 * Gets the fromTime for this <code>LibraryHour</code>.
	 *
	 * @return the fromTime
	 */
	public Date getFromTime() {
		return fromTime;
	}

	/**
	 * Sets the fromTime for this <code>LibraryHour</code>.
	 *
	 * @param fromTime the fromTime to set
	 */
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}

	/**
	 * Gets the toTime for this <code>LibraryHour</code>.
	 *
	 * @return the toTime
	 */
	public Date getToTime() {
		return toTime;
	}

	/**
	 * Sets the toTime for this <code>LibraryHour</code>.
	 *
	 * @param toTime the toTime to set
	 */
	public void setToTime(Date toTime) {
		this.toTime = toTime;
	}

	/**
	 * Gets the libraryHourSet for this <code>LibraryHour</code>.
	 *
	 * @return the libraryHourSet
	 */
	public LibraryHourSet getLibraryHourSet() {
		return libraryHourSet;
	}

	/**
	 * Sets the libraryHourSet for this <code>LibraryHour</code>.
	 *
	 * @param libraryHourSet the libraryHourSet to set
	 */
	public void setLibraryHourSet(LibraryHourSet libraryHourSet) {
		this.libraryHourSet = libraryHourSet;
	}

	/**
	 * Gets the displayLabel for this <code>LibraryHour</code>.
	 *
	 * @return the displayLabel
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * Sets the displayLabel for this <code>LibraryHour</code>.
	 *
	 * @param displayLabel the displayLabel to set
	 */
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}

	/**
	 * Returns true if the library is closed for this hour range
	 *
	 * @return True if the library is closed.
	 */
	public boolean isClosed() {
		return this.toTime == null || this.fromTime == null;
	}


}
