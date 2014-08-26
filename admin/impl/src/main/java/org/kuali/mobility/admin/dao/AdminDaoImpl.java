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

package org.kuali.mobility.admin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.springframework.stereotype.Repository;

/**
 * The DAO for actually performing administrative tasks on the data store.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 1.0.0
 */
@Repository
public class AdminDaoImpl implements AdminDao {

	/**
	 * A reference to the <code>EntityManager</code>.
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#getAllHomeScreens()
	 */
	@SuppressWarnings("unchecked")
	public List<HomeScreen> getAllHomeScreens() {
		Query query = entityManager.createNamedQuery("HomeScreen.getAllHomeScreens");
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#getHomeScreenById(long)
	 */
	@Override
	public HomeScreen getHomeScreenById(long homeScreenId) {
		Query query = entityManager.createNamedQuery("HomeScreen.getHomeScreenById");
		query.setParameter("id", homeScreenId);
		try {
			return (HomeScreen) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#getHomeScreenByAlias(java.lang.String)
	 */
	@Override
	public HomeScreen getHomeScreenByAlias(String alias) {
		Query query = entityManager.createNamedQuery("HomeScreen.getHomeScreenByAlias");
		query.setParameter("alias", alias);
		try {
			return (HomeScreen) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#saveHomeScreen(org.kuali.mobility.admin.entity.HomeScreen)
	 */
	@Override
	public Long saveHomeScreen(HomeScreen homeScreen) {
		if (homeScreen == null) {
			return null;
		}
		if (homeScreen.getAlias() != null) {
			homeScreen.setAlias(homeScreen.getAlias().trim());
		}
		for (HomeTool ht : homeScreen.getHomeTools()) {
			ht.setHomeScreen(homeScreen);
		}
		try {
			if (homeScreen.getHomeScreenId() == null) {
				entityManager.persist(homeScreen);
			} else {
				deleteHomeToolsByHomeScreenId(homeScreen.getHomeScreenId());
				entityManager.merge(homeScreen);
			}
		} catch (OptimisticLockException oe) {
			return null;
		}
		return homeScreen.getHomeScreenId();
	}

	/**
	 * Deletes all the HomeTool objects associated with the given HomeScreen.  This effectively removes all Tool associations from a HomeScreen.
	 *
	 * @param homeScreenId
	 */
	private void deleteHomeToolsByHomeScreenId(long homeScreenId) {
		Query query = entityManager.createNamedQuery("HomeScreen.deleteHomeToolsByHomeScreenId");
		query.setParameter("id", homeScreenId);
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#deleteHomeScreenById(long)
	 */
	@Override
	public void deleteHomeScreenById(long homeScreenId) {
		Query query = entityManager.createNamedQuery("HomeScreen.deleteHomeScreenById");
		query.setParameter("id", homeScreenId);
		query.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#getAllTools()
	 */
	@SuppressWarnings("unchecked")
	public List<Tool> getAllTools() {
		Query query = entityManager.createNamedQuery("Tool.getAllTools");
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#getToolById(long)
	 */
	public Tool getToolById(long toolId) {
		Query query = entityManager.createNamedQuery("Tool.getToolById");
		query.setParameter("id", toolId);
		try {
			return (Tool) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#saveTool(org.kuali.mobility.admin.entity.Tool)
	 */
	public Long saveTool(Tool tool) {
		if (tool == null) {
			return null;
		}
		if (tool.getTitle() != null) {
			tool.setTitle(tool.getTitle().trim());
		}
		if (tool.getSubtitle() != null) {
			tool.setSubtitle(tool.getSubtitle().trim());
		}
		if (tool.getUrl() != null) {
			tool.setUrl(tool.getUrl().trim());
		}
		if (tool.getIconUrl() != null) {
			tool.setIconUrl(tool.getIconUrl().trim());
		}
		if (tool.getDescription() != null) {
			tool.setDescription(tool.getDescription().trim());
		}
		if (tool.getContacts() != null) {
			tool.setContacts(tool.getContacts().trim());
		}
		if (tool.getKeywords() != null) {
			tool.setKeywords(tool.getKeywords().trim());
		}
		try {
			if (tool.getToolId() == null) {
				entityManager.persist(tool);
			} else {
				entityManager.merge(tool);
			}
		} catch (OptimisticLockException oe) {
			return null;
		}
		return tool.getToolId();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.dao.AdminDao#deleteToolById(java.lang.Long)
	 */
	public void deleteToolById(Long toolId) {
		Query query = entityManager.createNamedQuery("Tool.deleteToolById");
		query.setParameter("toolId", toolId);
		query.executeUpdate();
	}


	/**
	 * Gets the reference to the <code>EntityManager</code>.
	 *
	 * @return The reference to the <code>EntityManager</code>.
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the reference to the <code>EntityManager</code>.
	 *
	 * @param entityManager The reference to the <code>EntityManager</code>.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
} 
