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

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.kuali.mobility.push.entity.Push;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of the CXF Device Service
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 * @deprecated This class is moved to an external project "push-service"
 */
@Deprecated
@Service
public class CXFPushService {

	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFPushService.class);

	private static final String RECIPIENTS = "recipients";

	/**
	 * A reference to the <code>PushService</code>.
	 */
	private PushService pushService;


	@GET
	@Path("/ping/get")
	public String pingGet() {
		return "{\"status\":\"OK\"}";
	}

	@POST
	@Path("/ping/post")
	public String pingPost() {
		return "{\"status\":\"OK\"}";
	}

	/**
	 * A CXF Service method for processing an HTTP. Post submitted push notification.
	 *
	 * @param data String - A JSON formatted string containing details for the push to send as well as the recipient users or devices ids.
	 * @return
	 */
	@POST
	@Path("/submit")
	public Response angularService(@RequestBody String data) {
		LOG.info("Angular Service : " + data);
		return service(data);
	}

	/**
	 * A CXF Service method for processing an HTTP.Post submitted push notification.
	 *
	 * @param data String - A JSON formatted string containing details for the push to send as well as the recipient users or devices ids.
	 * @return
	 */
	@POST
	@Path("/service")
	public Response formService(@FormParam(value = "data") String data) {
		LOG.info("FormService: " + data);
		return service(data);
	}

	private Response service(String data) {
		LOG.info("Service JSON: " + data);
		JSONObject queryParams;
		Push push = new Push();
		List<String> usernames = new ArrayList<String>();
		List<String> deviceIds = new ArrayList<String>();
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
			push.setTitle(queryParams.getString("title"));
			push.setMessage(queryParams.getString("message"));
			push.setUrl(queryParams.getString("url"));
			push.setSender(queryParams.getString("senderKey"));

			// If there a usernames, we will add them
			if (queryParams.has(RECIPIENTS)) {
				JSONArray usernamesJson = queryParams.getJSONArray(RECIPIENTS);
				Iterator<String> usernameIterator = usernamesJson.iterator();
				while (usernameIterator.hasNext()) {
					usernames.add(usernameIterator.next());
				}
			}

			// If there are device ids, we will use them
			if (queryParams.has("devices")) {
				JSONArray devicesJson = queryParams.getJSONArray("devices");
				Iterator<String> deviceIterator = devicesJson.iterator();
				while (deviceIterator.hasNext()) {
					deviceIds.add(deviceIterator.next());
				}
			}

		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		} catch (ClassCastException cce) {
			LOG.error(cce.getLocalizedMessage(), cce);
			return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).build();
		}

		pushService.sendPush(push, usernames, deviceIds);

		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	/**
	 * CXF Service method used by devices informing KME that a message has been received
	 * on the device.
	 *
	 * @param data JSON containing the device id and notification id
	 * @return
	 */
	@GET
	@Path("/received")
	public Response receivedNotification(@QueryParam(value = "data") String data) {
		// TODO mark a message as received
		return Response.status(Response.Status.OK.getStatusCode()).build();
	}

	@GET
	@Path("/getUserDetails")
	public String getUserDetails(@QueryParam("pushId") final String id) {
		String value = "";
		try {
			Push push = pushService.findPushById(Long.parseLong(id));
			value = "pushJSON('" + JSONSerializer.toJSON(push).toString() + "');";
		} catch (NumberFormatException nfe) {
			LOG.error("Number Format Exception: " + nfe.getMessage());
		}
		return value;
	}

	public void setPushService(PushService pushService) {
		this.pushService = pushService;
	}
}
