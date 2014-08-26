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

package org.kuali.mobility.writer.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.writer.entity.Article;
import org.kuali.mobility.writer.entity.ArticleRejection;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Data Access Object for articles.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@Repository
public class ArticleDaoImpl implements ArticleDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Article getArticle(long articleId) {
		return getEntityManager().find(Article.class, articleId);
	}


	@Override
	@Transactional
	public Article maintainArticle(Article article) {
		Article a = article;
		if (article.getId() == null) { // Store new
			getEntityManager().persist(article);
		} else { // Update existing
			a = getEntityManager().merge(article);
		}
		return a;
	}

	@Override
	public List<Article> getRejectedArticles(String tool, String userId) {
		Query query = getEntityManager().createNamedQuery("Article.getRejectedArticles");
		query.setParameter("userId", userId);
		query.setParameter("tool", tool);
		return query.getResultList();
	}

	@Override
	public List<Article> getSavedArticles(String tool, String userId, boolean isEditor) {
		String nq = (isEditor ? "Article.getSavedArticlesEditor" : "Article.getSavedArticles");
		Query query = getEntityManager().createNamedQuery(nq);
		query.setParameter("userId", userId);
		query.setParameter("tool", tool);
		return query.getResultList();
	}


	@Override
	public List<Article> getSubmittedArticles(String tool) {
		Query query = getEntityManager().createNamedQuery("Article.getSubmittedArticles");
		query.setParameter("tool", tool);
		return query.getResultList();
	}

	@Override
	public ArticleRejection getArticleRejection(long rejectionId) {
		return getEntityManager().find(ArticleRejection.class, rejectionId);
	}


	@Override
	public long getNumArticles(String tool, long topicId) {
		String nq = (topicId == 0 ? "Article.getNumArticlesTopNews" : "Article.getNumArticles");
		Query query = getEntityManager().createNamedQuery(nq);
		if (topicId != 0) {
			query.setParameter("topicId", topicId);
		}
		query.setParameter("tool", tool);
		return (Long) query.getSingleResult();
	}

	@Override
	public List<Article> getArticles(String tool, long topicId, int from, int fetchSize) {
		String nq = (topicId == 0 ? "Article.getTopNewsRange" : "Article.getNewsRange");
		Query query = getEntityManager().createNamedQuery(nq);
		query.setFirstResult(from);
		query.setMaxResults(fetchSize);
		if (topicId != 0) {
			query.setParameter("topicId", topicId);
		}
		query.setParameter("tool", tool);
		List<Article> resultList = new ArrayList<Article>();
		resultList.addAll(query.getResultList());
		return resultList;
	}

	@Override
	public List<Article> searchArticles(String tool, String searchText, int from, int fetchSize) {
		Query query = this.getEntityManager().createNamedQuery("Article.searchNews");
		query.setParameter("text", "%" + searchText + "%");
		query.setParameter("tool", tool);
		query.setFirstResult(from);
		query.setMaxResults(fetchSize);
		return query.getResultList();
	}

	@Override
	public long searchArticlesCount(String tool, String searchText) {
		Query query = this.getEntityManager().createNamedQuery("Article.searchNewsCount");
		query.setParameter("text", "%" + searchText + "%");
		query.setParameter("tool", tool);
		return (Long) query.getSingleResult();
	}

	@Override
	public long getNumberRejectedArticles(String tool, String userId) {
		Query query = getEntityManager().createNamedQuery("Article.getNumRejectedArticles");
		query.setParameter("userId", userId);
		query.setParameter("tool", tool);
		return (Long) query.getSingleResult();
	}

	@Override
	public long getNumberSavedArticles(String instance, String userId, boolean isEditor) {
		String nq = (isEditor ? "Article.getNumSavedArticlesEditor" : "Article.getNumSavedArticles");
		Query query = getEntityManager().createNamedQuery(nq);
		query.setParameter("userId", userId);
		query.setParameter("tool", instance);
		return (Long) query.getSingleResult();
	}

	@Override
	public long getNumberSubmittedArticles(String instance) {
		Query query = getEntityManager().createNamedQuery("Article.getNumSubmittedArticles");
		query.setParameter("tool", instance);
		return (Long) query.getSingleResult();
	}
}
