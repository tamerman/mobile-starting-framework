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

package org.kuali.mobility.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.authn.util.AuthenticationMapper;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.CoreService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class ChildPageTag extends SimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ChildPageTag.class);
    private AuthenticationMapper authMapper;
    private String id;
    private String title;
    private boolean homeButton;
    private boolean backButton;
    private String backButtonURL;
    private boolean preferencesButton;
    private String preferencesButtonURL;
    private boolean loginButton;
    private String loginButtonURL;
    private String logoutButtonURL;
	private String jqmHeader;

    public void doTag() throws JspTagException {
        PageContext pageContext = (PageContext) getJspContext();
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        setAuthMapper((AuthenticationMapper) ac.getBean("authenticationMapper"));
        CoreService coreService = (CoreService) ac.getBean("coreService");
        User user = (User) pageContext.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
        String contextPath = servletContext.getContextPath();
        JspWriter out = pageContext.getOut();

        
        // Get Cookies for Phonegapy stuff.
        HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
        Cookie cks[] = hsr.getCookies();
        if(cks != null){
	        for(Cookie c : cks){
	            if(c.getName().equals("jqmHeader")){
	            	LOG.info("---jqmHeader: " + jqmHeader);
	            	jqmHeader = c.getValue();
	            	LOG.info("---jqmHeader: " + jqmHeader);
	            }
	        }
        }
        
        	
        
        try {
            out.println("<div data-role=\"page\" id=\"" + getId() + "\">");
            
            
        	LOG.info("----" + jqmHeader);
            if(getJqmHeader() != null && getJqmHeader().equals("hide")){
            	LOG.info("---- Hide Header");
            	out.println("<div data-role=\"header\" style=\"display:none\">");
            }else if(getJqmHeader() != null && getJqmHeader().equals("fixed")){
            	LOG.info("---- Fixed Header");
            	out.println("<div data-role=\"header\" data-position=\"fixed\">");
            }else{
            	LOG.info("---- Show Header");
           	   	out.println("<div data-role=\"header\">");
            }
            
            
//            out.println("<div data-role=\"header\">");

            if (isLoginButton() || getAuthMapper().getLoginURL() != null) {
                if (user == null || user.isPublicUser()) {
                    out.println("<a href=\"" + (getLoginButtonURL() != null ? getLoginButtonURL() : (getAuthMapper().getLoginURL() != null ? getAuthMapper().getLoginURL() : contextPath + "/login")) + "\" data-role=\"button\" data-icon=\"lock\">Login</a>");
                } else {
                    out.println("<a href=\"" + (getLogoutButtonURL() != null ? getLogoutButtonURL() : (getAuthMapper().getLogoutURL() != null ? getAuthMapper().getLogoutURL() : contextPath + "/logout")) + "\" data-role=\"button\" data-icon=\"unlock\">Logout</a>");
                }
            }

            if (isBackButton()) {
                out.println("<a href=\"" + (getBackButtonURL() != null ? getBackButtonURL() : "javascript:history.back()") + "\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\" data-transition=\"pop\">Back</a>");
            }
            out.println("<h1>" + getTitle() + "</h1>");
            if (isPreferencesButton()) {
                out.println("<a href=\"" + (getPreferencesButtonURL() != null ? getPreferencesButtonURL() : contextPath + "/preferences") + "\" class=\"ui-btn-right\" data-icon=\"gear\" data-iconpos=\"notext\">Preferences</a>");
            }
            if (isHomeButton()) {
                out.println("<a href=\"" + contextPath + "/home\" class=\"ui-btn-right\" data-icon=\"home\" data-iconpos=\"notext\">Home</a>");
            }
            out.println("</div>");
            getJspBody().invoke(out);
            out.println("</div>");
        } catch (IOException ioe) {
            throw new JspTagException(ioe);
        } catch (JspException je) {
            throw new JspTagException(je);
        }
    }

    public String getJqmHeader() {
		return jqmHeader;
	}

	public void setJqmHeader(String jqmHeader) {
		this.jqmHeader = jqmHeader;
	}

	/**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the homeButton
     */
    public boolean isHomeButton() {
        return homeButton;
    }

    /**
     * @param homeButton the homeButton to set
     */
    public void setHomeButton(boolean homeButton) {
        this.homeButton = homeButton;
    }

    /**
     * @return the backButton
     */
    public boolean isBackButton() {
        return backButton;
    }

    /**
     * @param backButton the backButton to set
     */
    public void setBackButton(boolean backButton) {
        this.backButton = backButton;
    }

    /**
     * @return the backButtonURL
     */
    public String getBackButtonURL() {
        return backButtonURL;
    }

    /**
     * @param backButtonURL the backButtonURL to set
     */
    public void setBackButtonURL(String backButtonURL) {
        this.backButtonURL = backButtonURL;
    }

    /**
     * @return the preferencesButton
     */
    public boolean isPreferencesButton() {
        return preferencesButton;
    }

    /**
     * @param preferencesButton the preferencesButton to set
     */
    public void setPreferencesButton(boolean preferencesButton) {
        this.preferencesButton = preferencesButton;
    }

    /**
     * @return the preferencesButtonURL
     */
    public String getPreferencesButtonURL() {
        return preferencesButtonURL;
    }

    /**
     * @param preferencesButtonURL the preferencesButtonURL to set
     */
    public void setPreferencesButtonURL(String preferencesButtonURL) {
        this.preferencesButtonURL = preferencesButtonURL;
    }

    /**
     * @return the loginButton
     */
    public boolean isLoginButton() {
        return loginButton;
    }

    /**
     * @param loginButton the loginButton to set
     */
    public void setLoginButton(boolean loginButton) {
        this.loginButton = loginButton;
    }

    /**
     * @return the loginButtonURL
     */
    public String getLoginButtonURL() {
        return loginButtonURL;
    }

    /**
     * @param loginButtonURL the loginButtonURL to set
     */
    public void setLoginButtonURL(String loginButtonURL) {
        this.loginButtonURL = loginButtonURL;
    }

    /**
     * @return the logoutButtonURL
     */
    public String getLogoutButtonURL() {
        return logoutButtonURL;
    }

    /**
     * @param logoutButtonURL the logoutButtonURL to set
     */
    public void setLogoutButtonURL(String logoutButtonURL) {
        this.logoutButtonURL = logoutButtonURL;
    }

    /**
     * @return the authMapper
     */
    public AuthenticationMapper getAuthMapper() {
        return authMapper;
    }

    /**
     * @param authMapper the authMapper to set
     */
    public void setAuthMapper(AuthenticationMapper authMapper) {
        this.authMapper = authMapper;
    }
}
