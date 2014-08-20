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

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.kuali.mobility.admin.dao.AdminDao;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Service for actually performing administrative tasks
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Service(value = "ToolService")
public class ToolServiceImpl implements ToolService, ApplicationContextAware {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ToolServiceImpl.class);

	/**
	 * A reference to the <code>adminDao</code>.
	 */
	@Autowired
    private AdminDao adminDao;
	
	// TODO why do we need this??
	private ApplicationContext context;
	
	@Context
    private MessageContext messageContext;
	
	@Resource(name = "messageSource")
	private MessageSource messageSource;
	
	
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
	
	private String decode(String code){
		if(code != null){
			return messageSource.getMessage(code, null, null);
		}else{
			return code;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.service.AdminService#getAllTools()
	 */
	@GET
    @Path("/tools")
	@Override
	public List<Tool> getAllTools() {
		List<Tool> tools = getAdminDao().getAllTools();
		
		for(Tool tool : tools){
			tool.setTitle(decode(tool.getTitle()));
			tool.setDescription(decode(tool.getDescription()));
		}
		
		Collections.sort(tools);
		return tools;
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.service.AdminService#getToolById(long)
	 */
	@GET
    @Path("/toolById/{toolId}")
	@Override
	public Tool getToolById(@PathParam(value="toolId") Long toolId) {
		return getAdminDao().getToolById(toolId);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.service.AdminService#saveTool(org.kuali.mobility.admin.entity.Tool)
	 */
	@POST
    @Path("/saveTool")
	@Transactional
	@Override
	public Long saveTool(@RequestBody String data) {
		return null;
		//return getAdminDao().saveTool(tool);
	}

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.admin.service.AdminService#deleteToolById(long)
	 */
	@GET
    @Path("/deleteToolById/{toolId}")
	@Transactional
	@Override
	public void deleteToolById(@PathParam(value="toolId") Long toolId) {
		HttpServletRequest request;
		if( getMessageContext() != null ) {
            request = (HttpServletRequest) getMessageContext().getHttpServletRequest();
        } else {
            request = (HttpServletRequest)PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
        }
		if (null == request || null == request.getSession()) {
			LOG.error("request==null, quit!");
		} else {
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute(AuthenticationConstants.KME_USER_KEY);
            if (user.isMember("KME-ADMINISTRATORS")) {
            	getAdminDao().deleteToolById(toolId);
            }
		}
	}

	/**
	 * @return the adminDao
	 */
	public AdminDao getAdminDao() {
		return adminDao;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
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

}


