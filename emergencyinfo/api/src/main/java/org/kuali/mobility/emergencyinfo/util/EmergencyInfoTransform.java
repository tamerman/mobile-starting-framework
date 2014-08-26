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

package org.kuali.mobility.emergencyinfo.util;

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfo;
import org.kuali.mobility.emergencyinfo.entity.EmergencyInfoImpl;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class EmergencyInfoTransform implements Transformer {

	@Override
	public EmergencyInfoImpl transform(Object obj) {
		EmergencyInfoImpl proxy = null;

		if (obj instanceof EmergencyInfoImpl) {
			proxy = (EmergencyInfoImpl) obj;
		} else if (obj instanceof EmergencyInfo) {
			proxy = new EmergencyInfoImpl();
			proxy.setCampus(((EmergencyInfo) obj).getCampus());
			proxy.setEmergencyInfoId(((EmergencyInfo) obj).getEmergencyInfoId());
			proxy.setLink(((EmergencyInfo) obj).getLink());
			proxy.setOrder(((EmergencyInfo) obj).getOrder());
			proxy.setTitle(((EmergencyInfo) obj).getTitle());
			proxy.setType(((EmergencyInfo) obj).getType());
			proxy.setVersionNumber(((EmergencyInfo) obj).getVersionNumber());
		}
		return proxy;
	}
}
