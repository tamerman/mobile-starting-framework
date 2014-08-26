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

package org.kuali.mobility.grades.controllers;

import org.kuali.mobility.grades.entity.ModuleResults;
import org.kuali.mobility.grades.service.GradesService;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * Controller for the Grades tool
 *
 * @author Kuali Mobility Team
 */
@Controller
@RequestMapping("/grades")
public class GradesController {

	/**
	 * A reference to the <code>GradesService</code>
	 */
	@Autowired
	@Qualifier("gradesService")
	private GradesService service;

	/**
	 * A reference to the KME properties
	 */
	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	/**
	 * Default controller for the Grades tool
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(Model uiModel, HttpServletRequest request) {
		User user = (User) request.getSession(true).getAttribute(AuthenticationConstants.KME_USER_KEY);
//		if(user.isPublicUser()){
//			uiModel.addAttribute("title", "grades.loginRequired");
//			return "grades/error";
//		}
		if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			return "ui3/grades/index";
		}
		return "grades/index";
	}

	/**
	 * Controller for when the user is requesting the results
	 *
	 * @throws ParseException
	 */
	@RequestMapping(value = "viewResults", method = RequestMethod.POST)
	public String viewExams(Model uiModel,
							@RequestHeader(required = false, value = "User-Agent", defaultValue = "unknown") String userAgent,
							//@RequestParam("startDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
							//@RequestParam("endDate")   @DateTimeFormat(pattern="yyyy-MM-dd")  Date endDate,
							@RequestParam("startDate") String startDateString,
							@RequestParam("endDate") String endDateString,
							HttpServletRequest request) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(startDateString);
		Date endDate = sdf.parse(endDateString);

		List<ModuleResults> results = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		Locale lang = localeResolver.resolveLocale(request);
		results = this.service.getResults(startDate, endDate, user.getLoginName(), lang.getLanguage());

		// Add to uiModel
		uiModel.addAttribute("results", results);
		uiModel.addAttribute("startDate", startDateString);
		uiModel.addAttribute("endDate", endDateString);
		uiModel.addAttribute("studentNum", user.getLoginName());
		return "grades/viewGrades";
	}

	/**
	 * Sets the reference to the grades service
	 *
	 * @param service
	 */
	public void setService(GradesService service) {
		this.service = service;
	}


	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
