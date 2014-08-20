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

import org.kuali.mobility.file.dao.FileDao;
import org.kuali.mobility.file.entity.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service for doing the actual work of interacting with Campus objects.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Service
public class FileServiceImpl implements FileService {

	private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);
	private String ME = this.getClass().getName();
	
	@Autowired
	private FileDao fileDao;
	
	@Override
	@Transactional
	public Long saveFile(File file){
		return fileDao.saveFile(file);
	}

	@Transactional
	public boolean removeFile(File file){
		return fileDao.removeFile(file);
	}	
	
	@Transactional
	public File findFileById(Long Id){
		return fileDao.findFileById(Id);
	}
	
	@Transactional
	public List<File> findFilesByName(String name){
		return fileDao.findFilesByName(name);
	}

	@Transactional
	public List<File> findAllFiles(){
		return fileDao.findAllFiles();
	}
	
	public void setFileDao(FileDao dao){
		this.fileDao = dao;
	}
	
	public FileDao getFileDao(){
		return this.fileDao;
	}
	
	
}
