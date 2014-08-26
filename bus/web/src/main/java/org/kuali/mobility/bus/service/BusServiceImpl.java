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

package org.kuali.mobility.bus.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.bus.dao.BusDao;
import org.kuali.mobility.bus.entity.*;
import org.kuali.mobility.bus.service.util.BusStopDistanceUtil;
import org.kuali.mobility.bus.util.ScheduledStopComparitor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import java.util.*;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class BusServiceImpl implements BusService, ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(BusServiceImpl.class);
	private ApplicationContext applicationContext;
	@Resource(name = "busDao")
	private BusDao dao;

	@GET
	@Path("/route/lookup/{campus}")
	@Override
	public List<BusRoute> getRoutes(@QueryParam(value = "campus") String campus) {
		List<BusRoute> routes = getDao().getBusRoutes();
		if (CollectionUtils.isEmpty(routes)) {
			getDao().loadRoutes();
			routes = getDao().getBusRoutes();
		}
		return routes;
	}

	@GET
	@Path("/route/search/{campus}")
	@Override
	public BusRoute getRoute(@QueryParam(value = "campus") String campus, @QueryParam(value = "routeId") long id) {
		BusRoute route = null;
		List<BusRoute> routes = getDao().getBusRoutes();
		if (CollectionUtils.isEmpty(routes)) {
			getDao().loadRoutes();
			routes = getDao().getBusRoutes();
		}
		for (BusRoute r : routes) {
			if (r.getId() == id) {
				route = r;
				break;
			}
		}
		return route;
	}

	@GET
	@Path("/stop/lookup/{campus}")
	@Override
	public List<BusStop> getStops(@QueryParam(value = "campus") String campus) {
		List<BusStop> stops = getDao().getBusStops();
		if (CollectionUtils.isEmpty(stops)) {
			getDao().loadRoutes();
			stops = getDao().getBusStops();
		}
		return stops;
	}

	@GET
	@Path("/stop/routestopdistances")
	@Override
	public List<BusRoute> getRoutesWithDistance(
			@QueryParam(value = "latitude") double lat1,
			@QueryParam(value = "longitude") double lon1,
			@QueryParam(value = "radius") double radius) {
		BusStopDistanceUtil bsutil = new BusStopDistanceUtil();
		// adds same stops but for different routes
		List<BusRoute> routesImpl = getRoutes("");
		for (BusRoute r : routesImpl) {
			for (BusStop s : r.getStops()) {
				double dist = bsutil.calculateDistance(s, lat1, lon1);
				s.setDistance(dist);
			}
		}
		return routesImpl;
	}

	@GET
	@Path("/stop/nearby")
	@Override
	public List<BusStop> getNearbyStops(
			@QueryParam(value = "latitude") double lat1,
			@QueryParam(value = "longitude") double lon1,
			@QueryParam(value = "radius") double radius) {
		List<BusStop> stops = new ArrayList<BusStop>();
		Map<Double, BusStop> mapaTemp = new TreeMap<Double, BusStop>();
		double dist = 0.0;
		List<BusStop> busStops = getDao().getBusStops();
		if (CollectionUtils.isEmpty(busStops)) {
			getDao().loadRoutes();
			busStops = getDao().getBusStops();
		}
		BusStopDistanceUtil bsutil = new BusStopDistanceUtil();
		for (BusStop s : busStops) {
			dist = bsutil.calculateDistance(s, lat1, lon1);
			if (dist <= radius) {
				//distance in km
				mapaTemp.put(dist, s);
			}
		}
		for (Iterator<Double> it = mapaTemp.keySet().iterator(); it.hasNext(); ) {
			Double key = (Double) it.next();
			BusStop stop = mapaTemp.get(key);
			stop.setDistance(key);
			stops.add(stop);
		}
		return stops;
	}

	@GET
	@Path("/stop/lookupbyid/{campus}")
	@Override
	public BusStop getStop(@QueryParam(value = "campus") String campus, @QueryParam(value = "stopId") long id) {
		BusStop stop = null;
		List<BusStop> stops = getStops("");

		for (BusStop s : stops) {
			if (s.getId() == id) {
				stop = s;
				break;
			}
		}
		return stop;
	}

	@GET
	@Path("/stop/lookupbyname/{campus}")
	@Override
	public BusStop getStopByName(
			@QueryParam(value = "name") final String stopName,
			@QueryParam(value = "campus") final String campus) {
		BusStop busStop = null;
		for (BusStop s : getStops("")) {
			if (s.getName() != null
					&& s.getName().equalsIgnoreCase(stopName)) {
				busStop = s;
				break;
			}
		}
		LOG.debug("Bus stop found for " + stopName + " with " + (busStop.getScheduledStop() == null ? "NULL" : busStop.getScheduledStop().size()) + " stops.");
		return busStop;
	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/stop/schedule/{campus}")
	@Override
	public List<ScheduledStop> getArrivalData(
			@QueryParam(value = "routeId") final String routeId,
			@QueryParam(value = "stopId") final String stopId,
			@QueryParam(value = "campus") final String campus) {

		List<ScheduledStop> arrivals;
		BusStop stop = null;

		if (StringUtils.isNotBlank(routeId)) {
			BusRoute route = getRoute(campus, Long.parseLong(routeId));
			if (null != route && null != route.getStops() && !route.getStops().isEmpty()) {
				for (BusStop s : route.getStops()) {
					LOG.debug(route.getName() + " routeid: " + route.getId() + " stopid: " + s.getId() + " stop name " + s.getName());
					if (s.getId() == Long.parseLong(stopId)) {
						stop = s;
						break;
					}
				}
			}
		} else {
			stop = getStop(campus, Long.parseLong(stopId));
			LOG.debug("Unable to find route.");
		}

		if (null != stop) {
			arrivals = (List<ScheduledStop>) (List<?>) stop.getScheduledStop();
		} else {
			arrivals = new ArrayList<ScheduledStop>();
		}
		Collections.sort(arrivals, new ScheduledStopComparitor());
		return arrivals;
	}

	@GET
	@Path("/locations")
	@Override
	public List<Bus> getAllBuses(@QueryParam("campus") String campus) {
		if (null == getDao().getBuses() || getDao().getBuses().isEmpty()) {
			getDao().loadBusLocations();
		}
		return getDao().getBuses();
	}

	@GET
	@Path("/stops/{stopId}")
	@Override
	public BusStop getStopById(@PathParam("stopId") int stopId) {
		List<BusStop> busStops = (List<BusStop>) this.getStops("");
		BusStop foundStop = null;
		for (BusStop stop : busStops) {
			if (stopId == stop.getId()) {
				foundStop = stop;
				break;
			}
		}
		return foundStop;
	}

	@GET
	@Path("/alerts")
	@Override
	public List<BusAlert> getAlerts() {
		return getDao().getBusAlerts();
	}

	public void setDao(BusDao dao) {
		this.dao = dao;
	}

	public BusDao getDao() {
		return dao;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
