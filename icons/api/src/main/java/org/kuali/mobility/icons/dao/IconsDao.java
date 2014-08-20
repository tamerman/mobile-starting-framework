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

package org.kuali.mobility.icons.dao;

import org.kuali.mobility.icons.entity.WebIcon;

import java.util.List;

/**
 * Data Access Object.
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public interface IconsDao {

    /**
     * Persists a <code>WebIcon</code>
     * @param icon Icon to persist.
     * @return The persisted instance of the Icon
     */
    public abstract WebIcon saveWebIcon(WebIcon icon);

    /**
     * Returns a list of all the persisted icons.
     * @return List of all the persisted icons.
     */
    public abstract List<WebIcon> getIcons();

    /**
     * Gets a specific icon.
     * @param iconName Name of the icon.
     * @param theme Theme of the icon
     * @return The icon.
     */
    public abstract WebIcon getIcon(String iconName, String theme);

    /**
     * Returns true if the icon with the specific name and theme exists
     * @param iconName Name of the icon to check existence of.
     * @param theme Theme of the icon.
     * @return True if the icons exists, false if it does not exists.
     */
    public abstract boolean iconExists(String iconName, String theme);
}
