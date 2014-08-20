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

package org.kuali.mobility.events.dao;

import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.shared.InitBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;

/**
 * The backing class for the Page JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class EventInitBean implements InitBean {
    private static final Logger LOG = LoggerFactory.getLogger(EventInitBean.class);

    @Resource(name = "eventDao")
    private EventsDao dao;

    @Override
    public void loadData() {
        LOG.info("Initializing events...");
        getDao().initData(null);
        LOG.info("Refreshing Events...");
        for (Category c : getDao().getCategories()) {
            getDao().initData(null,c.getCategoryId());
        }
        LOG.info("Finished initializing events.");
    }

    public EventsDao getDao() {
        return dao;
    }

    public void setDao(EventsDao dao) {
        this.dao = dao;
    }
}
