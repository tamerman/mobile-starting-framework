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

package org.kuali.mobility.events.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.kuali.mobility.events.entity.Category;
import org.kuali.mobility.events.entity.CategoryImpl;
import org.kuali.mobility.events.entity.DayImpl;

/**
 * @author Aniruddha Jani <ajani@vivantech.com>
 */
public class CategoryTransform implements Transformer {

	@Override
	public CategoryImpl transform(Object obj) {
		CategoryImpl proxy = null;

		if (obj instanceof Category) {
			proxy = new CategoryImpl();
			proxy.setCategoryId(((Category) obj).getCategoryId());
			proxy.setCampus(((Category) obj).getCampus());
			proxy.setOrder(((Category) obj).getOrder());
			proxy.setReturnPage(((Category) obj).getReturnPage());
			proxy.setTitle(((Category) obj).getTitle());
			proxy.setUrlString(((Category) obj).getUrlString());
			proxy.setHasEvents(((Category) obj).getHasEvents());

			List<DayImpl> days = new ArrayList<DayImpl>();
			CollectionUtils.collect(((Category) obj).getDays(), new DayTransform(), days);
			proxy.setDays(days);
		}

		return proxy;
	}

}
