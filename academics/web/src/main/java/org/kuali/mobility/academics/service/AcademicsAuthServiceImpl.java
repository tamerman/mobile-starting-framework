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

package org.kuali.mobility.academics.service;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.kuali.mobility.academics.dao.AcademicsDao;
import org.kuali.mobility.academics.entity.Section;
import org.kuali.mobility.academics.entity.Term;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.DeviceService;
import org.kuali.mobility.push.service.PushService;
import org.kuali.mobility.push.service.rest.pojo.SendPushRequest;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.api.UserAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.util.*;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.1.0
 */
@Service
@WebService(endpointInterface = "org.kuali.mobility.academics.service.AcademicsAuthService")
public class AcademicsAuthServiceImpl implements AcademicsAuthService, ApplicationContextAware {
	private static final Logger LOG = LoggerFactory.getLogger(AcademicsAuthServiceImpl.class);

	@Resource(name="academicsDao")
	private AcademicsDao dao;

	@Resource(name="kmeUserDao")
	private UserDao userDao;

    @Autowired
    private DeviceService deviceService;

	private ApplicationContext applicationContext;

	@Context
	private MessageContext messageContext;

    @Autowired
    private PushService pushService;

    @Autowired
    @Qualifier("academicsProperties")
    private Properties academicsProperties;

	@Override
	@GET
	@Path("/getTerms")
	public List<Term> getTerms() {
		LOG.debug("Entering getTerms.");

        HttpServletRequest request;
        if( getMessageContext() != null ) {
            request = (HttpServletRequest) getMessageContext().getHttpServletRequest();
        } else {
            request = (HttpServletRequest)PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
        }
        return getTerms( request );
    }

	public List<Term> getTerms(HttpServletRequest request) {
        LOG.debug( "Entering getTerms." );
		List<Term> terms = new ArrayList<Term>();
		Map<String, String> query = new HashMap<String, String>();

		String remoteUser = null;
		if (null != request) {
			remoteUser = request.getRemoteUser();
		}
        else {
            LOG.error( "HttpServletRequest is null, not able to get the RemoteUser !!!");
        }

		query.put(AcademicsConstants.USER_ID, remoteUser);

		terms = getDao().getTerms(query);
		return terms;
	}

	@Override
	@GET
	@Path("/getClassSchedule")
	public List<Section> getClassSchedule(
		@QueryParam("termId") final String termId,
		@QueryParam("careerId") final String careerId) {

        HttpServletRequest request;
        if( getMessageContext() != null ) {
            request = (HttpServletRequest) getMessageContext().getHttpServletRequest();
        } else {
            request = (HttpServletRequest)PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
        }
		return getClassSchedule(request, termId, careerId);
	}

	public List<Section> getClassSchedule(
		HttpServletRequest request,
		@QueryParam("termId") final String termId,
		@QueryParam("careerId") final String careerId) {
		List<Section> sections = new ArrayList<Section>();

		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.CAREER_ID, careerId);
		query.put(AcademicsConstants.TERM_ID, termId);

		String remoteUser = null;
		if (null != request) {
			remoteUser = request.getRemoteUser();
		}
        else {
            LOG.error( "HttpServletRequest is null, not able to get the RemoteUser !!!");
        }

		query.put(AcademicsConstants.USER_ID, remoteUser);

		sections = getDao().getMyClassSchedule(query);

		return sections;
	}

    @Override
    @Path("/getGradeAlertOpt")
    @GET
    public String getGradeAlertOpt() {
        String optInStatus= "off";
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
            User user = (User) session.getAttribute(AuthenticationConstants.KME_USER_KEY);
            String remoteUser = request.getRemoteUser();
            LOG.info("request.getRemoteUser() = " + request.getRemoteUser());
            if (user == null) {
                LOG.error("User is null in session.  This should NEVER occur!");

                if (remoteUser == null || remoteUser.isEmpty()) {
                    LOG.error("====== Can't get remote user, quit!");
                } else {
                    user = getUserDao().loadUserByLoginName(remoteUser);
                }
            } else if (user.isPublicUser()) {
                LOG.error(user.getLoginName() + "====== Public user found when opting in. This should never happen ======");
                LOG.error("Mismatch to RemoteUser[" + remoteUser + "], reset to RemoteUser");
                user.setLoginName(remoteUser);
            }

            if (user != null) {
                List<UserAttribute> attributes = user.getAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
                if (attributes == null || attributes.isEmpty()) {
                    optInStatus = "off";
                } else if (attributes.size() == 1) {
                    optInStatus = attributes.get(0).getAttributeValue();
                } else if (attributes.size() > 1) {
                    // This case shouldn't ever happen.  There should never be more than one
                    // attribute for this particular key.
                    optInStatus = attributes.get(0).getAttributeValue();
                    user.clearAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
                    getUserDao().saveUser(user);
                    user.addAttribute(AcademicsConstants.USER_ATTR_GRADEALERT, optInStatus);
                    getUserDao().saveUser(user);
                }
            }
        }
        return optInStatus;
    }

    @Override
	@Path("/updateGradeAlertOpt")
	@GET
	public boolean updateGradeAlertOpt(@QueryParam("opt") final String opt) {
		boolean updateResult = false;

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
            String remoteUser = request.getRemoteUser();
            LOG.info("request.getRemoteUser() = " + request.getRemoteUser());
            if( user == null ) {
                LOG.error("User is null in session.  This should NEVER occur!");

                if (remoteUser == null || remoteUser.isEmpty()) {
                    LOG.error("====== Can't get remote user, quit!");
                } else {
                    user = getUserDao().loadUserByLoginName(remoteUser);
                }
            }
            else if ( user.isPublicUser() )   {
                LOG.error(user.getLoginName() + "====== Public user found when opting in. This should never happen ======");
                LOG.error("Mismatch to RemoteUser[" + remoteUser + "], reset to RemoteUser");
                user.setLoginName(remoteUser);
            }

            if( user != null ) {
                LOG.debug("====== User exists, attempting to update grade alert with "+opt);
                updateResult = user.removeAttribute(AcademicsConstants.USER_ATTR_GRADEALERT);
                LOG.debug("====== removeAttribute on grade alerts returned "+updateResult);
                user.addAttribute(AcademicsConstants.USER_ATTR_GRADEALERT, opt);

                if( user.isPublicUser() ) {
                    LOG.error("====== Public user found when opting in. This should never happen ======");
                } else {
                    if (null != getUserDao().saveUser(user)) {
                        updateResult = true;
                    }
                }
                if( session != null ) {
                    String deviceId = (String)session.getAttribute(AuthenticationConstants.DEVICE_ID);
                    if( null == deviceId ) {
                        LOG.debug("====== No device id stored in session. ======");
                    } else {
                        Device device = deviceService.findDeviceByDeviceId(deviceId);
                        if( device == null ) {
                            LOG.error("====== Unable to find device for id in session ======");
                        } else {
                            device.setUsername(user.getLoginName());
                            deviceService.saveDevice(device);
                            LOG.debug("Updating device "+deviceId+" to belong to "+user.getLoginName());
                        }
                    }
                }
            }
		}
		return updateResult;
	}

    @Override
    @Path("testGradeAlert")
    @GET
    public String testGradeAlert () {
        String result = "0";
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
            if( session != null ) {
                String deviceId = (String)session.getAttribute(AuthenticationConstants.DEVICE_ID);
                LOG.info("device id stored in session is " + deviceId);

                    Push push = new Push();
                    push.setTitle("[TEST] " + getAcademicsProperties().getProperty("academics.grade.alert.push.title"));
                    push.setMessage("[TEST] " + getAcademicsProperties().getProperty("academics.grade.alert.push.message"));
                    push.setEmergency(true);
                    push.setSender("RS5XcyVYoHSgnLVY2ZZw");
                    push.setUrl(null);

				SendPushRequest spr = new SendPushRequest();
				spr.setPush(push);
				push = pushService.sendPush(push,Arrays.asList(new String[]{request.getRemoteUser()}), null);
                LOG.info("Post Push:" + push.toString());
                result = "2";
            }
        }
        return result;
    }

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

    public void setDeviceService(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    public void setPushService(PushService pushService) {
        this.pushService = pushService;
    }

    /**
	 * }
	 *
	 * @return the dao
	 */
	public AcademicsDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(AcademicsDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param context the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext context) {
		this.applicationContext = context;
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


    public Properties getAcademicsProperties() {
        return academicsProperties;
    }

    public void setAcademicsProperties(Properties academicsProperties) {
        this.academicsProperties = academicsProperties;
    }


}
