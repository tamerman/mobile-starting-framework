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

package org.kuali.mobility.events.service;

import flexjson.JSONSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.kuali.mobility.events.dao.EventsDao;
import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.CategoryImpl;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventImpl;
import org.kuali.mobility.events.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@WebService(endpointInterface = "org.kuali.mobility.events.service.EventsService")
public class EventsServiceImpl implements EventsService {

    private static final Logger LOG = LoggerFactory.getLogger(EventsServiceImpl.class);

    private static String[] DATE_PATTERNS = {
            "YYYY-MM-DD",
            "YYYY-MMM-DD",
            "DD-MM-YYYY",
            "DD-MMM-YYYY",
            "DD MMMM YYYY",
            "MMMM DD, YYYY",
            "DD/MM/YYYY",
            "YYYYMMDD"
    };

    @Resource(name="eventDao")
    private EventsDao dao;
//    private List<Event> resultEvents;
//    private List<Category> resultCategories;

    @GET
    @Path("/findEvent")
    @Override
    public EventImpl getEvent(@QueryParam(value = "campus") String campus, @QueryParam(value = "categoryId") String categoryId,
    				@QueryParam(value = "eventId") String eventId) {
        Event event;

        getDao().initData(campus, categoryId);

        List<Event> events = (List<Event>) CollectionUtils.select(getDao().getEvents(), new EventPredicate(campus, categoryId, eventId));

        if (events == null || events.isEmpty()) {
            LOG.info("No events matched the criteria.");
            event = null;
        } else if (events.size() > 1) {
            LOG.info("Multiple events found that match the criteria.");
            event = events.get(0);
        } else {
            event = events.get(0);
        }

        return (EventImpl)(new EventTransform().transform(event));
    }

    @GET
    @Path("/byCategory/{categoryId}")
    @Override
    public List<EventImpl> getAllEvents(@QueryParam(value = "campus") String campus,@PathParam(value = "categoryId") String categoryId) {
        getDao().initData(campus, categoryId);
        List<Event> events = (List<Event>) CollectionUtils.select(getDao().getEvents(), new EventPredicate(campus, categoryId, null));
        Collections.sort(events, new EventComparator());

        List<EventImpl> eventList = new ArrayList<EventImpl>();
        CollectionUtils.collect(events, new EventTransform(),eventList);

        return eventList;
    }

    @GET
    @Path("/categories")
    @Override
    public List<CategoryImpl> getCategoriesByCampus(@QueryParam(value = "campus") String campus) {
        List<Category> categories = new ArrayList<Category>();

		LOG.debug( "Loading categories for campus ["+campus+"]" );
        getDao().initData(campus);

        categories = (List<Category>) CollectionUtils.select(getDao().getCategories(), new CategoryPredicate(campus, null));

		LOG.debug( "Number of categories for campus ["+campus+"] = "+categories.size() );

        List<CategoryImpl> categoryObjs = new ArrayList<CategoryImpl>();
        for(int i = 0; i < categories.size(); i++) {
        	Collection events = CollectionUtils.select(getDao().getEvents(), new EventPredicate(campus, categories.get(i).getCategoryId(), null));        	
        	if (events.size() > 0) {
            	categories.get(i).setHasEvents(true);
        	} else {
        		categories.get(i).setHasEvents(false);
        	}
        }
        CollectionUtils.collect(categories, new CategoryTransform(), categoryObjs);
        
        return categoryObjs;
    }

    @GET
    @Path("/findByDateRange")
    public List<EventImpl> getEventsByDateRange(
        @QueryParam(value="campus") String campus,
        @QueryParam(value="fromDate") String fromDate,
        @QueryParam(value="toDate") String toDate,
        @QueryParam(value="categoryId") String categoryId
    ) {
        Date startDate,endDate;
        List<EventImpl> events = new ArrayList<EventImpl>();
        try {
            startDate = DateUtils.parseDateStrictly(fromDate, DATE_PATTERNS);
            endDate = DateUtils.parseDateStrictly(toDate,DATE_PATTERNS);
        } catch( ParseException pe ) {
            LOG.error(pe.getLocalizedMessage(),pe);
            startDate = new Date();
            endDate = new Date();
        }
        if( null == categoryId || categoryId.isEmpty() ) {
            events = getEventsForDateRange(fromDate,toDate);
        } else {
            events = getAllEventsByDateFromTo(campus, categoryId, startDate, endDate);
        }
        return events;
    }

    @GET
    @Deprecated
    @Path("/getEventsForDateRange")
    public List<EventImpl> getEventsForDateRange(@QueryParam(value = "fromDate") String fromDateStr, @QueryParam(value = "toDate") String toDateStr) {
        List<EventImpl> filteredEvents = new ArrayList<EventImpl>();
        List<EventImpl> eventList = new ArrayList<EventImpl>();
        List<EventImpl> allEvents = new ArrayList<EventImpl>();
        List<CategoryImpl> categories = new ArrayList<CategoryImpl>();
        String campus = "ALL";
        categories = getCategoriesByCampus(campus);

        Date fromDate = null;
        Date toDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fromDate = sdf.parse(fromDateStr);
            toDate = sdf.parse(toDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(Category category : categories) {
            eventList = getAllEvents(campus, category.getCategoryId());
            if(!eventList.isEmpty() && eventList != null) {
                allEvents.addAll(eventList);
            }
        }
        boolean hasEndDate = true;
        Iterator it = allEvents.iterator();
        while(it.hasNext()) {
            EventImpl obj = (EventImpl) it.next();
            if(obj.getEndDate() != null) {
                hasEndDate = !(obj.getEndDate().after(toDate));
            }
            if (!obj.getStartDate().before(fromDate) && hasEndDate) {
                filteredEvents.add(obj);
            }
            hasEndDate = false;
        }
        Collections.sort(filteredEvents, new Comparator<EventImpl>() {
            public int compare(EventImpl e1, EventImpl e2) {
                return e1.getStartDate().compareTo(e2.getStartDate());
            }
        });
        return filteredEvents;
    }


    public List<EventImpl> getAllEvents() {
        List<EventImpl> allEvents = new ArrayList<EventImpl>();
        List<EventImpl> eventList = new ArrayList<EventImpl>();
        List<CategoryImpl> categories = new ArrayList<CategoryImpl>();
        String campus = "ALL";
        categories = getCategoriesByCampus(campus);
        for(CategoryImpl  category : categories) {
            eventList = getAllEvents(campus, category.getCategoryId());
            allEvents.addAll(eventList);
        }
        return  allEvents;
    }


    @GET
    @Path("/onDate")
    public List<EventImpl> getEventsForCurrentDate(@QueryParam(value = "date") String date) {
        List<EventImpl> filteredEvents = new ArrayList<EventImpl>();
        List<EventImpl> allEvents = getAllEvents();
        String campus = "ALL";
        Iterator it = allEvents.iterator();
        while(it.hasNext()) {
              EventImpl obj = (EventImpl) it.next();
              Date eventDate = obj.getStartDate();
              Format formatter = new SimpleDateFormat("yyyy-MM-dd");
              String eventDateStr = formatter.format(eventDate);
              if(eventDateStr.equals(date)) {
                  filteredEvents.add(obj);
              }
        }
        return filteredEvents;
    }


    @GET
    @Path("/nextDay")
    public String getNextDay(@QueryParam(value = "currentDate") String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date eventDate = null;
        Date nextDay = null;
        try {
            eventDate = sdf.parse(currentDate);
            nextDay = addDays(eventDate, 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String nextDayStr = sdf.format(nextDay);
        return nextDayStr;
    }


    @GET
    @Path("/previousDay")
    public String getPreviousDay(@QueryParam(value = "currentDate") String currentDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date eventDate = null;
        Date previousDay = null;
        try {
            eventDate = sdf.parse(currentDate);
            previousDay = addDays(eventDate, -1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String previousDayStr = sdf.format(previousDay);
        return previousDayStr;
    }


    public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


    @GET
    @Path("/category/{categoryId}")
    @Override
    public CategoryImpl getCategory(@QueryParam(value = "campus") String campus, @PathParam(value = "categoryId") String categoryId) {

        if (getDao().getCategories() == null || getDao().getCategories().isEmpty()) {
            getDao().initData(campus);
        }

        Category category = (Category) CollectionUtils.find(getDao().getCategories(), new CategoryPredicate(campus, categoryId));

        return (CategoryImpl)(new CategoryTransform().transform(category));
    }

    /**
     * @return the dao
     */
    public EventsDao getDao() {
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EventsDao dao) {
        this.dao = dao;
    }

    public String getEventJson(final String eventId) {
        return new JSONSerializer().exclude("*.class").deepSerialize(this.getEvent(null, null, eventId));
    }


    //Events Grouped by Category code starts here
    @GET
    @Deprecated
    @Path("/current/{campus}/{categoryId}")
    @Override
    public List<EventImpl> getAllEventsByDateCurrent(@QueryParam(value = "campus") String campus, @QueryParam(value = "categoryId") String categoryId,
    			@QueryParam (value = "mon-dd-yy") Date dateCurrent) {

        List<EventImpl> events = getAllEvents(campus, categoryId);
        List<Event> resultEvents = new ArrayList<Event>();
        Iterator it = events.iterator();
        while (it.hasNext()) {
            Event obj = (Event) it.next();
            Date obtainedDate = obj.getStartDate();
            if (obtainedDate.equals(dateCurrent)) {
                resultEvents.add(obj);
            }

        }

        List<EventImpl> resultEventObjs = new ArrayList<EventImpl>();
        CollectionUtils.collect(resultEvents,new EventTransform(),resultEventObjs);

        return resultEventObjs;
    }

    @GET
    @Path("/between/{campus}/{categoryId}")
    @Deprecated
    @Override
    public List<EventImpl> getAllEventsByDateFromTo(@QueryParam(value = "campus") String campus, @QueryParam(value = "categoryId") String categoryId,
    		@QueryParam (value = "from-mon-dd-yy") Date from, @QueryParam (value = "to-mon-dd-yy") Date to) {

        List<EventImpl> events = getAllEvents(campus, categoryId);
        List<Event> resultEvents = new ArrayList<Event>();
        Iterator it = events.iterator();
        while (it.hasNext()) {
            Event obj = (Event) it.next();
            if (!obj.getStartDate().before(from) || !obj.getEndDate().after(to)) {
                resultEvents.add(obj);
            }

        }

        List<EventImpl> resultEventObjs = new ArrayList<EventImpl>();
        CollectionUtils.collect(resultEvents,new EventTransform(),resultEventObjs);

        return resultEventObjs;

    }

    @GET
    @Path("/fordate/{campus}/{categoryId}")
    @Deprecated
    @Override
    public List<EventImpl> getAllEventsByDateSpecific(@QueryParam(value = "campus") String campus, @QueryParam(value = "categoryId") String categoryId,
    		@QueryParam (value = "yyyy-mm-dd") String specific) {
        List<? extends Event> events = getAllEvents(campus, categoryId);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Event> resultEvents = new ArrayList<Event>();
        try {
			for( Event e : events ) {
				String startDate = sdf.format(e.getStartDate());
				if( specific.equalsIgnoreCase( startDate ) ) {
					LOG.debug( "Found match for event date ["+startDate+"] to ["+specific+"]." );
	                resultEvents.add(e);
                }
            }
        } catch (Exception ex) {
            LOG.info("Failure from getAllEventsByDateSpecific method");
            ex.printStackTrace();
        }
		LOG.debug( "Number of events matching date: "+resultEvents.size() );
        List<EventImpl> resultEventObjs = new ArrayList<EventImpl>();
        CollectionUtils.collect(resultEvents,new EventTransform(),resultEventObjs);
		LOG.debug( "Number of events after transform: "+resultEventObjs.size() );
        return resultEventObjs;
    }
    //Saket's contribution ends here
}
