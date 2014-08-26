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


package org.kuali.mobility.notification.service;

import org.kuali.mobility.notification.dao.NotificationDao;
import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.entity.UserNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao notificationDao;

	@Override
	@Transactional
	public Notification findNotificationById(Long id) {
		return getNotificationDao().findNotificationById(id);
	}

	@Override
	@Transactional
	public List<Notification> findAllNotifications() {
		return getNotificationDao().findAllNotifications();
	}

	@Override
	@Transactional
	public List<Notification> findAllValidNotifications(Date date) {
		return getNotificationDao().findAllValidNotifications(date);
	}

	@Override
	@Transactional
	public Long saveNotification(Notification notification) {
		return getNotificationDao().saveNotification(notification);
	}

	@Override
	@Transactional
	public void deleteNotificationById(Long id) {
		getNotificationDao().deleteNotificationById(id);
	}

	@Override
	@Transactional
	public UserNotification findUserNotificationById(Long id) {
		return getNotificationDao().findUserNotificationById(id);
	}

	@Override
	@Transactional
	public UserNotification findUserNotificationByNotificationId(Long id) {
		return getNotificationDao().findUserNotificationByNotificationId(id);
	}

	@Override
	@Transactional
	public Long saveUserNotification(UserNotification userNotification) {
		return getNotificationDao().saveUserNotification(userNotification);
	}

	@Override
	@Transactional
	public void deleteUserNotificationById(Long id) {
		getNotificationDao().deleteUserNotificationById(id);
	}

	@Override
	@Transactional
	public List<UserNotification> findAllUserNotificationsByDeviceId(String id) {
		return getNotificationDao().findAllUserNotificationsByDeviceId(id);
	}

	@Override
	@Transactional
	public List<UserNotification> findAllUserNotificationsByPersonId(Long id) {
		return getNotificationDao().findAllUserNotificationsByPersonId(id);
	}

	public NotificationDao getNotificationDao() {
		return notificationDao;
	}

	public void setNotificationDao(NotificationDao notificationDao) {
		this.notificationDao = notificationDao;
	}
}
