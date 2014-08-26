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

package org.kuali.mobility.l10n.service;

import java.util.Map;

import org.kuali.mobility.l10n.entity.LocalisedString;

/**
 * A service used to retrieve and persist localised strings.
 * This service must NOT be used instead of a <code>MessageSource</code> to get localised messages.
 * This service should only be used to save new values for localised strings.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface LocalisationService {

	/**
	 * Gets a localised String.
	 *
	 * @param code   Code of the string to get
	 * @param locale The locale to get the String for.
	 * @return
	 */
	public LocalisedString getLocalisedString(String code, String locale);

	/**
	 * Saves a localised String.
	 * Usefull when saving a string for multiple languages.
	 *
	 * @param code           Code as which to save the String.
	 * @param stringLanguage Map of language code to the actual string.
	 */
	public void saveLocalisedString(String code, Map<String, String> stringLanguage);
}
