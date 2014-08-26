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

package org.kuali.mobility.writer.entity;

import org.kuali.mobility.security.authz.expression.Expression;
import org.kuali.mobility.security.authz.expression.GroupExpression;
import org.kuali.mobility.security.authz.expression.OrExpression;

/**
 * A class defining permission for the Library Tool
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
public class WriterPermissions {


	public static final String SPAMMER_PREFIX = "WRITER_SPAMMER_";

	public static final String JOURNALIST_PREFIX = "WRITER_JOURNALIST_";

	public static final String EDITOR_PREFIX = "WRITER_EDITOR_";

	public static final String ADMIN_PREFIX = "WRITER_ADMIN_";


	/**
	 * Builds and returns an expression that checks if the user is a spammer of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getSpammerExpression(String instance) {
		return new GroupExpression(SPAMMER_PREFIX + instance.toUpperCase());
	}

	/**
	 * Builds and returns an expression that checks if the user is a journalist of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getJournalistExpression(String instance) {
		return new GroupExpression(JOURNALIST_PREFIX + instance.toUpperCase());
	}

	/**
	 * Builds and returns an expression that checks if the user is an editor of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getEditorExpression(String instance) {
		return new GroupExpression(EDITOR_PREFIX + instance.toUpperCase());
	}

	/**
	 * Builds and returns an expression that checks if the user is an admin of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getAdminExpression(String instance) {
		return new GroupExpression(ADMIN_PREFIX + instance.toUpperCase());
	}


	/**
	 * Builds and returns an expression that checks if the user is a journalist or an editor of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getJournalistOrEditorExpression(String instance) {
		return new OrExpression().addChild(getJournalistExpression(instance)).addChild(getEditorExpression(instance));
	}

	/**
	 * Builds and returns an expression that checks if the user is an editor or an admin of a specific writer instance
	 *
	 * @param instance Instance to check against.
	 * @return The expression that can be used to check the permission.
	 */
	public static Expression getEditorOrAdminExpression(String instance) {
		return new OrExpression().addChild(getEditorExpression(instance)).addChild(getAdminExpression(instance));
	}


}
