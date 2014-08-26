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

package org.kuali.mobility.people.entity;

import java.util.List;

/**
 * An interface for a person
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface Person extends DirectoryEntry {

	/**
	 * Gets the Hashed user name.
	 *
	 * @return Hashed user name.
	 */
	public String getHashedUserName();

	/**
	 * Gets the firstname.
	 *
	 * @return Firstname
	 */
	public String getFirstName();

	/**
	 * Sets the firstname
	 *
	 * @param firstName Firstname.
	 */
	public void setFirstName(String firstName);

	/**
	 * Gets the Last name
	 *
	 * @return Last name
	 */
	public String getLastName();

	/**
	 * Sets the last name
	 *
	 * @param lastName Last name
	 */
	public void setLastName(String lastName);

	/**
	 * Gets the username
	 *
	 * @return
	 */
	public String getUserName();

	/**
	 * @param userName
	 */
	public void setUserName(String userName);

	/**
	 * @return
	 */
	public String getDisplayName();

	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName);

	/**
	 * @return
	 */
	public String getEmail();

	/**
	 * @param email
	 */
	public void setEmail(String email);

	/**
	 * @return
	 */
	public String getPhone();

	/**
	 * @param phone
	 */
	public void setPhone(String phone);

	/**
	 * @return
	 */
	public String getAddress();

	/**
	 * @param address
	 */
	public void setAddress(String address);

	/**
	 * @return
	 */
	public List<String> getLocations();

	/**
	 * @param locations
	 */
	public void setLocations(List<String> locations);

	/**
	 * @return
	 */
	public List<String> getAffiliations();

	/**
	 * @param affiliations
	 */
	public void setAffiliations(List<String> affiliations);

	/**
	 * @return
	 */
	public List<String> getDepartments();

	/**
	 * @param departments
	 */
	public void setDepartments(List<String> departments);

}
