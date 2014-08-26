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

package org.kuali.mobility.security.group.dao;

import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.entity.GroupMembership;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Repository
public class GroupDaoImpl implements GroupDao {
	private static final Logger LOG = LoggerFactory.getLogger(GroupDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;

	private Map<String, Group> groupMap;

	public void init() {
		getGroups();
	}

	// This method can be used to refresh the cached group data.
	@Override
	public List<Group> getGroups() {
		Query query = getEntityManager().createNamedQuery("Group.loadAllGroups");
		List<Group> groups = query.getResultList();
		if (groups != null && !groups.isEmpty()) {
			Map<String, Group> newGroupMap = new HashMap<String, Group>();
			for (Group g : groups) {
				newGroupMap.put(g.getName(), g);
			}
			setGroupMap(newGroupMap);
		}
		return groups;
	}

	@Override
	public Group getGroup(Long id) {
		Query query = getEntityManager().createNamedQuery("Group.loadGroupById");
		query.setParameter("id", id);
		Group group = (Group) query.getSingleResult();
		if (getGroupMap() != null) {
			getGroupMap().put(group.getName(), group);
		} else {
			getGroups();
		}
		return group;
	}

	@Override
	public Group getGroup(String name) {
		Group group = null;
		if (getGroupMap() != null && getGroupMap().containsKey(name)) {
			group = getGroupMap().get(name);
		}
		return group;
	}

	@Override
	@Transactional
	public Long saveGroup(Group group) {
		Long id;
		if (null == group) {
			LOG.error("Attempting to save null group. Why is this happening?");
			id = null;
		} else {
			if (null == group.getId()) {
				getEntityManager().persist(group);
			} else {
				getEntityManager().merge(group);
			}
			id = group.getId();
		}
		return id;
	}

	@Override
	@Transactional
	public void removeGroup(Group group) {
		if (null == group || group.getId() == null) {
			LOG.error("Attempting to remove null group.");
		} else {
			Query query = getEntityManager().createNamedQuery("GroupMembership.loadMembershipByGroupId");
			query.setParameter("groupId", group.getId());
			List<GroupMembership> groupMemberships = query.getResultList();
			if (groupMemberships != null && groupMemberships.size() != 0) {
				Query updateQuery = getEntityManager().createNamedQuery("GroupMembership.deleteMembershipByGroupId");
				updateQuery.setParameter("groupId", group.getId());
				updateQuery.executeUpdate();
			}
			getEntityManager().remove(getGroup(group.getId()));
		}
	}

	@Override
	public void removeFromGroup(List<User> users, Long groupId) {
		if (null == users || users.size() == 0) {
			LOG.error("Attempting to remove null users.");
		} else {
			for (User user : users) {
				Query query = getEntityManager().createNamedQuery("GroupMembership.deleteMembershipByUserId");
				query.setParameter("userId", user.getId());
				query.executeUpdate();
			}
		}
	}

	@Override
	public void addToGroup(List<User> users, Long groupId) {
		if (null == users || users.size() == 0) {
			LOG.error("Attempting to add null users.");
		} else {
			for (User user : users) {
				GroupMembership groupMembership = new GroupMembership();
				groupMembership.setGroupId(groupId);
				groupMembership.setUserId(user.getId());
				getEntityManager().persist(groupMembership);
			}
		}

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public Map<String, Group> getGroupMap() {
		if (groupMap == null) {
			getGroups();
		}
		return groupMap;
	}

	@Override
	public void setGroupMap(Map<String, Group> groupMap) {
		this.groupMap = groupMap;
	}
}
