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

package org.kuali.mobility.bus.entity;

import org.kuali.mobility.bus.util.ScheduledStopComparitor;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author joseswan
 */
@XmlRootElement(name="stop")
@XmlSeeAlso({ScheduledStop.class})
public class BusStop implements Serializable {

    private long id;

    private String name;

    private String latitude;
    private String longitude;

    private double distance;

    /*private String unit;*/
	private List<ScheduledStop> scheduledStop;

    public BusStop() {
        scheduledStop = new ArrayList<ScheduledStop>();
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void addSchedule(ScheduledStop newSchedule) {
        List<ScheduledStop> stops = getScheduledStop();
        stops.add(newSchedule);
        this.setScheduledStop(stops);
    }

    public boolean equals( Object o )
    {
        boolean isEqual = false;

        if( o != null && o instanceof BusStop )
        {
            if( getName() != null && getName().equalsIgnoreCase( ((BusStop)o).getName() ) )
            {
                isEqual = true;
            }
        }

        return isEqual;
    }

    public int hashCode()
    {
        return 41 + ( getName() == null ? 0 : getName().hashCode() );
    }

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distance = distance;
	}

    public List<ScheduledStop> getScheduledStop() {
        return scheduledStop;
    }

    public void setScheduledStop(List<ScheduledStop> scheduledStop) {
        Collections.sort(scheduledStop, new ScheduledStopComparitor());
        this.scheduledStop = scheduledStop;
    }
}
