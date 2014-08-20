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

package org.kuali.mobility.academics.controller;

import static org.junit.Assert.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.*;
import org.kuali.mobility.academics.controllers.AcademicsControllerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.ui.ExtendedModelMap;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class AcademicsControllerImplTest {
    private static final Logger LOG = LoggerFactory.getLogger( AcademicsControllerImplTest.class );

    private static ApplicationContext applicationContext;

    public AcademicsControllerImplTest() {
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/AcademicsSpringBeans.xml" };
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        AcademicsControllerImplTest.setApplicationContext( new FileSystemXmlApplicationContext( getConfigLocations() ));
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
//    @Test
//    public void testgetSections() {
//    	
//    	/*  query.put( AcademicsConstants.TERM_ID, "1920" );
//        query.put( AcademicsConstants.COURSE_ID, "020607" );
//        query.put( AcademicsConstants.COURSE_OFFER_NBR, "1");
//        query.put( AcademicsConstants.CLASS_NUBER, "14589");
//        query.put( AcademicsConstants.SEARCH_MODE, "N");*/
//        assertTrue( "Failed to find application context.", null != getApplicationContext() );
//        AcademicsControllerImpl controller = (AcademicsControllerImpl)getApplicationContext().getBean("academicsController");
//        String termId = "1920";
//        String subjectId = "MUSED";
//        String catalogNumber = "203";
//        String careerId = "UMUS";
//        String viewName = controller.getSections(null,new ExtendedModelMap(),termId, subjectId, catalogNumber, careerId);
//        LOG.debug(viewName);
//        assertTrue( "Failed to find view.", "academics/sections".equalsIgnoreCase(viewName));
//    }

//    @Test
//    public void test() {
//        fail("Not implemented yet.");
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
    public static void setApplicationContext(ApplicationContext newApplicationContext) {
        applicationContext = newApplicationContext;
    }
}
