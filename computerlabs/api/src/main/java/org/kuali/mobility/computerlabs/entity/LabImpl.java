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

import org.kuali.mobility.shared.EncodingTypes;
import org.kuali.mobility.util.DigestConstants;
import org.kuali.mobility.util.DigestUtil;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * An object representing a computer lab.  This could be a room in a building on campus.
 *
 * @author Kuali Mobility Team
 */
@XmlRootElement(name = "lab")
public class LabImpl implements Serializable, Comparable<LabImpl>, Lab {

	private static final long serialVersionUID = -372599891985133670L;

	private String lab;
	private String floor;
	private String building;
	private String buildingCode;
	private String campus;
	private String availability;
	private String windowsAvailability;
	private String macAvailability;
	private String linuxAvailability;
	private String softwareAvailability;
	private String floorplan;

	private String labUID;

	public LabImpl() {
	}

	@Override
	public int compareTo(LabImpl that) {
		if (this.getCampus() == null || that == null || that.getCampus() == null) {
			return -1;
		}
		return this.getCampus().compareTo(that.getCampus());
	}

	@Override
	public boolean equals(Object object) {
		if (object != null) {
			LabImpl lab = (LabImpl) object;
			if ((((this.getCampus() == null || this.getCampus().equals("")) && (lab.getCampus() == null || lab.getCampus().equals(""))) || (this.getCampus() != null && this.getCampus().equals(lab.getCampus()))) &&
				(((this.getLab() == null || this.getLab().equals("")) && (lab.getLab() == null || lab.getLab().equals(""))) || ( this.getLab() != null && this.getLab().equals(lab.getLab()))) &&
				(((this.getBuilding() == null || this.getBuilding().equals("")) && (lab.getBuilding() == null || lab.getBuilding().equals(""))) || (this.getBuilding() != null && this.getBuilding().equals(lab.getBuilding()))) &&
				(((this.getBuildingCode() == null || this.getBuildingCode().equals("")) && (lab.getBuildingCode() == null || lab.getBuildingCode().equals(""))) || (this.getBuildingCode() != null && this.getBuildingCode().equals(lab.getBuildingCode()))) &&
				(((this.getAvailability() == null || this.getAvailability().equals("")) && (lab.getAvailability() == null || lab.getAvailability().equals(""))) || (this.getAvailability() != null && this.getAvailability().equals(lab.getAvailability()))) &&
				(((this.getMacAvailability() == null || this.getMacAvailability().equals("")) && (lab.getMacAvailability() == null || lab.getMacAvailability().equals(""))) || (this.getMacAvailability() != null && this.getMacAvailability().equals(lab.getMacAvailability()))) &&
				(((this.getSoftwareAvailability() == null || this.getSoftwareAvailability().equals("")) && (lab.getSoftwareAvailability() == null || lab.getSoftwareAvailability().equals(""))) || (this.getSoftwareAvailability() != null && this.getSoftwareAvailability().equals(lab.getSoftwareAvailability()))) &&
				(((this.getFloorplan() == null || this.getFloorplan().equals("")) && (lab.getFloorplan() == null || lab.getFloorplan().equals(""))) || (this.getFloorplan() != null && this.getFloorplan().equals(lab.getFloorplan()))) &&
				(((this.getWindowsAvailability() == null || this.getWindowsAvailability().equals("")) && (lab.getWindowsAvailability() == null || lab.getWindowsAvailability().equals(""))) || (this.getWindowsAvailability() != null && this.getWindowsAvailability().equals(lab.getWindowsAvailability())))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getLab() {
		return lab;
	}

	@Override
	public void setLab(String lab) {
		this.lab = lab;
	}

	@Override
	public String getFloor() {
		return floor;
	}

	@Override
	public void setFloor(String floor) {
		this.floor = floor;
	}

	@Override
	public String getBuilding() {
		return building;
	}

	@Override
	public void setBuilding(String building) {
		this.building = building;
	}

	@Override
	public String getBuildingCode() {
		return buildingCode;
	}

	@Override
	public void setBuildingCode(String buildingCode) {
		this.buildingCode = buildingCode;
	}

	@Override
	public String getCampus() {
		return campus;
	}

	@Override
	public void setCampus(String campus) {
		this.campus = campus;
	}

	@Override
	public String getAvailability() {
		return availability;
	}

	@Override
	public void setAvailability(String availability) {
		this.availability = availability;
	}

	@Override
	public String getWindowsAvailability() {
		return windowsAvailability;
	}

	@Override
	public void setWindowsAvailability(String windowsAvailability) {
		this.windowsAvailability = windowsAvailability;
	}

	@Override
	public String getMacAvailability() {
		return macAvailability;
	}

	@Override
	public void setMacAvailability(String macAvailability) {
		this.macAvailability = macAvailability;
	}

	@Override
	public String getLinuxAvailability() {
		return linuxAvailability;
	}

	@Override
	public void setLinuxAvailability(String linuxAvailability) {
		this.linuxAvailability = linuxAvailability;
	}

	@Override
	public String getSoftwareAvailability() {
		return softwareAvailability;
	}

	@Override
	public void setSoftwareAvailability(String softwareAvailability) {
		this.softwareAvailability = softwareAvailability;
	}

	@Override
	public String getFloorplan() {
		return floorplan;
	}

	@Override
	public void setFloorplan(String floorplan) {
		this.floorplan = floorplan;
	}

	/**
	 * @return the labUID
	 */
	public String getLabUID() {
		if( null == labUID ) {
			this.setLabUID(null);
		}
		return labUID;
	}

	/**
	 * @param labUID the labUID to set
	 */
	public void setLabUID(String labUID) {
		this.labUID = DigestUtil.getDigest( getCampus() + getBuildingCode() + getLab(), DigestConstants.SHA1, EncodingTypes.HEX );
	}

}
