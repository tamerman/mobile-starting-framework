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

package org.kuali.mobility.reporting.dao;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.reporting.entity.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit="mdot")
public class ReportingDaoImplTest {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingDaoImplTest.class);

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    private ReportingDao dao;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void preTest() {
        setDao(new ReportingDaoImpl());
        JpaUnitils.injectEntityManagerInto(getDao());
    }


    @Test
    @Transactional(TransactionMode.ROLLBACK)
    public void testReportingTransaction() throws Exception {

        java.util.Date date= new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Submission submission1 = new Submission();
        submission1.setParentId(new Long(1));
        submission1.setRevisionNumber(new Long(123));
        submission1.setRevisionUserId("user1");
        submission1.setName("Nurul");
        submission1.setType("TYPE1");
        submission1.setGroup("KME");
        submission1.setActive(10);
        submission1.setPostDate(timestamp);
        submission1.setArchivedDate(timestamp);
        submission1.setIpAddress("192.168.54.32");
        submission1.setUserAgent("Chrome");
        submission1.setUserId("nurul1988");
        submission1.setVersionNumber(new Long(12345));

        assertTrue("ID is not null.",submission1.getId()==null);

        Long id1 = dao.saveSubmission(submission1);
        LOG.debug("Submission 1 ID is: "+id1);
        assertTrue("Failed to save Submission1", id1 != null);

        Submission submission2 = new Submission();
        submission2.setParentId(new Long(1));
        submission2.setRevisionNumber(new Long(234));
        submission2.setRevisionUserId("user2");
        submission2.setName("Joe");
        submission2.setGroup("KME");
        submission2.setActive(15);
        submission2.setPostDate(timestamp);
        submission2.setArchivedDate(timestamp);
        submission2.setIpAddress("192.168.54.28");
        submission2.setUserAgent("Chrome");
        submission2.setUserId("joeswan");
        submission2.setVersionNumber(new Long(23546));

        assertTrue("ID is not null.",submission2.getId()==null);

        Long id2 = dao.saveSubmission(submission2);
        assertTrue("Failed to save Submission2", id2 != null);

        submission2.setType("TYPE2");

        Long id2s = dao.saveSubmission(submission2);

        assertTrue("Submission 2 updated but inserted a new row.",id2.compareTo(id2s) == 0);

        List<Submission>submissionList1 = dao.findAllSubmissions();
        assertTrue("No Submission found.",submissionList1!=null && submissionList1.size() > 0);
        assertTrue("Expected 2 Submissions, found "+submissionList1.size(),submissionList1.size() == 2);

        Submission submission3 = dao.findSubmissionById(submission2.getId());
        assertTrue("Failed to find submission by ID",submission3 != null);
        assertTrue("Submission found for ID "+submission2.getId()+" but objects are not equal.",submission2.equals(submission3));


        List<Submission> submissionList2 = dao.findAllSubmissionsByParentId(new Long(1));
        assertTrue("Failed to find submission by parent ID",submissionList2 != null && submissionList2.size() > 0);
        assertTrue("Expected 2 Submissions, found "+submissionList2.size(),submissionList2.size() == 2);
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public ReportingDao getDao() {
        return dao;
    }

    public void setDao(ReportingDao dao) {
        this.dao = dao;
    }
}
