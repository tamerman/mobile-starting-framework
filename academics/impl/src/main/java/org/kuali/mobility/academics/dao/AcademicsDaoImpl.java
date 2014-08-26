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

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.entity.*;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.academics.util.CareerPredicate;
import org.kuali.mobility.academics.util.TermPredicate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class AcademicsDaoImpl implements AcademicsDao, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(AcademicsDaoImpl.class);
	private ApplicationContext applicationContext;
	private List<Term> terms;
	private List<Career> careers;
	private List<Subject> subjects;
	private String emplid;


	public List<Term> getTerms() {
		return terms;
	}

	/**
	 * @return the terms
	 */
	public List<Term> getTerms(final Map<String, String> query) {
		if (null == terms) {
			getTerms();
		}
		return terms;
	}

	/**
	 * @param terms the terms to set
	 */
	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	/**
	 * @return the careers
	 */
	public List<Career> getCareers(final Map<String, String> query) {
		List<Career> tCareers = new ArrayList<Career>();
		if (null != query && query.containsKey(AcademicsConstants.TERM_ID)) {
			Term term = (Term) CollectionUtils.find(terms, new TermPredicate(query.get(AcademicsConstants.TERM_ID)));
			if (term != null) {
				tCareers = term.getCareers();
			}
		} else {
			tCareers = careers;
		}
		return tCareers;
	}

	/**
	 * @param careers the careers to set
	 */
	public void setCareers(List<Career> careers) {
		this.careers = careers;
	}

	/**
	 * @return the subjects
	 */
	public List<Subject> getSubjects(final Map<String, String> query) {
		List<Subject> tSubjects = new ArrayList<Subject>();
		if (null != query
				&& query.containsKey(AcademicsConstants.TERM_ID)) {
			List<Career> tCareer = getCareers(query);

			if (query.containsKey(AcademicsConstants.CAREER_ID) && !query.get(AcademicsConstants.CAREER_ID).equalsIgnoreCase("ALL")) {
				Career career = (Career) CollectionUtils.find(tCareer, new CareerPredicate(query.get(AcademicsConstants.CAREER_ID)));
				tSubjects = career.getSubjects();
			} else {
				for (Career c : tCareer) {
					tSubjects.addAll(c.getSubjects());
				}
				Comparator<Subject> comparator = new Comparator<Subject>() {
					public int compare(Subject c1, Subject c2) {
						return c1.getDescription().compareToIgnoreCase(c2.getDescription());
					}
				};
				Collections.sort(tSubjects, comparator);
			}
		} else {
			tSubjects = subjects;
		}
		return tSubjects;
	}

	/**
	 * @param subjects the subjects to set
	 */
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<CatalogNumber> getCatalogNumbers(Map<String, String> query) {
		List<CatalogNumber> lCatalogNumbers = new ArrayList<CatalogNumber>();
		return lCatalogNumbers;
	}

	public List<Section> getSections(Map<String, String> query) {
		List<Section> lSection = new ArrayList<Section>();
		return lSection;
	}

	public Section getSectionDetail(String termId, String careerId, String subjectId, String catalogNumber, String sectionNumber) {
		Section lSection = (Section) getApplicationContext().getBean("sectionBean");
		return lSection;
	}

	public Section getSectionDetail(Section section) {
		Section lSection = (Section) getApplicationContext().getBean("sectionBean");
		return lSection;
	}

	public SearchResult getSearchResults(Map<String, String[]> query) {
		SearchResult result = new SearchResult();
		return result;
	}

	public List<Section> getMyClassSchedule(final Map<String, String> query) {
		List<Section> lSections = new ArrayList<Section>();
		return lSections;
	}

	/**
	 * @return the applicationContext
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * @param applicationContext the applicationContext to set
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
