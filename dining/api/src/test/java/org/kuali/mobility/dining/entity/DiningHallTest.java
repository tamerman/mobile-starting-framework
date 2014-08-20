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

package org.kuali.mobility.dining.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.shared.entity.Address;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class DiningHallTest {
    private static final Logger LOG = LoggerFactory.getLogger(DiningHallTest.class);


    @Test
    public void testMarshallObjectTree() {
        DiningHall diningHall = new DiningHall();
        diningHall.setType("hall");
        diningHall.setCampus("main");
        diningHall.setName("Test Hall A");
        Building building = new Building();
        building.setName("Building A");
        building.setCode("BldgA");
        building.setLatitude("38.2341N");
        building.setLongitude("92.1345W");
        Address address = new Address();
        address.setId(new Long(42));
        address.setStreet1("356 ABC Street");
        address.setStreet2("Suite K");
        address.setCity("Townsville");
        address.setState("MT");
        address.setPostalCode("76532-0000");
        building.setAddress(address);
        diningHall.setBuilding(building);
        diningHall.setErrors(null);
        List<Menu> menus = new ArrayList<Menu>();
        for (int i = 0; i < 3; i++) {
            Menu menu = new Menu();
            menu.setName("Meal " + i);
            menu.setDescription("Phasellus mauris sem, ornare quis adipiscing non, vehicula in orci.");
            List<MenuItemGroup> groups = new ArrayList<MenuItemGroup>();
            for (int j = 0; j < 10; j++) {
                if (j % (i + 1) == 1) {
                    continue;
                } else {
                    MenuItemGroup group = new MenuItemGroup();
                    group.setName("Group " + j);
                    group.setDescription("Proin id lorem vitae erat tristique mattis. Nullam sed pellentesque lorem. Sed auctor porttitor arcu id dapibus.");
                    List<MenuItem> items = new ArrayList<MenuItem>();
                    for (int k = 0; k < 5; k++) {
                        MenuItem item = new MenuItem();
                        item.setName("Food Item " + k);
                        item.setDescription("Cras eleifend risus sit amet neque tincidunt placerat. Vestibulum tincidunt sagittis risus in pharetra. Nunc lacinia eleifend risus.");
                        item.setRatingCount(k % (j + 1));
                        List<String> attributes = new ArrayList<String>();
                        if (k % (3) == 1) {
                            attributes.add("vegetarian");
                        }
                        if (((3 * k) + (j + 1)) % (3) == 0) {
                            attributes.add("gluten free");
                        }
                        if (k * ((i + 1) * (j + 3)) % (3) == 2) {
                            attributes.add("halal");
                        }
                        item.setAttributes(attributes);
                        List<Double> prices = new ArrayList<Double>();
                        prices.add( Math.round((1 + (Math.random() * (6 - 1)))*100.0) / 100.0);
                        if( (0 + (Math.random() * (100 - 0))) < 25 ) {
                            prices.add( Math.round((4 + (Math.random() * (10 - 4)))*100.0) / 100.0);
                        }
                        item.setPrices(prices);
                        List<NutritionalInfo> nutInfo = new ArrayList<NutritionalInfo>();
                        NutritionalInfo ninfo = new NutritionalInfo();
                        ninfo.setName("Calories");
                        ninfo.setValue((k % 2 == 0 ? "381" : "170"));
                        ninfo.setUnits("Cal");
                        nutInfo.add(ninfo);
                        ninfo = new NutritionalInfo();
                        ninfo.setName("Serving Size");
                        ninfo.setUnits((k % 3 == 0 ? "g" : "each"));
                        ninfo.setValue((k % 3 == 0 ? (k % 2 == 0 ? "180" : "210") : "1"));
                        nutInfo.add(ninfo);
                        item.setNutritionalInfo(nutInfo);
                        items.add(item);
                    }
                    group.setMenuItems(items);
                    groups.add(group);
                }
                menu.setItemGroups(groups);
            }
            menus.add(menu);
        }
        diningHall.setMenus(menus);
        diningHall.setMenuUrls(null);
        try {
            // Write to file
            JAXBContext jc = JAXBContext.newInstance(DiningHall.class);
            //Create marshaller
            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //Marshal object into file.
            m.marshal(diningHall, baos);

            LOG.debug(baos.toString());
        } catch (JAXBException jbe) {
            LOG.error(jbe.getLocalizedMessage(), jbe);
        }
    }

    @Test
    public void testUnMarshallObjectTree() {
        DiningHallGroup diningHallGroup = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(DiningHallGroup.class);
            Unmarshaller um = jc.createUnmarshaller();
            InputStream in = this.getClass().getResourceAsStream("/DiningHall.xml");
            diningHallGroup = (DiningHallGroup) um.unmarshal(in);

        } catch (JAXBException jbe) {
            LOG.error(jbe.getLocalizedMessage(), jbe);
        }

        assertFalse("Dining hall object is null and should not be.", diningHallGroup == null);
    }

}
