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

package org.kuali.mobility.auth.service;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.auth.dao.AuthDao;
import org.kuali.mobility.auth.entity.AuthResponse;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;

/**
 * Implementation of the <code>AuthService</code>
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
@Service
@WebService(endpointInterface = "org.kuali.mobility.auth.service.AuthService")
public class AuthServiceImpl implements AuthService {
	
	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger( AuthServiceImpl.class );

    @Context
    private MessageContext messageContext;

	/**
	 * A reference to the <code>AuthDao</code>.
	 */
	@Resource(name="authDao")
    private AuthDao dao;

    @Override
    @POST
    @Path("/login")
    public AuthResponse authenticate(@FormParam("loginName") String loginName,
        @FormParam("password") String password) {
        AuthResponse response = getDao().authenticate(loginName,password);

        if( response.didAuthenticate() ) {
            HttpServletRequest request;
            if( getMessageContext() != null ) {
                request = (HttpServletRequest) getMessageContext().getHttpServletRequest();
            } else {
                request = (HttpServletRequest) PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
            }
            HttpSession session = request.getSession();
            session.setAttribute(AuthenticationConstants.KME_USER_KEY,response.getUser());
        }

        response.setUser(null);

        return response;
    }

	/**
	 * Sets the reference to the <code>AuthDao</code>.
	 * @param dao The reference to the <code>AuthDao</code>.
	 */
	public void setDao(AuthDao dao) {
		this.dao = dao;
	}
	
	/**
	 * Gets the reference to the <code>AuthDao</code>.
	 * @returns The reference to the <code>AuthDao</code>.
	 */
	public AuthDao getDao() {
		return dao;
	}

    public MessageContext getMessageContext() {
        return messageContext;
    }

    public void setMessageContext(MessageContext messageContext) {
        this.messageContext = messageContext;
    }
}
