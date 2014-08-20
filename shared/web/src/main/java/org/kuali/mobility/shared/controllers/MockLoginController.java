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

package org.kuali.mobility.shared.controllers;

import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.LoginService;
import org.kuali.mobility.shared.entity.MockUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.Formatter;

@Controller
@RequestMapping("/mocklogin")
public class MockLoginController {

	private static final Logger LOG = LoggerFactory.getLogger(MockLoginController.class);

	@Autowired
	private LoginService loginService;

	@RequestMapping(method = RequestMethod.GET)
	public String mockLogin(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		MockUser mockUser = (MockUser) request.getSession().getAttribute(Constants.KME_MOCK_USER_KEY);

		if (mockUser != null) {
			uiModel.addAttribute("mockuser", mockUser);
		} else {
			uiModel.addAttribute("mockuser", new MockUser());
		}
		return "mocklogin";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String submit(HttpServletRequest request, HttpServletResponse response, Model uiModel, @ModelAttribute("mockuser") MockUser mockUser, BindingResult result) {
		if (isValidQuery(mockUser, result)) {
			request.getSession().setAttribute(Constants.KME_MOCK_USER_KEY, mockUser);

			String userName = mockUser.getUserId();
			String password = mockUser.getPassword();

			if (loginService.isValidLogin(userName, password)) {
				return "redirect:/home";
			} else {
				return "mocklogin";
			}

		} else {
			return "mocklogin";
		}
	}

	private boolean isValidQuery(MockUser mockUser, BindingResult result) {
		boolean hasErrors = false;
		Errors errors = ((Errors) result);
		if (!loginService.isValidUser(mockUser.getUserId())) {
			errors.rejectValue("userId", "", "Please enter a registered username");
			hasErrors = true;
		}
		if (mockUser.getUserId() == null || "".equals(mockUser.getUserId().trim())) {
			errors.rejectValue("userId", "", "Please enter a username");
			hasErrors = true;
		}
		if (mockUser.getPassword() == null || "".equals(mockUser.getPassword().trim())) {
			errors.rejectValue("password", "", "Please enter a Password");
			hasErrors = true;
		}
		return !hasErrors;
	}

	public static String calculateHash(MessageDigest algorithm,
	                                   String message) throws Exception {

		algorithm.update(message.getBytes());

		byte[] hash = algorithm.digest();

		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

}
