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

package org.kuali.mobility.security.authz.util;

import org.kuali.mobility.security.authz.expression.AndExpression;
import org.kuali.mobility.security.authz.expression.Expression;
import org.kuali.mobility.security.authz.expression.GroupExpression;
import org.kuali.mobility.security.authz.expression.NotExpression;
import org.kuali.mobility.security.authz.expression.OrExpression;
import org.kuali.mobility.security.authz.expression.PersonAttributeExpression;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class XstreamUtility {

	private static XStream expressionXstream = populateExpressionXstream();

	public static Expression xmlToExpression(String xml) {
		return (Expression) expressionXstream.fromXML(xml);
	}

	public static String expressionToXml(Expression expression) {
		return expressionXstream.toXML(expression);
	}

	private static XStream populateExpressionXstream() {
		XStream xstream = new XStream();

		xstream.alias("or", OrExpression.class);
		xstream.alias("and", AndExpression.class);
		xstream.alias("not", NotExpression.class);
		xstream.alias("group", GroupExpression.class);
		xstream.alias("personAttribute", PersonAttributeExpression.class);

		xstream.addImplicitCollection(AndExpression.class, "children");
		xstream.addImplicitCollection(OrExpression.class, "children");
		xstream.addImplicitCollection(NotExpression.class, "children");

		xstream.registerConverter(new GroupConverter());
		xstream.registerConverter(new PersonAttributeConverter());

		return xstream;
	}

	private static class GroupConverter implements Converter {
		public boolean canConvert(Class clazz) {
			return clazz.equals(GroupExpression.class);
		}

		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
			GroupExpression groupExpression = (GroupExpression) value;
			writer.addAttribute("key", groupExpression.getKey());
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			return new GroupExpression(reader.getAttribute("key"));
		}
	}

	private static class PersonAttributeConverter implements Converter {
		public boolean canConvert(Class clazz) {
			return clazz.equals(PersonAttributeExpression.class);
		}

		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
			PersonAttributeExpression personAttributeExpression = (PersonAttributeExpression) value;
			writer.addAttribute("key", personAttributeExpression.getKey());
			writer.addAttribute("value", personAttributeExpression.getValue());
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			return new PersonAttributeExpression(reader.getAttribute("key"), reader.getAttribute("value"));
		}
	}

}
