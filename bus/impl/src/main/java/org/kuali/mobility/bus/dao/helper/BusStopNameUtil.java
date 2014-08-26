/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.bus.dao.helper;

import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

public class BusStopNameUtil {

	public static Logger LOG = LoggerFactory.getLogger(BusStopNameUtil.class);
	private String busStopNameMappingFile;
	private List<UMBusStopName> busStopNames;

	public BusStopNameUtil() {
	}

	@SuppressWarnings("unchecked")
	public void loadStopNames() {
		LOG.debug("Preparing to load bus stop name mappings from " + getBusStopNameMappingFile());
		XStream xstream = new XStream();
		xstream.processAnnotations(UMBusStopNames.class);
		xstream.processAnnotations(UMBusStopName.class);
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream(getBusStopNameMappingFile());
		UMBusStopNames stopnames = null;
		try {
			stopnames = (UMBusStopNames) xstream.fromXML(stream);
			this.setBusStopNames(stopnames.getStopnames());
			LOG.debug("Loaded " + getBusStopNames().size() + " bus stopnames ");
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
	}

	public String updateBusStopName(String name) {
		String stopName = new String();
		if (null != name) {
			for (UMBusStopName sn : this.getBusStopNames()) {
				LOG.debug("N1 [" + sn.getName1() + "] N2 [" + sn.getName2() + "]");
				if (sn.getName1().equalsIgnoreCase(name)) {
					stopName = sn.getName2();
					break;
				} else {
					stopName = name;
				}
			}
		} else {
			stopName = name;
		}
		return stopName;

	}

	public List<UMBusStopName> getBusStopNames() {
		if (busStopNames == null || busStopNames.isEmpty()) {
			LOG.debug("Bus Stop name mappings are not loaded.  Attempting to load them.");
			loadStopNames();
		}
		return busStopNames;
	}

	public void setBusStopNames(List<UMBusStopName> busStopNames) {
		this.busStopNames = busStopNames;
	}

	/**
	 * @return the busStopNameMappingFile
	 */
	public String getBusStopNameMappingFile() {
		return busStopNameMappingFile;
	}

	/**
	 * @param busStopNameMappingFile the busStopNameMappingFile to set
	 */
	public void setBusStopNameMappingFile(String mappingFile) {
		this.busStopNameMappingFile = mappingFile;
	}
}
