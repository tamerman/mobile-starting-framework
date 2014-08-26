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


package org.kuali.mobility.icons.service;

import org.kuali.mobility.icons.dao.IconsDao;
import org.kuali.mobility.icons.entity.WebIcon;

import java.io.File;
import java.util.List;

/**
 * Service
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface IconsService {

	/**
	 * Sets the reference to the <code>IconsDao</code>.
	 *
	 * @param dao The reference to the <code>IconsDao</code>.
	 */
	public void setDao(IconsDao dao);

	/**
	 * Gets the reference to the <code>IconsDao</code>.
	 *
	 * @return The reference to the <code>IconsDao</code>.
	 */
	public IconsDao getDao();

	/**
	 * Returns a list of webicons
	 *
	 * @return
	 */
	public abstract List<WebIcon> getIcons();

	/**
	 * Gets a specific web icon
	 *
	 * @param icon  Name of the icon.
	 * @param theme Theme of the icon
	 * @return The WebIcon instance
	 */
	public abstract WebIcon getIcon(String icon, String theme);

	/**
	 * Returns true if the icon with the specific name and theme exists
	 *
	 * @param iconName Name of the icon to check existence of.
	 * @param theme    Theme of the icon.
	 * @return True if the icons exists, false if it does not exists.
	 */
	public abstract boolean iconExists(String iconName, String theme);

	/**
	 * Saves a WebIcon
	 *
	 * @param icon
	 * @return
	 */
	public abstract WebIcon saveWebIcon(WebIcon icon);

	public File getImageFile(String iconName, String iconTheme, int size);
}
