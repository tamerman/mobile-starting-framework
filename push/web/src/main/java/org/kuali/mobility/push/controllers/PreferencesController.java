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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.kuali.mobility.push.entity.Preference;
import org.kuali.mobility.push.entity.Sender;
import org.kuali.mobility.push.service.PreferenceService;
import org.kuali.mobility.push.service.SenderService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserAttribute;
import org.kuali.mobility.shared.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Controller for managing preferences.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
@Controller 
@RequestMapping("/pushprefs")
public class PreferencesController {

	private static final String USERNAME = "username";
	
	/**
	 * A reference to the Logger. 
	 */
	private static final Logger LOG = LoggerFactory.getLogger(PreferencesController.class);
	
	@Autowired
	@Qualifier("kmeProperties")
	private Properties kmeProperties;
	
	/**
	 * A reference to the SenderService
	 */
    @Autowired
    private SenderService senderService;

    /**
     * A method for setting the <code>SenderService<code> for the Controller object. 
     * 
     * @param senderService
     */
    public void setSenderService(SenderService senderService) {
        this.senderService = senderService;
    }


    /**
     * A reference to the PreferenceService 
     */
	@Autowired
    private PreferenceService preferenceService;

	/**
	 * A method for setting the <code>PreferenceService</code> for the controller object. 
	 * 
	 * @param preferenceService
	 */
	public void setPreferenceService(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }    
    
	/**
	 * A controller method for accessing a displaying all the push preferences for a given user. 
	 * 
	 * @param request
	 * @param uiModel
	 * @param key
	 * @return
	 */
    @RequestMapping(value = "/pushprefs", method = RequestMethod.GET)    
    public String preferences(HttpServletRequest request, Model uiModel, @RequestParam(value="key", required=false) String key) { 
    	List<Sender> senders = senderService.findAllUnhiddenSenders();
    	uiModel.addAttribute("senders", senders);
		User user = (User) request.getSession(true).getAttribute(Constants.KME_USER_KEY);
    	if(!user.isPublicUser()){
        	uiModel.addAttribute(USERNAME, user.getLoginName());
        	
        	List<UserAttribute> list = user.getAttributes();
        	Iterator<UserAttribute> it = list.iterator();
        	while(it.hasNext()){
        		UserAttribute ua = (UserAttribute) it.next();
        		LOG.info(ua.getAttributeName() + "/" + ua.getAttributeValue());
        	}
    	}else{
        	uiModel.addAttribute(USERNAME, ""); 
    	}
    	String viewName = "push/preferences";
    	if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
            viewName = "ui3/push/pushpreferences";
    	}
    	return viewName;
    }  
    
    @RequestMapping(value = "/js/preferences.js")
    public String getHistoryJavaScript(Model uiModel, HttpServletRequest request) {
        return "ui3/push/js/preferences";
    }
    
    /**
     * A controller method for redirecting the user to a login page.  
     * 
     * @param request
     * @param uiModel
     * @param key
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)    
    public String login(HttpServletRequest request, Model uiModel, @RequestParam(value="key", required=false) String key) { 
    	List<Sender> senders = senderService.findAllUnhiddenSenders();
    	uiModel.addAttribute("senders", senders);    	
//    	return "push/preferences";
    	return "redirect:/pushprefs";
    }  
    
    /**
     * A controller method for getting the preferences for a given username and returning to the accessing page via a JSON formatted string. 
     * 
     * @param uiModel
     * @param request
     * @param username Username of the preferences requested. 
     * @return String A JSON formatted string containing the preferences for a given username.
     */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
	public String getPreferences(Model uiModel, HttpServletRequest request, @RequestParam(value="username", required=false) String username) {
		List<Preference> preferences = preferenceService.findPreferencesByUsername(username);
//		User currentUser = userDao.loadUserByLoginName(username);
//		LOG.info(currentUser);
		
		
		if(preferences == null){
			return "{\"preferences\":[]}";			
		}else{
			return "{\"preferences\":" + new JSONSerializer().toJSON(preferences).toString() + "}";
		}
	}  
    
    /**
     * A controller method for saving prefernces for a given username after they have been changed by the user. 
     * 
     * @param data A JSON formatted string containing the end configuration of the preference values for a given user. 
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestBody String data) {
    	LOG.info("-----Register-----");
    	if(data == null){
    		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
    	} 

    	JSONObject queryParams;
    	try{
    		queryParams = (JSONObject) JSONSerializer.toJSON(data);
    		LOG.info(queryParams.toString());
    	}catch(JSONException je){
    		LOG.error("JSONException in :" + data + " : " + je.getMessage());
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    	}
    		
    	Preference pref = new Preference();
    	pref.setEnabled(queryParams.getBoolean("enabled"));
    	pref.setUsername(queryParams.getString(USERNAME));
    	pref.setPushSenderID(queryParams.getLong("senderId"));
    	LOG.info("\n--- New Preference---" + pref.toString());
    	
    	Preference temp = preferenceService.findPreference(pref.getUsername(), pref.getPushSenderID());
    	if(temp != null){
    		LOG.info("--- Pref Exists --- UPDATE");
        	temp.setEnabled(queryParams.getBoolean("enabled"));
        	temp.setUsername(queryParams.getString(USERNAME));
        	temp.setPushSenderID(queryParams.getLong("senderId"));

    		if(!temp.isEnabled()){
    			preferenceService.savePreference(temp);
    		}else{
    			preferenceService.removePreference(temp.getId());
    		}
    	}else{
    		LOG.info("--- Pref Doesn't Exist --- ADD");
    		if(pref.isEnabled()){
    			preferenceService.savePreference(pref);
    		}
    	}
   		
   		return new ResponseEntity<String>(HttpStatus.OK);
    }

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}	
    
}
