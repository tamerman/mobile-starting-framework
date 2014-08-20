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

package org.kuali.mobility.writer.tags;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.icons.service.IconsService;
import org.kuali.mobility.shared.interceptors.NativeCookieInterceptor;
import org.kuali.mobility.writer.entity.Article;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;


/**
 * Tag to display an image for an article.
 *
 * This tag will create an img html element with a reference to the thumbnail of the image, and
 * optionally a link to the full size image.
 *
 * If the article has no image attached, the tag will use a themed icon for the tool (or a default
 * image if no themed image is found)
 *
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public class ArticleImageTag extends SimpleTagSupport {

	private static final long serialVersionUID = -3446876982138991278L;

	/** A reference to a logger */
	private static final Logger LOG = LoggerFactory.getLogger(ArticleImageTag.class);

    /** Name of the icon to use for writer articles */
    private static final String WRITER_ICON_NAME = "WriterArticle";

	/** URL prefix for writer */
	private static final String prefix = "/writer/";
	
	/** Id for the image */
	private String id;

	/**
	 * Optional style to apply to the image
	 */
	private String style;
	
	/**
	 * Article who's image should be displayed
	 */
	private Article article;
	
	/**
	 * Name of tool instance
	 */
	private String instance;
	
	/** Flag if the thumbnail should be wrapped with a link to the full image */
	private boolean wrapLink;
	
	/**
	 * Sets the id of the element that will be inserted.
	 * @param id id of the element that will be inserted.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the article who's image should be displayed
	 * @param article The article who's image should be displayed.
	 */
	public void setArticle(Article article){
		this.article = article;
	}
	
	/**
	 * Sets the flag if the image should be wrapped with a link to
	 * the full size image.
	 * @param wrapLink flag if the image should be wrapped with a link to
     * the full size image.
	 */
	public void setWrapLink(boolean wrapLink){
		this.wrapLink = wrapLink;
	}
	
	/**
	 * Sets the style to apply to the image
	 * @param style CSS style to apply.
	 */
	public void setStyle(String style){
		this.style = style;
	}
	

	public void doTag() { 
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		HttpServletRequest request =  (HttpServletRequest)pageContext.getRequest();
		String contextPath = request.getContextPath();
        ServletContext servletContext = pageContext.getServletContext();
        WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        IconsService iconService = (IconsService)ac.getBean("iconsService");

		String thumbNailPath;
		String fullPath = null;
		final String instanceURL = contextPath+prefix+instance;
		/*
		 * First check if the article exits, and it has image media.
		 * If it does not exist or has no image, we need to use
		 * the default image for that instance of the tool
		 */
		if (article == null || article.getImage() == null || article.getImage().getId() == 0){

            /*
             * Check if there is an icon themed for this instance of the writer tool
             */
            if (StringUtils.isEmpty(instance) && iconService.getIcon(WRITER_ICON_NAME, instance) != null) {
                thumbNailPath = contextPath+"/getIcon/" + WRITER_ICON_NAME + "-" + instance + "-80@2.png";
            }
            else{
                thumbNailPath = contextPath+"/getIcon/WriterArticle-80@2.png";
            }
		}
		else {
			thumbNailPath = instanceURL+"/media/"+article.getImage().getId()+"?thumb=1";
			fullPath = instanceURL + "/media/view/"+article.getImage().getId();
		}
		boolean addLink = (wrapLink && fullPath != null);
		boolean isNative = (Boolean)request.getSession(true).getAttribute(NativeCookieInterceptor.SESSION_NATIVE);
		try {
			// Wrap with link if required
			if(addLink && isNative){
				String serverPath = request.getScheme() + "://" + request.getServerName();
				if (request.getServerPort() != 80){
					serverPath += ":" + request.getServerPort();
				}
				serverPath += fullPath;
				writeChildBrowserJS(out,serverPath);
			}
			else if (addLink){
				out.print("<a href=\"");
				out.print(fullPath);
				out.print("\">");
			}
			
			// Put image container
			out.print("<img src=\"");
			out.print(thumbNailPath);
			out.print("\"");
			
			// Add image id if specified
			if (this.id != null){
				out.print(" id=\"");
				out.print(this.id);
				out.print("\"");
			}
			
			// Add style if specified
			if (this.style != null){
				out.print(" style=\"");
				out.print(this.style);
				out.print("\"");
			}
			
			out.print(" />");
			
			// Close wrapping link
			if(addLink){
				out.print("</a>");
			}
		}
		catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	/**
	 * Sets the name of the tool instance
	 * @param instance
	 */
	public void setInstance(String instance) {
		this.instance = instance;
	}

    /**
     * To view the full size image of the article we will use the child browser cordova plugin
     * if we are native. This is handy especially when busy editing an article where we can not
     * navigate to another page - the changed being edited will be lost.
     * @param out JSP Writer output
     * @param previewPath Path to the image preview
     * @throws IOException Thrown when the JspWriter throws an exception
     */
	private void writeChildBrowserJS(JspWriter out, String previewPath) throws IOException{
		out.println("<script>");
		out.println("$(document).ready(function(){");
		out.println("	$(\"#previewLink"+id+"\").click(function(){");
		out.println("		window.plugins.childBrowser.onLocationChange = function(loc){");
		out.println("			if (loc.indexOf(\"close\") > 0){");
		out.println("				window.plugins.childBrowser.close();");
		out.println("			}");
		out.println("		};");
		out.println("		window.plugins.childBrowser.showWebPage(\""+previewPath+"\",{showLocationBar: false });");
		out.println("	});");
		out.println("});");
		out.println("</script>");
		out.println("<a id=\"previewLink" +id +"\" href=\"#\" />");
	}
}
