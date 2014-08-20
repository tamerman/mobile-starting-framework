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

package org.kuali.mobility.auth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.kuali.mobility.auth.AuthBootListener;
import org.kuali.mobility.auth.entity.AuthResponse;
import org.kuali.mobility.security.user.api.UserDao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the AuthDao.
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AuthDaoImplTest {
    private static final Logger LOG = LoggerFactory.getLogger( AuthDaoImplTest.class );

    private static final String PEPPER = "BlossomBubblesButtercup";
    private static final String USER_NM = "mojojojo";
    private static final String PASSWORD_X = "mojojojo";
    private static final String PASSWORD_HASH = "bee780b494e10ef9062bde69a00fb5b3b3e13d0e";

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    @Resource(name="kmeUserDao")
    private UserDao userDao;

    @Resource(name="authDao")
    private AuthDaoImpl dao;

    @Resource(name="authBootBean")
    private AuthBootListener authBootListener;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        getAuthBootListener().contextInitialized(null);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAuthenticate() {
        AuthResponse response = getDao().authenticate(USER_NM,PASSWORD_X);

        assertTrue("Failed to authenticate.",response.didAuthenticate());
        assertFalse("Failed to find user in response.", response.getUser() == null);
        assertTrue("Failed to find proper display name.","Mojo Jojo".equals(response.getUser().getDisplayName()));
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public AuthDaoImpl getDao() {
        return dao;
    }

    public void setDao(AuthDaoImpl dao) {
        this.dao = dao;
    }

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }

    public AuthBootListener getAuthBootListener() {
        return authBootListener;
    }

    public void setAuthBootListener(AuthBootListener authBootListener) {
        this.authBootListener = authBootListener;
    }
}
