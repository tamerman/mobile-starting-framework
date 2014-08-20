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

package org.kuali.mobility.tags;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * The backing class for the ListItem JSP tag.  Renders an li appropriate for the mobile framework.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 *
 */
public class ListItemTag extends SimpleTagSupport {
    
    private static final Logger LOG = LoggerFactory.getLogger(ListItemTag.class);
    
    private String dataRole;
    private String dataTheme;
    private String dataIcon;
    private boolean hideDataIcon;
    private String cssClass;
    private String id;
    
    public void doTag() throws JspException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            out.println("<li " + (dataRole != null && !"".equals(dataRole.trim()) ? " data-role=\"" + dataRole.trim() + "\"" : "") + (hideDataIcon ? " data-icon=\"false\"" : (dataIcon != null && !"".equals(dataIcon.trim()) ? " data-icon=\"" + dataIcon.trim() + "\"" : "")) + (cssClass != null && !"".equals(cssClass.trim()) ? " class=\"" + cssClass.trim() + "\"" : "") + " data-theme=\"" + (dataTheme != null && !"".equals(dataTheme.trim()) ? dataTheme : "c") + "\"" + (id != null && !"".equals(id.trim()) ? " id=\"" + id + "\"" : "") + ">");
            getJspBody().invoke(out);          
            out.println("</li>");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
    
    public void setDataRole(String dataRole) {
        this.dataRole = dataRole;
    }
    
    public void setDataTheme(String dataTheme) {
        this.dataTheme = dataTheme;
    }
    
    public void setDataIcon(String dataIcon) {
        this.dataIcon = dataIcon;
    }
    
    public void setHideDataIcon(boolean hideDataIcon) {
        this.hideDataIcon = hideDataIcon;
    }
    
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
    
	public void setId(String id) {
		this.id = id;
	}
}
