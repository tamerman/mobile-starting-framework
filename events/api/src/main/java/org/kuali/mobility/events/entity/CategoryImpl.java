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

package org.kuali.mobility.events.entity;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "category")
public class CategoryImpl implements Serializable, Category {

	private static final long serialVersionUID = 2433472025839184720L;
	private String categoryId;
	private String title;
	private String returnPage;

	private List<DayImpl> days;
	private int order;
	private String campus;
	private String urlString;
	private boolean hasEvents;

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getReturnPage()
	 */
	@Override
	public String getReturnPage() {
		return returnPage;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setReturnPage(java.lang.String)
	 */
	@Override
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getDays()
	 */
	@XmlElement(name = "days")
	@Override
	public List<DayImpl> getDays() {
		return days;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setDays(java.util.List)
	 */
	@Override
	public void setDays(List<? extends Day> days) {
		this.days = (List<DayImpl>) (List<?>) days;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getCategoryId()
	 */
	@Override
	public String getCategoryId() {
		return categoryId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setCategoryId(java.lang.String)
	 */
	@Override
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getTitle()
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setTitle(java.lang.String)
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getOrder()
	 */
	@Override
	public int getOrder() {
		return order;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setOrder(int)
	 */
	@Override
	public void setOrder(int order) {
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getCampus()
	 */
	@Override
	public String getCampus() {
		return campus;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setCampus(java.lang.String)
	 */
	@Override
	public void setCampus(String campus) {
		this.campus = campus;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getUrlString()
	 */
	@Override
	public String getUrlString() {
		return urlString;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setUrlString(java.lang.String)
	 */
	@Override
	public void setUrlString(String url) {
		this.urlString = url;
	}

	@XmlElement(name = "hasEvents")
	@Override
	public boolean getHasEvents() {
		return hasEvents;
	}

	@Override
	public void setHasEvents(boolean hasEvents) {
		this.hasEvents = hasEvents;
	}
}
