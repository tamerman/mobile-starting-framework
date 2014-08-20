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

package org.kuali.mobility.academics.service;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.api.UserAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
/**
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AcademicsAuthServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(AcademicsAuthServiceImplTest.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("academicsAuthService")
	private AcademicsAuthServiceImpl academicsAuthService;

	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;

	private MessageContext messageContext;
	private MockHttpServletRequest httpServletRequest;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		setHttpServletRequest(new MockHttpServletRequest());
		setMessageContext(mock(MessageContext.class));
		getAcademicsAuthService().setMessageContext(this.getMessageContext());
		when(getMessageContext().getHttpServletRequest()).thenReturn(this.getHttpServletRequest());
	}

	@Test
    @DirtiesContext
	public void testGradeAlertOptIn() {
		getHttpServletRequest().setRemoteUser("mojojojo");
        HttpSession session = getHttpServletRequest().getSession();
        session.setAttribute(AuthenticationConstants.DEVICE_ID,"deviceIdB");
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
		assertTrue("Failed to opt in for grade alerts.",optIn);
		User user = getUserDao().loadUserByLoginName("mojojojo");
		assertFalse("Failed to find user in database.",user==null);
		List<UserAttribute> attributes = user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
		assertFalse("Attribute not found.",attributes==null);
		assertFalse("Attribute not found.",attributes.isEmpty());
		assertTrue("More than one attribute for grade alert opt in found.",attributes.size()==1);
		assertTrue("Failed to save opt in status.","on".equalsIgnoreCase(attributes.get(0).getAttributeValue()));
	}

	@Test
    @DirtiesContext
	public void testGradeAlertOptOut() {
		getHttpServletRequest().setRemoteUser("blossom");
        HttpSession session = getHttpServletRequest().getSession();
        session.setAttribute(AuthenticationConstants.DEVICE_ID,"AbCdEfGhIjKlMnOpQrStUvWxYz");
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("OFF");
		assertTrue("Failed to opt in for grade alerts.",optIn);
		User user = getUserDao().loadUserByLoginName("blossom");
		assertFalse("Failed to find user in database.",user==null);
		List<UserAttribute> attributes = user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
		assertFalse("Attribute not found.",attributes==null);
		assertFalse("Attribute not found.",attributes.isEmpty());
		LOG.debug("====== Found "+attributes.size()+" attributes for grade alerts.");
		assertTrue("More than one attribute for grade alert opt in found.",attributes.size()==1);
		for( UserAttribute attribute : attributes ) {
			LOG.debug("====== Attribute Name ["+attribute.getAttributeName()+"] Value ["+attribute.getAttributeValue()+"]");
		}
		assertTrue("Failed to save opt in status.","off".equalsIgnoreCase(attributes.get(0).getAttributeValue()));
	}

	@Test
    @DirtiesContext
	public void testGradeAlertWithNullRemoteUser() {
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
		assertFalse("Opt in succeeded and shouldn't have.",optIn);
	}

	@Test
    @DirtiesContext
	public void testGradeAlertWithEmptyRemoteUser() {
		getHttpServletRequest().setRemoteUser("");
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
		assertFalse("Opt in succeeded and shouldn't have.",optIn);
	}

	@Test
    @DirtiesContext
	public void testGradeAlertWithBadUser() {
		getHttpServletRequest().setRemoteUser("utonium");
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
		assertFalse("Opt in succeeded and shouldn't have.",optIn);
	}

	@Test
    @DirtiesContext
	public void testGradeAlertWithNullRequest() {
		when(getMessageContext().getHttpServletRequest()).thenReturn(null);
		boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
		assertFalse("Opt in succeeded and shouldn't have.",optIn);
	}

    @Test
    @DirtiesContext
    public void testGradeAlertWithNullSession() {
        getHttpServletRequest().setRemoteUser("blossom");
        getHttpServletRequest().setSession(null);
        boolean optIn = getAcademicsAuthService().updateGradeAlertOpt("ON");
        assertTrue("Opt in succeeded.",optIn);
    }

    public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public AcademicsAuthServiceImpl getAcademicsAuthService() {
		return academicsAuthService;
	}

	public void setAcademicsAuthService(AcademicsAuthServiceImpl academicsAuthService) {
		this.academicsAuthService = academicsAuthService;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	public MockHttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public void setHttpServletRequest(MockHttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
