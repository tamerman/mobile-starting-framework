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

package org.kuali.mobility.writer.entity;

import java.util.Date;

import javax.persistence.*;

/**
 * A class representing a comment placed on an article.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@NamedQueries({
		@NamedQuery(
				name = "Comment.getCommentsForArticle",
				query = "SELECT c FROM Comment c WHERE article_id = :articleId ORDER BY c.timestamp DESC"),
		@NamedQuery(
				name = "Comment.deleteComment",
				query = "DELETE Comment WHERE id = :commentId"
		)
})
@Entity
@Table(name = "WRITER_COMMENT")
public class Comment {

	/**
	 * Display name for the user that created the comment
	 */
	@Column(name = "USER_DISPLAY_NAME", nullable = false, length = 128)
	private String userDisplayName;

	/**
	 * Primary key of the comment
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	/**
	 * Id of the article on which this comment is placed
	 */
	@Column(name = "ARTICLE_ID", nullable = false)
	private long articleId;

	/**
	 * Timestamp of when the comment was placed
	 */
	@Column(name = "TIMESTAMP", nullable = false)
	private Date timestamp;

	/**
	 * Title of the comment
	 */
	@Column(name = "TITLE", nullable = false, length = 64)
	private String title;

	/**
	 * The comment text
	 */
	@Column(name = "TEXT", nullable = false, length = 250)
	private String text;

	/**
	 * Version number for the comment
	 */
	@Version
	@Column(name = "VER_NBR")
	protected long versionNumber;

	/**
	 * @return the userDisplayName
	 */
	public String getUserDisplayName() {
		return userDisplayName;
	}

	/**
	 * @param userDisplayName the userDisplayName to set
	 */
	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the articleId
	 */
	public long getArticleId() {
		return articleId;
	}

	/**
	 * @param articleId the articleId to set
	 */
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Sets the version number of the Comment
	 *
	 * @return
	 */
	public long getVersionNumber() {
		return versionNumber;
	}


	/**
	 * Gets the version number of the Comment
	 *
	 * @return
	 */
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}

}
