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

package org.kuali.mobility.push.service;

import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.service.DeviceService;

import java.util.List;

/**
 * Blank implementation of the device service.
 * This is only usefull for unit tests, or KME implementations that does
 * not want to implement push notifications
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NoopDeviceServiceImpl implements DeviceService {

	@Override
	public Device saveDevice(Device device) {
		return null;
	}

	@Override
	public Device registerDevice(Device device) {
		return null;
	}

	@Override
	public List<Device> getDevices() {
		return null;
	}

	@Override
	public Device findDeviceByDeviceId(String deviceId) {
		return null;
	}
}
