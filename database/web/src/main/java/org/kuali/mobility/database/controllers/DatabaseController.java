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

package org.kuali.mobility.database.controllers;

import org.apache.commons.io.IOUtils;
import org.kuali.mobility.database.entity.DatabaseSchemaOutputForm;
import org.kuali.mobility.database.service.DatabaseService;
import org.kuali.mobility.database.validators.DatabaseSchemaOutputFormValidator;
import org.kuali.mobility.security.authn.util.AuthenticationConstants;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.user.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/database")
public class DatabaseController {

	private static Logger LOG = LoggerFactory.getLogger(DatabaseController.class);

	@Autowired
	private DatabaseService service;

	public void setDatabaseService(DatabaseService service) {
		this.service = service;
	}

	private static final Map<String, String> dialectTypes;

	static {
		dialectTypes = new LinkedHashMap<String, String>();
		dialectTypes.put("", "Select a Database Type");
		dialectTypes.put("org.hibernate.dialect.Oracle10gDialect", "Oracle10g");
//    	dialectTypes.put("org.kuali.mobility.database.entity.KMEOracleDialect", "Oracle");
		dialectTypes.put("org.hibernate.dialect.MySQL5Dialect", "MySQL5");
//    	dialectTypes.put("org.kuali.mobility.database.entity.KMEMySql5Dialect", "MySQL5");
		dialectTypes.put("org.hibernate.dialect.SQLServerDialect", "SQL Server");
		dialectTypes.put("org.hibernate.dialect.PostgreSQLDialect", "PostgreSQL");
		dialectTypes.put("org.hibernate.dialect.DerbyDialect", "Derby");
		dialectTypes.put("org.hibernate.dialect.H2Dialect", "H2");
//    	dialectTypes.put("", "");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model uiModel, HttpServletRequest request) {
//    	String schema = service.getSchema();
//    	uiModel.addAttribute("schema", schema);
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATORS", request)) {
			viewName = "redirect:/errors/401.jsp";
			LOG.info("Redirecting to: " + viewName);
			return viewName;
		} else {
			DatabaseSchemaOutputForm form = new DatabaseSchemaOutputForm();
			uiModel.addAttribute("form", form);
			uiModel.addAttribute("dialectTypes", dialectTypes);
			return "database/schemaoutputform";
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getSchema(HttpServletRequest request, Model uiModel, @ModelAttribute("form") DatabaseSchemaOutputForm form, BindingResult result) {

		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATORS", request)) {
			viewName = "redirect:/errors/401.jsp";
			LOG.info("Redirecting to: " + viewName);
			return viewName;
		}

		DatabaseSchemaOutputFormValidator validator = new DatabaseSchemaOutputFormValidator();
		validator.validate(form, result);

		if (result.hasErrors()) {
			uiModel.addAttribute("dialectTypes", dialectTypes);
			return "database/schemaoutputform";
		} else {
			String schema = this.getSchema(form.getDialectType(), form.getDelimiter(), form.isNewLineBeforeDelimiter(), form.isRemoveForeignKeys());
			uiModel.addAttribute("schema", schema);
			uiModel.addAttribute("dialect", form.getDialectType());
			uiModel.addAttribute("delimiter", form.getDelimiter());
			return "database/schemaoutput";
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "download")
	public String getSchemaDownload(HttpServletRequest request, Model uiModel, @ModelAttribute("form") DatabaseSchemaOutputForm form, BindingResult result, HttpServletResponse response) {
		String viewName;
		if (!isAllowedAccess("KME-ADMINISTRATORS", request)) {
			viewName = "redirect:/errors/401.jsp";
			LOG.info("Redirecting to: " + viewName);
			return viewName;
		}

		DatabaseSchemaOutputFormValidator validator = new DatabaseSchemaOutputFormValidator();
		validator.validate(form, result);

		if (result.hasErrors()) {
			uiModel.addAttribute("dialectTypes", dialectTypes);
			return "database/schemaoutputform";
		} else {
			try {
				response.setContentType("text/plain");
				response.setHeader("Content-Disposition", "attachment;filename=schema.ddl");
				String schema = this.getSchema(form.getDialectType(), form.getDelimiter(), form.isNewLineBeforeDelimiter(), form.isRemoveForeignKeys());
				InputStream is = new ByteArrayInputStream(schema.getBytes("UTF-8"));
				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
			} catch (IOException ex) {
				// log.info("Error writing file to output stream. Filename was '" +
				// fileName + "'");
				throw new RuntimeException("IOError writing file to output stream");
			}
			return null;
		}
	}

    /*
    @RequestMapping(value="/schema", method = RequestMethod.GET)
	@ResponseBody
	public String getSchema(@RequestParam(value = "dialect", required = true) String dialect, @RequestParam(value = "delimiter", required = true) String delimiter, HttpServletRequest request) {
//		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String schema = service.getSchema(dialect, delimiter);
		return schema;
	}
	*/
    
    /*
    @RequestMapping(value = "/schema2", method = RequestMethod.GET)
    public void getFile(@RequestParam(value = "dialect", required = true) String dialect, @RequestParam(value = "delimiter", required = true) String delimiter, @RequestParam(value = "newline", required = true) boolean newLine, HttpServletResponse response) {
		try {
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition", "attachment;filename=schema.ddl");
			String schema = this.getSchema(dialect, delimiter, newLine);
			InputStream is = new ByteArrayInputStream(schema.getBytes("UTF-8"));
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			// log.info("Error writing file to output stream. Filename was '" +
			// fileName + "'");
			throw new RuntimeException("IOError writing file to output stream");
		}
    }
    */

	private String getSchema(String dialect, String delimiter, boolean newLine, boolean overrideAlterTable) {
		if (newLine) {
			delimiter = "\n" + delimiter;
		}
		String schema = service.getSchema(dialect, delimiter, overrideAlterTable);
		return schema;
	}

	public boolean isAllowedAccess(String roleName, HttpServletRequest request) {
		boolean isAllowed = false;

		if (roleName == null || roleName.isEmpty()) {
			isAllowed = true;
		} else if (request.getSession() == null) {
			LOG.info("Request Session NULL");
			isAllowed = false;
		} else {
			User user = (User) request.getSession().getAttribute(AuthenticationConstants.KME_USER_KEY);
			if (user == null || user.isPublicUser()) {
				LOG.info("User NULL or Public");
				isAllowed = false;
			} else if (user.getGroups() == null || user.getGroups().isEmpty()) {
				LOG.info("UserGrous NULL or isEmpty()");
				isAllowed = false;
			} else {
				for (Group group : user.getGroups()) {
					LOG.info(group.getName());
					if (group.getName().equalsIgnoreCase(roleName)) {
						isAllowed = true;
						break;
					}
				}
			}
		}
//        return true;
		return isAllowed;
	}


}
