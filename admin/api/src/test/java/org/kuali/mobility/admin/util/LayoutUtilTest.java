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

package org.kuali.mobility.admin.util;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.mobility.admin.entity.HomeScreen;

/**
 * Unit test to check the functions of the <coce>LayoutUtil</code>
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.3.0
 */
public class LayoutUtilTest {

	/**
	 * Check that all the predefined registered as valid layouts
	 * actually result in valids
	 */
	@Test
	public void testValidLayoutPredefined() {
		for (String layout : HomeScreen.LAYOUTS) {
			Assert.assertTrue("Layout " + layout + " is expected to be valid", LayoutUtil.isValidLayout(layout));
		}
	}

	/**
	 * A null layout should not be a valid layout
	 */
	@Test
	public void testNullInvalidLayoutPredefined() {
		Assert.assertFalse("null layout is not expected to be valid", LayoutUtil.isValidLayout(null));
	}

	/**
	 * If the user specifies a invalid layout while changing layout is not allowed,
	 * it should be changed to the default layout
	 */
	@Test
	public void testCorrectedLayout() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "false");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		String layout = "myFakeLayout";
		String newLayout = LayoutUtil.getValidLayout(layout, kmeProperties);

		Assert.assertEquals(HomeScreen.LAYOUT_LIST, newLayout);
	}

	/**
	 * If changing layout is not allowed, and the user specifies another valid
	 * layout, it should be changed to the default layout
	 */
	@Test
	public void testCorrectedLayout2() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "false");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		String newLayout = LayoutUtil.getValidLayout(HomeScreen.LAYOUT_TILES, kmeProperties);

		Assert.assertEquals(HomeScreen.LAYOUT_LIST, newLayout);
	}

	/**
	 * If the user specified an invalid layout while changing layout is allowed,
	 * it should be changed to the default layout
	 */
	@Test
	public void testCorrectedLayout3() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "true");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		String layout = "myFakeLayout";
		String newLayout = LayoutUtil.getValidLayout(layout, kmeProperties);

		Assert.assertEquals(HomeScreen.LAYOUT_LIST, newLayout);
	}

	/**
	 * An invalid layout should be indicated as invalid
	 */
	@Test
	public void testIsValidLayout() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "true");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		String layout = "myFakeLayout";
		Assert.assertFalse("Layout " + layout + " is not expected to be valid", LayoutUtil.isValidLayout(layout));
	}


	/**
	 * A invalid layout should not be seen as an allowed layout
	 */
	@Test
	public void testIsLayoutAllowed() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "true");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		String layout = "myFakeLayout";
		Assert.assertFalse("Layout " + layout + " is not expected to be valid", LayoutUtil.isLayoutAllowed(layout, kmeProperties));
	}

	/**
	 * A valid layout while changing layout is not allowed, should result in a layout not allowe
	 */
	@Test
	public void testIsLayoutAllowed2() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "false");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		Assert.assertFalse("Layout " + HomeScreen.LAYOUT_TILES + " is not expected to be valid", LayoutUtil.isLayoutAllowed(HomeScreen.LAYOUT_TILES, kmeProperties));
	}

	/**
	 * A invalid layout should not be seen as an allowed layout
	 */
	@Test
	public void testIsLayoutAllowed3() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "true");
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);

		Assert.assertTrue("Layout " + HomeScreen.LAYOUT_TILES + " is expected to be valid", LayoutUtil.isLayoutAllowed(HomeScreen.LAYOUT_TILES, kmeProperties));
	}

	/**
	 * Layout change is not allowed
	 */
	@Test
	public void testIsLayoutChangeAllowed() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "false");
		Assert.assertFalse(LayoutUtil.isLayoutChangeAllowed(kmeProperties));
	}

	/**
	 * Layout change is allowed
	 */
	@Test
	public void testIsLayoutChangeAllowed2() {
		Properties kmeProperties = new Properties();
		kmeProperties.put(LayoutUtil.PROP_LAYOUT_EDITABLE, "true");
		Assert.assertTrue(LayoutUtil.isLayoutChangeAllowed(kmeProperties));
	}
}
