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

package org.kuali.mobility.events.entity;

import java.util.List;

public interface Category {

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getReturnPage()
	 */
	public abstract String getReturnPage();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setReturnPage(java.lang.String)
	 */
	public abstract void setReturnPage(String returnPage);

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getDays()
	 */
	public abstract List<? extends Day> getDays();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setDays(java.util.List)
	 */
	public abstract void setDays(List<? extends Day> days);

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getCategoryId()
	 */
	public abstract String getCategoryId();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setCategoryId(java.lang.String)
	 */
	public abstract void setCategoryId(String categoryId);

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getTitle()
	 */
	public abstract String getTitle();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setTitle(java.lang.String)
	 */
	public abstract void setTitle(String title);

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getOrder()
	 */
	public abstract int getOrder();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setOrder(int)
	 */
	public abstract void setOrder(int order);

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#getCampus()
	 */
	public abstract String getCampus();

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.Category#setCampus(java.lang.String)
	 */
	public abstract void setCampus(String campus);

	public abstract String getUrlString();

	public abstract void setUrlString(String url);

	public abstract boolean getHasEvents();
	
	public abstract void setHasEvents(boolean hasEvents);
}
