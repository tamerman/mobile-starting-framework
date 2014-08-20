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

package org.kuali.mobility.academics.dao;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.entity.CatalogNumber;
import org.kuali.mobility.academics.entity.SearchResult;
import org.kuali.mobility.academics.entity.Section;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.academics.util.CatalogNumberPredicate;
import org.kuali.mobility.shared.entity.ToolMessage;
import org.kuali.mobility.shared.entity.ToolMessageType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class AcademicsDaoImplDemo extends AcademicsDaoImpl {
    private static Logger LOG = LoggerFactory.getLogger(AcademicsDaoImplDemo.class);

    private List<CatalogNumber> catalogNumbers = new ArrayList<CatalogNumber>();
    private Map<String,List<CatalogNumber>> demoCache = new HashMap<String,List<CatalogNumber>>();

    private void initCatalogNumbers() {
        if( getCatalogNumbers().isEmpty() ) {
            for( int i = 0; i < 10; i++) {
                LOG.debug("Attempting to load file named: CatalogNumber"+i+".xml");
                CatalogNumber catalogNumber = null;
                try {
                    JAXBContext jc = JAXBContext.newInstance(CatalogNumber.class);
                    Unmarshaller um = jc.createUnmarshaller();
                    InputStream in = this.getClass().getResourceAsStream("/CatalogNumber"+i+".xml");
                    catalogNumber = (CatalogNumber)um.unmarshal(in);
                    getCatalogNumbers().add(catalogNumber);
                } catch (JAXBException jbe) {
                    LOG.error(jbe.getLocalizedMessage(), jbe);
                }
            }
        }
    }

    public List<CatalogNumber> getCatalogNumbers(Map<String, String> query) {
        List<CatalogNumber> lCatalogNumbers = new ArrayList<CatalogNumber>();

        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(query.get(AcademicsConstants.TERM_ID));
        keyBuilder.append("#");
        keyBuilder.append(query.get(AcademicsConstants.SUBJECT_ID));

        String key = keyBuilder.toString();
        if( getDemoCache().containsKey(key) ) {
            lCatalogNumbers = getDemoCache().get(key);
        } else {
            initCatalogNumbers();
            for( CatalogNumber c : getCatalogNumbers() ) {
                if( (100 - (Math.random() * (100-1))) < 50 ) {
                    lCatalogNumbers.add(c);
                }
            }
            getDemoCache().put(key,lCatalogNumbers);
        }
        return lCatalogNumbers;
    }

    public List<Section> getSections(Map<String, String> query) {
        List<Section> lSection = new ArrayList<Section>();
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(query.get(AcademicsConstants.TERM_ID));
        keyBuilder.append("#");
        keyBuilder.append(query.get(AcademicsConstants.SUBJECT_ID));

        String key = keyBuilder.toString();
        if( getDemoCache().containsKey(key) ) {
            List<CatalogNumber> numbers = getDemoCache().get(key);
            CatalogNumber number = (CatalogNumber)CollectionUtils.find(numbers,new CatalogNumberPredicate(query.get(AcademicsConstants.CATALOG_NUMBER)));
            for(Section s : number.getSections()) {
                s.setSubjectId(query.get(AcademicsConstants.SUBJECT_ID));
                s.setCatalogNumber(query.get(AcademicsConstants.CATALOG_NUMBER));
                lSection.add(s);
            }
        }

        return lSection;
    }

    public Section getSectionDetail(Section section)
    {
        return section;
    }

    public Section getSectionDetail(String termId, String careerId, String subjectId, String catalogNumber, String sectionNumber)
    {
        Section lSection = null;
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(termId);
        keyBuilder.append("#");
        keyBuilder.append(subjectId);
        String key = keyBuilder.toString();
        if( getDemoCache().containsKey(key) ) {
            List<CatalogNumber> numbers = getDemoCache().get(key);
            CatalogNumber number = (CatalogNumber)CollectionUtils.find(numbers,new CatalogNumberPredicate(catalogNumber));
            for(Section s : number.getSections()) {
                if( sectionNumber.equalsIgnoreCase(s.getNumber()) ) {
                    lSection = s;
                    s.setSubjectId(subjectId);
                    s.setCatalogNumber(catalogNumber);
                    break;
                }
            }
        }
        return lSection;
    }

    public List<CatalogNumber> getCatalogNumbers() {
        return catalogNumbers;
    }

    public void setCatalogNumbers(List<CatalogNumber> catalogNumbers) {
        this.catalogNumbers = catalogNumbers;
    }

    public SearchResult getSearchResults(Map<String, String[]> query) {
        SearchResult searchResult = (SearchResult)getApplicationContext().getBean("academicsSearchResult");

        if( null == getCatalogNumbers() || getCatalogNumbers().isEmpty() ) {
            initCatalogNumbers();
        }
        // Returning random results
        int i = (int)(Math.random() * getCatalogNumbers().size());

        CatalogNumber number = getCatalogNumbers().get(i);

        searchResult.setSections(number.getSections());

        if( (int)(Math.random() * 10) < 2 ) {
            ToolMessage message = new ToolMessage();
            message.setType(ToolMessageType.ALERT);
            message.setLabel("Too Many Results");
            message.setDescription("Your search returned too many results to display, please refine your search criteria.");
            searchResult.addMessage(message);
        }
        if( (int)(Math.random() * 10) < 2 ) {
            ToolMessage message = new ToolMessage();
            message.setType(ToolMessageType.ERROR);
            message.setLabel("Error");
            message.setDescription("An error occurred while performing your search.");
            searchResult.addMessage(message);
        }

        return searchResult;
    }

    public Map<String, List<CatalogNumber>> getDemoCache() {
        return demoCache;
    }

    public void setDemoCache(Map<String, List<CatalogNumber>> demoCache) {
        this.demoCache = demoCache;
    }
}
