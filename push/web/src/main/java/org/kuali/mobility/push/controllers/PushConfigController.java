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

package org.kuali.mobility.push.controllers;

import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.push.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.kuali.mobility.push.entity.PushConfigConstants.*;

 /**
 * Controller to write out a PushConfig.js file containing the configuration for push notifications.
 *
 * @deprecated This class is moved to an external project "push-service"
 * @author Kuali Mobility Team {mobility.dev@kuali.org}
 * @since 2.2.0
 */
@Deprecated
@Controller
public class PushConfigController {

	@Autowired
	private PushService pushService;

	/**
	 * Request to get the PushConfig.js file
	 * @param platform
	 * @param model
	 * @return
	 */
	@RequestMapping(value="js/PushConfig.js", produces={"text/javascript"})
	public String getPushConfig(
		@CookieValue("platform") String platform,
		Model model){
		
		if (Device.TYPE_BLACKBERRY.equalsIgnoreCase(platform)){
			this.getBlackberryPushConfig(model);
		}
		else if (Device.TYPE_ANDROID.equalsIgnoreCase(platform)){
			this.getAndroidPushConfig(model);
		}
		// TODO implement caching headers to cache the file
		return "push/js/PushConfig";
	}
	
	/**
	 * Update the model for the Android PushConfig.
	 * @param model
	 */
	private void getAndroidPushConfig(Model model) {
		Map<String, String> map = pushService.getPushConfig();
		model.addAttribute("applicationId", map.get(ANDROID_SENDER_ID));
	}

	/**
	 * Update the model for the Blackberry PushConfig.
	 * @param model
	 */
	private void getBlackberryPushConfig(Model model){
		Map<String, String> map = pushService.getPushConfig();
		model.addAttribute("applicationId", map.get(BLACKBERRY_APPLICATION_ID));
		model.addAttribute("registrationUrl", map.get(BLACKBERRY_PUSH_URL));
		model.addAttribute("port", map.get(BLACKBERRY_PUSH_PORT));
	}


}
