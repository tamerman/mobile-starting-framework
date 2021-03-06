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

import org.kuali.mobility.security.user.api.User;

/**
 * An Expression that does AND logic on one or more expressions.
 */
public class AndExpression extends AbstractNonLeafExpression {

	private static final long serialVersionUID = -7257967361622679088L;

	/**
	 * Creates a new instance of a <code>AndExpression</code>
	 */
	public AndExpression() {
		super();
	}

	/**
	 * Evaluates the expression against the user
	 *
	 * @param user User to test the expression against.
	 * @return True if the expression is true
	 */
	public boolean evaluate(User user) {
		for (Expression child : getChildren()) {
			if (!child.evaluate(user)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AndExpression) {
			AndExpression other = (AndExpression) obj;
			return getChildren().equals(other.getChildren());
		}
		return false;
	}


}
