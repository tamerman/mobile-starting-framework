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

import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoJPAImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmergencyInfoDaoImpl implements EmergencyInfoDao {

	private static final Logger LOG = LoggerFactory.getLogger(EmergencyInfoDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<EmergencyInfoJPAImpl> findAllEmergencyInfo() {
		List<EmergencyInfoJPAImpl> contacts = new ArrayList<EmergencyInfoJPAImpl>();
		Query query = entityManager.createNamedQuery("EmergencyInfo.findAll");
		try {
			contacts = query.getResultList();
		} catch (NoResultException e) {
			LOG.error("No results found in Emergency Info table.", e);
		}
		return contacts;
	}

	public EmergencyInfo findEmergencyInfoById(Long id) {
		EmergencyInfo contact = null;
		Query query = entityManager.createNamedQuery("EmergencyInfo.findById");
		query.setParameter("id", id);
		try {
			contact = (EmergencyInfoJPAImpl) query.getSingleResult();
		} catch (NoResultException e) {
			LOG.error("No emergency info found for id=" + id, e);
		}
		return contact;
	}

	@SuppressWarnings("unchecked")
	public List<? extends EmergencyInfo> findAllEmergencyInfoByCampus(String campus) {
		List<? extends EmergencyInfo> contacts = new ArrayList<EmergencyInfoJPAImpl>();
		Query query = entityManager.createNamedQuery("EmergencyInfo.findByCampus");
		query.setParameter("campus", campus);
		try {
			contacts = query.getResultList();
		} catch (NoResultException e) {
			LOG.error("No results found in Emergency Info table for campus=" + campus, e);
		}
		return contacts;
	}

	@Transactional
	public Long saveEmergencyInfo(EmergencyInfo emergencyInfo) {
		Long id = null;
		if (emergencyInfo != null) {
			LOG.debug("BEGIN: Save emergency info for " + emergencyInfo.getTitle());
			try {
				if (emergencyInfo.getEmergencyInfoId() == null) {
					entityManager.persist(emergencyInfo);
				} else {
					entityManager.merge(emergencyInfo);
				}
				id = emergencyInfo.getEmergencyInfoId();
			} catch (OptimisticLockException oe) {
				LOG.error("Unable to save emergency info for " + emergencyInfo.getTitle(), oe);
				id = null;
			}
			LOG.debug("END: Save emergency info for " + emergencyInfo.getTitle());
		}
		return id;
	}

	@Transactional
	public void deleteEmergencyInfoById(Long id) {
		if (null != id) {
			Query query = entityManager.createNamedQuery("EmergencyInfo.deleteById");
			query.setParameter("id", id);
			query.executeUpdate();
		}
	}

	@Transactional
	public void reorder(Long id, boolean up) {
		List<? extends EmergencyInfo> list = findAllEmergencyInfo();
		EmergencyInfo last = null;
		boolean flag = false;
		int index = -1;
		int count = list.get(0).getOrder();
		for (EmergencyInfo emergencyInfo : list) {
			index++;
			if (emergencyInfo.getEmergencyInfoId().equals(id)) {
				if (up && last != null) {
					swap(last, emergencyInfo);
					count = last.getOrder() + 1;
					continue;
				} else if (!up) {
					EmergencyInfo next = list.get(index + 1);
					swap(emergencyInfo, next);
					count = next.getOrder() + 1;
					continue;
				}
				flag = true;
			}
			if (flag) {
				emergencyInfo.setOrder(count);
				entityManager.merge(emergencyInfo);
			}
			count++;
			last = emergencyInfo;
		}
	}

	@Transactional
	private void swap(EmergencyInfo one, EmergencyInfo two) {
		int temp = one.getOrder();
		one.setOrder(two.getOrder());
		two.setOrder(temp);
		entityManager.merge(one);
		entityManager.merge(two);
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
