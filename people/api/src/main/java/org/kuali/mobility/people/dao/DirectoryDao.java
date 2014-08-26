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


package org.kuali.mobility.people.dao;

import java.util.List;

import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResult;

/**
 * Directory Data Access Object
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface DirectoryDao {

	/**
	 * Finds entries for the <code>SearchCriteria</code> and returns
	 * the <code>SearchResult</code>
	 *
	 * @param search Criteria to search with
	 * @return Results of the search.
	 */
	public SearchResult findEntries(SearchCriteria search);

	/**
	 * Looks up a person
	 *
	 * @param personId
	 * @return
	 */
	public Person lookupPerson(String personId);

	/**
	 * Finds a group by ID.
	 *
	 * @param groupId ID of the group
	 * @return
	 */
	public List<Group> findSimpleGroup(String groupId);

	/**
	 * Looks up a group
	 *
	 * @param groupId Group ID to get
	 * @return The group
	 */
	public Group lookupGroup(String groupId);
}
