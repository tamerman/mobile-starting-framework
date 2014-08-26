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

package org.kuali.mobility.icons.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Class representing a web icon.
 * <p/>
 * A WebIcon is an icon that can be used on a web page.
 * Each icon has a name and an optional theme.
 * <p/>
 * Normally you would not need to directly work with the WebIcon class,
 * but more often use the icons.tld inconjuction with css to use the icons.
 * <p/>
 * Icons are configured using an xml configuration file that should be in
 * the classpath. The IconsService will import and configure all icons
 * during boot time. See {@link org.kuali.mobility.icons.service.IconsServiceImpl IconsServiceImpl} for more info.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@NamedQueries({
		/**
		 * Find a web icon with the specified name and theme
		 */
		@NamedQuery(
				name = "WebIcon.findWebIcon",
				query = "SELECT wi FROM WebIcon wi WHERE name = :name AND theme = :theme ORDER BY theme ASC"),
		/**
		 * Find a web icon with the specified name and theme
		 */
		@NamedQuery(
				name = "WebIcon.findWebIconNoTheme",
				query = "SELECT wi FROM WebIcon wi WHERE name = :name AND theme IS NULL ORDER BY theme ASC"),
		/**
		 * Find all web icons
		 */
		@NamedQuery(
				name = "WebIcon.getAllWebIcon",
				query = "SELECT wi FROM WebIcon wi ORDER BY name, theme ASC"),
		/**
		 * Checks if a icon with the specified theme exists
		 */
		@NamedQuery(
				name = "WebIcon.exists",
				query = "SELECT COUNT(wi) FROM WebIcon wi WHERE name = :name AND theme = :theme"),
		/**
		 * Checks if a icon with the specified theme exists
		 */
		@NamedQuery(
				name = "WebIcon.existsNoTheme",
				query = "SELECT COUNT(wi) FROM WebIcon wi WHERE name = :name AND theme IS NULL")
})
@Entity
@Table(
		name = "WEB_ICON",
		uniqueConstraints = {
				@UniqueConstraint(
						name = "UN_NAME_THEME",
						columnNames = {"NM", "THEME"}
				)
		}
)
public class WebIcon {

	/**
	 * Default path where the icons will be located
	 */
	public static final String DEFAULT_PATH = "images/service-icons";

	@GeneratedValue(strategy = GenerationType.TABLE)
	@Id
	@Column(name = "ID")
	private Long id;

	/**
	 * Name of the icon.
	 * There may be more than on icon with the same name
	 * but the combination of name and theme must be unique
	 */
	@Column(name = "NM")
	private String name;

	/**
	 * Theme this icon should be used for.
	 * Each icon name may have many themes.
	 * The combination of icon name and theme must be unique.
	 * <br><br>
	 * Themes are intented to be used as a different look for the same icon.
	 * Example: If you want to have the tool icons look different per campus, you
	 * can apply the campus code as a theme, and configure accordingly.
	 */
	@Column(name = "THEME")
	private String theme;


	/**
	 * Path for icon from root of web app
	 */
	@Column(name = "PATH", nullable = false)
	private String path = DEFAULT_PATH;

	/**
	 * Creates a new instance of a <code>WebIcon</code>
	 */
	public WebIcon() {
	}

	/**
	 * Creates a new instance of a <code>WebIcon</code>
	 *
	 * @param name  Name of the icon.
	 * @param path  Path to the icon
	 * @param theme Theme for the icon
	 */
	public WebIcon(String name, String theme, String path) {
		this.name = name;
		this.theme = theme;
		this.path = path;
	}

	/**
	 * Returns the name of the icon.
	 *
	 * @return Name of the icon.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the icon
	 *
	 * @param name Name of the icon.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the path to the icon
	 *
	 * @return Path to the icon
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path to the icon
	 *
	 * @param path Path to the icon.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the theme of the icon.
	 *
	 * @return Theme of the icon.
	 */
	public String getTheme() {
		return theme;
	}

	/**
	 * Sets the theme for the icon.
	 *
	 * @param theme Theme for the icon;
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * Gets the id of the icon
	 *
	 * @return Id of the icon
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of the icon.
	 *
	 * @param id ID of the icon.
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
