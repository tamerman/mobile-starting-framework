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

package org.kuali.mobility.shared.controllers;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.file.entity.File;
import org.kuali.mobility.file.service.FileService;
import org.mockito.Mock;
import org.springframework.mock.web.*;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class FileControllerTest {
	private static final Logger LOG = LoggerFactory.getLogger(FileControllerTest.class);

	private static final String CONTENT_TYPE = "text/plain";
	private static final String CONTENT_ENCODING = "UTF-8";

	private static final String INDEX = "files";
	private static final String FILE_NAME = "file.test.properties";
	private static final String FORM_FILE_NAME = "file";

	private static MockServletContext servletContext;
	private FileController controller;
	private Properties kmeProperties;

	@Mock
	private FileService fileService;

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setController(new FileController());
		getController().setFileService(getFileService());
	}

	@Test
	public void testInjectionSucceeded() {
		assertTrue("Failed to find the file service", getController().getFileService() == getFileService());
	}

	@Test
	public void testIndex() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		when(getFileService().findAllFiles()).thenReturn(new ArrayList<File>());

		String viewName;
		try {
			viewName = getController().index(request, uiModel);
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			viewName = null;
		}
		assertTrue("View not what was expected.", INDEX.equals(viewName));
	}

	@Test
	public void testRemoveFile() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		Long fileHash = Long.getLong("42");
		File file = new File();
		file.setId(fileHash);

		when(getFileService().findFileById(fileHash)).thenReturn(file);
		when(getFileService().removeFile(file)).thenReturn(true);

		String viewName;
		try {
			viewName = getController().removeFile(uiModel, request, fileHash);
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			viewName = null;
		}
		assertTrue("Failed to remove file.", INDEX.equals(viewName));
	}

	@Test
	public void testRemoveFileNullFile() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		Long fileHash = Long.getLong("42");

		when(getFileService().findFileById(fileHash)).thenReturn(null);

		String viewName;
		try {
			viewName = getController().removeFile(uiModel, request, fileHash);
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			viewName = null;
		}
		assertTrue("Failed to remove file.", INDEX.equals(viewName));
	}

	@Test
	public void testRemoveFileFailOnService() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		Model uiModel = new ExtendedModelMap();

		Long fileHash = Long.getLong("42");
		File file = new File();
		file.setId(fileHash);

		when(getFileService().findFileById(fileHash)).thenReturn(file);
		when(getFileService().removeFile(file)).thenReturn(false);

		String viewName;
		try {
			viewName = getController().removeFile(uiModel, request, fileHash);
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			viewName = null;
		}
		assertTrue("Failed to remove file.", INDEX.equals(viewName));
	}

	@Test
	public void testHandleFormUpload() {
		MockMultipartHttpServletRequest request = new MockMultipartHttpServletRequest();

		String viewName;
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);

			MockMultipartFile mockFile = new MockMultipartFile(FORM_FILE_NAME,FILE_NAME,CONTENT_TYPE,in);

			request.addFile(mockFile);

			File file = new File(mockFile);

			when(getFileService().saveFile(file)).thenReturn(Long.valueOf(42));

			viewName = getController().handleFormUpload(request);
		} catch(IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(),ioe);
			viewName = null;
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			viewName = null;
		}
		assertTrue("Failed to handle form upload.", viewName.contains(FILE_NAME));
	}

	@Test
	public void testGetFile() {
		String viewName;
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		MockHttpServletResponse response = new MockHttpServletResponse();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);

			MockMultipartFile mockFile = new MockMultipartFile(FORM_FILE_NAME,FILE_NAME,CONTENT_TYPE,in);

			File file = new File(mockFile);

			when(getFileService().findFileById(file.getId())).thenReturn(file);

			getController().getFile(file.getId(),request,response);

			assertTrue("Content type of response is not text/plain.",CONTENT_TYPE.equals(response.getContentType()));
			assertTrue("Response content length does not match file length.",file.getFileSize()==response.getContentLength());
			String responseText = IOUtils.toString(response.getContentAsByteArray(),CONTENT_ENCODING);

			String fileContent = IOUtils.toString(file.getBytes(),CONTENT_ENCODING);
			assertTrue("Response content does not match file content.",fileContent.equals(responseText));

		} catch(IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(),ioe);
			fail("Could not get file because of an IOException.");
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Could not get file because of an Exception.");
		}
	}

	@Test
	public void testGetFileThrowIOException() {
		MockHttpServletRequest request = new MockHttpServletRequest(servletContext);
		HttpServletResponse response = mock(HttpServletResponse.class);
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);

			MockMultipartFile mockFile = new MockMultipartFile(FORM_FILE_NAME,FILE_NAME,CONTENT_TYPE,in);

			File file = new File(mockFile);

			when(getFileService().findFileById(file.getId())).thenReturn(file);
			when(response.getOutputStream()).thenThrow(new IOException());

			getController().getFile(file.getId(),request,response);
		} catch(IOException ioe) {
			LOG.error(ioe.getLocalizedMessage(),ioe);
			fail("Could not get file because of an IOException.");
		} catch( Exception e ) {
			LOG.error(e.getLocalizedMessage(),e);
			fail("Could not get file because of an Exception.");
		}
	}

	public FileController getController() {
		return controller;
	}

	public void setController(FileController controller) {
		this.controller = controller;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public FileService getFileService() {
		return fileService;
	}

	public void setFileService(FileService fileService) {
		this.fileService = fileService;
	}
}
