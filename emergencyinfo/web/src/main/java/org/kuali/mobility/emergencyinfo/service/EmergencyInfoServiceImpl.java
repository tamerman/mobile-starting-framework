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

package org.kuali.mobility.emergencyinfo.service;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.kuali.mobility.emergencyinfo.dao.EmergencyInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.PathParam;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoImpl;
import org.kuali.mobility.emergencyinfo.util.EmergencyInfoTransform;
import org.springframework.beans.factory.annotation.Qualifier;

@Service(value="EmergencyInfoService")
@WebService(endpointInterface="org.kuali.mobility.emergencyinfo.service.EmergencyInfoService")
public class EmergencyInfoServiceImpl implements EmergencyInfoService {
	private static final Logger LOG = LoggerFactory.getLogger( EmergencyInfoServiceImpl.class );
	private String emergencyinfoSourceUrl;

    public String getEmergencyinfoSourceUrl() {
		return emergencyinfoSourceUrl;
	}

	public void setEmergencyinfoSourceUrl(String emergencyinfoSourceUrl) {
		this.emergencyinfoSourceUrl = emergencyinfoSourceUrl;
	}

	@Autowired
	@Qualifier("emergencyInfoDao")
    private EmergencyInfoDao emergencyInfoDao;

//    @DELETE
//    @Path("information/delete")
//    @Transactional
//    @Override
    public void deleteEmergencyInfoById(@QueryParam(value = "id") Long id) {
        emergencyInfoDao.deleteEmergencyInfoById(id);
    }

    @GET
    @Path("information/lookup")
    @Transactional
    @Override
    public List<EmergencyInfoImpl> findAllEmergencyInfo() {
        List<EmergencyInfoImpl> contacts = new ArrayList<EmergencyInfoImpl>();
		CollectionUtils.collect( getEmergencyInfoDao().findAllEmergencyInfo(), new EmergencyInfoTransform(), contacts );
		return contacts;
    }

    @GET
    @Path("information/search")
    @Transactional
    @Override
    public EmergencyInfoImpl findEmergencyInfoById(@QueryParam(value = "id") Long id) {
		EmergencyInfoTransform transform = new EmergencyInfoTransform();
		return transform.transform( getEmergencyInfoDao().findEmergencyInfoById(id) );
    }

//    @PUT
//    @Path("information/save")
//    @Transactional
//    @Override
    public Long saveEmergencyInfo(EmergencyInfo emergencyInfo) {
        return emergencyInfoDao.saveEmergencyInfo(emergencyInfo);
    }

//    @PUT
//    @Path("information/reorder/{id}")
//    @Transactional
//    @Override
    public void reorder(@QueryParam(value = "id") Long id, @QueryParam(value = "up") boolean up) {
        emergencyInfoDao.reorder(id, up);
    }

    @GET
    @Path("information/bycampus/{campus}")
    @Transactional
    @Override
    public List<EmergencyInfoImpl> findAllEmergencyInfoByCampus(@PathParam(value = "campus") String campus) {
        List<EmergencyInfoImpl> contacts = new ArrayList<EmergencyInfoImpl>();
		CollectionUtils.collect( getEmergencyInfoDao().findAllEmergencyInfoByCampus(campus), new EmergencyInfoTransform(), contacts );
		LOG.debug( "Filtering emergency contacts for campus ["+campus+"] and found "+contacts.size() );
		return contacts;
    }

//    @Override
//    public EmergencyInfoJPAImpl fromJsonToEntity(String json) {
//        return new JSONDeserializer<EmergencyInfo>().use(null, EmergencyInfoJPAImpl.class).deserialize(json);
//    }
//
//    @Override
//    public String toJson(Collection<EmergencyInfo> collection) {
//        return new JSONSerializer().exclude("*.class").serialize(collection);
//    }
//
//    @Override
//    public Collection<EmergencyInfo> fromJsonToCollection(String json) {
//        return new JSONDeserializer<List<EmergencyInfo>>().use(null, ArrayList.class).use("values", EmergencyInfoJPAImpl.class).deserialize(json);
//    }

	/**
	 * @return the emergencyInfoDao
	 */
	public EmergencyInfoDao getEmergencyInfoDao() {
		return emergencyInfoDao;
	}

	/**
	 * @param emergencyInfoDao the emergencyInfoDao to set
	 */
	public void setEmergencyInfoDao(EmergencyInfoDao emergencyInfoDao) {
		this.emergencyInfoDao = emergencyInfoDao;
	}

}
