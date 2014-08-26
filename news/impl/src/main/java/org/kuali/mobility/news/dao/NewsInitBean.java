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

import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.shared.InitBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsInitBean implements InitBean {

	private static final Logger LOG = LoggerFactory.getLogger(NewsInitBean.class);

	@Resource(name = "newsDao")
	private NewsDao dao;
	@Resource(name = "newsCache")
	private NewsCache cache;

	public NewsInitBean() {
	}

	public NewsInitBean(NewsDao dao, NewsCache cache) {
		LOG.info("NewsInitBean Constructor");
		this.dao = dao;
		this.cache = cache;
	}

	/**
	 * @return the dao
	 */
	public NewsDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(NewsDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the cache
	 */
	public NewsCache getCache() {
		return cache;
	}

	/**
	 * @param cache the cache to set
	 */
	public void setCache(NewsCache cache) {
		this.cache = cache;
	}

	public void loadData() {
		LOG.info("Refreshing news...");
		for (NewsSource newsSource : getDao().findAllActiveNewsSources()) {
			getCache().updateCache(newsSource);
		}
	}
}



