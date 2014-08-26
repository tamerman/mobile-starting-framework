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

package org.kuali.mobility.people.service;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.SearchCriteria;
import org.kuali.mobility.people.entity.SearchResult;
import org.kuali.mobility.people.entity.SearchResultImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for the <code>DirectoryServiceImpl</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class DirectoryServiceImplTest {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DirectoryServiceImplTest.class);

	/**
	 * Reference to the test application context.
	 */
	private static ApplicationContext applicationContext;

	@BeforeClass
	public static void setUpClass() throws Exception {
		DirectoryServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
	}

	private static String[] getConfigLocations() {
		return new String[]{"classpath:/DirectorySpringBeans.xml"};
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

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

	@Test
	public void testLookupPerson() {
		DirectoryService service = (DirectoryService) getApplicationContext().getBean("directoryService");

		Person person = service.personLookup("joseswan");

		LOG.debug("Person object " + (null == person ? "is" : "is not") + " null");

		assertTrue("Could not find joseswan.", person != null || person.getUserName().isEmpty());
	}

	@Test
	public void testPersonSearch() {

		DirectoryService service = (DirectoryService) getApplicationContext().getBean("directoryService");
		List<Person> personlist = (List<Person>) service.personSearch("a", "a", "a", "a", "", "a", "a");
		LOG.debug("Person List object " + (null == personlist ? "is" : "is not") + " null");
		assertTrue("Could not find list.", personlist != null && personlist.size() > 0);

	}

	@Test
	public void testFindSimpleGroup() {
		DirectoryService service = (DirectoryService) getApplicationContext().getBean("directoryService");
		List<Group> groupList = (List<Group>) service.findSimpleGroup("cn=ITS Android Dev,ou=User Groups,ou=Groups,dc=umich,dc=edu");
		LOG.debug("Group List object " + (null == groupList ? "is" : "is not") + " null");
//        assertTrue( "Could not find list.", groupList != null && groupList.size() > 0);
	}

	@Test
	public void testFindEntries() {
		DirectoryService service = (DirectoryService) getApplicationContext().getBean("directoryService");
		SearchCriteria searchCriteria = new SearchCriteria();
//        searchCriteria = new SearchCriteria();
		searchCriteria.setExactness("");
		searchCriteria.setFirstName("a");
		searchCriteria.setLastName("a");
		searchCriteria.setLocation("a");
		searchCriteria.setSearchText("a");
		searchCriteria.setStatus("a");
		searchCriteria.setUserName("a");
		SearchResult result = service.findEntries(searchCriteria);
		LOG.debug("SearchResult  object " + (null == result ? "is" : "is not") + " null");
		assertTrue("Could not find list.", result != null || result.getSearchCriteria().isExactLastName());
	}

	@Test
	public void testLookupGroup() {
		DirectoryService service = (DirectoryService) getApplicationContext().getBean("directoryService");

		Group group = service.groupLookup("cn=ITS Android Dev,ou=User Groups,ou=Groups,dc=umich,dc=edu");

		LOG.debug("Group object " + (null == group ? "is" : "is not") + " null");

		assertTrue("Could not find ITS Android Dev.", group != null || group.getDisplayName().isEmpty());
	}
}

