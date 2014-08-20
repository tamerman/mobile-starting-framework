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

package org.kuali.mobility.emergencyinfo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
* @author Kuali Mobility Team (mobility.collab@kuali.org)
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class EmergencyInfoWSDaoImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(EmergencyInfoWSDaoImplTest.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    @Qualifier("emergencyInfoWSDao")
    private EmergencyInfoDao dao;

    @Test
    public void testFindAllEmergencyInfo() {
        List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfo();
        assertFalse("Null response from findAllEmergencyInfo",info==null);
    }

    @Test
    public void testFindEmergencyInfoByCampusWithNull() {
        List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus(null);
        assertTrue("Found info for null campus and shouldn't have.",info==null||info.isEmpty());
    }

    @Test
    public void testFindEmergencyInfoByCampusWithBadCampus() {
        List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus("BAD_CAMPUS");
        assertTrue("Found info for bad campus and shouldn't have.",info==null||info.isEmpty());
    }

    @Test
    public void testFindEmergencyInfoByCampus() {
        List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus("ALL");
        assertTrue("Found Emergency info for specified campus.",info!=null||!info.isEmpty());
    }

    public EmergencyInfoDao getDao() {
        return dao;
    }

    public void setDao(EmergencyInfoDao dao) {
        this.dao = dao;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
