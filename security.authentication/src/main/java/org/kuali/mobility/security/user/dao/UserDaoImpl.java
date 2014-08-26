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

package org.kuali.mobility.security.user.dao;

import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.entity.GroupMembership;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Repository
public class UserDaoImpl implements UserDao {

	private static final Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("kmeGroupDao")
	private GroupDao groupDao;

	@Override
	public User loadUserById(Long id) {
		Query query = getEntityManager().createNamedQuery("User.lookupById");
		query.setParameter("id", id);
		UserImpl user = null;

		try {
			user = (UserImpl) query.getSingleResult();
		} catch (EntityNotFoundException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (NonUniqueResultException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}

		if (null != user) {
			loadGroupMembership(user);
		}
		return user;
	}

	@Override
	public User loadUserByLoginName(String loginName) {
		Query query = getEntityManager().createNamedQuery("User.lookupByLoginName");
		query.setParameter("loginName", loginName);
		UserImpl user = null;

		try {
			user = (UserImpl) query.getSingleResult();
		} catch (EntityNotFoundException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (NonUniqueResultException e) {
			LOG.error(e.getLocalizedMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
		}

		if (null != user) {
			loadGroupMembership(user);
		}
		return user;
	}

	@Override
	@Transactional
	public Long saveUser(User user) {
		Long id;
		if (null == user) {
			id = null;
		} else {
			if (null == user.getId()) {
				getEntityManager().persist(user);
			} else {
				getEntityManager().merge(user);
			}
			id = user.getId();
			Query query = getEntityManager().createNamedQuery("GroupMembership.deleteMembershipByUserId");
			query.setParameter("userId", id);
			query.executeUpdate();
			for (Group g : user.getGroups()) {
				GroupMembership groupMembership = new GroupMembership();
				groupMembership.setGroupId(g.getId());
				groupMembership.setUserId(id);
				getEntityManager().persist(groupMembership);
			}
		}
		return id;
	}

	@Override
	public List<User> loadUserByGroup(Long groupId) {
		List<User> users = new ArrayList<User>();
		Query query = getEntityManager().createNamedQuery("GroupMembership.loadMembershipByGroupId");
		query.setParameter("groupId", groupId);
		List<GroupMembership> groupMemberships = query.getResultList();
		for (GroupMembership gm : groupMemberships) {
			User user = loadUserById(gm.getUserId());
			users.add(user);
		}
		return users;
	}

	@Override
	public List<User> loadAllUsers() {
		Query query = getEntityManager().createNamedQuery("User.lookupAllUsers");
		List<User> users = query.getResultList();
		for (User user : users) {
			loadGroupMembership((UserImpl) user);
		}
		return users;
	}

	private void loadGroupMembership(UserImpl user) {
		if (user != null && user.getId() != null) {
			List<Group> groups = new ArrayList<Group>();
			Query query = getEntityManager().createNamedQuery("GroupMembership.loadMembershipByUserId");
			query.setParameter("userId", user.getId());
			List<GroupMembership> groupMemberships = query.getResultList();
			for (GroupMembership gm : groupMemberships) {
				Group g = getGroupDao().getGroup(gm.getGroupId());
				groups.add(g);
			}
			user.setGroups(groups);
		}
	}

	private void loadGroupMembership(List<UserImpl> users) {
		for (UserImpl u : users) {
			loadGroupMembership(u);
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
