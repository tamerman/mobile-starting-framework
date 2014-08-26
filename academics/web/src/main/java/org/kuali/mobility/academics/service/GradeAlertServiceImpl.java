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

package org.kuali.mobility.academics.service;

import org.apache.cxf.annotations.Policies;
import org.apache.cxf.annotations.Policy;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.dao.GradesPostedNoticeDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.3.0
 */

public class GradeAlertServiceImpl implements GradeAlertService, ApplicationContextAware {
	private static final Logger LOG = LoggerFactory.getLogger(GradeAlertServiceImpl.class);
	private ApplicationContext context;
	@Context
	private MessageContext messageContext;
	private GradesPostedNoticeDao dao;


	@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
	@WebMethod(operationName = "publishGradeAlerts", action = "publishGradeAlerts")
	@Policies(
			@Policy(uri = "#UsernamePasswordPolicy",
					placement = Policy.Placement.BINDING_OPERATION_INPUT)
	)
	//@GET
	//@Path("/")
	@Override
	public String publishGradeAlerts(@WebParam(name = "uniqname") final List<String> uniqnameList) {
		String response;
		if (null != uniqnameList && !uniqnameList.isEmpty()) {
			response = "ok";
			LOG.debug("Graded:" + uniqnameList.size());
			for (String s : uniqnameList) {
				LOG.debug(s);

			}

			dao.uploadGradesPostedNotice(uniqnameList);
		} else {
			response = "error";
		}

		return response;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * @return the messageContext
	 */
	public MessageContext getMessageContext() {
		return messageContext;
	}

	/**
	 * @param messageContext the messageContext to set
	 */
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	public GradesPostedNoticeDao getDao() {
		return dao;
	}

	public void setDao(GradesPostedNoticeDao dao) {
		this.dao = dao;
	}
}
