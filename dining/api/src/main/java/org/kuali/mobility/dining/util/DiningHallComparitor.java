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

import org.kuali.mobility.dining.entity.DiningHall;

import java.util.Comparator;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class DiningHallComparitor implements Comparator<DiningHall> {
	public int compare(final DiningHall A, final DiningHall B) {
		int compValue;
		if (null == A) {
			compValue = -42;
		} else if (null == B) {
			compValue = 8;
		} else if (A.getCampus() != null && A.getCampus().equalsIgnoreCase(B.getCampus())) {
			if (A.getSortPosition() == 0 && B.getSortPosition() == 0) {
				// sort by name instead, no sort order provided.
				compValue = (A.getName()).compareTo(B.getName());
			} else {
				compValue = (A.getSortPosition() - B.getSortPosition());
			}
		} else {
			compValue = (A.getCampus()).compareTo(B.getCampus());
		}
		return compValue;
	}
}
