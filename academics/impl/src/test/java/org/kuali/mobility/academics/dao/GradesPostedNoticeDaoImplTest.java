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

package org.kuali.mobility.academics.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.academics.entity.GradesPostedNotice;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class GradesPostedNoticeDaoImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(GradesPostedNoticeDaoImplTest.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Resource(name="academicsGradesPostedNoticeDao")
    private GradesPostedNoticeDao dao;

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
	public void testSaveGradesPostedNotice() {
		GradesPostedNotice notice = new GradesPostedNotice();
		notice.setLoginName("mojojojo");
		notice.setInProcess(false);
		notice.setTimestampProcessed(null);
		notice.setTimestampReceived(new Timestamp(Calendar.getInstance().getTimeInMillis()));

		Long id = getDao().saveGradesPostedNotice(notice);
		LOG.debug("ID for saved notice is "+id);
		assertFalse("Failed to save notice.",id==null);
		assertTrue("ID does not match that of the object.",id.compareTo(notice.getId()) == 0);

		GradesPostedNotice notice2 = getDao().loadGradesPostedNotice(id);
		assertFalse("Notice not found in database and should have been.",notice2==null);
		assertFalse("Notice is in process and should not be.",notice2.isInProcess());
		assertTrue("Notice has wrong login name.","mojojojo".equalsIgnoreCase(notice2.getLoginName()));

		notice2.setInProcess(true);
		Long id2 = getDao().saveGradesPostedNotice(notice2);

		assertTrue("IDs do not match after update.",id.compareTo(id2)==0);
	}

	@Test
	public void testCountUnsentNotices() {
		Long count = getDao().countUnsentGradeNotices();
		LOG.debug("Count of unsent messages is "+count.toString());
		assertFalse("Count is null and should not be.",count==null);
	}

    /**
     * Test of uploadGradesPostedNotice method, of class GradesPostedNoticeDaoImpl.
     */
    @Test
    public void testUploadGradesPostedNotice() {
        List<String> uniqnames = new ArrayList<String>();
        uniqnames.add("akedar");
        uniqnames.add("amarsh");
        uniqnames.add("joe");
        uniqnames.add("mark");

        boolean result = getDao().uploadGradesPostedNotice(uniqnames);
        assertTrue("Apply grades upload notices successfully.", result);

	    List<? extends GradesPostedNotice> notices = getDao().getGradesToProcess(true);
		assertFalse("Dao returned null for getGradesToProcess:true.", notices == null);
	    assertFalse("Dao returned empty list for getGradesToProcess:true.", notices.isEmpty());
	    assertTrue(notices.size()+" notices found, should have found 6. see import.sql",notices.size()==6);


    }

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public GradesPostedNoticeDao getDao() {
		return dao;
	}

	public void setDao(GradesPostedNoticeDao dao) {
		this.dao = dao;
	}

}
