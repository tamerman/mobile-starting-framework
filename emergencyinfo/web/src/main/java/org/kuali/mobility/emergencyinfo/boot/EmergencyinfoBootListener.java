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

package org.kuali.mobility.emergencyinfo.boot;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoJPAImpl;
import org.kuali.mobility.emergencyinfo.service.EmergencyInfoService;
import org.kuali.mobility.shared.listeners.Bootables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Bootstrap listener for the Library Tool
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.1
 */
public class EmergencyinfoBootListener implements ServletContextListener {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(EmergencyinfoBootListener.class);
		
	private static final String POLICE = "Police";
	private static final String PHONE = "PHONE";
	private static final String PHONENUMBER = "1-812-555-1234";

	private static final String EMC = "Emergency Management & Continuity";
	private static final String EMAIL = "EMAIL";
	private static final String EMAILADDRESS = "iuemc@iu.edu";
	
	/**
	 * A reference to the Bootables
	 */
	@Autowired
	private Bootables bootables;
	
	/**
	 * 
	 */
	public void initialize(){
		bootables.registeredBootable(this);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOG.debug("EmergencyinfoBootListener Initializing...");
		
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		Properties kmeProperties = (Properties)ctx.getBean("kmeProperties");
		
		if (kmeProperties == null || !"true".equals(kmeProperties.getProperty("emergencyinfo.bootstrap"))){
			return;
		}

		EmergencyInfoService emergencyInfoService = (EmergencyInfoService) ctx.getBean("emergencyInfoService");
		
		LOG.debug( "Loading Emergency Info." );
		LOG.debug( "EmergencyInfoService is"+(null==emergencyInfoService?" ":" not ")+"null" );
		EmergencyInfo ei = new EmergencyInfoJPAImpl();
		
		ei = new EmergencyInfoJPAImpl();
		ei.setCampus("BL");
		ei.setLink(PHONENUMBER);
		ei.setTitle(POLICE);
		ei.setType(PHONE);
		emergencyInfoService.saveEmergencyInfo(ei);

		ei = new EmergencyInfoJPAImpl();
		ei.setCampus("IN");
		ei.setLink(PHONENUMBER);
		ei.setTitle(POLICE);
		ei.setType(PHONE);
		emergencyInfoService.saveEmergencyInfo(ei);

		ei = new EmergencyInfoJPAImpl();
		ei.setCampus("CO");
		ei.setLink(PHONENUMBER);
		ei.setTitle(POLICE);
		ei.setType(PHONE);
		emergencyInfoService.saveEmergencyInfo(ei);

		ei = new EmergencyInfoJPAImpl();
		ei.setCampus("ALL");
		ei.setLink(PHONENUMBER);
		ei.setTitle(POLICE);
		ei.setType(PHONE);
		emergencyInfoService.saveEmergencyInfo(ei);
		
		ei = new EmergencyInfoJPAImpl();
		ei.setCampus("ALL");
		ei.setLink(EMAILADDRESS);
		ei.setTitle(EMC);
		ei.setType(EMAIL);
		emergencyInfoService.saveEmergencyInfo(ei);
				
	}

}
