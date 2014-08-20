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

package org.kuali.mobility.mdot.listeners;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.StreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.entity.HomeTool;
import org.kuali.mobility.admin.entity.Tool;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.shared.ToolFromXML;
import org.kuali.mobility.shared.Wrapper;
import org.kuali.mobility.shared.listeners.BootstrapListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * KME Bootstrap listener
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
public class KMEBootstrapListener extends BootstrapListener {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(KMEBootstrapListener.class);

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.shared.listeners.BootstrapListener#bootstrapHomeScreenTools(javax.servlet.ServletContextEvent, org.kuali.mobility.admin.service.AdminService)
	 */
	@Override
	public HomeScreen bootstrapHomeScreenTools(ServletContextEvent event, AdminService adminService) {


		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		Wrapper useBootstrapping = (Wrapper) ctx.getBean("useBootstrappingFlag");

		if (!"true".equals(useBootstrapping.getValue())) {
			return null;
		}

		HomeScreen home = new HomeScreen();
		home.setAlias("PUBLIC");
		home.setTitle("Kuali Mobile");
		adminService.saveHomeScreen(home);

		List<HomeTool> tools = new ArrayList<HomeTool>();
        Tool tool;
        HomeTool ht;
		// Jira MOBILITY-127
		ArrayList toolsList = (ArrayList)ctx.getBean("homeScreenToolsConfig");
		LOG.debug( "Attempting to load "+toolsList.size()+" home screen configuration files." );
		for(int i = 0; i < toolsList.size(); i++){
			try {
    		String configFile = (String)toolsList.get(i);
			LOG.debug( "Processing "+configFile );
    		InputStream is = this.getClass().getClassLoader().getResourceAsStream(configFile);

    		XStream xs = new XStream(new DomDriver());
            xs.aliasField("native", ToolFromXML.class, "isNative");
            xs.aliasField("ios", ToolFromXML.class, "isiOS");
            xs.aliasField("android", ToolFromXML.class, "isAndroid");
            xs.aliasField("windows", ToolFromXML.class, "isWindows");
            xs.aliasField("blackberry", ToolFromXML.class, "isBlackberry");
            xs.aliasField("nonnative", ToolFromXML.class, "isNonNative");
            xs.aliasField("viewing-acls", ToolFromXML.class, "viewingACLS");
            xs.aliasField("publishing-acls", ToolFromXML.class, "publishingACLS");
            xs.alias("tool", ToolFromXML.class);
            ToolFromXML t = (ToolFromXML)xs.fromXML(is);

            tool = new Tool();
    		tool.setAlias(t.getAlias());
    		tool.setTitle(t.getTitle());
    		tool.setUrl(t.getUrl());
    		tool.setDescription(t.getDescription());
    		tool.setIconUrl(t.getIconUrl());
    		tool.setRequisites(t.getRequisites());

    		// TODO Deal with ACLs from the *Config.xml files.
            LOG.debug("--- Publishing ACLS " + t.getPublishingACLS().toString());
            LOG.debug("--- Viewing ACLS    " + t.getViewingACLS().toString());

    		adminService.saveTool(tool);
    		ht = new HomeTool(home, tool, i);
    		tools.add(ht);
			} catch( StreamException se ) {
				LOG.error( se.getLocalizedMessage(), se );
			} catch( Exception e ) {
				LOG.error( e.getLocalizedMessage(), e );
			}
        }
		home.setHomeTools(tools);
		return home;
	}

}
