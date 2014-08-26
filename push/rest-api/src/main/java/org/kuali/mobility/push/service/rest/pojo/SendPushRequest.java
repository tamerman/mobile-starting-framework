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

package org.kuali.mobility.push.service.rest.pojo;

import org.kuali.mobility.push.entity.Push;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a request to send a push notification
 * to a collection of users and/or devices.
 */
@XmlRootElement
public class SendPushRequest {

	/**
	 * The push message to send
	 */
	private Push push;

	/**
	 * Usernames to send the push notification to
	 */

	private ArrayList<String> usernames;

	/**
	 * Device Ids to send the push notification to
	 */

	private ArrayList<String> deviceIds;


	@XmlElementWrapper(name = "usernames")
	@XmlElement(name = "username")
	public ArrayList<String> getUsernames() {
		if (usernames == null) {
			usernames = new ArrayList<String>();
		}
		return usernames;
	}

	@XmlElementWrapper(name = "deviceIds")
	@XmlElement(name = "deviceId")
	public ArrayList<String> getDeviceIds() {
		if (deviceIds == null) {
			deviceIds = new ArrayList<String>();
		}
		return deviceIds;
	}


	public void setUsernames(List<String> usernames) {
		if (usernames == null) {
			this.usernames = new ArrayList<String>(0);
		} else if (usernames instanceof ArrayList) {
			this.usernames = (ArrayList<String>) usernames;
		} else {
			this.usernames = new ArrayList<String>(usernames);
		}
	}


	public void setRegistrationIds(List<String> deviceIds) {
		if (deviceIds == null) {
			this.deviceIds = new ArrayList<String>(0);
		} else if (deviceIds instanceof ArrayList) {
			this.deviceIds = (ArrayList<String>) deviceIds;
		} else {
			this.deviceIds = new ArrayList<String>(deviceIds);
		}
	}

	public Push getPush() {
		return push;
	}

	public void setPush(Push push) {
		this.push = push;
	}

}
