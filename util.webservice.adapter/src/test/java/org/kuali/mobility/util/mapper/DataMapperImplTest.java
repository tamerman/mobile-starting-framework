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

package org.kuali.mobility.util.mapper;

import org.junit.Test;
import org.kuali.mobility.util.mapper.domain.Seat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class DataMapperImplTest {

	private static final Logger logger = LoggerFactory.getLogger(DataMapperImplTest.class);

	public static final String DATA_URL = "http://ulib.iupui.edu/utility/seats.php?show=locations&type=data";

	public static final String DATA_FILE = "seatSampleData.xml";
	public static final String MAPPING_FILE = "seatMapping.xml";

	public static final String JSON_DATA_FILE = "sampleJsonData.json";
	public static final String JSON_MAPPING_FILE = "sampleJsonMapping.xml";

	@Test
	public void testMapData() {

		List<Seat> seats = new ArrayList<Seat>();
		try {
			DataMapperImpl mapper = new DataMapperImpl();
			seats = mapper.mapData(seats, DATA_FILE, MAPPING_FILE);
		} catch (ClassNotFoundException cnfe) {
			logger.error(cnfe.toString());
		}

		assertTrue("failed to parse file.", seats != null && seats.size() == 16);

		for (Seat s : seats) {
			logger.debug(s.getLab());
			logger.debug("\t" + s.getFloor());
			logger.debug("\t" + s.getBuildingCode());
			logger.debug("\t" + s.getAvailability());
			logger.debug("\t" + s.getWindowsAvailability());
			logger.debug("\t" + s.getMacAvailability());
		}
	}

	@Test
	public void testMapJsonData() {

		List<Seat> seats = new ArrayList<Seat>();
		try {
			DataMapperImpl mapper = new DataMapperImpl();
			seats = mapper.mapData(seats, JSON_DATA_FILE, JSON_MAPPING_FILE);
		} catch (ClassNotFoundException cnfe) {
			logger.error(cnfe.toString());
		}

		assertTrue("failed to parse file.", seats != null && seats.size() == 3);

		for (Seat s : seats) {
			logger.debug(s.getLab());
			logger.debug("\t" + s.getFloor());
			logger.debug("\t" + s.getBuildingCode());
			logger.debug("\t" + s.getAvailability());
			logger.debug("\t" + s.getWindowsAvailability());
			logger.debug("\t" + s.getMacAvailability());
		}
	}
}
