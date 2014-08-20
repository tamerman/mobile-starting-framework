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

package org.kuali.mobility.computerlabs.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabGroup;
import org.kuali.mobility.computerlabs.entity.Location;
import org.kuali.mobility.shared.InitBean;
import org.kuali.mobility.util.mapper.DataMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class ComputerLabsInitBean implements InitBean, ApplicationContextAware {

	private static Logger LOG = LoggerFactory.getLogger(ComputerLabsInitBean.class);
	private ApplicationContext applicationContext;
    @Resource(name="computerLabDao")
	private ComputerLabsDao dao;

	private Map<String, List<String>> labUrls;

    @Resource(name="dataMapper")
	private DataMapper dataMapper;

	private String dataMappingUrl;

    public void loadData() {
        List<LabGroup> groups = new ArrayList<LabGroup>();
        for (String key : getLabUrls().keySet()) {
            LabGroup group = (LabGroup) getApplicationContext().getBean("computerLabGroupBean");
            group.setName(key);
            LOG.debug("Processing lab group " + key);
            for (String groupUrl : getLabUrls().get(key)) {
                try {
                    LOG.debug("SourceUrl: " + groupUrl);
                    List<Lab> groupLabs = new ArrayList<Lab>();
                    try {
                        URL url = new URL(groupUrl);
                        if (getDataMappingUrl() != null && !"".equals(getDataMappingUrl().trim())) {
                            groupLabs = getDataMapper().mapData(groupLabs, url, new URL(getDataMappingUrl()));
                        } else {
                            groupLabs = getDataMapper().mapData(groupLabs, url, "labMapping.xml");
                        }
                    } catch (MalformedURLException mue) {
                        LOG.error(mue.getLocalizedMessage(), mue);
                        LOG.debug( "Bad URL, attempting to load data as file in classpath: "+groupUrl);
                        groupLabs = getDataMapper().mapData(groupLabs, groupUrl, "labMapping.xml");
                    }
                    LOG.debug("Loaded " + groupLabs.size() + " labs for group " + group.getName());
                    Map<String, Location> locations = new LinkedHashMap<String, Location>();
                    for (Lab lab : groupLabs) {
                        Location location = null;
                        if (locations.containsKey(lab.getBuildingCode())) {
                            location = locations.get(lab.getBuildingCode());
                        } else {
                            location = (Location) getApplicationContext().getBean("computerLabLocationBean");
                            location.setName(lab.getBuildingCode());
                            locations.put(lab.getBuildingCode(), location);
                        }
                        location.addLab(lab);
                    }
                    LOG.debug("Sorted into " + locations.size() + " locations.");
                    group.setLocations(new ArrayList(locations.values()));
                } catch (MalformedURLException mue) {
                    LOG.error(mue.getLocalizedMessage(), mue);
                } catch (ClassNotFoundException cnfe) {
                    LOG.error(cnfe.getLocalizedMessage(), cnfe);
                } catch (IOException io) {
                    LOG.error(io.getLocalizedMessage(), io);
                } catch (Exception e) {
                    LOG.error(e.getLocalizedMessage(), e);
                }
            }
            groups.add(group);
        }
        LOG.debug("Preparing to set " + groups.size() + " lab groups in dao.");
        getDao().setLabGroups(groups);
    }

    /**
	 * @return the dao
	 */
	public ComputerLabsDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(ComputerLabsDao dao) {
		this.dao = dao;
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

	/**
	 * @return the labUrls
	 */
	public Map<String, List<String>> getLabUrls() {
		return labUrls;
	}

	/**
	 * @param labUrls the labUrls to set
	 */
	public void setLabUrls(Map<String, List<String>> labUrls) {
		this.labUrls = labUrls;
	}

	/**
	 * @return the dataMapper
	 */
	public DataMapper getDataMapper() {
		return dataMapper;
	}

	/**
	 * @param dataMapper the dataMapper to set
	 */
	public void setDataMapper(DataMapper dataMapper) {
		this.dataMapper = dataMapper;
	}

	/**
	 * @return the dataMappingUrl
	 */
	public String getDataMappingUrl() {
		return dataMappingUrl;
	}

	/**
	 * @param dataMappingUrl the dataMappingUrl to set
	 */
	public void setDataMappingUrl(String dataMappingUrl) {
		this.dataMappingUrl = dataMappingUrl;
	}
}
