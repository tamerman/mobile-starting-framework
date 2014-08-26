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

package org.kuali.mobility.emergencyinfo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoJPAImpl;
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
public class EmergencyInfoDaoImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(EmergencyInfoDaoImplTest.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("emergencyInfoDao")
	private EmergencyInfoDao dao;

	@Test
	public void testFindAllEmergencyInfo() {
		List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfo();
		assertFalse("Null response from findAllEmergencyInfo", info == null);
		assertFalse("Zero responses from findAllEmergencyInfo", info.isEmpty());
	}

	@Test
	public void testFindByIdWithNull() {
		EmergencyInfo ei = getDao().findEmergencyInfoById(null);
		assertTrue("Found emergency info and should not have.", ei == null);
	}

	@Test
	public void testFindByIdWithBadId() {
		EmergencyInfo ei = getDao().findEmergencyInfoById(new Long(42));
		assertTrue("Found emergency info and should not have.", ei == null);
	}

	@Test
	public void testFindById() {
		EmergencyInfo ei = getDao().findEmergencyInfoById(new Long(3));
		assertFalse("Failed to find emergency info.", ei == null);
		assertTrue("Found emergency info but not the one expected.", ei.getTitle().equals("Ann Arbor Fire Department"));
	}

	@Test
	public void testFindByCampusWithNull() {
		List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus(null);
		assertTrue("Found info for null campus and shouldn't have.", info == null || info.isEmpty());
	}

	@Test
	public void testFindByCampusWithBadCampus() {
		List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus("BAD_CAMPUS");
		assertTrue("Found info for bad campus and shouldn't have.", info == null || info.isEmpty());
	}

	@Test
	public void testFindByCampus() {
		List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus("AA");
		assertFalse("Failed to find info for specified campus.", info == null || info.isEmpty());
		assertTrue("Three results expected and found " + info.size(), info.size() == 3);
	}

	@Test
	public void testDeleteByIdWithNull() {
		getDao().deleteEmergencyInfoById(null);
	}

	@Test
	public void testDeleteById() {
		getDao().deleteEmergencyInfoById(new Long(6));
		List<? extends EmergencyInfo> info = getDao().findAllEmergencyInfoByCampus("FL");
		assertTrue("Failed to delete item from database.", info != null && info.size() == 1);
	}

	@Test
	public void testSaveWithNull() {
		Long id = getDao().saveEmergencyInfo(null);
		assertTrue("Received an ID for saving a null object and shouldn't have.", id == null);
	}

	@Test
	public void testSaveWithNewObject() {
		EmergencyInfo info = new EmergencyInfoJPAImpl();
		info.setCampus("ABC");
		info.setLink("1-404-555-1212");
		info.setOrder(1);
		info.setTitle("ABC Emergency Info");
		info.setType("PHONE");
		assertTrue("ID is not null prior to save and should be.", info.getEmergencyInfoId() == null);
		Long id = getDao().saveEmergencyInfo(info);
		assertFalse("ID is null after save and should not be.", id == null);
		assertTrue("ID returned from method does not match that of the object.", id == info.getEmergencyInfoId());
	}

	@Test
	public void testSaveWithOldObject() {
		EmergencyInfo info = getDao().findEmergencyInfoById(new Long(5));
		assertFalse("Failed to find pre-loaded data.", info == null);
		info.setLink("911");
		Long oldId = info.getEmergencyInfoId();
		Long newId = getDao().saveEmergencyInfo(info);
		assertFalse("ID is null after save and should not be.", newId == null);
		assertTrue("ID returned from method does not match that of the object.", newId == info.getEmergencyInfoId());
		assertTrue("ID changed during update and should not have.", oldId == info.getEmergencyInfoId() && oldId == newId);
	}

	@Test
	public void testReorderUp() {
		Long id = new Long(2);
		getDao().reorder(id, true);
		EmergencyInfo info = getDao().findEmergencyInfoById(id);
		assertFalse("Failed to find info for id.", info == null);
		assertTrue("Failed to move the item at all.", info.getOrder() != 2);
		assertTrue("Failed to move the item up the list.", info.getOrder() < 2);
	}

	@Test
	public void testReorderDown() {
		Long id = new Long(3);
		getDao().reorder(id, false);
		EmergencyInfo info = getDao().findEmergencyInfoById(id);
		assertFalse("Failed to find info for id.", info == null);
		assertTrue("Failed to move the item at all.", info.getOrder() != 4);
		assertTrue("Failed to move the item up the list.", info.getOrder() > 4);
	}

	@Test
	public void testReorderUpWithFirstItem() {
		Long id = new Long(1);
		getDao().reorder(id, true);
		EmergencyInfo info = getDao().findEmergencyInfoById(id);
		assertFalse("Failed to find info for id.", info == null);
		assertTrue("Moved the item on the list and shouldn't have.", info.getOrder() == 1);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EmergencyInfoDao getDao() {
		return dao;
	}

	public void setDao(EmergencyInfoDao dao) {
		this.dao = dao;
	}
}
