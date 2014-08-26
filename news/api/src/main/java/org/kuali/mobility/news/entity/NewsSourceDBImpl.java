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

package org.kuali.mobility.news.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a source for a news feed.  Each NewsSource has an associated NewsFeed.
 * A NewsSource contains the URL and display order for a feed, whereas the NewsFeed contains the actual feed data.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Entity
@Table(name = "NEWS_SRC_T")
public class NewsSourceDBImpl implements NewsSource {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "URL")
	private String url;

	@Column(name = "ACTIVE")
	private boolean active;

	@Column(name = "ORDR")
	private int order;

	// TODO: Change this so it can be persisted.
	@Transient
	private Long parentId;

	@Transient
	private String title;

	@Transient
	private String author;

	@Transient
	private String description;

	@Transient
	private List<NewsArticleImpl> articles;

	@Transient
	private List<NewsSourceDBImpl> children;

	@Transient
	private boolean hasChildren = false;

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#getUrl()
	 */
	@Override
	public String getUrl() {
		return url;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#setUrl(java.lang.String)
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#isActive()
	 */
	@Override
	public boolean isActive() {
		return active;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#setActive(boolean)
	 */
	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#getOrder()
	 */
	@Override
	public int getOrder() {
		return order;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.news.entity.NewsSource#setOrder(int)
	 */
	@Override
	public void setOrder(int order) {
		this.order = order;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the articles
	 */
	public List<? extends NewsArticle> getArticles() {
		return articles;
	}

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<? extends NewsArticle> articles) {
		this.articles = (List<NewsArticleImpl>) (List<?>) articles;
	}

	/**
	 * @return the children
	 */
	public List<? extends NewsSource> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<? extends NewsSource> children) {
		this.children = (List<NewsSourceDBImpl>) (List<?>) children;
	}

	public void addChild(NewsSource child) {
		this.children.add((NewsSourceDBImpl) child);
	}

	public boolean hasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

}
