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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Represents a single article present in a news feed.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Entity
@Table(name = "NEWS_ARTICLE_T")
@XmlRootElement(name = "article")
public class NewsArticleImpl implements Serializable, Comparable<NewsArticle>, NewsArticle {

	private static final long serialVersionUID = -133725965130444787L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private String articleId;
	@Column(name = "TITLE")
	private String title;
	@Column(name = "LINK")
	private String link;
	@Column(name = "DESCRIPTION", columnDefinition = "CLOB")
	private String description;
	@Column(name = "PUBLISHERDATE")
	private Date publishDate;
	@Column(name = "SOURCEID")
	private long sourceId;
	@Column(name = "PUBLISHERDATEDISPALY")
	private String publishDateDisplay;

	private final SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM dd, yyyy h:mm a");

	@Override
	public NewsArticle copy() {
		NewsArticle copy = new NewsArticleImpl();
		if (title != null) {
			copy.setTitle(new String(title));
		}
		if (link != null) {
			copy.setLink(new String(link));
		}
		if (description != null) {
			copy.setDescription(new String(description));
		}
		if (articleId != null) {
			copy.setArticleId(new String(articleId));
		}
		copy.setSourceId(sourceId);
		copy.setPublishDate(new Date(publishDate.getTime()));

		return copy;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getPublishDateDisplay()
     */
	@Override
	public String getPublishDateDisplay() {
		return this.publishDateDisplay;
	}

	public void setPublishDateDisplay(String publishDateDisplay) {
		this.publishDateDisplay = publishDateDisplay;
	}

	@Override
	public int compareTo(NewsArticle arg0) {
		return publishDate.compareTo(arg0.getPublishDate());
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getTitle()
     */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setTitle(java.lang.String)
     */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getLink()
     */
	@Override
	public String getLink() {
		return link;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setLink(java.lang.String)
     */
	@Override
	public void setLink(String link) {
		this.link = link;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getDescription()
     */
	@Override
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setDescription(java.lang.String)
     */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getPublishDate()
     */
	@Override
	public Date getPublishDate() {
		return publishDate;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setPublishDate(java.sql.Timestamp)
     */
	@Override
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		this.publishDateDisplay = format.format(publishDate);
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getArticleId()
     */
	@Override
	public String getArticleId() {
		return articleId;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setArticleId(java.lang.String)
     */
	@Override
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#getSourceId()
     */
	@Override
	public long getSourceId() {
		return sourceId;
	}

	/* (non-Javadoc)
     * @see org.kuali.mobility.news.entity.NewsArticle#setSourceId(long)
     */
	@Override
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
}
