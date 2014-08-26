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

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * A class representing an hourset.
 * An hour set is a grouping of hours when the library is open.
 * Hours sets are grouped by an <code>LibraryHourPeriod</code> which defined the period for the hour set.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@NamedQueries({
		@NamedQuery(
				name = "LibraryHourSet.getHourSets",
				query = "SELECT hs FROM LibraryHourSet hs WHERE libraryId = :libraryId ORDER BY hs.period.order")

})
@Entity
@Table(name = "LIBRARY_HOUR_SET")
public class LibraryHourSet {

	/**
	 * Id of this hour set
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	/**
	 * Library to which this hour set belongs
	 */
	@Column(name = "LIBRARY_ID")
	private long libraryId;

	/**
	 * Period this set is for
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "PERIOD_ID", nullable = false, updatable = false)
	@OrderBy(value = "id DESC")
	private LibraryHourPeriod period;

	/**
	 * List of hours in this set
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "libraryHourSet", fetch = FetchType.EAGER)
	@OrderBy(value = "dayOfWeek ASC")
	private List<LibraryHour> hours;

	/**
	 * Gets the id for this <code>LibraryHourSet</code>.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>LibraryHourSet</code>.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the libraryId for this <code>LibraryHourSet</code>.
	 *
	 * @return the libraryId
	 */
	public long getLibraryId() {
		return libraryId;
	}

	/**
	 * Sets the libraryId for this <code>LibraryHourSet</code>.
	 *
	 * @param libraryId the libraryId to set
	 */
	public void setLibraryId(long libraryId) {
		this.libraryId = libraryId;
	}

	/**
	 * Gets the period for this <code>LibraryHourSet</code>.
	 *
	 * @return the period
	 */
	public LibraryHourPeriod getPeriod() {
		return period;
	}

	/**
	 * Sets the period for this <code>LibraryHourSet</code>.
	 *
	 * @param period the period to set
	 */
	public void setPeriod(LibraryHourPeriod period) {
		this.period = period;
	}

	/**
	 * Gets the hours for this <code>LibraryHourSet</code>.
	 *
	 * @return the hours
	 */
	public List<LibraryHour> getHours() {
		return hours;
	}

	/**
	 * Sets the hours for this <code>LibraryHourSet</code>.
	 *
	 * @param hours the hours to set
	 */
	public void setHours(List<LibraryHour> hours) {
		this.hours = hours;
	}


}
