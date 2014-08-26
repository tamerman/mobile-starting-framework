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

import org.kuali.mobility.l10n.dao.LocalisationDao;
import org.kuali.mobility.l10n.entity.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Implementation of the <code>LocalisationServiceTest</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Service(value = "localisationService")
public class LocalisationServiceImpl implements LocalisationService {

	/**
	 * A reference to the <code>LocalisationDao</code>.
	 */
	@Autowired
	@Qualifier("localisationDao")
	private LocalisationDao localisationDao;

	/* (non-Javadoc)
	 * @see org.kuali.mobility.l10n.service.LocalisationServiceTest#getLocalisedString(java.lang.String, java.lang.String)
	 */
	@Override
	public LocalisedString getLocalisedString(String code, String locale) {
		return localisationDao.getLocalisedString(code, locale);
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.l10n.service.LocalisationServiceTest#saveLocalisedString(java.lang.String, java.util.Map)
	 */
	@Override
	public void saveLocalisedString(String code, Map<String, String> stringLanguage) {
		for (String language : stringLanguage.keySet()) {
			this.localisationDao.saveLocalisedString(code, language, stringLanguage.get(language));
		}
	}

}
