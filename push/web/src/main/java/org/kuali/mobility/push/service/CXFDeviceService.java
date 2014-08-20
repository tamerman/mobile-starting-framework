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

package org.kuali.mobility.push.service;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;

/**
 * Implementation of the CXF Device Service
 *
 * @deprecated This class is moved to an external project "push-service"
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Deprecated
@Service
public class CXFDeviceService {

	private static final String USERNAME = "username";

	/** A reference to a logger for this service */
	private static final Logger LOG = LoggerFactory.getLogger(CXFDeviceService.class);
	
	/**
	 * A reference to the Controllers <code>DeviceService</code> object.
	 */
	private DeviceService deviceService;

	/**
	 *  A reference to the Controllers <code>UserDao</code> object. 
	 */
	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;
	
	@GET
	@Path("/ping/get")
	public String pingGet(){
		return "{\"status\":\"OK\"}";
	}

	@POST
	@Path("/ping/post")
	public String pingPost(){
		return "{\"status\":\"OK\"}";
	}


	/**
	 * A controller method for registering a device for push notifications as defined by a JSON formatted string. 
	 * 
	 * @param data JSON formatted string describing a device to be register for push notifications.
	 * @return
	 */
	@GET
    @Path("/register")
	public Response register(@Context MessageContext context, @QueryParam(value="data") String data) {
		HttpServletRequest request = context.getHttpServletRequest();
		LOG.info("-----Register-----");
		if(data == null){
			return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
		} 

		JSONObject queryParams;
		try{
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		}catch(JSONException je){
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}
			
		Device device = new Device();
		device.setDeviceName(queryParams.getString("name"));
		device.setDeviceId(queryParams.getString("deviceId"));
		device.setRegId(queryParams.getString("regId"));
		device.setType(queryParams.getString("type"));
		
		// We might not have a username yet
		if (queryParams.containsKey(USERNAME)){
			device.setUsername(queryParams.getString(USERNAME));
		}
		device.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));

        device = deviceService.registerDevice(device);

		User user = (User)request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		if( user == null ) {
			LOG.error("No user found in request. This should never happen!");
		} else if( user.isPublicUser() ) {
			LOG.debug("Public user found, no user profile updates necessary.");
		} else if( !user.getLoginName().equals(device.getUsername()) ) {
			LOG.debug("User on device does not match user in session! This should never happen either.");
		} else if( user.attributeExists(AuthenticationConstants.DEVICE_ID,device.getDeviceId())) {
			LOG.debug("Device id already exists on user and no action needs to be taken.");
		} else {
			user.addAttribute(AuthenticationConstants.DEVICE_ID,device.getDeviceId());
			getUserDao().saveUser(user);
		}
		return Response.status(Response.Status.OK.getStatusCode()).build();
	}
	
	/**
	 * CXF Service method for updating a pre-existing registered device. 
	 * 
	 * @param data A JSON formatted string describing details of a device to update.
	 * @return
	 */
	@GET
    @Path("/update")
	public Response update(@Context MessageContext context, @QueryParam(value="data") String data) {
		HttpServletRequest request = context.getHttpServletRequest();
		LOG.info("-----Register-----");
		if(data == null){
			return Response.status(Response.Status.OK.getStatusCode()).build();
		} 

		JSONObject queryParams;
		try{
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			LOG.info(queryParams.toString());
		}catch(JSONException je){
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}

		Device temp = new Device();

		// If the device already exists, update.
		if(temp != null){
			LOG.info("-----Device already exists." + temp.toString());
			// Remove this device from the logged user's profile if it exists.
			User user = getUserDao().loadUserByLoginName(temp.getUsername());
			if( user != null ) {
				user.removeAttribute(AuthenticationConstants.DEVICE_ID,temp.getDeviceId());
				getUserDao().saveUser(user);
			}
			temp.setDeviceName(queryParams.getString("name"));
			temp.setUsername(queryParams.getString(USERNAME));
			temp.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
			deviceService.saveDevice(temp);

			// Find the real user and set the new device.
			User user2 = (User)request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
			if( user2 == null ) {
				LOG.error("No user found in request. This should never happen!");
			} else if( user2.isPublicUser() ) {
				LOG.debug("Public user found, no user profile updates necessary.");
			} else if( !user2.getLoginName().equals(temp.getUsername()) ) {
				LOG.debug("User on device does not match user in session! This should never happen either.");
			} else if( user2.attributeExists(AuthenticationConstants.DEVICE_ID,temp.getDeviceId())) {
				LOG.debug("Device id already exists on user and no action needs to be taken.");
			} else {
				user2.addAttribute(AuthenticationConstants.DEVICE_ID,temp.getDeviceId());
				getUserDao().saveUser(user2);
			}

		}
		return Response.status(Response.Status.OK.getStatusCode()).build();
	}
	

	/**
	 * Get the controllers <code>UserDao</code> object.
	 * @return
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * A method for setting the <code>UserDao</code> of the controller. 
	 * 
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }
}
