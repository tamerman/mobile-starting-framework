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

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.writer.entity.Comment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for the CommentDao
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@Repository
public class CommentDaoImpl implements CommentDao {

	/**
	 * A reference to the <code>EntityManager</code>.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Gets the <code>EntityManager</code>
	 *
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the <code>EntityManager</code>
	 *
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Comment> getCommentsForArticle(long articleId) {
		Query query = getEntityManager().createNamedQuery("Comment.getCommentsForArticle");
		query.setParameter("articleId", articleId);
		List<Comment> resultList = query.getResultList();
		return resultList;
	}

	@Override
	@Transactional
	public Comment addComment(Comment comment) {
		getEntityManager().persist(comment);
		return comment;
	}

	@Override
	@Transactional
	public void deleteComment(long commentId) {
		Query query = getEntityManager().createNamedQuery("Comment.deleteComment");
		query.setParameter("commentId", commentId);
		query.executeUpdate();
	}

}
