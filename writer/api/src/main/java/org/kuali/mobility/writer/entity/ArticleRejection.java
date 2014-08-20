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

package org.kuali.mobility.writer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A class representing an article's rejection
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@Entity
@Table(name="WRITER_ARTICLE_REJECTION")
public class ArticleRejection {

	/**
	 * Primary key for this rejection
	 */
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;
	
	/**
	 * Reason for rejecting the article
	 */
	@Column(name="REASON", nullable=false)
	private String reason;
	
	/**
	 * ID of the user that rejected the article
	 */
	@Column(name="USER_ID", nullable=false)
	private String userId;
	
	/**
	 * Display name of the user that rejected the article.
	 */
	@Column(name="USER_DISPLAY_NAME", nullable=false)
	private String userDisplayName;
	
	/**
	 * Id of the article to which this rejection belongs
	 */
	@Column(name="ARTICLE_ID", nullable=false)
	private long articleId;
	
	@Version
	@Column(name="VER_NBR")
	protected long versionNumber;
	
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
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

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
	 * Sets the version number of the ArticleRejection
	 * @return
	 */
	public long getVersionNumber() {
		return versionNumber;
	}
	

	/**
	 * Gets the version number of the ArticleRejection
	 * @return
	 */
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}    
}
