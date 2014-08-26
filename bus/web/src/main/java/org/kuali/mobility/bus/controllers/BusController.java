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

package org.kuali.mobility.bus.controllers;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.bus.entity.Bus;
import org.kuali.mobility.bus.entity.BusAlert;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.kuali.mobility.bus.service.BusService;
import org.kuali.mobility.bus.util.BusConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@Controller
@RequestMapping("/bus")
public class BusController implements ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(BusController.class);
	@Resource(name = "busService")
	private BusService service;
	@Resource(name = "busProperties")
	private Properties busProperties;
	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	private ApplicationContext applicationContext;

	private static final Map<String, String> URL_MAP;

	static {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put(BusConstants.MAP.toString(), "/viewBusTracking");
		aMap.put(BusConstants.STOPS.toString(), "/viewStops");
		aMap.put(BusConstants.ROUTES.toString(), "");
		URL_MAP = Collections.unmodifiableMap(aMap);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		String viewName = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			viewName = "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();

			//took out for viewBusTracking
			//List<? extends BusRoute> routes = getService().getRoutes(campus);
			//LOG.debug("Found " + routes.size() + " bus routes via local service for campus " + campus);
			//uiModel.addAttribute("routes", routes);
			uiModel.addAttribute("campus", campus);

			// Adding these to the session upon entry so that we don't
			// have to calculate the order upon every page load.
			HttpSession mySession = request.getSession();
			mySession.setAttribute(BusConstants.URL_MAP.toString(), URL_MAP);
			mySession.setAttribute(BusConstants.TAB_ORDER.toString(), getTabOrder());

			uiModel.addAttribute("currentTime", System.currentTimeMillis());
			uiModel.addAttribute("routeId", null);
			uiModel.addAttribute("stopId", null);
			Properties kmeProperties = (Properties) getApplicationContext().getBean("kmeProperties");
			uiModel.addAttribute("initialLatitude", kmeProperties.getProperty("maps.center.lat", "0.0"));
			uiModel.addAttribute("initialLongitude", kmeProperties.getProperty("maps.center.lon", "0.0"));
			if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
				viewName = "ui3/bus/index";
			} else {
				viewName = "bus/index";
			}
		}
		return viewName;
	}

	@RequestMapping(value = "/templates/{key}")
	public String getAngularTemplates(
			@PathVariable("key") String key,
			HttpServletRequest request,
			Model uiModel) {
		uiModel.addAttribute("currentTime", System.currentTimeMillis());
		uiModel.addAttribute("initialLatitude", getKmeProperties().getProperty("maps.center.lat", "0.0"));
		uiModel.addAttribute("initialLongitude", getKmeProperties().getProperty("maps.center.lon", "0.0"));
		uiModel.addAttribute("stopId", null);
		uiModel.addAttribute("routeId", null);
		uiModel.addAttribute("tabOrder", getBusProperties().getProperty("bus.taborder", "map,nearbystops,favorites,routes").split(","));
		return "ui3/bus/templates/" + key;
	}

	@RequestMapping(value = "/js/{key}.js")
	public String getJavaScript(
			@PathVariable("key") String key,
			Model uiModel,
			HttpServletRequest request) {
		String selectedCampus = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		selectedCampus = user.getViewCampus();
		uiModel.addAttribute("campus", selectedCampus);
		uiModel.addAttribute("tabOrder", getBusProperties().getProperty("bus.taborder", "routes,favorites,nearbystops,map"));
		uiModel.addAttribute("distanceUnits", getBusProperties().getProperty("bus.distance.units", "imperial"));
		uiModel.addAttribute("favoriteDisabled", getBusProperties().getProperty("bus.favorite.color.disabled", "white"));
		uiModel.addAttribute("favoriteEnabled", getBusProperties().getProperty("bus.favorite.color.enabled", "yellow"));
		uiModel.addAttribute("stopRefresh", getBusProperties().getProperty("bus.stop.refresh", "10"));
		uiModel.addAttribute("initialLatitude", getKmeProperties().getProperty("maps.center.lat", "0.0"));
		uiModel.addAttribute("initialLongitude", getKmeProperties().getProperty("maps.center.lon", "0.0"));
		uiModel.addAttribute("initialZoom", getKmeProperties().getProperty("maps.google.default.zoom", "8"));

		uiModel.addAttribute("defaultTab", getBusProperties().getProperty("bus.taborder", "map,nearbystops,favorites,routes").split(",")[0]);

		return "ui3/bus/js/" + key;
	}

	@RequestMapping(value = "/viewStops")
	public String viewStops(HttpServletRequest request, Model uiModel) {
		HttpSession mySession = request.getSession();
		mySession.setAttribute(BusConstants.URL_MAP.toString(), URL_MAP);
		mySession.setAttribute(BusConstants.TAB_ORDER.toString(), getTabOrder());
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			return "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();
		}
		LOG.debug("viewStops:" + campus);
		List<? extends BusRoute> routes = getService().getRoutes(campus);
		JSONArray jsonRoutes = (JSONArray) JSONSerializer.toJSON(routes);
		uiModel.addAttribute("routes", jsonRoutes);
		List<? extends BusStop> stops = getService().getStops(campus);
		JSONArray jsonStops = (JSONArray) JSONSerializer.toJSON(stops);
		LOG.debug("Found " + stops.size() + " bus stops via local service for campus " + campus);
		uiModel.addAttribute("stops", jsonStops);
		uiModel.addAttribute("campus", campus);
		return "bus/viewStops";
	}

	@RequestMapping(value = "/viewBus")
	public String viewBus(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String routeId, @RequestParam(required = true) String busId, @RequestParam(required = false) String campus) {
		HttpSession mySession = request.getSession();
		mySession.setAttribute(BusConstants.URL_MAP.toString(), URL_MAP);
		mySession.setAttribute(BusConstants.TAB_ORDER.toString(), getTabOrder());
		BusRoute route = getService().getRoute(campus, Long.parseLong(routeId));
		JSONObject jsonRoutes = (JSONObject) JSONSerializer.toJSON(route);
		uiModel.addAttribute("busId", busId);
		uiModel.addAttribute("route", jsonRoutes);
		return "bus/viewBus";
	}

	@RequestMapping(value = "/viewNearByStops")
	public String viewNearByStops(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String latitude, @RequestParam(required = false) String longitude, @RequestParam(required = false) String radius) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			return "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();
		}
		if ((latitude != null) && (longitude != null) && (radius != null)) {
			LOG.debug("latitude: " + latitude);
			LOG.debug("longitude: " + longitude);
			LOG.debug("radius: " + radius);
			List<? extends BusStop> stopsnear = getService().getNearbyStops(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius));
			//
			if (stopsnear != null) {
				LOG.debug("Found nearby stops " + stopsnear.size());
			} else {
				LOG.debug("Unable to find stop.");
			}

			uiModel.addAttribute("stopsnear", stopsnear);
			uiModel.addAttribute("campus", campus);
		}

		return "bus/viewNearByStops";
	}

	@RequestMapping(value = "/viewRoute", method = RequestMethod.GET)
	public String viewRoute(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String routeId, @RequestParam(required = false) String campus) {
		BusRoute route = getService().getRoute(campus, Long.parseLong(routeId));

		uiModel.addAttribute("route", route);
		uiModel.addAttribute("campus", campus);

		return "bus/viewRoute";
	}

	@RequestMapping(value = "/favoriteStops")
	public String viewFavoriteStops(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			return "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();
		}
		uiModel.addAttribute("campus", campus);
		return "bus/favoriteStops";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewStop")
	public String viewStop(HttpServletRequest request, Model uiModel, @RequestParam(required = true) String routeId, @RequestParam(required = true) String stopId, @RequestParam(required = false) String campus) {
		HttpSession mySession = request.getSession();
		mySession.setAttribute(BusConstants.URL_MAP.toString(), URL_MAP);
		mySession.setAttribute(BusConstants.TAB_ORDER.toString(), getTabOrder());
		BusStop stop = null;
		//LOG.debug( "Found route "+route.getName() + "routeid: " + route.getId());
		stop = getService().getStop(campus, Long.parseLong(stopId));
		List<BusRoute> routes = (List<BusRoute>) getService().getRoutes(campus);
		uiModel.addAttribute("routes", routes);
		uiModel.addAttribute("routeId", routeId);
		uiModel.addAttribute("stop", stop);
		uiModel.addAttribute("campus", campus);

		return "bus/viewStop";
	}

	@RequestMapping(value = "/viewRouteTracking", method = RequestMethod.GET)
	public String viewRouteTracking(HttpServletRequest request, Model uiModel) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			return "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();
		}
		List<? extends BusRoute> routes = getService().getRoutes(campus);
		LOG.debug("Found " + routes.size() + " bus routes via local service for campus " + campus);
		uiModel.addAttribute("routes", routes);
		uiModel.addAttribute("campus", campus);
		return "bus/viewRouteTracking";
	}

	@RequestMapping(value = "/viewBusTracking", method = RequestMethod.GET)
	public String viewBusTracking(HttpServletRequest request, Model uiModel, @RequestParam(required = false) String routeId, @RequestParam(required = false) String stopId) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			//campus = "all";
			return "redirect:/campus?toolName=bus";
		} else {
			campus = user.getViewCampus();
		}
		uiModel.addAttribute("campus", campus);
		Properties kmeProperties = (Properties) getApplicationContext().getBean("kmeProperties");

		uiModel.addAttribute("currentTime", System.currentTimeMillis());
		uiModel.addAttribute("initialLatitude", kmeProperties.getProperty("maps.center.lat", "0.0"));
		uiModel.addAttribute("initialLongitude", kmeProperties.getProperty("maps.center.lon", "0.0"));
		if (StringUtils.isNotBlank(stopId)) {
			uiModel.addAttribute("stopId", stopId);
		} else {
			uiModel.addAttribute("stopId", null);
		}
		if (StringUtils.isNotBlank(routeId)) {
			uiModel.addAttribute("routeId", routeId);
		} else {
			uiModel.addAttribute("routeId", null);
		}
		return "bus/viewBusTracking";
	}

	@Deprecated
	@RequestMapping(value = "/busLocations", method = RequestMethod.GET)
	@ResponseBody
	public String busLocations(Model uiModel) {
		List<Bus> buses = this.getService().getDao().getBuses();
		JSONArray jsonBuses;
		if (buses != null) {
			jsonBuses = (JSONArray) JSONSerializer.toJSON(buses);
		} else {
			return null;
		}
		return jsonBuses.toString();
	}

	@Deprecated
	@RequestMapping(value = "/busRoutes", method = RequestMethod.GET)
	@ResponseBody
	public String busRoutes(Model uiModel) {
		List<BusRoute> routes = this.getService().getDao().getBusRoutes();
		JSONArray jsonRoutes;
		if (routes != null) {
			jsonRoutes = (JSONArray) JSONSerializer.toJSON(routes);
		} else {
			return null;
		}
		return jsonRoutes.toString();
	}

	@Deprecated
	@RequestMapping(value = "/busAlerts", method = RequestMethod.GET)
	@ResponseBody
	public String busAlerts(Model uiModel) {
		List<BusAlert> alerts = this.getService().getDao().getBusAlerts();
		JSONArray jsonAlerts;
		if (alerts != null) {
			jsonAlerts = (JSONArray) JSONSerializer.toJSON(alerts);
		} else {
			return null;
		}
		return jsonAlerts.toString();
	}

	@Deprecated
	@RequestMapping(value = "/routesByDistance", method = RequestMethod.GET)
	@ResponseBody
	public String routesByDistance(Model uiModel, @RequestParam(required = true) String latitude, @RequestParam(required = true) String longitude, @RequestParam(required = true) String radius) {
		LOG.debug("latitude: " + latitude);
		LOG.debug("longitude: " + longitude);
		LOG.debug("radius: " + radius);
		List<? extends BusRoute> distanceRoutes = getService().getRoutesWithDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius));
		//test distance for michigan first one gets feet distance second is for 20-24 mile range
		//List<? extends BusRoute> distanceRoutes = getService().getRoutesWithDistance(Double.parseDouble("42.285948"), Double.parseDouble("-83.731227"), Double.parseDouble(radius));
		//List<? extends BusRoute> distanceRoutes = getService().getRoutesWithDistance(Double.parseDouble("42.322043"), Double.parseDouble("-83.270874"), Double.parseDouble(radius));
		JSONArray jsonStops = (JSONArray) JSONSerializer.toJSON(distanceRoutes);
		return jsonStops.toString();
	}

	@Deprecated
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/busETAs/{stopId}", method = RequestMethod.GET)
	@ResponseBody
	public String busstop(@PathVariable("stopId") int stopId, Model uiModel) {
		List<BusStop> busStops = (List<BusStop>) this.getService().getStops("");
		BusStop foundStop = null;
		for (BusStop stop : busStops) {
			if (stopId == stop.getId()) {
				foundStop = stop;
				break;
			}
		}
		if (foundStop != null) {
			JSONObject jsonStop = (JSONObject) JSONSerializer.toJSON(foundStop);
			return jsonStop.toString();
		}
		return null;
	}

	private List<String> getTabOrder() {
		List<String> tabOrder = new ArrayList<String>();
		for (String s : getBusProperties().getProperty("bus." + BusConstants.TAB_ORDER).split(",")) {
			if ("true".equalsIgnoreCase(getBusProperties().getProperty("bus." + s + ".enabled", "false"))) {
				tabOrder.add(s);
			}
		}
		return tabOrder;
	}

	/**
	 * @return the service
	 */
	public BusService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(BusService service) {
		this.service = service;
	}

	/**
	 * @return the busProperties
	 */
	public Properties getBusProperties() {
		return busProperties;
	}

	/**
	 * @param busProperties the busProperties to set
	 */
	public void setBusProperties(Properties busProperties) {
		this.busProperties = busProperties;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
