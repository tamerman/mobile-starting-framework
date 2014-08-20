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

package org.kuali.mobility.emergencyinfo.dao;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoImpl;
import org.kuali.mobility.emergencyinfo.util.CampusPredicate;
import org.kuali.mobility.util.mapper.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmergencyInfoWSDaoImpl implements EmergencyInfoDao {

	private static final Logger LOG = LoggerFactory.getLogger( EmergencyInfoWSDaoImpl.class );

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DataMapper dataMapper;

	private String sourceUrl;
	private List<EmergencyInfoImpl> contacts = null;

	private void initData() {
		contacts = new ArrayList<EmergencyInfoImpl>();
		try {
			URL url = new URL( getSourceUrl() );
			contacts.addAll(getDataMapper().mapData(contacts, url, "emergencyinfoMapping.xml"));
		} catch( MalformedURLException mue ) {
			LOG.error( "URL supplied for sourceUrl is malformed.", mue );
		} catch( ClassNotFoundException cnfe ) {
			LOG.error( "Data mapper unable to load class defined in mapping.", cnfe );
		} catch( IOException ioe ) {
			LOG.error( "Unabled to access emergency info source url.", ioe );
		} catch (Exception e) {
			LOG.error( "If you see this, something really, really strange happened.", e);
		}
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public EmergencyInfo findEmergencyInfoById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends EmergencyInfo> findAllEmergencyInfo() {
		if( null == contacts ) {
			initData();
		}
		return contacts;
	}

	@Override
	public List<? extends EmergencyInfo> findAllEmergencyInfoByCampus(String campus) {
		if( null == contacts ) {
			initData();
		}
		List<? extends EmergencyInfo> campusContacts = new ArrayList<EmergencyInfoImpl>();
		campusContacts.addAll( CollectionUtils.select( contacts, new CampusPredicate(campus)) );
		return campusContacts;
	}

	@Override
	public Long saveEmergencyInfo(EmergencyInfo emergencyInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmergencyInfoById(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reorder(Long id, boolean up) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the sourceUrl
	 */
	public String getSourceUrl() {
		return sourceUrl;
	}

	/**
	 * @param sourceUrl the sourceUrl to set
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	/**
	 * @return the dataMapper
	 */
	public DataMapper getDataMapper() {
		return dataMapper;
	}

	/**
	 * @param dataMapper the dataMapper to set
	 */
	public void setDataMapper(DataMapper dataMapper) {
		this.dataMapper = dataMapper;
	}
}
