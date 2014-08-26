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

package org.kuali.mobility.push.service;

import org.kuali.mobility.push.entity.Preference;

import java.util.List;

/**
 * Blank implementation of the push service.
 * This is only usefull for unit tests, or KME implementations that does
 * not want to implement push notifications
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NoopPreferenceServiceImpl implements PreferenceService {


	@Override
	public List<Preference> findPreferencesByUsername(String username) {
		return null;
	}

	@Override
	public Preference findPreference(String username, String shortName) {
		return null;
	}

	@Override
	public Preference findPreference(String username, Long senderId) {
		return null;
	}

	@Override
	public Preference findPreference(long id) {
		return null;
	}

	@Override
	public List<String> findUsersThatAllowedSender(String senderKey) {
		return null;
	}

	@Override
	public Preference savePreference(Preference preference) {
		return null;
	}

	@Override
	public boolean removePreference(Long preferenceId) {
		return false;
	}
}
