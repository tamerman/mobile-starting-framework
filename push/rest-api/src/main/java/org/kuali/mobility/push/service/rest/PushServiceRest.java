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

import org.kuali.mobility.push.service.rest.pojo.PushConfigResponse;
import org.kuali.mobility.push.service.rest.pojo.PushResponse;
import org.kuali.mobility.push.service.rest.pojo.SendPushRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface PushServiceRest {


	/**
	 * Handles a request to send a push
	 *
	 * @param request
	 */
	@POST
	@Path("/sendPush")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PushResponse sendPush(SendPushRequest request);


	@GET
	@Path("/pushConfig")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PushConfigResponse getPushConfig();

	@GET
	@Path("/findPushById")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public PushResponse findPushById(@QueryParam(value = "id") Long pushId);
}
