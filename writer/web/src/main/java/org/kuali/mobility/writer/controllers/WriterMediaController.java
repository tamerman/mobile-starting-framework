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

package org.kuali.mobility.writer.controllers;

import org.apache.commons.io.IOUtils;
import org.kuali.mobility.writer.entity.Media;
import org.kuali.mobility.writer.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Controller for Writer media
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@Controller 
@RequestMapping("/writer/{instance}/media")
public class WriterMediaController {

	/**
	 * Reference to the WriterService
	 */
	@Autowired
    @Qualifier("writerService")
	private WriterService service;
	

	@RequestMapping(value = "/{mediaId}", method = RequestMethod.GET)
	public void getMedia(
			@PathVariable int mediaId,
			@RequestParam(value="thumb", required=false, defaultValue="false") boolean thumb,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		long dateChanged = request.getDateHeader("If-Modified-Since") / 1000;
		
		File mediaFile = service.getMediaFile(mediaId, thumb);
		long mediaChanged = mediaFile.lastModified() / 1000;
		if (dateChanged == mediaChanged){
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		Media media = service.getMedia(mediaId);
		response.setContentType(media.getMimeType());
		response.setDateHeader("Last-Modified", mediaFile.lastModified());
		
		int bytesWritten = IOUtils.copy(new FileInputStream(mediaFile), response.getOutputStream());
		response.setContentLength(bytesWritten);
		
	}

	/**
	 * Controller to view an article's image
	 */
	@RequestMapping(value = "/view/{mediaId}", method = RequestMethod.GET)
	public String viewImage(
			@PathVariable(value="mediaId") long mediaId,
			@PathVariable("instance") String instance,
			Model uiModel) {
		
		// add to uiModel
		uiModel.addAttribute("mediaId", mediaId);
		uiModel.addAttribute("toolInstance", instance);
		return "writer/viewImage";
	}
	
}
