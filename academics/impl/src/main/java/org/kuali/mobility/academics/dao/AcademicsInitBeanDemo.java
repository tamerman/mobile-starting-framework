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

package org.kuali.mobility.academics.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.entity.Career;
import org.kuali.mobility.academics.entity.Subject;
import org.kuali.mobility.academics.entity.Term;
import org.kuali.mobility.shared.InitBean;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class AcademicsInitBeanDemo implements InitBean {
	private static Logger LOG = LoggerFactory.getLogger(AcademicsInitBeanDemo.class);

	@Resource(name = "academicsDao")
	private AcademicsDao dao;

	public void loadData() {
		List<Term> terms = new ArrayList<Term>();
		try {
			Term term = null;
			JAXBContext jc = JAXBContext.newInstance(Term.class);
			Unmarshaller um = jc.createUnmarshaller();
			InputStream in = this.getClass().getResourceAsStream("/ScheduleOfClasses.xml");
			term = (Term) um.unmarshal(in);
			terms.add(term);
		} catch (JAXBException jbe) {
			LOG.error(jbe.getLocalizedMessage(), jbe);
		}
		getDao().setTerms(terms);
		List<Career> careers = new ArrayList<Career>();
		List<Subject> subjects = new ArrayList<Subject>();
		careers.addAll(terms.get(0).getCareers());
		for (Career c : careers) {
			subjects.addAll(c.getSubjects());
		}
		getDao().setCareers(careers);
		getDao().setSubjects(subjects);
	}

	public AcademicsDao getDao() {
		return dao;
	}

	public void setDao(AcademicsDao dao) {
		this.dao = dao;
	}
}
