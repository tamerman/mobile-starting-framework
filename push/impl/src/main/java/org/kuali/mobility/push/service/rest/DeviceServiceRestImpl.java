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

package org.kuali.mobility.push.service.rest;

import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the <code>IconsDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class DeviceServiceRestImpl implements DeviceService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(DeviceServiceRestImpl.class);

	/**
	 * A reference to the rest client
	 */
	private DeviceServiceRest restClient;

	@Override
	public Device saveDevice(Device device) {
		try {
			return restClient.saveDevice(device).getDevice();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public Device registerDevice(Device device) {
		try {
			return restClient.registerDevice(device).getDevice();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public List<Device> getDevices() {
		try {
			return restClient.getDevices().getDevices();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return new ArrayList<Device>(0);
		}

	}

	@Override
	public Device findDeviceByDeviceId(String deviceId) {
		try {
			return restClient.findDeviceByDeviceId(deviceId).getDevice();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	public void setDeviceServiceRest(DeviceServiceRest restClient) {
		this.restClient = restClient;
	}

	public void setRestClient(DeviceServiceRest restClient) {
		this.restClient = restClient;
	}
}
