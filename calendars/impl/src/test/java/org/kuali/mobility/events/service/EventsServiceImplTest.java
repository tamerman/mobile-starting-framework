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

import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        return new String[] { "/SpringBeans.xml","/webmvc-config.xml" };
    }

    /**
     * Test of getEvent method, of class EventsServiceImpl.
     */
//    @Test
//    public void testGetEvent() {
//        System.out.println("getEvent");
//        String campus = "";
//        String categoryId = "";
//        String eventId = "";
//        EventsServiceImpl instance = new EventsServiceImpl();
//        Event expResult = null;
//        Event result = instance.getEvent(campus, categoryId, eventId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getAllEvents method, of class EventsServiceImpl.
//     */
//    @Test
//    public void testGetAllEvents() {
//        System.out.println("getAllEvents");
//        String campus = "";
//        String categoryId = "";
//        EventsServiceImpl instance = new EventsServiceImpl();
//        List expResult = null;
//        List result = instance.getAllEvents(campus, categoryId);
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getCategoriesByCampus method, of class EventsServiceImpl.
//     */
//    @Test
//    public void testGetCategoriesByCampus() {
//
//        String campus = null;
//        EventsServiceImpl instance = (EventsServiceImpl)getApplicationContext().getBean("eventService");
//        List result = instance.getCategoriesByCampus(campus);
//        assertTrue( "Failed to load categories.", result != null && result.size() > 0 );
//    }

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
