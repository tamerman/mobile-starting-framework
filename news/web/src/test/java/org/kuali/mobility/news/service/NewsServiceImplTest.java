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

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class NewsServiceImplTest {

	private static final Logger LOG = LoggerFactory.getLogger( NewsServiceImplTest.class );

	private static ApplicationContext applicationContext;

    @BeforeClass
    public static void createApplicationContext() {
    	NewsServiceImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/SpringBeans.xml" };
    }

	@Test
	public void testGetAllNewsSources() {
		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
		assertTrue( "Service did not instantiate properly.", service != null );
		assertTrue( "Service does not have a dao reference.", service.getDao() != null );
		assertTrue( "Service does not have a cache reference.", service.getCache() != null );
		List<NewsSource> sources = (List<NewsSource>)(List<?>)service.getAllNewsSources();
		assertTrue( "Failed to find news sources.", sources != null && sources.size() > 0 );
	}

	@Test
	public void testGetAllActiveNewsSources() {
		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
		List<NewsSource> sources = (List<NewsSource>)(List<?>)service.getAllNewsSources();
		assertTrue( "Failed to find news sources.", sources != null && sources.size() > 0 );
	}

	@Test
	public void testGetNewsSourceById() {
		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
		NewsSource source = service.getNewsSourceById(new Long(2) );
		assertTrue( "Failed to find news source.", source != null && "BBC - News".equalsIgnoreCase( source.getName() ) );
	}

//	@Test
//	public void testGetAllActiveNewsFeeds() {
//		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
//		List<NewsFeed> feeds = service.getAllActiveNewsFeeds();
//		assertTrue( "Failed to find news feeds.", feeds != null && feeds.size() > 0 );
//	}
//
//	@Test
//	public void testGetNewsFeeds() {
//		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
//		List<NewsFeed> feeds = service.getNewsFeeds( new Long(1), new Boolean( true ) );
//		assertTrue( "Failed to find child news feed for source 1.", feeds != null && feeds.size() > 0 );
//	}
//
//	@Test
//	public void testGetNewsFeed() {
//		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
//		NewsFeed feed = service.getNewsFeed( new Long(2) );
//		assertTrue( "Failed to find news feed for source 2.", feed != null && feed.getArticles() != null && feed.getArticles().size() > 0 );
//	}

	@Test
	public void testGetNewsArticle() {
		NewsService service = (NewsService)getApplicationContext().getBean("newsService");
		NewsSource source = service.getNewsSourceById( new Long(2) );
		assertTrue( "Failed to find news feed for source 2.", source != null && source.getArticles() != null && source.getArticles().size() > 0 );
		NewsArticle articleFirst = source.getArticles().get(0);
		assertTrue( "No articles found for feed in source 2.", articleFirst != null );
		LOG.debug( "Article ID: "+articleFirst.getArticleId() );
		LOG.debug( "Article Title: "+articleFirst.getTitle() );
		LOG.debug( "Article's Source ID: "+articleFirst.getSourceId() );
		NewsArticle articleSecond = service.getNewsArticle(articleFirst.getArticleId(), articleFirst.getSourceId() );
		assertTrue( "Failed to lookup article by source ID, article ID.", articleSecond != null);
        articleSecond = null;
        articleSecond = service.getNewsArticle(articleFirst.getArticleId());
		assertTrue( "Failed to lookup article by article ID.", articleSecond != null);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		NewsServiceImplTest.applicationContext = applicationContext;
	}

}
