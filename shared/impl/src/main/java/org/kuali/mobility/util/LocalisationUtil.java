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

package org.kuali.mobility.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * A bean used to help with localisation
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Component(value = "localisationUtil")
@DependsOn(value = {"supportedLanguages"})
public class LocalisationUtil {

	/**
	 * A reference to the list of supported languages
	 */
	@Resource(name = "supportedLanguages")
	private List<String> supportedLanguages;

	/**
	 * Returns a map of language code to String for that language
	 *
	 * @param fieldName
	 * @param request
	 * @return
	 */
	public Map<String, String> getLocalisedString(String fieldName, HttpServletRequest request) {
		Map<String, String> fieldMap = new HashMap<String, String>();

		String value;
		for (String language : this.supportedLanguages) {
			value = request.getParameter(fieldName + "." + language);
			if (value != null) {
				fieldMap.put(language, value);
			}
		}
		return fieldMap;
	}

	/**
	 * Gets the localised code for a field.
	 * This is the code that is used in localised properties files, or as a key
	 * for the database.
	 *
	 * @param fieldName
	 * @param request
	 * @return
	 */
	public String getLocalisedStringCode(String fieldName, HttpServletRequest request) {
		String code = request.getParameter(fieldName + ".code");
		return code == null ? generateNewCode() : code;
	}

	/**
	 * Gets the list of supported languages
	 *
	 * @return
	 */
	public List<String> getSupportedLanguages() {
		return Collections.unmodifiableList(supportedLanguages);
	}

	/**
	 * Returns true if the language is a language that we support.
	 *
	 * @param language
	 * @return
	 */
	public boolean isSupportedLanguage(String language) {
		return supportedLanguages.contains(language);
	}

	/**
	 * Generate a new
	 *
	 * @return
	 */
	private String generateNewCode() {
		// TODO use something better
		return System.currentTimeMillis() + "";
	}
}
