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

import org.junit.Test;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NotExpressionTest {

	@Test
	public void testNone() throws Exception {
		NotExpression expression = new NotExpression();
		User user = new UserImpl();

		assertTrue(expression.evaluate(user));
	}

	@Test
	public void testOne() throws Exception {
		NotExpression expression = new NotExpression();
		PersonAttributeExpression prsnAttExp = new PersonAttributeExpression("user.authenticated", "false");

		expression.addChild(prsnAttExp);

		User user = new UserImpl();

		assertTrue(expression.evaluate(user));

		user.addAttribute("user.authenticated", "false");
		assertFalse(expression.evaluate(user));
	}

	@Test
	public void testTwo() throws Exception {
		NotExpression expression = new NotExpression();
		PersonAttributeExpression prsnAttExp = new PersonAttributeExpression("user.authenticated", "false");
		expression.addChild(prsnAttExp);

		prsnAttExp = new PersonAttributeExpression("user.iu.ou", "BL");
		expression.addChild(prsnAttExp);

		User user = new UserImpl();

		assertTrue(expression.evaluate(user));

		user.addAttribute("user.authenticated", "false");
		assertFalse(expression.evaluate(user));

		user.removeAttribute("user.authenticated");
		user.addAttribute("user.iu.ou", "BL");
		assertFalse(expression.evaluate(user));

		user.addAttribute("user.authenticated", "false");
		assertFalse(expression.evaluate(user));
	}

}
