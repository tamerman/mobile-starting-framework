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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.news.entity.NewsSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:SpringBeans.xml")
public class NewsDaoImplTest implements ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(NewsDaoImplTest.class);
	private ApplicationContext applicationContext;
	@Resource(name = "newsDao")
	private NewsDaoImpl dao;
	@Resource(name = "newsInitBean")
	private NewsInitBean initBean;

	@Before
	public void setUpTests() {
		getInitBean().loadData();
	}

	@Test
	public void testFindAllActiveNewsSources() {
		List<NewsSource> sources = (List<NewsSource>) getDao().findAllActiveNewsSources();
		assertTrue("Failed to find news sources.", sources != null && sources.size() > 0);
	}

	@Test
	public void testFindAllNewsSources() {
		List<NewsSource> sources = (List<NewsSource>) getDao().findAllNewsSources();
		assertTrue("Failed to find news sources.", sources != null && sources.size() > 0);
	}

	@Test
	public void testFindNewsSources() {
		List<NewsSource> sources = (List<NewsSource>) getDao().findNewsSources(Long.valueOf(0), new Boolean(true));
		assertTrue("Failed to find news sources.", sources != null && sources.size() > 0);
	}

	@Test
	public void testLookup() {
		NewsSource source = getDao().lookup(new Long(2));
		assertTrue("Failed to find news source.", source != null && "BBC - News".equalsIgnoreCase(source.getName()));
	}

	@Test
	public void testUpdateCache() {
		Long id = new Long(50);
		NewsSource source = getDao().lookup(id);
		assertTrue("Failed to find news source for id.", source != null);

		getDao().getCache().updateCache(source);
		NewsSource source2 = getDao().lookup(id);
		assertTrue("Failed to find news source for id after update.", source != null);
	}

	@Test
	public void testUpdateCacheWithInactiveSource() {
		NewsSource source = (NewsSource) getApplicationContext().getBean("newsSource");
		source.setId(new Long(1337));
		source.setActive(false);

		getDao().getCache().updateCache(source);
		assertTrue("Source added to cache and should not have been.", null == getDao().lookup(source.getId()));
	}

	@Test
	public void testUpdateCacheWithNewSource() {
		NewsSource source = (NewsSource) getApplicationContext().getBean("newsSource");
		source.setId(new Long(1337));
		source.setActive(true);
		source.setTitle("CNN.com - Travel");
		source.setUrl("http://rss.cnn.com/rss/cnn_travel.rss");

		getDao().getCache().updateCache(source);

		NewsSource source2 = getDao().lookup(source.getId());
		assertTrue("Source failed to add to cache.", source2 != null);

		assertTrue("Source added did not have articles.", null != source2.getArticles() && !source2.getArticles().isEmpty());
	}

	@Test
	public void testUpdateSourceWithNull() {
		try {
			getDao().getCache().updateSource(null);
		} catch (Exception e) {
			fail("Exception thrown when updating sources in cache with null objects.");
		}
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public NewsDaoImpl getDao() {
		return dao;
	}

	public void setDao(NewsDaoImpl dao) {
		this.dao = dao;
	}

	public NewsInitBean getInitBean() {
		return initBean;
	}

	public void setInitBean(NewsInitBean initBean) {
		this.initBean = initBean;
	}
}
