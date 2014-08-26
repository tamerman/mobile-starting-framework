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

package org.kuali.mobility.tours.service;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ToursServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(ToursServiceImplTest.class);

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ToursServiceImplTest.applicationContext = applicationContext;
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		ToursServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/ToursSpringBeans.xml"};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Test
	public void testFindAllTours() {
		ToursServiceImpl service = (ToursServiceImpl) getApplicationContext().getBean("toursService");
		//assertTrue("found no tours", service.findAllTours().size() > 0);
	}

	@Test
	public void testCreateTourKml() {

	}
}
