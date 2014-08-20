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

package org.kuali.mobility.security.authz.expression;

import org.junit.Assert;
import org.junit.Test;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;

import static junit.framework.TestCase.assertFalse;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class PersonAttributeExpressionTest {

    @Test
    public void testNone() throws Exception {
        PersonAttributeExpression prsnAttExp = new PersonAttributeExpression("user.authenticated", "false");
        User user = new UserImpl();
        assertFalse(prsnAttExp.evaluate(user));
    }

    @Test
    public void testOne() throws Exception {
        PersonAttributeExpression prsnAttExp = new PersonAttributeExpression("user.authenticated", "false");
        User user = new UserImpl();
        assertFalse(prsnAttExp.evaluate(user));
        user.addAttribute("user.authenticated", "false");
        assertTrue(prsnAttExp.evaluate(user));
    }

    @Test
    public void testTwo() throws Exception {
        PersonAttributeExpression prsnAttExp = new PersonAttributeExpression("user.authenticated", "false");
        prsnAttExp = new PersonAttributeExpression("user.iu.ou", "BL");
        User user = new UserImpl();
        Assert.assertFalse(prsnAttExp.evaluate(user));

        user.addAttribute("user.authenticated", "false");
        Assert.assertFalse(prsnAttExp.evaluate(user));

        user.removeAttribute("user.authenticated");
        user.addAttribute("user.iu.ou", "BL");
        Assert.assertTrue(prsnAttExp.evaluate(user));

        user.addAttribute("user.authenticated", "false");
        assertTrue(prsnAttExp.evaluate(user));
    }
}
