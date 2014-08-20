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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The RemoteUserInterceptor looks for a remote_user and creates a user 
 * object in the session for it.  It is the base hook to start an authenticated 
 * session. All authN and authZ should either be added to it or come after 
 * it in the filter/interceptor chain.
 */
public class RemoteUserInterceptor implements HandlerInterceptor {
	private static final Logger LOG = LoggerFactory.getLogger( RemoteUserInterceptor.class );

	@Autowired
	@Qualifier("kmeUserDao")
	private UserDao userDao;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		LOG.trace("RemoteUserInterceptor preHandle");
        User user = (User)request.getSession(true).getAttribute( AuthenticationConstants.KME_USER_KEY );
		if( user == null )
		{
            LOG.debug("No user found in session, creating a new one.");
			user = new UserImpl();
			request.getSession().setAttribute( AuthenticationConstants.KME_USER_KEY, user );
		}
		if( request.getRemoteUser() != null && !request.getRemoteUser().isEmpty() )
		{
            LOG.debug("REMOTE_USER exists. Preparing to reconcile user :"+request.getRemoteUser());

			if( user.isPublicUser() )
			{
                LOG.debug("User was public, loading user "+request.getRemoteUser()+" from database.");
				User existingUser = getUserDao().loadUserByLoginName(request.getRemoteUser());
				if( existingUser == null ) {
                    LOG.debug("User "+request.getRemoteUser()+" not found in database.");
					user.setLoginName(request.getRemoteUser());
					getUserDao().saveUser(user);
				} else {
                    LOG.debug("User found and being pushed into session.");
					request.getSession().setAttribute( AuthenticationConstants.KME_USER_KEY, existingUser );
				}
			}
			else if( !request.getRemoteUser().equalsIgnoreCase( user.getLoginName() ) )
			{
				LOG.info( "Identify mismatch. Expected ["+user.getLoginName()+"] recieved ["+request.getRemoteUser()+"]" );
				user.invalidateUser();
				request.getSession().invalidate();
				request.getSession(true);
				response.sendError(401, "Identity Mismatch.  Attempting to override existing user with a new one." );
			}

		}

        /*	Comment out the following block as all requests (include static contents i.e. image, cs and js files) go through
            this interceptor, while only requests for protected/secured contents have REMOTE_USER.
            Should improve or refactor in the future.
        */
        /*
        else
		{
            LOG.info("REMOTE_USER NOT exists");
			if( user.getLoginName() != null )
			{
				if( !user.getLoginName().startsWith( AuthenticationConstants.PUBLIC_USER ) )
				{
					LOG.info( "Identity mismatch. Session user populated when no REMOTE_USER provided. User removed from session." );
					user.setLoginName(AuthenticationConstants.PUBLIC_USER + request.getSession().getId());
				}
				else if( !user.getLoginName().equalsIgnoreCase( AuthenticationConstants.PUBLIC_USER + request.getSession().getId() ) )
				{
					LOG.info( "Identity mismatch. Public user key does not match expected id. User updated in session.");
					user.invalidateUser();
                    user = new UserImpl();
					user.setLoginName(AuthenticationConstants.PUBLIC_USER + request.getSession().getId());
                    request.getSession().setAttribute( AuthenticationConstants.KME_USER_KEY, user );
                }
			}
		}
		*/
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
