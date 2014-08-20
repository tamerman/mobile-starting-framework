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

package org.kuali.mobility.writer.service;

import org.kuali.mobility.writer.entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Service for the writer tool
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public interface WriterService {


    /**
     * Returns a list of comments that was placed on the specified article.
     * If no comments was placed an empty list should be returned.
     * @param articleId Id of the article to get comments of.
     * @return A list of comments placed on the article.
     */
    public abstract List<Comment> getCommentsForArticle(long articleId);

    /**
     * Gets the number of comments that has been placed on a article.
     * @param articleId Id of the article to get the count of.
     * @return The number of comments that has been placed.
     */
    public abstract int getNumberCommentForArticle(long articleId);

    /**
     * Gets the number of articles for the specified topic
     * @param topicId Topic to search in
     * @return Number of published articles in that topic
     */
    public abstract long getNumArticles(String tool, long topicId);


    /**
     * Gets articles that matches the specified criteria
     * @param tool tool to get news of
     * @param topicId Id of topic to get articles of
     * @param from Starting row of resultset
     * @param fetchSize Number of articles to retrieve
     * @return
     */
    public abstract List<Article> getArticles(String tool, long topicId, int from, int fetchSize);

    /**
     * Searches for articles
     * @param tool tool to get news of
     * @param searchText Text to search
     * @param from Starting row of resultset
     * @param fetchSize Number of articles to retrieve
     * @return
     */
    public abstract List<Article> searchArticles(String tool, String searchText, int from, int fetchSize);

    /**
     * Returns the number of search results
     * @param tool tool to get news of
     * @param searchText
     * @return
     */
    public abstract long searchArticlesCount(String tool, String searchText);

    /**
     * Gets a specific article.
     * @param articleId Id if the article to get
     * @return The article
     */
    public Article getArticle(long articleId);

    /**
     * Gets the number of saved articles by the specified user.
     * @param userId
     * @return
     */
    public abstract long getNumberSavedArticles(String tool, String userId, boolean isEditor);

    /**
     * Gets the list of articles that are currently saved by the specified user.
     * @param userId
     * @return
     */
    public abstract List<Article> getSavedArticles(String tool, String userId, boolean isEditor);

    /**
     * Gets the number of rejected articles by the speified user.
     * @param userId
     * @return
     */
    public abstract long getNumberRejectedArticles(String tool, String userId);

    /**
     * Gets the list of articles that are currently rejected for the specified user.
     * @param userId
     * @return
     */
    public abstract List<Article> getRejectedArticles(String tool, String userId);

    /**
     * Gets the rejection for the specified id.
     * @param rejectionId Id of the rejection
     * @return
     */
    public abstract ArticleRejection getArticleRejection(long rejectionId);

    /**
     * Gets the number of submitted articles
     */
    public abstract long getNumberSubmittedArticles(String tool);

    /**
     * Gets the list of articles that are currently submitted.
     * @param instance Get the articles submitted for the writer instance
     * @return
     */
    public abstract List<Article> getSubmittedArticles(String instance);

    /**
     * Adds a comment.
     * @param comment The comment to add.
     * @return The comment with updated id
     */
    public abstract Comment addComment(Comment comment);

    /**
     * Maintains the article in the database.
     * @param article Article to maintain
     * @return The updated/maintained article.
     */
    public abstract Article maintainArticle(Article article);

    /**
     * Gets the spesified media
     * @param mediaId
     * @return
     */
    public abstract Media getMedia(long mediaId);

    /**
     * Stores the specified media to the file system
     * @param mediaType Type of media to store
     * @param extention Original extension of the media
     * @param isThumbnail Is this for a thumbnail?
     * @param inputStream The stream to the media
     * @return
     */
    public abstract String storeMedia(int mediaType, String extention, boolean isThumbnail, InputStream inputStream);

    /**
     * Gets a media File
     */
    public abstract File getMediaFile(long mediaId, boolean isThumbnail) throws FileNotFoundException;

    /**
     *
     * @param media
     */
    public abstract Media maintainMedia(Media media);

    /**
     * Removes persisted media
     * @param mediaId
     */
    //public void removeMedia(long mediaId);

    /**
     * Persists an ArticleRejection
     * @param articleRejection
     */
    public abstract void persistArticleRejection(ArticleRejection articleRejection);

    /**
     * Gets all the topics
     * @return
     */
    public abstract List<Topic> getTopics();

    /**
     * Gets the specific topic
     * @param topicId
     * @return
     */
    public abstract Topic getTopic(long topicId);

    /**
     * Persists a topic
     * @param topic Topic to be persisted.
     */
    public abstract Topic saveTopic(Topic topic);

    /**
     * Deletes a comment.
     * @param commentId ID of the comment to remove
     */
    public abstract void deleteComment(long commentId);

    /**
     * Updates the media of an article
     * @param article Article to update
     * @param media
     * @return
     */
    public abstract Article updateMedia(Article article, Media media);

    /**
     * Removes media from an article
     * @param article Article to update
     * @param mediaType
     * @return
     */
    public abstract Article removeMedia(Article article, int mediaType);

    /**
     * Uploads a media file
     * @param mediaFile
     * @param mediaType
     * @return
     */
    public abstract Media uploadMediaData(MultipartFile mediaFile, int mediaType) throws IllegalArgumentException;

    /**
     * Removes all wapad badge notifications for the specified user.
     * @param username User to clear notifications of
     */
    public abstract void removeNotifications(String username);


    /**
     * Gets all the categories
     * @return
     */
    public abstract List<Category> getCategories();

    /**
     * Gets the specific category
     * @param categoryId ID of the category to get
     * @return The category.
     */
    public abstract Category getCategory(long categoryId);

    /**
     * Persists a category
     * @param category The category to persist.
     * @return The persisted instance of the category
     */
    public abstract Category saveCategory(Category category);

}
