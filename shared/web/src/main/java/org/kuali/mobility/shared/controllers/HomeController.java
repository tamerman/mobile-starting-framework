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

package org.kuali.mobility.shared.controllers;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.admin.util.LayoutUtil;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.campus.entity.Campus;
import org.kuali.mobility.campus.service.CampusService;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.push.entity.Sender;
import org.kuali.mobility.push.service.SenderService;
import org.kuali.mobility.security.authz.entity.AclExpression;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.CoreService;
import org.kuali.mobility.shared.entity.Backdoor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Controller for Home screen and other pages
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 1.0.0
 */
@Controller
@RequestMapping("/")
public class HomeController implements ApplicationContextAware {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(HomeController.class);

	@Resource(name = "&entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Autowired
	private AdminService adminService;

	@Autowired
	private AlertsService alertsService;

	@Autowired
	private ConfigParamService configParamService;

	@Autowired
	private CampusService campusService;

	@Autowired
	private CoreService coreService;

	@Autowired
	private SenderService senderService;

	/**
	 * A reference to the <code>ApplicationContext</code>.
	 */
	private ApplicationContext applicationContext;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@Resource(name = "supportedLanguages")
	private List<String> supportedLanguages;

	/**
	 * Controller method for the home page
	 */
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home(
			@CookieValue(value = "homeLayout", required = false) String homeLayout,
			HttpServletRequest request,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String ipAddress = "";
		if (user.isMember(getConfigParamService().findValueByName("Admin.Group.Name"))) {
			try {
				ipAddress = "<p><i><small>Server: " + InetAddress.getLocalHost().getHostName() + "</small></i></p>";
			} catch (UnknownHostException e) {
			}
		}
		uiModel.addAttribute("ipAddress", ipAddress);

		if (kmeProperties != null) {
			// Setting default campus to view if the institution defined one
			// and one doesn't already exist.
			if (null != user && user.getViewCampus() == null) {
				LOG.debug("campus.default : " + kmeProperties.getProperty("campus.default", null));
				user.setViewCampus(kmeProperties.getProperty("campus.default", null));
			}
			uiModel.addAttribute("defaultCampus", kmeProperties.getProperty("campus.default", null));
			uiModel.addAttribute("showAbout", Boolean.parseBoolean(kmeProperties.getProperty("home.about.enabled", "false")));
			uiModel.addAttribute("showCacheUpdate", Boolean.parseBoolean(kmeProperties.getProperty("appcache.display.update", "false")));

		} else {
			uiModel.addAttribute("showAbout", false);
			uiModel.addAttribute("showCacheUpdate", false);
		}
		String currentLayout = LayoutUtil.getValidLayout(homeLayout, kmeProperties);
		uiModel.addAttribute("currentLayout", currentLayout);
		buildHomeScreen(request, uiModel);

		String viewName = "index";

		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			viewName = "ui3/home/index";
		}
		return viewName;

	}

	@RequestMapping(value = "/home/js/{key}.js")
	public String getJavaScript(@PathVariable("key") String key,
								Model uiModel, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		uiModel.addAttribute("campus", user.getViewCampus());
		return "ui3/home/js/" + key;
	}

	/**
	 * Controller method for the preference screen
	 */
	@RequestMapping(value = "preferences", method = RequestMethod.GET)
	public String preferences(
			@CookieValue(value = "homeLayout", required = false) String homeLayoutCookie,
			@RequestParam(value = "homeLayout", required = false) String homeLayoutParam,
			HttpServletRequest request,
			HttpServletResponse response,
			Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String homeToolName = "home";
		List<Campus> campuses = getCampusService().findCampusesByTool(homeToolName);
		List<HomeScreen> homeScreens = getAdminService().getAllHomeScreens();

		String currentLayout = homeLayoutCookie;

		boolean useSecureCookie = Boolean.parseBoolean(this.getKmeProperties().getProperty("kme.secure.cookie", "false"));
		// Change layout if requested
		if (!StringUtils.isEmpty(homeLayoutParam)) {
			currentLayout = LayoutUtil.getValidLayout(homeLayoutParam, kmeProperties);
			Cookie layoutCookie = new Cookie("homeLayout", currentLayout);
			int cookieMaxAge = Integer.parseInt(getKmeProperties().getProperty("cookie.max.age", "3600"));
			layoutCookie.setMaxAge(cookieMaxAge); // default one hour, should implement in kme.config.properties.
			layoutCookie.setPath(request.getContextPath());
			layoutCookie.setSecure(useSecureCookie);
			response.addCookie(layoutCookie);
		}


		// Determine current home layout
		boolean allowLayoutChange = false;
		if (kmeProperties != null) {
			allowLayoutChange = Boolean.parseBoolean(kmeProperties.getProperty("home.layout.userEditable", "false"));
			if (allowLayoutChange) {
				currentLayout = LayoutUtil.getValidLayout(currentLayout, kmeProperties);
				uiModel.addAttribute("currentLayout", currentLayout);
				uiModel.addAttribute("availableLayouts", HomeScreen.LAYOUTS);
			}
		}

		List<Sender> senders = senderService.findAllUnhiddenSenders();

		// Add attributes to model
		uiModel.addAttribute("senders", senders);
		uiModel.addAttribute("toolName", homeToolName);
		uiModel.addAttribute("campuses", campuses);
		uiModel.addAttribute("homeScreens", homeScreens);
		uiModel.addAttribute("user", user);
		uiModel.addAttribute("supportedLanguages", getSupportedLanguages());
		uiModel.addAttribute("allowLayoutChange", allowLayoutChange);
		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			return "ui3/home/preferences";
		}
		return "preferences";
	}

	/**
	 * Controller method to retrieve the appcache manifest
	 */
	@RequestMapping(value = "kme.appcache", method = RequestMethod.GET)
	public String cachemanifest(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		//response.setContentType("text/cache-manifest");
		if (kmeProperties != null) {
			uiModel.addAttribute("appcacheTimestamp", kmeProperties.getProperty("appcache.timestamp", ""));
			uiModel.addAttribute("appcacheEnabled", kmeProperties.getProperty("appcache.enabled", "not found"));
		} else {
			LOG.error("Unable to find kmeProperties bean");
		}
		return "cacheManifest";
	}

	/**
	 * Controller method to log out a user
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model uiModel) {
		return "logout";
	}


	/**
	 * Controller method to allow the user to confirm to log out
	 */
	@RequestMapping(value = "logout-confirm", method = RequestMethod.GET)
	public String logoutYes(HttpServletRequest request, Model uiModel) {
		request.getSession().setAttribute(Constants.KME_MOCK_USER_KEY, null);
		request.getSession().setAttribute(Constants.KME_USER_KEY, null);
		return "redirect:/home";
	}

	/**
	 * Controller method for the about screen
	 */
	@RequestMapping(value = "about", method = RequestMethod.GET)
	public String about(HttpServletRequest request, Model uiModel) {
		String viewName = "about";
		uiModel.addAttribute("kmeVersion", kmeProperties.getProperty("kme.version"));
		uiModel.addAttribute("institutionVersion", kmeProperties.getProperty("institution.version", null));
		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			viewName = "ui3/home/about";
		}
		return viewName;
	}

	/**
	 * Controller method to download a ddl.
	 */
	@Deprecated
	@SuppressWarnings({"unchecked", "rawtypes"})
	@RequestMapping(value = "ddl", method = RequestMethod.GET)
	public void exportDatabaseSchema(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		PersistenceUnitInfo persistenceUnitInfo = getEntityManagerFactory().getPersistenceUnitInfo();

		Map jpaPropertyMap = getEntityManagerFactory().getJpaPropertyMap();
		jpaPropertyMap.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		Configuration configuration = new Ejb3Configuration().configure(persistenceUnitInfo, jpaPropertyMap).getHibernateConfiguration();

		SchemaExport schema = new SchemaExport(configuration);
		schema.setFormat(true);
		schema.setDelimiter(";");
		schema.setOutputFile("/tmp/schema.sql");
		schema.create(false, false);
	}

	/**
	 * Builds the content that should be displayd on the home screen.
	 */
	private void buildHomeScreen(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		Backdoor backdoor = (Backdoor) request.getSession().getAttribute(Constants.KME_BACKDOOR_USER_KEY);

		int myState = 0;
		Cookie cks[] = request.getCookies();
		if (cks != null) {
			for (Cookie c : cks) {
				if (c.getName().equals("native")) {
					if ("yes".equals(c.getValue())) {
						myState |= Tool.NATIVE;
					} else {
						myState |= Tool.NON_NATIVE;
					}
				}
				if (c.getName().equals("platform")) {
					if ("iOS".equals(c.getValue())) {
						myState |= Tool.IOS;
					} else if ("Android".equals(c.getValue())) {
						myState |= Tool.ANDROID;
					} else if ("WindowsMobile".equals(c.getValue())) {
						myState |= Tool.WINDOWS_PHONE;
					} else if ("Blackberry".equals(c.getValue())) {
						myState |= Tool.BLACKBERRY;
					}
				}
			}
		}

		String alias = "PUBLIC";
		if (request.getParameter("alias") != null) {
			alias = request.getParameter("alias").toUpperCase();
		}

		HomeScreen home;
		if (user != null && user.getViewCampus() != null) {
			alias = user.getViewCampus();
		}

		home = getAdminService().getHomeScreenByAlias(alias);
		if (home == null) {
			LOG.debug("Home screen was null, getting PUBLIC screen.");
			home = getAdminService().getHomeScreenByAlias("PUBLIC");
		}

		List<HomeTool> copy;
		if (home == null) {
			LOG.error("Home screen object still null when it should not be.");
			copy = new ArrayList<HomeTool>();
		} else {
			copy = new ArrayList<HomeTool>(home.getHomeTools());
		}
		Collections.sort(copy);

		List<HomeTool> userTools = new ArrayList<HomeTool>();
		for (HomeTool homeTool : copy) {
			Tool tool = homeTool.getTool();

			AclExpression viewingPermission = tool.getViewingPermission();
			if (viewingPermission != null && viewingPermission.getParsedExpression() != null && !viewingPermission.getParsedExpression().evaluate(user)) {
				continue;
			}

			if ("alerts".equals(tool.getAlias())) {
				int count = getAlertsService().findAlertCountByCampus(user.getViewCampus());
				if (count > 0) {
					tool.setBadgeCount(Integer.toString(count));
					tool.setIconUrl("images/service-icons/srvc-alerts-red.png");
				} else {
					tool.setBadgeCount(null);
					tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
				}
			}
			if ("incident".equals(tool.getAlias()) || "reportingadmin".equals(tool.getAlias())) {
				tool.setBadgeText("beta");
			}

			if ("backdoor".equals(tool.getAlias())) {
				if (backdoor != null) {
					tool.setBadgeCount(backdoor.getUserId());
				} else {
					tool.setBadgeCount("");
				}
			}
			// If a tools requisites is unset it will be treated as available to any, same as Tool.ANY.
			if ((tool.getRequisites() & myState) == myState || (tool.getRequisites() == Tool.UNDEFINED_REQUISITES)) {
				LOG.info("--- SHOW TOOL: " + tool.getAlias());
				userTools.add(homeTool);
			} else {
				LOG.info("--- HIDE TOOL: " + tool.getAlias());
			}
		}
		uiModel.addAttribute("title", home.getTitle());
		uiModel.addAttribute("tools", userTools);
	}

	/**
	 * Gets a reference to the <code>ApplicationContext</code>.
	 *
	 * @return A reference to the <code>ApplicationContext</code>.
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * Sets the reference to the <code>ApplicationContext</code>.
	 *
	 * @param applicationContext The reference to the <code>ApplicationContext</code>.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Gets a reference to the KME Properties.
	 *
	 * @return A reference to the KME Properties.
	 */
	public Properties getKmeProperties() {
		return this.kmeProperties;
	}

	/**
	 * Sets the reference to the KME Properties.
	 *
	 * @param kmeProperties The reference to the KME Properties.
	 */
	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	/**
	 * A reference to the <code>LocalContainerEntityManagerFactoryBean</code>.
	 */
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	/**
	 * A reference to the <code>AdminService</code>.
	 */
	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	/**
	 * A reference to the <code>AlertsService</code>.
	 */
	public AlertsService getAlertsService() {
		return alertsService;
	}

	public void setAlertsService(AlertsService alertsService) {
		this.alertsService = alertsService;
	}

	/**
	 * A reference to the <code>ConfigParamService</code>.
	 */
	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}

	/**
	 * A reference to the <code>CampusService</code>.
	 */
	public CampusService getCampusService() {
		return campusService;
	}

	public void setCampusService(CampusService campusService) {
		this.campusService = campusService;
	}

	/**
	 * A reference to the <code>CoreService</code>.
	 */
	public CoreService getCoreService() {
		return coreService;
	}

	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}

	public SenderService getSenderService() {
		return senderService;
	}

	public void setSenderService(SenderService senderService) {
		this.senderService = senderService;
	}

	/**
	 * A list of locale codes which KME supports
	 */
	public List<String> getSupportedLanguages() {
		return supportedLanguages;
	}

	public void setSupportedLanguages(List<String> supportedLanguages) {
		this.supportedLanguages = supportedLanguages;
	}

}
