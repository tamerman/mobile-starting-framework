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

package org.kuali.mobility.tours.controllers;

import de.micromata.opengis.kml.v_2_2_0.Kml;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.tours.entity.*;
import org.kuali.mobility.tours.service.ToursService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/tours")
public class ToursController {

	private static Logger LOG = LoggerFactory.getLogger(ToursController.class);

	@Autowired
    private ToursService toursService;

	//@Autowired
	//private AdsService adsService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model uiModel) {
    	List<Tour> tours = toursService.findAllTours();
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	for (Iterator<Tour> iter = tours.iterator(); iter.hasNext(); ){
    		Tour tour = iter.next();
    		if (!toursService.hasAccessToViewTour(user, tour)) {
    			iter.remove();
    		}
    	}

    	uiModel.addAttribute("tours", tours);
    	return "tours/home";
    }

    @RequestMapping(value = "publish", method = RequestMethod.GET)
    public String publish(HttpServletRequest request, Model uiModel) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}

    	List<Tour> tours = toursService.findAllTours();
    	List<POI> POIs = toursService.findAllCommonPOI();
    	for (Iterator<Tour> iter = tours.iterator(); iter.hasNext(); ){
    		Tour tour = iter.next();
    		if (!toursService.hasAccessToEditTour(user, tour)) {
    			iter.remove();
    		}
    	}
    	for (Iterator<POI> iter = POIs.iterator(); iter.hasNext(); ){
    		POI poi = iter.next();
    		if (!toursService.hasAccessToEditPOI(user, poi)) {
    			iter.remove();
    		}
    	}


    	uiModel.addAttribute("tours", tours);
    	uiModel.addAttribute("pois", POIs);
    	return "tours/index";
    }

	@RequestMapping(value = "/view/{tourId}", method = RequestMethod.GET)
    public String viewTour(HttpServletRequest request, Model uiModel, @PathVariable("tourId") long tourId) {
    	try {
    		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
	    	Tour tour = toursService.findTourById(tourId);

	    	if (!toursService.hasAccessToViewTour(user, tour)){
	    		uiModel.addAttribute("message", "You do not have access to view this tour.");
	    		return "tours/message";
	    	}

	    	if (tour != null){
	    		if (tour.getPointsOfInterest() != null) {
	    			Collections.sort(tour.getPointsOfInterest());
	    		}
		    	try {
		    		if (tour.getFbText1() != null) tour.setFbText1(URLEncoder.encode(tour.getFbText1(), "UTF-8"));
		    		if (tour.getFbText2() != null) tour.setFbText2(URLEncoder.encode(tour.getFbText2(), "UTF-8"));
				} catch (Exception e) {

				}
		    	uiModel.addAttribute("tour", tour);
		    	try {
					uiModel.addAttribute("pageUrl", URLEncoder.encode(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/tours/view/" + tour.getTourId(), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
	    	}
    	} catch (Exception e) {
    		LOG.error("Error retrieving tour." + tourId, e);
    	}
    	return "tours/tour";
    }

	@RequestMapping(value = "/details/{poiId}", method = RequestMethod.GET)
    public String viewPoiDetails(HttpServletRequest request, Model uiModel, @PathVariable("poiId") long poiId) {
    	try {
			POI poi = toursService.findPoiById(poiId);

			if (poi != null && poi.getTourId() != null) {
				User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		    	Tour tour = toursService.findTourById(poi.getTourId());

		    	if (!toursService.hasAccessToViewTour(user, tour)){
		    		uiModel.addAttribute("message", "You do not have access to view details of this location.");
		    		return "tours/message";
		    	}

		    	POI prevPoi = toursService.findPoiByOrder(poi.getTourId(), poi.getOrder() - 1);
		    	POI nextPoi = toursService.findPoiByOrder(poi.getTourId(), poi.getOrder() + 1);

		    	if (prevPoi != null) {
		    		uiModel.addAttribute("prevPoi", prevPoi);
		    	}
		    	if (nextPoi != null) {
		    		uiModel.addAttribute("nextPoi", nextPoi);
		    	}
			}

	    	if (poi != null && poi.getMedia() != null) {
	    		poi.setMedia(StringEscapeUtils.escapeJavaScript(poi.getMedia()));
	    	}

	    	if (poi.getPhoneNumbers() != null) {
	    		for (POIPhoneNumber number : poi.getPhoneNumbers()){
	    			number.setFormattedNumber(String.format("(%s) %s-%s", number.getNumber().substring(0, 3), number.getNumber().substring(3, 6), number.getNumber().substring(6, 10)));
	    		}
	    	}

	    	uiModel.addAttribute("poi", poi);
		} catch (Exception e) {
			LOG.error("Error retrieving POI details." + poiId, e);
		}
    	return "tours/details";
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/map/{tourId}", method = RequestMethod.GET)
    public String viewTourMap(HttpServletRequest request, Model uiModel, @PathVariable("tourId") long tourId) {
    	try {
    		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    		Tour tour = toursService.findTourById(tourId);

	    	if (!toursService.hasAccessToViewTour(user, tour)){
	    		uiModel.addAttribute("message", "You do not have access to view this tour.");
	    		return "tours/message";
	    	}

	    	uiModel.addAttribute("tour", tour);

	    	JsonConfig config = new JsonConfig();
	    	config.registerPropertyExclusion(POI.class, "tour");
	    	config.registerPropertyExclusion(POI.class, "versionNumber");
	    	config.registerPropertyExclusion(POI.class, "tourId");
	    	config.registerPropertyExclusion(POI.class, "permissions");
	    	config.registerPropertyExclusion(POI.class, "phoneNumbers");
	    	config.registerPropertyExclusion(Tour.class, "permissions");
	    	JSONObject json =  (JSONObject) JSONSerializer.toJSON(tour, config);
	    	JSONArray pointsOfInterest = json.getJSONArray("pointsOfInterest");
	    	for (Iterator<JSONObject> iter = pointsOfInterest.iterator(); iter.hasNext();) {
	    		try {
	    			JSONObject poi = iter.next();
	    			String mediaJson = poi.getString("media");
	    			if (!mediaJson.isEmpty()) {
		    			JSONArray media = (JSONArray) JSONSerializer.toJSON(mediaJson);
						poi.element("media", media);
	    			} else {
	    				poi.element("media", new JSONArray());
	    			}
				} catch (Exception e) {}
	    	}
	    	uiModel.addAttribute("tourJson", json.toString());
    	} catch (Exception e) {
    		LOG.error("Error viewing map for tour " + tourId, e);
    	}
    	return "tours/map";
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "new", method = RequestMethod.GET)
    public String edit(HttpServletRequest request, Model uiModel) {
    	try {
    		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
        	if (!toursService.hasAccessToPublish(user)) {
    			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
        		return "tours/message";
    		}
	    	List<POI> definedPois = toursService.findAllCommonPOI();
	    	for (Iterator<POI> iter = definedPois.iterator(); iter.hasNext(); ){
	    		POI poi = iter.next();
	    		if (!toursService.hasAccessToViewPOI(user, poi)) {
	    			iter.remove();
	    		}
	    	}
	    	JsonConfig config = new JsonConfig();
	    	config.registerPropertyExclusion(POI.class, "tour");
	    	config.registerPropertyExclusion(POI.class, "versionNumber");
	    	config.registerPropertyExclusion(POI.class, "tourId");
	    	config.registerPropertyExclusion(POI.class, "permissions");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "poi");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "poiId");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "id");
	    	config.registerPropertyExclusion(TourPermission.class, "tour");
	    	config.registerPropertyExclusion(TourPermission.class, "tourId");
	    	config.registerPropertyExclusion(TourPermission.class, "permissionId");
	    	JSONArray pointsOfInterest =  (JSONArray) JSONSerializer.toJSON(definedPois, config);
	    	for (Iterator<JSONObject> iter = pointsOfInterest.iterator(); iter.hasNext();) {
	    		try {
	    			JSONObject poi = iter.next();
	    			String mediaJson = poi.getString("media");
	    			if (!mediaJson.isEmpty()) {
		    			JSONArray media = (JSONArray) JSONSerializer.toJSON(mediaJson);
						poi.element("media", media);
	    			} else {
	    				poi.element("media", new JSONArray());
	    			}
				} catch (Exception e) {}
	    	}
	    	uiModel.addAttribute("pois", pointsOfInterest.toString());
    	} catch (Exception e) {
    		LOG.error("Error creating new tour.", e);
    	}
    	return "tours/edit";
    }

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/edit/{tourId}", method = RequestMethod.GET)
    public String editTour(Model uiModel, HttpServletRequest request, @PathVariable("tourId") long tourId) {
    	try {
    		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
        	if (!toursService.hasAccessToPublish(user)) {
    			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
        		return "tours/message";
    		}
    		Tour tour = toursService.findTourById(tourId);

	    	if (!toursService.hasAccessToEditTour(user, tour)){
	    		uiModel.addAttribute("message", "You do not have access to edit this tour.");
	    		return "tours/message";
	    	}

	    	if (tour.getPointsOfInterest() != null) {
	    		Collections.sort(tour.getPointsOfInterest());
	    	}
	    	JsonConfig config = new JsonConfig();
	    	config.registerPropertyExclusion(POI.class, "tour");
	    	config.registerPropertyExclusion(POI.class, "versionNumber");
	    	config.registerPropertyExclusion(POI.class, "tourId");
	    	config.registerPropertyExclusion(POI.class, "permissions");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "poi");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "poiId");
	    	config.registerPropertyExclusion(POIPhoneNumber.class, "id");
	    	config.registerPropertyExclusion(POIEmailAddress.class, "poi");
	    	config.registerPropertyExclusion(POIEmailAddress.class, "poiId");
	    	config.registerPropertyExclusion(POIEmailAddress.class, "id");
	    	config.registerPropertyExclusion(TourPermission.class, "tour");
	    	config.registerPropertyExclusion(TourPermission.class, "tourId");
	    	config.registerPropertyExclusion(TourPermission.class, "permissionId");
	    	JSONObject json =  (JSONObject) JSONSerializer.toJSON(tour, config);
	    	JSONArray pointsOfInterest = json.getJSONArray("pointsOfInterest");
	    	if (pointsOfInterest != null) {
		    	for (Iterator<JSONObject> iter = pointsOfInterest.iterator(); iter.hasNext();) {
		    		try {
		    			JSONObject poi = iter.next();
		    			String mediaJson = poi.getString("media");
		    			if (!mediaJson.isEmpty()) {
			    			JSONArray media = (JSONArray) JSONSerializer.toJSON(mediaJson);
							poi.element("media", media);
		    			} else {
		    				poi.element("media", new JSONArray());
		    			}
					} catch (Exception e) {
						LOG.error("Problem modifying media collection for tour POI", e);
					}
		    	}
	    	}
	    	
	    	uiModel.addAttribute("tourJson", json.toString());

	    	List<POI> definedPois = toursService.findAllCommonPOI();
	    	for (Iterator<POI> iter = definedPois.iterator(); iter.hasNext(); ){
	    		POI poi = iter.next();
	    		if (!toursService.hasAccessToViewPOI(user, poi)) {
	    			iter.remove();
	    		}
	    	}
	    	pointsOfInterest =  (JSONArray) JSONSerializer.toJSON(definedPois, config);
	    	for (Iterator<JSONObject> iter = pointsOfInterest.iterator(); iter.hasNext();) {
	    		try {
	    			JSONObject poi = iter.next();
	    			String mediaJson = poi.getString("media");
	    			if (!mediaJson.isEmpty()) {
		    			JSONArray media = (JSONArray) JSONSerializer.toJSON(mediaJson);
						poi.element("media", media);
	    			} else {
	    				poi.element("media", new JSONArray());
	    			}
				} catch (Exception e) {
					LOG.error("Problem modifying media collection for common POI", e);
				}
	    	}
	    	uiModel.addAttribute("pois", pointsOfInterest.toString());
    	} catch (Exception e) {
    		LOG.error("Error editing tour " + tourId, e);
    	}
    	return "tours/edit";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ResponseBody
    public String verifyAdsGroup(@RequestParam("group") String groupName) {
    	boolean valid = true;//adsService.validateAdsGroup(groupName);
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("group", groupName);
    	result.put("valid", valid);

    	return JSONSerializer.toJSON(result).toString();
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String save(HttpServletRequest request, @RequestParam("data") String postData,  Model uiModel) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}

    	Tour tour = convertTourFromJson(postData);

    	if (tour.getTourId() != null) {
    		Tour existingTour = toursService.findTourById(tour.getTourId());
    		if (existingTour != null && !toursService.hasAccessToEditTour(user, existingTour)){
        		uiModel.addAttribute("message", "You do not have access to edit this tour.");
        		return "tours/message";
        	}
    	}

    	toursService.saveTour(tour);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "delete/{tourId}", method = RequestMethod.GET)
    public String delete(Model uiModel, HttpServletRequest request, @PathVariable("tourId") Long tourId) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}
    	Tour tour = toursService.findTourById(tourId);
    	if (!toursService.hasAccessToEditTour(user, tour)){
    		uiModel.addAttribute("message", "You do not have access to delete this tour.");
    		return "tours/message";
    	}
    	toursService.deleteTourById(tourId);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "copy/{tourId}", method = RequestMethod.GET)
    public String copy(Model uiModel, HttpServletRequest request, @PathVariable("tourId") Long tourId) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}
    	Tour tour = toursService.findTourById(tourId);
    	if (!toursService.hasAccessToEditTour(user, tour)){
    		uiModel.addAttribute("message", "You do not have access to delete this tour.");
    		return "tours/message";
    	}
    	toursService.duplicateTourById(tourId);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "poi/new", method = RequestMethod.GET)
    public String editPoi(Model uiModel, HttpServletRequest request) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}
    	return "tours/editPoi";
    }

	@RequestMapping(value = "poi/edit/{poiId}", method = RequestMethod.GET)
    public String editPoi(HttpServletRequest request, Model uiModel, @PathVariable("poiId") long poiId) {
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}

		POI poi = toursService.findPoiById(poiId);
    	if (!toursService.hasAccessToEditPOI(user, poi)){
    		uiModel.addAttribute("message", "You do not have access to edit this point of interest.");
    		return "tours/message";
    	}

    	JsonConfig config = new JsonConfig();
    	config.registerPropertyExclusion(POI.class, "tour");
    	config.registerPropertyExclusion(POI.class, "tourId");
    	config.registerPropertyExclusion(POIPhoneNumber.class, "poi");
    	config.registerPropertyExclusion(POIPhoneNumber.class, "id");
    	config.registerPropertyExclusion(POIPhoneNumber.class, "poiId");
    	config.registerPropertyExclusion(POIEmailAddress.class, "poi");
    	config.registerPropertyExclusion(POIEmailAddress.class, "poiId");
    	config.registerPropertyExclusion(POIEmailAddress.class, "id");
    	config.registerPropertyExclusion(POIPermission.class, "poiId");
    	config.registerPropertyExclusion(POIPermission.class, "poi");
    	config.registerPropertyExclusion(POIPermission.class, "permissionId");
    	JSONObject json =  (JSONObject) JSONSerializer.toJSON(poi, config);
		String mediaJson = json.getString("media");
		if (!mediaJson.isEmpty()) {
			try {
				JSONArray media = (JSONArray) JSONSerializer.toJSON(mediaJson);
				json.element("media", media);
			} catch (Exception e) {
				LOG.error("Problem modifying media collection for common POI", e);
			}
		} else {
			json.element("media", new JSONArray());
		}
    	uiModel.addAttribute("poiJson", json.toString());
    	return "tours/editPoi";
    }

    @RequestMapping(value = "poi/edit", method = RequestMethod.POST)
    public String savePoi(HttpServletRequest request, @RequestParam("data") String postData,  Model uiModel) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
			return "tours/message";
		}

    	POI poi = convertPoiFromJson(postData);

    	if (poi != null && poi.getPoiId() != null) {
    		POI existingPOI = toursService.findPoiById(poi.getPoiId());

    		if (!toursService.hasAccessToEditPOI(user, existingPOI)){
        		uiModel.addAttribute("message", "You do not have access to edit this point of interest.");
        		return "tours/message";
        	}
    	}

    	toursService.savePoi(poi);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "poi/copy/{poiId}", method = RequestMethod.GET)
    public String copyPoi(@PathVariable("poiId") Long poiId) {
    	toursService.duplicatePoiById(poiId);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "poi/delete/{poiId}", method = RequestMethod.GET)
    public String deletePoi(HttpServletRequest request, @PathVariable("poiId") Long poiId, Model uiModel) {
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
    	if (!toursService.hasAccessToPublish(user)) {
			uiModel.addAttribute("message", "You do not have access to create or edit tours.");
    		return "tours/message";
		}

    	POI poi = toursService.findPoiById(poiId);
    	if (!toursService.hasAccessToEditPOI(user, poi)){
    		uiModel.addAttribute("message", "You do not have access to delete this point of interest.");
    		return "tours/message";
    	}

    	toursService.deletePoiById(poiId);
    	return "redirect:/tours/publish";
    }

    @RequestMapping(value = "kml/{tourId}", method = RequestMethod.GET)
    public String downloadKml(@PathVariable("tourId") Long tourId, HttpServletResponse response) {
    	Tour tour = toursService.findTourById(tourId);
    	Kml kml = toursService.createTourKml(tour);
    	OutputStream os = new ByteArrayOutputStream();
    	try {
			kml.marshal(os);
		} catch (Exception e) {
		}
    	byte [] data = os.toString().getBytes();
    	response.setContentType("application/vnd.google-earth.kml+xml");
		response.setContentLength(data.length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + tour.getName() + ".kml\"" );
		try {
			response.getOutputStream().write(data, 0, data.length);
		} catch (IOException e) {
		}
    	return null;
    }

    @RequestMapping(value = "kml", method = RequestMethod.POST)
    public String downloadKml(@RequestParam("data") String postData, @RequestParam("name") String name, HttpServletResponse response) {
    	String newline = System.getProperty("line.separator");
    	byte [] data = postData.replace("<EOL>", newline).getBytes();
    	response.setContentType("application/vnd.google-earth.kml+xml");
		response.setContentLength(data.length);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + name + ".kml\"" );
		try {
			response.getOutputStream().write(data, 0, data.length);
		} catch (IOException e) {
		}
    	return null;
    }

    @SuppressWarnings("unchecked")
	private Tour convertTourFromJson(String json) {
    	Tour tour = new Tour();
    	JSONObject tourJson = (JSONObject) JSONSerializer.toJSON(json);

    	try {
    		tour.setTourId(tourJson.getLong("id"));
    	} catch (Exception e) {
    		tour.setTourId(null);
    	}
    	tour.setName(tourJson.getString("name"));
    	tour.setDescription(tourJson.getString("description"));
    	tour.setImageUrl(tourJson.getString("imageUrl"));
    	tour.setPath(tourJson.getString("path"));

    	tour.setTweetText1(tourJson.getString("tweetText1"));
    	tour.setTweetText2(tourJson.getString("tweetText2"));
    	tour.setFbText1(tourJson.getString("fbText1"));
    	tour.setFbText2(tourJson.getString("fbText2"));

    	tour.setTweetText1Enabled(tourJson.getBoolean("tweetText1Enabled") ? "T" : "F");
    	tour.setTweetText2Enabled(tourJson.getBoolean("tweetText2Enabled") ? "T" : "F");
    	tour.setFbText1Enabled(tourJson.getBoolean("fbText1Enabled") ? "T" : "F");
    	tour.setFbText2Enabled(tourJson.getBoolean("fbText2Enabled") ? "T" : "F");

    	try {
    		tour.setVersionNumber(tourJson.getLong("version"));
    	} catch (Exception e) {
    		tour.setVersionNumber(null);
    	}
    	try {
    		tour.setDistance(tourJson.getLong("distance"));
    	} catch (Exception e) {
    		tour.setDistance(null);
    	}

    	JSONArray permissionsArray = tourJson.getJSONArray("permissions");
		Set<TourPermission> permissions = new HashSet<TourPermission>();
		for (Iterator<JSONObject> iter = permissionsArray.iterator(); iter.hasNext();) {
			try {
				JSONObject permission = iter.next();
				TourPermission tourPermission = new TourPermission();

				tourPermission.setGroupName(permission.getString("group"));
				tourPermission.setType(permission.getString("type"));
				tourPermission.setTour(tour);
				tourPermission.setTourId(tour.getTourId());

				permissions.add(tourPermission);
			} catch (Exception e) {
				LOG.error("Error parsing tour permission.", e);
			}
		}
		tour.setPermissions(permissions);

    	JSONArray pointsOfInterest = tourJson.getJSONArray("POIs");
		List<POI> POIs = new ArrayList<POI>();
		for (Iterator<JSONObject> iter = pointsOfInterest.iterator(); iter.hasNext();) {
			JSONObject pointOfInterest = iter.next();
			POI poi = new POI();

			try {
				poi.setPoiId(pointOfInterest.getLong("poiId"));
	    	} catch (Exception e) {
	    		poi.setPoiId(null);
	    	}
			try {
				poi.setName(pointOfInterest.getString("name"));
	    	} catch (Exception e) {
	    		poi.setName(null);
	    	}
			try {
				poi.setOfficialName(pointOfInterest.getString("officialName"));
	    	} catch (Exception e) {
	    		poi.setOfficialName(null);
	    	}
			try {
				poi.setDescription(pointOfInterest.getString("description"));
	    	} catch (Exception e) {
	    		poi.setDescription(null);
	    	}
			try {
				poi.setShortDescription(pointOfInterest.getString("shortDescription"));
	    	} catch (Exception e) {
	    		poi.setShortDescription(null);
	    	}
			try {
				poi.setLocationId(pointOfInterest.getString("iuBuildingCode"));
	    	} catch (Exception e) {
	    		try {
					poi.setLocationId(pointOfInterest.getString("venueId"));
		    	} catch (Exception ex) {
		    		poi.setLocationId(null);
		    	}
	    	}
			poi.setType(pointOfInterest.getString("type"));

			try {
				poi.setDistanceToNextPoi(pointOfInterest.getLong("distanceToNext"));
	    	} catch (Exception e) {
	    		poi.setDistanceToNextPoi(null);
	    	}

			poi.setOrder(pointOfInterest.getInt("order"));

			JSONObject location = pointOfInterest.getJSONObject("location");
			poi.setLatitude(((Double)location.getDouble("lat")).floatValue());
			poi.setLongitude(((Double)location.getDouble("lng")).floatValue());
			try {
				poi.setMedia(pointOfInterest.getString("media"));
			} catch (Exception e) {}
			try {
				poi.setUrl(pointOfInterest.getString("url"));
			} catch (Exception e) {}
			try {
				poi.setThumbnailUrl(pointOfInterest.getString("thumbnailUrl"));
	    	} catch (Exception e) {
	    		poi.setThumbnailUrl(null);
	    	}
			try {
				poi.setFbLikeUrl(pointOfInterest.getString("fbLikeUrl"));
	    	} catch (Exception e) {
	    		poi.setFbLikeUrl(null);
	    	}
			try {
				poi.setFbLikeButtonEnabled(pointOfInterest.getBoolean("fbLikeButtonEnabled") ? "T" : "F");
	    	} catch (Exception e) {
	    		poi.setFbLikeButtonEnabled("F");
	    	}

			JSONArray phoneNumbers = pointOfInterest.getJSONArray("phoneNumbers");
			if (phoneNumbers != null) {
				for (Iterator<JSONObject> iter2 = phoneNumbers.iterator(); iter2.hasNext();) {
					JSONObject phnNumber = iter2.next();
					POIPhoneNumber phoneNumber = new POIPhoneNumber();
					try {
						phoneNumber.setName(phnNumber.getString("name"));
			    	} catch (Exception e) {
			    		phoneNumber.setName(null);
			    	}
					try {
						phoneNumber.setNumber(phnNumber.getString("value"));
			    	} catch (Exception e) {
			    		phoneNumber.setNumber(null);
			    	}
					phoneNumber.setPoi(poi);
					phoneNumber.setPoiId(poi.getPoiId());
					poi.getPhoneNumbers().add(phoneNumber);
				}
			}

			JSONArray emailAddresses = pointOfInterest.getJSONArray("emailAddresses");
			if (emailAddresses != null) {
				for (Iterator<JSONObject> iter2 = emailAddresses.iterator(); iter2.hasNext();) {
					JSONObject email = iter2.next();
					POIEmailAddress emailAddress = new POIEmailAddress();
					try {
						emailAddress.setName(email.getString("name"));
			    	} catch (Exception e) {
			    		emailAddress.setName(null);
			    	}
					try {
						emailAddress.setAddress(email.getString("address"));
			    	} catch (Exception e) {
			    		emailAddress.setAddress(null);
			    	}
					emailAddress.setPoi(poi);
					emailAddress.setPoiId(poi.getPoiId());
					poi.getEmailAddresses().add(emailAddress);
				}
			}

			poi.setTourId(tour.getTourId());
			poi.setTour(tour);
			POIs.add(poi);
		}
    	tour.setPointsOfInterest(POIs);

    	return tour;
    }

	@SuppressWarnings("unchecked")
	private POI convertPoiFromJson(String json) {
    	POI poi = new POI();
    	JSONObject pointOfInterest = (JSONObject) JSONSerializer.toJSON(json);

		try {
			poi.setPoiId(pointOfInterest.getLong("id"));
    	} catch (Exception e) {
    		poi.setPoiId(null);
    	}
		try {
			poi.setVersionNumber(pointOfInterest.getLong("version"));
    	} catch (Exception e) {
    		poi.setVersionNumber(null);
    	}

		try {
			poi.setName(pointOfInterest.getString("name"));
    	} catch (Exception e) {
    		poi.setName(null);
    	}
		try {
			poi.setOfficialName(pointOfInterest.getString("officialName"));
    	} catch (Exception e) {
    		poi.setOfficialName(null);
    	}
		try {
			poi.setDescription(pointOfInterest.getString("description"));
    	} catch (Exception e) {
    		poi.setDescription(null);
    	}
		try {
			poi.setShortDescription(pointOfInterest.getString("shortDescription"));
    	} catch (Exception e) {
    		poi.setShortDescription(null);
    	}
		try {
			poi.setLocationId(pointOfInterest.getString("locationId"));
    	} catch (Exception e) {
    		poi.setLocationId(null);
    	}
		try {
			poi.setUrl(pointOfInterest.getString("url"));
    	} catch (Exception e) {
    		poi.setUrl(null);
    	}
		try {
			poi.setThumbnailUrl(pointOfInterest.getString("thumbnailUrl"));
    	} catch (Exception e) {
    		poi.setThumbnailUrl(null);
    	}
		try {
			poi.setFbLikeUrl(pointOfInterest.getString("fbLikeUrl"));
    	} catch (Exception e) {
    		poi.setFbLikeUrl(null);
    	}
		poi.setFbLikeButtonEnabled(pointOfInterest.getBoolean("fbLikeButtonEnabled") ? "T" : "F");
		poi.setType(pointOfInterest.getString("type"));

		JSONArray permissionsArray = pointOfInterest.getJSONArray("permissions");
		Set<POIPermission> permissions = new HashSet<POIPermission>();
		for (Iterator<JSONObject> iter = permissionsArray.iterator(); iter.hasNext();) {
			try {
				JSONObject permission = iter.next();
				POIPermission poiPermission = new POIPermission();

				poiPermission.setGroupName(permission.getString("group"));
				poiPermission.setType(permission.getString("type"));
				poiPermission.setPoi(poi);
				poiPermission.setPoiId(poi.getPoiId());

				permissions.add(poiPermission);
			} catch (Exception e) {
				LOG.error("Error parsing tour permission.", e);
			}
		}
		poi.setPermissions(permissions);

		JSONArray phoneNumbers = pointOfInterest.getJSONArray("phoneNumbers");
		if (phoneNumbers != null) {
			for (Iterator<JSONObject> iter2 = phoneNumbers.iterator(); iter2.hasNext();) {
				JSONObject phnNumber = iter2.next();
				POIPhoneNumber phoneNumber = new POIPhoneNumber();
				try {
					phoneNumber.setName(phnNumber.getString("name"));
		    	} catch (Exception e) {
		    		phoneNumber.setName(null);
		    	}
				try {
					phoneNumber.setNumber(phnNumber.getString("value"));
		    	} catch (Exception e) {
		    		phoneNumber.setNumber(null);
		    	}
				phoneNumber.setPoi(poi);
				phoneNumber.setPoiId(poi.getPoiId());
				poi.getPhoneNumbers().add(phoneNumber);
			}
		}
		JSONArray emailAddresses = pointOfInterest.getJSONArray("emailAddresses");
		if (emailAddresses != null) {
			for (Iterator<JSONObject> iter2 = emailAddresses.iterator(); iter2.hasNext();) {
				JSONObject email = iter2.next();
				POIEmailAddress emailAddress = new POIEmailAddress();
				try {
					emailAddress.setName(email.getString("name"));
		    	} catch (Exception e) {
		    		emailAddress.setName(null);
		    	}
				try {
					emailAddress.setAddress(email.getString("address"));
		    	} catch (Exception e) {
		    		emailAddress.setAddress(null);
		    	}
				emailAddress.setPoi(poi);
				emailAddress.setPoiId(poi.getPoiId());
				poi.getEmailAddresses().add(emailAddress);
			}
		}

		poi.setTour(null);
		poi.setTourId(null);

		JSONObject location = pointOfInterest.getJSONObject("location");
		poi.setLatitude(((Double)location.getDouble("latitude")).floatValue());
		poi.setLongitude(((Double)location.getDouble("longitude")).floatValue());
		try {
			poi.setMedia(pointOfInterest.getString("media"));
		} catch (Exception e) {}

    	return poi;
    }

	public ToursService getToursService() {
		return toursService;
	}
	
	public void setToursService(ToursService toursService) {
		this.toursService = toursService;
	}
}
