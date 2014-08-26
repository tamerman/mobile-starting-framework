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

package org.kuali.mobility.auth.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.auth.entity.AuthResponse;
import org.kuali.mobility.util.DigestConstants;
import org.kuali.mobility.util.DigestUtil;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.shared.EncodingTypes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Implementation of the <code>AuthDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
@Repository
public class AuthDaoImpl implements AuthDao, ApplicationContextAware {
	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(AuthDaoImpl.class);

	@Resource(name = "kmeUserDao")
	private UserDao userDao;

	private ApplicationContext applicationContext;

	private EncodingTypes defaultEncoding;
	private DigestConstants defaultDigest;

	private String pepper;
	private String encoding;
	private String digest;

	public void init() {
		if (DigestConstants.MD5.equalsName(getDigest())) {
			setDefaultDigest(DigestConstants.MD5);
		} else {
			setDefaultDigest(DigestConstants.SHA1);
		}
		if (EncodingTypes.BASE64.equalsName(getEncoding())) {
			setDefaultEncoding(EncodingTypes.BASE64);
		} else {
			setDefaultEncoding(EncodingTypes.HEX);
		}
	}

	public AuthResponse authenticate(String loginName, String password) {
		AuthResponse response = (AuthResponse) getApplicationContext().getBean("authResponse");

		StringBuilder builder = new StringBuilder();
		builder.append(password);
		builder.append(loginName);
		builder.append(getPepper());
		builder.append(password);
		builder.append(new StringBuilder(getPepper()).reverse().toString());
		String hashWord = DigestUtil.getDigest(builder.toString(), getDefaultDigest(), getDefaultEncoding());

		if (null != loginName && !loginName.isEmpty()) {
			User user = getUserDao().loadUserByLoginName(loginName);
			if (null != user && user.getPassword().equals(hashWord)) {
				// User credentials match, authenticate the user.
				response.setDidAuthenticate(true);
			} else {
				response.setMessage("Unable to verify login credentials.");
			}
			response.setUser(user);
		} else {
			response.setMessage("Login name not specified.");
			response.setDidAuthenticate(false);
		}

		return response;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public EncodingTypes getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setDefaultEncoding(EncodingTypes defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public DigestConstants getDefaultDigest() {
		return defaultDigest;
	}

	public void setDefaultDigest(DigestConstants defaultDigest) {
		this.defaultDigest = defaultDigest;
	}

	public String getPepper() {
		return pepper;
	}

	public void setPepper(String pepper) {
		this.pepper = pepper;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}
}
