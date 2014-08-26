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

import java.util.List;

public interface NewsSource {

	public Long getId();

	public void setId(Long id);

	/**
	 * @return the URL of the feed
	 */
	public String getUrl();

	/**
	 * @param url the URL of the feed
	 */
	public void setUrl(String url);

	/**
	 * @return whether the feed is active or not
	 */
	public boolean isActive();

	/**
	 * @param active set this feed active or inactive
	 */
	public void setActive(boolean active);

	/**
	 * @return the name assigned to this feed
	 */
	public String getName();

	/**
	 * @param name the name to set for this feed.  It is not displayed to end users.
	 */
	public void setName(String name);

	/**
	 * @return the title
	 */
	public String getTitle();

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title);

	/**
	 * @return the author
	 */
	public String getAuthor();

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author);

	/**
	 * @return the description of the feed
	 */
	public String getDescription();

	/**
	 * @param description the description of the feed
	 */
	public void setDescription(String description);

	/**
	 * @return the articles
	 */
	public List<? extends NewsArticle> getArticles();

	/**
	 * @param articles the articles to set
	 */
	public void setArticles(List<? extends NewsArticle> articles);

	/**
	 * @return the display order
	 */
	public int getOrder();

	/**
	 * @param order the display order
	 */
	public void setOrder(int order);

	public void setParentId(Long parentId);

	public Long getParentId();

	public void setChildren(List<? extends NewsSource> children);

	public List<? extends NewsSource> getChildren();

	public void addChild(NewsSource child);

	public boolean hasChildren();

	public void setHasChildren(boolean hasChildren);
}
