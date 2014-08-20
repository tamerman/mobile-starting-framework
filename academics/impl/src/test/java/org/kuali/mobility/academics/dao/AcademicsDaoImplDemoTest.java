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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.kuali.mobility.academics.entity.CatalogNumber;
import org.kuali.mobility.academics.entity.SearchResult;
import org.kuali.mobility.academics.entity.Term;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.shared.InitBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:TestSpringBeans.xml")
public class AcademicsDaoImplDemoTest {
    private static final Logger LOG = LoggerFactory.getLogger( AcademicsDaoImplDemoTest.class );

    @Resource(name="academicsDao")
    private AcademicsDao dao;
    @Resource(name="academicsInitBean")
    private InitBean academicsInitBean;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        getAcademicsInitBean().loadData();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetTerms() {
        List<Term> terms = getDao().getTerms();
        assertFalse("Terms is null and should not be.",terms==null);
        assertFalse("Terms is empty and should not be.",terms.isEmpty());
        assertTrue("Terms has more than one item and should not.", terms.size() == 1);
    }

    @Test
    public void testGetCatalogNumbers() {
        String termId = "1970";
        String subjectId = "DHYGRACK";

        Map<String,String> query = new HashMap<String,String>();
        query.put(AcademicsConstants.TERM_ID,termId);
        query.put(AcademicsConstants.SUBJECT_ID,subjectId);

        List<CatalogNumber> numbers = getDao().getCatalogNumbers(query);
        assertFalse("Catalog Numbers is null and shouldn't be.",numbers==null);
        assertFalse("Catalog Numbers is empty and shouldn't be.",numbers.isEmpty());
    }

    @Test
    public void testGetSearchResults() {
        Map<String,String[]> query = new HashMap<String,String[]>();

        SearchResult searchResult = getDao().getSearchResults(query);
        assertFalse("SearchResult is null and can never be.",searchResult==null);

    }

    public AcademicsDao getDao() {
        return dao;
    }

    public void setDao(AcademicsDao dao) {
        this.dao = dao;
    }

    public InitBean getAcademicsInitBean() {
        return academicsInitBean;
    }

    public void setAcademicsInitBean(InitBean academicsInitBean) {
        this.academicsInitBean = academicsInitBean;
    }
}
