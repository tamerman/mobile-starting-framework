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

package org.kuali.mobility.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * The backing class for the LabeledRow JSP tag.  Renders a row of content with a styleable label.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class LabeledRowTag extends SimpleTagSupport {

	private static final Logger LOG = LoggerFactory.getLogger(LabeledRowTag.class);

	private String fieldLabel;
	private String fieldValue;

	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			if (fieldLabel != null && !fieldLabel.trim().equals("")) {
				out.println("<div class=\"labeledRow labeledRowLabel\" style=\"width:30%; text-align:right; float:left; color:#900;\">" + fieldLabel + "</div> ");
				out.println("<div class=\"labeledRow labeledRowData\" style=\"width:60%; padding-left:35%\">" + (fieldValue == null || fieldValue.trim().equals("") ? "&nbsp;" : fieldValue));
				if (getJspBody() != null) {
					getJspBody().invoke(out);
				}
				out.println("</div>");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}


}	
