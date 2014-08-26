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

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.ParsingFeedException;
import com.sun.syndication.io.SyndFeedInput;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsCacheImpl implements NewsCache, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(NewsCacheImpl.class);
	private ApplicationContext applicationContext;
	private Map<Long, NewsSource> newsSources;

	public NewsCacheImpl() {
		newsSources = new HashMap<Long, NewsSource>();
	}

	/**
	 * Update the cache for a single NewsSource. Called when a NewsSource is
	 * saved.
	 *
	 * @param source the NewsSource that was saved
	 */
	public void updateCache(NewsSource source) {
		if (source.isActive()) {
			LOG.debug("NewsSource " + source.getId() + " is active & will be refreshed.");
			if (!getNewsSources().containsKey(source.getId())) {
				getNewsSources().put(source.getId(), source);
			}
			updateSource(source);
		} else {
			LOG.debug("NewsSource is inactive & being removed.");
			getNewsSources().remove(source.getId());
		}
	}

	/**
	 * Does the actual work of updating a news feed and its articles
	 *
	 * @param feed   the NewsFeed to update
	 * @param source the NewsSource that defines the feed to update
	 */
	@SuppressWarnings("unchecked")
	public void updateSource(NewsSource source) {
		if (source == null) {
			LOG.error("Not updating, source is null.");
			return;
		} else if (source.getUrl() == null) {
			LOG.debug("Not updating source due to no URL for source " + source.getId());
			source.setTitle(source.getName());
			return;
		}

		URL feedUrl = null;
		try {
			feedUrl = new URL(source.getUrl());
		} catch (MalformedURLException e) {
			LOG.error("Bad feed url: " + source.getUrl(), e);
		}
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed syndFeed = null;
		try {
			syndFeed = input.build(new InputStreamReader(feedUrl.openStream()));
			if (syndFeed != null) {
				LOG.debug("Feed data retrieved, populating articles for: " + syndFeed.getTitle());
				source.setTitle(syndFeed.getTitle());
				source.setAuthor(syndFeed.getAuthor());
				source.setDescription(syndFeed.getDescription());

				List<NewsArticle> articles = new ArrayList<NewsArticle>();
				for (SyndEntryImpl entry : (List<SyndEntryImpl>) syndFeed.getEntries()) {
					LOG.debug("Processing article: " + entry.getTitle());
					NewsArticle article = (NewsArticle) getApplicationContext().getBean("newsArticle");

					article.setTitle(entry.getTitle());
					try {
						article.setDescription(entry.getDescription().getValue());
					} catch (NullPointerException npe) {
						LOG.error("Description for " + entry.getTitle() + " is null.");
						article.setDescription(null);
					}
					article.setLink(entry.getLink());
					try {
						article.setPublishDate(new Date(entry.getPublishedDate().getTime()));
					} catch (Exception e) {
						LOG.error("Error creating timestamp for article: " + entry.getTitle());
						LOG.error(e.getLocalizedMessage());
					}
					article.setSourceId(source.getId());
					try {
						article.setArticleId(URLEncoder.encode(entry.getUri(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						article.setArticleId(entry.getUri());
					}

					articles.add(article);
				}
				source.setArticles(articles);
			} else {
				source.setTitle(source.getName());
			}
		} catch (ParsingFeedException pfe) {
			LOG.error("Error parsing feed: " + source.getName());
			LOG.error(pfe.getLocalizedMessage(), pfe);
		} catch (Exception e) {
			LOG.error("Error reading feed: " + source.getName(), e);
		}

	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Map<Long, NewsSource> getNewsSources() {
		return newsSources;
	}

	public void setNewsSources(Map<Long, NewsSource> newsSources) {
		this.newsSources = newsSources;
		for (NewsSource source : newsSources.values()) {
			updateSource(source);
		}
	}
}
