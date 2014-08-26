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

package org.kuali.mobility.weather.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "weather")
@XmlAccessorType(XmlAccessType.FIELD)
public class Weather {

	@XmlElement(name = "forecasts")
	private List<HashMap<String, String>> forecasts;
	@XmlElement(name = "dailyForecasts")
	private List<Forecast> dailyForecasts;

	private String currentCondition;
	private String temperature;
	private String humidity;
	private String windDirection;
	private String windSpeed;
	private String pressure;

	public Weather() {
		forecasts = new ArrayList<HashMap<String, String>>();
		dailyForecasts = new ArrayList<Forecast>();
	}


	public List<HashMap<String, String>> getForecasts() {
		return forecasts;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public void setForecasts(List<HashMap<String, String>> forecasts) {
		this.forecasts = forecasts;
	}

	public String getCurrentCondition() {
		return currentCondition;
	}

	public void setCurrentCondition(String currentCondition) {
		this.currentCondition = currentCondition;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWind() {
		String speed = getWindSpeed();
		if ("0".equals(speed)) {
			return "0 MPH";
		}
		return speed + " MPH from the " + getWindDirection();
	}

	public String getWindDirection() {
		String text = "";
		try {
			int direction = Integer.parseInt(windDirection);
			if (direction > 348.75 && direction <= 11.25) {
				text = "North";
			}
			if (direction > 11.25 && direction <= 33.75) {
				text = "North Northeast";
			}
			if (direction > 33.75 && direction <= 56.25) {
				text = "Northeast";
			}
			if (direction > 56.25 && direction <= 78.75) {
				text = "East Northeast";
			}
			if (direction > 78.75 && direction <= 101.25) {
				text = "East";
			}
			if (direction > 101.25 && direction <= 123.75) {
				text = "East Southeast";
			}
			if (direction > 123.75 && direction <= 146.25) {
				text = "Southeast";
			}
			if (direction > 146.25 && direction <= 168.75) {
				text = "South Southeast";
			}
			if (direction > 168.75 && direction <= 191.25) {
				text = "South";
			}
			if (direction > 191.25 && direction <= 213.75) {
				text = "South Southwest";
			}
			if (direction > 213.75 && direction <= 236.25) {
				text = "Southwest";
			}
			if (direction > 236.25 && direction <= 258.75) {
				text = "West Southwest";
			}
			if (direction > 258.75 && direction <= 281.25) {
				text = "West";
			}
			if (direction > 281.25 && direction <= 303.75) {
				text = "West Northwest";
			}
			if (direction > 303.75 && direction <= 326.25) {
				text = "Northwest";
			}
			if (direction > 326.25 && direction <= 348.75) {
				text = "North Northwest";
			}
		} catch (Exception e) {
		}
		return text;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public List<Forecast> getDailyForecasts() {
		return dailyForecasts;
	}

	public void setDailyForecasts(List<Forecast> dailyForecasts) {
		this.dailyForecasts = dailyForecasts;
	}

}
