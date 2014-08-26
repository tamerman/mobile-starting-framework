/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.tags;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.authn.util.AuthenticationMapper;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.CoreService;
import org.kuali.mobility.shared.interceptors.NativeCookieInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * The backing class for the Page JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class PageTag extends SimpleTagSupport {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PageTag.class);

	/**
	 * @return the appcacheEnabled
	 */
	public static String getAppcacheEnabled() {
		return appcacheEnabled;
	}

	private AuthenticationMapper authMapper;

	/**
	 * +
	 * ID to give to the page
	 */
	private String id;

	/**
	 * Title of the page
	 */
	private String title;

	/**
	 * Flag if the home button should be displayed
	 */
	private boolean homeButton;

	/**
	 * Flag if the back button should be displayed
	 */
	private boolean backButton;

	/**
	 * Link that should be used for the back url.
	 * If no link is specified, the last page in history
	 * will be used
	 */
	private String backButtonURL;

	/**
	 * Flag if the preference button should be displayed.
	 */
	private boolean preferencesButton;

	/**
	 * Link that should be used for prefernces.
	 */
	private String preferencesButtonURL;

	/**
	 * Flag if the current page makes use of maps
	 */
	private boolean usesGoogleMaps;

	/**
	 * Name of the appcache file to use.
	 * If no file name is specified, appcache will be disabled for
	 * this page
	 */
	private String appcacheFilename;

	/**
	 * Comma separated list of style sheets to attach.
	 * File names will have {contextPath}/css/ prepended as well
	 * as the .css extension
	 */
	private String cssFilename;

	/**
	 * Javascript function to call onBodyLoad.
	 */
	private String onBodyLoad;

	/**
	 * Force a specific platform for this page.
	 * This is not normally necessary, the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used by the tag to detect the platform.
	 */
	private String platform;

	/**
	 * Force a specific phonegap version on this page.
	 * This is not normally necessary, the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used by the tag to detect the phonegap version.
	 */
	private String phonegap;

	/**
	 * Comma separated list of javascript files to additionally include with this page.
	 * The file names will have {contextpath}/js prepended as well as the
	 * .js extension
	 */
	private String jsFilename;

	/**
	 * Optional setting on the jquery mobile header
	 * fixed - Display heading fixed at the top
	 * hidden - Hide the heading completely
	 */
	private String jqmHeader;

	/**
	 * Locale to use with the maps
	 */
	private String mapLocale;

	/**
	 * Flag if the login/logout button should be displayed
	 */
	private boolean loginButton;

	/**
	 * URL to link to the login button
	 */
	private String loginButtonURL;

	/**
	 * URL to link to the logout button
	 */
	private String logoutButtonURL;

	/**
	 * Flag if google analytics should be disabled
	 */
	private boolean disableGoogleAnalytics;

	/**
	 * Optional theme to apply to this page.
	 */
	private String theme;


	private static String appcacheEnabled;

	public void setAppcacheEnabled(String ap) {
		appcacheEnabled = ap;
	}

	/**
	 * @param id the id of the div containing the page content
	 */
	public void setId(String id) {
		this.id = id;
	}

	public void setJqmHeader(String jqmHeader) {
		this.jqmHeader = jqmHeader;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public void setPhonegap(String phonegap) {
		this.phonegap = phonegap;
	}

	/**
	 * @param title the title that will appear in the the header bar
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param mapLocale that will be used.
	 */
	public void setMapLocale(String mapLocale) {
		this.mapLocale = mapLocale;
	}

	public void setOnBodyLoad(String onBodyLoad) {
		this.onBodyLoad = onBodyLoad;
	}

	/**
	 * Enable the home button
	 *
	 * @param homeButton
	 */
	public void setHomeButton(boolean homeButton) {
		this.homeButton = homeButton;
	}

	/**
	 * enable the back button
	 *
	 * @param backButton
	 */
	public void setBackButton(boolean backButton) {
		this.backButton = backButton;
	}

	/**
	 * set the URL that the back button points to. If null, the back button uses
	 * the browser history.
	 *
	 * @param backButtonURL
	 */
	public void setBackButtonURL(String backButtonURL) {
		this.backButtonURL = backButtonURL;
	}

	/**
	 * enable a preferences button
	 *
	 * @param preferencesButton
	 */
	public void setPreferencesButton(boolean preferencesButton) {
		this.preferencesButton = preferencesButton;
	}

	/**
	 * the URL for preferences. If null, the context path + "/preferences" will
	 * be used.
	 *
	 * @param preferencesButtonURL
	 */
	public void setPreferencesButtonURL(String preferencesButtonURL) {
		this.preferencesButtonURL = preferencesButtonURL;
	}

	/**
	 * select whether to include the Google Maps API v3 javascript
	 *
	 * @param usesGoogleMaps
	 */
	public void setUsesGoogleMaps(boolean usesGoogleMaps) {
		this.usesGoogleMaps = usesGoogleMaps;
	}

	/**
	 * Enables the HTML5 app cache and specifies the manifest file name.
	 *
	 * @param appcacheFilename
	 */
	public void setAppcacheFilename(String appcacheFilename) {
		this.appcacheFilename = appcacheFilename;
	}

	/**
	 * the name of the css file without the .css extension
	 *
	 * @param cssFilename
	 */
	public void setCssFilename(String cssFilename) {
		this.cssFilename = cssFilename;
	}

	/**
	 * the name of the javascript file without the .js extension
	 *
	 * @param jsFilename
	 */
	public void setJsFilename(String jsFilename) {
		this.jsFilename = jsFilename;
	}

	/**
	 * enable a login/logout button
	 *
	 * @param loginButton
	 */
	public void setLoginButton(boolean loginButton) {
		this.loginButton = loginButton;
	}

	/**
	 * the URL to log in. If null, the context path + "/login" will be used.
	 *
	 * @param loginButtonURL
	 */
	public void setLoginButtonURL(String loginButtonURL) {
		this.loginButtonURL = loginButtonURL;
	}

	/**
	 * the URL to log out. If null, the context path + "/logout" will be used.
	 *
	 * @param logoutButtonURL
	 */
	public void setLogoutButtonURL(String logoutButtonURL) {
		this.logoutButtonURL = logoutButtonURL;
	}

	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();

		Cookie cks[] = hsr.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("jqmHeader")) {
					jqmHeader = c.getValue();
					//LOG.info("---jqmHeader: " + jqmHeader);
				}
			}
		}


		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		setAuthMapper((AuthenticationMapper) ac.getBean("authenticationMapper"));
		CoreService coreService = (CoreService) ac.getBean("coreService");
		Properties kmeProperties = (Properties) ac.getBean("kmeProperties");
		Locale locale = RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());
		MessageSource ms = (MessageSource) ac.getBean("messageSource");
		String msgCatString = null;

		User user = (User) pageContext.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		String contextPath = servletContext.getContextPath();
		JspWriter out = pageContext.getOut();
		try {
			out.println("<!DOCTYPE html>");

			if (getAppcacheEnabled().isEmpty()) {
				setAppcacheEnabled(kmeProperties.getProperty("appcache.enabled", "true"));
			}

			LOG.info("param.appcacheEnabled: " + getAppcacheEnabled());

			if (!appcacheEnabled.equals("false")) {
				LOG.debug("Appcache Enabled");
				out.println("<html manifest=\"" + contextPath + "/" + (StringUtils.isEmpty(getAppcacheFilename()) ? "kme.appcache" : getAppcacheFilename().trim()) + "\">");
			} else {
				LOG.debug("Appcache Disabled");
				out.println("<html>");
			}

			out.println("<head>");
			out.println("<title>" + getTitle() + "</title>");
			out.println("<link href=\"" + kmeProperties.getProperty("favico.url", "http://www.kuali.org/favicon.ico") + "\" rel=\"icon\" />");
			out.println("<link href=\"" + kmeProperties.getProperty("favico.url", "http://www.kuali.org/favicon.ico") + "\" rel=\"shortcut icon\" />");

			out.println("<link rel=\"apple-touch-icon\" href=\"" + contextPath + "/touch-icon-iphone.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"" + contextPath + "/touch-icon-ipad.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"" + contextPath + "/touch-icon-iphone-retina.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"" + contextPath + "/touch-icon-ipad-retina.png\"/>");

			out.println("<link href=\"" + contextPath + "/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />");

			addTheme();

			// Attach all CSS files
			for (String cssFile : getCssFilenames()) {
				out.println("<link href=\"" + contextPath + "/css/" + cssFile + ".css\" rel=\"stylesheet\" type=\"text/css\" />");
			}

			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.cookie.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/ServerDetails.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/custom.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.mobile.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.tmpl.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.ready.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.templates.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.transit.min.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/BrowserDetect.js\"></script>");


			if (this.isNative()) {
				out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/PushConfig.js\"></script>");
			}

//			out.println("<script src=\"http://jsconsole.com/remote.js?2EA94DB3-FD2F-4FF8-B41E-AB2B9A064544\"></script>");
			if (isPlatform(Device.TYPE_IOS)) {
				if (isPhoneGap("1.4.1")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/phonegap-1.4.1.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/ChildBrowser.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/barcodescanner.js\"></script>");
					//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/Connection.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/PushHandler.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/Badge.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/applicationPreferences.js\"></script>");
				} else if (isPhoneGap("1.7.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/cordova-1.7.0.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/ChildBrowser.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/barcodescanner.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/ActionSheet.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/Badge.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/LocalNotifications.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/Notifications.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/PrintPlugin.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/1.7.0/applicationPreferences.js\"></script>");
				} else if (isPhoneGap("2.2.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/cordova-2.2.0.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/ActionSheet.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/applicationPreferences.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/AudioStreamer.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/Badge.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/barcodescanner.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.2.0/ChildBrowser.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/PushHandler.js\"></script>");
				} else if (isPhoneGap("2.3.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.3.0/cordova-2.3.0.js\"></script>");
				} else if (isPhoneGap("2.4.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.4.0/cordova-2.4.0.js\"></script>");
				} else if (isPhoneGap("2.5.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.5.0/cordova-2.5.0.js\"></script>");
				} else if (isPhoneGap("2.6.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.6.0/cordova-2.6.0.js\"></script>");
				} else if (isPhoneGap("2.7.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.7.0/cordova-2.7.0.js\"></script>");
				} else if (isPhoneGap("2.8.1")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/2.8.1/cordova.js\"></script>");
				}
			} else if (isPlatform(Device.TYPE_ANDROID)) {
				if (isPhoneGap("2.2.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/cordova-2.2.0.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/childbrowser.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/barcodescanner.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/statusbarnotification.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/datePickerPlugin.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/applicationPreferences.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/AudioStreamer.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/GCMPlugin.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/2.2.0/CORDOVA_GCM_script.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/PushHandler.js\"></script>");

				} else {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/phonegap-" + getPhonegap() + ".js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/childbrowser.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/barcodescanner.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/statusbarnotification.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/C2DMPlugin.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/PG_C2DM_script.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/PushHandler.js\"></script>");
				}
			} else if (isPlatform(Device.TYPE_BLACKBERRY)) {
				if (isPhoneGap("2.2.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/blackberry/2.2.0/cordova-2.2.0.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/blackberry/2.2.0/kme-application.js\"></script>");
				}
			} else if (isPlatform(Device.TYPE_WINDOWS)) {
				if (isPhoneGap("2.2.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/windowsMobile/2.2.0/cordova-2.2.0.js\"></script>");
				}
			}

			/* Google Analytics */
			String profileId = coreService.findGoogleAnalyticsProfileId().trim();
			if (!disableGoogleAnalytics && profileId.length() > 0) {
				String profileDomain = coreService.getGoogleAnalyticsProfileDomain().trim();
				if (coreService.isGoogleUniversalAnalytics() && !profileDomain.isEmpty()) {
					out.println("<script type=\"text/javascript\">");
					out.println("(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){");
					out.println("(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),");
					out.println("m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)");
					out.println("})(window,document,'script','//www.google-analytics.com/analytics.js','ga');");

					out.println("ga('create', '" + profileId + "', '" + profileDomain + "'); ");
					out.println("ga('send', 'pageview');");
					out.println("</script>");
				} else {
					out.println("<script type=\"text/javascript\">");
					out.println("var _gaq = _gaq || [];");
					out.println("_gaq.push(['_setAccount', '" + profileId + "']);");
					out.println("_gaq.push(['_trackPageview']);");
					out.println("(function() {");
					out.println("var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;");
					out.println("ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';");
					out.println("var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);");
					out.println("})();");
					out.println("</script>");
				}
			}

			if (isUsesGoogleMaps()) {
				if (getMapLocale() != null) {
					out.println("<script type=\"text/javascript\" src=\"https://maps.google.com/maps/api/js?sensor=true&language=" + getMapLocale() + "\"></script>");
				} else {
					out.println("<script type=\"text/javascript\" src=\"https://maps.google.com/maps/api/js?sensor=true\"></script>");
				}
			}

			// Now add all the javascripts
			for (String javascript : getJsFilenames()) {
				out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/" + javascript + ".js\"></script>");
			}
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">");
//            out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
//            out.println("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");

			out.println("</head>");

			if (getOnBodyLoad() != null) {
				out.println("<body onload='" + getOnBodyLoad() + "'>");
			} else {
				out.println("<body>");
			}

			out.println("<div data-role=\"page\" id=\"" + getId() + "\">");

			LOG.info("----" + jqmHeader);
			if (getJqmHeader() != null && getJqmHeader().equals("hide")) {
				LOG.info("---- Hide Header");
				out.println("<div data-role=\"header\" style=\"display:none\">");
			} else if (getJqmHeader() != null && getJqmHeader().equals("fixed")) {
				LOG.info("---- Fixed Header");
				out.println("<div data-role=\"header\" data-position=\"fixed\">");
			} else {
				LOG.info("---- Show Header");
				out.println("<div data-role=\"header\">");
			}

			if (isLoginButton() || getAuthMapper().getLoginURL() != null) {
				if (user == null || user.isPublicUser()) {
					msgCatString = ms.getMessage("shared.login", null, "Login", locale);
					out.println("<a href=\"" + (getLoginButtonURL() != null ? getLoginButtonURL() : (getAuthMapper().getLoginURL() != null ? getAuthMapper().getLoginURL() : contextPath + "/login")) + "\" data-role=\"button\" data-icon=\"lock\">" + msgCatString + "</a>");
				} else {
					msgCatString = ms.getMessage("shared.logout", null, "Logout", locale);
					out.println("<a href=\"" + (getLogoutButtonURL() != null ? getLogoutButtonURL() : (getAuthMapper().getLogoutURL() != null ? getAuthMapper().getLogoutURL() : contextPath + "/logout")) + "\" data-role=\"button\" data-icon=\"unlock\">" + msgCatString + "</a>");
				}
			}
			if (isBackButton()) {
				msgCatString = ms.getMessage("shared.back", null, "Back", locale);
//				out.println("<a href=\"" + (getBackButtonURL() != null ? getBackButtonURL() : "javascript: history.go(-1)") + "\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\">" + msgCatString + "</a>");
				boolean showButton = true;
				if (null == this.getPhonegap()
						|| "".equalsIgnoreCase(getPhonegap())) {
					// showButton = true;
				} else {
					if (null != kmeProperties) {
						StringBuilder builder = new StringBuilder("shim.backbutton.");
						builder.append(this.getPlatform().toLowerCase());
						if (kmeProperties.containsKey(builder.toString())
								&& "false".equalsIgnoreCase(kmeProperties.getProperty(builder.toString()))) {
							showButton = false;
						}
					}
				}
				if (showButton) {
					out.println("<a href=\"" + ((getBackButtonURL() != null && StringUtils.isNotBlank(getBackButtonURL())) ? getBackButtonURL() : "javascript: history.go(-1)") + "\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\">" + msgCatString + "</a>");
				}
			}
			out.println("<h1>" + getTitle() + "</h1>");
			if (isPreferencesButton()) {
				if (null != kmeProperties
						&& "true".equalsIgnoreCase(kmeProperties.getProperty("home.preferences.enabled", "true"))) {
					msgCatString = ms.getMessage("shared.preferences", null, "Preferences", locale);
					out.println("<a href=\"" + (getPreferencesButtonURL() != null ? getPreferencesButtonURL() : contextPath + "/preferences") + "\" class=\"ui-btn-right\" data-icon=\"gear\" data-iconpos=\"notext\">" + msgCatString + "</a>");
				}
			}
			if (isHomeButton()) {
				msgCatString = ms.getMessage("shared.home", null, "Home", locale);
//				out.println("<a href=\"" + contextPath + "/home\" class=\"ui-btn-right\" data-icon=\"home\" data-iconpos=\"notext\">" + msgCatString + "</a>");
				boolean homeButton = true;
				if (null == this.getPhonegap()
						|| "".equalsIgnoreCase(getPhonegap())) {
					// showButton = true;
				} else {
					if (null != kmeProperties) {
						StringBuilder builder = new StringBuilder("shim.homebutton.");
						builder.append(this.getPlatform().toLowerCase());
						if (kmeProperties.containsKey(builder.toString())
								&& "false".equalsIgnoreCase(kmeProperties.getProperty(builder.toString()))) {
							homeButton = false;
						}
					}
				}
				if (homeButton) {
					out.println("<a href=\"" + contextPath + "/home\" class=\"ui-btn-right\" data-icon=\"home\" data-iconpos=\"notext\">" + msgCatString + "</a>");
				}
			}
			out.println("</div>");
			getJspBody().invoke(out);
			out.println("</div>");
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Returns true if the specified platform is the current platform
	 *
	 * @param platform The platform to test for.
	 * @return True if the platforms match, false if they don't
	 */
	boolean isPlatform(String platform) {
		if (platform == null) return false;
		String _platform = getPlatform();
		return !StringUtils.isEmpty(_platform) && platform.equalsIgnoreCase(_platform);
	}

	/**
	 * Returns true if the page is being created for a native platform
	 *
	 * @return True if the platform is native
	 */
	boolean isNative() {
		// If there is phonegap, it must be native
		if (!StringUtils.isEmpty(getPhonegap())) {
			return true;
		}

		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		Boolean isNative = (Boolean) hsr.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_NATIVE);
		return (isNative == null ? false : isNative.booleanValue());
	}

	/**
	 * Returns true if the given PhoneGap version, is the current PhoneGap version of the device.
	 *
	 * @param phonegap The PhoneGap version to test.
	 * @return True if the PhoneGap versions match, false if the don't
	 */
	boolean isPhoneGap(String phonegap) {
		if (StringUtils.isEmpty(phonegap)) return false;
		return !StringUtils.isEmpty(getPhonegap()) && phonegap.equalsIgnoreCase(getPhonegap());
	}

	/**
	 * Adds the theme to the page if there is one available
	 *
	 * @throws IOException
	 */
	void addTheme() throws IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		Properties kmeProperties = (Properties) ac.getBean("kmeProperties");
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);


		boolean useCampusTheme = kmeProperties != null && "true".equals(kmeProperties.getProperty("theme.perCampus", "false"));
		String viewingCampus = user == null ? null : user.getViewCampus();
		String defaultTheme = kmeProperties == null ? null : kmeProperties.getProperty("theme.default");
		String themeToAdd = null;

		// 1 - if this tag requests a theme, we attempt to use it
		if (!StringUtils.isEmpty(this.theme) && ac.getResource("/css/theme-" + this.theme + ".css").exists()) {
			themeToAdd = this.theme;
		}
		// 2 - Try and use campus theme if configured to use it
		if (themeToAdd == null && useCampusTheme && !StringUtils.isEmpty(viewingCampus) && ac.getResource("/css/theme-" + viewingCampus + ".css").exists()) {
			themeToAdd = viewingCampus;
		}
		// 3 - Use default theme
		if (themeToAdd == null && !StringUtils.isEmpty(defaultTheme) && ac.getResource("/css/theme-" + defaultTheme + ".css").exists()) {
			themeToAdd = defaultTheme;
		}

		if (themeToAdd != null) {
			pageContext.getOut().println("<link href=\"" + request.getContextPath() + "/css/theme-" + themeToAdd + ".css\" rel=\"stylesheet\" type=\"text/css\" />");
		}
	}

	/**
	 * @return the disableGoogleAnalytics
	 */
	public boolean isDisableGoogleAnalytics() {
		return disableGoogleAnalytics;
	}

	/**
	 * @param disableGoogleAnalytics the disableGoogleAnalytics to set
	 */
	public void setDisableGoogleAnalytics(boolean disableGoogleAnalytics) {
		this.disableGoogleAnalytics = disableGoogleAnalytics;
	}

	public AuthenticationMapper getAuthMapper() {
		return authMapper;
	}

	public void setAuthMapper(AuthenticationMapper authMapper) {
		this.authMapper = authMapper;
	}

	/**
	 * Sets the theme to apply to this page
	 *
	 * @param theme Theme to set
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * @return the backButton
	 */
	public boolean isBackButton() {
		return backButton;
	}

	/**
	 * @return the backButtonURL
	 */
	public String getBackButtonURL() {
		return backButtonURL;
	}

	/**
	 * @return the preferencesButton
	 */
	public boolean isPreferencesButton() {
		return preferencesButton;
	}

	/**
	 * @return the preferencesButtonURL
	 */
	public String getPreferencesButtonURL() {
		return preferencesButtonURL;
	}

	/**
	 * @return the usesGoogleMaps
	 */
	public boolean isUsesGoogleMaps() {
		return usesGoogleMaps;
	}

	/**
	 * @return the appcacheFilename
	 */
	public String getAppcacheFilename() {
		return appcacheFilename;
	}

	/**
	 * Gets abn array of CSS Filesnames that should be attached to the page
	 *
	 * @return
	 */
	public String[] getCssFilenames() {
		String[] cssFiles = null;
		if (getCssFilename() != null && !cssFilename.trim().equals("")) {
			if (getCssFilename().indexOf(',') > 0) {// There was more than one css specified
				cssFiles = getCssFilename().split(",");
			} else {// There was no comma
				cssFiles = new String[]{getCssFilename()};
			}
		} else {
			cssFiles = new String[]{}; // Never return null
		}
		return cssFiles;
	}

	/**
	 * @return the onBodyLoad
	 */
	public String getOnBodyLoad() {
		return onBodyLoad;
	}

	/**
	 * Returns the current platform.
	 * If the platform was set on the tag, that platform will be used, else
	 * the platform detected by the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used
	 *
	 * @return the platform
	 */
	public String getPlatform() {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		String plat = (String) hsr.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_PLATFORM);
		return StringUtils.isEmpty(this.platform) ? plat : this.platform;
	}

	/**
	 * Returns the current phonegap version.
	 * If the phonegap version was set on the tag, that version will be used, else
	 * the version detected by the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used.
	 *
	 * @return The phonegap version
	 */
	public String getPhonegap() {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		String cor = (String) hsr.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_PHONEGAP);
		return StringUtils.isEmpty(this.phonegap) ? cor : this.phonegap;
	}

	/**
	 * @return the jsFilename
	 */
	public String[] getJsFilenames() {
		String[] javascripts = null;
		if (getJsFilename() != null && !jsFilename.trim().equals("")) {

			if (getJsFilename().indexOf(',') > 0) {// There was more than one java script specified
				javascripts = getJsFilename().split(",");
			} else {// There was no comma
				javascripts = new String[]{getJsFilename()};
			}


		} else {
			javascripts = new String[]{};// Never return null
		}
		return javascripts;
	}

	/**
	 * @return the jqmHeader
	 */
	public String getJqmHeader() {
		return jqmHeader;
	}

	/**
	 * @return the mapLocale
	 */
	public String getMapLocale() {
		return mapLocale;
	}

	/**
	 * @return the loginButton
	 */
	public boolean isLoginButton() {
		return loginButton;
	}

	/**
	 * @return the loginButtonURL
	 */
	public String getLoginButtonURL() {
		return loginButtonURL;
	}

	/**
	 * @return the logoutButtonURL
	 */
	public String getLogoutButtonURL() {
		return logoutButtonURL;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the homeButton
	 */
	public boolean isHomeButton() {
		return homeButton;
	}

	/**
	 * @return the cssFilename
	 */
	public String getCssFilename() {
		return cssFilename;
	}

	/**
	 * @return the jsFilename
	 */
	public String getJsFilename() {
		return jsFilename;
	}
}
