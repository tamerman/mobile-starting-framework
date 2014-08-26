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

package org.kuali.mobility.math.geometry;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class Spherical {
	public static double computeHeading(Point from, Point to) {
		double deltaLng = degreesToRadians(to.getLongitude() - from.getLongitude());
		double lat1 = degreesToRadians(from.getLatitude());
		double lat2 = degreesToRadians(to.getLatitude());
		double y = Math.sin(deltaLng) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(deltaLng);
		return radiansToDegrees(Math.atan2(y, x));
	}

	public static double computeDistanceBetween(Point from, Point to) {
		double R = 6378100; // radius of Earth in m
		double deltaLat = degreesToRadians(to.getLatitude() - from.getLatitude());
		double deltaLng = degreesToRadians(to.getLongitude() - from.getLongitude());
		double lat1 = degreesToRadians(from.getLatitude());
		double lat2 = degreesToRadians(to.getLatitude());

		double a = Math.sin(deltaLat / 2.0) * Math.sin(deltaLat / 2.0) +
				Math.sin(deltaLng / 2.0) * Math.sin(deltaLng / 2.0) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));
		return R * c;
	}

	public static double degreesToRadians(double degrees) {
		return degrees * Math.PI / 180.0;
	}

	public static double radiansToDegrees(double radians) {
		return radians * 180.0 / Math.PI;
	}
}
