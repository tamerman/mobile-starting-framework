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

package org.kuali.mobility.file.service;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.file.dao.FileDaoImpl;
import org.kuali.mobility.file.entity.File;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.database.annotations.Transactional;
import org.unitils.database.util.TransactionMode;
import org.unitils.orm.jpa.JpaUnitils;
import org.unitils.orm.jpa.annotation.JpaEntityManagerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(UnitilsJUnit4TestClassRunner.class)
@JpaEntityManagerFactory(persistenceUnit="mdot")
public class FileServiceImplTest {
	private static final Logger LOG = LoggerFactory.getLogger(FileServiceImplTest.class);
	private static final String FILE_NAME = "file.test.properties";
	private static final String CONTENT_TYPE = "text/plain";

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	private FileDaoImpl dao;
	private FileServiceImpl service;

	@BeforeClass
	public static void setUpClass() throws Exception {
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
	}

	@Before
	public void preTest() {
		setService(new FileServiceImpl());
		setDao(new FileDaoImpl());
		getService().setFileDao(getDao());
		JpaUnitils.injectEntityManagerInto(getDao());
	}

	@Test
	@Transactional(TransactionMode.ROLLBACK)
	public void testFileService() {
		File file = new File();
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE_NAME);
			byte[] inputFile = IOUtils.toByteArray(in);
			file.setBytes(inputFile);
			file.setFileSize(inputFile.length);
		} catch( IOException ioe ) {
			LOG.error( ioe.getLocalizedMessage(), ioe );
		}
		file.setFileName(FILE_NAME);
		file.setContentType(CONTENT_TYPE);
		file.setPostedTimestamp(new Timestamp( Calendar.getInstance().getTimeInMillis() ) );

		assertTrue("File has an ID and should not have.",file.getId()==null);

		Long fileId = getService().saveFile(file);

		LOG.debug("New file id is: "+fileId);

		assertTrue("Could not save file.", fileId != null && fileId.intValue() > 0);

		File lookupFile = getService().findFileById(fileId);

		assertTrue("Failed to find file for ID " + fileId, lookupFile != null);

		List<File> listOfFiles = getService().findFilesByName(FILE_NAME);

		assertTrue("Failed to find files for name "+FILE_NAME, listOfFiles != null && listOfFiles.size() == 1 );

		List<File> allFiles = getService().findAllFiles();

		assertTrue("Failed to find all files.", allFiles != null && allFiles.size() == 1 );

		File fileToRemove = allFiles.get(0);
		boolean didRemove = getService().removeFile(fileToRemove);

		assertTrue("Failed to remove file ID "+fileToRemove.getId(),didRemove);

		allFiles = getService().findAllFiles();

		assertTrue("Found files and should not have.", allFiles == null || allFiles.size() == 0 );
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public FileDaoImpl getDao() {
		return dao;
	}

	public void setDao(FileDaoImpl dao) {
		this.dao = dao;
	}

	public FileServiceImpl getService() {
		return service;
	}

	public void setService(FileServiceImpl service) {
		this.service = service;
	}
}
