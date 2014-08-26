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

package org.kuali.mobility.dining.util;

import org.apache.commons.collections.Predicate;
import org.kuali.mobility.dining.entity.DiningHall;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class DiningHallBuildingPredicate implements Predicate {
	private String buildingName;

	public DiningHallBuildingPredicate(String buildingName) {
		super();
		this.setBuildingName(buildingName);
	}

	@Override
	public boolean evaluate(Object obj) {
		boolean match = false;
		if (obj instanceof DiningHall) {
			if (getBuildingName() != null
					&& getBuildingName().equalsIgnoreCase(((DiningHall) obj).getBuilding().getName())) {
				match = true;
			}
		}
		return match;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
}
