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

package org.kuali.mobility.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A class representing an hour period
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@Entity
@Table(name="LIBRARY_HOUR_PERIOD")
public class LibraryHourPeriod {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;
	
	/**
	 * Localisable label
	 */
	@Column(name="LABEL")
	private String label;
	
	/**
	 * On some UI the order in which periods are displayed is important
	 */
	@Column(name="ORDR")
	private int order;

	/**
	 * Gets the id for this <code>LibraryHourPeriod</code>.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>LibraryHourPeriod</code>.
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the label for this <code>LibraryHourPeriod</code>.
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the label for this <code>LibraryHourPeriod</code>.
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Gets the order for this <code>LibraryHourPeriod</code>.
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order for this <code>LibraryHourPeriod</code>.
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
}
