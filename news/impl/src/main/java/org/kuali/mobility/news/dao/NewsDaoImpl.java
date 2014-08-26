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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.util.NewsSourcePredicate;
import org.kuali.mobility.util.mapper.DataMapper;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsDaoImpl implements NewsDao {

	public static final Logger LOG = LoggerFactory.getLogger(NewsDaoImpl.class);
	private ApplicationContext applicationContext;
	private NewsCache cache;
	private DataMapper mapper;
	private String newsSourceFile;
	private String newsSourceUrl;
	private String newsMappingFile;
	private String newsMappingUrl;

	public List<? extends NewsSource> findNewsSources(final Long parentId, final Boolean isActive) {
		initData();
		return (List<? extends NewsSource>) CollectionUtils.select(getCache().getNewsSources().values(), new NewsSourcePredicate(parentId, isActive));
	}

	@Override
	public List<? extends NewsSource> findAllActiveNewsSources() {
		initData();
		return (List<? extends NewsSource>) CollectionUtils.select(getCache().getNewsSources().values(), new NewsSourcePredicate(null, new Boolean(true)));
	}

	public List<? extends NewsSource> findAllActiveNewsSources(final Long parentId) {
		initData();
		return (List<? extends NewsSource>) CollectionUtils.select(getCache().getNewsSources().values(), new NewsSourcePredicate(parentId, new Boolean(true)));
	}

	@Override
	public List<? extends NewsSource> findAllNewsSources() {
		initData();
		return (new ArrayList<NewsSource>(getCache().getNewsSources().values()));
	}

	public List<? extends NewsSource> findAllNewsSources(final Long parentId) {
		initData();
		return (List<? extends NewsSource>) CollectionUtils.select(getCache().getNewsSources().values(), new NewsSourcePredicate(parentId, null));
	}

	@Override
	public NewsSource lookup(Long id) {
		initData();
		NewsSource source = null;
		source = getCache().getNewsSources().get(id);
		return source;
	}

	@Override
	public NewsSource save(NewsSource newsSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NewsSource delete(NewsSource newsSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		Map<Long, NewsSource> source = getCache().getNewsSources();
		if (source == null || source.isEmpty()) {
			source = new HashMap<Long, NewsSource>();
			List<NewsSource> sources = new ArrayList<NewsSource>();

			boolean isNewsSourceUrlAvailable = (getNewsSourceUrl() != null ? true : false);
			boolean isNewsMappingUrlAvailable = (getNewsMappingUrl() != null ? true : false);

			try {
				if (isNewsSourceUrlAvailable) {
					if (isNewsMappingUrlAvailable) {
						sources = (List<NewsSource>) mapper.mapData(source,
								new URL(getNewsSourceUrl()),
								new URL(getNewsMappingUrl()));
					} else {
						sources = (List<NewsSource>) mapper.mapData(source,
								new URL(getNewsSourceUrl()),
								getNewsMappingFile());

					}
				} else {
					if (isNewsMappingUrlAvailable) {
						// not supported in mapper.mapData
						LOG.error("DataMapper does NOT support this case!");
						return;
					} else {
						sources = (List<NewsSource>) mapper.mapData(source,
								getNewsSourceFile(), getNewsMappingFile());
					}
				}

				int i = 0;
				for (NewsSource s : sources) {
					s.setActive(true);
					if (s.isActive()) {
						getCache().updateSource(s);
					}
					if (s.getId() == null) {
						s.setId(new Long(i));
					}
					source.put(s.getId(), s);
					i++;
				}

				for (NewsSource s : source.values()) {
					if (null != s.getParentId() && s.getParentId().intValue() > 0) {
						NewsSource parent = source.get(s.getParentId());
						parent.addChild(s);
						parent.setHasChildren(true);
						LOG.debug(" ============== " + parent.getId() + " hasChildren is " + parent.hasChildren() + " ============== ");
					}
				}

				getCache().setNewsSources(source);

			} catch (MalformedURLException e) {
				LOG.error(e.getMessage());
				// e.printStackTrace();
			} catch (ClassNotFoundException e) {
				LOG.error(e.getMessage());
				// e.printStackTrace();
			} catch (IOException e) {
				LOG.error(e.getMessage());
				// e.printStackTrace();
			}

		}
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public NewsCache getCache() {
		return cache;
	}

	public void setCache(NewsCache cache) {
		this.cache = cache;
	}

	public DataMapper getMapper() {
		return mapper;
	}

	public void setMapper(DataMapper mapper) {
		this.mapper = mapper;
	}

	public String getNewsSourceFile() {
		return newsSourceFile;
	}

	public void setNewsSourceFile(String newsSourceFile) {
		this.newsSourceFile = newsSourceFile;
	}

	public String getNewsSourceUrl() {
		return newsSourceUrl;
	}

	public void setNewsSourceUrl(String newsSourceUrl) {
		this.newsSourceUrl = newsSourceUrl;
	}

	public String getNewsMappingFile() {
		return newsMappingFile;
	}

	public void setNewsMappingFile(String newsMappingFile) {
		this.newsMappingFile = newsMappingFile;
	}

	public String getNewsMappingUrl() {
		return newsMappingUrl;
	}

	public void setNewsMappingUrl(String newsMappingUrl) {
		this.newsMappingUrl = newsMappingUrl;
	}
}
