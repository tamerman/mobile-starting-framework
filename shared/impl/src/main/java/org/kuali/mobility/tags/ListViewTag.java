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
 * The backing class for the ListView JSP tag.  Renders a ul to contain a list of ListItem tags.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class ListViewTag extends SimpleTagSupport {

	private static final Logger LOG = LoggerFactory.getLogger(ListViewTag.class);

	private String id;
	private boolean filter;
	private String dataTheme;
	private String dataDividerTheme;
	private boolean dataInset;
	private String cssClass;

	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			out.println("<ul data-role=\"listview\"" + (id != null && !"".equals(id.trim()) ? " id=\"" + id.trim() + "\"" : "") + (cssClass != null && !"".equals(cssClass.trim()) ? " class=\"" + cssClass.trim() + "\"" : "") + (dataTheme != null && !"".equals(dataTheme.trim()) ? " data-theme=\"" + dataTheme.trim() + "\"" : "") + " data-inset=\"" + (dataInset ? "true" : "false") + "\" data-filter=\"" + (filter ? "true" : "false") + "\"" + (dataDividerTheme != null && !"".equals(dataDividerTheme.trim()) ? " data-dividertheme=\"" + dataDividerTheme + "\"" : "") + ">");
			getJspBody().invoke(out);
			out.println("</ul>");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}


	public void setId(String id) {
		this.id = id;
	}

	public void setDataTheme(String dataTheme) {
		this.dataTheme = dataTheme;
	}

	public void setDataDividerTheme(String dataDividerTheme) {
		this.dataDividerTheme = dataDividerTheme;
	}

	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	public void setDataInset(boolean dataInset) {
		this.dataInset = dataInset;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
}	
