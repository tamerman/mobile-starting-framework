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

package org.kuali.mobility.dining.controllers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class DiningControllerTest {

	private static final Logger LOG = LoggerFactory.getLogger(DiningControllerTest.class);

	@Autowired
	private static ApplicationContext applicationContext;

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

	@BeforeClass
	public static void setUpClass() throws Exception {
		DiningControllerTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/DiningSpringBeans.xml"};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

//    @Test
//    public void testGetJsonData() {
//        assertTrue( "Failed to find application context.", null != getApplicationContext() );
//        DiningController controller = (DiningController)getApplicationContext().getBean("diningController");
//
//        String jsonData = controller.getMenusJson( "Bursley Dining Hall", null);
//
//        LOG.debug( "JSON: "+jsonData );
//
//        assertTrue( "Failed to find json data.", jsonData != null && !jsonData.isEmpty() );
//
//        jsonData = controller.getMenusJson( "Bursley Dining Hall", "");
//
//        LOG.debug( "JSON: "+jsonData );
//
//        assertTrue( "Failed to find json data.", jsonData != null && !jsonData.isEmpty() );
//    }
}
