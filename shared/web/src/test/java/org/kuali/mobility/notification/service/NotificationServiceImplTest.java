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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.notification.dao.NotificationDao;
import org.kuali.mobility.notification.dao.NotificationDaoImpl;
import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.entity.UserNotification;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit = "mdot")
public class NotificationServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImplTest.class);

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private NotificationDao dao;
	private NotificationServiceImpl service;


	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		setService(new NotificationServiceImpl());
		setDao(new NotificationDaoImpl());
		getService().setNotificationDao(getDao());
		JpaUnitils.injectEntityManagerInto(getDao());
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testNotificationTransactions() {
		Notification n1 = new Notification();
		n1.setTitle("Test Title 1");
		n1.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec tempus massa et dapibus ullamcorper. Donec porta adipiscing dui vitae ullamcorper.");
		n1.setNotificationType(Long.valueOf(42));
		n1.setPrimaryCampus("ALL");
		Calendar cal = Calendar.getInstance();
		n1.setStartDate(new Timestamp(cal.getTimeInMillis()));
		cal.add(Calendar.DATE, 1);
		n1.setEndDate(new Timestamp(cal.getTimeInMillis()));

		assertTrue("Noticiation 1 ID is not null.", n1.getNotificationId() == null);

		Long n1ID = getService().saveNotification(n1);

		LOG.debug("Notification 1 ID is: " + n1ID);
		assertTrue("Notification 1 not saved.", n1ID != null);

		Notification n2 = new Notification();
		n2.setTitle("Test Title 2");
		n2.setMessage("Aenean convallis ut arcu vitae scelerisque. Sed id augue vestibulum, porta lacus a, malesuada erat. Vestibulum ut auctor ante.");
		n2.setNotificationType(Long.valueOf(9));
		n2.setPrimaryCampus("ALL");
		cal.add(Calendar.DATE, -1);
		n2.setStartDate(new Timestamp(cal.getTimeInMillis()));
		cal.add(Calendar.DATE, 2);
		n2.setEndDate(new Timestamp(cal.getTimeInMillis()));

		assertTrue("Noticiation 2 ID is not null.", n2.getNotificationId() == null);

		Long n2ID = getService().saveNotification(n2);

		assertTrue("Notification 2 not saved.", n2ID != null);

		n2.setPrimaryCampus("BL");

		Long n2ID2 = getService().saveNotification(n2);

		assertTrue("Notification 2 updated but inserted a new row.", n2ID.compareTo(n2ID2) == 0);

		List<Notification> notifications = getService().findAllNotifications();

		assertTrue("No notifications found.", notifications != null && notifications.size() > 0);
		assertTrue("Expected 2 notifications, found " + notifications.size(), notifications.size() == 2);

		cal = Calendar.getInstance();
		notifications = getService().findAllValidNotifications(new Date(cal.getTimeInMillis()));

		assertTrue("Expected 2 notifications valid today, found " + notifications.size(), notifications.size() == 2);

		cal.add(Calendar.DATE, 1);
		cal.add(Calendar.MINUTE, 120);

		notifications = getService().findAllValidNotifications(new Date(cal.getTimeInMillis()));

		assertTrue("Expected 1 notification valid today, found " + notifications.size(), notifications.size() == 1);

		Notification n3 = getService().findNotificationById(n2.getNotificationId());

		assertTrue("Failed to find notification by ID", n3 != null);
		assertTrue("Notification found for ID " + n2.getNotificationId() + " but objects are not equal.", n2.equals(n3));

		UserNotification un1 = new UserNotification();
		un1.setDeviceId("1234567890");
		un1.setNotificationId(n1ID);
		cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 180);
		un1.setNotifyDate(new Date(cal.getTimeInMillis()));
		un1.setPersonId(Long.valueOf(1999));

		assertTrue("User notification 1 has an ID and shouldn't.", un1.getUserNotificationId() == null);

		Long un1ID = getService().saveUserNotification(un1);

		assertTrue("User notification 1 failed to save.", un1ID != null);

		UserNotification un2 = new UserNotification();
		un2.setDeviceId("0987654321");
		un2.setNotificationId(n2ID);
		un2.setNotifyDate(new Date(cal.getTimeInMillis()));
		un2.setPersonId(Long.valueOf(1999));

		assertTrue("User notification 2 has an ID and shouldn't.", un2.getUserNotificationId() == null);

		Long un2ID = getService().saveUserNotification(un2);

		assertTrue("User notification 2 failed to save.", un2ID != null);

		cal.add(Calendar.DATE, 1);
		un2.setNotifyDate(new Date(cal.getTimeInMillis()));

		Long un2ID2 = getService().saveUserNotification(un2);

		assertTrue("User notification 2 was inserted not updated.", un2ID.compareTo(un2ID2) == 0);

		UserNotification lookup = getService().findUserNotificationById(un1ID);

		assertTrue("No user notification found by ID.", lookup != null);
		assertTrue("Incorrect user notification found by ID.", lookup.equals(un1));

		lookup = getService().findUserNotificationByNotificationId(n2.getNotificationId());

		assertTrue("No user notification found by notification ID.", lookup != null);
		assertTrue("Incorrect user notification found by notification ID.", lookup.equals(un2));

		lookup = getService().findUserNotificationById(Long.valueOf(0));

		assertTrue("Found user notification and should not have.", lookup == null);

		lookup = getService().findUserNotificationByNotificationId(Long.valueOf(0));

		assertTrue("Found user notification by notification id and should not have.", lookup == null);

		List<UserNotification> userNotifications = getService().findAllUserNotificationsByDeviceId("1234567890");

		assertTrue("No user notifications found for device id 1234567890.", userNotifications != null && userNotifications.size() > 0);
		assertTrue("Multiple user notifications found for device, should be 1.", userNotifications.size() == 1);

		userNotifications = getService().findAllUserNotificationsByPersonId(Long.valueOf(0));

		assertTrue("Found user notifications for invalid user id.", userNotifications == null || userNotifications.isEmpty());

		userNotifications = getService().findAllUserNotificationsByPersonId(Long.valueOf(1999));

		assertTrue("No user notifications found for valid user.", userNotifications != null && userNotifications.size() > 0);
		assertTrue("Expected 2 user notifications and found " + userNotifications.size(), userNotifications.size() == 2);

		getService().deleteUserNotificationById(un1.getUserNotificationId());
		getService().deleteUserNotificationById(un2.getUserNotificationId());

		userNotifications = getService().findAllUserNotificationsByPersonId(Long.valueOf(1999));

		assertTrue("Failed to delete user notifications.", userNotifications == null || userNotifications.isEmpty());

		getService().deleteNotificationById(n2.getNotificationId());
		getService().deleteNotificationById(n1.getNotificationId());
		notifications = getService().findAllNotifications();

		assertTrue("Failed to delete notifications, should have found zero.", notifications.size() == 0);
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public NotificationDao getDao() {
		return dao;
	}

	public void setDao(NotificationDao dao) {
		this.dao = dao;
	}

	public NotificationServiceImpl getService() {
		return service;
	}

	public void setService(NotificationServiceImpl service) {
		this.service = service;
	}
}
