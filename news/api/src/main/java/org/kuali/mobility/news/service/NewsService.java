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

package org.kuali.mobility.news.service;

import org.kuali.mobility.news.dao.NewsCache;
import org.kuali.mobility.news.dao.NewsDao;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsSource;

import javax.jws.WebService;
import java.util.List;

/**
 * An interface for a contract for interacting with the news entity objects.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@WebService
public interface NewsService {

    public List<? extends NewsSource> getNewsSources(Long parentId, Boolean isActive);

    /**
     * @return a list of all NewsSource objects sorted on NewsSource.order from
     * least to greatest
     */
    @Deprecated
    public List<? extends NewsSource> getAllNewsSources();
    public List<? extends NewsSource> getAllNewsSources(Boolean compact);

    /**
     * @return a list of all active NewsSource objects sorted on
     * NewsSource.order from least to greatest
     */
    @Deprecated
    public List<? extends NewsSource> getAllActiveNewsSources();
    public List<? extends NewsSource> getAllActiveNewsSources(Boolean compact);

    @Deprecated
    public List<? extends NewsSource> getAllActiveNewsSources(Long parentId);
    public List<? extends NewsSource> getAllActiveNewsSources(Long parentId,Boolean compact);

    /**
     * Retrieve a NewsSource object
     *
     * @param id the id of the NewsSource to retrieve
     * @return the found NewsSource if it exists, null otherwise
     */
    public NewsSource getNewsSourceById(Long id);

    /**
     * Retrieve the details of an article
     *
     * @param articleId the id of the article to retrieve
     * @param sourceId the id of the NewsSource to which the article belongs.
     * @return a NewsArticle object
     */
    public NewsArticle getNewsArticle(String articleId, long sourceId);

    public NewsArticle getNewsArticle(String articleId);

    public void setDao(NewsDao dao);

    public NewsDao getDao();

    public void setCache(NewsCache cache);

    public NewsCache getCache();
}
