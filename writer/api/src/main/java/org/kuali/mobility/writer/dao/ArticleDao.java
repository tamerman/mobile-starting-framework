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

package org.kuali.mobility.writer.dao;

import java.util.List;

import org.kuali.mobility.writer.entity.Article;
import org.kuali.mobility.writer.entity.ArticleRejection;

/**
 * Article Data Access Object
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public interface ArticleDao {
	/**
	 * Gets a specific article.
	 * @param articleId Id if the article to get
	 * @return The article
	 */
	public Article getArticle(long articleId);
	
	/**
	 * Updates the article to persistence.
	 * @param article Article to update.
	 */
	public Article maintainArticle(Article article);
	
	/**
	 * Gets the list of articles that are currently rejected for the specified user.
	 * @param userId
	 * @return
	 */
	public List<Article> getRejectedArticles(String tool, String userId);
	
	/**
	 * Gets the number of rejected articles for the user on the specific tool
	 * instance
	 * @param tool name of the tool instance.
	 * @param userId Id of the user
	 * @return
	 */
	public long getNumberRejectedArticles(String tool, String userId);
	
	/**
	 * Gets the list of articles that are currently saved by the specified user.
	 * @param userId
	 * @return
	 */
	public List<Article> getSavedArticles(String tool, String userId, boolean isEditor);
	
	/**
	 * 
	 * Gets the number of saved articles for the user
	 * @param instance
	 * @param userId
	 * @param isEditor
	 * @return
	 */
	public long getNumberSavedArticles(String instance, String userId, boolean isEditor);
	
	/**
	 * Gets the list of articles that are currently submitted.
	 * @param userId
	 * @return
	 */
	public List<Article> getSubmittedArticles(String instance);
	
	/**
	 * Gets the number of submitted articles.
	 * @param instance name of the tool instance
	 * @return
	 */
	public long getNumberSubmittedArticles(String instance);
	
	/**
	 * Gets the rejection for the specified rejection id
	 * @param rejectionId
	 * @return
	 */
	public ArticleRejection getArticleRejection(long rejectionId);
	
	public long getNumArticles(String tool, long topicId);
	
	public List<Article> getArticles(String tool, long topicId, int from, int fetchSize);
	
	/**
	 * Searches for articles
	 * @param searchText Text to search
	 * @param from Starting row of resultset
	 * @param fetchSize Number of articles to retrieve
	 * @return
	 */
	public abstract List<Article> searchArticles(String tool, String searchText, int from, int fetchSize);
	
	/**
	 * Returns the number of search results
	 * @param searchText
	 * @return
	 */
	public abstract long searchArticlesCount(String tool, String searchText);
}
