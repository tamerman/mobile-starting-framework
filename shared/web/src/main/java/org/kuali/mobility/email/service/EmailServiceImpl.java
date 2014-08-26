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

package org.kuali.mobility.email.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

/**
 * A service for doing the actual work of sending email.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOG = LoggerFactory
			.getLogger(EmailServiceImpl.class);

	@Autowired
	@Qualifier("kmeProperties")
	private Properties kmeProperties;

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	@Override
	public boolean sendEmail(String body, String subject, String emailAddressTo, String emailAddressFrom) {
		boolean emailSent = false;

		if (emailAddressFrom == null || StringUtils.isEmpty(emailAddressFrom)) {
			emailAddressFrom = kmeProperties.getProperty("email.from");
			if (emailAddressFrom == null) {
				return emailSent;
			}
		}

		if (emailAddressTo == null || StringUtils.isEmpty(emailAddressTo)) {
			return emailSent;
		}

		if (subject == null || StringUtils.isEmpty(subject)) {
			return emailSent;
		}

		if (body == null || StringUtils.isEmpty(body)) {
			return emailSent;
		}

		try {
			Email email = new SimpleEmail();
			email.setHostName(kmeProperties.getProperty("email.host"));
			email.setSmtpPort(Integer.parseInt(kmeProperties.getProperty("email.port")));
			email.setAuthenticator(new DefaultAuthenticator(kmeProperties.getProperty("email.username"),
					kmeProperties.getProperty("email.passsword")));
			email.setSSLOnConnect(true);
			email.setFrom(emailAddressFrom);
			email.setSubject(subject);
			email.setMsg(body);
			email.addTo(emailAddressTo);
			email.send();
			emailSent = true;
			LOG.debug("Mail Sent...");
		} catch (EmailException e) {
			LOG.error("Mail send failed...", e);
		}
		return emailSent;
	}

}
