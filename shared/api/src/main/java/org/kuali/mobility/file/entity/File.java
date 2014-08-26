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

package org.kuali.mobility.file.entity;

import java.io.IOException;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

//import org.kuali.mobility.file.dao.FileDaoImpl;
import org.springframework.web.multipart.MultipartFile;

// TODO: Move out of the reporting tool. 

@Entity
@Table(name = "KME_FILE_T")
public class File {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID", nullable = false, updatable = false)
	private Long id;

	@Transient
	private MultipartFile file;

	@Lob
	@Column(name = "FILE_DATA")
	private byte[] bytes;

	@Column(name = "CNTNT_TYP")
	private String contentType;

	@Column(name = "FILE_NM")
	private String fileName;

	@Column(name = "FILE_SZ")
	private int fileSize;

	@Column(name = "PST_TS")
	private Timestamp postedTimestamp;


	@Version
	@Column(name = "VER_NBR")
	protected Long versionNumber;

	public File() {
	}

	public File(MultipartFile file) {
		this.id = id;
		this.file = file;
		this.fileName = file.getOriginalFilename();
		this.contentType = file.getContentType();
		try {
			this.bytes = file.getBytes();
			this.fileSize = file.getBytes().length;
		} catch (IOException e) {
			System.out.println("getBytes Error: " + e.getMessage());
		}
	}

	public File(Long id, MultipartFile file) {
		this.id = id;
		this.file = file;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getFileSize() {
		return fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}

	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}

	@Override
	public String toString() {
		String newline = "\r\n";
		String str = newline + "File Id:     " + this.getId();
		str = str + newline + "Filename:    " + this.getFileName();
		str = str + newline + "ContentType: " + this.getContentType();
		str = str + newline + "Size:        " + this.getFileSize();
		str = str + newline + "Timestamp:   " + this.getPostedTimestamp();
		return str;
	}

}
