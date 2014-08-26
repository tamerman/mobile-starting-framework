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

package org.kuali.mobility.shared.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class SharedControllerTest {

	private SharedController controller;

	@Before
	public void preTest() {
		setController(new SharedController());
	}

	@Test
	public void testQrcode() {
		String viewName = getController().qrcode(null, null);
		assertTrue("Qrcode not found.", "qrcode".equals(viewName));
	}

	@Test
	public void testRadio() {
		String viewName = getController().radio(null, null);
		assertTrue("Radio not found.", "radio".equals(viewName));
	}

	@Test
	public void testPlugins() {
		String viewName = getController().plugins(null, null);
		assertTrue("Plugins not found.", "plugins".equals(viewName));
	}

	public SharedController getController() {
		return controller;
	}

	public void setController(SharedController controller) {
		this.controller = controller;
	}
}
