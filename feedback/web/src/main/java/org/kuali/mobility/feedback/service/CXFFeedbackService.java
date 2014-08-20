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

package org.kuali.mobility.feedback.service;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.kuali.mobility.email.service.EmailService;
import org.kuali.mobility.feedback.dao.FeedbackDao;
import org.kuali.mobility.feedback.entity.Feedback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Implementation of the CXF Sender Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFFeedbackService implements ApplicationContextAware {

	/** A reference to a logger for this service */
	private static final Logger LOG = LoggerFactory.getLogger(CXFFeedbackService.class);

    private ApplicationContext applicationContext;

    @Context
    private MessageContext messageContext;

    /**
     * A reference to the Spring Locale Resolver
     */
    @Resource(name="localeResolver")
    private LocaleResolver localeResolver;

	@Resource(name="kmeProperties")
	private Properties kmeProperties;
	
	/** A reference to the FeedbackService */
    @Autowired
    private FeedbackService feedbackService;

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
    	
	@POST
	@Path("/send")
	public Response sendFeedback(@RequestBody String data){
        if (data == null) {
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build();
        }

        JSONObject queryParams;
        try {
            queryParams = (JSONObject) JSONSerializer.toJSON(data);
            LOG.info(queryParams.toString());
        } catch (JSONException je) {
            LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
        }
        
        try {
        	// -----------------------
        	// SAVE FEED BACK AND SEND
        	// -----------------------
            HttpServletRequest request;
            if( getMessageContext() != null ) {
                request = (HttpServletRequest) getMessageContext().getHttpServletRequest();
            } else {
                request = (HttpServletRequest) PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
            }
            User user = null;
            if (request !=null) {
                user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
            }

            Locale userLocale = this.localeResolver.resolveLocale(request);
		    Feedback fb = new Feedback();
		    fb.setNoteText(queryParams.getString("noteText"));
		    fb.setDeviceType(queryParams.getString("deviceType"));
		    fb.setUserAgent(queryParams.getString("userAgent"));
		    fb.setService(queryParams.getString("service"));
		    fb.setEmail(queryParams.getString("email"));
            if ( user != null  ) {
                if (user.getLoginName()!=null)
                    fb.setUserId(user.getLoginName());
                if (fb.getCampus()==null || fb.getCampus().trim().isEmpty())
                    fb.setCampus(user.getViewCampus());
            }

        	this.getFeedbackService().saveFeedback(fb, userLocale);
            LOG.info("Boom, all saved & sent! userLocale=" + userLocale);
		} catch (Exception e) {
            LOG.error("Exception while trying to send feedback", e);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}
        
		return Response.status(Response.Status.OK.getStatusCode()).build();
	}	
	
	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public MessageContext getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }
}
