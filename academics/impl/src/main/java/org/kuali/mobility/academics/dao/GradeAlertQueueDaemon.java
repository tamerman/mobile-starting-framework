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

package org.kuali.mobility.academics.dao;

import org.kuali.mobility.academics.entity.GradesPostedNotice;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.PushService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserAttribute;
import org.kuali.mobility.security.user.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.3.0
 */
public class GradeAlertQueueDaemon {
	private static Logger LOG = LoggerFactory.getLogger(GradeAlertQueueDaemon.class);

	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;

	@Autowired
	private GradesPostedNoticeDao gradeDao;

	@Autowired
	private PushService pushService;

	@Autowired
	@Qualifier("academicsProperties")
	private Properties academicsProperties;

	private static Thread backgroundThread;
	private int waitInterval = 30;

	private boolean shouldRun = true;

	public void init() {
		LOG.info("Starting Grade Alert daemon.");
		setBackgroundThread(new Thread(new BackgroundThread()));
		getBackgroundThread().setDaemon(true);
		getBackgroundThread().start();
	}

	public void cleanup() {
		LOG.info("Stopping Grade Alert daemon.");
	}

	protected void processGradeAlerts() {
		List<? extends GradesPostedNotice> gradesToProcess = getGradeDao().getGradesToProcess(false);
		for (GradesPostedNotice notice : gradesToProcess) {

			if (notice.getLoginName() == null || notice.getLoginName().trim().isEmpty()) {
				LOG.info("GradesPostedNotice uniqname is null or empty, should NOT happen!");
				//continue;
			} else {
				User user = getUserDao().loadUserByLoginName(notice.getLoginName());
				if (user == null) {
					LOG.info("No user record found for " + notice.getLoginName());
					//continue;
				} else {
					LOG.debug("Creating grade alert for " + user.getLoginName());
					List<UserAttribute> attributes = user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
					if (attributes != null && attributes.size() > 0) {
						// Attempt to reduce the number of attributes to one
						// since there should not be more than one.
						for (UserAttribute attribute : attributes) {
							if ("on".equalsIgnoreCase(attribute.getAttributeValue())) {
								// Process grade alert.
								Push push = new Push();
								push.setTitle(getAcademicsProperties().getProperty("academics.grade.alert.push.title"));
								push.setMessage(getAcademicsProperties().getProperty("academics.grade.alert.push.message"));
								push.setEmergency(true);
								push.setSender("RS5XcyVYoHSgnLVY2ZZw"); // TODO remove hardcoded sender Id
								push.setUrl(null);
								List<String> usernames = new ArrayList<String>();
								usernames.add(user.getLoginName());// TODO optomize this to rather send batches of users
								pushService.sendPush(push, usernames, null);
							} else {
								LOG.debug("GradeAlert Opt-in is not turned on for user: " + notice.getLoginName());
								//continue;
							}
						}
					}
				}
			}

			notice.setTimestampProcessed(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			notice.setInProcess(false);
			getGradeDao().saveGradesPostedNotice(notice);
		}
	}

	public static Thread getBackgroundThread() {
		return backgroundThread;
	}

	public static void setBackgroundThread(Thread backgroundThread) {
		GradeAlertQueueDaemon.backgroundThread = backgroundThread;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GradesPostedNoticeDao getGradeDao() {
		return gradeDao;
	}

	public void setGradeDao(GradesPostedNoticeDao gradeDao) {
		this.gradeDao = gradeDao;
	}

	public int getWaitInterval() {
		return waitInterval;
	}

	public void setWaitInterval(int waitInterval) {
		this.waitInterval = waitInterval;
	}

	public boolean shouldRun() {
		return shouldRun;
	}

	public void setShouldRun(boolean shouldRun) {
		this.shouldRun = shouldRun;
	}

	public Properties getAcademicsProperties() {
		return academicsProperties;
	}

	public void setAcademicsProperties(Properties academicsProperties) {
		this.academicsProperties = academicsProperties;
	}

	private class BackgroundThread implements Runnable {
		@Override
		public void run() {
			while (shouldRun()) {
				try {
					/*
					 * Sleeping first for base KME since the test data
					 * is self referencing and the URLs won't be valid
					 * when the bean is initialized by Spring but will
					 * be once the web app is fully up and running.
					 */
					Thread.sleep(1000 * getWaitInterval());
					processGradeAlerts();
				} catch (InterruptedException ie) {
					LOG.error(ie.getLocalizedMessage(), ie);
				} catch (NullPointerException npe) {
					// No idea why we get these yet.
					LOG.error(npe.getLocalizedMessage(), npe);
				} catch (Exception e) {
					LOG.error(e.getLocalizedMessage(), e);
				}
			}
		}
	}
}
