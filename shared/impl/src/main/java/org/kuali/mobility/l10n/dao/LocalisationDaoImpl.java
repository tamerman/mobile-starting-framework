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

package org.kuali.mobility.l10n.dao;

import org.kuali.mobility.l10n.entity.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Implementation of the <code>LocalisationDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Repository(value = "localisationDao")
public class LocalisationDaoImpl implements LocalisationDao {

	/**
	 * Name of the localisation cache
	 */
	private static final String L10N_CACHE_NAME = "l10nCache";

	/**
	 * A reference to the <code>EntityManager</code>
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * A reference to the <code>CacheManager</code>
	 */
	@Autowired
	@Qualifier("cacheManager")
	private CacheManager cacheManager;


	/* (non-Javadoc)
	 * @see org.kuali.mobility.l10n.dao.LocalisationDao#getLocalisedString(java.lang.String, java.lang.String)
	 */
	@Override
	@Cacheable(value = "l10nCache", key = "#locale.concat('-').concat(#code)")
	public LocalisedString getLocalisedString(String code, String locale) {
		Query q = entityManager.createNamedQuery("LocalisedString.getString");
		q.setParameter("code", code);
		q.setParameter("locale", locale);

		LocalisedString ls = null;
		try {
			ls = (LocalisedString) q.getSingleResult();
		} catch (NoResultException nre) {
			// This is okay, there just isn't such a string
		}
		return ls;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.l10n.dao.LocalisationDao#saveLocalisedString(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void saveLocalisedString(String code, String locale, String content) {
		LocalisedString ls = getLocalisedString(code, locale);
		if (ls == null) {
			ls = new LocalisedString();
		}
		ls.setCode(code);
		ls.setContent(content);
		ls.setLocale(locale);

		if (ls.getId() == null) {
			entityManager.persist(ls);
		} else {
			entityManager.merge(ls);
		}
		// Cache String
		Cache cache = cacheManager.getCache(L10N_CACHE_NAME);
		cache.put(ls.getLocale() + "-" + ls.getCode(), ls);
	}

}
