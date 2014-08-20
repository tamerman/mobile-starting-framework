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

package org.kuali.mobility.computerlabs.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.computerlabs.util.LabTransform;
import org.kuali.mobility.computerlabs.util.LocationTransform;

/**
 * An object representing a location for a computer lab. This most likely would
 * be a building. LocationImpl contains the name of the building and a
 * <code>List&lt;LabImpl&gt;</code> of labs or rooms in the building containing
 * available computers.
 *
 * @author Kuali Mobility Team
 */
@XmlRootElement(name = "location")
public class LocationImpl implements Serializable, Comparable<Location>, Location {

	private static final long serialVersionUID = -4991494626566555287L;
	private String name;
	@XmlElement(name = "labs")
	private List<LabImpl> labs;

	private static final LabTransform LAB_TRANSFORM = new LabTransform();

	public LocationImpl() {
		super();
		this.labs = new ArrayList<LabImpl>();
	}

	public LocationImpl(String name) {
		this.name = name;
		this.labs = new ArrayList<LabImpl>();
	}

	@Override
	public List<LabImpl> getLabs() {
		return labs;
	}

	@Override
	public void setLabs(List<? extends Lab> labs) {
		CollectionUtils.collect(labs, LAB_TRANSFORM, this.labs);
	}

	@Override
	public void addLab( Lab lab ) {
		this.labs.add( LAB_TRANSFORM.transform(lab) );
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(Location that) {
		if (this == null || that == null) {
			return -1;
		}
		if (this.getName() == that.getName()) {
			return 0;
		}
		return this.getName().compareTo(that.getName());
	}
}
