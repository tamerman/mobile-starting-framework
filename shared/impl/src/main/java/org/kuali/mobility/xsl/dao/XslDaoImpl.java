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

package org.kuali.mobility.xsl.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.kuali.mobility.xsl.entity.Xsl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public class XslDaoImpl implements XslDao {
	private static final Logger LOG = LoggerFactory.getLogger(XslDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    public void deleteXslById(Long id) {
        Query query = entityManager.createQuery("delete from Xsl x where x.xslId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Xsl> findAllXsl() {
        Query query = entityManager.createQuery("select x from Xsl x order by x.id");
        return query.getResultList();
    }

    public Xsl findXslById(Long id) {
        Query query = entityManager.createQuery("select x from Xsl x where x.xslId = :id");
        query.setParameter("id", id);
        return (Xsl) query.getSingleResult();
    }
    
    public Xsl findXslByCode(String code) {
        Query query = entityManager.createQuery("select x from Xsl x where x.code = :code");
        query.setParameter("code", code);
        return (Xsl) query.getSingleResult();
    }

    public Long saveXsl(Xsl xsl) {
        if (xsl == null) {
            return null;
        }
	    try {
	        if (xsl.getValue() != null) {
	            xsl.setValue(xsl.getValue().trim());
	        }
	        if (xsl.getXslId() == null) {
	            entityManager.persist(xsl);
	        } else {
	            entityManager.merge(xsl);
	        }
	    } catch( OptimisticLockException ole ) {
		    LOG.error(ole.getLocalizedMessage(),ole);
	        return null;
	    }
		return xsl.getXslId();
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
