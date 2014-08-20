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
import org.kuali.mobility.dining.entity.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Repository
public class DiningDaoImpl implements DiningDao {
    private static final Logger LOG = LoggerFactory.getLogger(DiningDaoImpl.class);

    private List<DiningHall> diningHalls;
    private List<DiningHallGroup> diningHallGroups;

    public List<DiningHallGroup> getDiningHallGroups() {
        return diningHallGroups;
    }

    public void setDiningHallGroups(List<DiningHallGroup> diningHallGroups) {
        this.diningHallGroups = diningHallGroups;
    }

    public List<DiningHall> getDiningHalls() {
        return diningHalls;
    }

    public List<Menu> getMenus() {
        List<DiningHall> diningHallList = new ArrayList<DiningHall>();
        List<Menu> menus = new ArrayList<Menu>();
        for( DiningHallGroup diningHallGroup : getDiningHallGroups() ) {
              diningHallList = diningHallGroup.getDiningHalls();
              for(DiningHall diningHall : diningHallList) {
                    menus.addAll(diningHall.getMenus());
              }
        }
        return menus;
    }

    public List<MenuItem> getMenuItems() {
        List<MenuItem> menuItems = new ArrayList<MenuItem>();
        List<DiningHall> diningHallList = new ArrayList<DiningHall>();
        for( DiningHallGroup diningHallGroup : getDiningHallGroups() ) {
            diningHallList = diningHallGroup.getDiningHalls();
            for(DiningHall diningHall : diningHallList) {
                for(Menu menu : diningHall.getMenus()) {
                    for(MenuItemGroup group : menu.getItemGroups()) {
                        menuItems.addAll(group.getMenuItems());
                    }
                }
            }
        }
        return menuItems;
    }

    public void setDiningHalls(List<DiningHall> diningHalls) {
        this.diningHalls = diningHalls;
    }

    public void setMenus(List<Menu> menus) {
        // do nothing
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        // do nothing
    }

    /*
    private String getXmlUrl(String campusCode) {
        String url = null;
        List<String> urls = getDiningUrls().get(campusCode);
        if (urls != null && urls.size() > 0) {
            url = urls.get(0);
        }
        return url;
    }

    @SuppressWarnings("unchecked")
    private List<Menu> parseUrl(String url){
        List<Menu> menus = new ArrayList<Menu>();
        try {
            // Set up formatters
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            // Process the XML
            Document doc = retrieveDocumentFromUrl(url, 5000, 5000);
            Element root = doc.getRootElement();
            List<Element> xmlMenus = root.getChildren("menu");
            for (Iterator<Element> iterator = xmlMenus.iterator(); iterator.hasNext();) {
                Element xmlMenu = iterator.next();
                // Make the date format configurable
                String dateStr = xmlMenu.getChildText("date");
                Menu menu = new Menu();
                try {
                    Date date = sdf.parse(dateStr);
                    menu.setDate(date);
                    List<Element> items = xmlMenu.getChildren("item");
                    for (Iterator<Element> itemItr = items.iterator(); itemItr.hasNext();) {
                        try {
                            Element xmlItem = itemItr.next();
                            String name = xmlItem.getChildText("name");
                            String priceStr = xmlItem.getChildText("price");
                            double price = Double.parseDouble(priceStr);
                            MenuItem item = new MenuItem(name, price);
                            menu.getItems().add(item);
                        } catch (Exception e) {}
                    }
                    menus.add(menu);
                } catch (ParseException e) {}
            }
        } catch (JDOMException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return menus;
    }

    private Document retrieveDocumentFromUrl(String urlStr, int connectTimeout, int readTimeout) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        URL urlObj = new URL(urlStr);
        URLConnection urlConnection = urlObj.openConnection();
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeout);
        doc = builder.build(urlConnection.getInputStream());
        return doc;
    }
    */
}
