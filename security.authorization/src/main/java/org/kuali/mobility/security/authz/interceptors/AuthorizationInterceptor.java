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

package org.kuali.mobility.security.authz.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.user.api.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AuthorizationInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession(true).getAttribute(AuthenticationConstants.KME_USER_KEY);

        if (user != null && !user.isPublicUser() && user.getLoginName() != null) {
            
            List<Group> groups = user.getGroups();
            if (groups == null){
            	groups = new ArrayList<Group>();
            }

// Removing this since it doesn't seem to serve any purpose at this time. -joseswan
//            user.addAttribute("user.authenticated", "true");

            String principalName = user.getLoginName().trim();
            
            // TODO: Refactor this to use and injected AuthZ Data Source (Active Directory, LDAP, etc)

            if ("admin".equals(principalName)) {
                user.addAttribute("user.campus", "ALL");
//                groups.add("KME-ADMIN");
            } else if ("student".equals(principalName)) {
                user.addAttribute("user.campus", "BL");
//                groups.add("KME-STUDENT");
            } else if ("staff".equals(principalName)) {
                user.addAttribute("user.campus", "IN");
//                groups.add("KME-STAFF");
            } else if ("faculty".equals(principalName)) {
                user.addAttribute("user.campus", "BL");
//                groups.add("KME-FACULTY");
            }
                        
            user.setGroups(groups);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

}
