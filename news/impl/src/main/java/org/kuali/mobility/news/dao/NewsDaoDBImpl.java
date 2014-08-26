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

package org.kuali.mobility.news.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.util.mapper.DataMapper;
import org.kuali.mobility.util.mapper.DataMapperImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO for actually persisting and retrieving NewsSource objects
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @see org.kuali.mobility.news.dao.NewsDao
 */
@Repository
public class NewsDaoDBImpl implements NewsDao {

	public static final Logger LOG = LoggerFactory.getLogger(NewsDaoDBImpl.class);

	private ApplicationContext applicationContext;
	private NewsCache cache;

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<NewsSource> findAllActiveNewsSources() {
		Query query = entityManager.createQuery("select s from NewsSource s where s.active = :active order by s.order");
		query.setParameter("active", true);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<NewsSource>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<NewsSource> findAllNewsSources() {
		Query query = entityManager.createQuery("select s from NewsSource s order by s.order");
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<NewsSource>();
		}
	}

	@Override
	@Transactional
	public NewsSource lookup(Long id) {
		Query query = entityManager.createQuery("select s from NewsSource s where s.id = :id");
		query.setParameter("id", id);
		try {
			NewsSource s = (NewsSource) query.getSingleResult();
			return s;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional
	public NewsSource save(NewsSource newsSource) {
		if (newsSource == null) {
			return null;
		}
		try {
			if (newsSource.getId() == null) {
				entityManager.persist(newsSource);
			} else {
				entityManager.merge(newsSource);
			}
		} catch (OptimisticLockException oe) {
			return null;
		}
		return newsSource;
	}

	@Override
	@Transactional
	public NewsSource delete(NewsSource newsSource) {
		Query query = entityManager.createQuery("delete from NewsSource ns where ns.id = :id");
		query.setParameter("id", newsSource.getId());
		query.executeUpdate();
		return newsSource;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public NewsCache getCache() {
		return cache;
	}

	public void setCache(NewsCache cache) {
		this.cache = cache;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<NewsSource> findNewsSources(Long parentId, Boolean isActive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsSource> findAllActiveNewsSources(Long parentId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<NewsSource> findAllNewsSources(Long parentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
