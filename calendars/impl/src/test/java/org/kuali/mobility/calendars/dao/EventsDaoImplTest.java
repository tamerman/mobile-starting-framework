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

package org.kuali.mobility.calendars.dao;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author joseswan
 */
public class EventsDaoImplTest {

	private static ApplicationContext applicationContext;

	@BeforeClass
	public static void createApplicationContext() throws Exception {
		setApplicationContext(new ClassPathXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/SpringBeans.xml", "classpath:/webmvc-config.xml"};
	}

	/**
	 * Test of initData method, of class EventsDaoImpl.
	 */
	@Test
	public void testInitData_3args() {
	}

	/**
	 * Test of initData method, of class EventsDaoImpl.
	 */
//    @Test
//    public void testInitData_String_String() {
//        EventsDao dao = (EventsDao)getApplicationContext().getBean("eventDao");
////        dao.initData( null );
//
//        int eventCount = 0;
//
////        dao.initData( null, "14" );
////
////        assertTrue( "Failed to load events.", dao.getEvents() != null && dao.getEvents().size() > 0 );
////
////        eventCount = dao.getEvents().size();
////        System.out.append( "Events list length: " + eventCount );
//        dao.initData( null, "8" );
//
//        assertTrue( "Failed to load events1.", dao.getEvents() != null && dao.getEvents().size() > eventCount );
//
//        eventCount = dao.getEvents().size();
//        System.out.println( "Events list length: " + eventCount );
//
//        dao.initData( null, "8" );
//
//        assertTrue( "Failed to load events2.", dao.getEvents() != null && dao.getEvents().size() > 0 );
//        assertTrue( "Event list improperly removed duplicates.", dao.getEvents().size() == eventCount );
//
//        eventCount = dao.getEvents().size();
//        System.out.println( "Events list length: " + eventCount );
//    }
//
//    /**
//     * Test of initData method, of class EventsDaoImpl.
//     */
//    @Test
//    public void testInitData_String() {
//        EventsDaoImpl dao = (EventsDaoImpl)getApplicationContext().getBean("eventDao");
//
//        dao.initData( null );
//
//        assertTrue( "Failed to load categories.", dao.getCategories() != null && dao.getCategories().size() > 0 );
//    }

	/**
	 * @return the applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param aApplicationContext the applicationContext to set
	 */
	public static void setApplicationContext(ApplicationContext aApplicationContext) {
		applicationContext = aApplicationContext;
	}

}
