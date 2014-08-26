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

package org.kuali.mobility.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the AuthDao.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AuthBootListenerTest {
	private static final Logger LOG = LoggerFactory.getLogger(AuthBootListenerTest.class);

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@Resource(name = "kmeUserDao")
	private UserDao userDao;

	@Resource(name = "kmeGroupDao")
	private GroupDao groupDao;

	@Resource(name = "authBootBean")
	private AuthBootListener authBootListener;

	@Before
	public void setUp() {
		getAuthBootListener().contextInitialized(null);
	}

	@Test
	public void DataLoadTest() {
		User user = getUserDao().loadUserById(new Long(2));
		assertFalse("User is null and shouldn't be.", user == null);
		assertTrue("User loaded has wrong ID.", user.getId().equals(new Long(2)));
		assertTrue("User has the wrong login name.", "fuzzy".equals(user.getLoginName()));

		user = getUserDao().loadUserByLoginName("mojojojo");
		assertFalse("User is null and shouldn't be.", user == null);
		assertTrue("User loaded has wrong ID.", user.getId().equals(new Long(1)));
		assertTrue("User has the wrong login name.", "mojojojo".equals(user.getLoginName()));
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
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

	public AuthBootListener getAuthBootListener() {
		return authBootListener;
	}

	public void setAuthBootListener(AuthBootListener authBootListener) {
		this.authBootListener = authBootListener;
	}
}
