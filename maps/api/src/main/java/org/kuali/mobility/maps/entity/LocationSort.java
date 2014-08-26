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


package org.kuali.mobility.maps.entity;

import java.util.Comparator;

public class LocationSort implements Comparator<Location> {

	public int compare(Location loc1, Location loc2) {
		String cmp1 = loc1.getName();
		String cmp2 = loc2.getName();
		for (int cmp1pos = 0; cmp1pos < cmp1.length(); cmp1pos++) {
			char chr1 = cmp1.charAt(cmp1pos);
			if (cmp1pos < cmp2.length()) {
				// Still possible to compare at same position
				char chr2 = cmp2.charAt(cmp1pos);
				boolean chr1IsDigit = Character.isDigit(chr1);
				boolean chr2IsDigit = Character.isDigit(chr2);
				if (chr1IsDigit && chr2IsDigit) {
					// Compare numbers
					if (chr1 != chr2) {
						return chr1 - chr2;
					}
				} else if (chr1IsDigit && !chr2IsDigit) {
					// chr1 is a number and chr2 is not.
					return 1;
				} else if (!chr1IsDigit && chr2IsDigit) {
					return -1;
				} else {
					// Compare strings
					if (chr1 != chr2) {
						return chr1 - chr2;
					}
				}
			} else {
				//Our position in cmp1 can no longer be compared to cmp2. loc1 > loc2
				return 1;
			}
		}
		if (cmp2.length() > cmp1.length()) {
			// cmp2 was able to be compared further but cmp1 ran out of characters. loc2 > loc1
			return -1;
		}
		return 0;
	}

}
