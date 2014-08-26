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

package org.kuali.mobility.dining.entity;

import org.kuali.mobility.shared.entity.ErrorMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@XmlRootElement(name = "diningHall")
@XmlAccessorType(XmlAccessType.FIELD)
public class DiningHall {
	private String name;
	private String campus;
	private Building building;
	private String type;
	@XmlElement(name = "menuUrl")
	private List<String> menuUrls;
	@XmlElement(name = "menu")
	private List<? extends Menu> menus;
	@XmlElement(name = "error")
	private List<? extends ErrorMessage> errors;
	private int sortPosition;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCampus() {
		return campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getMenuUrls() {
		return menuUrls;
	}

	public void setMenuUrls(List<String> menuUrls) {
		this.menuUrls = menuUrls;
	}

	public List<? extends Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<? extends Menu> menus) {
		this.menus = menus;
	}

	public List<? extends ErrorMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<? extends ErrorMessage> errors) {
		this.errors = errors;
	}

	public int getSortPosition() {
		return sortPosition;
	}

	public void setSortPosition(int sortPosition) {
		this.sortPosition = sortPosition;
	}
}
