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

package org.kuali.mobility.people.service;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.PersonImpl;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class CXFPeopleService {
	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFPeopleService.class);

	@Autowired
	@Qualifier("directoryService")
	private DirectoryService service;

	/**
	 * Performs a search for ui3
	 */
	@POST
	@Path("/ui3groupMembers")
	public Group ui3groupMembers(@RequestBody String groupName) {
		return getService().groupLookup(groupName);
	}

	@POST
	@Path("/ui3search")
	public SearchResultImpl ui3Search(@RequestBody String data) {
		if (data == null) {
			return null; //and sadness
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}

		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setExactness(queryParams.getString("exactness"));
		searchCriteria.setFirstName(queryParams.getString("firstName"));
		searchCriteria.setLastName(queryParams.getString("lastName"));
		searchCriteria.setLocation(queryParams.getString("location"));
		searchCriteria.setSearchText(queryParams.getString("searchText"));
		searchCriteria.setStatus(queryParams.getString("status"));
		searchCriteria.setUserName(queryParams.getString("username"));
		SearchResultImpl result = (SearchResultImpl) getService().findEntries(searchCriteria);
		if (result != null) {
			result.setSearchCriteria(searchCriteria);
		}
		return result;
	}

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
		searchCriteria = new SearchCriteria();
		searchCriteria.setExactness(exactness);
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		searchCriteria.setLocation(location);
		searchCriteria.setSearchText(searchText);
		searchCriteria.setStatus(status);
		searchCriteria.setUserName(username);
		SearchResultImpl result = (SearchResultImpl) getService().findEntries(searchCriteria);
		if (result != null) {
			result.setSearchCriteria(searchCriteria);
		}
		return result;
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
				(getService().getDirectoryDao().findEntries(searchCriteria)).getPeople(),
				getService().getPersonTransform(),
				people);

		return people;
	}

	public DirectoryService getService() {
		return service;
	}

	public void setService(DirectoryService service) {
		this.service = service;
	}
}
