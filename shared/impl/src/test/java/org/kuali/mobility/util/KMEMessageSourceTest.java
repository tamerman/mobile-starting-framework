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

package org.kuali.mobility.util;

import static org.junit.Assert.*;

import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.jsp.PageContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.l10n.service.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockPageContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.tags.MessageTag;

/**
 * Unit test for the KME Message Source
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/LocalisationSpringBeans.xml")
public class KMEMessageSourceTest {

	private static final Locale LOCALE_EN = new Locale("en");
	private static final Locale LOCALE_AF = new Locale("af");

	private static final String AF = "af";
	private static final String EN = "en";

	private static final String HOME_CODE = "test.home";
	private static final String HOME_EN = "Home";
	private static final String HOME2_EN = "Home Page";
	private static final String HOME2_AF = "Tuis Bladsy";

	/**
	 * A reference to the <code>MessageSource</code>.
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	/**
	 * A reference to the <code>LocalisationService</code>.
	 */
	@Autowired
	@Qualifier("localisationService")
	private LocalisationService localisationService;

	/**
	 * Check that we can atleast get a message from a file with the message source
	 */
	@Test
	public void testGetMessage() {
		String message = messageSource.getMessage(HOME_CODE, null, LOCALE_EN);

		// Check that we got atleast something
		assertNotNull(message);

		// Check that it is what we expect
		assertEquals(HOME_EN, message);
	}

	/**
	 * Test that when you change strings to the database, the message source]
	 * should reflect this change
	 */
	@Test
	@DirtiesContext
	public void testUpdateMessageToDB() {
		// Replace home string with new values
		localisationService.saveLocalisedString(HOME_CODE, getNewHomeStrings());

		String message;
		// Check that English is what we expect
		message = messageSource.getMessage(HOME_CODE, null, LOCALE_EN);
		assertEquals(HOME2_EN, message);

		// Check that Afrikaans is what we expect
		message = messageSource.getMessage(HOME_CODE, null, LOCALE_AF);
		assertEquals(HOME2_AF, message);
	}

	/**
	 * Create a map that can be used to replace the home string with new values
	 *
	 * @return
	 */
	private Map<String, String> getNewHomeStrings() {
		Map<String, String> localisedStrings = new HashMap<String, String>();
		localisedStrings.put(EN, HOME2_EN);
		localisedStrings.put(AF, HOME2_AF);
		return localisedStrings;
	}


	public void test() {
		CharArrayWriter caw = new CharArrayWriter();
		MockPageContext mpc = new MockPageContext();
		MessageTag messageTag = new MessageTag();
		messageTag.setPageContext(mpc);
		messageTag.setCode(HOME_CODE);

	}

}
