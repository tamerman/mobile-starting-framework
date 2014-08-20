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

package org.kuali.mobility.library.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.library.entity.*;
import org.kuali.mobility.library.service.LibraryService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for Library
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
@Controller
@RequestMapping("/library")
public class LibraryControllerImpl {

	/** 
	 * A reference to a Logger 
	 */
	private static final Logger LOG = LoggerFactory.getLogger( LibraryControllerImpl.class );

	/**
	 * A reference to the <code>service</code>
	 */
	@Autowired
	@Qualifier("libraryService")
	private LibraryService service;

	/**
	 * A reference to the KME properties
	 */
	@Resource(name="kmeProperties")
	private Properties kmeProperties;

	/**
	 * Sets the reference to the <code>service</code>
	 */
	public void setService(LibraryService service) {
		this.service = service;
	}

	
	/**
	 * The main menu for the library tool
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/index";
		}
		else{
			return "library/index";
		}
	}


    /**
     * The main menu for the library tool
     */
    @RequestMapping(value="home", method = RequestMethod.GET)
    public String ui3Home() {
            return "ui3/library/partials/home";
    }

	/**
	 *	Used to retrieve libraries and then displays the menu selection for the Library Contact Details selection screen 
	 */
	@RequestMapping(value = "/viewContact", method = RequestMethod.GET)
	public String viewContact(Model uiModel) {
		
		Map<String, List<Library>> libraryMap = this.service.getLibrariesByCampus();
		List<String> campusCodes = this.service.getCampusWithLibraries();
		
		uiModel.addAttribute("campusCodes" , campusCodes);
		uiModel.addAttribute("libraryMap" , libraryMap);
		uiModel.addAttribute("actionPage", "viewContact");

		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/partials/selectLibrary";
		}
		else{
			return "library/selectLibrary";
		}
	}

	
	/**
	 *	Display the Library contact details for the selected library 
	 */
	@RequestMapping(value = "/viewContact/{id}", method = RequestMethod.GET)
	public String viewContact(
			HttpServletRequest request,
			@PathVariable(value="id") int libraryId,
			Model uiModel) {
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		boolean isAdmin = LibraryPermissions.ADMIN_EXPRESSION.evaluate(user);
		
		// Get lib id from get request. Retrieve Library record from DB.
		Library library = service.getLibrary(libraryId);
		LibraryContactDetail lcd = library.getLibraryContactDetail();

		uiModel.addAttribute("libraryId", libraryId);
		uiModel.addAttribute("library", library);
		uiModel.addAttribute("libraryContactDetail", lcd);
		uiModel.addAttribute("isAdmin", isAdmin);

		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/partials/viewContact";
		}
		else{
			return "library/viewContact";
		}
	}
	
	/**
	 * Admin page to allow users to edit contact details of a specific library
	 */
	@RequestMapping(value = "/editContact/{id}", method = RequestMethod.GET)
	public String editContact(
			HttpServletRequest request,
			@PathVariable(value = "id") int libraryId,
			Model uiModel){
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		
		// If the user is not admin take him away from here
		if (!LibraryPermissions.ADMIN_EXPRESSION.evaluate(user)){
			return "redirect:/library/viewContact/"+libraryId;
		}
		
		Library library = service.getLibrary(libraryId);
		uiModel.addAttribute("contactDetail", library.getLibraryContactDetail());
		uiModel.addAttribute("libraryId", libraryId);
        uiModel.addAttribute("library", library);

		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/partials/editContact";
		}
		else{
			return "library/editContact";
		}
	}
	
	
	/**
	 * Invoked when the user submits the form to edit the contact details of a library
	 */
	@RequestMapping(value = "/editContact/{id}", method = RequestMethod.POST)
	public String editContact(
			HttpServletRequest request,
			@PathVariable(value="id") long libraryId,
			@RequestParam(value="telephone") String telephone,
			@RequestParam(value="fax") String fax,
			@RequestParam(value="generalInfoDesk") String generalInfoDesk,
			@RequestParam(value="email") String email,
			@RequestParam(value="postalAddress") String postalAddress,
			@RequestParam(value="physicalAddress") String physicalAddress,
			@RequestParam(value="latitude") String latitude,
			@RequestParam(value="longitude") String longitude,
			Model uiModel){
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// If the user is not admin take him away from here
		if (!LibraryPermissions.ADMIN_EXPRESSION.evaluate(user)){
			return "redirect:/library/viewContact/"+libraryId;
		}
		
		Library library = service.getLibrary(libraryId);
		LibraryContactDetail lcd = library.getLibraryContactDetail();
		if (lcd == null){
			lcd = new LibraryContactDetail();
		}
		
		lcd.setEmail(email);
		lcd.setFax(fax);
		lcd.setGeneralInfoDesk(generalInfoDesk);
		lcd.setLatitude(latitude);
		lcd.setLongitude(longitude);
		lcd.setPhysicalAddress(fixNewLines(physicalAddress));
		lcd.setPostalAddress(fixNewLines(postalAddress));
		lcd.setTelephone(telephone);
		
		library = service.saveLibrary(library);
		
		uiModel.addAttribute("library", library);
		return "redirect:/library/viewContact/"+libraryId;
	}
	
	
	/**
	 *	Used to retrieve libraries and then displays the menu selection for the Library operating hours selection screen 
	 */
	@RequestMapping(value = "/viewHours", method = RequestMethod.GET)
	public String viewHours(
			HttpServletRequest request,
			Model uiModel) {
		
		Map<String, List<Library>> libraryMap = this.service.getLibrariesByCampus();
		List<String> campusCodes = this.service.getCampusWithLibraries();
		// TODO get the campus objects for these campuses to get the campus names
		
		uiModel.addAttribute("campusCodes" , campusCodes);
		uiModel.addAttribute("libraryMap" , libraryMap );
		uiModel.addAttribute("actionPage", "viewHours");


		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/partials/selectLibrary";
		}
		else{
			return "library/selectLibrary";
		}

	}
	
	
	/**
	 * Allows the user to view the hours for a library
	 */
	@RequestMapping(value = "/viewHours/{id}", method = RequestMethod.GET)
	public String viewHours(
			HttpServletRequest request,
			@PathVariable(value = "id") int libraryId,
			Model uiModel) {
		
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		boolean isAdmin = LibraryPermissions.ADMIN_EXPRESSION.evaluate(user);
		
		Library library = service.getLibrary(libraryId);
		List<LibraryHourSet> hourSets = service.getLibraryHourSets(libraryId);
		List<LibraryHourSet> displayHourSets = new ArrayList<LibraryHourSet>(hourSets.size());
		for (LibraryHourSet lhs : hourSets){
			displayHourSets.add(service.getDisplayableHoursSet(lhs));
		}
		// Based on Library record get Library Contact Details
		LibraryContactDetail lcd = library.getLibraryContactDetail();
		
		uiModel.addAttribute("hourSets", displayHourSets);
		uiModel.addAttribute("library", library);
		uiModel.addAttribute("libraryContactDetails", lcd);
		uiModel.addAttribute("isAdmin", isAdmin);


		if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
			return "ui3/library/partials/viewHours";
		}
		else{
			return "library/viewHours";
		}
	}
	
	
	/**
	 * Admin page to edit the hours
	 */
	@RequestMapping(value="/editHours/{id}" , method = RequestMethod.GET)
	public String editHours(
			HttpServletRequest request,
			@PathVariable(value="id") long libraryId,
			Model uiModel){
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		
		// If the user is not admin take him away from here
		if (!LibraryPermissions.ADMIN_EXPRESSION.evaluate(user)){
			return "redirect:/library/viewHours/"+libraryId;
		}
		
		// Get lib id from get request. Retrieve Library record from DB.
		Library library = service.getLibrary(libraryId);
		List<LibraryHourSet> hourSets = service.getLibraryHourSets(libraryId);
		
		uiModel.addAttribute("library", library);
		uiModel.addAttribute("libraryId", libraryId);
		uiModel.addAttribute("hourSets", hourSets);


        if("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion","classic"))) {
            return "ui3/library/partials/editHours";
        }
        else{
            return "library/editHours";
        }

		
	}
	
	/**
	 * Invoked when the user saves edited hours.
	 */
	@RequestMapping(value="/editHours/{id}" , method = RequestMethod.POST)
	public String editHours(
			HttpServletRequest request,
			@PathVariable(value="id") int libraryId){
		
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		
		// If the user is not admin take him away from here
		if (!LibraryPermissions.ADMIN_EXPRESSION.evaluate(user)){
			return "redirect:/library/viewHours/"+libraryId;
		}
		
		List<LibraryHourSet> hourSets = service.getLibraryHourSets(libraryId);
		LibraryHourSet hourSet;
		List<LibraryHour> hours;
		LibraryHour hour;

		for (int hsIdx = 0 ; hsIdx < hourSets.size() ; hsIdx++){
			hourSet = hourSets.get(hsIdx);
			hours = hourSet.getHours();
			
			for(int hIdx = 0 ; hIdx < hours.size() ; hIdx++){
				hour = hours.get(hIdx);
				int hourSetIndex = hsIdx + 1;
				int hourIndex = hIdx + 1;
				if( request.getParameter("s"+ hourSetIndex +"h"+ hourIndex + "closed") == null){
					hour.setFromTime(this.stringToDate("HH:mm:ss" , request.getParameter("s"+ hourSetIndex +"h"+ hourIndex +"fromTime")));
					hour.setToTime(this.stringToDate("HH:mm:ss" , request.getParameter("s"+ hourSetIndex +"h"+ hourIndex +"toTime")));
				}else{
					hour.setFromTime(null);
					hour.setToTime(null);
				}
				hours.set(hIdx , hour);
			}
			hourSet.setHours(hours);
			service.saveLibraryHourSets(hourSet);
		}

        // Call the view hours controller
        return "redirect:/library/viewHours/"+libraryId;
	}
	

	/**
	 * Replace new lines with HTML friendly versions
	 * @param string
	 * @return
	 */
	private static final String fixNewLines(String string){
		string = string.replace("\r\n", "\n");
		string = string.replace("\r", "\n");
		string = string.replace("\n", "<br/>");
		return string;
	}
	
	private Date stringToDate(String format, String time ){
		Date date = null;
		DateFormat formatter = new SimpleDateFormat(format);
		try{
			date = formatter.parse(time+":00");
		}catch(Exception e){
			LOG.error(">>>> stringToDate : "+e.getMessage());
		}
		return date;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
