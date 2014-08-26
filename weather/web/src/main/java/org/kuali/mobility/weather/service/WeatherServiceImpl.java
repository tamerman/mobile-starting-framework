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

package org.kuali.mobility.weather.service;

import org.kuali.mobility.weather.dao.WeatherDao;
import org.kuali.mobility.weather.entity.Forecast;
import org.kuali.mobility.weather.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.*;

public class WeatherServiceImpl implements WeatherService {

	private static Logger LOG = LoggerFactory.getLogger(WeatherServiceImpl.class);

	private WeatherDao weatherDao;

	private Weather weatherData;

	@GET
	@Path("/getWeatherForecast")
	@Override
	public Weather getWeatherForecast() {
		if (weatherData == null) {
			weatherData = weatherDao.parseWeather();
		}
		List<Forecast> forecasts = new ArrayList<Forecast>();
		Iterator it = weatherData.getForecasts().iterator();
		while (it.hasNext()) {
			LOG.info("---- New Forecast ----");
			HashMap map = (HashMap) it.next();
			Set<String> keySet = map.keySet();

			Forecast forecast = new Forecast();
			forecast.setIconLink((String) map.get("iconLink"));
			forecast.setName((String) map.get("name"));
			forecast.setText((String) map.get("text"));

			forecasts.add(forecast);

			Iterator<String> keys = keySet.iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				LOG.info("Key" + key + ": " + map.get(key));
			}
		}

		weatherData.setDailyForecasts(forecasts);

		return weatherData;
	}

	public void refreshWeather() {
		LOG.info("Refreshing weather cache...");
		Weather cache = weatherDao.parseWeather();
		setWeatherData(cache);
		LOG.info("Finished refreshing weather cache.");
	}

	public Weather getWeatherData() {
		return weatherData;
	}

	public void setWeatherData(Weather weatherData) {
		this.weatherData = weatherData;
	}

	public WeatherDao getWeatherDao() {
		return weatherDao;
	}

	public void setWeatherDao(WeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}

}


