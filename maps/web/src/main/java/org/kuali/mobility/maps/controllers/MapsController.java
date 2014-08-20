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

package org.kuali.mobility.maps.controllers;

import flexjson.JSONSerializer;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringEscapeUtils;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.maps.entity.*;
import org.kuali.mobility.maps.service.MapsService;
import org.kuali.mobility.maps.util.MapsConstants;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@Controller
@RequestMapping("/maps")
public class MapsController implements ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(MapsController.class);

	private static final String FOURSQUARE_CLIENT_ID = "Maps.Foursquare.Client.Id";
	private static final String FOURSQUARE_CLIENT_SECRET = "Maps.Foursquare.Client.Secret";

	@Resource(name="kmeProperties")
	private Properties kmeProperties;

	@Autowired
    private MapsService mapsService;

    @Autowired
	private ConfigParamService configParamService;

	private ApplicationContext applicationContext;

    @RequestMapping(method = RequestMethod.GET)
    public String getHome(Model uiModel, HttpServletRequest request, @RequestParam(required = false) String city, @RequestParam(required = false) String postalCode, @RequestParam(required = false) String state, @RequestParam(required = false) String street1, @RequestParam(required = false) String street2) {
        String viewName = null;
        String selectedCampus = null;
    	User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

   		if (user.getViewCampus() == null) {
    		viewName = "redirect:/campus?toolName=maps";
    	} else {
    		selectedCampus = user.getViewCampus();
            MapsFormSearch mapsFormSearch = new MapsFormSearch();
            mapsFormSearch.setSearchCampus(selectedCampus);
            uiModel.addAttribute("mapsearchform", mapsFormSearch);
            uiModel.addAttribute("arcGisUrl", getMapsService().getArcGisUrl());
            uiModel.addAttribute("campus",selectedCampus);
            Properties kmeProperties = (Properties)getApplicationContext().getBean("kmeProperties");
            if( kmeProperties != null ) {
                uiModel.addAttribute("initialLatitude",kmeProperties.getProperty("maps.center.lat", "0.0"));
                uiModel.addAttribute("initialLongitude",kmeProperties.getProperty("maps.center.lon", "0.0"));
                uiModel.addAttribute("useCampusBounds",Boolean.parseBoolean(kmeProperties.getProperty("maps.useCampusBounds","false")));
            } else {
                uiModel.addAttribute("initialLatitude","0.0");
                uiModel.addAttribute("initialLongitude","0.0");
                uiModel.addAttribute("useCampusBounds",false);
            }
            if( "3".equalsIgnoreCase( getKmeProperties().getProperty("kme.uiVersion","classic") ) ) {
                if("mapquest".equalsIgnoreCase(getKmeProperties().getProperty("maps.api", "google"))) {
                    viewName = "ui3/maps/indexMapquest";
                } else {
                    viewName = "ui3/maps/indexGoogle";
                }
            } else {
                if("mapquest".equalsIgnoreCase(getKmeProperties().getProperty("maps.api", "google"))) {
                    viewName =  "maps/mapquest";
                }
                else {
                    viewName =  "maps/home";
                }
            }
        }
        return viewName;
    }

    @RequestMapping(value="/templates/{key}")
    public String getAngularTemplates(
            @PathVariable("key") String key,
                HttpServletRequest request,
            Model uiModel ) {
        return "ui3/maps/templates/"+key;
    }

    @RequestMapping(value = "/js/{key}.js")
    public String getJavaScript(
            @PathVariable("key") String key,
            Model uiModel,
            HttpServletRequest request) {
        String viewName = null;
        String selectedCampus = null;
        User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
        selectedCampus = ( (user ==null || null == user.getViewCampus() ) ? MapsConstants.ALL_GROUPS : user.getViewCampus() );
        uiModel.addAttribute("campus",selectedCampus);
        if( kmeProperties != null ) {
            uiModel.addAttribute("initialLatitude",kmeProperties.getProperty("maps.center.lat", "0.0"));
            uiModel.addAttribute("initialLongitude",kmeProperties.getProperty("maps.center.lon", "0.0"));
            uiModel.addAttribute("initialZoom",kmeProperties.getProperty("maps.google.default.zoom", "8"));
            uiModel.addAttribute("useCampusBounds",Boolean.parseBoolean(kmeProperties.getProperty("maps.useCampusBounds","false")));
        } else {
            uiModel.addAttribute("initialLatitude","0.0");
            uiModel.addAttribute("initialLongitude","0.0");
            uiModel.addAttribute("initialZoom","8");
            uiModel.addAttribute("useCampusBounds",false);
        }
        if( "maps".equalsIgnoreCase(key) ) {
            if("mapquest".equalsIgnoreCase(getKmeProperties().getProperty("maps.api", "google"))) {
                viewName = "ui3/maps/js/mapquest";
            } else {
                viewName = "ui3/maps/js/google";
            }
        } else {
            viewName = "ui3/maps/js/"+key;
        }
        return viewName;
    }

    @RequestMapping(value = "/location", method = RequestMethod.GET)
    public Object get(Model uiModel, HttpServletRequest request, @RequestParam(required = false) String latitude, @RequestParam(required = false) String longitude) {
        return "maps/location";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public Object getForm(Model uiModel) {
		MapsFormSearch form = new MapsFormSearch();
    	uiModel.addAttribute("mapsearchform", form);
        return "maps/formtest";
    }

    @Deprecated
    @RequestMapping(value = "/group/{groupCode}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Object getBuildings(@PathVariable("groupCode") String groupId) {
    	try {
    		MapsGroup group = getMapsService().getMapsGroupById(groupId);
    		if (group != null) {
    			return group.toJson();
    		}
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}
    	return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @Deprecated
    @RequestMapping(value = "/building/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Object get(@PathVariable("id") String id) {
        Location location = getMapsService().getLocationById(id);
        if (location != null) {
        	return location.toJson();
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/building/{id}", method = RequestMethod.GET)
    public Object get(Model uiModel, @PathVariable("id") String id) {
        Location location = getMapsService().getLocationById(id);
        if (location != null) {
        	uiModel.addAttribute("location", location);
        	uiModel.addAttribute("id", location.getId());
        }
        return "maps/building";
    }

	@RequestMapping(value = "/building/search", method = RequestMethod.GET)
	public String searchBuildings(Model uiModel, @RequestParam(required = true) String criteria, @RequestParam(required = true) String groupCode) {
		criteria = criteria.trim();
		LOG.info("Search: " + groupCode + " : " + criteria);
		MapsFormSearchResultContainer container = search(criteria, groupCode);
		uiModel.addAttribute("container", container);
		return "maps/search";
	}

	@RequestMapping(value = "/building/searchassist", method = RequestMethod.GET)
	public String searchBuildingsAutocomplete(Model uiModel, @RequestParam(required = true) String criteria, @RequestParam(required = true) String groupCode) {
		criteria = criteria.trim();
		LOG.info("Search: " + groupCode + " : " + criteria);
		MapsFormSearchResultContainer container = search(criteria, groupCode);
		uiModel.addAttribute("container", container);
		return "maps/searchautocomplete";
	}

	@RequestMapping(value = "/building/search", method = RequestMethod.POST)
	public String searchBuildings(HttpServletRequest request, @ModelAttribute("mapsearchform") MapsFormSearch mapsFormSearch, BindingResult bindingResult, SessionStatus status, Model uiModel) {
		String searchString = mapsFormSearch.getSearchText();
		searchString = searchString.trim();
		String searchCampus = mapsFormSearch.getSearchCampus();
		MapsFormSearchResultContainer container = search(searchString, searchCampus);
		uiModel.addAttribute("container", container);
		return "maps/home";
	}

	@Deprecated
	@RequestMapping(value = "/foursquare", method = RequestMethod.GET)
	@ResponseBody
	protected String getFoursquareData(@RequestParam("lat") String latitude, @RequestParam("lng") String longitude, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String foursquareId = "";
		String foursquareSecret = "";
		try {
			foursquareId = getConfigParamService().findValueByName(FOURSQUARE_CLIENT_ID);
			foursquareSecret = getConfigParamService().findValueByName(FOURSQUARE_CLIENT_SECRET);
		} catch (Exception e) {
			LOG.error("Foursquare config parameters are not set.");
		}

		BufferedReader br = null;
        GetMethod get = null;
        StringBuilder sb = new StringBuilder();
        try {
            get = new GetMethod("https://api.foursquare.com/v2/venues/search?v=20110627&ll="+ latitude + "," + longitude +"&limit=8&client_id=" + foursquareId + "&client_secret=" + foursquareSecret);
            br = new BufferedReader(new InputStreamReader(getInputStreamFromGetMethod(get, 10000)));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {

        } finally {
            if (br != null) {
                br.close();
            }
            if (get != null) {
                get.releaseConnection();
            }
        }

        return sb.toString();
	}

	@Deprecated
	@RequestMapping(value = "/foursquare/{id}", method = RequestMethod.GET)
	@ResponseBody
	protected String getFoursquareData(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String foursquareId = "";
		String foursquareSecret = "";
		try {
			foursquareId = getConfigParamService().findValueByName(FOURSQUARE_CLIENT_ID);
			foursquareSecret = getConfigParamService().findValueByName(FOURSQUARE_CLIENT_SECRET);
		} catch (Exception e) {
			LOG.error("Foursquare config parameters are not set.");
		}

        BufferedReader br = null;
        GetMethod get = null;
        StringBuilder sb = new StringBuilder();
        try {
            get = new GetMethod("https://api.foursquare.com/v2/venues/" + id + "?client_id=" + foursquareId + "&client_secret=" + foursquareSecret);
            br = new BufferedReader(new InputStreamReader(getInputStreamFromGetMethod(get, 10000)));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {

        } finally {
            if (br != null) {
                br.close();
            }
            if (get != null) {
                get.releaseConnection();
            }
        }

        return sb.toString();
	}

	private InputStream getInputStreamFromGetMethod(GetMethod get, int timeout) throws Exception {
        get.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
        HttpClient httpClient = new HttpClient();
        httpClient.getParams().setParameter(HttpClientParams.SO_TIMEOUT, new Integer(timeout));
        httpClient.getParams().setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(timeout));
        httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, new Integer(timeout));
        int status = httpClient.executeMethod(get);
        if (status == HttpStatus.OK.value()) {
        	return get.getResponseBodyAsStream();
        }
        return null;
    }

    @Deprecated
	private MapsFormSearchResultContainer search(String searchString, String searchGroupId) {
    	MapsFormSearchResultContainer container = new MapsFormSearchResultContainer();
    	try {
    		List<MapsFormSearchResult> results = new ArrayList<MapsFormSearchResult>();
    		//MapsGroup group = mapsService.getMapsGroupById(searchGroupId);
    		//Umich impl
    		MapsGroup group = getMapsService().getMapsGroupById(searchString);
    		if (group != null) {
	    		Set<Location> locationSet = group.getMapsLocations();
	    		List<Location> locations = new ArrayList<Location>();
	    		locations.addAll(locationSet);
	    		Collections.sort(locations, new LocationSort());
	    		for (Location location : locations) {
/*	    			boolean addLocation = false;
	    			if (location.getName() != null && location.getName().toLowerCase().indexOf(searchString.toLowerCase()) > -1) {
	    				addLocation = true;
	    			} else if (location.getDescription() != null && location.getDescription().toLowerCase().indexOf(searchString.toLowerCase()) > -1) {
	    				addLocation = true;
	    			} else if (location.getId() != null && location.getId().toLowerCase().trim().equals(searchString.toLowerCase().trim())) {
	    				addLocation = true;
	    			} */
	    			boolean addLocation = true;
	    			if (location.getLatitude().doubleValue()== 0.0 && location.getLongitude().doubleValue() == 0.0){
	    				addLocation = false;
	    			}

	    			if (addLocation) {
	        			MapsFormSearchResult result = new MapsFormSearchResult();
	        			result.setName(location.getName());
	        			result.setCode(location.getId());
	        			result.setLatitude(location.getLatitude());
	        			result.setLongitude(location.getLongitude());
	        			String jsonString = StringEscapeUtils.escapeJavaScript(new JSONSerializer().exclude("*.class").serialize(location));
	        			result.setInfo(jsonString);
	    				results.add(result);
	    			}
	    		}
    		}
    		container.setResults(results);

    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}
    	return container;
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

	public MapsService getMapsService() {
		return mapsService;
	}

	public void setMapsService(MapsService mapsService) {
		this.mapsService = mapsService;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
}
