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

package org.kuali.mobility.reporting.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.file.entity.File;

//import org.kuali.mobility.reporting.entity.File;
import org.kuali.mobility.reporting.entity.Submission;
import org.springframework.stereotype.Repository;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Repository
public class ReportingDaoImpl implements ReportingDao {

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Submission> findAllSubmissions() {
		try {
			Query query = entityManager.createQuery("select s from Submission s");
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Submission> findAllSubmissionsByParentId(Long id) {
		try {
			Query query = entityManager.createQuery("select s from Submission s where s.parentId = :id or s.id = :id order by s.revisionNumber desc");
			query.setParameter("id", id);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Submission findSubmissionById(Long id) {
		try {
			Query query = entityManager.createQuery("select s from Submission s where s.id = :id");
			query.setParameter("id", id);
			return (Submission) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Long saveSubmission(Submission submission) {
		if (submission == null) {
			return null;
		}
		try {
			if (submission.getId() == null) {
				entityManager.persist(submission);
			} else {
				entityManager.merge(submission);
			}
		} catch (OptimisticLockException oe) {
			return null;
		}
		return submission.getId();
	}

	public Long saveAttachment(File file) {
		if (file == null) {
			return null;
		}
		try {
			if (file.getId() == null) {
				entityManager.persist(file);
			} else {
				entityManager.merge(file);
			}
		} catch (OptimisticLockException oe) {
			return null;
		}
		return file.getId();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
