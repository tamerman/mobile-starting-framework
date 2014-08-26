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

package org.kuali.mobility.bus.service.util;

import org.kuali.mobility.bus.entity.BusStop;

public class BusStopDistanceUtil {
	private static double earthRadius = 6371; // km

	public BusStopDistanceUtil() {

	}

	/*calculate each busstop distance based on lat,lon */
	public double calculateDistance(BusStop s, double lat1, double lon1) {
		double pk = 180 / 3.14159;

		double a1 = Double.parseDouble(s.getLatitude()) / pk;
		double a2 = Double.parseDouble(s.getLongitude()) / pk;
		double b1 = lat1 / pk;
		double b2 = lon1 / pk;

		double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
		double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
		double t3 = Math.sin(a1) * Math.sin(b1);
		double tt = Math.acos(t1 + t2 + t3);

		return 6366000 * tt;
		    /*
		    double lat2 = Double.parseDouble(s.getLatitude());
			double lon2 = Double.parseDouble(s.getLongitude());
			double dlon = Math.toRadians(lon2 - lon1);
			double dlat = Math.toRadians(lat2 - lat1);
			double a = (Math.sin(dlat / 2)) * (Math.sin(dlat / 2))
					+ (Math.sin(dlon / 2)) * Math.sin(dlon / 2)
					* Math.cos(lat1) * Math.cos(lat2);
			double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
			double dist = earthRadius * c;
		    return dist;
		    */
	}

}
