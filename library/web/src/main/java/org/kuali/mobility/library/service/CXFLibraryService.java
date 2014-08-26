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

package org.kuali.mobility.library.service;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.library.entity.Library;
import org.kuali.mobility.library.entity.LibraryContactDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.text.ParseException;
import java.util.Map;

/**
 * Implementation of the CXF Library Service
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFLibraryService {


	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFLibraryService.class);

	@Autowired
	private LibraryService libraryService;

	/**
	 * A reference to the {@code MessageContext}
	 */
	@Context
	private MessageContext messageContext;

	/**
	 * Gets the contact details of a library
	 *
	 * @param libraryId
	 * @return
	 * @throws ParseException
	 */
	@GET
	@Path("/getLibraryContact/{libraryId}")
	public String getLibraryContact(@PathParam("libraryId") Long libraryId) throws ParseException {
		LibraryContactDetail lcd = libraryService.getLibrary(libraryId).getLibraryContactDetail();
		return new JSONSerializer().exclude("*.class", "*.version").deepSerialize(lcd);
	}

	/**
	 * Gets a library
	 *
	 * @param libraryId
	 * @return
	 * @throws ParseException
	 */
	@GET
	@Path("/getLibrary/{libraryId}")
	public String getLibrary(@PathParam(value = "libraryId") Long libraryId) throws ParseException {
		Library library = libraryService.getLibrary(libraryId);
		return new JSONSerializer().exclude("*.class", "*.version").deepSerialize(library);
	}

	@POST
	@Path("/updateContactDetails")
	public String saveLibraryContact(@RequestBody String body) {
		LibraryContactDetail requestLibrary = new JSONDeserializer<LibraryContactDetail>().deserialize(body, LibraryContactDetail.class);
		Map<String, Object> map = new JSONDeserializer<Map<String, Object>>().deserialize(body);
		Library originalLibrary = libraryService.getLibrary((Long) map.get("libraryId"));
		originalLibrary.setLibraryContactDetail(requestLibrary);
		originalLibrary = libraryService.saveLibrary(originalLibrary);
		return new JSONSerializer().exclude("*.class", "*.version").deepSerialize(originalLibrary);
	}

	/**
	 * Gets the reference to the {@LibraryService}.
	 *
	 * @return
	 */
	public LibraryService getLibraryService() {
		return libraryService;
	}

	/**
	 * Sets the reference to the {@GradesService}
	 *
	 * @param gradesService
	 */
	public void setLibraryService(LibraryService gradesService) {
		this.libraryService = gradesService;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}
}
