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

package org.kuali.mobility.bus.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.kuali.mobility.bus.entity.Bus;
import org.kuali.mobility.bus.entity.BusAlert;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import java.util.List;

/**
 * Implementation of the CXF Device Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFBusService {

	/** A reference to a logger for this service */
	private static final Logger LOG = LoggerFactory.getLogger(CXFBusService.class);
	
	@Autowired
	@Qualifier("busService")
	private BusService service;
	
	@GET
    @Path("/locations")
	public String getBusLocations(){
		List<Bus> buses = this.getService().getDao().getBuses();
		JSONArray jsonBuses;
		if (buses != null) {
			jsonBuses = (JSONArray) JSONSerializer.toJSON(buses);			
		} else {
			return null;
		}
		return jsonBuses.toString();
	}

	@GET
    @Path("/routes")
	public String getBusRoutes(){
		List<BusRoute> routes = this.getService().getDao().getBusRoutes();
		JSONArray jsonRoutes;
		if (routes != null) {
			jsonRoutes = (JSONArray) JSONSerializer.toJSON(routes);			
		} else {
			return null;
		}
		return jsonRoutes.toString();
	}
	
	@GET
    @Path("/stops/{stopId}")
	public String getBusStops(@PathParam("stopId") int stopId){
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

	@GET
    @Path("/alerts")
	public String busAlerts() {
		List<BusAlert> alerts = this.getService().getDao().getBusAlerts();
		JSONArray jsonAlerts;
		if (alerts != null) {
			jsonAlerts = (JSONArray) JSONSerializer.toJSON(alerts);
		} else {
			return null;
		}
		return jsonAlerts.toString();
	}
	
	@GET
    @Path("/routes/byDistance")
	public String getBusRoutesByDistance(@QueryParam(value="latitude") String latitude, @QueryParam(value="longitude") String longitude, @QueryParam(value="radius") String radius){
		LOG.debug("latitude: " + latitude);
		LOG.debug("longitude: " + longitude);
		LOG.debug("radius: " + radius);
		List<? extends BusRoute> distanceRoutes = getService().getRoutesWithDistance(Double.parseDouble(latitude), Double.parseDouble(longitude), Double.parseDouble(radius));
		JSONArray jsonStops = (JSONArray) JSONSerializer.toJSON(distanceRoutes);
		return jsonStops.toString();
	}

	@GET
	@Path("/ping/get")
	public String pingGet(){
		return "{\"status\":\"OK\"}";
	}

	@POST
	@Path("/ping/post")
	public String pingPost(){
		return "{\"status\":\"OK\"}";
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
	
	
}
