/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.feedback.service;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.email.service.EmailService;
import org.kuali.mobility.feedback.dao.FeedbackDao;
import org.kuali.mobility.feedback.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.LocaleResolver;

import java.sql.Timestamp;
import java.util.Locale;

public class FeedbackServiceImpl implements FeedbackService {

	private static final Logger LOG = LoggerFactory
			.getLogger(FeedbackServiceImpl.class);

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private FeedbackDao feedbackDao;

	@Autowired
	private EmailService emailService;

	private String toEmailAddress;

	private String fromEmailAddress;

    private String emailSubject;

    /**
     * A reference to the Spring Message source
     */
    @Resource(name="messageSource")
    private MessageSource messageSource;

    @Override
    public void saveFeedback(Feedback feedback, Locale userLocale) {
        if (feedback!=null) {
            //// reset device type to localized value
            String deviceTypeVal = getLocalisedMessage(feedback.getDeviceType(), userLocale);
            feedback.setDeviceType(deviceTypeVal);

            saveFeedback(feedback);
        }

    }

    @Override
	@Transactional
	public void saveFeedback(Feedback feedback) {
        if (feedback.getPostedTimestamp()==null) {
            feedback.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
        }
		feedbackDao.saveFeedback(feedback);
		sendEmail(feedback);
	}

	private void sendEmail(Feedback f) {
		try {
			String fromEmail = fromEmailAddress;
			if (f.getEmail() != null) {
				fromEmail = f.getEmail();
			}
			emailService.sendEmail(f.toString(), emailSubject, toEmailAddress, fromEmail);
		} catch (Exception e) {
			LOG.error("Error sending feedback email " + f.getFeedbackId(), e);
		}
	}


    /**
     * Gets a localised message
     * @param key Key of the message to get
     * @param locale
     * @param args Arguments for the message
     * @return A localised string
     */
    private String getLocalisedMessage(String key, Locale locale, String...args){
        if (locale==null) {
            locale = Locale.ENGLISH;
        }
        return messageSource.getMessage(key, args, locale);
    }

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public FeedbackDao getFeedbackDao() {
		return feedbackDao;
	}

	public void setFeedbackDao(FeedbackDao feedbackDao) {
		this.feedbackDao = feedbackDao;
	}

	public EmailService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}

	public String getToEmailAddress() {
		return toEmailAddress;
	}

	public void setToEmailAddress(String toEmailAddress) {
		this.toEmailAddress = toEmailAddress;
	}

	public String getFromEmailAddress() {
		return fromEmailAddress;
	}

	public void setFromEmailAddress(String fromEmailAddress) {
		this.fromEmailAddress = fromEmailAddress;
	}

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

}
