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

package org.kuali.mobility.writer.controllers;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.icons.service.IconsService;
import org.kuali.mobility.push.entity.Device;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.shared.interceptors.NativeCookieInterceptor;
import org.kuali.mobility.writer.entity.*;
import org.kuali.mobility.writer.service.PublishService;
import org.kuali.mobility.writer.service.WriterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Controller for edit article
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Controller
@RequestMapping("/writer/{instance}/editArticle")
public class WriterEditArticleController {

	/**
	 * Name of the icon to use for writer articles
	 */
	private static final String WRITER_ICON_NAME = "WriterArticle";

	private static final Logger LOG = LoggerFactory.getLogger(WriterEditArticleController.class);

	/**
	 * Reference to the writerService
	 */
	@Autowired
	@Qualifier("writerService")
	private WriterService service;

	@Autowired
	@Qualifier("iconsService")
	private IconsService iconService;


	public void setWriterService(WriterService service) {
		this.service = service;
	}

	@Autowired
	@Qualifier("publishService")
	private PublishService publishService;

	@Resource(name = "writerProperties")
	private Properties writerProperties;

	/**
	 * Default page for writer
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(@PathVariable("instance") String instance) {
		return "redirect:/writer/" + instance + "/editArticle/new";
	}

	/**
	 * This will happen in the rare case where the user created a new article, saved it
	 * and goes back in history. The session object will then not be available for the controller
	 * at /editArticle/new. Therefor we redirect to the first page that will set a new
	 * object in the session again, and continue as usual
	 *
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(HttpSessionRequiredException.class)
	public String handleHttpSessionRequiredException(
			HttpSessionRequiredException ex) {
		return "redirect:.";
	}

	/**
	 * Edit existing article
	 */
	@RequestMapping(value = "/{articleId}", method = RequestMethod.GET)
	public String editExistingArticle(
			HttpServletRequest request,
			@PathVariable long articleId,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// Temporary code to determine the device platform and or browser
		// BB returns null for device platform must then use user Agent.
		String nativePlatform = (String) request.getSession().getAttribute(NativeCookieInterceptor.SESSION_PLATFORM);
		boolean isBlackberry = Device.TYPE_BLACKBERRY.equalsIgnoreCase(nativePlatform);

		Article article = service.getArticle(articleId);

		// If the article is saved to an editor, and this is not the same editor
		if (article.getStatus() == Article.STATUS_SUBMITTED_SAVED
				&& !user.getLoginName().equals(article.getEditorId())) {
			// TODO rather redirect to a page where the user is informed of an error
			return "redirect:/writer/" + instance + "/admin";
		}

		// If an article is already published or deleted you cannot edit it again
		if (article.getStatus() == Article.STATUS_PUBLISHED || article.getStatus() == Article.STATUS_DELETED) {
			// TODO rather redirect to a page where the user is informed of an error
			return "redirect:/writer/" + instance + "/admin";
		}

		// If the article is submitted, and you are not an editor
		if (article.getStatus() == Article.STATUS_SUBMITTED && !WriterPermissions.getEditorExpression(instance).evaluate(user)) {
			// TODO rather redirect to a page where the user is informed of an error
			return "redirect:/writer/" + instance + "/admin";
		}

		List<Topic> topics = service.getTopics();
		List<Category> categories = service.getCategories();
		//Category[] categories = Category.values();
		String username = user.getLoginName();
		boolean isJournalist = WriterPermissions.getJournalistExpression(instance).evaluate(user);
		boolean isEditor = WriterPermissions.getEditorExpression(instance).evaluate(user);

		boolean canSubmit = !isEditor && isJournalist; // Editors cannot submit, only journalists
		boolean canPublish = isEditor;
		
		/*
		 * If the article is submitted and a editor is editing the article
		 * we need to immediately save the article to the editor
		 */
		if (article.getStatus() == Article.STATUS_SUBMITTED && isEditor) {
			article.setEditorId(username);
			article.setStatus(Article.STATUS_SUBMITTED_SAVED);
			article = this.service.maintainArticle(article);
		}

		//If user does not have Editor permission, can't reject, and if logged in user is the journalist of the article he can't reject
		boolean canReject = isEditor && article.getJournalistId() != null && !article.getJournalistId().equals(username);


		if (iconService.iconExists(WRITER_ICON_NAME, instance)) {
			uiModel.addAttribute("defaultIcon", WRITER_ICON_NAME + "-" + instance + "-80@2.png");
		} else {
			uiModel.addAttribute("defaultIcon", WRITER_ICON_NAME + "-80@2.png");
		}

		// add to uiModel
		uiModel.addAttribute("article", article);
		uiModel.addAttribute("topics", topics);
		uiModel.addAttribute("categories", categories);
		uiModel.addAttribute("canSubmit", canSubmit);
		uiModel.addAttribute("canPublish", canPublish);
		uiModel.addAttribute("canReject", canReject);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		uiModel.addAttribute("nativePlatform", nativePlatform);
		uiModel.addAttribute("isBlackberry", isBlackberry);
		return "writer/editArticle";
	}

	/**
	 * This method is called by ajax when using the native app to create articles
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createArticle(
			HttpServletRequest request,
			@RequestHeader(required = false, value = "User-Agent", defaultValue = "unknown") String userAgent,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);


		// Temporary code to determine the device platform and or browser
		// BB returns null for device platform must then use user Agent.
		String nativePlatform = (String) request.getSession().getAttribute(NativeCookieInterceptor.SESSION_PLATFORM);
		boolean isBlackberry = Device.TYPE_BLACKBERRY.equalsIgnoreCase(nativePlatform);

		Article article = new Article();
		List<Topic> topics = service.getTopics();
		List<Category> categories = service.getCategories();

		boolean isJournalist = WriterPermissions.getJournalistExpression(instance).evaluate(user);
		boolean isEditor = WriterPermissions.getEditorExpression(instance).evaluate(user);

		boolean canSubmit = isJournalist;
		boolean canPublish = isEditor;
		boolean canReject = false; // Cannot reject new articles

		article.setJournalist(user.getDisplayName());
		article.setJournalistId(user.getLoginName());


		// add to uiModel
		uiModel.addAttribute("article", article);
		uiModel.addAttribute("topics", topics);
		uiModel.addAttribute("categories", categories);
		uiModel.addAttribute("canSubmit", canSubmit);
		uiModel.addAttribute("canPublish", canPublish);
		uiModel.addAttribute("canReject", canReject);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		uiModel.addAttribute("nativePlatform", nativePlatform);
		uiModel.addAttribute("isBlackberry", isBlackberry);
		return "writer/editArticle";
	}


	@RequestMapping(value = "/createNew", method = RequestMethod.POST)
	public
	@ResponseBody
	String createArticle(
			HttpServletRequest request,
			@ModelAttribute("article") Article article,
			@PathVariable("instance") String instance) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		article.setStatus(Article.STATUS_SAVED);
		article.setJournalist(user.getDisplayName());
		article.setJournalistId(user.getLoginName());
		if (WriterPermissions.getEditorExpression(instance).evaluate(user)) {
			article.setEditorId(user.getLoginName());
		}
		article.setTimestamp(new Date());
		article.setToolInstance(instance);
		service.maintainArticle(article);
		JSONObject response = new JSONObject();
		response.accumulate("articleId", article.getId());
		return response.toString();

	}

	@RequestMapping(value = "/discard/{articleId}", method = RequestMethod.GET)
	public String discardArticle(
			HttpServletRequest request,
			@PathVariable("instance") String instance,
			@PathVariable long articleId) {


		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		/*
		 *  First make sure user is logged in.
		 *  The interceptor cannot catch this type of URL
		 */
		if (!user.isPublicUser()) {
			return "redirect:/login?returnURL=/writer/editArticle?articleId=" + articleId;
		}

		// Make sure the user is of this group
		if (!WriterPermissions.getJournalistOrEditorExpression(instance).evaluate(user)) {
			return "redirect:/writer";
		}

		Article article = service.getArticle(articleId);

		if (article != null) {
			article.setStatus(Article.STATUS_DISCARDED);
			service.maintainArticle(article);
		}
		return "redirect:../../admin";
	}


	/**
	 *
	 */
	@RequestMapping(value = "/maintainArticle")
	public String maintainArticle(
			HttpServletRequest request,
			@ModelAttribute("article") Article articleObj,
			@RequestParam(value = "uploadVideo", required = false) MultipartFile videoFile,
			@RequestParam(value = "uploadImage", required = false) MultipartFile imageFile,
			@RequestParam(value = "pageAction", required = true) int pageAction,
			@RequestParam(value = "topicId", required = true) long topicId,
			@RequestParam(value = "articleId", required = false) Long articleId,
			@RequestParam(value = "removeImage", required = false, defaultValue = "false") boolean removeImage,
			@RequestParam(value = "removeVideo", required = false, defaultValue = "false") boolean removeVideo,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		Article article = null;
		if (articleId != null) {
			article = this.service.getArticle(articleId);
		}

			/*
			 * If an article for the submitted id does not exist we can submit that object.
			 * If an article for the submitted id DOES exist, we need to get the latest copy
			 * from the database, update the object, and persist
			 */
		if (article != null) {
				/* Copy all the fields from the submitted article object to the one
				 * we need to persist */
			Article original = this.service.getArticle(articleId);
			BeanUtils.copyProperties(articleObj, article);
			article.setId(original.getId());
			article.setVideo(original.getVideo());
			article.setImage(original.getImage());
			article.setVersionNumber(original.getVersionNumber());
		} else {
			article = articleObj;
		}
			
			
			/*
			 * If no journalist name has been set yet, then this user
			 * is the journalist.
			 */
		if (article.getJournalist() == null) {
			article.setJournalist(user.getDisplayName() == null ? user.getLoginName() : user.getDisplayName());
			article.setJournalistId(user.getLoginName());
		}
			/*
			 * if the user is the editor we have to set the editor field.
			 */
		if (WriterPermissions.getEditorExpression(instance).evaluate(user)) {
			article.setEditorId(user.getLoginName());
		} else {
			article.setEditorId(null);
		}
		article.setTimestamp(new Date());
		article.setToolInstance(instance);

		int newState = pageAction;
			/*
			 * If we are rejecting an article, we are first going to save the 
			 * article in the current editor's list. The next page will complete
			 * setting the status to rejected, to avoid rejected articles without
			 * a reason when the user cancels the Article Rejection Screen.
			 */
		if (pageAction == Article.STATUS_REJECTED) {
			newState = Article.STATUS_SAVED;
		}

		/**
		 * If the article was submitted and saved, we need to preserve that state
		 */
		if (newState == Article.STATUS_SAVED && article.getStatus() == Article.STATUS_SUBMITTED_SAVED) {
			article.setStatus(Article.STATUS_SUBMITTED_SAVED);
		} else {
			article.setStatus(newState);
		}
		Topic topic = service.getTopic(topicId);
		article.setTopic(topic);
			
			/* Flag if there was a problem saving the media, we do not want the user to loose changes to
			 * the article if something went wrong saving the media */
		boolean mediaFailure = false;


		if (removeImage) {
			article = service.removeMedia(article, Media.MEDIA_TYPE_IMAGE);
		} else if (imageFile != null && !imageFile.isEmpty()) {
			Media image = service.uploadMediaData(imageFile, Media.MEDIA_TYPE_IMAGE);
			if (image != null) {
				article = service.updateMedia(article, image);
			} else {
				LOG.warn("There was an error saving the new image for the article");
				mediaFailure = true;
			}
		}

		if (removeVideo) {
			article = service.removeMedia(article, Media.MEDIA_TYPE_VIDEO);
		} else if (videoFile != null && !videoFile.isEmpty()) {
			Media video = service.uploadMediaData(videoFile, Media.MEDIA_TYPE_VIDEO);
			article = service.updateMedia(article, video);
		}

		article = service.maintainArticle(article);
		// After persisting the content of the article, we inform the user of the media problem
		if (mediaFailure) {
			uiModel.addAttribute("backURL", "/writer/" + instance + "/editArticle/" + article.getId());
			uiModel.addAttribute("errorMessage", "writer.errorUploadingMedia");
			uiModel.addAttribute("errorTitle", "writer.somethingWentWrong");
			return "error";
		}
			/*
			 * Send notification for new article
			 */
		if (pageAction == Article.STATUS_PUBLISHED) {
			publishService.publishArticle(article, user); // Async
		}


		if (pageAction == Article.STATUS_SAVED) {// save action
			return "redirect:/writer/" + instance + "/savedArticles";
		} else if (pageAction == Article.STATUS_PUBLISHED) {// publish action
			return "redirect:/writer/" + instance + "/viewArticle?articleId=" + article.getId();
		} else if (pageAction == Article.STATUS_REJECTED) {// publish action
			return "redirect:/writer/" + instance + "/rejectArticle?articleId=" + article.getId();
		}
		return "redirect:/writer/" + instance + "/admin";
	}


	@RequestMapping(value = "/mediaUpload", method = RequestMethod.POST)
	public ResponseEntity<String> mediaUpload(HttpServletRequest request,
											  @RequestParam(value = "file", required = false) MultipartFile mediaFile,
											  @RequestParam(value = "mediaType") int mediaType,
											  @RequestParam(value = "articleId", required = false) long articleId,
											  Model uiModel) {


		Media media = service.uploadMediaData(mediaFile, mediaType);
		if (media == null) {
			return new ResponseEntity<String>(HttpStatus.METHOD_FAILURE);
		} else {
			Article article = (Article) uiModel.asMap().get("article");
			boolean hadArticle = true;
			if (article == null) {
				article = this.service.getArticle(articleId);
				hadArticle = false;
			}

			article = service.updateMedia(article, media);
			if (hadArticle) {
				uiModel.addAttribute("article", article);
			}
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	private final String getInstanceTheme(String instance) {
		if (writerProperties == null) {
			return null;
		}
		String theme = null;
		// Check if we need to do instance themes
		if ("true".equals(writerProperties.getProperty("writer.instanceThemes", "false"))) {
			theme = instance;
			// Check if we need to map this theme
			String mappedTheme = writerProperties.getProperty("writer.instanceThemesMap." + instance.toLowerCase());
			if (!StringUtils.isEmpty(mappedTheme)) {
				theme = mappedTheme.toLowerCase();
			}
		}
		return theme;
	}

}
