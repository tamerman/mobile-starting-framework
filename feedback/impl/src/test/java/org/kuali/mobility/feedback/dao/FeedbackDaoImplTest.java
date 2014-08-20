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

package org.kuali.mobility.feedback.dao;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.feedback.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class FeedbackDaoImplTest {

	private static final Logger LOG = LoggerFactory.getLogger(FeedbackDaoImplTest.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FeedbackDao dao;

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
    public void testFeedbackTransactions() {
    	LOG.debug("Performing Test 1");
    	Feedback f1 = new Feedback();
    	f1.setVersionNumber(new Long(1));
    	f1.setUserId("someone");
    	f1.setUserAgent("mobile");
    	f1.setService("bus");
    	f1.setPostedTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	f1.setNoteText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tempus massa et dapibus ullamcorper. Donec porta adipiscing dui vitae ullamcorper.");
    	f1.setEmail("someone@somedomain.com");
    	f1.setDeviceType("mobile");
    	f1.setCampus("ALL");
    	f1.setAffiliation("Vivantech");
    	
    	assertTrue("Feedback 1 ID is not null.",f1.getFeedbackId()==null);
    	
    	getDao().saveFeedback(f1);

    	Long f1ID = f1.getFeedbackId();
    	
        LOG.debug("Feedback 1 ID is: "+f1ID);
        assertTrue("Feedback 1 was not saved.", f1ID != null);

        Feedback lookupFeedback1 = getDao().findFeedbackById(f1ID);
        assertNotNull("Failed to find feedback by ID.",lookupFeedback1);
        assertEquals("Failed to find expected saved affiliation from the DB.","Vivantech",lookupFeedback1.getAffiliation());
        assertEquals("Failed to find expected saved user id from the DB.","someone",lookupFeedback1.getUserId());

        LOG.debug("Performing Test 2");
    	Feedback f2 = new Feedback();
    	f2.setVersionNumber(new Long(1));
    	f2.setUserId("someone");
    	f2.setUserAgent("mobile");
    	f2.setService("dining");
    	f2.setPostedTimestamp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    	f2.setNoteText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tempus massa et dapibus ullamcorper. Donec porta adipiscing dui vitae ullamcorper.");
    	f2.setEmail("someone@somedomain.com");
    	f2.setDeviceType("mobile");
    	f2.setCampus("ALL");
    	f2.setAffiliation("Vivantech");
    	
    	assertTrue("Feedback 2 ID is not null.",f2.getFeedbackId()==null);
    	
    	getDao().saveFeedback(f2);
    	
    	Long f2ID = f2.getFeedbackId();

        LOG.debug("Feedback 2 ID is: "+ f2ID);
        assertTrue("Feedback 2 was not saved.", f2ID != null);
        
        f2.setCampus("BL");
        
        getDao().saveFeedback(f2);
        
        Long f2ID2 = f2.getFeedbackId(); 
        
        assertTrue("Feedback 2 updated but inserted a new row.",f2ID.compareTo(f2ID2) == 0);
        
        LOG.debug("Performing Test 3");
        Feedback f3 = null;
        
        getDao().saveFeedback(f3);
        
        assertTrue("Feedback 3 was null but saved", f3 == null);        
    	
    }

	public FeedbackDao getDao() {
		return dao;
	}

	public void setDao(FeedbackDao dao) {
		this.dao = dao;
	}

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
