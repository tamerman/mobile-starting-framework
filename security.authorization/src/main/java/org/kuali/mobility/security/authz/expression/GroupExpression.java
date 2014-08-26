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
 * An expression to check if a user is a member of a group.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class GroupExpression implements Expression {

	private static final long serialVersionUID = 8899577797440033748L;

	/**
	 * Key if the group to check
	 */
	private String key;

	/**
	 * Creates a new instance of a <code>GroupExpression</code>
	 *
	 * @param key
	 */
	public GroupExpression(String key) {
		this.key = key;
	}

	/**
	 * @param user User to test the expression against.
	 * @return
	 */
	public boolean evaluate(User user) {
		return (user != null && user.getGroups() != null && user.isMember(key.toUpperCase()));
	}

	/**
	 * Gets the key of this <code>GroupExpression</code>
	 *
	 * @return
	 */
	public String getKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroupExpression) {
			GroupExpression other = (GroupExpression) obj;
			return key.equals(other.key);
		}
		return false;
	}

}
