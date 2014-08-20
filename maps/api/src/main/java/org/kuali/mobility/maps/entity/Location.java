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

package org.kuali.mobility.maps.entity;

import flexjson.JSONSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/*
 * Modifying the Location object? Remember to update the copy method.
 */
@XmlRootElement(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = -2588912315204722978L;

    private String id;
    private String name;
    private String description;
    private String streetNumber;
    private String streetDirection;
    private String street;
    private String city;
    private String state;
    private String zip;
    private Double latitude;
    private Double longitude;
    private boolean active;

    public Location() {}

    public Location(String id, String name, String description, String street, String city, String state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.street = street;
        this.city = city;
        this.state = state;
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreetDirection() {
        return streetDirection;
    }

    public void setStreetDirection(String streetDirection) {
        this.streetDirection = streetDirection;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Location copy() {
        Location location = new Location();
        location.setActive(this.active);
        if (city != null) {
            location.setCity(new String(city));
        }
        if (id != null) {
            location.setId(new String(this.id));
        }
        if (latitude != null) {
            location.setLatitude(new Double(this.latitude));
        }
        if (longitude != null) {
            location.setLongitude(new Double(this.longitude));
        }
        if (name != null) {
            location.setName(new String(this.name));
        }
        if (description != null) {
            location.setDescription(new String(this.description));
        }
        if (state != null) {
            location.setState(new String(this.state));
        }
        if (street != null) {
            location.setStreet(new String(this.street));
        }
        if (zip != null) {
            location.setZip(new String(this.zip));
        }
        return location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
