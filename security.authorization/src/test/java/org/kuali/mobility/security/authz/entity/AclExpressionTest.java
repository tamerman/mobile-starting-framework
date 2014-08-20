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

package org.kuali.mobility.security.authz.entity;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class AclExpressionTest {

        @Test
        public void copyWithIds() {
            AclExpression expression = new AclExpression();
            boolean includeIds = true;
            expression.setId(10L);
            expression.setHomeScreenId(15L);
            expression.setToolId(20L);
            expression.setPermissionMode("");
            expression.setPermissionType("");
            expression.setExpression("");
            expression.setVersionNumber(25L);
            AclExpression expression1 = expression.copy(includeIds);
            assertTrue(expression1.getId() != null);
        }

        @Test
        public void copyWithoutIds() {
            AclExpression expression = new AclExpression();
            boolean includeIds = true;
            expression.setHomeScreenId(15L);
            expression.setToolId(20L);
            expression.setPermissionMode("");
            expression.setPermissionType("");
            expression.setExpression("");
            expression.setVersionNumber(25L);
            AclExpression expression1 = expression.copy(includeIds);
            assertTrue(expression1.getId() == null);
        }
}
