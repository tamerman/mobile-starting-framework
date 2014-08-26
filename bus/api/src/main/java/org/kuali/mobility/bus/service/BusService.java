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

import org.kuali.mobility.bus.dao.BusDao;
import org.kuali.mobility.bus.entity.*;

import javax.jws.WebService;
import java.util.List;

@WebService
public interface BusService {

	public List<BusRoute> getRoutes(String campus);

	public BusRoute getRoute(String campus, long id);

	public List<BusStop> getStops(String campus);

	public BusStop getStop(String campus, long id);

	public BusStop getStopByName(String name, String campus);

	public BusStop getStopById(int id);

	public List<BusStop> getNearbyStops(double lat1, double lon1, double radius);

	public List<BusRoute> getRoutesWithDistance(double lat1, double lon1, double radius);

	public List<ScheduledStop> getArrivalData(String campus, String routeId, String stopId);

	public List<Bus> getAllBuses(String campus);

	public List<BusAlert> getAlerts();

	public BusDao getDao();
}
