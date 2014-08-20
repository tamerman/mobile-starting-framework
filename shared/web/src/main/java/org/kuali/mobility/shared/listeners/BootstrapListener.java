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

package org.kuali.mobility.shared.listeners;

import org.kuali.mobility.admin.entity.HomeScreen;
import org.kuali.mobility.admin.service.AdminService;
import org.kuali.mobility.configparams.entity.ConfigParam;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.shared.Wrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

public abstract class BootstrapListener implements ServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(BootstrapListener.class);

    @Autowired
    @Qualifier("kmeProperties")
    private Properties kmeProperties;
    

    /**
     * 
     */
	public void contextInitialized(ServletContextEvent event) {
		LOG.info("BootstrapListener started initializing...");

		ApplicationContext ctx = org.springframework.web.context.support.WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());

		Wrapper useBootstrapping = (Wrapper) ctx.getBean("useBootstrappingFlag");
		Bootables bootables = (Bootables) ctx.getBean("bootables");
		if ("true".equals(useBootstrapping.getValue())) {
			
			if (bootables != null){
				bootables.contextInitialized(event);
			}
			
			
            LOG.debug( "Use Bootstrapping flag is true, checking user profile tables." );

            if( null != getKmeProperties()
                && "create-drop".equalsIgnoreCase(kmeProperties.getProperty("shared.hibernate.hbm2ddl.auto"))) {

            }

			LOG.debug( "Use Bootstrapping flag is true, begin data load." );
			AdminService adminService = (AdminService) ctx.getBean("AdminService");
			HomeScreen home = bootstrapHomeScreenTools(event, adminService);
			if (home == null) {
				LOG.debug( "Home is null, terminating bootstrap." );
				return;
			}
			adminService.saveHomeScreen(home);


			// Only ConfigParams are initialized in BootStrapListener. 
			// All other tool specifc initializations are done in their respective *BootListeners.
			ConfigParamService configParamService = (ConfigParamService) ctx.getBean("ConfigParamService");			
			ConfigParam param = new ConfigParam();
			param.setName("Admin.Group.Name");
			param.setValue("KME-ADMINISTRATORS");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("Backdoor.Group.Name");
			param.setValue("KME-BACKDOOR");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("Sakai.Url.Base");
			param.setValue("https://regression.oncourse.iu.edu/oauthdirect/");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("Alerts.CacheUpdate.Minute");
			param.setValue("5");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("News.CacheUpdate.Minute");
			param.setValue("5");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("News.Sample.Size");
			param.setValue("3");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("CAMPUS_STATUS_XML_URL");
			param.setValue("https://test.uisapp2.iu.edu/my2-unt/DataExport.do?__p_dispatch__=campusStatus&campus=");
			configParamService.saveConfigParam(param);

			param = new ConfigParam();
			param.setName("Food.Url.SE");
			param.setValue("http://gus.ius.edu/dining-services/feed/?format=xml");
			configParamService.saveConfigParam(param);

			LOG.info("Count: " + adminService.getAllHomeScreens().size());

			LOG.info("BootstrapListener finished initializing.");
		}
	}

	public abstract HomeScreen bootstrapHomeScreenTools(ServletContextEvent event, AdminService adminService);

	public void contextDestroyed(ServletContextEvent event) {}

    public Properties getKmeProperties() {
        return kmeProperties;
    }

    public void setKmeProperties(Properties kmeProperties) {
        this.kmeProperties = kmeProperties;
    }

}
