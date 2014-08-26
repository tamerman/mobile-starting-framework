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

package org.kuali.mobility.grades.service;

import flexjson.JSONSerializer;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.grades.entity.ModuleResults;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Implementation of the CXF Grades Service
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFGradesService {


	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFGradesService.class);

	@Autowired
	private GradesService gradesService;

	/**
	 * A reference to the {@code MessageContext}
	 */
	@Context
	private MessageContext messageContext;


	@Autowired
	private LocaleResolver localeResolver;

	@GET
	@Path("/getModuleResults")
	public String getResults(@QueryParam("startDate") String startDateString, @QueryParam("endDate") String endDateString) throws ParseException {

		HttpServletRequest request = (HttpServletRequest) this.getMessageContext().getHttpServletRequest();
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(startDateString);
		Date endDate = sdf.parse(endDateString);

		Locale lang = localeResolver.resolveLocale(request);

		List<ModuleResults> results = gradesService.getResults(startDate, endDate, user.getLoginName(), lang.getLanguage());
		return new JSONSerializer().exclude("*.class").deepSerialize(results);
	}

	/**
	 * Gets the reference to the {@GradesService}.
	 *
	 * @return
	 */
	public GradesService getGradesService() {
		return gradesService;
	}

	/**
	 * Sets the reference to the {@GradesService}
	 *
	 * @param gradesService
	 */
	public void setGradesService(GradesService gradesService) {
		this.gradesService = gradesService;
	}

	public MessageContext getMessageContext() {
		return messageContext;
	}

	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}
}
