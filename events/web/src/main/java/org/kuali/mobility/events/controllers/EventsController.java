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

package org.kuali.mobility.events.controllers;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.CategoryImpl;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventImpl;
import org.kuali.mobility.events.service.EventsService;
import org.kuali.mobility.events.util.CategoryComparator;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Controller
@RequestMapping("/events")
public class EventsController {

	private static final Logger LOG = LoggerFactory.getLogger(EventsController.class);
	@Resource(name = "eventService")
	private EventsService eventsService;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@Resource(name = "eventsProperties")
	private Properties eventsProperties;

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(method = RequestMethod.GET)
	public String homePage(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String date, @RequestParam(required = false) String direction) throws ParseException {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String viewName = null;
		if (user.getViewCampus() == null) {
			viewName = "redirect:/campus?toolName=events";
		} else if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			viewName = "ui3/events/index";
		} else {
			String campus = null;
			campus = user.getViewCampus();

			List<Category> categories = (List<Category>) getEventsService().getCategoriesByCampus(campus);
			LOG.debug("Found " + categories.size() + " categories via local service for campus " + campus);
			uiModel.addAttribute("categories", categories);
			uiModel.addAttribute("campus", campus);

			//code for making the new home page of events
			HashMap<Category, List<EventImpl>> mObj = new HashMap<Category, List<EventImpl>>();

			if (null == date || date.isEmpty()) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date d = new Date();
				date = sdf.format(new Date());
			} else {
				int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date tempDate = sdf.parse(date);

				String nextDate;
				if ("previous".equalsIgnoreCase(direction)) {
					nextDate = sdf.format(tempDate.getTime() - MILLIS_IN_DAY);
				} else if ("next".equalsIgnoreCase(direction)) {
					nextDate = sdf.format(tempDate.getTime() + MILLIS_IN_DAY);
				} else {
					nextDate = date;
				}
				date = nextDate;
			}

			uiModel.addAttribute("displayDate", date);

			for (Category c : categories) {
				List<EventImpl> eventList = (List<EventImpl>) getEventsService().getAllEventsByDateSpecific(campus, c.getCategoryId(), date);

				if (eventList != null && !eventList.isEmpty()) {
					mObj.put(c, eventList);
				}

				LOG.debug("Found " + eventList.size() + " events for category " + c.getTitle());
			}
			uiModel.addAttribute("categoryMap", mObj);

			List<Category> categoriesWithEvents = new ArrayList<Category>();
			categoriesWithEvents.addAll(mObj.keySet());

			Collections.sort(categoriesWithEvents, new CategoryComparator());

			uiModel.addAttribute("categoryList", categoriesWithEvents);

			uiModel.addAttribute("showCategoryTab", getEventsProperties().getProperty("events.showCategoryTab", "true"));
			uiModel.addAttribute("showDateRangeTab", getEventsProperties().getProperty("events.showDateRangeTab", "true"));
			if ("true".equalsIgnoreCase(getEventsProperties().getProperty("events.enableHistoricalDate", "false"))) {
				uiModel.addAttribute("showPreviousDate", "true");
			} else {
				// check if selected date is after Today
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date selectedDate = sdf.parse(date);
				if (selectedDate.before(new Date())) {
					LOG.debug(selectedDate + " is before Today");
					uiModel.addAttribute("showPreviousDate", "false");
				} else {
					LOG.debug(selectedDate + " is after Today");
					uiModel.addAttribute("showPreviousDate", "true");
				}
			}

			viewName = "events/index";
		}
		return viewName;
	}

	@RequestMapping(value = "/byCategory", method = RequestMethod.GET)
	public String homeByCategory(HttpServletRequest request, Model uiModel) throws ParseException {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			return "redirect:/campus?toolName=events";
		} else {
			campus = user.getViewCampus();
		}
		List<? extends Category> categories = getEventsService().getCategoriesByCampus(campus);
		uiModel.addAttribute("categories", categories);
		uiModel.addAttribute("campus", campus);

		uiModel.addAttribute("showCategoryTab", getEventsProperties().getProperty("events.showCategoryTab", "true"));
		uiModel.addAttribute("showDateRangeTab", getEventsProperties().getProperty("events.showDateRangeTab", "true"));

		return "events/category";
	}

	@RequestMapping(value = "/byDateRange", method = RequestMethod.GET)
	public String homeByDateRange(HttpServletRequest request, Model uiModel) throws ParseException {
		uiModel.addAttribute("showCategoryTab", getEventsProperties().getProperty("events.showCategoryTab", "true"));
		uiModel.addAttribute("showDateRangeTab", getEventsProperties().getProperty("events.showDateRangeTab", "true"));

		return "events/dateRange";
	}

	@RequestMapping(value = "/eventstForDateRange", method = RequestMethod.GET)
	public String eventsListForDateRange(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String from, @RequestParam(required = true) String to) throws ParseException {
		String campus = "ALL";
		List<EventImpl> eventList = null;
		Category category = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date fromDate = null;
		Date toDate = null;
		try {
			fromDate = sdf.parse(from);
			toDate = sdf.parse(to);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (toDate.compareTo(fromDate) >= 0) {
			eventList = getEventsService().getEventsForDateRange(from, to);

			uiModel.addAttribute("events", eventList);

			if (eventList != null && eventList.size() > 0) {
				category = eventList.get(0).getCategory();
			} else {
				category = getEventsService().getCategory(campus, category.getCategoryId());
			}
			if (category == null) {
				LOG.error("Couldn't find category for categoryId - " + category.getCategoryId());
				category = new CategoryImpl();
				category.setCategoryId(category.getCategoryId());
				category.setTitle(category.getCategoryId());
			}
			uiModel.addAttribute("errorMsg", "");
		} else {
			uiModel.addAttribute("errorMsg", getEventsProperties().get("events.errorMsg"));
		}
		uiModel.addAttribute("category", category);
		uiModel.addAttribute("campus", "ALL");
		return "events/eventsForDateRange";
	}

	@RequestMapping(value = "/viewEvents", method = RequestMethod.GET)
	public String viewEvents(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus) throws Exception {
		String filteredCategoryId = (String) request.getSession().getAttribute("categoryId");
		String filteredCampus = (String) request.getSession().getAttribute("campus");
		List<Event> eventList = (List<Event>) getEventsService().getAllEvents(filteredCampus, filteredCategoryId);
		uiModel.addAttribute("events", eventList);
		Category category;
		if (eventList != null && eventList.size() > 0) {
			category = eventList.get(0).getCategory();
		} else {
			category = getEventsService().getCategory(campus, filteredCategoryId);
		}
		if (category == null) {
			LOG.error("Couldn't find category for categoryId - " + filteredCategoryId);
			category = new CategoryImpl();
			category.setCategoryId(filteredCategoryId);
			category.setTitle(filteredCategoryId);
		}
		uiModel.addAttribute("category", category);
		uiModel.addAttribute("campus", campus);
		return "events/eventsList";
	}

	@RequestMapping(value = "/viewEvent", method = RequestMethod.GET)
	public String viewEvent(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String categoryId, @RequestParam(required = false) String campus, @RequestParam(required = true) String eventId) throws Exception {
		//Event event = eventsService.getEvent(campus, categoryId, eventId);
		//uiModel.addAttribute("event", event);
		String filteredCategoryId = (String) request.getSession().getAttribute("categoryId");
		String filteredCampus = (String) request.getSession().getAttribute("campus");
		String filteredEventId = (String) request.getSession().getAttribute("eventId");
		uiModel.addAttribute("categoryId", filteredCategoryId);
		uiModel.addAttribute("campus", filteredCampus);
		uiModel.addAttribute("event", filteredEventId);
		return "events/detail";
	}

	@RequestMapping(value = "/viewEvent", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public String getEventJson(@RequestParam(required = true) String eventId) {
		return getEventsService().getEventJson(eventId);
	}

	@RequestMapping(value = "/viewEventsByDateFromTo", method = RequestMethod.GET)
	public String viewEventsByDateFromTo(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String dateFrom, @RequestParam(required = true) String dateTo) throws Exception {

		SortedMap<String, HashMap<Category, List<Event>>> mObjFT = new TreeMap<String, HashMap<Category, List<Event>>>();
		List<String> listOfDates = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateF = sdf.parse(dateFrom);
		Date dateT = sdf.parse(dateTo);
		//checking if the start date is smaller than the end date
		if (dateF.before(dateT)) {
			//getting the array list of dates from the range
			listOfDates = listOfDates(dateFrom, dateTo);
			Iterator ite = listOfDates.iterator();
			while (ite.hasNext()) {
				String fetchedDate = (String) ite.next();
				System.out.println(fetchedDate);
				//storing into map, each date as key and "list of events grouped by category" as value pair
//                mObjFT.put(fetchedDate, supportViewEventsDateSpecific(fetchedDate, categories));
			}
			uiModel.addAttribute("eventDateFromTo", mObjFT);
		}

		return "events/eventsListDateRange";
	}

	public List<String> listOfDates(String from, String to) throws Exception {
		List<String> lst = new ArrayList<String>();
		int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date dateF = sdf.parse(from);
		Date dateT = sdf.parse(to);

		while (dateF.equals(dateT) || dateF.before(dateT)) {
			lst.add(from);
			from = sdf.format(dateF.getTime() + MILLIS_IN_DAY);
			dateF = sdf.parse(from);
		}

		return lst;
	}

	@RequestMapping(value = "/templates/{key}")
	public String getAngularTemplates(
			@PathVariable("key") String key,
			HttpServletRequest request,
			Model uiModel) {
		uiModel.addAttribute("showCategoryTab", getEventsProperties().getProperty("events.showCategoryTab", "true"));
		uiModel.addAttribute("showDateRangeTab", getEventsProperties().getProperty("events.showDateRangeTab", "true"));
		return "ui3/events/templates/" + key;
	}

	@RequestMapping(value = "/js/events.js")
	public String getJavaScript(Model uiModel, HttpServletRequest request) {
		uiModel.addAttribute("today", DATE_FORMAT.format(GregorianCalendar.getInstance().getTime()));
		return "ui3/events/js/events";
	}


	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public EventsService getEventsService() {
		return eventsService;
	}

	public void setEventsService(EventsService eventsService) {
		this.eventsService = eventsService;
	}

	public Properties getEventsProperties() {
		return eventsProperties;
	}

	public void setEventsProperties(Properties eventsProperties) {
		this.eventsProperties = eventsProperties;
	}
}
