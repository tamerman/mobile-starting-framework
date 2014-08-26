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

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResultImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class DirectoryDaoUMImpl implements DirectoryDao,
		ApplicationContextAware {

	private static final Logger LOG = LoggerFactory
			.getLogger(DirectoryDaoUMImpl.class);
	// private static final String SEARCH_URL = "https://mcommunity.umich.edu/mcPeopleService/people/compact/search";
	private static final String SEARCH_URL = "https://mcommunity.umich.edu/mcPeopleService/people/search";
	private static final String PERSON_LOOKUP_URL = "https://mcommunity.umich.edu/mcPeopleService/people/";
	private static final String GROUP_SEARCH_URL = "https://mcommunity.umich.edu/mcGroupService/group/search";
	private static final String GROUP_LOOKUP_URL = "https://mcommunity.umich.edu/mcGroupService/groupProfile/dn/";
	private static final String GROUP_MEMBER_URL = "https://mcommunity.umich.edu/mcGroupService/groupMembers/dn/";
	private static final String DEFAULT_CHARACTER_SET = "UTF-8";
	private ApplicationContext applicationContext;

	@Override
	public SearchResultImpl findEntries(SearchCriteria search) {
		SearchResultImpl results = null;
		String searchText = search.getSearchText();
		if (searchText != null && searchText.contains("<script>")) {
			// Do not perform any search
		}
		if (searchText != null && searchText.contains(";")) {
			// Do not perform any search
		} else if (searchText != null && searchText.contains("eval")) {
			// Do not perform any search
		} else {
			results = (SearchResultImpl) getApplicationContext().getBean(
					"searchResult");

			StringBuilder queryString = new StringBuilder();

			if (search.getSearchText() != null && !search.getSearchText().trim().isEmpty()) {
				searchText = searchText.replaceAll("[^\\w\\s]", "");    //Removes all special character
				queryString.append("searchCriteria=");
				queryString.append(searchText.trim());
			} else if (search.getUserName() != null && !search.getUserName().isEmpty()) {
				queryString.append("uniqname=");
				queryString.append(search.getUserName().trim());
			} else {
				if ("starts".equalsIgnoreCase(search.getExactness())) {
					search.setExactness("starts with");
				}
				if (search.getFirstName() != null
						&& !search.getFirstName().trim().isEmpty()) {
					queryString.append("givenName=");
					queryString.append(search.getFirstName().trim());
					queryString.append("&givenNameSearchType=");
					queryString.append(search.getExactness());
					queryString.append("&");
				}
				if (search.getLastName() != null && !search.getLastName().trim().isEmpty()) {
					queryString.append("sn=");
					queryString.append(search.getLastName().trim());
					queryString.append("&snSearchType=");
					queryString.append(search.getExactness());
				}
			}

			LOG.debug("QueryString will be : " + queryString.toString());

			try {
				URLConnection connection = new URL(SEARCH_URL).openConnection();

				connection.setDoOutput(true); // Triggers POST.
				connection.setRequestProperty("Accept-Charset", DEFAULT_CHARACTER_SET);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + DEFAULT_CHARACTER_SET);
				OutputStream output = null;

				output = connection.getOutputStream();
				output.write(queryString.toString().getBytes(
						DEFAULT_CHARACTER_SET));

				InputStream response = connection.getInputStream();
				String contentType = connection.getHeaderField("Content-Type");

				if (contentType != null
						&& "application/json".equalsIgnoreCase(contentType)) {
//					LOG.debug("Attempting to parse JSON using Gson.");

					List<Person> peeps = new ArrayList<Person>();
					BufferedReader reader = null;
					try {
						reader = new BufferedReader(new InputStreamReader(
								response, DEFAULT_CHARACTER_SET));

						String jsonData = IOUtils.toString(response,
								DEFAULT_CHARACTER_SET);

						LOG.debug("Attempting to parse JSON using JSON.simple.");
						LOG.debug(jsonData);

						JSONParser parser = new JSONParser();

						Object rootObj = parser.parse(jsonData);

						JSONObject jsonObject = (JSONObject) rootObj;
						JSONArray jsonPerson = (JSONArray) jsonObject
								.get("person");
						for (Object o : jsonPerson) {
							peeps.add(parsePerson((JSONObject) o));
						}
					} catch (UnsupportedEncodingException uee) {
						LOG.error(uee.getLocalizedMessage());
					} catch (IOException ioe) {
						LOG.error(ioe.getLocalizedMessage());
					} catch (ParseException pe) {
						LOG.error(pe.getLocalizedMessage(), pe);
					} finally {
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException logOrIgnore) {
								LOG.error(logOrIgnore.getLocalizedMessage());
							}
						}
					}
					results.setPeople(peeps);
				} else {
					LOG.debug("Content type was not application/json.");
				}
			} catch (IOException ioe) {
				LOG.error(ioe.getLocalizedMessage());
			}
			LOG.debug("Searching for groups.");
			results.setGroups(searchForGroup(search));
		}
		return results;
	}

	@Override
	public Person lookupPerson(String personId) {
		Person person = null;

		BufferedReader reader = null;
		try {
			URL service = new URL(PERSON_LOOKUP_URL + personId);
			LOG.debug("Personlookupurl :" + PERSON_LOOKUP_URL + personId);

			String jsonData = null;
			jsonData = IOUtils.toString(service, DEFAULT_CHARACTER_SET);

			LOG.debug("Attempting to parse JSON using JSON.simple.");
			LOG.debug(jsonData);

			JSONParser parser = new JSONParser();

			Object rootObj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) rootObj;
			JSONObject jsonPerson = (JSONObject) jsonObject.get("person");

			if (jsonPerson == null) {
				LOG.debug("Results were not parsed, " + personId
						+ " not found.");
			} else if (jsonPerson.containsKey("errors")
					&& jsonPerson.get("errors") != null
					&& jsonPerson.get("errors").hashCode() != 0) {
				// TODO get error description
				// Object v = raw.get("errors");
				LOG.debug("errors:" + jsonPerson.get("errors"));
			} else {
				person = parsePerson(jsonPerson);
			}

		} catch (UnsupportedEncodingException uee) {
			LOG.error(uee.toString());
		} catch (IOException ioe) {
			LOG.error(ioe.toString());
		} catch (ParseException pe) {
			LOG.error(pe.getLocalizedMessage(), pe);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException logOrIgnore) {
					LOG.error(logOrIgnore.getLocalizedMessage());
				}
			}
		}
		return person;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Group lookupGroup(String dn) {
		Group group = (Group) getApplicationContext().getBean("directoryGroup");
		;

		try {
			String newdn;
			if (!dn.startsWith("cn=")) {
				newdn = "cn=" + dn
						+ ",ou=User Groups,ou=Groups,dc=umich,dc=edu";
			} else {
				newdn = dn;
			}
			newdn = newdn.replaceAll("\\s", "%20").trim();

			URL url = new URL(GROUP_LOOKUP_URL + newdn);

			LOG.debug("grouplookupurl :" + GROUP_LOOKUP_URL + newdn);

			String jsonData = null;
			jsonData = IOUtils.toString(url, DEFAULT_CHARACTER_SET);

			LOG.debug("Attempting to parse JSON using JSON.simple.");
			LOG.debug(jsonData);

			JSONParser parser = new JSONParser();

			Object rootObj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) rootObj;
			JSONObject jsonGroup = (JSONObject) jsonObject.get("group");

			if (null != jsonGroup.get("errors")
					&& !"".equalsIgnoreCase((String) jsonGroup.get("errors"))) {
				// Process the errors.
			} else {
				group.setDN((String) jsonGroup.get("distinguishedName"));
				group.setDisplayName((String) jsonGroup.get("displayName"));
				group.setEmail((String) jsonGroup.get("email") + "@umich.edu");

				if (null != jsonGroup.get("description")) {
					if ((jsonGroup.get("description")) instanceof String) {
						List<String> tDesc = new ArrayList<String>();
						tDesc.add((String) jsonGroup.get("description"));
						group.setDescriptions(tDesc);
					} else {
						group.setDescriptions((List<String>) jsonGroup
								.get("description"));
					}
				}

				List<String> desc = new ArrayList<String>();
				desc.add((String) jsonGroup.get("description"));
				group.setDescriptions(desc);

				List<Person> owners = new ArrayList<Person>();
				if (null != jsonGroup.get("groupOwners")) {
					if ((jsonGroup.get("groupOwners")) instanceof JSONArray) {
						JSONArray jsonOwners = (JSONArray) jsonGroup
								.get("groupOwners");
						for (Object owner : jsonOwners) {
							JSONObject o = (JSONObject) owner;
							Person person = parsePerson(o);
							owners.add(person);
						}
					} else {
						JSONObject o = (JSONObject) jsonGroup
								.get("groupOwners");
						Person person = parsePerson(o);
						owners.add(person);
					}
				}
				group.setOwners(owners);
			}

			url = new URL(GROUP_MEMBER_URL + newdn);
			jsonData = IOUtils.toString(url, DEFAULT_CHARACTER_SET);
			LOG.debug("Attempting to parse JSON using JSON.simple.");
			LOG.debug(jsonData);

			rootObj = parser.parse(jsonData);

			jsonObject = (JSONObject) rootObj;
			jsonGroup = (JSONObject) jsonObject.get("group");

			if (null != jsonObject.get("errors")
					&& !"".equalsIgnoreCase((String) jsonObject.get("errors"))) {
				// Process the errors.
			} else {
				List<Person> members = new ArrayList<Person>();
				if ((jsonGroup.get("members")) instanceof JSONArray) {
					JSONArray jsonMembers = (JSONArray) jsonGroup
							.get("members");
					for (Object member : jsonMembers) {
						JSONObject m = (JSONObject) member;
						Person person = parsePerson(m);
						members.add(person);
					}
				} else {
					JSONObject m = (JSONObject) jsonGroup.get("members");
					Person person = parsePerson(m);
					members.add(person);
				}
				LOG.debug("Adding " + members.size() + " members to the group.");
				group.setMembers(members);
				LOG.debug("Group's member list is " + group.getMembers().size()
						+ " in length.");

				List<Group> subGroups = new ArrayList<Group>();

				if (null != jsonGroup.get("subGroups")) {
					// && !"".equalsIgnoreCase(
					// (String)jsonGroup.get("subGroups") ) ) {
					if (jsonGroup.get("subGroups") instanceof JSONArray) {
						JSONArray jsonGroups = (JSONArray) jsonGroup
								.get("subGroups");
						for (Object tsg : jsonGroups) {
							Group subGroup = (Group) getApplicationContext()
									.getBean("directoryGroup");
							JSONObject sg = (JSONObject) tsg;
							subGroup.setDN((String) sg.get("distinguishedName"));
							if (null != sg.get("groupDescription")) {
								if ((sg.get("groupDescription")) instanceof String) {
									List<String> tDesc = new ArrayList<String>();
									tDesc.add((String) sg
											.get("groupDescription"));
									subGroup.setDescriptions(tDesc);
								} else {
									subGroup.setDescriptions((List) sg
											.get("groupDescription"));
								}
							}
							subGroup.setDisplayName((String) sg
									.get("displayName"));
							subGroup.setEmail((String) sg.get("groupEmail"));
							subGroups.add(subGroup);
						}
					} else {
						Group subGroup = (Group) getApplicationContext()
								.getBean("directoryGroup");
						JSONObject sg = (JSONObject) jsonGroup.get("subGroups");
						subGroup.setDN((String) sg.get("distinguishedName"));
						if (null != sg.get("groupDescription")) {
							if ((sg.get("groupDescription")) instanceof String) {
								List<String> tDesc = new ArrayList<String>();
								tDesc.add((String) sg.get("groupDescription"));
								subGroup.setDescriptions(tDesc);
							} else {
								subGroup.setDescriptions((List) sg
										.get("groupDescription"));
							}
						}
						subGroup.setDisplayName((String) sg.get("displayName"));
						subGroup.setEmail((String) sg.get("groupEmail"));
						subGroups.add(subGroup);
					}
				}
				group.setSubGroups(subGroups);
			}
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
		} catch (ParseException pe) {
			LOG.error(pe.getLocalizedMessage(), pe);
		}
		return group;
	}

	public List<Group> findSimpleGroup(String search) {

		List<Group> searchResults = new ArrayList<Group>();
		Group entry = null;
		StringBuilder queryString = new StringBuilder();

		if (search != null && !search.trim().isEmpty()) {
			queryString.append("searchCriteria=");
			queryString.append(search.trim());
		}

		LOG.debug("Group serach QueryString will be : "
				+ queryString.toString());
		try {
			URLConnection connection = new URL(GROUP_SEARCH_URL)
					.openConnection();

			connection.setDoOutput(true); // Triggers POST.
			connection.setRequestProperty("Accept-Charset",
					DEFAULT_CHARACTER_SET);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset="
							+ DEFAULT_CHARACTER_SET);
			OutputStream output = null;

			output = connection.getOutputStream();
			output.write(queryString.toString().getBytes(DEFAULT_CHARACTER_SET));

			String jsonData = null;
			jsonData = IOUtils.toString(connection.getInputStream(),
					DEFAULT_CHARACTER_SET);

			LOG.debug("Attempting to parse JSON using JSON.simple.");
			LOG.debug(jsonData);

			JSONParser parser = new JSONParser();

			Object rootObj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) rootObj;

			JSONArray groups = (JSONArray) jsonObject.get("group");
			for (Object group : groups) {
				JSONObject g = (JSONObject) group;
				LOG.debug(g.toString());
				entry = (Group) getApplicationContext().getBean(
						"directoryGroup");
				entry.setDN((String) g.get("distinguishedName"));
				if (null != g.get("errors")
						&& (g.get("errors")) instanceof JSONObject) {
					JSONObject error = (JSONObject) g.get("errors");
					if (null != error.get("global")) {
						JSONObject globalError = (JSONObject) error
								.get("global");
						entry.setDisplayName("Error");
						List<String> tDesc = new ArrayList<String>();
						tDesc.add((String) globalError.get("description"));
						entry.setDescriptions(tDesc);
					}
				} else {
					if (null != g.get("description")) {
						if ((g.get("description")) instanceof String) {
							List<String> tDesc = new ArrayList<String>();
							tDesc.add((String) g.get("description"));
							entry.setDescriptions(tDesc);
						} else {
							entry.setDescriptions((List) g.get("description"));
						}
					}
					entry.setDisplayName((String) g.get("displayName"));
					entry.setEmail((String) g.get("email"));
				}
				searchResults.add(entry);
			}

		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(), ioe);
		} catch (ParseException pe) {
			LOG.error(pe.getLocalizedMessage(), pe);
		}
		return searchResults;
	}

	public List<Group> searchForGroup(SearchCriteria searchCriteria) {
		List<Group> groups = new ArrayList<Group>();

		if (null != searchCriteria.getSearchText() && !searchCriteria.getSearchText().isEmpty()) {
			groups.addAll(findSimpleGroup(searchCriteria.getSearchText()));
		}
		if (null != searchCriteria.getFirstName() && !searchCriteria.getFirstName().isEmpty()) {
			groups.addAll(findSimpleGroup(searchCriteria.getFirstName()));
		}
		if (null != searchCriteria.getLastName() && !searchCriteria.getLastName().isEmpty()) {
			groups.addAll(findSimpleGroup(searchCriteria.getLastName()));
		}

		return groups;
	}

	private Person parsePerson(final JSONObject jsonData) {
		Person person = null;

		person = (Person) getApplicationContext().getBean("directoryPerson");

		if (jsonData.isEmpty() || "".equalsIgnoreCase(jsonData.toString())) {
			person.setDisplayName("Error");
			List<String> tDesc = new ArrayList<String>();
			tDesc.add("To many search results found. Please try again.");
			person.setAffiliations(tDesc);
		} else if (null != jsonData.get("errors")
				&& (jsonData.get("errors")) instanceof JSONObject) {
			JSONObject error = (JSONObject) jsonData.get("errors");
			if (null != error.get("global")) {
				JSONObject globalError = (JSONObject) error.get("global");
				person.setDisplayName("Error");
				List<String> tDesc = new ArrayList<String>();
				tDesc.add((String) globalError.get("description"));
				person.setAffiliations(tDesc);
			}
		} else {
			person.setDisplayName((String) jsonData.get("displayName"));
			if (null != jsonData.get("name")
					&& !"".equalsIgnoreCase((String) jsonData.get("name"))) {
				person.setDisplayName((String) jsonData.get("name"));
			}
			person.setEmail((String) jsonData.get("email"));
			person.setUserName((String) jsonData.get("uniqname"));

			if (null != jsonData.get("affiliation")) {
				if (jsonData.get("affiliation") instanceof String) {
					List<String> tList = new ArrayList<String>();
					tList.add((String) jsonData.get("affiliation"));
					person.setAffiliations(tList);
				} else {
					person.setAffiliations((List) jsonData.get("affiliation"));
				}
			}

			if (null != jsonData.get("aff")) {
				if (jsonData.get("aff") instanceof String) {
					List<String> tList = new ArrayList<String>();
					tList.add((String) jsonData.get("aff"));
					person.setAffiliations(tList);
				} else {
					person.setAffiliations((List) jsonData.get("aff"));
				}
			}

			if (null != jsonData.get("title")) {
				if (jsonData.get("title") instanceof String) {
					List<String> tList = new ArrayList<String>();
					tList.add((String) jsonData.get("title"));
					person.setDepartments(tList);
				} else {
					person.setDepartments((List) jsonData.get("title"));
				}
			}

			if (null != jsonData.get("workAddress")) {
				if (jsonData.get("workAddress") instanceof String) {
					person.setAddress(((String) jsonData.get("workAddress"))
							.replace(" $ ", "\n"));
				} else {
					JSONArray addresses = (JSONArray) jsonData
							.get("workAddress");
					person.setAddress(((String) addresses.get(0)).replace(
							" $ ", "\n"));
				}
			}

			if (null != jsonData.get("workPhone")) {
				if (jsonData.get("workPhone") instanceof String) {
					person.setPhone(((String) jsonData.get("workPhone"))
							.replace(" $ ", "\n"));
				} else {
					JSONArray phones = (JSONArray) jsonData.get("workPhone");
					person.setPhone(((String) phones.get(0)).replace(" $ ",
							"\n"));
				}
			}
		}
		return person;
	}

	public String getURLContent(String url) {
		URLConnection conn;
		StringBuilder sb = null;
		try {
			conn = new URL(url).openConnection();
			LOG.debug("connection opened ok" + url);
			/*
			 * String encoding = conn.getContentEncoding(); LOG.debug("encoding
			 * : " + encoding); if (encoding == null) { encoding = "ISO-8859-1";
			 * }
			 */
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), DEFAULT_CHARACTER_SET));
			sb = new StringBuilder(16384);
			try {
				String line;

				while ((line = br.readLine()) != null) {

					LOG.debug(line);
					sb.append(line);
					sb.append('\n');
				}
			} finally {
				br.close();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
