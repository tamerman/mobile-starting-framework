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
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class TabBarItem extends SimpleTagSupport {

    private static final Logger LOG = LoggerFactory.getLogger( TabBarItem.class );

    private String url;
    private String label;
    private String selected;
    private String transition;

    @Override
    public void doTag() throws JspException {
        PageContext pageContext = (PageContext)getJspContext();
        JspWriter out = pageContext.getOut();
        StringBuilder builder = new StringBuilder();
        try {
        	out.println( "<li>" );
            if( null != getUrl() && !getUrl().trim().isEmpty() ) {
                builder.append( "<a href=\"" + getUrl().trim() + "\"" );
				builder.append( " data-transition=\"" );
                if( null != getTransition() 
                	&& !"".equalsIgnoreCase( getTransition() ) ) {
                	builder.append( getTransition() );
				} else {
                	builder.append( "none" );
                }
                builder.append( "\"" );
                if( null != getSelected() &&
                        ( "true".equalsIgnoreCase( getSelected() )
                        	|| "yes".equalsIgnoreCase( getSelected() ) ) ) {
                     builder.append( " class=\"ui-btn-active ui-state-persist\"" );
				}
				builder.append( ">" );
				out.println( builder.toString() );
            }
            if( null != getLabel() && !getLabel().trim().isEmpty() ) {
                out.println( getLabel().trim() );
            } else {
                getJspBody().invoke( out );
            }
            if( null != getUrl() && !getUrl().trim().isEmpty() ) {
                out.println( "</a>" );
            }
            out.println( "</li>" );
        } catch( Exception e ) {
            LOG.error( e.getLocalizedMessage(), e );
        }
    }


    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the selected
     */
    public String getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(String selected) {
        this.selected = selected;
    }

    public void setTransition(String transition) {
    	this.transition = transition;
    }
    
    public String getTransition() {
    	return transition;
    }

}
