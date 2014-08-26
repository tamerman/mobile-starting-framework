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
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PopupTag extends SimpleTagSupport {
	private static final Logger LOG = LoggerFactory.getLogger(PopupTag.class);
	private String dataTheme;
	private String dismissible;
	private String id;
	private String onClick;
	private String style;
	private String closeButton;
	private String closeDataTheme;

	public void doTag() throws JspException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		try {
			String output = "<div " +
					"data-role=\"popup\" " +
					"class=\"ui-content\" " +
					"style=\"" + (style != null && !"".equals(style.trim()) ? style : "") + "\" " +
					"id=\"" + (id != null && !"".equals(id.trim()) ? id : "defaultPopupID") + "\" " +
					"data-theme=\"" + (dataTheme != null && !"".equals(dataTheme.trim()) ? dataTheme : "d") + "\" " +
					"data-dismissible=\"" + (dismissible != null && !"".equals(dismissible.trim()) ? dismissible : "true") + "\">";

			if (closeButton != null && !"".equals(closeButton.trim())) {
				if ("right".equals(closeButton.trim())) {
					output += "<a href=\"#\" data-rel=\"back\" data-role=\"button\" data-theme=\"" + (closeDataTheme != null && !"".equals(closeDataTheme.trim()) ? closeDataTheme : "d") + "\" data-icon=\"delete\" data-iconpos=\"notext\" class=\"ui-btn-right\" onClick=\"" + (onClick != null && !"".equals(onClick.trim()) ? onClick : "") + "\">Close</a>";
				} else if ("left".equals(closeButton.trim())) {
					output += "<a href=\"#\" data-rel=\"back\" data-role=\"button\" data-theme=\"" + (closeDataTheme != null && !"".equals(closeDataTheme.trim()) ? closeDataTheme : "d") + "\" data-icon=\"delete\" data-iconpos=\"notext\" class=\"ui-btn-left\" onClick=\"" + (onClick != null && !"".equals(onClick.trim()) ? onClick : "") + "\">Close</a>";
				}
			}

			LOG.info(dataTheme);
			LOG.info(dismissible);
			LOG.info(id);

			out.println(output);
			JspFragment body = getJspBody();
			if (body != null) {
				body.invoke(out);
			}
			out.println("</div>");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}

	public String getDataTheme() {
		return dataTheme;
	}

	public void setDataTheme(String dataTheme) {
		this.dataTheme = dataTheme;
	}

	public String getDismissible() {
		return dismissible;
	}

	public void setDismissible(String dismissible) {
		this.dismissible = dismissible;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getCloseButton() {
		return closeButton;
	}

	public void setCloseButton(String closeButton) {
		this.closeButton = closeButton;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCloseDataTheme() {
		return closeDataTheme;
	}

	public void setCloseDataTheme(String closeDataTheme) {
		this.closeDataTheme = closeDataTheme;
	}

}
