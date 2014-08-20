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

import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.api.UserAttribute;

import java.util.List;

public class PersonAttributeExpression implements Expression {

	private static final long serialVersionUID = 4461424607920854636L;

	private String key;

	private String value;

	public PersonAttributeExpression(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public boolean evaluate(User user) {
		boolean success = false;
		List<UserAttribute> attributes = user.getAttribute(key);
		if (attributes != null) {
			for(UserAttribute attribute : attributes) {
				if (attribute.getAttributeValue() != null
					&& attribute.getAttributeValue().equalsIgnoreCase(value)) {
					success = true;
					break;
				}
			}
		}
		return success;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PersonAttributeExpression) {
			PersonAttributeExpression other = (PersonAttributeExpression)obj;
			return key.equals(other.key) && value.equals(other.value);
		}
		return false;
	}
	
}
