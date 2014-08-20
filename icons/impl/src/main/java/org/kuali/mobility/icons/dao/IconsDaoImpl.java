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

package org.kuali.mobility.icons.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.icons.entity.WebIcon;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;

/**
 * Implementation of the <code>IconsDao</code>
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@Repository
public class IconsDaoImpl implements IconsDao {
	
	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger( IconsDaoImpl.class );

	/**
	 * A reference to the <code>EntityManager</code>
	 */
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Gets the reference to the <code>EntityManager</code>
	 * @returns The reference to the <code>EntityManager</code>
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the reference to the <code>EntityManager</code>
	 * @param entityManager The reference to the <code>EntityManager</code>
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}


    /* (non-Javadoc)
     * @see coza.opencollab.mobility.icon.service.IconDao#getIcon()
     */
    @Override
    public List<WebIcon> getIcons() {
        Query query = getEntityManager().createNamedQuery("WebIcon.getAllWebIcon");
        return query.getResultList();
    }

    /* (non-Javadoc)
     * @see coza.opencollab.mobility.icon.service.IconDao#getIcon(java.lang.String, java.lang.String)
     */
    @Override
    public WebIcon getIcon(String icon, String theme) {
        Query query;
        if (theme == null){
            query = getEntityManager().createNamedQuery("WebIcon.findWebIconNoTheme");
        }
        else{
            query = getEntityManager().createNamedQuery("WebIcon.findWebIcon");
            query.setParameter("theme", theme);
        }
        query.setParameter("name", icon);

        WebIcon iconObj = null;
        try{
            iconObj = (WebIcon)query.getSingleResult();
        }
        catch(NonUniqueResultException ex){
            LOG.warn("Exception while trying to retrieve icon", ex);
        }
        catch(EntityNotFoundException ex){
            LOG.warn("Exception while trying to retrieve icon", ex);
        }
        catch(NoResultException ex){
            iconObj = null;
        }
        return iconObj;
    }

    @Override
    public boolean iconExists(String iconName, String theme) {
        Query query;
        if (theme == null){
            query = getEntityManager().createNamedQuery("WebIcon.existsNoTheme");
        }
        else{
            query = getEntityManager().createNamedQuery("WebIcon.exists");
            query.setParameter("theme", theme);
        }
        query.setParameter("name", iconName);
        long count = (Long)query.getSingleResult();
        return count > 0;
    }

    /*
     * (non-Javadoc)
     * @see coza.opencollab.mobility.icon.service.IconDao#saveWebIcon(coza.opencollab.mobility.icon.entity.WebIcon)
     */
    @Override
    @Transactional
    public WebIcon saveWebIcon(WebIcon icon) {
        if (icon.getId() == null || icon.getId().longValue() == 0L){
            this.getEntityManager().persist(icon);
            return icon;
        }
        else {
            return this.getEntityManager().merge(icon);
        }
    }
}
