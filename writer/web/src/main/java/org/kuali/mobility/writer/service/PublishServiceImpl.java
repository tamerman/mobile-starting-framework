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

package org.kuali.mobility.writer.service;

import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.email.service.EmailService;
import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.DeviceService;
import org.kuali.mobility.push.service.PreferenceService;
import org.kuali.mobility.push.service.PushService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.writer.entity.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class is responsible for publishing writer articles.
 * This implementation will create notification for users that has to be
 * notified of a published article, and will also send an email to the
 * editor of the article.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public class PublishServiceImpl implements PublishService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PublishServiceImpl.class);

	/**
	 * A cached thread pool which allows us to queue notification tasks
	 */
	private final ExecutorService exeService = Executors.newCachedThreadPool();

	/**
	 * A reference to the push service
	 */
	@Autowired
	private PushService pushService;

	/**
	 * A reference to the <code>EmailService</code>.
	 */
	@Autowired
	private EmailService emailService;

	/**
	 * A reference to the <code>MessageSource</code>
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	/**
	 * A reference to the writer tool's properties
	 */
	@Resource(name = "writerProperties")
	private Properties writerProperties;

	/**
	 * A reference to the <code>PreferenceService</code>
	 */
	@Autowired
	private PreferenceService preferenceService;

	/**
	 * A reference to the device service.
	 */
	@Autowired
	private DeviceService deviceService;

	@Override
	public void publishArticle(Article article, User user) {
		this.exeService.execute(new PublishRunner(article, user));
	}

	/**
	 * Find the users that should be notified about a published article.
	 * This implementation will find all users that did not opt out for push notifications for this tool's instance.
	 * <p/>
	 * You could override this method for your institution if you have other ways to find students to sent notifications
	 * for.
	 */
	protected List<String> findUsersToNotify(Article article) {

		// Get the sender id for the instance of the tool
		String senderId = getInstanceSenderId(article.getToolInstance());

		// If there is no sender ID this tool instance may not send push notifications
		if (StringUtils.isEmpty(senderId)) {
			LOG.info("This tool instance does not have a sender ID, therefore it may not send push notifications");
			return Collections.EMPTY_LIST;
		}

		// Get all the registered devices
		return preferenceService.findUsersThatAllowedSender(senderId);
	}

	private String getInstanceSenderId(String instance) {
		String property = "writer.senderId." + instance.toLowerCase();
		return writerProperties.getProperty(property);
	}

	/**
	 * A runnable that will be used to send the notification using a device
	 * specific implementation of the send service.
	 */
	private final class PublishRunner implements Runnable {

		/**
		 * Article being published
		 */
		private final Article article;

		/**
		 * User that is publishing the article
		 */
		private final User user;

		/**
		 * @param article
		 * @param user
		 */
		PublishRunner(Article article, User user) {
			this.article = article;
			this.user = user;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			String instance = this.article.getToolInstance();
			String senderKey = getInstanceSenderId(instance);
			if (senderKey == null) {
				LOG.warn("Not sending push notifications. Expected to have property \"writer.senderId." + instance.toLowerCase() + "\" set in writer.config.properties");
				return;
			}
			List<String> usernames = findUsersToNotify(article);
			Push push = sendPush(senderKey, this.article, usernames);

			// Send email to the user if the user has an email
			if (!StringUtils.isEmpty(user.getEmail())) {
				sendMail(push);
			}

		}

		/**
		 * Creates and send the Push message
		 *
		 * @param article   Article being published
		 * @param usernames Usernames to send notification to.
		 * @return
		 */
		private Push sendPush(String senderKey, Article article, List<String> usernames) {
			String toolInstance = this.article.getToolInstance();
			Push push = new Push();
			push.setEmergency(article.getCategory() == Article.CATEGORY_IMPORTANT);
			push.setMessage(article.getSynopsis());
			push.setSender(senderKey);
			push.setTitle(article.getHeading());
			push.setUrl("/writer/" + toolInstance + "/viewArticle?articleId=" + article.getId());
			return pushService.sendPush(push, usernames, null);
		}

		/**
		 * Sends email to the editor of the article
		 *
		 * @param push
		 */
		private void sendMail(Push push) {
			Locale locale = new Locale(user.getPreferredLanguage());
			String emailTo = user.getEmail();
			String displayName = user.getLoginName();
			Object[] values = new Object[]{
					displayName, // user display name
					article.getHeading(), // Article heading
					messageSource.getMessage(article.getTopic().getLabel(), null, locale), // Topic
					messageSource.getMessage(getCategoryLabel(), null, locale), // Category
					article.getSynopsis(), // Synopsis
					article.getText(), // Article text
					push.getTitle(),    // Push title
					push.getRecipients(), // Push recipients
					push.getMessage()};

			String emailSubject = messageSource.getMessage("writer.publish.email.subject", null, locale);
			String emailBody = messageSource.getMessage("writer.publish.email.body", values, locale);
			emailService.sendEmail(emailBody, emailSubject, emailTo, null);
		}

		private String getCategoryLabel() {
			long category = article.getCategory();
			if (category == Article.CATEGORY_GENERAL) {
				return "writer.common";
			} else {
				return "writer.important";
			}
		}

	}
}
