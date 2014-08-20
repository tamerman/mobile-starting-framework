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

package org.kuali.mobility.l10n.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.l10n.entity.LocalisedString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A unit test for the localisation service
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/LocalisationSpringBeans.xml")
public class LocalisationServiceTest {

	private static final String EN = "en";
	private static final String AF = "af";
	private static final String MSG_AF = "Afrikaans is die beste";
	private static final String MSG_EN = "English is the best";
	private static final String MSG_CODE = "my.message";
	private static final String MSG2_AF = "Afrikaans is die pad on te stap";
	private static final String MSG2_EN = "English is the way to go";
	
	/**
	 * A reference to the <code>LocalisationService</code>.
	 */
	@Autowired
	@Qualifier(value="localisationService")
	private LocalisationService localisationService;
	
	/**
	 * Test that we can save a localised String
	 */
	@Test
	public void testPersist() {
		localisationService.saveLocalisedString(MSG_CODE, getDummyStrings());
	}
	
	/**
	 * Test that we can save a localised Strings and get back what we are expecting
	 */
	@Test
	public void testPersistAndRetrieve() {
		localisationService.saveLocalisedString(MSG_CODE, getDummyStrings());
		
		LocalisedString string = localisationService.getLocalisedString(MSG_CODE, EN);
		assertEquals(MSG_EN, string.getContent());
		
		string = localisationService.getLocalisedString(MSG_CODE, AF);
		assertEquals(MSG_AF, string.getContent());
	}
	
	/**
	 * Test that we can update a persisted localised string
	 */
	@Test
	public void testUpdateLocalisedString(){
		localisationService.saveLocalisedString(MSG_CODE, getDummyStrings());
		localisationService.saveLocalisedString(MSG_CODE, getDummyStrings2());
		
		LocalisedString string = localisationService.getLocalisedString(MSG_CODE, EN);
		assertEquals(MSG2_EN, string.getContent());
		
		string = localisationService.getLocalisedString(MSG_CODE, AF);
		assertEquals(MSG2_AF, string.getContent());
	}
	
	
	private Map<String, String> getDummyStrings(){
		Map<String, String> localisedStrings = new HashMap<String, String>();
		localisedStrings.put(EN, MSG_EN);
		localisedStrings.put(AF, MSG_AF);
		return localisedStrings;
	}
	
	private Map<String, String> getDummyStrings2(){
		Map<String, String> localisedStrings = new HashMap<String, String>();
		localisedStrings.put(EN, MSG2_EN);
		localisedStrings.put(AF, MSG2_AF);
		return localisedStrings;
	}

}
