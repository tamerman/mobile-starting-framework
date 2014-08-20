/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.library.entity;

import org.kuali.mobility.security.authz.expression.Expression;
import org.kuali.mobility.security.authz.expression.GroupExpression;

/**
 * A class defining permission for the Library Tool
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
public class LibraryPermissions {

	/**
	 * Group required to for admin features on the library tool
	 */
	public static final String ADMIN = "LIBRARY_ADMIN";
	
	/**
	 * Expression to check if the user has admin rights
	 */
	public static final Expression ADMIN_EXPRESSION = new GroupExpression(ADMIN);
	
	
}
