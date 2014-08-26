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
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.shared.InitBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class AcademicsInitBean implements InitBean {
	private static Logger LOG = LoggerFactory.getLogger(AcademicsInitBean.class);

	@Resource(name = "academicsDao")
	private AcademicsDao dao;

	public void loadData() {
		LOG.info("Initializing Academics...");
		try {
			LOG.info("Loading academics bootstrap data.");
			if (null != getDao()) {
				List<Term> terms = getDao().getTerms();
				List<Career> careers = new ArrayList<Career>();
				List<Subject> subjects = new ArrayList<Subject>();

				for (Term t : terms) {
					Map<String, String> query = new HashMap<String, String>();
					query.put(AcademicsConstants.TERM_ID, t.getId());
					List<Career> lCareers = getDao().getCareers(query);
					for (Career c : lCareers) {
						Map<String, String> query2 = new HashMap<String, String>();
						query2.put(AcademicsConstants.TERM_ID, t.getId());
						query2.put(AcademicsConstants.CAREER_ID, c.getId());
						List<Subject> lSubjects = getDao().getSubjects(query2);

						for (Subject s : lSubjects) {
							if (!subjects.contains(s)) {
								subjects.add(s);
							}
						}

						if (!careers.contains(c)) {
							careers.add(c);
						}
					}
				}
				LOG.debug("Setting academics bootstrap data.");
				getDao().setCareers(careers);
				getDao().setSubjects(subjects);
				getDao().setTerms(terms);
				LOG.debug("Loaded " + careers.size() + " careers.");
				LOG.debug("Loaded " + subjects.size() + " subjects.");
			}
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage(), e);
		}
		LOG.info("Academics initialization complete.");
	}

	/**
	 * @return the dao
	 */
	public AcademicsDao getDao() {
		return dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(AcademicsDao dao) {
		this.dao = dao;
	}

}
