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
import org.kuali.mobility.events.entity.Event;
import org.kuali.mobility.events.entity.EventContactImpl;
import org.kuali.mobility.events.entity.EventImpl;

public class EventTransform implements Transformer {


	@Override
	public Object transform(Object obj) {
		EventImpl proxy = null;

		if (obj instanceof EventImpl) {
			proxy = (EventImpl) obj;
		} else if (obj instanceof Event) {
			proxy = new EventImpl();
			proxy.setEventId(((Event) obj).getEventId());
			proxy.setType(((Event) obj).getType());
			proxy.setTitle(((Event) obj).getTitle());
			proxy.setStartDate(((Event) obj).getStartDate());
			proxy.setEndDate(((Event) obj).getEndDate());
			proxy.setDisplayStartTime(((Event) obj).getDisplayStartTime());
			proxy.setDisplayEndTime(((Event) obj).getDisplayEndTime());
			proxy.setDisplayStartDate(((Event) obj).getDisplayStartDate());
			proxy.setDisplayEndDate(((Event) obj).getDisplayEndDate());
			proxy.setLink(((Event) obj).getLink());
			proxy.setLocation(((Event) obj).getLocation());
			proxy.setAllDay(((Event) obj).isAllDay());
			proxy.setCategory(((Event) obj).getCategory());
			proxy.setCost(((Event) obj).getCost());
			proxy.setDescription(((Event) obj).getDescription());
			proxy.setOtherInfo(((Event) obj).getOtherInfo());

			List<EventContactImpl> contacts = new ArrayList<EventContactImpl>();
			CollectionUtils.collect(((Event) obj).getContact(), new EventContactTransform(), contacts);
			proxy.setContact(contacts);
		}
		return proxy;
	}

}
