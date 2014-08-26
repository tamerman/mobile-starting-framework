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

package org.kuali.mobility.academics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class AcademicsServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(AcademicsServiceImplTest.class);

	private static ApplicationContext applicationContext;

	public AcademicsServiceImplTest() {
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/AcademicsSpringBeans.xml"};
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		AcademicsServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
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
//    public void testGetTerms() {
//        AcademicsServiceImpl service = (AcademicsServiceImpl) getApplicationContext().getBean("academicsService");
//
//        List<Term> terms = service.getTerms();
//
//        LOG.debug( "Terms is "+terms.size()+" items long." );
//        assertTrue( "Could not get terms.", terms != null && !terms.isEmpty() );
//    }

//    @Test
//    public void testGetSections() {
//        AcademicsServiceImpl service = (AcademicsServiceImpl) getApplicationContext().getBean("academicsService");
//
//        LOG.debug( "Entering testGetSections." );
//        List<Section> sections = service.getSections("1920", "MATH", "147", "LSA");
//        
//        assertTrue( "Could not get sections.", sections != null && !sections.isEmpty() );
//        LOG.debug( "Leaving testGetSections." );
//    }
//    
//    @Test
//    public void testSearchResults() {
//        AcademicsServiceImpl service = (AcademicsServiceImpl) getApplicationContext().getBean("academicsService");
//
//        List<Section> sections = service.getSearchResults("1920", null, "conduct", null, null, null, null);
//
//        LOG.debug( "Search results is "+sections.size()+" items long." );
//        assertTrue( "Could not get search results.", sections != null && !sections.isEmpty() );
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
