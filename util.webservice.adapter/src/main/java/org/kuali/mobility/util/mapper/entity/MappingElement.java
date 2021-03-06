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

package org.kuali.mobility.util.mapper.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class MappingElement implements Serializable {

	private static final long serialVersionUID = -7978581905560378671L;

	@XStreamAsAttribute
	private String mapTo;
	@XStreamAsAttribute
	private String mapFrom;
	@XStreamAsAttribute
	private boolean list;
	@XStreamAsAttribute
	private String type;
	@XStreamAsAttribute
	private boolean isAttribute;

	/**
	 * @return the list
	 */
	public boolean isList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(final boolean list) {
		this.list = list;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type == null ? "java.lang.String" : type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public String getMapFrom() {
		return (mapFrom == null || mapFrom.isEmpty() ? getMapTo() : mapFrom);
	}

	public void setMapFrom(String mapFrom) {
		this.mapFrom = mapFrom;
	}

	public boolean isAttribute() {
		return isAttribute;
	}

	public void setAttribute(boolean isAttribute) {
		this.isAttribute = isAttribute;
	}
}
