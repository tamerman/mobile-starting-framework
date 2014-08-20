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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Defines a home screen with a collection of tools
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@NamedQueries({
	// Gets all the Home Screens
	@NamedQuery(
		name="HomeScreen.getAllHomeScreens",
		query="SELECT h FROM HomeScreen h"
	),
	// Gets a home screen by ID
	@NamedQuery(
		name="HomeScreen.getHomeScreenById",
		query="SELECT h FROM HomeScreen h WHERE h.homeScreenId = :id"
	),
	// Gets a home screen by alias
	@NamedQuery(
		name="HomeScreen.getHomeScreenByAlias",
		query="SELECT h FROM HomeScreen h WHERE h.alias = :alias"
	),
	// Deleted a home screen tools by home screen ID
	@NamedQuery(
		name="HomeScreen.deleteHomeToolsByHomeScreenId",
		query="DELETE FROM HomeTool ht WHERE ht.homeScreenId = :id"
	),
	// Deleted a home screen by ID
	@NamedQuery(
		name="HomeScreen.deleteHomeScreenById",
		query="DELETE FROM HomeScreen h WHERE h.homeScreenId = :id"
	),

})
@Entity
@Table(name="KME_HM_SCRN_T")
@XmlRootElement(name="homeScreen")
public class HomeScreen implements Serializable {

	private static final long serialVersionUID = 4947101996672004361L;

	/**
	 * Tiled home screen layout
	 */
	public static final String LAYOUT_LIST = "list";

	/**
	 * List home screen layout
	 */
	public static final String LAYOUT_TILES = "tiles";

	/** List of available screen layouts */
	public static final String[] LAYOUTS = {LAYOUT_LIST, LAYOUT_TILES};
	
	/**
	 * ID of this Home Screen
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long homeScreenId;

	/**
	 * Alias for this <code>HomeScreen</code>.
	 */
	@Column(name="ALIAS")
	private String alias;

	/**
	 * Title for this <code>HomeScreen</code>.
	 */
	@Column(name="TTL")
	private String title;

	/**
	 * Tools that will be displayed on this <code>HomeScreen</code>.
	 */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="homeScreen")
	private List<HomeTool> homeTools;

	/**
	 * Version number for this <code>HomeScreen</code>.
	 */
	@Version
	@Column(name="VER_NBR")
	private Long versionNumber;

	/**
	 * Creates a new instance of a <code>HomeScreen</code>
	 */
	public HomeScreen() {
		homeTools = new ArrayList<HomeTool>();
	}

	/**
	 * Gets the Tools that can be displayed on this <code>HomeScreen</code>
	 * @return the HomeTool objects associated with this HomeScreen
	 */
	public List<HomeTool> getHomeTools() {
		return homeTools;
	}

	/**
	 * set the HomeTool objects
	 * @param homeTools
	 */
	public void setHomeTools(List<HomeTool> homeTools) {
		this.homeTools = homeTools;
	}

	/**
	 * set the HomeTools collection with an array
	 * @param homeTools
	 */
	public void setHomeTools(HomeTool[] homeTools) {
		this.homeTools = Arrays.asList(homeTools);
	}

	/**
	 * Gets the homeScreenId for this <code>HomeScreen</code>.
	 * @return the homeScreenId
	 */
	public Long getHomeScreenId() {
		return homeScreenId;
	}

	/**
	 * Sets the homeScreenId for this <code>HomeScreen</code>.
	 * @param homeScreenId the homeScreenId to set
	 */
	public void setHomeScreenId(Long homeScreenId) {
		this.homeScreenId = homeScreenId;
	}

	/**
	 * Gets the alias for this <code>HomeScreen</code>.
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * Sets the alias for this <code>HomeScreen</code>.
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * Gets the title for this <code>HomeScreen</code>.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title for this <code>HomeScreen</code>.
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the versionNumber for this <code>HomeScreen</code>.
	 * @return the versionNumber
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Sets the versionNumber for this <code>HomeScreen</code>.
	 * @param versionNumber the versionNumber to set
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HomeScreen [homeScreenId=" + homeScreenId + ", alias=" + alias
				+ ", title=" + title + ", homeTools=" + homeTools
				+ ", versionNumber=" + versionNumber + "]";
	}

}
