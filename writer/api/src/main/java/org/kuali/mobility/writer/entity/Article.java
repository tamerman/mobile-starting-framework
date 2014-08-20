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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A class representing an article.
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@NamedQueries({
	
	// Gets articles rejected by this user
	@NamedQuery(
		name="Article.getRejectedArticles",
		query="SELECT a FROM Article a WHERE a.journalistId = :userId AND status = 3 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Gets number of rejected articles by this user
	@NamedQuery(
		name="Article.getNumRejectedArticles",
		query="SELECT COUNT(a) FROM Article a WHERE a.journalistId = :userId AND status = 3 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Gets saved articles for an editor
	@NamedQuery(
		name="Article.getSavedArticlesEditor",
		query="SELECT a FROM Article a WHERE (a.journalistId = :userId AND a.status = 1) OR (a.editorId = :userId AND a.status = 21) AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Gets the number of saved articles for an editor
	@NamedQuery(
		name="Article.getNumSavedArticlesEditor",
		query="SELECT COUNT(a) FROM Article a WHERE (a.journalistId = :userId AND a.status = 1) OR (a.editorId = :userId AND a.status = 21) AND a.tool = :tool ORDER BY a.timestamp DESC"),

	//  Gets saved articles for a journalist
	@NamedQuery(
		name="Article.getSavedArticles",
		query="SELECT a FROM Article a WHERE a.journalistId = :userId AND status = 1 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	//  Gets the number of saved articles for a journalist
	@NamedQuery(
		name="Article.getNumSavedArticles",
		query="SELECT COUNT(a) FROM Article a WHERE a.journalistId = :userId AND status = 1 AND a.tool = :tool ORDER BY a.timestamp DESC"),

	// Gets submitted articles
	@NamedQuery(
		name="Article.getSubmittedArticles",
		query="SELECT a FROM Article a WHERE status = 2 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Gets number of submitted articles
	@NamedQuery(
		name="Article.getNumSubmittedArticles",
		query="SELECT COUNT(a) FROM Article a WHERE status = 2 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Gets the number of top news articles
	@NamedQuery(
		name="Article.getNumArticlesTopNews",
		query="SELECT COUNT(a) from Article a WHERE status = 4 AND a.tool = :tool"),

	// Gets the number of articles published for the specified topic
	@NamedQuery(
		name="Article.getNumArticles",
		query="SELECT COUNT(a) from Article a WHERE topic.id = :topicId AND status = 4 AND a.tool = :tool"),

	// Gets a range of published top news
	@NamedQuery(
		name="Article.getTopNewsRange",
		query="SELECT a from Article a WHERE status = 4 AND a.tool = :tool ORDER BY a.timestamp  DESC"),

	// Gets a range of published articles for the specified topic
	@NamedQuery(
		name="Article.getNewsRange",
		query="SELECT a FROM Article a WHERE topic.id = :topicId AND status = 4 AND a.tool = :tool ORDER BY a.timestamp DESC"),
	
	// Searches for articles
	@NamedQuery(
		name="Article.searchNews",
		query="SELECT a FROM Article a WHERE (a.text LIKE :text OR a.heading LIKE :text) AND status = 4 AND a.tool = :tool ORDER BY a.timestamp DESC"),
		
	// Gets the count of articles that matches a search
	@NamedQuery(
		name="Article.searchNewsCount",
		query="SELECT COUNT(a) FROM Article a WHERE (a.text LIKE :text OR a.heading LIKE :text) AND status = 4 AND a.tool = :tool")
		
})
@Entity
@Table(name="WRITER_ARTICLE")
public class Article {

	// Statuses of articles
	
	/** Status of an article that has been saved by its owner */
	public static final int STATUS_SAVED 			= 1;
	
	/** Status of an article that has been submitted to an editor */
	public static final int STATUS_SUBMITTED 		= 2;
	
	/** Status of an article that is submited and saved by an editor */
	public static final int STATUS_SUBMITTED_SAVED	= 21;
	
	/** Status of an article that is rejected */
	public static final int STATUS_REJECTED 		= 3;
	
	/** Status of an article that has been published */
	public static final int STATUS_PUBLISHED 		= 4;
	
	/** Status of an article that has been discarded **/
	public static final int STATUS_DISCARDED 		= 5;
	
	/** Status of an article that has been deleted **/
	public static final int STATUS_DELETED 			= 6;

	// Categories of articles
	// TODO This should also move to the database
	// The ids MUST remain the same else it will not work properly
	// Do the same as with Topic
	/** General news article */
	public static final long CATEGORY_GENERAL	= 1;
	
	/** Important news article */
	public static final long CATEGORY_IMPORTANT	= 2;

	/** Id of the article */
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;

	/**
	 * Id of the user that wrote the article
	 */
	@Column(name="JOURNALIST_ID")
	private String journalistId;

	/**
	 * URL of the additional link per article.
	 */
	@Column(name="LINK_URL")
	private String linkUrl;
	
	/**
	 * ID of the editor.
	 * This field will only be set if the editor is the author of the
	 * article, or if the article is currently saved by an editor.
	 */
	@Column(name="EDITOR_ID")
	private String editorId;

	/**
	 * Current status of the article
	 * 1 - Saved
	 * 2 - Submitted
	 * 3 - Rejected
	 * 4 - Published
	 * 5 - Discarded
	 */
	@Column(name="STATUS", nullable=false)
	private int status;

	/** Heading of the article */
	@Column(name="HEADING", nullable=false, length=64)
	private String heading;

	/** Synopsis of the article */
	@Column(name="SYNOPSIS", nullable=false, length=250)
	private String synopsis;

	/** Text of the article */
	@Column(name="TEXT", nullable=false, length=4000)
	private String text;

	/** Topic of the article */
	@OneToOne(optional=true)
	@JoinColumn(name="TOPIC_ID")
	private Topic topic;

	/** Category of the article */
	@Column(name="CATEGORY_ID", nullable=false)
	private long category;

	/** Last date the state of the article changed/saved */
	@Column(name="TIMESTAMP", nullable=false)
	private Date timestamp;

	/** Display name of the journalist */
	@Column(name="JOURNALIST", nullable=false, length=255)
	private String journalist;

	/**
	 * Id of the rejection reason for this article, zero if not rejected
	 */
	@OneToOne(optional=true)
	@JoinColumn(name="REJECTION_ID")
	private ArticleRejection rejection;

	/**
	 * Id of the image in the media table
	 */
	@OneToOne(optional=true)
	@JoinColumn(name="IMAGE_ID")
	private Media image;

	/**
	 * Id of the video in the media table.
	 */
	@OneToOne(optional=true)
	@JoinColumn(name="VIDEO_ID")
	private Media video;

	@Version
	@Column(name="VER_NBR")
	protected Long versionNumber;
	
	/**
	 * Tool instance on which this article is published
	 */
	@Column(name="TOOL")
	private String tool;
	
	/**
	 * @return the image
	 */
	public Media getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Media image) {
		this.image = image;
	}

	/**
	 * @return the video
	 */
	public Media getVideo() {
		return video;
	}

	/**
	 * @param video the video to set
	 */
	public void setVideo(Media video) {
		this.video = video;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the rejection
	 */
	public ArticleRejection getRejection() {
		return rejection;
	}

	/**
	 * @param rejection the rejection to set
	 */
	public void setRejection(ArticleRejection rejection) {
		this.rejection = rejection;
	}



	/**
	 * @return the journalistId
	 */
	public String getJournalistId() {
		return journalistId;
	}

	/**
	 * @param journalistId the journalistId to set
	 */
	public void setJournalistId(String journalistId) {
		this.journalistId = journalistId;
	}

	/**
	 * @return the journalist
	 */
	public String getJournalist() {
		return journalist;
	}

	/**
	 * @param journalist the journalist to set
	 */
	public void setJournalist(String journalist) {
		this.journalist = journalist;
	}


	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
     * Set the timestamp the article was last modified
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
     * Gets the ID for the article
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
     * Set the ID for the article
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
     * Gets the heading for the article.
	 * @return the heading
	 */
	public String getHeading() {
		return heading;
	}

	/**
     * Sets the heading of the article.
	 * @param heading the heading to set
	 */
	public void setHeading(String heading) {
		this.heading = heading;
	}

	/**
     * Gets the synopsis text for the article.
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}

	/**
     * Set the synopsis text for the article.
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	/**
     * Gets the main article text.
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
     * Sets the main text for this article
	 * @param text the text to set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
     * Gets the Topic for this Article.
	 * @return the topic
	 */
	public Topic getTopic() {
		return topic;
	}

	/**
     * Sets the topic for this article.
	 * @param topic the topic to set
	 */
	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	/**
     * Gets the Category for this article
	 * @return the category
	 */
	public long getCategory() {
		return category;
	}

	/**
     * Sets the category for this article.
	 * @param category the category to set
	 */
	public void setCategory(long category) {
		this.category = category;
	}

	/**
     * Gets the ID of the editor.
	 * @return the editorId
	 */
	public String getEditorId() {
		return editorId;
	}

	/**
     * Sets the ID of the editor
	 * @param editorId the editorId to set
	 */
	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}

	/**
	 * Sets the version number of the Article
	 * @return Version number of this entity.
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}
	

	/**
	 * Gets the version number of the Article
	 * @param versionNumber The version number for this entity.
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}


    /**
     * Gets the name of the tool instance
     * @return Name of the tool's instance.
     */
	public String getToolInstance() {
		return tool;
	}

    /**
     * Sets the tool instance name.
     * @param tool Tool instance name.
     */
	public void setToolInstance(String tool) {
		this.tool = tool;
	}

    /**
     * Get the attached link to the article
     * @return The link
     */
	public String getLinkUrl() {
		return linkUrl;
	}

    /**
     * Sets the attached link to the article
     * @param linkUrl The link.
     */
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	

	public String toString(){
		StringBuilder sb = new StringBuilder(256);
		sb.append("Article[");
		sb.append("category=").append(this.category);
		sb.append(",heading=").append(this.heading);
		sb.append(",id=").append(this.id);
		sb.append(",journalist=").append(this.journalist);
		sb.append(",synopsis=").append(this.synopsis);
		sb.append(",text=").append(this.text);
		sb.append(",timestamp=").append(this.timestamp);
		sb.append(",topic=").append(this.topic);
		sb.append(",version=").append(this.versionNumber);
		sb.append(",link=").append(this.linkUrl);
		sb.append("]");
		return sb.toString();
	}
}
