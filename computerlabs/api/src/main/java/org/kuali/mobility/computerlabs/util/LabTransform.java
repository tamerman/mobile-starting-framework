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

package org.kuali.mobility.computerlabs.util;

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.LabImpl;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class LabTransform implements Transformer {

	@Override
	public LabImpl transform(Object obj) {
		LabImpl proxy = null;
		if (obj instanceof LabImpl) {
			proxy = (LabImpl) obj;
		} else if (obj instanceof Lab) {
			proxy = new LabImpl();
			proxy.setAvailability(((Lab) obj).getAvailability());
			proxy.setBuilding(((Lab) obj).getBuilding());
			proxy.setBuildingCode(((Lab) obj).getBuildingCode());
			proxy.setCampus(((Lab) obj).getCampus());
			proxy.setFloor(((Lab) obj).getFloor());
			proxy.setFloorplan(((Lab) obj).getFloorplan());
			proxy.setLab(((Lab) obj).getLab());
			proxy.setLinuxAvailability(((Lab) obj).getLinuxAvailability());
			proxy.setMacAvailability(((Lab) obj).getMacAvailability());
			proxy.setSoftwareAvailability(((Lab) obj).getSoftwareAvailability());
			proxy.setWindowsAvailability(((Lab) obj).getWindowsAvailability());
		}
		return proxy;
	}
}
