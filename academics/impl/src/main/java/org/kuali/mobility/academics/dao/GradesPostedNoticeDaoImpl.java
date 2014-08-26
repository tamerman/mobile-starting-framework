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

package org.kuali.mobility.academics.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.entity.GradesPostedNotice;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.3.0
 */
@Repository
public class GradesPostedNoticeDaoImpl implements GradesPostedNoticeDao, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(GradesPostedNoticeDaoImpl.class);
	private ApplicationContext applicationContext;

	@PersistenceContext
	private EntityManager entityManager;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@Resource(name = "academicsProperties")
	private Properties academicsProperties;

	@SuppressWarnings("unchecked")
	@Transactional
	public boolean uploadGradesPostedNotice(List<String> gradereadylist) {
		LOG.debug("BEGIN: uploadGradesPostedNotice.");
		boolean response = true;

		if (gradereadylist == null || gradereadylist.isEmpty()) {
			response = false;
		} else {
			try {
				LOG.debug("uploadGradesPostedNotice displaying size:" + gradereadylist.size());
				for (String s : gradereadylist) {
					LOG.debug(s);
					GradesPostedNotice gradespostednotice = (GradesPostedNotice) getApplicationContext().getBean("academicsGradesPostedNoticeBean");
					gradespostednotice.setLoginName(s);
					gradespostednotice.setInProcess(false);
					gradespostednotice.setTimestampProcessed(null);
					Date date = new Date();
					gradespostednotice.setTimestampReceived(new Timestamp(date.getTime()));
					if (null == saveGradesPostedNotice(gradespostednotice)) {
						LOG.debug("uploadGradesPostedNotice: save failed");
						response = false;
					}
				}
			} catch (OptimisticLockException oe) {
				LOG.error("Unable to save grade posted notice data.", oe);
				response = false;
			}
			LOG.debug("END: uploadGradesPostedNotice.");
		}
		return response;
	}

	@Override
	@Transactional
	public Long saveGradesPostedNotice(GradesPostedNotice gradespostednotice) {
		Long id = null;
		if (gradespostednotice == null || gradespostednotice.getLoginName() == null || gradespostednotice.getLoginName().trim().isEmpty()) {
			id = new Long(-1);
		} else {
			LOG.debug("BEGIN: Save grades posted notice data for " + gradespostednotice.getLoginName());
			try {
				if (gradespostednotice.getId() == null) {
					LOG.debug("in persist");
					LOG.debug(gradespostednotice.getLoginName());
					LOG.debug(gradespostednotice.getTimestampReceived().toString());
					getEntityManager().persist(gradespostednotice);
				} else {
					getEntityManager().merge(gradespostednotice);
				}
				id = gradespostednotice.getId();
			} catch (OptimisticLockException oe) {
				LOG.error("Unable to save grade alerts posted notice for " + gradespostednotice.getLoginName(), oe);
				id = new Long(-1);
			}
			LOG.debug("END: Save grades posted notice data " + gradespostednotice.getLoginName());
		}
		return id;
	}

	@Override
	public GradesPostedNotice loadGradesPostedNotice(Long id) {
		LOG.debug("Looking up grade notice for " + id);
		Query query = getEntityManager().createNamedQuery("GradesPostedNotice.findById");
		query.setParameter("id", id);
		return (GradesPostedNotice) query.getSingleResult();
	}

	@Override
	public Long countUnsentGradeNotices() {
		Query query = getEntityManager().createNamedQuery("GradesPostedNotice.countUnsentNotices");
		Long count = (Long) query.getSingleResult();
		return count;
	}

	@Transactional
	public List<? extends GradesPostedNotice> getGradesToProcess(boolean getAll) {
		List<GradesPostedNotice> notices = null;
		try {
			Query query = getEntityManager().createNamedQuery("GradesPostedNotice.getUnsent");
			if (!getAll) {
				query.setMaxResults(Integer.parseInt(getAcademicsProperties().getProperty("academics.grade.alert.batch.size", "10")));
			}
			query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
			notices = query.getResultList();

			for (GradesPostedNotice notice : notices) {
				notice.setInProcess(true);
				getEntityManager().merge(notice);
			}


		} catch (OptimisticLockException oe) {
			LOG.error(oe.toString());
		} catch (Exception e) {
			LOG.error(e.toString());
		}

		return notices;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public Properties getAcademicsProperties() {
		return academicsProperties;
	}

	public void setAcademicsProperties(Properties academicsProperties) {
		this.academicsProperties = academicsProperties;
	}
}
