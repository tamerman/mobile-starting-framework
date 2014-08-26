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

package org.kuali.mobility.push.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A class representing a Device registered on KME which is able to receive push notifications
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
@XmlRootElement
public class Device implements Serializable {

	/**
	 * Device type constant for Android devices
	 */
	public static final String TYPE_ANDROID = "Android";

	/**
	 * Devices type constant for iOS devices
	 */
	public static final String TYPE_IOS = "iOS";

	/**
	 * Device type constant for BlackBerry devices
	 */
	public static final String TYPE_BLACKBERRY = "BlackBerry";

	/**
	 * Device type constraint for Windows devices
	 */
	public static final String TYPE_WINDOWS = "WindowsMobile";

	/**
	 * Unique version UID for this class
	 */
	private static final long serialVersionUID = -4157056311695870783L;

	/**
	 * ID for this <code>Device</code> instance.
	 */
	private Long id;

	/**
	 * Type for ths <code>Device instance.
	 * The device type will be one of the following constants
	 * <ul>
	 * <li><code>Device.TYPE_ANDROID</code></li>
	 * <li><code>Device.TYPE_BLACKBERRY</code></li>
	 * <li><code>Device.TYPE_IOS</code></li>
	 * <li><code>Device.TYPE_WINDOWS</code></li>
	 * </ul>
	 */
	private String type;

	/**
	 * Registration ID for push notifications for this <code>Device</code> instance.
	 * <br>For Android it will be the Registration ID received from GCM.
	 * <br>For iOS it will be the Device Token received from APNS
	 * <br>For BlackBerry it will be the BlackBerry PIN (in HEX) retrieved from the device.
	 */
	private String regId;

	/**
	 * The name retrieved from the actual native device.
	 * <br>On iOS this is the user set name.
	 * <br>On Android an Blackberry it is the model name of the device
	 */
	private String deviceName;

	/**
	 * Username of the person to who this native device belongs
	 */
	private String username;

	/**
	 * Unique identifier for the native device.
	 */
	private String deviceId;

	/**
	 * The timestamp of the last update for this <code>Device</code> details.
	 */
	private Timestamp postedTimestamp;

	/**
	 * Version number for this <code>Device</code> record
	 */
	private Long versionNumber;

	/**
	 * Creates a new instance of a <code>Device</code>
	 */
	public Device() {
	}

	/**
	 * Gets the ID for this <code>Device</code>
	 *
	 * @return ID for this <code>Device</code>
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the ID for this <code>Device</code>
	 *
	 * @param id for this <code>Device</code>
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}

	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	@Override
	public String toString() {
		String newline = "\r\n";
		String str = "";
		str = str + newline + "ID        : " + this.getId();
		str = str + newline + "DeviceID  : " + this.getDeviceId();
		str = str + newline + "Username  : " + this.getUsername();
		str = str + newline + "DeviceName: " + this.getDeviceName();
		str = str + newline + "RegID     : " + this.getRegId();
		str = str + newline + "Type      : " + this.getType();
		str = str + newline + "Timestamp : " + this.getPostedTimestamp();
		return str;
	}

	/**
	 * A method to return a device as a JSON formatted string.
	 *
	 * @return String - JSON formatted.
	 */
	public String toJson() {
		String str = "{";
		str = str + "\"Id\":\"" + this.getId() + "\",";
		str = str + "\"deviceId\":\"" + this.getDeviceId() + "\",";
		if (this.getUsername() == null) {
			str = str + "\"username\":\"\",";
		} else {
			str = str + "\"username\":\"" + this.getUsername() + "\",";
		}
		str = str + "\"deviceName\":\"" + this.getDeviceName() + "\",";
		str = str + "\"regId\":\"" + this.getRegId() + "\",";
		str = str + "\"registrationDate\":\"" + this.getPostedTimestamp() + "\",";
		str = str + "\"registrationTimeStamp\":\"" + this.getPostedTimestamp().getTime() + "\",";
		str = str + "\"type\":\"" + this.getType() + "\"";
		str = str + "}";
		return str;
	}


	@Override
	public boolean equals(Object obj) {
		boolean match = false;

		if (obj == this) {
			match = true;
		} else if (!(obj instanceof Device)) {
			match = false;
		} else if (((Device) obj).hashCode() == this.hashCode()) {
			match = true;
		}

		return match;
	}

	@Override
	public int hashCode() {
		final int salt = 42;
		int code = salt;
		code += this.toString().hashCode();
		if (null != getDeviceId()) {
			code += getDeviceId().hashCode();
		}
		if (null != getDeviceId()) {
			code += getDeviceName().hashCode();
		}
		if (null != getDeviceId()) {
			code += getType().hashCode();
		}
		if (null != getDeviceId()) {
			code += getUsername().hashCode();
		}
		if (null != getDeviceId()) {
			code += getId().intValue();
		}
		return code;
	}
}
