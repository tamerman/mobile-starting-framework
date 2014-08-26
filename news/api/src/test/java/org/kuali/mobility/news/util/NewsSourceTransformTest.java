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

package org.kuali.mobility.news.util;

import org.junit.Before;
import org.junit.Test;
import org.kuali.mobility.news.entity.NewsSourceImpl;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsSourceTransformTest {

	private NewsSourceTransform transform;

	@Before
	public void setUpTest() {
		setTransform(new NewsSourceTransform());
	}

	@Test
	public void testTransformWithBadObject() {
		NewsSourceImpl source = getTransform().transform(new String("bob"));
		assertTrue("Source was not null and should have been.", source == null);
	}

	@Test
	public void testTransform() {
		NewsSourceImpl source = new NewsSourceImpl();
		source.setId(new Long(42));
		source.setTitle("Test Title");
		source.setDescription("Test Description");
		source.setParentId(new Long(0));
		source.setAuthor("Mojo Jojo");

		NewsSourceImpl source2 = transform.transform(source);

		assertTrue("News Source is null and should not be.", source2 != null);
		assertTrue("News Source does not match input source.", source.getId() == source2.getId()
				&& source.getTitle() == source2.getTitle()
				&& source.getDescription() == source2.getDescription()
				&& source.getAuthor() == source2.getAuthor());
	}

	public NewsSourceTransform getTransform() {
		return transform;
	}

	public void setTransform(NewsSourceTransform transform) {
		this.transform = transform;
	}
}
