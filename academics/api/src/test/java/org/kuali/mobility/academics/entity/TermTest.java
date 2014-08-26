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
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class TermTest {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(TermTest.class);

	@Test
	public void testMarshallObjectTree() {
		Term term = new Term();
		term.setId("1234");
		term.setShortDescription("Test Term");
		term.setActive(true);
		term.setDescription("Test Term Description");
		List<Career> careers = new ArrayList<Career>();
		for (int i = 0; i < 3; i++) {
			Career career = new Career();
			career.setId("Career" + i);
			career.setDescription("Description for " + i);
			career.setShortDescription("Career " + i);
			List<Subject> subjects = new ArrayList<Subject>();
			for (int j = 0; j < 3; j++) {
				Subject subject = new Subject();
				subject.setId("Subject" + j);
				subject.setShortDescription("Subject " + j);
				subject.setDescription("Description for " + j);
				subjects.add(subject);
			}
			career.setSubjects(subjects);
			careers.add(career);
		}
		term.setCareers(careers);

		try {
			// Write to file
			JAXBContext jc = JAXBContext.newInstance(Term.class);
			//Create marshaller
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			//Marshal object into file.
			m.marshal(term, baos);

			LOG.debug(baos.toString());
		} catch (JAXBException jbe) {
			LOG.error(jbe.getLocalizedMessage(), jbe);
		}
	}

	@Test
	public void testUnMarshallObjectTree() {
		Term term = null;
		try {
			JAXBContext jc = JAXBContext.newInstance(Term.class);
			Unmarshaller um = jc.createUnmarshaller();
			InputStream in = this.getClass().getResourceAsStream("/ScheduleOfClasses.xml");
			term = (Term) um.unmarshal(in);

		} catch (JAXBException jbe) {
			LOG.error(jbe.getLocalizedMessage(), jbe);
		}

		assertFalse("Term object is null and should not be.", term == null);
		assertTrue("Term had improper number of careers: " + term.getCareers().size(), term.getCareers() != null && term.getCareers().size() == 22);
	}
}
