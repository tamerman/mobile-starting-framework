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

package org.kuali.mobility.bus.entity;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@XmlRootElement(name = "scheduledStop")
@XmlSeeAlso({Bus.class})
public class ScheduledStop implements Serializable {

	private double timeToArrival;
	private String busStopRouteName;
	private Bus bus;

	/**
	 * @return the timeToArrival
	 */
	public double getTimeToArrival() {
		return timeToArrival;
	}

	/**
	 * @param timeToArrival the timeToArrival to set
	 */
	public void setTimeToArrival(double timeToArrival) {
		this.timeToArrival = timeToArrival;
	}

	/**
	 * @return the bus
	 */
	public Bus getBus() {
		return bus;
	}

	/**
	 * @param bus the bus to set
	 */
	public void setBus(Bus bus) {
		this.bus = (Bus) bus;
	}

	/**
	 * @param busStopRouteName the busStopRouteName to set
	 */
	public void setBusStopRouteName(String name) {
		this.busStopRouteName = name;

	}

	/**
	 * @return the busStopRouteName
	 */
	public String getBusStopRouteName() {
		return busStopRouteName;
	}


}
