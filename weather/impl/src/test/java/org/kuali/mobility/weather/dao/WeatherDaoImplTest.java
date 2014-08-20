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

package org.kuali.mobility.weather.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.weather.entity.Weather;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertTrue;

public class WeatherDaoImplTest {

	private static Logger LOG = LoggerFactory.getLogger(WeatherDaoImplTest.class);

	private static ApplicationContext applicationContext;
	
    @BeforeClass
    public static void createApplicationContext() {
    	WeatherDaoImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/SpringBeans.xml" };
    }

    @Test
	public void testParseWeather() {
		WeatherDaoImpl dao = (WeatherDaoImpl)getApplicationContext().getBean("weatherDao");

		Resource resource = applicationContext.getResource("classpath:weather.xml");

		try {
			
			dao.setUrl(resource.getURL().toString());
			
			Weather weather = dao.parseWeather();

			assertTrue("Failed to parse weather data for pressure.", weather.getPressure() != null);
			
		} catch (Exception e) {
			
			LOG.debug("Failed to find weather.xml in classpath.");
			
			assertTrue("Failed to find weather.xml in classpath.", false);
			
		}
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		WeatherDaoImplTest.applicationContext = applicationContext;
	}

}
