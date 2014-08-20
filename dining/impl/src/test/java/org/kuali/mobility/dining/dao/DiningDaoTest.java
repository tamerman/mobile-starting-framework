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

package org.kuali.mobility.dining.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.dining.entity.DiningHallGroup;
import org.kuali.mobility.dining.entity.Menu;
import org.kuali.mobility.dining.entity.MenuItem;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import java.util.List;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class DiningDaoTest {
    private static final Logger LOG = LoggerFactory.getLogger(DiningDaoTest.class);

    @Resource(name="diningDao")
    private DiningDao dao;
    @Resource(name="diningInitBean")
    private DiningInitBean diningInitBean;

    @Before
    public void preTest() {
        getDiningInitBean().loadData();
    }

    @Test
    public void testGetDiningHallGroups() {
        List<DiningHallGroup> diningHallGroups = null;
        diningHallGroups = getDao().getDiningHallGroups();
        assertFalse("Dining Halls are null and should not be.", diningHallGroups==null);
        assertFalse("Dining Halls is empty and should not be.", diningHallGroups.isEmpty());
        assertTrue("Expected 2 dining halls and found otherwise.", diningHallGroups.size()==2);
    }

    @Test
    public void testGetMenus() {
        List<Menu> menus = null;
        menus = getDao().getMenus();
        assertFalse("Menus are null and should not be.", menus==null);
        assertFalse("Menus is empty and should not be.", menus.isEmpty());
        assertTrue("Expected 6 menus and found otherwise.", menus.size()==6);
    }

    @Test
    public void testGetMenuItems() {
        List<MenuItem> items = null;
        items = getDao().getMenuItems();
        assertFalse("Menu Items are null and should not be.", items==null);
        assertFalse("Menu Items is empty and should not be.", items.isEmpty());
    }

    public DiningDao getDao() {
        return dao;
    }

    public void setDao(DiningDao dao) {
        this.dao = dao;
    }

    public DiningInitBean getDiningInitBean() {
        return diningInitBean;
    }

    public void setDiningInitBean(DiningInitBean diningInitBean) {
        this.diningInitBean = diningInitBean;
    }
}
