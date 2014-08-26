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

package org.kuali.mobility.writer.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.writer.dao.*;
import org.kuali.mobility.writer.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * Implementation of the WriterService.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public class WriterServiceImpl implements WriterService {

	/**
	 * Maximum size an image should be stored as
	 */
	private static final int MAX_IMAGE_HEIGHT = 1080;

	/**
	 * Maximum size an image should be stored as
	 */
	private static final int MAX_IMAGE_WIDTH = 1080;

	/**
	 * Size for thumbnailed images
	 */
	private static final int MAX_IMAGE_THUMB_HEIGHT = 80;

	/**
	 * Size for thumbnailed images
	 */
	private static final int MAX_IMAGE_THUMB_WIDTH = 80;

	private static final float IMAGE_QUALITY = 0.9f;


	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(WriterServiceImpl.class);
	/**
	 * Data Access Object for Articles.
	 */
	@Autowired
	private ArticleDao articleDao;

	/**
	 * Data Access Object for categogies.
	 */
	@Autowired
	private CategoryDao categoryDao;

	/**
	 * Data Access Object for Comments.
	 */
	@Autowired
	private CommentDao commentDao;

	/**
	 * Data Access Object for Media.
	 */
	@Autowired
	private MediaDao mediaDao;

	/**
	 * Data Access Object for Topics.
	 */
	@Autowired
	private TopicDao topicDao;
	/**
	 * Data Access Object for Article Rejections.
	 */
	@Autowired
	private ArticleRejectionDao articleRejectionDao;


//	/**
//	 * A reference to the user notification service
//	 TODO
//	@Autowired
//	private UserPushService userPushService;
//     */


	/**
	 * Instantiates a new Writer service impl.
	 */
	public WriterServiceImpl() {
	}


	@Override
	//@Cacheable(value="wapadArticle", key="#articleId")
	public Article getArticle(long articleId) {
		return articleDao.getArticle(articleId);
	}


	@Override
	public long getNumberSavedArticles(String instance, String userId, boolean isEditor) {
		return this.articleDao.getNumberSavedArticles(instance, userId, isEditor);
	}

	@Override
	public long getNumberRejectedArticles(String tool, String userId) {
		return this.articleDao.getNumberRejectedArticles(tool, userId);
	}

	@Override
	public long getNumberSubmittedArticles(String instance) {
		return this.articleDao.getNumberSubmittedArticles(instance);
	}

	@Override
	public List<Article> getSavedArticles(String tool, String userId, boolean isEditor) {
		return articleDao.getSavedArticles(tool, userId, isEditor);
	}

	@Override
	public List<Article> getRejectedArticles(String tool, String userId) {
		return articleDao.getRejectedArticles(tool, userId);
	}

	@Override
	public List<Article> getSubmittedArticles(String tool) {
		return articleDao.getSubmittedArticles(tool);
	}

	@Override
	public Comment addComment(Comment comment) {
		return commentDao.addComment(comment);
	}

	@Override
	@Transactional
	//@CacheEvict(value="wapadArticle", key="#article.id")
	public Article maintainArticle(Article article) {
		return articleDao.maintainArticle(article);
	}

	@Override
	public List<Comment> getCommentsForArticle(long articleId) {
		return this.commentDao.getCommentsForArticle(articleId);
	}

	@Override
	public int getNumberCommentForArticle(long articleId) {
		return this.getCommentsForArticle(articleId).size();
	}

	@Override
	public ArticleRejection getArticleRejection(long rejectionId) {
		return articleDao.getArticleRejection(rejectionId);
	}

	@Override
	public Media getMedia(long mediaId) {
		return mediaDao.getMedia(mediaId);
	}

	@Override
	public String storeMedia(int mediaType, String extention, boolean isThumbnail, InputStream inputStream) {
		return mediaDao.storeMedia(mediaType, extention, isThumbnail, inputStream);
	}

	@Override
	public Media maintainMedia(Media media) {
		return this.mediaDao.maintainMedia(media);
	}

	@Override
	public void persistArticleRejection(ArticleRejection articleRejection) {
		articleRejectionDao.persistArticleRejection(articleRejection);
	}

	@Override
	//@Cacheable("wapadTopic")
	public List<Topic> getTopics() {
		return topicDao.getTopics();
	}

	@Override
	//@Cacheable(value="wapadTopic", key="#topicId")
	public Topic getTopic(long topicId) {
		return topicDao.getTopic(topicId);
	}

	@Override
	public Topic saveTopic(Topic topic) {
		return this.topicDao.saveTopic(topic);
	}

	@Override
	public void deleteComment(long commentId) {
		this.commentDao.deleteComment(commentId);
	}

	public Article updateMedia(Article article, Media media) {

		// First remove old if there was
		article = this.removeMedia(article, media.getType());
		if (media.getType() == Media.MEDIA_TYPE_VIDEO) {
			article.setVideo(media);
		} else if (media.getType() == Media.MEDIA_TYPE_IMAGE) {
			article.setImage(media);
		}
		article = this.maintainArticle(article);
		return article;
	}

	public Article removeMedia(Article article, int mediaType) {
		Media oldMedia = null;
		if (mediaType == Media.MEDIA_TYPE_VIDEO) {
			oldMedia = article.getVideo();
			article.setVideo(null);
		} else if (mediaType == Media.MEDIA_TYPE_IMAGE) {
			oldMedia = article.getImage();
			article.setImage(null);
		}
		
		/*
		 * If there was old media we need to persist
		 */
		if (oldMedia != null) {
			article = this.maintainArticle(article);
			mediaDao.removeMedia(oldMedia.getId());
		}
		return article;
	}

	public Media uploadMediaData(MultipartFile mediaFile, int mediaType) {
		// TODO this whole media saving code should be moved to a media store project!
		Media returnMedia = null;
		if (mediaFile != null && !mediaFile.isEmpty()) {
			try {
				String mediaExt = null;
				// Get the file extension from the original filename
				if (mediaFile.getOriginalFilename() != null) {
					String oName = mediaFile.getOriginalFilename();
					int index = oName.lastIndexOf('.');
					if (index > 0) {
						mediaExt = oName.substring(index + 1);
					}
				}
				// If the extension is still empty, we fallback to .dat
				if (mediaExt == null || mediaExt.length() == 0) {
					mediaExt = ".dat";
				}

				InputStream originalMediaStream = mediaFile.getInputStream();
				Media uploadedMedia = new Media();
				uploadedMedia.setType(mediaType);
				IOUtils.closeQuietly(originalMediaStream);
				
				/*/
				 * Attempt to get the mime type of the media
				 */
				if (mediaFile.getContentType() == null || mediaFile.getContentType().length() == 0) {
					if (mediaType == Media.MEDIA_TYPE_VIDEO) {
						uploadedMedia.setMimeType("video/3gp"); // Fallback format TODO get proper mapping
					}
				} else {
					uploadedMedia.setMimeType(mediaFile.getContentType());
				}
				if (mediaType == Media.MEDIA_TYPE_VIDEO) {
					String originalPath = this.storeMedia(mediaType, mediaExt, false, originalMediaStream);
					uploadedMedia.setPath(originalPath);
					uploadedMedia.setThumbNailPath(""); // No thumbnail for video for now
				} else if (mediaType == Media.MEDIA_TYPE_IMAGE) {
					uploadedMedia.setMimeType("image/jpeg"); // We allways save images a jpeg

					InputStream scaledImageStream = null;
					String newFilePath = null;
					BufferedImage originalImage = ImageIO.read(mediaFile.getInputStream());
					if (originalImage == null) {
						LOG.error("Failed to read uploaded image - posibly incorrect file type");
						return null;
					}

					// If we are saving an image, we need to resize the original to the max configured size, and also convert to jpeg
					if (originalImage.getWidth() > MAX_IMAGE_WIDTH || originalImage.getHeight() > MAX_IMAGE_HEIGHT) {
						scaledImageStream = this.resizeImage(mediaFile.getInputStream(), MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT);
					}
					// Image is small enough, just convert to jpeg
					else {
						scaledImageStream = this.convertToJPEG(mediaFile.getInputStream());
					}
					if (scaledImageStream == null) {
						LOG.error("Failed to scale uploaded image - posibly incorrect file type");
						return null;
					}
					newFilePath = this.storeMedia(Media.MEDIA_TYPE_IMAGE, ".jpeg", false, scaledImageStream);
					uploadedMedia.setPath(newFilePath);
					IOUtils.closeQuietly(scaledImageStream);


					// Create a thumbnail of the image
					scaledImageStream = this.resizeImage(mediaFile.getInputStream(), MAX_IMAGE_THUMB_WIDTH, MAX_IMAGE_THUMB_HEIGHT);
					if (scaledImageStream == null) {
						LOG.error("Failed to scale thumbnail of uploaded image - posibly incorrect file type");
						return null;
					}
					newFilePath = this.storeMedia(Media.MEDIA_TYPE_IMAGE, ".jpeg", true, scaledImageStream);
					uploadedMedia.setThumbNailPath(newFilePath);
					IOUtils.closeQuietly(scaledImageStream);
				}
				returnMedia = this.maintainMedia(uploadedMedia);
			} catch (IOException e) {
				LOG.error("Exception trying to upload media data", e);
			}
		}
		return returnMedia;
	}

	/**
	 * Resize the image to an appropriate thumbnail size.
	 *
	 * @param inputStream
	 * @return
	 * @throws java.io.IOException
	 */
	private final InputStream convertToJPEG(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(inputStream)
				.scale(1)
				.outputQuality(IMAGE_QUALITY)
				.outputFormat("jpeg")
				.toOutputStream(os);
		InputStream resizedStream = new ByteArrayInputStream(os.toByteArray());
		return resizedStream;
	}

	/**
	 * Resize the image to an appropriate thumbnail size.
	 *
	 * @param inputStream
	 * @return
	 * @throws java.io.IOException
	 */
	private final InputStream resizeImage(InputStream inputStream, int width, int height)
			throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		Thumbnails.of(inputStream)
				.size(width, height)
				.keepAspectRatio(true)
				.outputQuality(IMAGE_QUALITY)
				.outputFormat("jpeg")
				.toOutputStream(os);
		InputStream resizedStream = new ByteArrayInputStream(os.toByteArray());
		return resizedStream;
	}


	@Override
	public long getNumArticles(String tool, long topicId) {
		return this.articleDao.getNumArticles(tool, topicId);
	}

	@Override
	public List<Article> getArticles(String tool, long topicId, int from, int fetchSize) {
		return this.articleDao.getArticles(tool, topicId, from, fetchSize);
	}

	@Override
	public void removeNotifications(String username) {
		// TODO this.userPushService.deletePushesFor(username, "wapad");
	}

	@Override
	public List<Article> searchArticles(String tool, String searchText, int from, int fetchSize) {
		return this.articleDao.searchArticles(tool, searchText, from, fetchSize);
	}

	@Override
	public long searchArticlesCount(String tool, String searchText) {
		return this.articleDao.searchArticlesCount(tool, searchText);
	}

	@Override
	public File getMediaFile(long mediaId, boolean isThumbnail) throws FileNotFoundException {
		return mediaDao.getMedia(mediaId, isThumbnail);
	}

	@Override
	//@Cacheable("wapadCategory")
	public List<Category> getCategories() {
		return categoryDao.getCategories();
	}

	@Override
	//@Cacheable(value="wapadCategory", key="#categoryId")
	public Category getCategory(long categoryId) {
		return categoryDao.getCategory(categoryId);
	}

	@Override
	public Category saveCategory(Category category) {
		return this.categoryDao.saveCategory(category);
	}

}
