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

package org.kuali.mobility.feedback.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.kuali.mobility.feedback.dao.FeedbackDao;
import org.kuali.mobility.feedback.dao.FeedbackSubjectDao;
import org.kuali.mobility.feedback.entity.FeedbackSubject;
import org.springframework.beans.factory.annotation.Autowired;

public class FeedbackSubjectServiceImpl implements FeedbackSubjectService {

	private static Logger LOG = LoggerFactory
			.getLogger(FeedbackSubjectServiceImpl.class);

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private FeedbackSubjectDao feedbackSubjectDao;


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * @return the feedbackSubjectDao
	 */
	public FeedbackSubjectDao getFeedbackSubjectDao() {
		return feedbackSubjectDao;
	}

	/**
	 * @param feedbackSubjectDao the feedbackSubjectDao to set
	 */
	public void setFeedbackSubjectDao(FeedbackSubjectDao feedbackSubjectDao) {
		this.feedbackSubjectDao = feedbackSubjectDao;
	}

	@Override
	public List<FeedbackSubject> getFeedbackSubjects() {
		// TODO Auto-generated method stub
		return feedbackSubjectDao.getFeedbackSubjects();
	}


}
