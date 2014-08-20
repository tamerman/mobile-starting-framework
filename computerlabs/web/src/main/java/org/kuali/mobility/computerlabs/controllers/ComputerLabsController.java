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

package org.kuali.mobility.computerlabs.controllers;

import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.service.ComputerLabsService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Controller
@RequestMapping("/computerlabs")
public class ComputerLabsController implements ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(ComputerLabsController.class);
	private ApplicationContext applicationContext;

	@Resource(name="computerLabService")
	private ComputerLabsService computerLabService;

    @Resource(name="kmeProperties")
    private Properties kmeProperties;

    @Resource(name="computerLabProperties")
    private Properties computerLabProperties;

    @RequestMapping(method = RequestMethod.GET)
	public String viewCampus(HttpServletRequest request, Model uiModel) {
		String viewName = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			viewName = "redirect:/campus?toolName=computerlabs";
        } else if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/computerlabs/index";
		} else {
			campus = user.getViewCampus();
			if (getComputerLabProperties() != null) {
				String groupLabs = getComputerLabProperties().getProperty("computerlabs.groupLabs");
				if (groupLabs != null
						&& "true".equalsIgnoreCase(groupLabs)) {
					List<? extends LabGroup> groups = getComputerLabService().getLabGroups();
					uiModel.addAttribute("labGroups", groups);
					viewName = "computerlabs/groups";
				} else {
					viewName = "redirect:/computerlabs/list";
				}
			} else {
				viewName = "redirect:/computerlabs/list";
			}
			LOG.debug("Computerlabs campus different " + user.getViewCampus());
			uiModel.addAttribute("campus", campus);
		}
		return viewName;
	}

	@RequestMapping(value = "/list")
	public String getList(Model uiModel, HttpServletRequest request,
		@RequestParam(required = false) String groupId ) {
		String viewName = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			viewName = "redirect:/campus?toolName=computerlabs";
		} else {
			campus = user.getViewCampus();
            String filteredCampus = (String)request.getSession().getAttribute("campus");
			uiModel.addAttribute("campus", filteredCampus);
			LOG.debug("Computerlabs campus different " + user.getViewCampus());

			LabGroup group = getComputerLabService().getLabGroup((groupId == null ? filteredCampus : groupId));
			uiModel.addAttribute("group", group);

			if (getComputerLabProperties() != null) {
				String groupLabs = getComputerLabProperties().getProperty("computerlabs.groupLabs");
				if (groupLabs != null
						&& "true".equalsIgnoreCase(groupLabs)) {
					uiModel.addAttribute("pageTitle", group.getName());
				}
				uiModel.addAttribute( "useMaps", getComputerLabProperties().getProperty("computerlabs.useMaps", "true"));
				uiModel.addAttribute( "useDetail", getComputerLabProperties().getProperty("computerlabs.useDetail", "true"));
				uiModel.addAttribute( "groupSeats", getComputerLabProperties().getProperty("computerlabs.groupSeats", "true"));
				uiModel.addAttribute( "feedStatus", getComputerLabProperties().getProperty("computerlabs.feedStatus", "false"));
			}
			viewName = "computerlabs/list";
		}
		return viewName;
	}
	
	@RequestMapping(value = "/feeds")
    public String retrieveAndSaveSpreadsheetDataAsXML(Model uiModel,
                                     HttpServletRequest request) {
        String feedURL = getComputerLabProperties().getProperty("computerlabs.feedURL");
        getComputerLabService().retrieveAndSaveSpreadsheetDataAsXML(feedURL);
        return "computerlabs/list";
    }

	@RequestMapping(value = "/details")
	public String getViewSeatDetails(Model uiModel,
		HttpServletRequest request,
		@RequestParam(required = true) String labUid) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if( user != null ) {
			campus = user.getViewCampus();
		}
		Lab lab = (Lab)getComputerLabService().getLab(labUid);
		uiModel.addAttribute("lab", lab);
		uiModel.addAttribute("campus", campus);
		return "computerlabs/details";
	}

    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
            @PathVariable("key") String key,
            HttpServletRequest request,
            Model uiModel ) {
        if (getComputerLabProperties() != null) {
            uiModel.addAttribute( "useMaps", getComputerLabProperties().getProperty("computerlabs.useMaps", "true"));
            uiModel.addAttribute( "useDetail", getComputerLabProperties().getProperty("computerlabs.useDetail", "true"));
        }
        return "ui3/computerlabs/templates/"+key;
    }

    @RequestMapping(value = "/js/computerlabs.js")
    public String getJavaScript(Model uiModel) {
        if (getComputerLabProperties() != null) {
            uiModel.addAttribute( "groupLabs", getComputerLabProperties().getProperty("computerlabs.groupLabs", "false"));
            uiModel.addAttribute( "useMaps", getComputerLabProperties().getProperty("computerlabs.useMaps", "true"));
            uiModel.addAttribute( "useDetail", getComputerLabProperties().getProperty("computerlabs.useDetail", "true"));
            uiModel.addAttribute( "groupSeats", getComputerLabProperties().getProperty("computerlabs.groupSeats", "true"));
            uiModel.addAttribute( "feedStatus", getComputerLabProperties().getProperty("computerlabs.feedStatus", "false"));
        }
        return "ui3/computerlabs/js/computerlabs";
    }

	/**
	 * @return the computerLabService
	 */
	public ComputerLabsService getComputerLabService() {
		return computerLabService;
	}

	/**
	 * @param computerLabService the computerLabService to set
	 */
	public void setComputerLabService(ComputerLabsService computerLabService) {
		this.computerLabService = computerLabService;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }

    public Properties getComputerLabProperties() {
        return computerLabProperties;
    }

    public void setComputerLabProperties(Properties computerLabProperties) {
        this.computerLabProperties = computerLabProperties;
    }
}
