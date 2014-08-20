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

package org.kuali.mobility.dining.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.dining.service.DiningService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Controller
@RequestMapping("/dining")
public class DiningController implements ApplicationContextAware{

	public static final Logger LOG = LoggerFactory.getLogger(DiningController.class);

    private ApplicationContext applicationContext;

	@Resource(name="diningService")
	private DiningService diningService;

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    @RequestMapping
    public String index(HttpServletRequest request, Model uiModel ) {
        String viewName = null;
        User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
        String campus = null;
        if (user.getViewCampus() == null) {
            viewName = "redirect:/campus?toolName=dining";
        } else if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/dining/index";
        } else {
            uiModel.addAttribute("diningHalls",getDiningService().getDiningHallGroups());
            viewName = "dining/index";
        }
        return viewName;
    }

    @RequestMapping(value="/hall/{name}")
    public String getDiningHall(
            @PathVariable("name") String name,
            HttpServletRequest request,
            Model uiModel) {
        uiModel.addAttribute("name",name);
        return "dining/menus_all";
    }

    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
            @PathVariable("key") String key,
            HttpServletRequest request,
            Model uiModel ) {
        return "ui3/dining/templates/"+key;
    }

    @RequestMapping(value = "/js/dining.js")
    public String getJavaScript(Model uiModel) {
        Properties properties = (Properties) getApplicationContext().getBean("computerLabProperties");
        if (properties != null) {
            uiModel.addAttribute( "groupLabs", properties.getProperty("computerlabs.groupLabs", "false"));
            uiModel.addAttribute( "useMaps", properties.getProperty("computerlabs.useMaps", "true"));
            uiModel.addAttribute( "useDetail", properties.getProperty("computerlabs.useDetail", "true"));
            uiModel.addAttribute( "groupSeats", properties.getProperty("computerlabs.groupSeats", "true"));
            uiModel.addAttribute( "feedStatus", properties.getProperty("computerlabs.feedStatus", "false"));
        }
        return "ui3/dining/js/dining";
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void setDiningService(DiningService diningService) {
        this.diningService = diningService;
    }

    public DiningService getDiningService() {
        return diningService;
    }

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }
}
