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

package org.kuali.mobility.people.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A class reprsenting search criteria
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
@XmlRootElement(name="searchCriteria")
public class SearchCriteria {
	
	/**
	 * Firstname to search for.
	 */
	private String firstName;
	
	/**
	 * Last name to search for.
	 */
	private String lastName;
	
	/**
	 * Username to search for
	 */
	private String userName;

	/**
	 * Exactness of the search
	 */
	private String exactness = "starts";
	
	/**
	 * Status to search for.
	 */
	private String status;
	
	/**
	 * Locations to search for
	 */
	private String location;
	
	/**
	 * Text to search for
	 */
	private String searchText;


	/**
	 * Gets the searchText for this <code>SearchCriteria</code>.
	 * @return the searchText
	 */
	public String getSearchText() {
		return searchText;
	}

	/**
	 * Sets the searchText for this <code>SearchCriteria</code>.
	 * @param searchText the searchText to set
	 */
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public boolean isExactLastName() {
		return "exact".equals(exactness);
	}
	
	/**
	 * Gets the firstName for this <code>SearchCriteria</code>.
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName for this <code>SearchCriteria</code>.
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the lastName for this <code>SearchCriteria</code>.
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName for this <code>SearchCriteria</code>.
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the userName for this <code>SearchCriteria</code>.
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the userName for this <code>SearchCriteria</code>.
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Gets the exactness for this <code>SearchCriteria</code>.
	 * @return the exactness
	 */
	public String getExactness() {
		return exactness;
	}

	/**
	 * Sets the exactness for this <code>SearchCriteria</code>.
	 * @param exactness the exactness to set
	 */
	public void setExactness(String exactness) {
		this.exactness = exactness;
	}

	/**
	 * Gets the status for this <code>SearchCriteria</code>.
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status for this <code>SearchCriteria</code>.
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the location for this <code>SearchCriteria</code>.
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the location for this <code>SearchCriteria</code>.
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
}
