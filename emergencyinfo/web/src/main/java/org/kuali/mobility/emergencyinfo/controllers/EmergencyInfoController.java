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

package org.kuali.mobility.emergencyinfo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.service.EmergencyInfoService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;


@Controller
@RequestMapping("/emergencyinfo")
public class EmergencyInfoController {

	private static final Logger LOG = LoggerFactory.getLogger( EmergencyInfoController.class );
	
    @Resource(name="kmeProperties")
    private Properties kmeProperties;
	
    @Autowired
    private EmergencyInfoService emergencyInfoService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String getList(Model uiModel, HttpServletRequest request) {
    	String viewName = "emergencyinfo/list";
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String selectedCampus = "BL";
    	if (user.getViewCampus() == null) {
    		return "redirect:/campus?toolName=emergencyinfo";
    	} else {
    		selectedCampus = user.getViewCampus();
    	}
    	List<? extends EmergencyInfo> infos = emergencyInfoService.findAllEmergencyInfoByCampus(selectedCampus);
    	uiModel.addAttribute("emergencyinfos", infos);
		uiModel.addAttribute("campus",user.getViewCampus());
		
    	if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/emergencyinfo/index";
    	}
    	return viewName;
    }

    @RequestMapping(value = "/js/{key}.js")
    public String getJavaScript(
            @PathVariable("key") String key,
            Model uiModel,
            HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String selectedCampus = "BL";
    	if (user.getViewCampus() != null) {
    		selectedCampus = user.getViewCampus();
    	}
		uiModel.addAttribute("campus", selectedCampus);
        return "ui3/emergencyinfo/js/"+key;
    }
    
    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }
    
//    @RequestMapping(value = "/{emergencyInfoId}", method = RequestMethod.GET, headers = "Accept=application/json")
//    @ResponseBody
//    public Object get(@PathVariable("emergencyInfoId") Long emergencyInfoId) {
//        EmergencyInfoJPAImpl emergencyInfo = emergencyInfoService.findEmergencyInfoById(emergencyInfoId);
//        if (emergencyInfo == null) {
//            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//        }
//        return emergencyInfo.toJson();
//    }

//    @RequestMapping(headers = "Accept=application/json")
//    @ResponseBody
//    public String getListJson(HttpServletRequest request) {
//    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
//		String selectedCampus = user.getViewCampus();
//    	List<EmergencyInfo> infos = emergencyInfoService.findAllEmergencyInfoByCampus(selectedCampus);
//    	return emergencyInfoService.toJson(infos);
//    }

//    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
//    public ResponseEntity<String> post(@RequestBody String json) {
//        emergencyInfoService.saveEmergencyInfo(emergencyInfoService.fromJsonToEntity(json));
//        return new ResponseEntity<String>(HttpStatus.CREATED);
//    }
//
//    @RequestMapping(value = "/collection", method = RequestMethod.POST, headers = "Accept=application/json")
//    public ResponseEntity<String> postAll(@RequestBody String json) {
//        for (EmergencyInfoJPAImpl emergencyInfo: emergencyInfoService.fromJsonToCollection(json)) {
//            emergencyInfoService.saveEmergencyInfo(emergencyInfo);
//        }
//        return new ResponseEntity<String>(HttpStatus.CREATED);
//    }
//
//    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
//    public ResponseEntity<String> put(@RequestBody String json) {
//        if (emergencyInfoService.findEmergencyInfoById(emergencyInfoService.fromJsonToEntity(json).getEmergencyInfoId()) == null) {
//            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//        } else {
//            if (emergencyInfoService.saveEmergencyInfo(emergencyInfoService.fromJsonToEntity(json)) == null) {
//                return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
//            }
//        }
//        return new ResponseEntity<String>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/collection", method = RequestMethod.PUT, headers = "Accept=application/json")
//    public ResponseEntity<String> putAll(@RequestBody String json) {
//        for (EmergencyInfoJPAImpl emergencyInfo: emergencyInfoService.fromJsonToCollection(json)) {
//            if (emergencyInfoService.findEmergencyInfoById(emergencyInfo.getEmergencyInfoId()) == null) {
//                return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//            } else {
//                if (emergencyInfoService.saveEmergencyInfo(emergencyInfo) == null) {
//                    return new ResponseEntity<String>(HttpStatus.NOT_MODIFIED);
//                }
//            }
//        }
//        return new ResponseEntity<String>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/{emergencyInfoId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
//    public ResponseEntity<String> delete(@PathVariable("emergencyInfoId") Long emergencyInfoId) {
//        EmergencyInfoJPAImpl emergencyInfo = emergencyInfoService.findEmergencyInfoById(emergencyInfoId);
//        if (emergencyInfo == null) {
//            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
//        }
//        emergencyInfoService.deleteEmergencyInfoById(emergencyInfo.getEmergencyInfoId());
//        return new ResponseEntity<String>(HttpStatus.OK);
//    }

}
