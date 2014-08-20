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

package org.kuali.mobility.maps.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.maps.entity.Location;
import org.kuali.mobility.maps.entity.MapsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Implementation of the CXF Device Service
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0
 */
@Service
public class CXFMapsService {
	
	/** A reference to a logger for this service */
	private static final Logger LOG = LoggerFactory.getLogger(CXFMapsService.class);
	private static final String FOURSQUARE_CLIENT_ID = "Maps.Foursquare.Client.Id";
	private static final String FOURSQUARE_CLIENT_SECRET = "Maps.Foursquare.Client.Secret";
	
	@Autowired
	@Qualifier("mapsService")
	private MapsService service;
	
	@Autowired
	private ConfigParamService configParamService;
	
	@GET
	@Path("/group/{groupCode}")
	public Object getBuildings(@QueryParam("groupCode") String groupId) {
    	try {
    		MapsGroup group = this.getService().getMapsGroupById(groupId);
    		if (group != null) {
    			return group.toJson();
    		}
    	} catch (Exception e) {
    		LOG.error(e.getMessage(), e);
    	}
    	return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
	
	@GET
	@Path(value = "/building/{id}")
	public Object get(@QueryParam("id") String id) {
        Location location = this.getService().getLocationById(id);
        if (location != null) {
        	return location.toJson();
        }
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
	
	@GET
	@Path(value = "/foursquare")
	public String getFoursquareData(@QueryParam("lat") String latitude, @QueryParam("lng") String longitude) throws Exception {
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
	
	@GET
	@Path(value = "/foursquare/{id}")
	public String getFoursquareData(@QueryParam("id") String id) throws Exception {
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
	
	public MapsService getService() {
		return service;
	}
	public void setService(MapsService service) {
		this.service = service;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}
}
