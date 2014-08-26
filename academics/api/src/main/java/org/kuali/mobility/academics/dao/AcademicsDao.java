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

import org.kuali.mobility.academics.entity.*;

import java.util.List;
import java.util.Map;

public interface AcademicsDao {

	public List<Term> getTerms();

	public List<Term> getTerms(Map<String, String> query);

	public void setTerms(List<Term> terms);

	public List<Career> getCareers(Map<String, String> query);

	public void setCareers(List<Career> careers);

	public List<Subject> getSubjects(final Map<String, String> query);

	public void setSubjects(List<Subject> subjects);

	public List<CatalogNumber> getCatalogNumbers(Map<String, String> query);

	public List<Section> getSections(Map<String, String> query);

	public Section getSectionDetail(Section section);

	public Section getSectionDetail(String termId, String careerId, String subjectId, String catalogNumber, String sectionNumber);

	public SearchResult getSearchResults(Map<String, String[]> query);

	public List<Section> getMyClassSchedule(Map<String, String> query);
}
