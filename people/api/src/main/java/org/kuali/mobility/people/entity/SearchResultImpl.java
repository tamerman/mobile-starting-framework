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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.people.service.util.GroupTransform;
import org.kuali.mobility.people.service.util.PersonTransform;

/**
 * Implementation of a search result
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@XmlRootElement(name = "searchResults")
public class SearchResultImpl implements SearchResult {

	/**
	 * Error String
	 */
	private String error;

	/**
	 * Search criteria
	 */
	private SearchCriteria searchCriteria;

	/**
	 * List of people
	 */
	//@XmlElementWrapper
	@XmlElement(name = "people")
	private List<PersonImpl> people;

	/**
	 * List of groups
	 */
	// @XmlElementWrapper
	@XmlElement(name = "groups")
	private List<GroupImpl> groups;

	/**
	 * Creates a new instance of a <code>SearchResultImpl</code>
	 */
	public SearchResultImpl() {
		people = new ArrayList<PersonImpl>();
		groups = new ArrayList<GroupImpl>();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#getError()
	 */
	@Override
	public String getError() {
		return error;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#setError(java.lang.String)
	 */
	@Override
	public void setError(String error) {
		this.error = error;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#getPeople()
	 */
	@Override
	public List<PersonImpl> getPeople() {
		return people;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#setPeople(java.util.List)
	 */
	@Override
	public void setPeople(List<? extends Person> people) {
		CollectionUtils.collect(people, new PersonTransform(), this.people);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#getGroups()
	 */
	@Override
	public List<GroupImpl> getGroups() {
		return groups;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#setGroups(java.util.List)
	 */
	@Override
	public void setGroups(List<? extends Group> groups) {
		CollectionUtils.collect(groups, new GroupTransform(), this.groups);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#getSearchCriteria()
	 */
	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.people.entity.SearchResult#setSearchCriteria(org.kuali.mobility.people.entity.SearchCriteria)
	 */
	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
}
