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

package org.kuali.mobility.security.authz.expression;

import org.junit.Before;
import org.junit.Test;
import org.kuali.mobility.security.group.api.Group;
import org.kuali.mobility.security.group.entity.GroupImpl;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for the group expression
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class GroupExpressionTest {

	private static final String GROUP_ADMIN_NAME = "ADMIN";
	private Group GROUP_ADMIN;

	@Before
	public void setup() {
		GROUP_ADMIN = new GroupImpl();
		GROUP_ADMIN.setName(GROUP_ADMIN_NAME);
	}


	/**
	 * Tests that a user is in a group
	 */
	@Test
	public void testIsIngroup() {

		User user = new UserImpl();
		user.addGroup(GROUP_ADMIN);

		GroupExpression gp = new GroupExpression(GROUP_ADMIN_NAME);
		assertTrue("User is expected to be in group", gp.evaluate(user));
	}

	/**
	 * Test that a user is not in a group
	 */
	@Test
	public void testIsNotInGroup() {
		User user = new UserImpl();
		user.addGroup(GROUP_ADMIN);

		GroupExpression gp = new GroupExpression("RANDOM_GROUP");
		assertFalse("User is NOT expected to be in group", gp.evaluate(user));
	}
}
