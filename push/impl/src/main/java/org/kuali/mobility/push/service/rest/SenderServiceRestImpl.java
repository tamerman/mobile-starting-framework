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

import org.kuali.mobility.push.entity.Sender;
import org.kuali.mobility.push.service.SenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the <code>IconsDao</code>
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class SenderServiceRestImpl implements SenderService {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(SenderServiceRestImpl.class);

	/**
	 * A reference to the rest servoce
	 */
	private SenderServiceRest restClient;

	@Override
	public List<Sender> findAllUnhiddenSenders() {
		try {
			return restClient.findAllUnhiddenSenders();
		} catch (Exception e) {
			LOG.error("Exception while trying to get senders", e);
			return new ArrayList<Sender>(0);
		}

	}

	public void setRestClient(SenderServiceRest restClient) {
		this.restClient = restClient;
	}
}
