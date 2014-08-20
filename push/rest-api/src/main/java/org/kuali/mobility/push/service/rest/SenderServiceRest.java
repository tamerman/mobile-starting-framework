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

package org.kuali.mobility.push.service.rest;

import org.kuali.mobility.push.entity.Sender;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Interface for retrieving and persisting <code>Sender</code> for the purpose of sending
 * <code>Push</code> messages to <code>Device</code>.
 *
 * Implementations of this service will typically provide for the access and checking of 
 * the status of a sender and provide for opt-out/opt-in push preferences to the user. 
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.0.0
 */
public interface SenderServiceRest {

	/**
	 * Find and return all available <code>Sender</code> objects, useful for opt-in/opt-out preferences.
	 * @return List of all <code>Sender</code> objects whom are not set to be hidden.
	 */
	@GET
	@Path("/findAllUnhiddenSenders")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	List<Sender> findAllUnhiddenSenders();

}
