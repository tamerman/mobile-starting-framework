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

package org.kuali.mobility.shared.interceptors;

import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.push.entity.Device;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;
/**
 * Interceptor to attempt to keep track if the user is using KME natively
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.2.1
 */
public class NativeCookieInterceptor implements HandlerInterceptor {
	
	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger(NativeCookieInterceptor.class);

	/** Session variable that will be set with the platform value */
	public static final String SESSION_PLATFORM = "session_platform";
	
	/** Session variable that will be set with the phonegap version */
	public static final String SESSION_PHONEGAP = "session_phonegap";
	
	/** Session variable that will be set with the flag if the device is running native */
	public static final String SESSION_NATIVE 	= "session_native";

    /** Name of the cookie to set for the platform */
    public static final String COOKIE_PLATFORM = "platform";

    /** Name of the cookie to set the native flag */
    public static final String COOKIE_NATIVE = "native";

    /** Name of the cookie to set the phonegap version */
    public static final String COOKIE_PHONEGAP = "phonegap";

	/**
	 * Reference to the KME application properties
	 */
	@Resource(name="kmeProperties")
	private Properties kmeProperties;

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		checkPlatform(request, response);
		String phonegap = checkPhonegap(request, response);
		checkNative(request, response, phonegap);
        checkAuthenticatedUser(request, response) ;

		return true;
	}

    /**
     * Attempts to detect REMOTE_USER and sets currentNetworkId cookie with the value
     * @param request
     * @param response
     * @return
	 * @deprecated This could should be placed in an other interceptor, this interceptor is only meant to detect platform specifics
     */
	@Deprecated
    private void checkAuthenticatedUser(HttpServletRequest request, HttpServletResponse response){
        String loggedInUser = request.getRemoteUser();
		User user;
		if(StringUtils.isEmpty(loggedInUser) && ((user = (User)request.getSession().getAttribute(Constants.KME_USER_KEY)) != null)){
			loggedInUser = user.getLoginName();
		}

        LOG.debug("REMOTE_USER: "+ loggedInUser);
        if (loggedInUser != null && !loggedInUser.trim().isEmpty()) {
            boolean useSecureCookies = Boolean.parseBoolean(getKmeProperties().getProperty("kme.secure.cookie", "false"));
            Cookie userCookie = new Cookie("currentNetworkId", loggedInUser);
            userCookie.setMaxAge(60 * 60); //1hr
            userCookie.setPath(request.getContextPath());
            userCookie.setSecure(useSecureCookies);
            response.addCookie(userCookie);
            LOG.debug("Setting currentNetworkId cookie : " + loggedInUser);
        }
    }

	/**
	 * Attempts to detect the phonegap version and sets a cookie with the value
	 * @param request
	 * @param response
	 * @return
	 */
	private String checkPhonegap(HttpServletRequest request, HttpServletResponse response){
		String phonegapParam = request.getParameter(COOKIE_PHONEGAP);
		String phoneGapCookie = findCookie(request.getCookies(), COOKIE_PHONEGAP);
		String phonegapVersion = null;
		
		// If there is a phonegap param present, rather use that
		if(!StringUtils.isEmpty(phonegapParam)){
			phonegapVersion = phonegapParam;
		}
		// Else use the existing cookie if present
		else if(!StringUtils.isEmpty(phoneGapCookie)){
			phonegapVersion = phoneGapCookie;
		}

		boolean useSecureCookies = Boolean.parseBoolean(getKmeProperties().getProperty("kme.secure.cookie", "false"));
		Cookie cookie = new Cookie(COOKIE_PHONEGAP, phonegapVersion);
		
		int cookieMaxAge = Integer.parseInt(getKmeProperties().getProperty("cookie.max.age", "3600"));
		cookie.setMaxAge(cookieMaxAge); // default one hour, should implement in kme.config properties.
		cookie.setPath(request.getContextPath());
		cookie.setSecure(useSecureCookies);
		response.addCookie(cookie);

		LOG.debug("Setting cordova version : " + phonegapVersion);
		request.getSession().setAttribute(SESSION_PHONEGAP, phonegapVersion);

		return phonegapVersion;
	}
	
	/**
	 * Attempts to detect the platform and sets the platform cookie
	 * @param request
	 * @param response
	 * @return
	 */
	private String checkPlatform(HttpServletRequest request, HttpServletResponse response){
		String platformParam = request.getParameter(COOKIE_PLATFORM);
		String platformCookie = findCookie(request.getCookies(), COOKIE_PLATFORM);
		String platformName;
		
		// If there is a platform param, rather use that
		if (!StringUtils.isEmpty(platformParam)){
			platformName = platformParam;
		}
		// if there is a platform cookie, refresh it
		else if(!StringUtils.isEmpty(platformCookie)){
			platformName = platformCookie;
		}
		// If there still is no platform, try and detect it
		else{
			platformName = findPlatform(request);
		}

		boolean useSecureCookies = Boolean.parseBoolean(getKmeProperties().getProperty("kme.secure.cookie", "false"));
		Cookie cookie = new Cookie(COOKIE_PLATFORM, platformName);
		int cookieMaxAge = Integer.parseInt(getKmeProperties().getProperty("cookie.max.age", "3600"));		
		cookie.setMaxAge(cookieMaxAge); // default one hour, should implement in kme.config properties.
		cookie.setPath(request.getContextPath());
		cookie.setSecure(useSecureCookies);
		response.addCookie(cookie);
		LOG.debug("Setting platform cookie : " + platformName);

		request.getSession().setAttribute(SESSION_PLATFORM, platformName);
		return platformName;
	}
	
	
	/**
	 * Attempts to find the device platform (only for native)
	 * @param request
	 * @return
	 */
	private static final String findPlatform(HttpServletRequest request){
		String userAgent = request.getHeader("User-Agent");


		String platform;
        if (StringUtils.isEmpty(userAgent)){
            // We don't know what it is!
            platform = "none";
        }
		else if(userAgent.contains("iPhone") || userAgent.contains("iPad") || userAgent.contains("iPod") || userAgent.contains("Macintosh")){
			platform = Device.TYPE_IOS;
		}
		else if(userAgent.contains("Android")){
			platform = Device.TYPE_ANDROID;
		}
		else if(userAgent.contains("Blackberry") || userAgent.contains("BlackBerry")){
			platform = Device.TYPE_BLACKBERRY;
		}
		else if(userAgent.contains("MSIE")){
			platform = Device.TYPE_WINDOWS;
		}
		// We don't know what it is!
		else{
			platform = "none";
		}
		
		// Final chance to detect blackberry with the custom BlackBerry Header
		String rimHeader = request.getHeader("RIM-Widget"); 
		if (!StringUtils.isEmpty(rimHeader) && "KME-Blackberry-Application".equalsIgnoreCase(rimHeader)){
			platform = Device.TYPE_BLACKBERRY;
		}
		
		return platform;
	}

	/**
	 * Attempts tp check if the device is running natively and sets the native cookie
	 * @param request
	 * @param phonegap
	 * @return
	 */
	private boolean checkNative(HttpServletRequest request, HttpServletResponse response, String phonegap){
		String nativeParam  = request.getParameter("native");
		String nativeCookie = findCookie(request.getCookies(), COOKIE_NATIVE);
		boolean isNative = false;
		if (!StringUtils.isEmpty(nativeParam)){
			isNative = "yes".equalsIgnoreCase(nativeParam);
		}
		// If there is a phonegap version, it must be native
		else if (!StringUtils.isEmpty(phonegap)){
			isNative = true;
		}
		// Use the previous cookie value
		else if(!StringUtils.isEmpty(nativeCookie)){
			isNative = "yes".equalsIgnoreCase(nativeCookie);
		}
		
		/*
		 *  If detected a native setting, but there was no phonegap version, we have to 
		 *  assume something is wrong and not enable nativeness
		 */
		if (isNative && StringUtils.isEmpty(phonegap)){
			LOG.info("We detected a native user, but has no reference to a phonegap version - disabling nativeness");
			isNative = false;
		}
		
		// If there is a cordova version, it must be native
		boolean useSecureCookies = Boolean.parseBoolean(getKmeProperties().getProperty("kme.secure.cookie", "false"));
		Cookie cookie = new Cookie(COOKIE_NATIVE, (isNative ? "yes" : "no"));
		int cookieMaxAge = Integer.parseInt(getKmeProperties().getProperty("cookie.max.age", "3600"));
		cookie.setMaxAge(cookieMaxAge); // default one hour, should implement in kme.config properties.
		cookie.setPath(request.getContextPath());
		cookie.setSecure(useSecureCookies);
		response.addCookie(cookie);
		LOG.debug("Setting native cookie : " + isNative);

		request.getSession().setAttribute(SESSION_NATIVE, isNative);
		return isNative;
	}
	
	/**
	 * Finds a cookie with the specified name
	 * @param cookies
	 * @param cookie
	 * @return
	 */
	private static final String findCookie(Cookie[] cookies, String cookie){
		if (cookies == null || cookies.length == 0){
			return null;
		}
		for(Cookie c : cookies){
			if (cookie.equals(c.getName()) && !StringUtils.isEmpty(c.getValue())){
				return c.getValue();
			}
		}
		// Cookie not found, or had an empty value
		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {}

	/**
	 * Gets the reference to the KME Application properties.
	 * @return reference to the KME Application properties.
	 */
	public Properties getKmeProperties() {
		return kmeProperties;
	}

	/**
	 * Sets the reference to the KME Application properties.
	 * @param kmeProperties Reference to the KME Application properties.
	 */
	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
