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

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.people.dao.DirectoryDao;
import org.kuali.mobility.people.entity.GroupImpl;
import org.kuali.mobility.people.entity.PersonImpl;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResultImpl;
import org.kuali.mobility.people.service.util.GroupTransform;
import org.kuali.mobility.people.service.util.PersonTransform;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.jws.WebService;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the <code>DirectoryService</code>
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
@WebService(endpointInterface = "org.kuali.mobility.people.service.DirectoryService")
public class DirectoryServiceImpl implements DirectoryService, ApplicationContextAware {

	/**
	 * A reference to the <code>ApplicationContext</code>.
	 */
	private ApplicationContext applicationContext;

	/**
	 * A reference to the <code>DirectoryDao</code>.
	 */
	private DirectoryDao directoryDao;

	/**
	 * A reference to the <code>PersonTransform</code>.
	 */
	private PersonTransform personTransform;
	
	/**
	 * A reference to the <code>GroupTransform</code>.
	 */
	private GroupTransform groupTransform;

	/**
	 * Performs a search
	 */
	
	@POST
	@Path("/search")
	public SearchResultImpl search(
			@FormParam("searchText") final String searchText,
			@FormParam("firstName") final String firstName,
			@FormParam("lastName") final String lastName,
			@FormParam("username") final String username,
			@FormParam("exactness") final String exactness,
			@FormParam("status") final String status,
			@FormParam("location") final String location) {
		SearchCriteria searchCriteria = new SearchCriteria();
//		searchCriteria = new SearchCriteria();
		searchCriteria.setExactness(exactness);
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		searchCriteria.setLocation(location);
		searchCriteria.setSearchText(searchText);
		searchCriteria.setStatus(status);
		searchCriteria.setUserName(username);
		SearchResultImpl result = findEntries(searchCriteria);
		if (result != null) {
			result.setSearchCriteria(searchCriteria);
		}
		return result;
	}
	
	/**
	 * Finds entries for a search criteria
	 */
	@Override
	public SearchResultImpl findEntries(SearchCriteria searchCriteria) {
		return (SearchResultImpl) directoryDao.findEntries(searchCriteria);
	}

	/**
	 * Searches for a person
	 */
	
	@POST
	@Path("/person/search")
	public List<PersonImpl> personSearch(
			@FormParam("searchText") final String searchText,
			@FormParam("firstName") final String firstName,
			@FormParam("lastName") final String lastName,
			@FormParam("username") final String username,
			@FormParam("exactness") final String exactness,
			@FormParam("status") final String status,
			@FormParam("location") final String location) {
		List<PersonImpl> people = new ArrayList<PersonImpl>();

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria = new SearchCriteria();
		searchCriteria.setExactness(exactness);
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		searchCriteria.setLocation(location);
		searchCriteria.setSearchText(searchText);
		searchCriteria.setStatus(status);
		searchCriteria.setUserName(username);

		CollectionUtils.collect(
				(getDirectoryDao().findEntries(searchCriteria)).getPeople(),
				 getPersonTransform(),
				 people);

		return people;
	}
	 
	/**
	 * Looks up a person
	 */
	@GET
	@Path("/person/lookup")
	@Override
	public PersonImpl personLookup(@QueryParam("username") final String personId) {
		return getPersonTransform().transform(directoryDao.lookupPerson(personId));
	}

	/**
	 * Searches for groups
	 */
	@GET
	@Path("/group/search")
	@Override
	public List<GroupImpl> findSimpleGroup(@QueryParam("searchTerms") final String groupId) {
		List<GroupImpl> groups = new ArrayList<GroupImpl>();
		CollectionUtils.collect(directoryDao.findSimpleGroup(groupId), getGroupTransform(), groups);
		return groups;
	}

	/**
	 * Looks up a group
	 */
	@GET
	@Path("/group/lookup")
	@Override
	public GroupImpl groupLookup(@QueryParam("dn") final String groupId) {
		return getGroupTransform().transform(directoryDao.lookupGroup(groupId));
	}

	/**
	 * Gets the reference to the <code>DirectoryDao</code>.
	 * @return Reference to the <code>DirectoryDao</code>.
	 */
	public DirectoryDao getDirectoryDao() {
		return directoryDao;
	}

	/**
	 * Sets the reference to the <code>DirectoryDao</code>.
	 * @param directoryDao Reference to the <code>DirectoryDao</code>.
	 */
	public void setDirectoryDao(DirectoryDao directoryDao) {
		this.directoryDao = directoryDao;
	}

	/**
	 * Gets the reference to the <code>PersonTransform</code>.
	 * @return Reference to the <code>PersonTransform</code>.
	 */
	public PersonTransform getPersonTransform() {
		return personTransform;
	}

	/**
	 * Sets the reference to the <code>PersonTransform</code>.
	 * @param personTransform Reference to the <code>PersonTransform</code>.
	 */
	public void setPersonTransform(PersonTransform personTransform) {
		this.personTransform = personTransform;
	}

	/**
	 * Gets the reference to the <code>GroupTransform</code>.
	 * @return Reference to the <code>GroupTransform</code>.
	 */
	public GroupTransform getGroupTransform() {
		return groupTransform;
	}

	/**
	 * Sets the reference to the <code>GroupTransform</code>.
	 * @param groupTransform Reference to the <code>GroupTransform</code>.
	 */
	public void setGroupTransform(GroupTransform groupTransform) {
		this.groupTransform = groupTransform;
	}

	/**
	 * Gets the reference to the <code>ApplicationContext</code>.
	 * @return Reference to the <code>ApplicationContext</code>.
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Sets the reference to the <code>ApplicationContext</code>.
	 * @param applicationContext Reference to the <code>ApplicationContext</code>.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
