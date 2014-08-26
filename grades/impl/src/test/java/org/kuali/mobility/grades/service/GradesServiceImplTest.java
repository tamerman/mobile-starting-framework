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

package org.kuali.mobility.grades.service;

import java.util.Date;
import java.util.List;

import static junit.framework.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.grades.entity.ModuleResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for the Grades Service
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/GradesSpringBeans.xml")
public class GradesServiceImplTest {

	/**
	 * A reference to the <code>GradesService</code>
	 */
	@Autowired
	@Qualifier("gradesService")
	private GradesService gradesService;

	/**
	 * This test will call the service and retrieve a pre-configured set of results.
	 * This test will simply check that the data is in the order we expected, and also
	 * provide a sanity check that the services still properly call the DAO,
	 */
	@Test
	public void test() {
		List<ModuleResults> results = gradesService.getResults(new Date(), new Date(), "test", "en");
		String str;

		// Check that we have the expected first mark
		str = results.get(0).getExamMark();
		assertEquals("76%", str);

		// Check the participationMarkComment of the second result
		str = results.get(1).getParticipationMarkComment();
		assertEquals("Distinction", str);

		// Check the module code of the third result
		str = results.get(2).getModuleName();
		assertEquals("IOPS 1 21 INTRO HUM GEO", str);

	}
}
