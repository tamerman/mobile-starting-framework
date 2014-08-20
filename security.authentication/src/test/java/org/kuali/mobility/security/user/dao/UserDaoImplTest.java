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

package org.kuali.mobility.security.user.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class UserDaoImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(UserDaoImplTest.class);

	private static final String[] LOGIN_NAME = {null, "mojojojo", "fuzzy", "bubbles"};

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("kmeGroupDao")
	private GroupDao groupDao;

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
	public void loadUserById() {
		User user = getUserDao().loadUserById(new Long(2));
		assertTrue("User was not loaded.", user != null);
		LOG.debug("User ID [" + user.getId() + "]");
		LOG.debug("User LOGIN_NM [" + user.getLoginName() + "]");
		LOG.debug("User DISPLAY_NM [" + user.getDisplayName() + "]");
		LOG.debug("User FIRST_NM [" + user.getFirstName() + "]");
		LOG.debug("User LAST_NM [" + user.getLastName() + "]");
		LOG.debug("User EMAIL_ADDR_X [" + user.getEmail() + "]");
		assertTrue("Login name did not match expected.", LOGIN_NAME[2].equals(user.getLoginName()));
		assertTrue("User had no attributes, expected 2", null != user.getAttributes() && !user.getAttributes().isEmpty());

		List<Group> groups = user.getGroups();
		for (Group g : groups) {
			LOG.debug("Group found: [ID:" + g.getId() + "][Name:" + g.getName() + "].");
		}
		assertTrue("Groups were null for user and should not have been.", groups != null && !groups.isEmpty());
	}

	@Test
	public void loadUserByLoginName() {
		User user = getUserDao().loadUserByLoginName("fuzzy");
		assertFalse("Failed to find any users for specified login name.", user == null);
	}

	@Test
	public void testSaveUser() {
		String fName = "Bubbles";
		String lName = "Utonium";
		String displayName = "Bubbles Utonium";
		String email = "bubbles@powerpuff.com";
		String url = "http://www.powerpuff.com";
		String campus = "TOWNSVILLE";

		User user = new UserImpl();
		user.setLoginName(LOGIN_NAME[3]);
		user.setFirstName(fName);
		user.setLastName(lName);
		user.setDisplayName(displayName);
		user.setEmail(email);
		user.setRequestURL(url);
		user.setViewCampus(campus);

		user.addAttribute("ATTRIBUTE_A", "valueA");

		List<Group> groups = new ArrayList<Group>();
		user.setGroups(groups);

		Long id = getUserDao().saveUser(user);

		LOG.debug("User saved with id " + id);
		assertTrue("User failed to save, ID = null", null != id);
		assertTrue("User display name didn't match.", displayName.equals(user.getDisplayName()));
		assertTrue("User first name didn't match.", fName.equals(user.getFirstName()));
		assertTrue("User last name didn't match.", lName.equals(user.getLastName()));
		assertTrue("User login name didn't match.", LOGIN_NAME[3].equals(user.getLoginName()));
		assertTrue("User request url didn't match.", url.equals(user.getRequestURL()));
		assertTrue("User email didn't match.", email.equals(user.getEmail()));
		assertFalse("User is a member of the test group and shouldn't be.", user.isMember("Rowdy Rough Boys"));
		assertFalse("User is a public user and shouldn't be.", user.isPublicUser());
		assertTrue("Attribute A was not found and should have been.", user.getAttribute("ATTRIBUTE_A").size() == 1);
		assertTrue("Attribute B was found and should not have been.", user.getAttribute("ATTRIBUTE_B").size() == 0);
		assertTrue("Found more attribute names than expected.", user.getAttributeNames().size() == 1);
	}

	@Test
	public void testUpdateUser() {
		User user = getUserDao().loadUserById(new Long(1));
		assertTrue("User was not loaded.", user != null);
		assertTrue("Login name did not match expected.", LOGIN_NAME[1].equals(user.getLoginName()));

		Group group = getGroupDao().getGroup(new Long(2));
		user.addGroup(group);

		user.addAttribute("ATTRIBUTE_Z", "valueZ");

		Long id = getUserDao().saveUser(user);

		assertTrue("Id changed on update and shouldn't have.", user.getId().compareTo(id) == 0);
	}

	@Test
	public void testSaveNullUser() {
		Long id = getUserDao().saveUser(null);
		assertTrue("Saved user and shouldn't have.", id == null);
	}

	@Test
	public void testRemoveUserAttribute() {
		Long oldId = new Long(2);
		User user = getUserDao().loadUserById(oldId);
		assertTrue("User was not loaded.", user != null);
		LOG.debug("User has [" + user.getAttributes().size() + "] attributes.");
		boolean didRemove = user.removeAttribute(null);
		assertFalse("removeAttribute returned true for null argument.", didRemove);
		assertTrue("User should have 2 attributes and does not.", user.getAttributes().size() == 2);
		user.removeAttribute("attributeZ");
		assertTrue("Removed an attribute that didn't exist.", user.getAttributes().size() == 2);
		didRemove = user.removeAttribute("ATTRIBUTE_D");
		assertTrue("removeAttribute returned false for attribute d.", didRemove);
		assertTrue("Failed to remove attribute d.", user.getAttributes().size() == 1);

		Long newId = getUserDao().saveUser(user);
		assertTrue("Failed to properly save user after removing attributes.", oldId.compareTo(newId) == 0);

		User user2 = getUserDao().loadUserById(oldId);
		assertTrue("User loaded from database has wrong number of attributes. Expected 1, found "+user2.getAttributes().size(), user2.getAttributes().size() == 1);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
