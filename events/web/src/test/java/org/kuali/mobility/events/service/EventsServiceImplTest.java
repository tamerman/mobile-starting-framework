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

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.events.entity.CategoryImpl;
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author swansje
 */
public class EventsServiceImplTest {

    private static ApplicationContext applicationContext;

    @BeforeClass
    public static void createApplicationContext() throws Exception
    {
        applicationContext = new ClassPathXmlApplicationContext( getConfigLocations() );
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/EventsSpringBeans.xml" };
    }

    /**
     * Test of getEvent method, of class EventsServiceImpl.
     */
    @Test
    public void testGetEvent() {
    	EventsService service=(EventsService) getApplicationContext().getBean("eventService");
        System.out.println("getEvent");
        String campus = null;
        String categoryId = "8";
        String eventId = "17448-1202143";
//        EventsServiceImpl instance = new EventsServiceImpl();
        Event result = service.getEvent(campus, categoryId, eventId);
        System.out.println("----result---"+result);
        assertFalse("Failed to load event.", result != null);
        
    }

    /**
     * Test of getAllEvents method, of class EventsServiceImpl.
     */
    @Test
    public void testGetAllEvents() {
        System.out.println("getAllEvents");
        EventsServiceImpl service=(EventsServiceImpl) getApplicationContext().getBean("eventService");
        String campus = null;
        String categoryId = "4";
//        EventsServiceImpl instance = new EventsServiceImpl();
//        List expResult = null;
        List result = service.getAllEvents(campus, categoryId);
        System.out.println("--result--"+result.size());
        assertTrue( "Failed to load all events.", result != null && result.size() > 0 );
    }

    /**
     * Test of getCategoriesByCampus method, of class EventsServiceImpl.
     */
    @Test
    public void testGetCategoriesByCampus() {

        String campus = null;
        EventsServiceImpl instance = (EventsServiceImpl)getApplicationContext().getBean("eventService");
        List result = instance.getCategoriesByCampus(campus);
        System.out.println("------result size testGetCategoriesByCampus--- "+result.size());
//        assertTrue( "Failed to load categories.", result != null && result.size() > 0 );
    }
    
    @Test
    public void testgetEventsByDateRange(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 List result =service.getEventsByDateRange(null, "1990-01-01", "2013-01-01", "4");
    	 System.out.println("------result size testgetEventsByDateRange--- "+result.size());
         assertTrue( "Failed to load events.", result != null && result.size() > 0 );
    }
    
    @Test
    public void testgetNextday(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 String nextday=service.getNextDay("2013-01-01");
    	 System.out.println("------nextday--- "+nextday);
         assertTrue( "Failed to load nextday.", nextday!= null);
    }
    
    @Test
    public void testgetPreviousday(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 String previousDay=service.getPreviousDay("2013-01-01");
    	 System.out.println("------Previous--- "+previousDay);
         assertTrue( "Failed to load Previous day.", previousDay!= null);
    }
    
    @Test
    public void testgetEventsForDateRange(){
    	String fromdate="2014-06-12";
    	String todate= "2014-06-16";
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 List<EventImpl> result =service.getEventsForDateRange(fromdate, todate);
    	 System.out.println("------result size testgetEventsForDateRange--- "+result.size());
//         assertTrue( "Failed to load events.", result != null && result.size() > 0 );
    }
    
    /**
     * Test of getAllEvents method, of class EventsServiceImpl.
     */
    @Test
    public void testGetAllEvent() {
        System.out.println("getAllEvents");
        EventsServiceImpl service=(EventsServiceImpl) getApplicationContext().getBean("eventService");
        List result = service.getAllEvents();
        System.out.println("--result-testGetAllEvent-"+result.size());
//        assertTrue( "Failed to load all events.", result != null && result.size() > 0 );
    }

    @Test
    public void testgetEventsForCurrentDate(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 List<EventImpl> result =service.getEventsForCurrentDate("2013-01-01");
    	 System.out.println("------result size testgetEventsForCurrentDate--- "+result.size());
//         assertTrue( "Failed to load events.", result != null && result.size() > 0 );
    }
    
    @Test
    public void  getCategoryTest(){
    	 EventsServiceImpl instance = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 CategoryImpl categoryImpl=instance.getCategory(null, "4");
    	 assertTrue( "Failed to load category.", categoryImpl != null);
    }
    
    
    @Test
    public void testgetEventJson(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 String jsonString=service.getEventJson("17448-1202143");
    	 System.out.println("------jsonString--- "+jsonString);
         assertTrue( "Failed to load jsonString.", jsonString!= null);
    }
    
    @Test
    public void testgetAllEventsByDateCurrent(){
    	 Date currdate=new Date(1990,01, 01); 
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 List<EventImpl> result =service.getAllEventsByDateCurrent(null, "4", currdate);
    	 System.out.println("------result size testgetEventsForCurrentDate--- "+result.size());
//         assertTrue( "Failed to load events.", result != null && result.size() > 0 );
    }

    @Test
    public void testgetAllEventsByDateSpecific(){
    	 EventsServiceImpl service = (EventsServiceImpl)getApplicationContext().getBean("eventService");
    	 List<EventImpl> result =service.getAllEventsByDateSpecific(null, "4", "2013-01-01");
    	 System.out.println("------result size testgetEventsForCurrentDate--- "+result.size());
//         assertTrue( "Failed to load events.", result != null && result.size() > 0 );
    }
    
    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @param applicationContext the applicationContext to set
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        EventsServiceImplTest.applicationContext = applicationContext;
    }

}
