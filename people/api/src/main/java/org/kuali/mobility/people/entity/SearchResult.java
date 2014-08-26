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
 * Interface for search results
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface SearchResult {

	/**
	 * Sets the <code>SearchCriteria</code>.
	 *
	 * @param criteria The <code>SearchCriteria</code>.
	 */
	void setSearchCriteria(SearchCriteria criteria);

	/**
	 * Gets the <code>SearchCriteria</code>.
	 *
	 * @return The <code>SearchCriteria</code>.
	 */
	SearchCriteria getSearchCriteria();

	/**
	 * Gets the error Strubg
	 *
	 * @return the error
	 */
	String getError();

	/**
	 * Gets the Groups
	 *
	 * @return the groups
	 */
	List<? extends Group> getGroups();

	/**
	 * Gets the people
	 *
	 * @return the people
	 */
	List<? extends Person> getPeople();

	/**
	 * Sets the error String
	 *
	 * @param error the error to set
	 */
	void setError(String error);

	/**
	 * Sets the Groups
	 *
	 * @param groups the groups to set
	 */
	void setGroups(List<? extends Group> groups);

	/**
	 * Sets the people
	 *
	 * @param people the people to set
	 */
	void setPeople(List<? extends Person> people);

}
