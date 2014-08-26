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

package org.kuali.mobility.academics.entity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class CatalogNumberTest {
	private static final Logger LOG = LoggerFactory.getLogger(CatalogNumberTest.class);

//    @Test
//    public void testMarshallObjectTree() {
//        CatalogNumber catalogNumber = new CatalogNumber();
//        catalogNumber.setNumber("1234567");
//        catalogNumber.setDescription("Random Course Description");
//        List<Section> sections = new ArrayList<Section>();
//        for(int i=0; i<3; i++) {
//            Section section = new Section();
//            section.setNumber(String.valueOf(100+(Math.random()*(20-1))));
//            section.setAdditionalInfo("Additional Info");
//            section.setAvailableSeats(String.valueOf(30+(Math.random()*(30-1))));
//            section.setCatalogNumber(catalogNumber.getNumber());
//            section.setClassTopic("Class Topic");
//            section.setCombinedSectionId("Sec 12345");
//            section.setCourseDescription("Course description");
//            section.setCourseTitle("Course Title");
//            section.setCreditHours(String.valueOf((1+(Math.random()*(5-1)))));
//            section.setEndDate("2014-03-31");
//            section.setStartDate("2014-01-12");
//            section.setEnrollmentCapacity("50");
//            section.setEnrollmentStatus("open");
//            section.setEnrollmentTotal("50");
//            section.setRepeatCode("R");
//            section.setSessionDescription("Session Description");
//            List<SectionMeeting> meetings = new ArrayList<SectionMeeting>();
//            for(int j=0; j<2; j++) {
//                SectionMeeting meeting = new SectionMeeting();
//                meeting.setNumber(String.valueOf(j));
//                meeting.setDays("MWF");
//                meeting.setLocation("ABC 1345");
//                meeting.set
//            }
//            section.setMeetings(meetings);
//        }
//        catalogNumber.setSections(sections);
//        try {
//            // Write to file
//            JAXBContext jc = JAXBContext.newInstance(CatalogNumber.class);
//            //Create marshaller
//            Marshaller m = jc.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            //Marshal object into file.
//            m.marshal(catalogNumber, baos);
//
//            LOG.debug(baos.toString());
//        } catch (JAXBException jbe) {
//            LOG.error(jbe.getLocalizedMessage(), jbe);
//        }
//    }

	@Test
	public void testUnMarshallObjectTree() {
		CatalogNumber catalogNumber = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(CatalogNumber.class);
			Unmarshaller um = jc.createUnmarshaller();
			InputStream in = this.getClass().getResourceAsStream("/CatalogNumber.xml");
			catalogNumber = (CatalogNumber) um.unmarshal(in);

		} catch (JAXBException jbe) {
			LOG.error(jbe.getLocalizedMessage(), jbe);
		}

		assertFalse("Catalog number object is null and should not be.", catalogNumber == null);
		assertTrue("Catalog number had improper number of sections: " + catalogNumber.getSections().size(), catalogNumber.getSections() != null && catalogNumber.getSections().size() == 32);
	}
}
