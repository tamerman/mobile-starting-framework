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

package org.kuali.mobility.icons.tags;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Tag that can be used to place a configured icon to html.
 * CSS still needs to be added for the icon.
 * <p/>
 * This tag is only useful if you are going to use responsive image sizes
 * by using css.
 * <p/>
 * If you need to use just one size of an icon (no responsive image densities)
 * you can just directly link to the image without using this tag.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public class WebIconTag extends SimpleTagSupport {

	/**
	 * Name of the icon to use
	 */
	private String icon;

	/**
	 * Theme to be applied to the icon (optional)
	 */
	private String theme;

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		JspWriter out = pageContext.getOut();

		// Write out a link to a transparent pixel for a placeholder
		out.write("<img src=\"" + request.getContextPath() + "/images/pixel.png\"");

		// Write the data-kme-icon attribute that will be used with css to select the icon
		out.write(" data-kme-icon=\"");
		out.write(icon);
		out.write("\"");

		// Write the data-kme-theme that will be used by css to use themed icon
		if (!StringUtils.isEmpty(theme)) {
			out.write(" data-kme-theme=\"");
			out.write(theme);
			out.write("\"");
		}

		out.write(" />");
	}

	/**
	 * Name of the icon to use.
	 *
	 * @param icon Name of the icon to use
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}

	/**
	 * Optional theme for the icon.
	 *
	 * @param theme Theme for the icon
	 */
	public void setTheme(String theme) {
		this.theme = theme;
	}

}
