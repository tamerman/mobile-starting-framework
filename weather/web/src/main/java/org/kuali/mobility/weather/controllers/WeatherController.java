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

package org.kuali.mobility.weather.controllers;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.alerts.service.AlertsService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Controller 
@RequestMapping("/weather")
public class WeatherController {
    
    @Autowired
    private WeatherService weatherService;
    
	@Autowired
    private AdminService adminService;
	
	@Autowired
    private AlertsService alertsService;
    
    @Resource(name="kmeProperties")
    private Properties kmeProperties;
	
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model uiModel) {

    	
    	String viewName = "weather/index";
    	uiModel.addAttribute("forecast", weatherService.getWeatherForecast());
    	
    	String alias = "PUBLIC";
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

    	HomeScreen home = new HomeScreen();
    	if (user != null && user.getViewCampus() != null) {
    		alias = user.getViewCampus();
    	} 
    	
    	home = adminService.getHomeScreenByAlias(alias);
    	if (home == null) {
    		home = adminService.getHomeScreenByAlias("PUBLIC");
    	}
    	
    	List<HomeTool> copy = new ArrayList<HomeTool>(home.getHomeTools());    	
    	
    	Tool alerts = null;
    	for (HomeTool homeTool : copy) {
    		Tool tool = homeTool.getTool();
    		if ("alerts".equals(tool.getAlias())) {
    			int count = alertsService.findAlertCountByCampus(user.getViewCampus());
    			if (count > 0) {
    				tool.setBadgeCount(Integer.toString(count));
    				tool.setIconUrl("images/service-icons/srvc-alerts-red.png");
    			} else {
    				tool.setBadgeCount(null);
    				tool.setIconUrl("images/service-icons/srvc-alerts-green.png");
    			}
    			alerts = tool;
    			break;
    		}
    	}

    	uiModel.addAttribute("alertsTool", alerts);
    	
    	if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/weather/index";
    	}
        return viewName;
    }
        
    @RequestMapping(value = "/js/weather.js")
    public String getJavaScript(Model uiModel) {
        return "ui3/weather/js/weather";
    }
    
    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }
    
}
