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

package org.kuali.mobility.events.dao;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.events.entity.*;
import org.kuali.mobility.events.util.CategoryPredicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class EventsDaoUMImpl extends EventsDaoImpl {

    private static final Logger LOG = LoggerFactory.getLogger( EventsDaoUMImpl.class );
    
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    @Override
    public void initData( final String campus, final String categoryId ) {
    	this.addEvents(loadEventsForCategory (campus, categoryId));
    	
    }
    public List<Event> loadEventsForCategory( final String campus, final String categoryId ) {
    	
    	LOG.debug( "Loading event feed for category "+categoryId );
        if( null == getEvents() || getEvents().isEmpty() )
        {
        	LOG.debug( "Events list was empty, creating a new one." );
            //setEvents( new ArrayList<Event>() );
        }
        if( null == getCategories() || getCategories().isEmpty() )
        {
        	LOG.debug( "Category list was empty, initializing a new one." );
        	initData( campus );
        }

        List<Event> newEvents = new ArrayList<Event>();

		Category category = (Category) CollectionUtils.find ( getCategories(), new CategoryPredicate( campus, categoryId ) );;
		
		if ( category != null ) {
			LOG.debug( "Found category object for id "+categoryId );
			XStream xstream = new XStream();
			xstream.processAnnotations(UMEventReader.class);
			xstream.processAnnotations(UMEvent.class);
			xstream.processAnnotations(UMSponsor.class);
			UMEventReader eventReader = null;
			try {
				URL url = new URL(category.getUrlString()+"&_type=xml");
				LOG.debug("Mapping events from url: " + category.getUrlString());
				
				if (url != null) {
					eventReader = (UMEventReader) xstream.fromXML(url);
				}
			} catch (MalformedURLException mue) {
	        	LOG.error( mue.getLocalizedMessage() );
	        }
			LOG.debug("check eventReader " + (eventReader == null?"null":"mnot null"));
			LOG.debug("check eventReader.getEvents " + (eventReader.getEvents() == null?"null":"mnot null"));

			if ( eventReader != null && eventReader.getEvents() != null) {
			for ( UMEvent e : eventReader.getEvents()){
				LOG.debug("processing e " + e.getTitle());
				Event newEvent = (Event) getApplicationContext().getBean("event");
				newEvent.setEventId(e.getId());
				newEvent.setCategory(category);
				newEvent.setTitle(e.getTitle());
				newEvent.setDisplayStartTime(e.getTimeBegin());
                                //Saket's Addition
                                newEvent.setType(e.getType());                                
			    newEvent.setDisplayStartDate(e.getDateBegin());
			    newEvent.setLocation(e.getBuildingName());
			    newEvent.setLink(e.getUrl());
			    try {
			    	if ( e.getTsBegin() != null && e.getTsBegin().isEmpty() == false) {
					newEvent.setStartDate(sdf.parse(e.getTsBegin())); 
					}
			    	if ( e.getTsEnd() != null && e.getTsEnd().isEmpty() == false) {
					newEvent.setEndDate(sdf.parse(e.getTsEnd()));
			    	}
				} catch (ParseException e1) {
					LOG.error(e1.getLocalizedMessage());
				}
				newEvent.setDisplayEndTime(e.getTimeEnd());
				newEvent.setDisplayEndDate(e.getDateEnd());
				List<String> myDescription = new ArrayList<String>();
				myDescription.add(e.getDescription());
				newEvent.setDescription( myDescription );
				List<EventContact> myContacts = new ArrayList<EventContact>();
				for ( UMSponsor f : e.getSponsors()){
					EventContact newContact = (EventContact) getApplicationContext().getBean("eventContact");
					newContact.setName(f.getGroupName());
					newContact.setUrl(f.getWebsite());
					myContacts.add(newContact);	
				}
				newEvent.setContact(myContacts);
				LOG.debug("CONTACT " + newEvent.getContact());
				newEvents.add(newEvent);
			}
			}
		}
		return( newEvents );
	}

}
