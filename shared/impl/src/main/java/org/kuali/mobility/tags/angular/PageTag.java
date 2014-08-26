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

package org.kuali.mobility.tags.angular;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.CoreService;
import org.kuali.mobility.shared.interceptors.NativeCookieInterceptor;
import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.util.Locale;
import java.util.Properties;

/**
 * The backing class for the Page JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class PageTag extends SimpleTagSupport {

	private static final Logger LOG = LoggerFactory.getLogger(PageTag.class);

	private String title;
	private String toolName;
	private String backFunction;
	private String cssFilename;
	private String institutionCss;
	private String jsFilename;
	private String ngAppName;
	private String ngControllerName;
	private String ngInitFunction;
	private String mapLocale;
	private String mapCallbackFunction;

	private boolean hideBackButton;
	private boolean hideMenuButton;
	private boolean disableGoogleAnalytics;
	private boolean usesGoogleMaps;

	private Properties kmeProperties;

	public void doTag() throws JspException {
		String appcacheEnabled;

		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		setKmeProperties((Properties) ac.getBean("kmeProperties"));

		CoreService coreService = (CoreService) ac.getBean("coreService");
		Properties kmeProperties = (Properties) ac.getBean("kmeProperties");
		Locale locale = RequestContextUtils.getLocale((HttpServletRequest) pageContext.getRequest());
		MessageSource ms = (MessageSource) ac.getBean("messageSource");

		User user = (User) pageContext.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		String contextPath = servletContext.getContextPath();

		appcacheEnabled = kmeProperties.getProperty("appcache.enabled", "true");


		JspWriter out = pageContext.getOut();
		StringBuilder builder = new StringBuilder();
		try {
			builder.append("<!DOCTYPE html>");
			builder.append("<html");
			if (!appcacheEnabled.equals("false")) {
				LOG.debug("Appcache Enabled");
				builder.append(" manifest=\"CONTEXT_PATH/kme.appcache\"");
			}
			if (null != getNgAppName()) {
				builder.append(" ng-app=\"");
				builder.append(getNgAppName());
				builder.append("\"");
			}
			builder.append(">");

			builder.append("<head>");
			builder.append("<title>");
			builder.append(getTitle());
			builder.append("</title>");
			builder.append("<link href=\"");
			builder.append(kmeProperties.getProperty("favico.url", "http://www.kuali.org/favicon.ico"));
			builder.append("\" rel=\"icon\" />");
			builder.append("<link href=\"");
			builder.append(kmeProperties.getProperty("favico.url", "http://www.kuali.org/favicon.ico"));
			builder.append("\" rel=\"shortcut icon\" />");

			builder.append("<link rel=\"apple-touch-icon\" href=\"CONTEXT_PATH/touch-icon-iphone.png\"/>");
			builder.append("<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"CONTEXT_PATH/touch-icon-ipad.png\"/>");
			builder.append("<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"CONTEXT_PATH/touch-icon-iphone-retina.png\"/>");
			builder.append("<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"CONTEXT_PATH/touch-icon-ipad-retina.png\"/>");

			builder.append("<script src=\"//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>\n");
			builder.append("<script src=\"//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular.min.js\"></script>\n");
			builder.append("<script src=\"//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-route.js\"></script>\n");
			builder.append("<script src=\"//ajax.googleapis.com/ajax/libs/angularjs/1.2.6/angular-sanitize.js\"></script>\n");

			builder.append("<script src=\"CONTEXT_PATH/js/jquery.cookie.js\"></script>");
			builder.append("<script src=\"CONTEXT_PATH/js/ui3/ui-bootstrap-tpls-0.10.0.min.js\"></script>\n");
			builder.append("<script src=\"CONTEXT_PATH/js/bootstrap.js\"></script>\n");
			builder.append("<script src=\"CONTEXT_PATH/js/bootbox.js\"></script>\n");

			builder.append("<!-- Latest compiled and minified CSS -->\n");
			builder.append("<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\"/>\n");
			builder.append("<link href=\"CONTEXT_PATH/css/ui3/kme.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
			builder.append("<link href=\"CONTEXT_PATH/css/ui3/institution.css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
			// Attach all CSS files
			for (String cssFile : getCssFilenames()) {
				builder.append("<link href=\"CONTEXT_PATH/css/ui3/");
				builder.append(cssFile);
				builder.append(".css\" rel=\"stylesheet\" type=\"text/css\"/>\n");
			}
			builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/BrowserDetect.js\"></script>\n");
			builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/ServerDetails.js\"></script>\n");

			if (isPlatform(Device.TYPE_IOS)) {
				if (isPhoneGap("1.4.1")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/phonegap-1.4.1.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/ChildBrowser.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/barcodescanner.js\"></script>\n");
					//                builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/Connection.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/PushHandler.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/Badge.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/applicationPreferences.js\"></script>\n");
				} else if (isPhoneGap("1.7.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/cordova-1.7.0.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/ChildBrowser.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/barcodescanner.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/ActionSheet.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/Badge.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/LocalNotifications.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/Notifications.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/PrintPlugin.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/1.7.0/applicationPreferences.js\"></script>\n");
				} else if (isPhoneGap("2.2.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/cordova-2.2.0.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/ActionSheet.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/applicationPreferences.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/AudioStreamer.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/Badge.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/barcodescanner.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.2.0/ChildBrowser.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/PushHandler.js\"></script>\n");
				} else if (isPhoneGap("2.3.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.3.0/cordova-2.3.0.js\"></script>\n");
				} else if (isPhoneGap("2.4.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.4.0/cordova-2.4.0.js\"></script>\n");
				} else if (isPhoneGap("2.5.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.5.0/cordova-2.5.0.js\"></script>\n");
				} else if (isPhoneGap("2.6.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.6.0/cordova-2.6.0.js\"></script>\n");
				} else if (isPhoneGap("2.7.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.7.0/cordova-2.7.0.js\"></script>\n");
				} else if (isPhoneGap("2.8.1")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/iOS/2.8.1/cordova.js\"></script>\n");
				}
			} else if (isPlatform(Device.TYPE_ANDROID)) {
				builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/PushConfig.js\"></script>\n");
				if (isPhoneGap("2.2.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/cordova-2.2.0.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/childbrowser.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/barcodescanner.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/statusbarnotification.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/datePickerPlugin.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/applicationPreferences.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/AudioStreamer.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/GCMPlugin.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/2.2.0/CORDOVA_GCM_script.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/PushHandler.js\"></script>\n");
				} else {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/phonegap-" + getPhonegap() + ".js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/childbrowser.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/barcodescanner.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/statusbarnotification.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/C2DMPlugin.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/PG_C2DM_script.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/android/PushHandler.js\"></script>\n");
				}
			} else if (isPlatform(Device.TYPE_BLACKBERRY)) {
				builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/PushConfig.js\"></script>\n");
				if (isPhoneGap("2.2.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/blackberry/2.2.0/cordova-2.2.0.js\"></script>\n");
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/blackberry/2.2.0/kme-application.js\"></script>\n");
				}
			} else if (isPlatform(Device.TYPE_WINDOWS)) {
				if (isPhoneGap("2.2.0")) {
					builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/js/windowsMobile/2.2.0/cordova-2.2.0.js\"></script>\n");
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
				builder.append("<script type=\"text/javascript\" src=\"https://maps.google.com/maps/api/js?libraries=geometry&v=3.exp&sensor=true");
				if (null != getMapLocale() && !getMapLocale().isEmpty()) {
					builder.append("&language=");
					builder.append(getMapLocale());
				}
				if (null != getMapCallbackFunction() && !getMapCallbackFunction().isEmpty()) {
					builder.append("&callback=");
					builder.append(getMapCallbackFunction());
				}
				builder.append("\"></script>\n");
//                builder.append("<script type=\"text/javascript\" src=\"//maps.google.com/maps/api/js?libraries=geometry&v=3&sensor=true\"></script>\n");
				builder.append("<script type=\"text/javascript\" src=\"//google-maps-utility-library-v3.googlecode.com/svn/trunk/styledmarker/src/StyledMarker.js\"></script>\n");
				builder.append("<script type=\"text/javascript\" src=\"//google-maps-utility-library-v3.googlecode.com/svn/trunk/markerwithlabel/src/markerwithlabel.js\"></script>\n");
			}

			if ("mapquest".equalsIgnoreCase(getKmeProperties().getProperty("maps.api"))) {
				builder.append("<script type=\"text/javascript\" src=\"http://www.mapquestapi.com/sdk/js/v7.0.s/mqa.toolkit.js?key=Fmjtd%7Cluubnu6r2q%2C8n%3Do5-9uyllr\"></script>\n");
			}

			for (String javascript : getJsFilenames()) {
				builder.append("<script type=\"text/javascript\" src=\"CONTEXT_PATH/");
				builder.append(getToolName());
				builder.append("/js/");
				builder.append(javascript);
				builder.append(".js\"></script>\n");
			}

			builder.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">\n");
			builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n");

			builder.append("</head>");
			builder.append("<body");
			if (null != getToolName()) {
				builder.append(" id=\"");
				builder.append(getToolName());
				builder.append("\"");
			}
			if (null != getNgControllerName()) {
				builder.append(" ng-controller=\"");
				builder.append(getNgControllerName());
				builder.append("\"");
			}
			if (null != getNgInitFunction()) {
				builder.append(" data-ng-init=\"");
				builder.append(getNgInitFunction());
				builder.append("()\"");
			}
			builder.append(">\n");

			builder.append("    <nav class=\"kme-top-nav navbar navbar-default navbar-fixed-top\" role=\"navigation\">\n");
			//back
			if (!isHideBackButton()) {
				boolean showButton = true;
				if (("true".equalsIgnoreCase(getKmeProperties().getProperty("shim.backbutton.ios")) && isPlatform(Device.TYPE_IOS)) ||
						("true".equalsIgnoreCase(getKmeProperties().getProperty("shim.backbutton.android")) && isPlatform(Device.TYPE_ANDROID))) {
					showButton = false;
				}
				if (showButton) {
					if ("left".equalsIgnoreCase(getKmeProperties().getProperty("nav.back.position"))) {
						builder.append("        <div class=\"nav navbar-nav  pull-left kme-nav-back\">\n");
					} else if ("right".equalsIgnoreCase(getKmeProperties().getProperty("nav.back.position"))) {
						builder.append("        <div class=\"nav navbar-nav  pull-right kme-nav-back\">\n");
					} else {
						builder.append("        <div class=\"nav navbar-nav  pull-left kme-nav-back\">\n");
					}

					builder.append("            <a ng-click=\"");
					if (null == getBackFunction() || getBackFunction().isEmpty()) {
						builder.append("kmeNavLeft");
					} else {
						builder.append(getBackFunction());
					}
					builder.append("()\" class=\"ur-small-element\"><span class=\"glyphicon glyphicon-chevron-left white\"></span></a>\n");
					builder.append("        </div>\n");
				}
			}
			//end back

			//menu
			if (!isHideMenuButton()) {
				boolean showButton = true;
				if (("true".equalsIgnoreCase(getKmeProperties().getProperty("shim.homebutton.ios")) && isPlatform(Device.TYPE_IOS)) ||
						("true".equalsIgnoreCase(getKmeProperties().getProperty("shim.homebutton.android")) && isPlatform(Device.TYPE_ANDROID))) {
					showButton = false;
				}
				if (showButton) {
					if ("left".equalsIgnoreCase(getKmeProperties().getProperty("nav.menu.position"))) {
						builder.append("        <div class=\"nav navbar-nav pull-left dropdown kme-nav-menu\">\n");
					} else if ("right".equalsIgnoreCase(getKmeProperties().getProperty("nav.menu.position"))) {
						builder.append("        <div class=\"nav navbar-nav pull-right dropdown kme-nav-menu\">\n");
					} else {
						builder.append("        <div class=\"nav navbar-nav pull-right dropdown kme-nav-menu\">\n");
					}
					builder.append("            <a class=\"dropdown-toggle ur-small-element\" href=\"#\" ><span class=\"glyphicon glyphicon-align-justify white\"></span></a>\n");
					builder.append("            <ul class=\"dropdown-menu\">\n");
					//home
					builder.append("                <li><a href=\"CONTEXT_PATH/home\">Home</a></li>\n");
					//end home
					builder.append("                <li ng-repeat=\"menu in menuItems.menus\" ng-class=\"{divider:menu.divider}\"><a href=\"CONTEXT_PATH{{menu.url}}\" _target=\"self\">{{menu.label}}</a></li>\n");
					if ("true".equalsIgnoreCase(kmeProperties.getProperty("home.preferences.enabled", "true"))) {
						builder.append("                <li class=\"divider\"></li>\n");
						builder.append("                <li><a href=\"CONTEXT_PATH/preferences\">Preferences</a></li>\n");
					}
					builder.append("            </ul>\n");
					builder.append("        </div>\n");
				}
			}
			//end menu

			builder.append("        <div class=\"navbar-header navbar-text \">");
			builder.append(getTitle());
			builder.append("</div>\n");

			builder.append("    </nav>\n");

			String output = builder.toString();
			output = output.replaceAll("CONTEXT_PATH", contextPath);
			out.println(output);
			getJspBody().invoke(out);
			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
	}


	/**
	 * Returns the current platform.
	 * If the platform was set on the tag, that platform will be used, else
	 * the platform detected by the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used
	 *
	 * @return the platform
	 */
	private String getPlatform() {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		return (String) hsr.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_PLATFORM);
	}

	/**
	 * Returns the current phonegap version.
	 * If the phonegap version was set on the tag, that version will be used, else
	 * the version detected by the {@link org.kuali.mobility.shared.interceptors.NativeCookieInterceptor}
	 * will be used.
	 *
	 * @return The phonegap version
	 */
	private String getPhonegap() {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		return (String) hsr.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_PHONEGAP);
	}

	/**
	 * Returns true if the specified platform is the current platform
	 *
	 * @param platform The platform to test for.
	 * @return True if the platforms match, false if they don't
	 */
	private boolean isPlatform(String platform) {
		if (StringUtils.isEmpty(platform)) return false;
		return !StringUtils.isEmpty(getPlatform()) && platform.equalsIgnoreCase(getPlatform());
	}

	/**
	 * Returns true if the given PhoneGap version, is the current PhoneGap version of the device.
	 *
	 * @param phonegap The PhoneGap version to test.
	 * @return True if the PhoneGap versions match, false if the don't
	 */
	private boolean isPhoneGap(String phonegap) {
		if (StringUtils.isEmpty(phonegap)) return false;
		return !StringUtils.isEmpty(getPhonegap()) && phonegap.equalsIgnoreCase(getPhonegap());
	}

	public String getBackFunction() {
		return backFunction;
	}

	public void setBackFunction(String backFunction) {
		this.backFunction = backFunction;
	}

	public String getCssFilename() {
		return cssFilename;
	}

	public void setCssFilename(String cssFilename) {
		this.cssFilename = cssFilename;
	}

	/**
	 * Gets an array of CSS Filesnames that should be attached to the page
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

	public String getInstitutionCss() {
		return institutionCss;
	}

	public void setInstitutionCss(String institutionCss) {
		this.institutionCss = institutionCss;
	}

	public boolean isDisableGoogleAnalytics() {
		return disableGoogleAnalytics;
	}

	public void setDisableGoogleAnalytics(boolean disabledGoogleAnalytics) {
		this.disableGoogleAnalytics = disabledGoogleAnalytics;
	}

	public boolean isUsesGoogleMaps() {
		return usesGoogleMaps;
	}

	public void setUsesGoogleMaps(boolean usesGoogleMaps) {
		this.usesGoogleMaps = usesGoogleMaps;
	}

	public String getJsFilename() {
		return jsFilename;
	}

	public void setJsFilename(String jsFilename) {
		this.jsFilename = jsFilename;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNgAppName() {
		return ngAppName;
	}

	public void setNgAppName(String ngAppName) {
		this.ngAppName = ngAppName;
	}

	public String getMapLocale() {
		return mapLocale;
	}

	public void setMapLocale(String mapLocale) {
		this.mapLocale = mapLocale;
	}

	public String getNgControllerName() {
		return ngControllerName;
	}

	public void setNgControllerName(String ngControllerName) {
		this.ngControllerName = ngControllerName;
	}

	public String getNgInitFunction() {
		return ngInitFunction;
	}

	public void setNgInitFunction(String ngInitFunction) {
		this.ngInitFunction = ngInitFunction;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public String getMapCallbackFunction() {
		return mapCallbackFunction;
	}

	public void setMapCallbackFunction(String mapCallbackFunction) {
		this.mapCallbackFunction = mapCallbackFunction;
	}


	public boolean isHideBackButton() {
		return hideBackButton;
	}


	public void setHideBackButton(boolean hideBackButton) {
		this.hideBackButton = hideBackButton;
	}


	public boolean isHideMenuButton() {
		return hideMenuButton;
	}


	public void setHideMenuButton(boolean hideMenuButton) {
		this.hideMenuButton = hideMenuButton;
	}
}
