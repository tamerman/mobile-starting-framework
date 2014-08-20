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

package org.kuali.mobility.admin.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.security.authz.entity.AclExpression;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.entity.Backdoor;
import org.kuali.mobility.util.LocalisationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CXF Device Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFHomeScreenService {
	/** A reference to a logger for this service */
	private static final Logger LOG = LoggerFactory.getLogger(CXFHomeScreenService.class);
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	
    @Context
    private MessageContext messageContext;
	
	@Autowired
	private AdminService adminService;
	
    /**
     * A reference to the <code>LocalisationUtil</code>
     */
    @Autowired
    @Qualifier("localisationUtil")
    private LocalisationUtil localisationUtil;
	
	@Autowired
	private AlertsService alertsService;

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
	 * Return HomeScreen as Json.
	 * @return
	 */
	@GET
	@Path("/json")
	public String getHomeScreenAsJson(){
		String alias = "PUBLIC";
//		List<HomeTool> hometools = getAllHomeTools(alias);		
		List<HomeTool> hometools = getHomeTools();		
		
		Iterator<HomeTool> it = hometools.iterator();
		String json = "";
		while(it.hasNext()){
			HomeTool ht = it.next();
			json += "{\"homeToolId\":" + 	ht.getHomeToolId() + ",";
			json += "\"homeScreenId\":" + 	ht.getHomeScreenId() +  ","; 
			json += "\"toolId\":" + 		ht.getToolId() +  ",";
			json += "\"order\":" + 		ht.getOrder() + ",";
			json += "\"versionNumber\":" + ht.getVersionNumber() + ",";
			json += "\"tool\":" + localizeTool(ht.getTool()).toJson() + "},";
		}
		json = json.substring(0, json.length() - 1);		
		return "{\"tools\":[" + json + "]}";
	}
	
	/**
	 * Return badge text and count with tool id for showing on homesreen temporary static values as Json.
	 * @return
	 */
	@GET
	@Path("/badges")
	public String getBadgeDetails(){
		String returnJson="{\"tools\":[{\"homeToolId\":1,\"badgeText\":\"Beta\",\"badgeCount\":1},{\"homeToolId\":2,\"badgeText\":\"\",\"badgeCount\":2},{\"homeToolId\":3,\"badgeText\":\"Beta\",\"badgeCount\":3},{\"homeToolId\":4,\"badgeText\":\"\",\"badgeCount\":4},{\"homeToolId\":6,\"badgeText\":\"\",\"badgeCount\":6},{\"homeToolId\":7,\"badgeText\":\"Beta\",\"badgeCount\":7}]}";
		return returnJson;
	}
	
	private String decode(String code){
		if(code != null){
			return messageSource.getMessage(code, null, null);
		}else{
			return code;
		}
	}
	
	private Tool localizeTool(Tool tool){
		Tool result = new Tool();
		result.setAclPublishingId(tool.getAclPublishingId());
		result.setAclViewingId(tool.getAclViewingId());
		result.setAlias(tool.getAlias());
		result.setBadgeCount(tool.getBadgeCount());
		result.setBadgeText(tool.getBadgeText());
		result.setContacts(tool.getContacts());
		result.setToolId(tool.getToolId());
		result.setUrl(tool.getUrl());
		result.setIconUrl(tool.getIconUrl());
		result.setKeywords(tool.getKeywords());
		result.setPublishingPermission(tool.getPublishingPermission());
		result.setRequisites(tool.getRequisites());
		
		//Localised Fields
		result.setSubtitle(decode(tool.getSubtitle()));
		result.setTitle(decode(tool.getTitle()));
		result.setDescription(decode(tool.getDescription()));
		return result;
	}
	
	
	private List<HomeTool> getAllHomeTools(String alias){
		HomeScreen home = getAdminService().getHomeScreenByAlias(alias);
		List<HomeTool> copy;
		if (home == null) {
			LOG.error("Home screen object still null when it should not be.");
			copy = new ArrayList<HomeTool>();
		} else {
			copy = new ArrayList<HomeTool>(home.getHomeTools());
		}
		Collections.sort(copy);

		List<HomeTool> userTools = new ArrayList<HomeTool>();
		for (HomeTool homeTool : copy) {
			userTools.add(homeTool);
		}
		return userTools;
	}
	
	private List<HomeTool> getHomeTools(){		
//		HttpServletRequest request = (HttpServletRequest)PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");		
		HttpServletRequest request = (HttpServletRequest) this.getMessageContext().getHttpServletRequest();
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);

		int myState = 0;
		Cookie cks[] = request.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("native")) {
					if ("yes".equals(c.getValue())) {
						myState |= Tool.NATIVE;
					} else {
						myState |= Tool.NON_NATIVE;
					}
				}
				if (c.getName().equals("platform")) {
					if ("iOS".equals(c.getValue())) {
						myState |= Tool.IOS;
					} else if ("Android".equals(c.getValue())) {
						myState |= Tool.ANDROID;
					} else if ("WindowsMobile".equals(c.getValue())) {
						myState |= Tool.WINDOWS_PHONE;
					} else if ("Blackberry".equals(c.getValue())) {
						myState |= Tool.BLACKBERRY;
					}
				}
			}
		}

		String alias = "PUBLIC";
		if (request.getParameter("alias") != null) {
			alias = request.getParameter("alias").toUpperCase();
		}

		HomeScreen home;
		if (user != null && user.getViewCampus() != null) {
			alias = user.getViewCampus();
		}
		
		home = getAdminService().getHomeScreenByAlias(alias);
		if (home == null) {
			LOG.debug("Home screen was null, getting PUBLIC screen.");
			home = getAdminService().getHomeScreenByAlias("PUBLIC");
		}

		List<HomeTool> copy;
		if (home == null) {
			LOG.error("Home screen object still null when it should not be.");
			copy = new ArrayList<HomeTool>();
		} else {
			copy = new ArrayList<HomeTool>(home.getHomeTools());
		}
		Collections.sort(copy);

		List<HomeTool> userTools = new ArrayList<HomeTool>();
		for (HomeTool homeTool : copy) {
			Tool tool = homeTool.getTool();

			AclExpression viewingPermission = tool.getViewingPermission();
			if (viewingPermission != null && viewingPermission.getParsedExpression() != null && !viewingPermission.getParsedExpression().evaluate(user)) {
				continue;
			}

			if ("alerts".equals(tool.getAlias())) {
				int count = getAlertsService().findAlertCountByCampus(alias);
				if (count > 0) {
					tool.setBadgeCount(Integer.toString(count));
					tool.setIconUrl("images/service-icons/srvc-alerts-red.png");
				} else {
					tool.setBadgeCount(null);
					tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
				}
			}
			if ("incident".equals(tool.getAlias()) || "reportingadmin".equals(tool.getAlias())) {
				tool.setBadgeText("beta");
			}

			if ("backdoor".equals(tool.getAlias())) {
				if (backdoor != null) {
					tool.setBadgeCount(backdoor.getUserId());
				} else {
					tool.setBadgeCount("");
				}
			}
			// If a tools requisites is unset it will be treated as available to any, same as Tool.ANY.
			if ((tool.getRequisites() & myState) == myState || (tool.getRequisites() == Tool.UNDEFINED_REQUISITES)) {
				LOG.info("--- SHOW TOOL: " + tool.getAlias());
				userTools.add(homeTool);
			} else {
				LOG.info("--- HIDE TOOL: " + tool.getAlias());
			}
		}
		return userTools;
	}
	
	
	
	
	/**
	 * @return the messageSource
	 */
	public MessageSource getMessageSource() {
		return messageSource;
	}

	/**
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * @return the messageContext
	 */
	public MessageContext getMessageContext() {
		return messageContext;
	}

	/**
	 * @param messageContext the messageContext to set
	 */
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	/**
	 * @return the adminService
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	/**
	 * @param adminService the adminService to set
	 */
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}



	/**
	 * @return the localisationUtil
	 */
	public LocalisationUtil getLocalisationUtil() {
		return localisationUtil;
	}

	/**
	 * @param localisationUtil the localisationUtil to set
	 */
	public void setLocalisationUtil(LocalisationUtil localisationUtil) {
		this.localisationUtil = localisationUtil;
	}

	/**
	 * @return the alertsService
	 */
	public AlertsService getAlertsService() {
		return alertsService;
	}

	/**
	 * @param alertsService the alertsService to set
	 */
	public void setAlertsService(AlertsService alertsService) {
		this.alertsService = alertsService;
	}
		
}
