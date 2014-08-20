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

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.news.dao.NewsCache;
import org.kuali.mobility.news.dao.NewsDao;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsArticleImpl;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.entity.NewsSourceImpl;
import org.kuali.mobility.news.util.CompactNewsSourceTransform;
import org.kuali.mobility.news.util.NewsArticleTransform;
import org.kuali.mobility.news.util.NewsServiceSort;
import org.kuali.mobility.news.util.NewsSourceTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service for actually doing the work of interacting with the nedws entity
 * objects
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @see org.kuali.mobility.news.service.NewsService
 */
public class NewsServiceImpl implements NewsService, ApplicationContextAware {

    private static final Logger LOG = LoggerFactory.getLogger(NewsServiceImpl.class);
    private ApplicationContext applicationContext;
    private NewsDao dao;
    private NewsCache cache;

    @Override
    @Deprecated
    public List<NewsSourceImpl> getAllNewsSources() {
        return getAllNewsSources(false);
    }

    @Override
    @GET
    @Path("/sources/getAll")
    public List<NewsSourceImpl> getAllNewsSources(@QueryParam("compact") Boolean compact) {
        LOG.debug("Called getAllNewsSources web service.");
        List<NewsSourceImpl> sources = new ArrayList<NewsSourceImpl>();
        if( null != compact && compact.booleanValue() ) {
            CollectionUtils.collect(getDao().findAllNewsSources(), new CompactNewsSourceTransform(), sources);
        } else {
            CollectionUtils.collect(getDao().findAllNewsSources(), new NewsSourceTransform(), sources);
        }
        Collections.sort( sources, new NewsServiceSort());

        return sources;
    }


    @GET
    @Path("/sources/getAllArticles")
    public List<NewsArticleImpl> getAllNewsArticles(@QueryParam("compact") Boolean compact) {
        List<NewsArticleImpl> articles = new ArrayList<NewsArticleImpl>();
        List<NewsArticleImpl> articleList = new ArrayList<NewsArticleImpl>();
        List<NewsArticleImpl> fullArticleList = new ArrayList<NewsArticleImpl>();
        List<NewsSourceImpl> sources = new ArrayList<NewsSourceImpl>();
        if( null != compact && compact.booleanValue() ) {
            CollectionUtils.collect(getDao().findAllNewsSources(), new CompactNewsSourceTransform(), sources);
        } else {
            CollectionUtils.collect(getDao().findAllNewsSources(), new NewsSourceTransform(), sources);
        }
        Collections.sort( sources, new NewsServiceSort());
        for(NewsSourceImpl source : sources) {
            articles = (List<NewsArticleImpl>) source.getArticles();
            if(articles != null && !articles.isEmpty()) {
                articleList.addAll(articles);
            }
        }
        CollectionUtils.collect(articleList, new NewsArticleTransform(), fullArticleList);
        Collections.sort(fullArticleList, new Comparator<NewsArticleImpl>() {
            public int compare(final NewsArticleImpl object1, final NewsArticleImpl object2) {
                return object1.getPublishDate().after(object2.getPublishDate()) ? 1 : -1;
            }
        });
        Collections.reverse(fullArticleList);
        return fullArticleList;
    }


    @Override
    @Deprecated
    public List<NewsSourceImpl> getAllActiveNewsSources() {
        return getAllActiveNewsSources(false);
    }

    @Override
    @GET
    @Path("/sources/getAllActive")
    public List<NewsSourceImpl> getAllActiveNewsSources(@QueryParam("compact") Boolean compact) {
        LOG.debug("Called getAllActiveNewsSources web service.");
        List<NewsSourceImpl> sources = new ArrayList<NewsSourceImpl>();
        if( null == compact || !compact.booleanValue() ) {
            LOG.debug("Returning list of sources not compacted.");
            CollectionUtils.collect(getDao().findAllActiveNewsSources(), new NewsSourceTransform(), sources);
        } else {
            LOG.debug("Returning list of sources compacted without articles.");
            CollectionUtils.collect(getDao().findAllActiveNewsSources(), new CompactNewsSourceTransform(), sources);
        }
        Collections.sort( sources, new NewsServiceSort());
        return sources;
    }

    @Override
    @Deprecated
    public List<NewsSourceImpl> getAllActiveNewsSources(@PathParam("parentId") final Long parentId) {
        return getAllActiveNewsSources(parentId,false);
    }

    @Override
    @GET
    @Path("/sources/getAllActive/{parentId}")
    public List<NewsSourceImpl> getAllActiveNewsSources(@PathParam("parentId") final Long parentId, @QueryParam("compact") Boolean compact) {
        LOG.debug("Called getAllActiveNewsSources web service.");
        List<NewsSourceImpl> sources = new ArrayList<NewsSourceImpl>();
        if( null != compact && compact.booleanValue() ) {
            CollectionUtils.collect(getDao().findAllActiveNewsSources(parentId), new CompactNewsSourceTransform(), sources);
        } else {
            CollectionUtils.collect(getDao().findAllActiveNewsSources(parentId), new NewsSourceTransform(), sources);
        }
        Collections.sort( sources, new NewsServiceSort());
        return sources;
    }

    @Override
    @GET
    @Path("/sources/getChildNewsSource/{parentId}")
    public List<NewsSourceImpl> getNewsSources(@PathParam("parentId") final Long parentId, @QueryParam("active") final Boolean isActive) {
        List<NewsSourceImpl> sources = new ArrayList<NewsSourceImpl>();
        CollectionUtils.collect(getDao().findNewsSources(parentId, isActive), new NewsSourceTransform(), sources);
        return sources;
    }

    @Override
    @GET
    @Path("/sources/getNewsSource/{sourceId}")
    public NewsSourceImpl getNewsSourceById(@PathParam("sourceId") final Long id) {
        LOG.debug("Called getNewsSourceById web service.");
        NewsSourceTransform transform = new NewsSourceTransform();
        return transform.transform(getDao().lookup(id));
    }

    @Override
    @GET
    @Path("/source/{sourceId}/article/{articleId}")
    public NewsArticleImpl getNewsArticle(@PathParam("articleId") final String articleId, @PathParam("sourceId") final long sourceId) {
        NewsArticleImpl foundArticle = null;
        LOG.debug("Looking for article id " + articleId);
        NewsSource source = getNewsSourceById(sourceId);
        NewsArticleTransform transform = new NewsArticleTransform();
        for (NewsArticle article : source.getArticles()) {
            LOG.debug("Comparing with: " + article.getArticleId());
            String id;
            try {
                id = URLDecoder.decode(article.getArticleId(), "UTF-8");
//				if (articleId.equals(id)) {
//					return article.copy();
//				}
                if (articleId.equalsIgnoreCase(id) || articleId.equalsIgnoreCase(article.getArticleId())) {
                    foundArticle = transform.transform(article);
                    break;
                }
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getLocalizedMessage());
            }
        }
        return foundArticle;
    }

    @Override
    @GET
    @Path("/articles")
    public NewsArticleImpl getNewsArticle(@QueryParam("articleId") final String articleId) {
        NewsArticleImpl foundArticle = null;
        LOG.debug("Looking for article id " + articleId);
        NewsArticleTransform transform = new NewsArticleTransform();
        for (NewsSource source : getDao().findAllActiveNewsSources()) {
            if (source.getArticles() != null && source.getArticles().size() > 0) {
                for (NewsArticle article : source.getArticles()) {
                    LOG.debug("Comparing with: " + article.getArticleId());
                    String id;
                    try {
                        id = URLDecoder.decode(article.getArticleId(), "UTF-8");
                        if (articleId.equalsIgnoreCase(id) || articleId.equalsIgnoreCase(article.getArticleId())) {
                            foundArticle = transform.transform(article);
                            break;
                        }
                    } catch (UnsupportedEncodingException e) {
                        LOG.error(e.getLocalizedMessage());
                    }
                }
            }
        }
        return foundArticle;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public NewsDao getDao() {
        return dao;
    }

    public void setDao(NewsDao dao) {
        this.dao = dao;
    }

    public NewsCache getCache() {
        return cache;
    }

    public void setCache(NewsCache cache) {
        this.cache = cache;
    }
}
