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

import org.kuali.mobility.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherInitBean {

	private static Logger LOG = LoggerFactory.getLogger(WeatherInitBean.class);

	private WeatherService weatherService;
	private int minutesToRefresh;
	
	private static Thread backgroundThread = null;

	public void init() {
		backgroundThread = new Thread(new BackgroundThread());
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}

	public void cleanup() {
		LOG.info("Cleaning up weather.");
	}

	public WeatherService getWeatherService() {
		return weatherService;
	}

	public void setWeatherService(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	public int getMinutesToRefresh() {
		return minutesToRefresh;
	}

	public void setMinutesToRefresh(int minutesToRefresh) {
		this.minutesToRefresh = minutesToRefresh;
	}

	private class BackgroundThread implements Runnable {

		public void run() {
			while (true) {
				try {
					weatherService.refreshWeather();
					try {
						Thread.sleep(1000 * 60 * getMinutesToRefresh());
					} catch (InterruptedException e) {
						LOG.error(e.getMessage(), e);
					}
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}

	}

}
