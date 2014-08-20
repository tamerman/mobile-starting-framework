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

package org.kuali.mobility.math.geometry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class SphericalTest {
    private static final Logger LOG = LoggerFactory.getLogger(SphericalTest.class);

    @Test
    public void testComputeHeading() {
        double expectedHeading = -142.8573142931038;
        Point p1 = new Point(42.276674,-83.738029); // Ann Arbor
        Point p2 = new Point(39.788,-86.165);       // Bloomington

        double heading = Spherical.computeHeading(p1, p2);

        LOG.debug("Must travel "+heading+" from Ann Arbor to Bloomington");

        assertTrue("Heading doesn't match. Found "+heading+". Expected "+expectedHeading, heading == expectedHeading);
    }

    @Test
    public void testComputeDistanceBetween() {
        double expectedDistance = 343887.6338228662;
        Point p1 = new Point(42.276674,-83.738029); // Ann Arbor
        Point p2 = new Point(39.788,-86.165);       // Bloomington

        double distance = Spherical.computeDistanceBetween(p1, p2);

        LOG.debug("Bloomington is "+distance+" meters from Ann Arbor.");

        assertTrue("Distance doesn't match. Found "+distance+". Expected "+expectedDistance,distance == expectedDistance);
    }
}
