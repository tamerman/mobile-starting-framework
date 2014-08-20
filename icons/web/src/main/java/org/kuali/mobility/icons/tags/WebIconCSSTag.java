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

package org.kuali.mobility.icons.tags;

import org.apache.commons.lang.StringUtils;
import org.kuali.mobility.icons.entity.WebIcon;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


/**
 * A tag that will create the css to retrieve a configured icon in a specific size.
 * If you have a jsp that generates css, you can use this tag to create the css for an icon.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public class WebIconCSSTag extends SimpleTagSupport {

    /**
     * The WebIcon to create CSS for.
     */
	private WebIcon icon;
	
	
	/**
	 * Size attribute
	 */
	private int size;

    /**
     * The multiplier to use.
     * Used for icons that need higher pixel density.
     * Setting this value higher than 1 will add the multiplier.
     * Setting this value lower than 1 will revert it back to 1.
     *
     * Example:
     * If size is set to 52, and multiplier is set to 2, the css
     * will have a link to a "iconName-52@2.png" image.
     *
     * If the size is set to 52, and the multiplier is set
     * to 1 (or unset, or set to lower than 1), the css will
     * have a link to a "iconName-52.png" image.
     */
    private int multiplier;
	

	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		HttpServletRequest request =  (HttpServletRequest)pageContext.getRequest();
		
		// If there is theme info, add that
		if(!StringUtils.isEmpty(icon.getTheme())){
			out.write("img[data-kme-icon=" + icon.getName() + "][data-kme-theme=" + icon.getTheme() + "], ");
			out.write("[data-kme-theme=" + icon.getTheme() + "] img[data-kme-icon="+ icon.getName() + "]{ ");
		}
		else{
			out.write("img[data-kme-icon=" + icon.getName() + "]{");
		}
		out.write("background-image: url('" + getBackgroundImageURL(request.getContextPath())+"');");
		out.write("}");
	}

    /**
     * Create a URL that can be used to retrieve the icon
     * from the servlet.
     * @param context Content path
     * @return A fullpath URL that can be used to retrieve the icon
     */
	private String getBackgroundImageURL(String context){
		StringBuilder sb = new StringBuilder(255);

        /*
         * Start with the path to the servlet.
         * /mdot/getIcon
         */
		sb.append(context).append("/getIcon/");

        /*
         * Add the name of the icon.
         * /mdot/getIcon/myIcon-
         */
        sb.append(icon.getName()).append('-');

        /*
         * If the icon has a theme, add that too.
         * /mdot/getIcon/myIcon-myTheme-
         */
        if (!StringUtils.isEmpty(icon.getTheme())){
            sb.append(icon.getTheme()).append('-');
        }

        /*
         * Add the size
         * /mdot/getIcon/myIcon-myTheme-100
         */
        sb.append(this.size);

        /*
         * If the icon has a multiplier, we add that too
         * /mdot/getIcon/myIcon-myTheme-100@2
         */
        if (this.multiplier > 1){
            sb.append("@").append(this.multiplier);
        }

        /*
         * Add the extension, all icons are converted to png format
         * /mdot/getIcon/myIcon-myTheme-100@2.png
         */
        sb.append(".png");
		return sb.toString();
	}

    /**
     * The size that the icon should be (width and height)
     * @param size Size for the icon
     */
	public void setSize(int size) {
		this.size = size;
	}

    /**
     * Set the multiplier for the icon.
     * @param multiplier Multiplier for the icon.
     */
    public void setMultiplier(int multiplier){
        this.multiplier = multiplier;
    }


    /**
     * Set the icon that will be used by this tag
     * @param icon WebIcon that will be used to generate CSS of.
     */
	public void setIcon(WebIcon icon) {
		this.icon = icon;
	}
	
	
}
