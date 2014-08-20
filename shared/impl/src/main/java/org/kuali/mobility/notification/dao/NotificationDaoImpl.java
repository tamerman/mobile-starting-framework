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

 
package org.kuali.mobility.notification.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.notification.entity.Notification;
import org.kuali.mobility.notification.entity.UserNotification;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the <code>NotificationDao</code>.
 */
@Repository
public class NotificationDaoImpl implements NotificationDao {

    @PersistenceContext
    private EntityManager entityManager;

	@Override
	public Notification findNotificationById(Long id) {
		Query query = entityManager.createQuery("select n from Notification n where n.notificationId = :id");
        query.setParameter("id", id);
        try {
        	return (Notification) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> findAllNotifications() {
        Query query = entityManager.createQuery("select n from Notification n");
        try { 
        	return (List<Notification>) query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> findAllValidNotifications(Date date) {
        Query query = entityManager.createQuery("select n from Notification n where (n.startDate is null and n.endDate is null) or (n.startDate is null and :date < n.endDate) or (:date > n.startDate and n.endDate is null) or (:date between n.startDate and n.endDate)");
        query.setParameter("date", date);
        try { 
        	return (List<Notification>) query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
	}
	
	@Override
	public Long saveNotification(Notification notification) {
        if (notification == null) {
            return null;
        }
        try {
	        if (notification.getNotificationId() == null) {
	            entityManager.persist(notification);
	        } else {
	            entityManager.merge(notification);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return notification.getNotificationId();
    }

	@Override
	public void deleteNotificationById(Long id) {
        Query query = entityManager.createQuery("delete from Notification n where n.notificationId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
	}

	@Override
	public UserNotification findUserNotificationById(Long id) {
		Query query = entityManager.createQuery("select un from UserNotification un where un.userNotificationId = :id");
        query.setParameter("id", id);
        try {
        	return (UserNotification) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}
	
	@Override
	public UserNotification findUserNotificationByNotificationId(Long id) {
		Query query = entityManager.createQuery("select un from UserNotification un where un.notificationId = :id");
        query.setParameter("id", id);
        try {
        	return (UserNotification) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}


	@Override
	public Long saveUserNotification(UserNotification userNotification) {
        if (userNotification == null) {
            return null; 
        }
        try {
	        if (userNotification.getUserNotificationId() == null) {
	            entityManager.persist(userNotification);
	        } else {
	            entityManager.merge(userNotification);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return userNotification.getUserNotificationId();
    }

	@Override
	public void deleteUserNotificationById(Long id) {
        Query query = entityManager.createQuery("delete from UserNotification un where un.userNotificationId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotification> findAllUserNotificationsByDeviceId(String id) {
        Query query = entityManager.createQuery("select un from UserNotification un where deviceId = :id");
        query.setParameter("id", id);
        try { 
        	return (List<UserNotification>) query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserNotification> findAllUserNotificationsByPersonId(Long id) {
        Query query = entityManager.createQuery("select un from UserNotification un where personId = :id");
        query.setParameter("id", id);
        try { 
        	return (List<UserNotification>) query.getResultList();
        } catch (Exception e) {        	
        	return null;
        }
	}

}
