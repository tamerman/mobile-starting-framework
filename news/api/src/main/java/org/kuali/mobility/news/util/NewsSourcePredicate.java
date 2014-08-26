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

package org.kuali.mobility.news.util;

import org.apache.commons.collections.Predicate;
import org.kuali.mobility.news.entity.NewsSource;

public class NewsSourcePredicate implements Predicate {

	private Long parentId;
	private Boolean active;

	public NewsSourcePredicate(final Long parentId, final Boolean active) {
		this.setParentId(parentId);
		this.setActive(active);
	}

	@Override
	public boolean evaluate(Object obj) {
		boolean parentMatch = false;
		boolean activeMatch = false;

		if (null != obj
				&& obj instanceof NewsSource) {
			if (getParentId() == null) {
				parentMatch = true;
			} else if (((NewsSource) obj).getParentId() == null) {
				if (getParentId().intValue() == 0) {
					parentMatch = true;
				} else {
					parentMatch = false;
				}
			} else if (getParentId().compareTo(((NewsSource) obj).getParentId()) == 0) {
				parentMatch = true;
			}
			if (isActive() == null) {
				activeMatch = true;
			} else if (isActive().booleanValue() == ((NewsSource) obj).isActive()) {
				activeMatch = true;
			}
		}
		return parentMatch && activeMatch;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
