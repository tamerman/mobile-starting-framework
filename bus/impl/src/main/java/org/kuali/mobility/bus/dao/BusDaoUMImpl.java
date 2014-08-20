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

package org.kuali.mobility.bus.dao;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.bus.dao.helper.BusStopNameUtil;
import org.kuali.mobility.bus.entity.*;
import org.kuali.mobility.bus.entity.helper.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class BusDaoUMImpl implements BusDao, ApplicationContextAware {

	public static Logger LOG = LoggerFactory.getLogger(BusDaoUMImpl.class);

	private ApplicationContext applicationContext;

	private List<BusStop> busStops;
	private List<BusRoute> busRoutes;
	private List<Bus> buses;
	private List<BusAlert> alerts;

	private String busAlertUrl;
	private String busStopUrl;
	private String busRouteUrl;
	private String busLocationUrl;
	private String busRoutePathUrl;
	
	private BusStopNameUtil busStopNameMapper;

    @Override
	public void loadAlerts() {
    	XStream xstream = new XStream();
    	xstream.processAnnotations(UMBusAlertReader.class);
    	xstream.processAnnotations(UMBusAlert.class);
    	xstream.addImplicitCollection(UMBusAlertReader.class, "alerts");
    	
    	UMBusAlertReader busAlertReader = null;
    	try {
    		busAlertReader = (UMBusAlertReader) xstream.fromXML(new URL(getBusAlertUrl()));
    	} catch (MalformedURLException ex) {
    		LOG.error(ex.toString());
    	}
    	List<BusAlert> busAlerts = new ArrayList<BusAlert>();
    	if (busAlertReader == null) {
    		LOG.debug("busAlertReader == null");
    	} else if (busAlertReader.getAlerts() == null) {
    		LOG.debug("busAlertReader.getAlerts() == null");
    	} else {
    		for (UMBusAlert b : busAlertReader.getAlerts()) {
    			BusAlert busAlert = (BusAlert) getApplicationContext().getBean("busAlert");
    			busAlert.setTitle(b.getTitle());
    			busAlert.setMessage(b.getMessage());
    			busAlert.setColor(b.getColor());
    			busAlert.setMessageType(b.getMessageType());
    			busAlerts.add(busAlert);
    		}
    	}
    	this.setAlerts(busAlerts);
	}
    
	public void loadRoutes() {
		XStream xstream = new XStream();
		xstream.processAnnotations(UMBusRouteReader.class);
		xstream.processAnnotations(UMBusRoute.class);
		xstream.processAnnotations(UMBusStop.class);
		xstream.processAnnotations(UMBusRoutePathReader.class);
		xstream.processAnnotations(UMBusRoutePathLatLong.class);
		xstream.processAnnotations(UMBusRoutePathInfo.class);
		xstream.addImplicitCollection(UMBusRouteReader.class, "routes");
		xstream.addImplicitCollection(UMBusRoute.class, "stops");
		xstream.addImplicitCollection(UMBusRoutePathReader.class, "paths");

		UMBusRouteReader routeReader = null;
		try {
			routeReader = (UMBusRouteReader) xstream.fromXML(new URL(
					getBusRouteUrl()));
		} catch (MalformedURLException ex) {
			LOG.error(ex.toString());
		}

		List<BusRoute> routes = new ArrayList<BusRoute>();
        List<BusStop>  stops  = new ArrayList<BusStop>();
        boolean hasRoutes = routeReader != null && CollectionUtils.isNotEmpty(routeReader.getRoutes());
        if (hasRoutes) {
			for (UMBusRoute r : routeReader.getRoutes()) {
				BusRoute route = (BusRoute) getApplicationContext().getBean("busRoute");
				route.setId(Long.parseLong(r.getId()));
				UMBusRoutePathReader pathReader = null;
				try {
					String fullUrl = getBusRoutePathUrl() + route.getId() + ".xml";
					pathReader = (UMBusRoutePathReader) xstream.fromXML(new URL(fullUrl));
				} catch (MalformedURLException ex) {
					LOG.error(ex.toString());
				} catch (StreamException ex) {
					LOG.error(ex.toString());
				}
				boolean hasPathReader = pathReader != null && (pathReader.getPaths() != null || pathReader.getInfo() != null);
		        if (hasPathReader) {
		        	BusRoutePath routePath = (BusRoutePath) getApplicationContext().getBean("busRoutePath");
		        	routePath.setId(pathReader.getInfo().getId());
		        	routePath.setColor(pathReader.getInfo().getColor());
		        	routePath.setTransparency(pathReader.getInfo().getTransparency());
		        	routePath.setLineWidth(pathReader.getInfo().getLineWidth());
		        	List<Double> points = new ArrayList<Double>();
		        	for (UMBusRoutePathLatLong point : pathReader.getPaths()) {
		        		points.add(Double.parseDouble(point.getLatitude()));
		        		points.add(Double.parseDouble(point.getLongitude()));
		        	}
		        	routePath.setLatLongs(points);
		        	route.setPath(routePath);
				}
				route.setName(r.getName());
				route.setColor(r.getColor());
				LOG.debug("route color:" + route.getColor());
				if (null == getBusStops()) {
					setBusStops(new ArrayList<BusStop>());
				}

				if (r!=null && r.getStops()!=null) {
					for (UMBusStop s : r.getStops()) {
						BusStop stop = (BusStop) getApplicationContext().getBean("busStop");
						//stop.setName(s.getName());
						//LOG.debug("bus stop ROUTE name: " + r.getName() + ", stopname -" + s.getName() + ", stopname2 -" + s.getName2() + ", stopname3 - " + s.getName3());
						//set busstopname loaded from xml
						stop.setName(getBusStopNameMapper().updateBusStopName(s.getName()));
						stop.setId(s.getName().hashCode());
						stop.setLatitude(s.getLatitude());
						stop.setLongitude(s.getLongitude());

                        List<ScheduledStop> schedule = new ArrayList<ScheduledStop>();

                        // TODO: Fix this to dynamically utilize the toacount
						// variable.
						// This is functional but potentially will break.
						if (s.getId1() != null) {
							LOG.debug("Looking up bus " + s.getId1());
							Bus tBus = getBus(Long.parseLong(s.getId1()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                           // scheduledStop.setTimeToArrival( (new Float( s.getToa1() ) ).intValue() );
                            // fix time as not rounding properly for float, changed to double
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa1())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId2() != null) {
							LOG.debug("Looking up bus " + s.getId2());
							Bus tBus = getBus(Long.parseLong(s.getId2()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa2())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId3() != null) {
							LOG.debug("Looking up bus " + s.getId3());
							Bus tBus = getBus(Long.parseLong(s.getId3()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa3())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId4() != null) {
							LOG.debug("Looking up bus " + s.getId4());
							Bus tBus = getBus(Long.parseLong(s.getId4()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa4())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId5() != null) {
							LOG.debug("Looking up bus " + s.getId5());
							Bus tBus = getBus(Long.parseLong(s.getId5()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa5())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId6() != null) {
							LOG.debug("Looking up bus " + s.getId6());
							Bus tBus = getBus(Long.parseLong(s.getId6()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa6())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId7() != null) {
							LOG.debug("Looking up bus " + s.getId7());
							Bus tBus = getBus(Long.parseLong(s.getId7()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa7())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId8() != null) {
						    LOG.debug("Looking up bus " + s.getId8());
							Bus tBus = getBus(Long.parseLong(s.getId8()));
							LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa8())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId9() != null) {
						    LOG.debug("Looking up bus " + s.getId9());
							Bus tBus = getBus(Long.parseLong(s.getId9()));
						    LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa9())/60));
							schedule.add( scheduledStop );
						}
						if (s.getId10() != null) {
						 	LOG.debug("Looking up bus " + s.getId10());
							Bus tBus = getBus(Long.parseLong(s.getId10()));
						    LOG.debug("Bus was " + (tBus == null ? "not " : "")
									+ "found.");
                            ScheduledStop scheduledStop = (ScheduledStop)getApplicationContext().getBean("scheduledStop");
                            scheduledStop.setBus(tBus);
                            scheduledStop.setBusStopRouteName(r.getName());
                            scheduledStop.setTimeToArrival( Math.round(new Double(s.getToa10())/60));
							schedule.add( scheduledStop );
						}

						if( stops.contains(stop) ) {
	                        LOG.debug( "Bus stop already exists in the list for: "+stop.getName() );
	                        int i = stops.indexOf( stop );
	                        BusStop oldStop = stops.get( i );
	                        if (oldStop.getScheduledStop()==null) {
	                        	oldStop.setScheduledStop(schedule);
	                        }
	                        else {
	                        	oldStop.getScheduledStop().addAll(schedule);
	                        }
	                        stop = oldStop;
						}
						else {
							LOG.debug( "Bus Stop is not found in master list for: "+stop.getName() );
							stop.setScheduledStop(schedule);
	                        stops.add(stop);
						}

						route.addStop(stop);

					}
				}
				if ( route.getStops()==null || route.getStops().isEmpty() ) {
					LOG.info("Route " + route.getName() + " has NO stops, so do NOT show it!!!");
				}
				else {
					routes.add(route);
				}
			}
        }
        setBusRoutes(routes);
        setBusStops( stops );
		LOG.info((null == getBusRoutes() ? "Failed to load" : "Loaded " + getBusRoutes().size()) + " routes.");
	}

	@Override
	public void loadStops() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadBusLocations() {
		XStream xstream = new XStream();
		xstream.processAnnotations(UMBusReader.class);
		xstream.processAnnotations(UMBus.class);
		xstream.addImplicitCollection(UMBusReader.class, "items");

		UMBusReader busReader = null;
		try {
			busReader = (UMBusReader) xstream.fromXML(new URL(
					getBusLocationUrl()));
		} catch (MalformedURLException ex) {
			LOG.error(ex.toString());
		}

		List<Bus> busData = new ArrayList<Bus>();
		if (busReader == null){
			LOG.debug("busReader == null");
		}
		else if (busReader.getItems() == null) {
			LOG.debug("busReader.getItems() == null");
		}
		else {
			for (UMBus b : busReader.getItems()) {
				Bus bus = (Bus) getApplicationContext().getBean("bus");
				bus.setName(b.getRouteName());
				bus.setId(b.getId());
				bus.setRouteId(b.getRouteId());
				bus.setRouteName(b.getRouteName());
				bus.setHeading(b.getHeading());
				bus.setColor(b.getColor());
				bus.setLatitude(b.getLatitude());
				bus.setLongitude(b.getLongitude());
				busData.add(bus);
			}
		}
		this.setBuses(busData);
	}
	
	@Override
	public List<BusAlert> getBusAlerts() {
		return alerts;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext
	 *            the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * @return the busStops
	 */
	public List<BusStop> getBusStops() {
		return busStops;
	}

	/**
	 * @param busStops
	 *            the busStops to set
	 */
	public void setBusStops(List<BusStop> busStops) {
		this.busStops = busStops;
	}

	/**
	 * @return the busRoutes
	 */
	public List<BusRoute> getBusRoutes() {
		return busRoutes;
	}

	/**
	 * @param busRoutes
	 *            the busRoutes to set
	 */
	public void setBusRoutes(List<BusRoute> busRoutes) {
		this.busRoutes = busRoutes;
	}

	/**
	 * @return the buses
	 */
	public List<Bus> getBuses() {
		return buses;
	}

	/**
	 * @param buses
	 *            the buses to set
	 */
	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	public Bus getBus(long id) {
		Bus bus = null;
		if (null == getBuses()) {
			loadBusLocations();
		}
		for (Bus b : getBuses()) {
			if (bus != null) {
				break;
			}
			if (b.getId() == id) {
				bus = b;
			}
		}
		return bus;
	}

	/**
	 * @return the busStopUrl
	 */
	public String getBusStopUrl() {
		return busStopUrl;
	}

	/**
	 * @param busStopUrl
	 *            the busStopUrl to set
	 */
	public void setBusStopUrl(String busStopUrl) {
		this.busStopUrl = busStopUrl;
	}

	/**
	 * @return the busRouteUrl
	 */
	public String getBusRouteUrl() {
		return busRouteUrl;
	}

	/**
	 * @param busRouteUrl
	 *            the busRouteUrl to set
	 */
	public void setBusRouteUrl(String busRouteUrl) {
		this.busRouteUrl = busRouteUrl;
	}

	/**
	 * @return the busLocationUrl
	 */
	public String getBusLocationUrl() {
		return busLocationUrl;
	}

	/**
	 * @param busLocationUrl
	 * the busLocationUrl to set
	 */
	public void setBusLocationUrl(String busLocationUrl) {
		this.busLocationUrl = busLocationUrl;
	}

    /**
     * @return the busStopNameMapper
     */
    public BusStopNameUtil getBusStopNameMapper() {
        return busStopNameMapper;
    }

    /**
     * @param busStopNameMapper the busStopNameMapper to set
     */
    public void setBusStopNameMapper(BusStopNameUtil busStopNameMapper) {
        this.busStopNameMapper = busStopNameMapper;
    }

	public String getBusRoutePathUrl() {
		return busRoutePathUrl;
	}

	public void setBusRoutePathUrl(String busRoutePathUrl) {
		this.busRoutePathUrl = busRoutePathUrl;
	}

	public String getBusAlertUrl() {
		return busAlertUrl;
	}

	public void setBusAlertUrl(String busAlertUrl) {
		this.busAlertUrl = busAlertUrl;
	}

	public List<BusAlert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<BusAlert> alerts) {
		this.alerts = alerts;
	}
}
