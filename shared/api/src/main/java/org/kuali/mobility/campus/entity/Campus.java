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

package org.kuali.mobility.campus.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * Defines a campus
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@XmlRootElement(name = "campus")
public class Campus implements Serializable {

	private static final long serialVersionUID = -8000615503651726243L;

	private String name;

	private String code;

	private List<String> tools;

	public Campus() {
		tools = new ArrayList<String>();
	}

	public List<String> getTools() {
		return tools;
	}

	public void setTools(List<String> tools) {
		this.tools = tools;
	}

	/**
	 * @return the name of the campus
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name of the campus
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the code that uniquely identifies this campus
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code that uniquely identifies this campus
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
