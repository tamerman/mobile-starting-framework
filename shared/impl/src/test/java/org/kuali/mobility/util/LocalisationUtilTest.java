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

package org.kuali.mobility.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A unit test for the Localisation Utility
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/LocalisationSpringBeans.xml")
public class LocalisationUtilTest {
	
	private static final String TITLE_EN = "The very best of titles";
	private static final String TITLE_AF = "Die heel beste titel";
	private static final String TITLE_FIELD = "title";
	private static final String TITLE_LOC_FIELD = "mytool.home.title";
	
	/**
	 * A reference to the <code>LocalisationUtil</code>.
	 */
	@Autowired
	@Qualifier("localisationUtil")
	private LocalisationUtil localisationUtil;
	
	/**
	 * Test that the localised property code is what we expect to have returned
	 */
	@Test
	public void testGetLocalisedCode(){
		HttpServletRequest request = createFormSubmit();
		String code = localisationUtil.getLocalisedStringCode(TITLE_FIELD, request);
		assertEquals(TITLE_LOC_FIELD, code);
	}
	
	/**
	 * Test that we get the Strings per language that we expected
	 */
	@Test
	public void testGetLanguages(){
		HttpServletRequest request = createFormSubmit();
		Map<String, String> strings = localisationUtil.getLocalisedString(TITLE_FIELD, request);
		
		assertEquals(strings.get("en"), TITLE_EN);
		assertEquals(strings.get("af"), TITLE_AF);
	}
	
	/**
	 * Test that the utility only uses the languages that we are
	 * supporting
	 */
	public void testSupportedLanguages(){
		HttpServletRequest request = createFormSubmit();
		Map<String, String> strings = localisationUtil.getLocalisedString(TITLE_FIELD, request);
		for(String language : strings.keySet()){
			assertTrue("We do not expect to support language : " + language, localisationUtil.isSupportedLanguage(language));
		}
	}
	
	/**
	 * Creates a dummy form submit request.
	 * @return
	 */
	private HttpServletRequest createFormSubmit(){
		MockHttpServletRequest r = new MockHttpServletRequest();
		r.setParameter(TITLE_FIELD + ".code", TITLE_LOC_FIELD);
		r.setParameter(TITLE_FIELD + ".en", TITLE_EN);
		r.setParameter(TITLE_FIELD + ".af", TITLE_AF);
		r.setParameter(TITLE_FIELD + ".ch", "I dont know chinese");
		
		return r;
	}
}
