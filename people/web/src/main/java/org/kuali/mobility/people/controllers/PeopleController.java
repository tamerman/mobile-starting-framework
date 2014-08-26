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

package org.kuali.mobility.people.controllers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResult;
import org.kuali.mobility.people.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Controller for the people tool
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Controller
@RequestMapping("/people")
public class PeopleController {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PeopleController.class);

	private static final String PEOPLE_SEARCH_RESULT = "People.Search.Results";
	private static final String PEOPLE_SEARCH_UNIQUE_RESULT = "People.Search.UniqueResult";
	private static final String PEOPLE_USERNAME_HASHES = "People.UserNames.Hashes";
	private static final String PEOPLE_SEARCH_RESULTS_PERSON = "People.Search.Results.Person";
	private static final String PEOPLE_SEARCH_CRITERIA = "People.Search.Criteria";
	private static final String GROUP_DISTINGUISHED_NAME_HASHES = "Group.distinguishedName.Hashes";
	private static final String GROUP_SEARCH_RESULTS = "Group.Search.Results";
	private static final String GROUP_DETAILS_MEMBERS = "Group.Details.Members";
	private static final String GROUP_SEARCH_RESULTS_GROUP = "Group.Search.Results.Group";

	/**
	 * Map of status types
	 */
	private static final Map<String, String> statusTypes = new LinkedHashMap<String, String>();

	static {
		statusTypes.put("Any", "Any Status");
		statusTypes.put("Student", "Student");
		statusTypes.put("Faculty", "Faculty");
		statusTypes.put("Employee", "Employee");
	}

	/**
	 * A reference to the <code>DirectoryService</code>
	 */
	@Autowired
	@Qualifier("directoryService")
	private DirectoryService peopleService;

	/**
	 * A reference to the Directory properties
	 */
	@Autowired
	@Qualifier("directoryProperties")
	private Properties directoryProperties;

	/**
	 * A reference to the <code>CampusService</code>.
	 */
	@Autowired
	private CampusService campusService;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	/**
	 * A reference to the Spring Message source
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	/**
	 * A reference to the Spring Locale Resolver
	 */
	@Resource(name = "localeResolver")
	private LocaleResolver localeResolver;

	/**
	 * Default page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model uiModel, HttpServletRequest request) {
		String viewName = null;
		SearchCriteria s = new SearchCriteria();
		uiModel.addAttribute("search", s);
		// removeFromCache(request.getSession(), PEOPLE_SEARCH_RESULT);
		// request.setAttribute("watermark",
		// "[Keyword] or [Last, First] or [First Last]");
		uiModel.addAttribute("locations", getCampusNames());
		uiModel.addAttribute("statusTypes", getStatusTypes(request));

		uiModel.addAttribute("basicSearchDiv", "div1");
		uiModel.addAttribute("advancedSearchDiv", "div2");

		uiModel.addAttribute("enableAdvancedSearchToggle", isAdvancedSearchEnabled());
		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			//viewName = "people/index";
			viewName = "ui3/people/index";
		} else {
			viewName = "people/index";
		}
		return viewName;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postSimpleSearch(Model uiModel,
								   @ModelAttribute("search") SearchCriteria search,
								   BindingResult result,
								   HttpServletRequest request) {

		uiModel.addAttribute("locations", getCampusNames());
		uiModel.addAttribute("statusTypes", getStatusTypes(request));

		String searchTypeString = request.getParameter("searchtype");
		if (searchTypeString.equals("BasicSearch")) {
			uiModel.addAttribute("basicSearchDiv", "div1");
			uiModel.addAttribute("advancedSearchDiv", "div2");
		}
		if (searchTypeString.equals("AdvancedSearch")) {
			uiModel.addAttribute("advancedSearchDiv", "div1");
			uiModel.addAttribute("basicSearchDiv", "div2");
		}
		if (validateSimpleSearch(search, result, request)) {
			SearchResult results = getPeopleService().findEntries(search);
			// LOG.debug("people POST size: " + people.size());
			if (results != null) {
				Map<String, String> userNameHashes = new HashMap<String, String>();
				removeFromCache(request.getSession(), PEOPLE_SEARCH_UNIQUE_RESULT);
				for (Person p : results.getPeople()) {
					if (p.getUserName() != null) {
						// LOG.debug("p.getHashedUserName() " +
						// p.getHashedUserName());
						userNameHashes.put(p.getHashedUserName(), p.getUserName());
					}
					if (p.getUserName() != null
							&& search != null
							&& search.getSearchText() != null
							&& p.getUserName().trim().equals(search.getSearchText().trim())) {
						putInCache(request.getSession(), PEOPLE_SEARCH_UNIQUE_RESULT, p);
					}
				}

				request.getSession().setAttribute(PEOPLE_USERNAME_HASHES, userNameHashes);
				putInCache(request.getSession(), PEOPLE_SEARCH_RESULT, results.getPeople());
				putInCache(request.getSession(), PEOPLE_SEARCH_CRITERIA, search);

				Map<String, String> groupDNHashes = new HashMap<String, String>();
				for (Group g : results.getGroups()) {
					if (g.getDisplayName() != null) {
						LOG.debug("g.getHashedDN() " + g.getHashedDN());
						groupDNHashes.put(g.getHashedDN(), g.getDN());
					}
				}
				request.getSession().setAttribute(GROUP_DISTINGUISHED_NAME_HASHES, groupDNHashes);
				// LOG.debug("group POST size: " + group.size());
				putInCache(request.getSession(), GROUP_SEARCH_RESULTS, results.getGroups());
				request.setAttribute("watermark", getLocalisedMessage("people.searchWatermark", request));
			}
			return "people/index";
		} else {
			return "people/index";
		}
	}

	@RequestMapping(value = "/advanced", method = RequestMethod.GET)
	public String viewSearchForm(Model uiModel, HttpServletRequest request) {
		SearchCriteria s = new SearchCriteria();
		s.setStatus("Any");
		uiModel.addAttribute("search", s);
		uiModel.addAttribute("locations", getCampusNames());
		uiModel.addAttribute("statusTypes", getStatusTypes(request));

		return "people/form";
	}

	@RequestMapping(value = "/advanced", method = RequestMethod.POST)
	public String postSearchForm(Model uiModel,
								 @ModelAttribute("search") SearchCriteria search,
								 BindingResult result,
								 HttpServletRequest request) {

		if (validateAdvancedSearch(search, result, request)) {
			SearchResult results = getPeopleService().findEntries(search);

			Map<String, String> userNameHashes = new HashMap<String, String>();
			for (Person p : results.getPeople()) {
				userNameHashes.put(p.getHashedUserName(), p.getUserName());
			}
			request.getSession().setAttribute(PEOPLE_USERNAME_HASHES, userNameHashes);
			putInCache(request.getSession(), PEOPLE_SEARCH_RESULT, results.getPeople());

			return "people/list";
		} else {
			uiModel.addAttribute("statusTypes", getStatusTypes(request));
			uiModel.addAttribute("locations", getCampusNames());
			return "people/form";
		}
	}

	@RequestMapping(value = "/{userNameHash}", method = RequestMethod.GET)
	public String getUserDetails(Model uiModel,
								 HttpServletRequest request,
								 @PathVariable("userNameHash") String userNameHash) {

		Map<String, Object> details = new HashMap<String, Object>();
		Person p = getPeopleService().personLookup(userNameHash);
		details.put("person", p);
		details.put("loggedIn", false);
		putInCache(request.getSession(), PEOPLE_SEARCH_RESULTS_PERSON, details);
		uiModel.addAttribute("person", p);
		return "people/details";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/group/{hashedDN}", method = RequestMethod.GET)
	public String getGroupDetails(Model uiModel,
								  HttpServletRequest request,
								  @PathVariable("hashedDN") String hashedDisplayName) {

		Map<String, Object> details = new HashMap<String, Object>();

		// Map<String, String> GroupNameHashes = (Map<String, String>)
		// request.getSession().getAttribute(GROUP_DISTINGUISHED_NAME_HASHES);
		Group g = null;
		// if (hashedDisplayName != null) {
		// String groupName = GroupNameHashes.get(hashedDisplayName);
		// LOG.debug("group:" + hashedDisplayName + "groupname:" + groupName);
		String groupName = hashedDisplayName;
		g = getPeopleService().groupLookup(groupName);
		/*
		 * if(g != null) LOG.debug("group detail " + g.getDisplayName());
		 */
		// }
		details.put("group", g);
		details.put("loggedIn", false);

		uiModel.addAttribute("group", g);
		uiModel.addAttribute("groupName", groupName);
		putInCache(request.getSession(), GROUP_SEARCH_RESULTS_GROUP, details);

		return "people/groupdetails";
	}

	/**
	 * adding groupmembers and owners page begin
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/group/groupmembers/{hashedDN}", method = RequestMethod.GET)
	public String getGroupMembers(Model uiModel, HttpServletRequest request,
								  @PathVariable("hashedDN") String hashedDisplayName) {
		Map<String, Object> members = new HashMap<String, Object>();
		// Map<String, String> GroupNameHashes = (Map<String, String>)
		// request.getSession().getAttribute(GROUP_DISTINGUISHED_NAME_HASHES);
		Group g = null;
		// if (hashedDisplayName != null) {
		// String groupName = GroupNameHashes.get(hashedDisplayName);
		// LOG.debug("group:" + hashedDisplayName + "groupname:" + groupName);
		String groupName = hashedDisplayName;
		g = getPeopleService().groupLookup(groupName);
		/*
		 * if(g != null) LOG.debug("group detail " + g.getDiplayName());
		 */
		// }
		members.put("group", g);
		members.put("loggedIn", false);
		uiModel.addAttribute("group", g);
		uiModel.addAttribute("groupName", groupName);
		putInCache(request.getSession(), GROUP_DETAILS_MEMBERS, members);
		return "people/groupmembers";
	}

	/**
	 * adding groupmembers and owners page end
	 */
	@RequestMapping(value = "/image/{hash}", method = RequestMethod.GET)
	public void getImage(@PathVariable("hash") String imageKeyHash,
						 Model uiModel, HttpServletRequest request,
						 HttpServletResponse response) throws Exception {
		byte[] byteArray = (byte[]) request.getSession().getAttribute(
				"People.Image.Email." + imageKeyHash);
		if (byteArray != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream(
					byteArray.length);
			baos.write(byteArray);
			if (baos != null) {
				ServletOutputStream sos = null;
				try {
					response.setContentLength(baos.size());
					sos = response.getOutputStream();
					baos.writeTo(sos);
					sos.flush();
				} catch (Exception e) {
					LOG.error("error creating image file", e);
				} finally {
					try {
						baos.close();
						sos.close();
					} catch (Exception e1) {
						LOG.error("error closing output stream", e1);
					}
				}
			}
		}
	}

	/**
	 * beginning of ui3
	 */
	@RequestMapping(value = "/templates/{key}")
	public String getAngularTemplates(
			@PathVariable("key") String key,
			HttpServletRequest request,
			Model uiModel) {
		return "ui3/people/templates/" + key;
	}

	@RequestMapping(value = "/js/people.js")
	public String getJavaScript(Model uiModel) {
		uiModel.addAttribute("locations", getCampusNames());
		uiModel.addAttribute("enableAdvancedSearchToggle", isAdvancedSearchEnabled());
		return "ui3/people/js/people";
	}

	/**
	 * end of ui3
	 */

	/**
	 * Validates a simple search's seach criteria
	 *
	 * @param search
	 * @param result
	 * @param request
	 * @return
	 */
	private boolean validateSimpleSearch(SearchCriteria search, BindingResult result, HttpServletRequest request) {
		boolean hasErrors = false;
		// Errors errors = ((Errors) result);
		if ((search.getSearchText() == null || search.getSearchText().trim().isEmpty())) {
			// errors.rejectValue("searchText", "",
			// "You must provide at least one letter to search.");
			request.setAttribute("watermark", getLocalisedMessage("people.simpleSearchErrorWatermark", request));
			hasErrors = true;
		}
		return !hasErrors;
	}

	/**
	 * Validates an advanced searche's criteria
	 *
	 * @param search
	 * @param result
	 * @return
	 */
	private boolean validateAdvancedSearch(SearchCriteria search, BindingResult result, HttpServletRequest request) {
		boolean hasErrors = false;
		Errors errors = ((Errors) result);
		if ((search.getLastName() == null || search.getLastName().trim().isEmpty())
				&& (search.getFirstName() == null || search.getFirstName().trim().isEmpty())
				&& (search.getUserName() == null || search.getUserName().trim().isEmpty())) {
			errors.rejectValue(
					"lastName",
					"",
					getLocalisedMessage("people.advancedSearchError", request));
			hasErrors = true;
		}
		return !hasErrors;
	}

	/**
	 * Sets the reference to the <code>DirectoryService<code>.
	 *
	 * @param peopleService Rreference to the <code>DirectoryService<code>.
	 */
	public void setPeopleService(DirectoryService peopleService) {
		this.peopleService = peopleService;
	}

	/**
	 * Puts an object in the user's session cache
	 *
	 * @param session
	 * @param key
	 * @param item
	 */
	private void putInCache(HttpSession session, String key, Object item) {
		session.setAttribute(key, item);
	}

	/**
	 * Gets an object from the user's session cache.
	 *
	 * @param session
	 * @param key
	 * @return
	 */
	private Object getFromCache(HttpSession session, String key) {
		return session.getAttribute(key);
	}

	/**
	 * Removes an object from the user's session cache
	 *
	 * @param session
	 * @param key
	 */
	private void removeFromCache(HttpSession session, String key) {
		session.removeAttribute(key);
	}

	/**
	 * Gets a list of a available campus names
	 *
	 * @return
	 */
	private List<String> getCampusNames() {
		List<Campus> campusses = this.campusService.getCampuses();
		List<String> names = new ArrayList<String>(campusses.size());
		for (Campus campus : campusses) {
			names.add(campus.getName());
		}
		return names;
	}

	/**
	 * Gets a map of session types
	 *
	 * @return
	 */
	private Map<String, String> getStatusTypes(HttpServletRequest request) {
		Map<String, String> statusNames = new LinkedHashMap<String, String>();
		statusNames.put("Any", getLocalisedMessage("people.status.any", request));
		statusNames.put("Student", getLocalisedMessage("people.status.student", request));
		statusNames.put("Faculty", getLocalisedMessage("people.status.faculty", request));
		statusNames.put("Employee", getLocalisedMessage("people.status.employee", request));
		return statusNames;
	}

	/**
	 * Gets the reference to the Directory Service's properties
	 *
	 * @return the directoryProperties Reference to the Directory Service's properties
	 */
	public Properties getDirectoryProperties() {
		return directoryProperties;
	}

	/**
	 * Sets the reference to the Directory Service's properties.
	 *
	 * @param directoryProperties the directoryProperties to set
	 */
	public void setDirectoryProperties(Properties directoryProperties) {
		this.directoryProperties = directoryProperties;
	}

	/**
	 * Sets the reference to the <code>CampusService</code>.
	 *
	 * @param campusService Reference to the <code>CampusService</code>.
	 */
	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	/**
	 * Gets the reference to the <code>DirectoryService</code>.
	 *
	 * @return the peopleService Reference to the <code>DirectoryService</code>.
	 */
	public DirectoryService getPeopleService() {
		return peopleService;
	}

	/**
	 * Returns true if advanced search is enabled
	 *
	 * @return True if the advanced search option should be available
	 */
	private boolean isAdvancedSearchEnabled() {
		return Boolean.parseBoolean(getDirectoryProperties().getProperty("people.enableAdvancedSearchToggle", "true"));
	}

	/**
	 * Gets a localised message
	 *
	 * @param key     Key of the message to get
	 * @param request The http request for the current user to render for
	 * @param args    Arguments for the message
	 * @return A localised string
	 */
	private String getLocalisedMessage(String key, HttpServletRequest request, String... args) {
		Locale locale = this.localeResolver.resolveLocale(request);
		return messageSource.getMessage(key, args, locale);
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
