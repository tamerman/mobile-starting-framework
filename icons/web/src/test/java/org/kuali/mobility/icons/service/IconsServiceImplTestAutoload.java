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

package org.kuali.mobility.icons.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.icons.entity.WebIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Unit test for the IconsService
 * <p/>
 * This unit test will test the autoload functionality of the IconService
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 3.0.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration("classpath:/IconsSpringBeans.xml")
public class IconsServiceImplTestAutoload {
	private static final Logger LOG = LoggerFactory.getLogger(IconsServiceImplTestAutoload.class);

	private static final String TEST1_NAME = "Tool1Book";
	private static final String TEST1_THEME = "campus1";
	private static final String TEST1_PATH = "/WEB-INF/svg/book-campus1.svg";
	private static final String TEST1_PATH_UPDATED = "/WEB-INF/svg/book-campus1_updated.svg";

	private static final String TEST2_NAME = "Tool2Trolley";
	private static final String TEST2_THEME = "campus1";
	private static final String TEST2_PATH = "/WEB-INF/svg/trolley-campus1.svg";


	@Autowired
	@Qualifier("iconsService")
	private IconsService iconService;


	/**
	 * Test that autoloading icons have the icons available from the service
	 */
	@Test
	@DirtiesContext
	public void testAutoLoadIcons() {
		WebIcon wi = iconService.getIcon(TEST1_NAME, TEST1_THEME);
		assertNotNull("Expected to find a webIcon but did not");
		assertEquals("Icon path is not what was expected", TEST1_PATH, wi.getPath());
	}

	/**
	 * Test that updating a auto imported icon works
	 */
	@Test
	@DirtiesContext
	public void testUpdateIconPath() {
		WebIcon wi = iconService.getIcon(TEST1_NAME, TEST1_THEME);

		wi.setPath(TEST1_PATH_UPDATED);
		iconService.saveWebIcon(wi);

		wi = iconService.getIcon(TEST1_NAME, TEST1_THEME);

		assertNotNull("Expected to find a webIcon but did not", wi);
		assertEquals("Icon path is not what was expected", TEST1_PATH_UPDATED, wi.getPath());
	}

	/**
	 * Test that getting an icon with an invalid theme return no icon
	 */
	@Test
	@DirtiesContext
	public void testGetNullInvalidThemeIcon() {
		WebIcon wi = iconService.getIcon(TEST1_NAME, "myCrazyThemeThatDoesntExist");
		assertNull("We did not expect to find an icon with an invalid theme", wi);
	}

	/**
	 * Test that trying to get an icon with the default theme - if there is no default theme -
	 * return null
	 */
	@Test
	@DirtiesContext
	public void testGetNullForNullDefaultThemeIcon() {
		WebIcon wi = iconService.getIcon(TEST1_NAME, null);
		assertNull("We did not expect to find an icon with an invalid theme", wi);
	}

	/**
	 * Test that icons from both test files are loaded
	 */
	@Test
	@DirtiesContext
	public void testFilesLoaded() {
		WebIcon wi = iconService.getIcon(TEST1_NAME, TEST1_THEME);
		assertNotNull("Expected to find a webIcon but did not", wi);

		wi = iconService.getIcon(TEST2_NAME, TEST2_THEME);
		assertNotNull("Expected to find a webIcon but did not", wi);
	}

}
