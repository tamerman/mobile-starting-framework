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

package org.kuali.mobility.icons.controllers;

import org.apache.commons.io.IOUtils;
import org.kuali.mobility.icons.entity.WebIcon;
import org.kuali.mobility.icons.service.IconsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Controller for Icons
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@Controller
public class WebIconsController {

	private static final Logger LOG = LoggerFactory.getLogger(WebIconsController.class);

	/**
	 * A reference to the icon service
	 */
	@Autowired
	@Qualifier("iconsService")
	private IconsService iconService;

	// TODO this is only appropriate for home screen
	@RequestMapping("css/icons.css")
	public String getIconsCss(Model uiModel) {
		List<WebIcon> icons = iconService.getIcons();

		uiModel.addAttribute("icons", icons);
		return "icons/icons";
	}

	/**
	 * Controller for a test page that displays all the icons
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping("iconTest")
	public String iconTest(Model uiModel) {
		List<WebIcon> icons = iconService.getIcons();

		uiModel.addAttribute("icons", icons);
		return "icons/iconTest";
	}

	// getIcon/news-114

	/**
	 * Controller to get an icon for a specific size.
	 * Example URL: getIcon/news-114
	 * The above will get the news icon, in 144x144 pixels.
	 *
	 * @param iconName Name of the icon to get.
	 * @param size     Size to get the icon in
	 * @param request  The HttpServletRequest being handled
	 * @param response The HttpServletResponse that will reply.
	 * @throws IOException Thrown when there is an IOException while converting the icon
	 */
	@RequestMapping("getIcon/{iconName:[a-zA-Z]+}-{size:\\d+}")
	public void getImageSize(
			@PathVariable("iconName") String iconName,
			@PathVariable("size") int size,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		writeIconToHttpResponse(iconName, null, size, request, response);
	}

	/**
	 * Controller to get an icon for a specific size, as well as for a specific theme.
	 * Example URL: getIcon/news-themeB-114
	 * The above will get the news icon, in 144x144 pixels, for the themeB theme.
	 *
	 * @param iconName Name of the icon to get.
	 * @param theme    The theme to get the icon in
	 * @param size     Size to get the icon in
	 * @param request  The HttpServletRequest being handled
	 * @param response The HttpServletResponse that will reply.
	 * @throws IOException Thrown when there is an IOException while converting the icon
	 */
	@RequestMapping("getIcon/{iconName:[a-zA-Z]+}-{theme:[a-zA-Z]+}-{size:\\d+}")
	public void getImageThemeSize(
			@PathVariable("iconName") String iconName,
			@PathVariable("theme") String theme,
			@PathVariable("size") int size,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		writeIconToHttpResponse(iconName, theme, size, request, response);
	}

	/**
	 * Controller to get an icon for a specific size while applying a multiplier.
	 * Example URL: getIcon/news-114@2
	 * The above will get the news icon, in 288x288 pixels (a 2 times multiplier is added to the 144 size).
	 *
	 * @param iconName   Name of the icon to get.
	 * @param size       Size to get the icon in
	 * @param multiplier The multiplier to apply to the icon.
	 * @param request    The HttpServletRequest being handled
	 * @param response   The HttpServletResponse that will reply.
	 * @throws IOException Thrown when there is an IOException while converting the icon
	 */
	@RequestMapping("getIcon/{iconName:[a-zA-Z]+}-{size:\\d+}@{multiplier:\\d+}")
	public void getIconWithMultiplier(
			@PathVariable("iconName") String iconName,
			@PathVariable("size") int size,
			@PathVariable("multiplier") int multiplier,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		writeIconToHttpResponse(iconName, null, (size * multiplier), request, response);
	}

	/**
	 * Controller to get an icon for a specific size (applying a multiplier) and a specified theme.
	 * Example URL: getIcon/news-themeB-114@2
	 * The above will get the news icon in themeB theme, with a size of
	 * 288x288 pixels (a 2 times multiplier is added to the 144 size).
	 *
	 * @param iconName   Name of the icon to get.
	 * @param theme      The theme to get the icon in
	 * @param size       Size to get the icon in
	 * @param multiplier The multiplier to apply to the icon.
	 * @param request    The HttpServletRequest being handled
	 * @param response   The HttpServletResponse that will reply.
	 * @throws IOException Thrown when there is an IOException while converting the icon
	 */
	@RequestMapping("getIcon/{iconName:[a-zA-Z]+}-{theme:[a-zA-Z]+}-{size:\\d+}@{multiplier:\\d+}")
	public void getImageThemeWithMultiplier(
			@PathVariable("iconName") String iconName,
			@PathVariable("theme") String theme,
			@PathVariable("size") int size,
			@PathVariable("multiplier") int multiplier,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		writeIconToHttpResponse(iconName, theme, (size * multiplier), request, response);
	}

	/**
	 * Get the icon matching all the criteria of the request and write it the the HttpServletResponse.
	 *
	 * @param iconName Name of the icon to get.
	 * @param theme    The theme to get the icon in.
	 * @param size     Size to get the icon in.
	 * @param request  The HttpServletRequest being handled.
	 * @param response The HttpServletResponse that will reply.
	 * @throws IOException Thrown if there is an exception creating the icon or writing it to the response.
	 */
	private void writeIconToHttpResponse(String iconName, String theme, int size, HttpServletRequest request, HttpServletResponse response) throws IOException {
		long dateChanged = request.getDateHeader("If-Modified-Since") / 1000;
		File imageFile = iconService.getImageFile(iconName, theme, size);

		long mediaChanged = imageFile.lastModified() / 1000;
		if (dateChanged == mediaChanged) {
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		response.setContentType("image/png");
		InputStream imageInput = new FileInputStream(imageFile);
		response.setDateHeader("Last-Modified", imageFile.lastModified());

		int bytesWritten = IOUtils.copy(imageInput, response.getOutputStream());
		response.setContentLength(bytesWritten);
		response.flushBuffer();
	}


}
