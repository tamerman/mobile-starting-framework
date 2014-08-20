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

package org.kuali.mobility.academics.service;

import org.kuali.mobility.academics.dao.AcademicsDao;
import org.kuali.mobility.academics.entity.*;

import javax.jws.WebService;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@WebService
public interface AcademicsService {

	public void setDao(AcademicsDao dao);

	public AcademicsDao getDao();

	public List<Term> getTerms();

	public Term getTerm(String termId);

	public List<Career> getCareers(String termId);

	public List<Subject> getSubjects(String termId, String careerId);

	public List<CatalogNumber> getCatalogNumbers(String termId, String subjectId);

	public List<Section> getSections(String termId, String subjectId, String catalogNumber, String careerId);

	public Section getSection( String sectionUID );
	
	public Section getSectionDetail(Section section);

    public Section getSectionDetail(String termId, String careerId, String subjectId, String catalogNumber, String sectionNumber);

	//public List<Section> getSearchResults(String termId, String careerId, String subjectId, String instructor, String searchCriteria, String filterCriteria, String showOpen, String distributionReq, String otherReq, String keyword);

    public SearchResult getClassSearchResults(String data);
    public SearchResult getKeywordSearch(String termId, String keyword);

    public SearchResult getSearchResults(Map<String, String[]> query);
}
