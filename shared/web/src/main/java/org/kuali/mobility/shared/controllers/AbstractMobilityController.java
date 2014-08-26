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

package org.kuali.mobility.shared.controllers;

import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.user.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Base controller that provides utilities for managing authorization
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
public class AbstractMobilityController {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractMobilityController.class);

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	public boolean isAllowedAccess(String roleName, HttpServletRequest request) {
		boolean isAllowed = false;

		if (roleName == null || roleName.isEmpty()) {
			isAllowed = true;
		} else if (request.getSession() == null) {
			LOG.info("Request Session NULL");
			isAllowed = false;
		} else {
			User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
			if (user == null || user.isPublicUser()) {
				LOG.info("User NULL or Public");
				isAllowed = false;
			} else if (user.getGroups() == null || user.getGroups().isEmpty()) {
				LOG.info("UserGrous NULL or isEmpty()");
				isAllowed = false;
			} else {
				for (Group group : user.getGroups()) {
					LOG.info(group.getName());
					if (group.getName().equalsIgnoreCase(roleName)) {
						isAllowed = true;
						break;
					}
				}
			}
		}
//        return true;
		return isAllowed;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
