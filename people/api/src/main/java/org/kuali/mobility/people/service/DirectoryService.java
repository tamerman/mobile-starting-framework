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

package org.kuali.mobility.people.service;

import java.util.List;

import org.kuali.mobility.people.dao.DirectoryDao;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResult;
import org.kuali.mobility.people.service.util.PersonTransform;

import javax.jws.WebService;

/**
 * Interface for a Directory Service
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
@WebService
public interface DirectoryService {

	/**
	 * Finds entries for the given <code>SearchCriteria</code>.
	 * @param search <code>SearchCriteria</code> to use for the search.
	 * @return A <code>SearchResult</code> object with the results
	 */
	public SearchResult findEntries(SearchCriteria search);

	/**
	 * Performs a search for a person
	 * @param searchText
	 * @param firstName
	 * @param lastName
	 * @param username
	 * @param exactness
	 * @param status
	 * @param location
	 * @return
	 */
	@Deprecated
	public List<? extends Person> personSearch(
			String searchText,
			String firstName,
			String lastName,
			String username,
			String exactness,
			String status,
			String location);
	
	/**
	 * Performs a lookup for a person
	 * @param personId
	 * @return The person
	 */
	public Person personLookup(String personId);

	//    public List<? extends Group> groupSearch( String searchText );
	
	/**
	 * Performs a lookup for a group.
	 * @param groupId Group ID to lookup
	 * @return The group.
	 */
	public Group groupLookup(String groupId);

	/**
	 * Finds a simple group
	 * @param groupId Group ID to find.
	 * @return List of groups.
	 */
	public List<? extends Group> findSimpleGroup(String groupId);
	
	public DirectoryDao getDirectoryDao();
	public PersonTransform getPersonTransform();
}
