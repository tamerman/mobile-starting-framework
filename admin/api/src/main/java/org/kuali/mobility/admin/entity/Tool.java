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

package org.kuali.mobility.admin.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

import org.kuali.mobility.security.authz.entity.AclExpression;

/**
 * Defines a "tool" to show on a home screen. It can be assigned to HomeScreens through HomeTool objects.
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 1.0.0
 */
@NamedQueries({
	// Gets all the tools
	@NamedQuery(
		name="Tool.getAllTools",
		query="SELECT t FROM Tool t"
	),
	// Gets a tool by id
	@NamedQuery(
		name="Tool.getToolById",
		query="SELECT t FROM Tool t WHERE t.toolId = :id"
	),
	// Deletes a tool by ID
	@NamedQuery(
		name="Tool.deleteToolById",
		query="delete from Tool t where t.toolId = :toolId"
	)
})
@Entity
@Table(name="TL_T")
@XmlRootElement(name="tool")
public class Tool implements Serializable, Comparable<Tool> {

	// Constants used for the Requisites field. 
	// !!! DON'T CHANGE VALUES!!! Matches values in ToolFromXML.java.
	public static final int UNDEFINED_REQUISITES = 0;
	public static final int NATIVE 			= 1;
	public static final int IOS				= 2;
	public static final int ANDROID			= 4;
	public static final int WINDOWS_PHONE	= 8;
	public static final int BLACKBERRY		= 16;
	public static final int NON_NATIVE 		= 32;
	public static final int ALL_PLATFORMS 	= IOS | ANDROID | WINDOWS_PHONE | BLACKBERRY;
	public static final int ANY	 			= NATIVE | NON_NATIVE | IOS | ANDROID | WINDOWS_PHONE | BLACKBERRY;


	private static final long serialVersionUID = 4709451428489759275L;

	/**
	 * ID for this <code>Tool</code>
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long toolId;

	/**
	 * Alias for this <code>Tool</code>
	 */
	@Column(name="ALIAS")
	private String alias;

	/**
	 * Title for this <code>Tool</code>
	 */
	@Column(name="TTL")
	private String title;

	/**
	 * Subtitle for this <code>Tool</code>
	 */
	@Column(name="SB_TTL")
	private String subtitle;

	/**
	 * Url for this <code>Tool</code>
	 */
	@Column(name="URL")
	private String url;

	/**
	 * Description for this <code>Tool</code>
	 */
	@Column(name="DESC_TXT")
	private String description;

	/**
	 * Icon URL for this <code>Tool</code>
	 */
	@Column(name="ICN_URL")
	private String iconUrl;

	/**
	 * Contacts for this <code>Tool</code>
	 */
	@Column(name="CONTACTS")
	private String contacts;

	/**
	 * Keywords for this <code>Tool</code>
	 */
	@Column(name="KEYWORDS")
	private String keywords;

	/**
	 * Requisites for this <code>Tool</code>
	 */
	@Column(name="REQUISITES")
	private int requisites;

	/**
	 * ACL Viewing ID for this <code>Tool</code>
	 */
	@Column(name="ACL_VIEW_ID", insertable=false, updatable=false)
	private Long aclViewingId;

	/**
	 * ACL Publising ID for this <code>Tool</code>
	 */
	@Column(name="ACL_PUB_ID", insertable=false, updatable=false)
	private Long aclPublishingId;

	/**
	 * Viewing permissions for this <code>Tool</code>
	 */
	@OneToOne(fetch=FetchType.EAGER, cascade={ CascadeType.ALL } )
	@JoinColumn(name="ACL_VIEW_ID", referencedColumnName="ID", nullable=true)
	private AclExpression viewingPermission;

	/**
	 * Publishing permissions for this <code>Tool</code>
	 */
	@OneToOne(fetch=FetchType.EAGER, cascade={ CascadeType.ALL } )
	@JoinColumn(name="ACL_PUB_ID", referencedColumnName="ID", nullable=true)
	private AclExpression publishingPermission;

	/**
	 * Version for this <code>Tool</code>
	 */
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;

	/**
	 * Badge count for this <code>Tool</code>
	 */
	@Transient
	private String badgeCount;

	/**
	 * Badge text for this <code>Tool</code>
	 */
	@Transient
	private String badgeText;

	/**
	 * Gets the toolId for this <code>Tool</code>.
	 * @return the toolId
	 */
	public Long getToolId() {
		return toolId;
	}

	/**
	 * Sets the toolId for this <code>Tool</code>.
	 * @param toolId the toolId to set
	 */
	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	/**
	 * Gets the alias for this <code>Tool</code>.
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias for this <code>Tool</code>.
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the title for this <code>Tool</code>.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title for this <code>Tool</code>.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the url for this <code>Tool</code>.
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url for this <code>Tool</code>.
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the description for this <code>Tool</code>.
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description for this <code>Tool</code>.
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the badgeCount for this <code>Tool</code>.
	 * @return the badgeCount
	 */
	public String getBadgeCount() {
		return badgeCount;
	}

	/**
	 * Sets the badgeCount for this <code>Tool</code>.
	 * @param badgeCount the badgeCount to set
	 */
	public void setBadgeCount(String badgeCount) {
		this.badgeCount = badgeCount;
	}

	/**
	 * Gets the iconUrl for this <code>Tool</code>.
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}

	/**
	 * Sets the iconUrl for this <code>Tool</code>.
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	/**
	 * Gets the versionNumber for this <code>Tool</code>.
	 * @return the versionNumber
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Sets the versionNumber for this <code>Tool</code>.
	 * @param versionNumber the versionNumber to set
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Tool that) {
		if (that == null) {
			return -1;
		}
		return this.title.compareTo(that.title);
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object that) {
		if (that == null) {
			return false;
		}
		if (that instanceof Tool) {
			return this.toolId.equals(((Tool)that).toolId);
		}
		return false;
	}


	/**
	 * Gets the contacts for this <code>Tool</code>.
	 * @return the contacts
	 */
	public String getContacts() {
		return contacts;
	}

	/**
	 * Sets the contacts for this <code>Tool</code>.
	 * @param contacts the contacts to set
	 */
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	/**
	 * Gets the requisites for this <code>Tool</code>.
	 * @return the requisites
	 */
	public int getRequisites() {
		return requisites;
	}

	/**
	 * Sets the requisites for this <code>Tool</code>.
	 * @param requisites the requisites to set
	 */
	public void setRequisites(int requisites) {
		this.requisites = requisites;
	}


	/**
	 * Gets the subtitle for this <code>Tool</code>.
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * Sets the subtitle for this <code>Tool</code>.
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * Gets the keywords for this <code>Tool</code>.
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords for this <code>Tool</code>.
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Gets the badgeText for this <code>Tool</code>.
	 * @return the badgeText
	 */
	public String getBadgeText() {
		return badgeText;
	}

	/**
	 * Sets the badgeText for this <code>Tool</code>.
	 * @param badgeText the badgeText to set
	 */
	public void setBadgeText(String badgeText) {
		this.badgeText = badgeText;
	}

	/**
	 * Gets the aclViewingId for this <code>Tool</code>.
	 * @return the aclViewingId
	 */
	public Long getAclViewingId() {
		return aclViewingId;
	}

	/**
	 * Sets the aclViewingId for this <code>Tool</code>.
	 * @param aclViewingId the aclViewingId to set
	 */
	public void setAclViewingId(Long aclViewingId) {
		this.aclViewingId = aclViewingId;
	}

	/**
	 * Gets the aclPublishingId for this <code>Tool</code>.
	 * @return the aclPublishingId
	 */
	public Long getAclPublishingId() {
		return aclPublishingId;
	}

	/**
	 * Sets the aclPublishingId for this <code>Tool</code>.
	 * @param aclPublishingId the aclPublishingId to set
	 */
	public void setAclPublishingId(Long aclPublishingId) {
		this.aclPublishingId = aclPublishingId;
	}

	/**
	 * Gets the viewingPermission for this <code>Tool</code>.
	 * @return the viewingPermission
	 */
	public AclExpression getViewingPermission() {
		return viewingPermission;
	}

	/**
	 * Sets the viewingPermission for this <code>Tool</code>.
	 * @param viewingPermission the viewingPermission to set
	 */
	public void setViewingPermission(AclExpression viewingPermission) {
		this.viewingPermission = viewingPermission;
	}

	/**
	 * Gets the publishingPermission for this <code>Tool</code>.
	 * @return the publishingPermission
	 */
	public AclExpression getPublishingPermission() {
		return publishingPermission;
	}

	/**
	 * Sets the publishingPermission for this <code>Tool</code>.
	 * @param publishingPermission the publishingPermission to set
	 */
	public void setPublishingPermission(AclExpression publishingPermission) {
		this.publishingPermission = publishingPermission;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toJson() {
		return "{\"toolId\":" 			+ toolId + "," +
				"\"alias\":\"" 		+ alias + "\"," +
				"\"title\":\"" 		+ title + "\"," +
				"\"subtitle\":\"" 	+ subtitle + "\"," + 
				"\"url\":\"" 			+ url + "\"," +
				"\"description\":\"" 	+ description + "\"," +
				"\"iconUrl\":\"" 		+ iconUrl + "\"," +
				"\"contacts\":\"" 	+ contacts + "\"," + 
				"\"keywords\":\"" 	+ keywords + "\"," +
				"\"requisites\":\"" 	+ requisites + "\"," +
				"\"badgeCount\":\"" 	+ badgeCount + "\"," +
				"\"badgeText\":\"" 	+ badgeText + "\"}";
	}

}
