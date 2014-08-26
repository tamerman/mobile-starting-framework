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

package org.kuali.mobility.security.authn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AuthenticationMapper {
	Logger LOG = LoggerFactory.getLogger(AuthenticationMapper.class);

	private String loginURL;
	private String logoutURL;

	private List<String> urlPatterns;

	public AuthenticationMapper(final String configFilePath) {
		urlPatterns = new ArrayList<String>();
		try {
			readProperties(configFilePath);
		} catch (Exception e) {
			LOG.error("Unable to load configuration file: " + configFilePath + "\n" + e.getLocalizedMessage());
		}
	}

	public boolean requiresAuthentication(final String url) {
		boolean requiresAuthN = false;
		for (String pattern : getUrlPatterns()) {
			if (url.startsWith(pattern)) {
				requiresAuthN = true;
			}
		}
		return requiresAuthN;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public String getLogoutURL() {
		return logoutURL;
	}

	public void setLogoutURL(String logoutURL) {
		this.logoutURL = logoutURL;
	}

	public List<String> getUrlPatterns() {
		return urlPatterns;
	}

	public void setUrlPatterns(List<String> urlPatterns) {
		this.urlPatterns = urlPatterns;
	}

	public void readProperties(final String configFilePath) throws Exception {
		Properties properties = new Properties();
		InputStream is = this.getClass().getResourceAsStream(configFilePath);
		properties.loadFromXML(is);

		for (Object s : properties.keySet()) {
			LOG.debug("Loading property " + (String) s + " = " + properties.getProperty((String) s));
			if (((String) s).startsWith(AuthenticationConstants.AUTH_PATH_PREFIX)) {
				this.urlPatterns.add(properties.getProperty((String) s));
			} else if ("loginURL".equalsIgnoreCase((String) s)) {
				this.setLoginURL(properties.getProperty((String) s));
			} else if ("logoutURL".equalsIgnoreCase((String) s)) {
				this.setLogoutURL(properties.getProperty((String) s));
			}
		}
	}
}
