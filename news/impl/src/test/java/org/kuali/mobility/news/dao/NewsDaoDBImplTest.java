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

package org.kuali.mobility.news.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsDaoDBImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(NewsDaoDBImplTest.class);

	private static ApplicationContext applicationContext;


	private static String[] getConfigLocations() {
		return new String[]{"classpath:/SpringBeans.xml"};
	}

	@BeforeClass
	public static void setUpClass() throws Exception {
		NewsDaoDBImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
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

	@Test
	public void testFindAllActiveNewsSources() {
//        NewsDao dao = (NewsDao)getApplicationContext().getBean("newsDaoDB");
//        List<NewsSource> sources = (List<NewsSource>)dao.findAllActiveNewsSources();
//        assertTrue("Failed to find news sources.", sources != null && sources.size() > 0);
	}

	@Test
	public void testFindAllNewsSources() {
//        NewsDao dao = (NewsDao) getApplicationContext().getBean("newsDaoDB");
//        List<NewsSource> sources = (List<NewsSource>)dao.findAllNewsSources();
//        assertTrue("Failed to find news sources.", sources != null && sources.size() > 0);
	}

	@Test
	public void testLookup() throws Exception {
//        NewsDao dao = (NewsDao) getApplicationContext().getBean("newsDaoDB");
//        NewsSource source = dao.lookup(new Long(2));
//        assertTrue("Failed to find news source.", source != null && "BBC - News".equalsIgnoreCase(source.getName()));

	}

	@Test
	public void testSave() {
//        NewsDao dao = (NewsDao) getApplicationContext().getBean("newsDaoDB");
//        NewsSource source = dao.save(new NewsSourceDBImpl());
//        assertTrue("Failed to save", source != null);
	}

	@Test
	public void testDelete() {
//        NewsDao dao = (NewsDao) getApplicationContext().getBean("newsDaoDB");
//        NewsSource source = dao.delete(new NewsSourceDBImpl());
//        assertTrue("Failed to save", source != null);
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
		applicationContext = applicationContext;
	}
}
