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

import org.kuali.mobility.push.entity.Preference;
import org.kuali.mobility.push.service.rest.pojo.ListResponse;
import org.kuali.mobility.push.service.rest.pojo.PreferenceResponse;
import org.kuali.mobility.push.service.rest.pojo.PreferencesResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public interface PreferenceServiceRest {

	/**
	 * Find and return preferences for a given username.
	 *
	 * @param username Username of the user for which the preferences are being searched.
	 * @return List of <code>Preference</code> objects for a given user.
	 */
	@GET
	@Path("/findPreferencesByUsername")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	PreferencesResponse findPreferencesByUsername(@QueryParam(value = "username") String username);

	/**
	 * Find and return preferences for a given username.
	 *
	 * @param username  Username of the user for which the preferences are being searched.
	 * @param shortName Shortname of the <code>Sender</code> for which a preference is sought.
	 * @return A <code>Preference</code> objects for a given user and sender shortname.
	 */
	@GET
	@Path("/findPreferenceByUsernameAndShortname")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	PreferenceResponse findPreference(@QueryParam(value = "username") String username, @QueryParam(value = "shortName") String shortName);

	/**
	 * Find and return preferences for a given username.
	 *
	 * @param username Username of the user for which the preferences are being searched.
	 * @param senderId Id of the <code>Sender</code> for which a preference is sought.
	 * @return A <code>Preference</code> objects for a given user and sender shortname.
	 */
	@GET
	@Path("/findPreferenceByUsernameAndSenderId")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	PreferenceResponse findPreference(@QueryParam(value = "username") String username, @QueryParam(value = "senderId") Long senderId);

	/**
	 * Find and return preferences for a given username.
	 *
	 * @param id Id for the preference being sought.
	 * @return A <code>Preference</code> objects for a given user.
	 */
	@GET
	@Path("/findPreferenceById")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	PreferenceResponse findPreference(@QueryParam(value = "id") long id);


	/**
	 * Finds a list of users that alows a sender to send messages
	 *
	 * @param senderKey key of the sender
	 * @return A list of usernames that allowed the sender
	 */
	@GET
	@Path("/findUsersThatAllowedSender")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	ListResponse<String> findUsersThatAllowedSender(@QueryParam(value = "senderKey") String senderKey);


	/**
	 * Persists a <code>Preference</code> object.
	 *
	 * @param preference Preference to persist.
	 */
	@POST
	@Path("/savePreference")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	PreferenceResponse savePreference(Preference preference);

	/**
	 * Removes a <code>Sender</code> object.
	 *
	 * @param preferenceId Id of the sender to remove
	 */
	@DELETE
	@Path("/removePreference")
	boolean removePreference(@QueryParam(value = "id") Long preferenceId);
}
