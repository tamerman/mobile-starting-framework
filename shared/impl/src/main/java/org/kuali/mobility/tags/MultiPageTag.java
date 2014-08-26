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
import org.kuali.mobility.shared.CoreService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.util.Properties;

/**
 * The backing class for the MultiPage JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class MultiPageTag extends PageTag {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(MultiPageTag.class);

	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();

		HttpServletRequest hsr = (HttpServletRequest) pageContext.getRequest();
		Cookie cks[] = hsr.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("jqmHeader")) {
					setJqmHeader(c.getValue());
					//LOG.info("---jqmHeader: " + jqmHeader);
				}
			}
		}

		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		setAuthMapper((AuthenticationMapper) ac.getBean("authenticationMapper"));
		CoreService coreService = (CoreService) ac.getBean("coreService");
		Properties kmeProperties = (Properties) ac.getBean("kmeProperties");
		User user = (User) pageContext.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
		String contextPath = servletContext.getContextPath();
		JspWriter out = pageContext.getOut();
		try {
			out.println("<!DOCTYPE html>");

			if (!getAppcacheEnabled().equals("false")) {
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
			//           out.println("<link rel=\"apple-touch-icon\" href=\"" + contextPath + "/apple-touch-icon-precomposed.png\"/>");

			out.println("<link rel=\"apple-touch-icon\" href=\"" + contextPath + "/touch-icon-iphone.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"72x72\" href=\"" + contextPath + "/touch-icon-ipad.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"114x114\" href=\"" + contextPath + "/touch-icon-iphone-retina.png\"/>");
			out.println("<link rel=\"apple-touch-icon\" sizes=\"144x144\" href=\"" + contextPath + "/touch-icon-ipad-retina.png\"/>");


			out.println("<link href=\"" + contextPath + "/css/jquery.mobile.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/kme.css\" rel=\"stylesheet\" type=\"text/css\" />");
			out.println("<link href=\"" + contextPath + "/css/institution.css\" rel=\"stylesheet\" type=\"text/css\" />");
			// Attach all CSS files
			for (String cssFile : getCssFilenames()) {
				out.println("<link href=\"" + contextPath + "/css/" + cssFile + ".css\" rel=\"stylesheet\" type=\"text/css\" />");
			}

			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.cookie.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/custom.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.mobile.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.tmpl.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.ready.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.templates.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.transit.min.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/BrowserDetect.js\"></script>");
			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/ServerDetails.js\"></script>");

			if (isNative()) {
				out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/PushConfig.js\"></script>");
			}


			out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.autoellipsis-1.0.3.min.js\"></script>");

//            if (getPlatform() != null && getPlatform().equals("iOS")) {
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/phonegap-" + getPhonegap() + ".js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/ChildBrowser.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/barcodescanner.js\"></script>");
////                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/Connection.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/PushHandler.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/Badge.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/iOS/applicationPreferences.js\"></script>");
//            } else if (getPlatform() != null && getPlatform().equals("Android")) {
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/phonegap-" + getPhonegap() + ".js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/childbrowser.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/barcodescanner.js\"></script>");
//                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/statusbarnotification.js\"></script>");
//                //out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/C2DMPlugin.js\"></script>");
//                //out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/android/PG_C2DM_script.js\"></script>");
//            }

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
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/PushConfig.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/blackberry/2.2.0/cordova-2.2.0.js\"></script>");
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/blackberry/2.2.0/kme-application.js\"></script>");
				}
			} else if (isPlatform(Device.TYPE_WINDOWS)) {
				if (isPhoneGap("2.2.0")) {
					out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/windowsMobile/2.2.0/cordova-2.2.0.js\"></script>");
				}
			}


			String profileId = coreService.findGoogleAnalyticsProfileId().trim();
			if (!isDisableGoogleAnalytics() && profileId.length() > 0) {
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

			if (isUsesGoogleMaps()) {
				if (getMapLocale() != null) {
					out.println("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=true&language=" + getMapLocale() + "\"></script>");
				} else {
					out.println("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=true\"></script>");
				}
			}

			// Now add all the javascripts
			for (String javascript : getJsFilenames()) {
				out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/" + javascript + ".js\"></script>");
			}

//            out.println("<meta name=\"apple-mobile-web-app-capable\" content=\"yes\" />");
//            out.println("<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"black\" />");
			out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0\">");
			out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
			out.println("</head>");

			if (getOnBodyLoad() != null) {
				out.println("<body onload='" + getOnBodyLoad() + "'>");
			} else {
				out.println("<body>");
			}

			getJspBody().invoke(out);

			out.println("</body>");
			out.println("</html>");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

}
