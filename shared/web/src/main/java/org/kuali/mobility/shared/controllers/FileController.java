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


package org.kuali.mobility.shared.controllers;

import org.kuali.mobility.file.entity.File;
import org.kuali.mobility.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

	private static Logger LOG = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {

		File file = getFileService().findFileById(id);
		LOG.info("--- Retrieve File ---");
		LOG.info(file.toString());

		response.setContentType(file.getContentType());
		response.setContentLength(file.getBytes().length);
		try {
			OutputStream out = response.getOutputStream();
			out.write(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(HttpServletRequest request, Model uiModel) {
		List<File> files = getFileService().findAllFiles();
		uiModel.addAttribute("files", files);
		uiModel.addAttribute("fileCount", files.size());

		return "files";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/remove/{fileHash}", method = RequestMethod.GET)
	public String removeFile(Model uiModel, HttpServletRequest request, @PathVariable("fileHash") Long fileHash) {
		File fileToDelete = getFileService().findFileById(fileHash);
		if (fileToDelete != null) {
			LOG.info("Will delete file with Id: " + fileToDelete.getId());
			if (getFileService().removeFile(fileToDelete)) {
				LOG.info("Did delete file.");
			}
		}

		return "files";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public String handleFormUpload(MultipartHttpServletRequest request) {

		MultipartFile mfile = request.getFile("file");
		// This constructor populates the fields in the File object.
		File file = new File(mfile);
		file.setPostedTimestamp(new Timestamp(System.currentTimeMillis()));
		Long fileId = getFileService().saveFile(file);

		LOG.info("--- Saving File ---");
		LOG.info(file.toString());

		return "{\"name\":\"" + file.getFileName() + "\",\"fileid\":\"" + file.getId() + "\",\"size\":\"" + file.getBytes().length + "\"}";
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
