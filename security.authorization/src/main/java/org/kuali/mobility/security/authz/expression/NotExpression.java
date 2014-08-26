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

import java.util.ArrayList;
import java.util.List;

public class NotExpression implements NonLeafExpression {

	private static final long serialVersionUID = -777343514543759642L;

	private List<Expression> children;

	public NotExpression() {
		children = new ArrayList<Expression>();
	}

	public boolean evaluate(User user) {
		for (Expression child : children) {
			if (child.evaluate(user)) {
				return false;
			}
		}
		return true;
	}

	public NonLeafExpression addChild(Expression expression) {
		children.add(expression);
		return this;
	}

	private Object readResolve() {
		if (children == null) {
			children = new ArrayList<Expression>();
		}
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotExpression) {
			NotExpression other = (NotExpression) obj;
			return children.equals(other.children);
		}
		return false;
	}

	@Override
	public List<Expression> getChildren() {
		return children;
	}

}
