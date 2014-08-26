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


package org.kuali.mobility.maps.entity;

import flexjson.JSONSerializer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "mapGroup")
public class MapsGroup implements Serializable {

	private static final long serialVersionUID = -4775149005202188253L;

	private String id;

	private String name;

	private boolean active;

	@XmlElement(name = "location")
	private Set<Location> mapsLocations;

	@XmlElement(name = "mapGroup")
	private Set<MapsGroup> mapsGroupChildren;

	public MapsGroup() {
		mapsLocations = new HashSet<Location>();
		mapsGroupChildren = new HashSet<MapsGroup>();
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").include("mapsLocations").serialize(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@XmlElement(name = "locations")
	public Set<Location> getMapsLocations() {
		return mapsLocations;
	}

	public void setMapsLocations(Set<Location> mapsLocations) {
		this.mapsLocations = mapsLocations;
	}

	@XmlElement(name = "mapGroupChildren")
	public Set<MapsGroup> getMapsGroupChildren() {
		return mapsGroupChildren;
	}

	public void setMapsGroupChildren(Set<MapsGroup> mapsGroupChildren) {
		this.mapsGroupChildren = mapsGroupChildren;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
