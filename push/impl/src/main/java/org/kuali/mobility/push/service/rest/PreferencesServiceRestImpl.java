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

package org.kuali.mobility.push.service.rest;

import org.kuali.mobility.push.entity.Preference;
import org.kuali.mobility.push.service.PreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the <code>IconsDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class PreferencesServiceRestImpl implements PreferenceService {

	/**
	 * Reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PreferencesServiceRestImpl.class);

	/**
	 * Reference to the rest client
	 */
	private PreferenceServiceRest restClient;

	@Override
	public List<Preference> findPreferencesByUsername(String username) {
		try {
			return restClient.findPreferencesByUsername(username).getPreferences();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return new ArrayList<Preference>(0);
		}
	}

	@Override
	public Preference findPreference(String username, String shortName) {
		try {
			return restClient.findPreference(username, shortName).getPreference();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public Preference findPreference(String username, Long senderId) {
		try {
			return restClient.findPreference(username, senderId).getPreference();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public Preference findPreference(long id) {
		try {
			return restClient.findPreference(id).getPreference();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public List<String> findUsersThatAllowedSender(String senderKey) {
		try {
			return restClient.findUsersThatAllowedSender(senderKey).getList();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return new ArrayList<String>(0);
		}

	}

	@Override
	public Preference savePreference(Preference preference) {
		try {
			return restClient.savePreference(preference).getPreference();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}

	}

	@Override
	public boolean removePreference(Long preferenceId) {
		return restClient.removePreference(preferenceId);
	}

	public void setRestClient(PreferenceServiceRest restClient) {
		this.restClient = restClient;
	}
}
