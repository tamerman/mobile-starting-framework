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

package org.kuali.mobility.security.group.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class GroupDaoImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(GroupDaoImplTest.class);

	private static final String[] GROUP_NAME = {"KME-ADMINISTRATORS", "KME-BACKDOOR", "TEST-GROUP"};

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("kmeGroupDao")
	private GroupDao dao;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
	}

	@Test
	public void testGetGroupById() {
		Group group1 = getDao().getGroup(new Long(1));
		assertTrue("Group is not null.", group1 != null);
		assertTrue("Group name does not match expected one.", GROUP_NAME[0].equals(group1.getName()));
	}

	@Test
	public void testGetGroupByName() {
		Group group2 = getDao().getGroup(GROUP_NAME[1]);
		assertTrue("Group is not null.", group2 != null);
		assertTrue("Group name does not match expected one.", GROUP_NAME[1].equals(group2.getName()));
	}

	@Test
	public void testSaveGroup() {
		Group group3 = new GroupImpl();
		group3.setName(GROUP_NAME[2]);
		group3.setDescription("Test description.");
		assertTrue("Group id is null.", group3.getId() == null);
		Long id = getDao().saveGroup(group3);
		assertFalse("Group id is null and should not be.", id == null);
	}

	@Test
	public void testSaveNullGroup() {
		Long id = getDao().saveGroup(null);
		assertTrue("Saved a null group. How did that happen?", id == null);
	}

	@Test
	public void testUpdateGroup() {
		Group group = getDao().getGroup(new Long(1));
		assertTrue("Group is not null.", group != null);
		group.setDescription("Default Kuali Mobility Administrator Group TEST");
		Long id = getDao().saveGroup(group);
		assertTrue("Failed to update the group description", id != null && group.getId().compareTo(id) == 0);
	}

	@Test
	public void testGroupEquals() {
		Group[] groups = new GroupImpl[2];
		groups[0] = getDao().getGroup(new Long(2));
		groups[1] = getDao().getGroup(new Long(2));
		assertTrue("Groups are not equal and should be.", groups[0].equals(groups[1]));
		assertTrue("Group is not equal to itself.", groups[0].equals(groups[0]));
		assertFalse("Groups are equal to null and shouldn't be.", groups[1].equals(null));
		assertFalse("Group is equal to a String? Shouldn't be.", groups[0].equals(GROUP_NAME[1]));
	}

	public GroupDao getDao() {
		return dao;
	}

	public void setDao(GroupDao dao) {
		this.dao = dao;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
