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

import org.kuali.mobility.push.entity.Push;
import org.kuali.mobility.push.service.PushService;
import org.kuali.mobility.push.service.rest.pojo.SendPushRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the <code>IconsDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class PushServiceRestImpl implements PushService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PushServiceRestImpl.class);

	/**
	 * Reference to the rest client
	 */
	private PushServiceRest restClient;

	@Override
	public Push sendPush(Push push, List<String> usernames, List<String> deviceIds) {
		SendPushRequest spr = new SendPushRequest();
		spr.setUsernames(usernames);
		spr.setRegistrationIds(deviceIds);
		spr.setPush(push);
		try {
			return restClient.sendPush(spr).getPush();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	@Override
	public Map<String, String> getPushConfig() {
		try {
			return restClient.getPushConfig().getMap();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return new HashMap<String, String>(0);
		}
	}

	@Override
	public Push findPushById(Long pushId) {
		try {
			return restClient.findPushById(pushId).getPush();
		} catch (Exception e) {
			LOG.error("Exception while calling remote service", e);
			return null;
		}
	}

	public void setRestClient(PushServiceRest restClient) {
		this.restClient = restClient;
	}
}
