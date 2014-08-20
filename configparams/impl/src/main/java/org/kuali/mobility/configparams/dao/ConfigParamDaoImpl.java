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

 
package org.kuali.mobility.configparams.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.configparams.entity.ConfigParam;
import org.springframework.stereotype.Repository;

/**
 * DAO to actually perform manipulation of ConfigParam objects
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Repository
public class ConfigParamDaoImpl implements ConfigParamDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    public void deleteConfigParamById(Long id) {
        Query query = entityManager.createQuery("delete from ConfigParam cp where cp.configParamId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<ConfigParam> findAllConfigParam() {
        Query query = entityManager.createQuery("select cp from ConfigParam cp order by cp.name");
        return query.getResultList();
    }

    public ConfigParam findConfigParamById(Long id) {
        Query query = entityManager.createQuery("select cp from ConfigParam cp where cp.configParamId = :id");
        query.setParameter("id", id);
        return (ConfigParam) query.getSingleResult();
    }
    
    public ConfigParam findConfigParamByName(String name) {
        Query query = entityManager.createQuery("select cp from ConfigParam cp where cp.name like :name");
        query.setParameter("name", name);
        return (ConfigParam) query.getSingleResult();
    }

    public Long saveConfigParam(ConfigParam configParam) {
        if (configParam == null) {
            return null;
        }
        if (configParam.getName() != null) {
            configParam.setName(configParam.getName().trim());
        }
        if (configParam.getValue() != null) {
            configParam.setValue(configParam.getValue().trim());
        }
        try {
	        if (configParam.getConfigParamId() == null) {
	            entityManager.persist(configParam);
	        } else {
	            entityManager.merge(configParam);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return configParam.getConfigParamId();
    }
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
}
