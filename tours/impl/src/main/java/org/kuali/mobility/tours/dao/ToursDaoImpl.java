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

package org.kuali.mobility.tours.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.kuali.mobility.tours.entity.POI;
import org.kuali.mobility.tours.entity.Tour;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ToursDaoImpl implements ToursDao {

	@PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public Tour findTourById(Long id) {
		Query query = entityManager.createQuery("select t from Tour t where t.tourId = :id");
        query.setParameter("id", id);
        try {
        	return (Tour) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}

	@Override
	public Tour findTourByName(String name) {
		Query query = entityManager.createQuery("select t from Tour t where t.name like :name");
        query.setParameter("name", name);
        try {
        	return (Tour) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}

	@Override
	@Transactional
	public Long saveTour(Tour tour) {
		if (tour == null) {
            return null;
        }
        if (tour.getName() != null) {
        	tour.setName(tour.getName().trim());
        }
        if (tour.getDescription() != null) {
        	tour.setDescription(tour.getDescription().trim());
        }
        try {
	        if (tour.getTourId() == null) {
	            entityManager.persist(tour);
	        } else {
	        	deletePoisByTourId(tour.getTourId());
	        	deletePermissionsByTourId(tour.getTourId());
	            entityManager.merge(tour);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return tour.getTourId();
	}
	
	private void deletePoisByTourId(long tourId) {
		deletePhoneNumbersAndEmailAddressesByTourId(tourId);
		Query query = entityManager.createQuery("delete from POI poi where poi.tourId = :id");
        query.setParameter("id", tourId);
        query.executeUpdate();
	}
	
	private void deletePermissionsByTourId(long tourId) {
		Query query = entityManager.createQuery("delete from TourPermission prmssn where prmssn.tourId = :id");
        query.setParameter("id", tourId);
        query.executeUpdate();
	}
	
	private void deletePermissionsByPoiId(long poiId) {
		Query query = entityManager.createQuery("delete from POIPermission prmssn where prmssn.poiId = :id");
        query.setParameter("id", poiId);
        query.executeUpdate();
	}
	
	private void deletePhoneNumbersAndEmailAddressesByTourId(long tourId) {
		Query query = entityManager.createQuery("delete from POIPhoneNumber phnNum where phnNum.poiId in (select poiId from POI poi where poi.tourId = :id)");
        query.setParameter("id", tourId);
        query.executeUpdate();
        
        query = entityManager.createQuery("delete from POIEmailAddress address where address.poiId in (select poiId from POI poi where poi.tourId = :id)");
        query.setParameter("id", tourId);
        query.executeUpdate();
	}
	
	private void deletePhoneNumbersAndEmailAddressesByPoiId(long poiId) {
		Query query = entityManager.createQuery("delete from POIPhoneNumber phnNum where phnNum.poiId = :id)");
        query.setParameter("id", poiId);
        query.executeUpdate();
        
        query = entityManager.createQuery("delete from POIEmailAddress address where address.poiId = :id)");
        query.setParameter("id", poiId);
        query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tour> findAllTours() {
		Query query = entityManager.createQuery("select t from Tour t order by t.name");
        return query.getResultList();
	}

	@Override
	@Transactional
	public void deleteTourById(Long id) {
		deletePoisByTourId(id);
		deletePermissionsByTourId(id);
		Query query = entityManager.createQuery("delete from Tour t where t.tourId = :id");
        query.setParameter("id", id);
        query.executeUpdate();
	}
	
	@Override
	public POI findPoiById(Long id) {
		Query query = entityManager.createQuery("select t from POI t where t.poiId = :id");
        query.setParameter("id", id);
        try {
        	return (POI) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}
	
	@Override
	public POI findPoiByOrder(Long tourId, Integer order) {
		Query query = entityManager.createQuery("select t from POI t where t.tourId = :tourId and t.order = :order");
        query.setParameter("tourId", tourId);
        query.setParameter("order", order);
        try {
        	return (POI) query.getSingleResult();
        } catch (Exception e) {
        	return null;
        }
	}
	
	@Override
	@Transactional
	public Long savePoi(POI poi) {
		if (poi == null) {
            return null;
        }
        if (poi.getName() != null) {
        	poi.setName(poi.getName().trim());
        }
        if (poi.getDescription() != null) {
        	poi.setDescription(poi.getDescription().trim());
        }
        try {
	        if (poi.getPoiId() == null) {
	            entityManager.persist(poi);
	        } else {
	        	deletePermissionsByPoiId(poi.getPoiId());
	        	deletePhoneNumbersAndEmailAddressesByPoiId(poi.getPoiId());
	            entityManager.merge(poi);
	        }
        } catch (OptimisticLockException oe) {
            return null;
        }
        return poi.getPoiId();
	}
	
	@Override
	@Transactional
	public void deletePoiById(Long poiId) {
		deletePermissionsByPoiId(poiId);
		deletePhoneNumbersAndEmailAddressesByPoiId(poiId);
		Query query = entityManager.createQuery("delete from POI t where t.poiId = :id");
        query.setParameter("id", poiId);
        query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<POI> findAllCommonPOI() {
		Query query = entityManager.createQuery("select t from POI t where t.tourId is null");
        return query.getResultList();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
