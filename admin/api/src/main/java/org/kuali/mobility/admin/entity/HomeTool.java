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

package org.kuali.mobility.admin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Defines an object to link HomeScreen objects with Tool objects
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 1.0.0
 */
@Entity
@Table(name = "KME_HM_TL_T")
@XmlRootElement(name = "homeTool")
public class HomeTool implements Serializable, Comparable<HomeTool> {

	private static final long serialVersionUID = -8942674782383943102L;

	/**
	 * ID for this <code>HomeTool</code>.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long homeToolId;

	/**
	 * Home Screen ID for this <code>HomeTool</code>.
	 */
	@Column(name = "HM_SCRN_ID", insertable = false, updatable = false)
	private Long homeScreenId;

	/**
	 * Tool ID for this <code>HomeTool</code>.
	 */
	@Column(name = "TL_ID", insertable = false, updatable = false)
	private Long toolId;

	/**
	 * Order index for this <code>HomeTool</code>.
	 */
	@Column(name = "ORDR")
	private int order;

	/**
	 * Home screen this <code>HomeTool</code> is linked too.
	 */
	@ManyToOne
	@JoinColumn(name = "HM_SCRN_ID")
	private HomeScreen homeScreen;

	/**
	 * Tool <code>HomeTool</code> is linked too.
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TL_ID")
	private Tool tool;

	/**
	 * Version of this <code>HomeTool</code>.
	 */
	@Version
	@Column(name = "VER_NBR")
	private Long versionNumber;

	/**
	 * Creates a new instance of a <code>HomeTool</code>
	 */
	public HomeTool() {
	}

	/**
	 * Creates a new instance of a <code>HomeTool</code>.
	 *
	 * @param homeScreen <code>Homescreen</code> this <code>HomeTool</code> is linked to.
	 * @param tool       <code>Tool</code> this <code>HomeTool</code> is linked to.
	 * @param order      Order for this <code>HomeTool</code>.
	 */
	public HomeTool(HomeScreen homeScreen, Tool tool, int order) {
		this.homeScreen = homeScreen;
		this.homeScreenId = homeScreen.getHomeScreenId();
		this.tool = tool;
		this.toolId = tool.getToolId();
		this.order = order;
	}


	/**
	 * Gets the homeToolId for this <code>HomeTool</code>.
	 *
	 * @return the homeToolId
	 */
	public Long getHomeToolId() {
		return homeToolId;
	}

	/**
	 * Sets the homeToolId for this <code>HomeTool</code>.
	 *
	 * @param homeToolId the homeToolId to set
	 */
	public void setHomeToolId(Long homeToolId) {
		this.homeToolId = homeToolId;
	}

	/**
	 * Gets the homeScreenId for this <code>HomeTool</code>.
	 *
	 * @return the homeScreenId
	 */
	public Long getHomeScreenId() {
		return homeScreenId;
	}

	/**
	 * Sets the homeScreenId for this <code>HomeTool</code>.
	 *
	 * @param homeScreenId the homeScreenId to set
	 */
	public void setHomeScreenId(Long homeScreenId) {
		this.homeScreenId = homeScreenId;
	}

	/**
	 * Gets the toolId for this <code>HomeTool</code>.
	 *
	 * @return the toolId
	 */
	public Long getToolId() {
		return toolId;
	}

	/**
	 * Sets the toolId for this <code>HomeTool</code>.
	 *
	 * @param toolId the toolId to set
	 */
	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	/**
	 * Gets the order for this <code>HomeTool</code>.
	 *
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Sets the order for this <code>HomeTool</code>.
	 *
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Gets the versionNumber for this <code>HomeTool</code>.
	 *
	 * @return the versionNumber
	 */
	public Long getVersionNumber() {
		return versionNumber;
	}

	/**
	 * Sets the versionNumber for this <code>HomeTool</code>.
	 *
	 * @param versionNumber the versionNumber to set
	 */
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * Gets the homeScreen for this <code>HomeTool</code>.
	 *
	 * @return the homeScreen
	 */
	@XmlTransient
	public HomeScreen getHomeScreen() {
		return homeScreen;
	}

	/**
	 * Sets the homeScreen for this <code>HomeTool</code>.
	 *
	 * @param homeScreen the homeScreen to set
	 */
	public void setHomeScreen(HomeScreen homeScreen) {
		this.homeScreen = homeScreen;
	}

	/**
	 * Gets the tool for this <code>HomeTool</code>.
	 *
	 * @return the tool
	 */
	public Tool getTool() {
		return tool;
	}

	/**
	 * Sets the tool for this <code>HomeTool</code>.
	 *
	 * @param tool the tool to set
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(HomeTool that) {
		if (that == null) {
			return -1;
		}
		if (this.order == that.order) {
			return 0;
		}
		return this.order < that.order ? -1 : 1;
	}


	public String toJson() {
		return "{\"homeToolId\":" + homeToolId + "," +
				"\"homeScreenId\":" + homeScreenId + "," +
				"\"toolId\":" + toolId + "," +
				"\"order\":" + order + "," +
				"\"tool\":" + tool.toJson() + "," +
				"\"versionNumber\":" + versionNumber + "}";
	}


}
