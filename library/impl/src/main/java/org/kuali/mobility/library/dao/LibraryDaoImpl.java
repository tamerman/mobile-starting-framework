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

package org.kuali.mobility.library.dao;

import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryHourPeriod;
import org.kuali.mobility.library.entity.LibraryHourSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Implementation of te <code>LibraryDao</code>
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@Repository
public class LibraryDaoImpl implements LibraryDao {
	
	/**
	 * A reference to a logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger( LibraryDaoImpl.class );

	/**
	 * Reference to the <code>EntityManager</code>
	 */
	@PersistenceContext
	private EntityManager entityManager;


	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#getLibraries()
	 */
	@Override
	public List<Library> getLibraries(){
		String nq = "Library.getLibraries";
		Query query = getEntityManager().createNamedQuery(nq);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#getLibrary(long)
	 */
	@Override
	public Library getLibrary(long libraryId) {
		return this.entityManager.find(Library.class, libraryId);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#saveLibrary(org.kuali.mobility.library.entity.Library)
	 */
	@Override
	@Transactional
	public Library saveLibrary(Library library) {
		if (library.getId() == null){
			this.entityManager.persist(library);
		}
		else {
			library = this.entityManager.merge(library);
		}
		return library;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#getLibraryHourSets(long)
	 */
	@Override
	public List<LibraryHourSet> getLibraryHourSets(long libraryId) {
		Query q = this.entityManager.createNamedQuery("LibraryHourSet.getHourSets");
		q.setParameter("libraryId", libraryId);
		return q.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#saveLibraryHourSets(org.kuali.mobility.library.entity.LibraryHourSet)
	 */
	@Override
	@Transactional
	public LibraryHourSet saveLibraryHourSets(LibraryHourSet lhs) {
		if (lhs.getId() == null){
			this.entityManager.persist(lhs);
			return lhs;
		}
		else {
			return this.entityManager.merge(lhs);
		}
	}
	
	

	/**
	 * Gets the reference to the <code>EntityManager</code>.
	 * @return The reference to the <code>EntityManager</code>.
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Sets the reference to the <code>EntityManager</code>.
	 * @param entityManager The reference to the <code>EntityManager</code>.
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#getLibariesForCampus(java.lang.String)
	 */
	@Override
	public List<Library> getLibariesForCampus(String campusCode) {
		Query query = getEntityManager().createNamedQuery("Library.getLibariesForCampus");
		query.setParameter("campusCode", campusCode);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#getCampusWithLibraries()
	 */
	@Override
	public List<String> getCampusWithLibraries() {
		Query query = getEntityManager().createNamedQuery("Library.getCampusWithLibraries");
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.library.dao.LibraryDao#saveLibraryHourPeriod(org.kuali.mobility.library.entity.LibraryHourPeriod)
	 */
	@Override
	@Transactional
	public LibraryHourPeriod saveLibraryHourPeriod(LibraryHourPeriod libraryHourPeriod) {
		
		if (libraryHourPeriod.getId() == null){
			getEntityManager().persist(libraryHourPeriod);
			return libraryHourPeriod;
		}
		else {
			return getEntityManager().merge(libraryHourPeriod);
		}
	}

}
