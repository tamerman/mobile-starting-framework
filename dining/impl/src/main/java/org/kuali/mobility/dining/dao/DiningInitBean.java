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

package org.kuali.mobility.dining.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.dining.entity.DiningHall;
import org.kuali.mobility.dining.entity.DiningHallGroup;
import org.kuali.mobility.shared.InitBean;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class DiningInitBean implements InitBean {

	private static final Logger LOG = LoggerFactory.getLogger(DiningInitBean.class);

	@Resource(name = "diningDao")
	private DiningDao dao;

	private Map<String, List<String>> diningUrls;
	private boolean loadFromFile;

	public void loadData() {
		List<DiningHallGroup> diningHallGroups = new ArrayList<DiningHallGroup>();
		DiningHall diningHall = null;
		DiningHallGroup diningHallGroup = new DiningHallGroup();
		if (isLoadFromFile()) {
			for (String key : getDiningUrls().keySet()) {

				try {
					JAXBContext jc = JAXBContext.newInstance(DiningHallGroup.class);
					Unmarshaller um = jc.createUnmarshaller();
					InputStream in = this.getClass().getResourceAsStream(getDiningUrls().get(key).get(0));
					diningHallGroup = (DiningHallGroup) um.unmarshal(in);

				} catch (JAXBException jbe) {
					LOG.error(jbe.getLocalizedMessage(), jbe);
				}
				diningHallGroups.add(diningHallGroup);
			}
		}
		getDao().setDiningHallGroups(diningHallGroups);
	}

	public DiningDao getDao() {
		return dao;
	}

	public void setDao(DiningDao dao) {
		this.dao = dao;
	}

	public Map<String, List<String>> getDiningUrls() {
		return diningUrls;
	}

	public void setDiningUrls(Map<String, List<String>> diningUrls) {
		this.diningUrls = diningUrls;
	}

	public boolean isLoadFromFile() {
		return loadFromFile;
	}

	public void setLoadFromFile(boolean loadFromFile) {
		this.loadFromFile = loadFromFile;
	}
}
