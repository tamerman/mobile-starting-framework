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

package org.kuali.mobility.writer.dao;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.writer.entity.Media;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.util.Properties;

/**
 * Implementation for the MediaDao
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
@Repository
public class MediaDaoImpl implements MediaDao{

    private static final String PROPERTY_MEDIA_DIRECTORY = "writer.mediaDir";

	private static final Logger LOG = LoggerFactory.getLogger(MediaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

    @Resource(name="writerProperties")
    private Properties writerProperties;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * Directory where media is stored
	 */
	private File mediaDirectory;

    private File getMediaDirectory() throws FileNotFoundException{
        if (mediaDirectory == null){
            String directoryPath = writerProperties.getProperty(PROPERTY_MEDIA_DIRECTORY);
            mediaDirectory = new File(directoryPath);
            if(!mediaDirectory.exists()){
                LOG.error("Media directory does not exist : " + directoryPath);
                throw new FileNotFoundException("Media directory does not exist : " + directoryPath);
            }
        }
        return mediaDirectory;
    }


	@Override
	public Media getMedia(long mediaId) {
		return getEntityManager().find(Media.class, mediaId);
	}


	@Override
	public String storeMedia(int mediaType, String extention, boolean isThumbnail, InputStream inputStream){
		String filepath = null;
		OutputStream output = null;
		try {
			File outputFile;
			/*
			 *  Generate new filename until one is found that does not exist.
			 *  This loop will normally exit the first time, but in the very unlikely 
			 *  case that more than one media is uploaded at the exact same time, 
			 *  this loop will run more than once.
			 *  TODO: This section could be placed in a synchronised block to
			 *  better cater for multiple threads attempting to create the same file
			 */
			if (!extention.startsWith(".")){
				extention = "." + extention;
			}
			do{
				// TODO find a better way to generate filenames
				filepath =  File.separatorChar + System.currentTimeMillis() + (isThumbnail ? "_thumb.png" : extention); 
				outputFile = new File(getMediaDirectory(),filepath);
			}while (outputFile.exists());
			output = new FileOutputStream(outputFile);
			IOUtils.copy(inputStream, output);
		} catch (IOException e) {
			LOG.warn("Exception while trying to store media stream to filesystem", e);
		}
		finally{
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(output);
		}
		return filepath;
	}


	@Override
	@Transactional
	public Media maintainMedia(Media media) {
		if (media.getId() == null){
			getEntityManager().persist(media);
		}
		else {
			media = getEntityManager().merge(media);
		}
		return media;
	}


	@Override
	@Transactional
	public void removeMedia(long mediaId) {
		Media m = this.getMedia(mediaId);
		if (m != null){
			String path = m.getPath();
			String thumbPath = m.getThumbNailPath();
			/*
			 * Delete the media if available
			 */
			if (path != null && path.length() > 0){
				try {
					FileUtils.forceDelete(new File(getMediaDirectory(), path));
				}catch (IOException e) {
					LOG.error("Exception trying to delete media", e);
				}
			}
			/*
			 * Delete the thumbnail if available
			 */
			if (thumbPath != null  && thumbPath.length() > 0){
				try {
					FileUtils.forceDelete(new File(getMediaDirectory(), thumbPath));
				}catch (IOException e) {
					LOG.error("Exception trying to delete media thumbnail", e);
				}
			}
			getEntityManager().remove(m);
		}
	}


	@Override
	public File getMedia(long mediaId, boolean isThumbnail) throws FileNotFoundException {
		Media media = getMedia(mediaId);
		File mediaFile;
		if (isThumbnail){
			mediaFile = new File(getMediaDirectory(), media.getThumbNailPath());
		}else {
			mediaFile = new File(getMediaDirectory(), media.getPath());
		}
		return mediaFile;
	}


}
