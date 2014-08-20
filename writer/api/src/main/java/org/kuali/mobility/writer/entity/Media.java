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

package org.kuali.mobility.writer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A class representing media on the webserver.
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@Entity
@Table(name="WRITER_MEDIA")
public class Media {

	/*
		TODO - This class with services should be moved to a shared service in KME
	 */

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	
	
	/**
	 * Primary key of this media.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ID")
	private Long id;
	
	/**
	 * Content type of this media.
	 * 1 - Image
	 * 2 - Video
	 */
	@Column(name="TYPE", nullable=false)
	private int type;
	
	/**
	 * Path to the media on the webserver.
	 */
	@Column(name="PATH", nullable=false)
	private String path;
	
	/**
	 * Path to the thumbnail on the webserver.
	 */
	@Column(name="PATH_THUMB", nullable=false)
	private String thumbNailPath;

	/**
	 * MIME type of this media
	 */
	@Column(name="MIME_TYPE")
	private String mimeType;
	
	@Version
	@Column(name="VER_NBR")
	protected long versionNumber;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the thumbNailPath
	 */
	public String getThumbNailPath() {
		return thumbNailPath;
	}

	/**
	 * @param thumbNailPath the thumbNailPath to set
	 */
	public void setThumbNailPath(String thumbNailPath) {
		this.thumbNailPath = thumbNailPath;
	}
	

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	/**
	 * Sets the version number of the ArticleRejection
	 * @return
	 */
	public long getVersionNumber() {
		return versionNumber;
	}
	

	/**
	 * Gets the version number of the ArticleRejection
	 * @return
	 */
	public void setVersionNumber(long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(256);
		sb.append("Media[");
		sb.append("id=").append(this.id);
		sb.append(",path=").append(this.path);
		sb.append(",thumbNailPath=").append(this.thumbNailPath);
		sb.append(",mimeType=").append(this.mimeType);
		sb.append(",version=").append(this.versionNumber);
		sb.append(",type=").append(this.type).append("]");
		return sb.toString();
	}
}
