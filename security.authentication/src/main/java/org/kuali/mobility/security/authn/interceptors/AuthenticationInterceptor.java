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

package org.kuali.mobility.security.authn.interceptors;

import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.authn.util.AuthenticationMapper;
import org.kuali.mobility.security.user.api.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The AuthenticationInterceptor is a check against the authentication 
 * xml properties file to see if a particular URL must use authentication.  
 * If it does and there is no user in the session then it forces a 
 * redirection.  It is a backup for the SSO filters (CAS/CoSIgn/Shibboleth/etc) 
 * to protect a resource.  If you somehow got to this filter without satisfying 
 * SSO, it will step in and send you to SSO to log in.
 *
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

	private static final Logger LOG = LoggerFactory.getLogger( AuthenticationInterceptor.class);
	
	private AuthenticationMapper authenticationMapper;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = (User)request.getSession(true).getAttribute( AuthenticationConstants.KME_USER_KEY );

		boolean passThrough = true;
		if( getAuthenticationMapper().requiresAuthentication( request.getServletPath() ) )
		{
			if( user == null )
			{
				LOG.info( "User object not found in session.  This should not happen." );
				doLogin( request, response );
				passThrough=false;
			}
			else if( user.isPublicUser() )
			{
				user.setRequestURL(request.getServletPath());
				doLogin( request, response );
				passThrough=false;
			}
		}
		return passThrough;
	}

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if( getAuthenticationMapper().getLoginURL().startsWith( "http:" ) )
		{
			response.sendRedirect( getAuthenticationMapper().getLoginURL() );
		}
		else
		{
			response.sendRedirect( request.getContextPath() + getAuthenticationMapper().getLoginURL() );
		}
	}

	public AuthenticationMapper getAuthenticationMapper() {
		return authenticationMapper;
	}

	public void setAuthenticationMapper(AuthenticationMapper authenticationMapper) {
		this.authenticationMapper = authenticationMapper;
	}
	
}
