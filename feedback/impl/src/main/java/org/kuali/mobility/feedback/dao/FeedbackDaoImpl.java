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

package org.kuali.mobility.feedback.dao;

import org.kuali.mobility.feedback.entity.Feedback;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class FeedbackDaoImpl implements FeedbackDao {

	@PersistenceContext
	private EntityManager entityManager;

	public FeedbackDaoImpl() {
	}

	@Transactional
	public void saveFeedback(Feedback feedback) {
		if (feedback == null) {
			return;
		}
		if (feedback.getFeedbackId() == null) {
			entityManager.persist(feedback);
		} else {
			entityManager.merge(feedback);
		}
	}

	//    public void deleteFeedbackById(Long id) {
//        Query query = entityManager.createQuery("delete from Feedback f where feedbackId = :id");
//        query.setParameter("id", id);
//        query.executeUpdate();
//    }
//    
	public Feedback findFeedbackById(Long id) {
		Query query = entityManager.createQuery("select f from Feedback f where f.feedbackId = :id");
		query.setParameter("id", id);
		return (Feedback) query.getSingleResult();
	}

//    @SuppressWarnings("unchecked")
//    public List<Feedback> findAllFeedback() {
//        Query query = entityManager.createQuery("select f from Feedback f order by f.feedbackId");
//        return query.getResultList();
//    }
//    
//    @SuppressWarnings("unchecked")
//    public List<Feedback> findAllFeedbackByService(String service) {
//        Query query = entityManager.createQuery("select f from Feedback f where service = :service order by f.feedbackId");
//        query.setParameter("service", service);
//        return query.getResultList();
//    }

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
} 
