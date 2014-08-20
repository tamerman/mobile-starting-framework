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

package org.kuali.mobility.auth.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.auth.service.AuthService;
import org.kuali.mobility.shared.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * Controller for Auth
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0-SNAPSHOT
 */
@Controller
@RequestMapping("/auth")
public class AuthControllerImpl {

	/** 
	 * A reference to a Logger 
	 */
	private static final Logger LOG = LoggerFactory.getLogger( AuthControllerImpl.class );

	@Resource(name="authService")
	private AuthService service;

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    /**
	 * Controller to load the index page for this tool
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
        String viewName = null;

        if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/auth/index";
        } else {
            viewName = "auth/index";
        }

        return viewName;
	}

    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
        @PathVariable("key") String key,
                HttpServletRequest request,
        Model uiModel ) {
        return "ui3/auth/templates/"+key;
    }

    @RequestMapping(value = "/js/{key}.js")
    public String getJavaScript(
        @PathVariable("key") String key,
        HttpServletRequest request,
        Model uiModel) {
        return "ui3/auth/js/"+key;
    }

    @RequestMapping(value="logout")
    public String logout(HttpServletRequest request, Model uiModel) {
        return "ui3/auth/logout";
    }

    @RequestMapping(value="logoutConfirm")
    public String logoutConfirm(HttpServletRequest request, Model uiModel) {
        request.getSession().setAttribute(Constants.KME_MOCK_USER_KEY, null);
        request.getSession().setAttribute(Constants.KME_USER_KEY, null);
        return "redirect:/home";
    }

    /**
	 * Sets the reference to the <code>AuthService</code>
	 */
	public void setService(AuthService service) {
		this.service = service;
	}

    /**
     * A reference to the <code>AuthService</code>
     */
    public AuthService getService() {
        return service;
    }

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }
}
