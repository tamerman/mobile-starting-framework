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

package org.kuali.mobility.file.dao;

import org.kuali.mobility.file.entity.File;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class FileDaoImpl implements FileDao {

	private static final Logger LOG = LoggerFactory.getLogger(FileDaoImpl.class);
	private String ME = this.getClass().getName();
	
	@PersistenceContext
    private EntityManager entityManager;
	
    public FileDaoImpl(){}
    
	public Long saveFile(File file){
		if(file == null){
    		return null;
    	}
    	try {
			if(file.getId() == null){
				entityManager.persist(file);
			}else{
				entityManager.merge(file);
			}
		} catch (OptimisticLockException e) {
			return null;
		}
    	return file.getId();
	}

	public boolean removeFile(File file){
		boolean result = true;
		if(file == null){
			return false;
		}
		if(file.getId() != null){
			try{
				File f = entityManager.find(File.class, file.getId());
				entityManager.remove(f);
			}catch(Exception e){
    			LOG.info("Exception Caught: " + e.getMessage());
				result = false;
			}
		}
		return result;
	}
	
    @SuppressWarnings("unchecked")
	public File findFileById(Long Id){
        Query query = entityManager.createQuery("select f from File f where f.id = " + Id);
        return (File) query.getSingleResult();
	}
	
    @SuppressWarnings("unchecked")
	public List<File> findFilesByName(String name){
        Query query = entityManager.createQuery("select f from File f where f.fileName like '" + name + "'");
        return query.getResultList();
	}

    @SuppressWarnings("unchecked")
	public List<File> findAllFiles(){
    	
    	Query query = entityManager.createQuery("select f from File f order by f.postedTimestamp desc");
        return query.getResultList();
	}
    
    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }	
}
