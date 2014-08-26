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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.icons.service.IconsService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.writer.entity.*;
import org.kuali.mobility.writer.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * Controller for the writer tool
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
@Controller
@RequestMapping("/writer/{instance}")
public class WriterController {

	private static final Logger LOG = LoggerFactory.getLogger(WriterController.class);

	/**
	 * Name of the icon to use for writer articles
	 */
	private static final String WRITER_ICON_NAME = "WriterArticle";

	/**
	 * A reference to the writer service
	 */
	@Autowired
	@Qualifier("writerService")
	private WriterService writerService;

	/**
	 * A reference to the writer tool's properties
	 */
	@Resource(name = "writerProperties")
	private Properties writerProperties;

	/**
	 * A reference to the iconService
	 */
	@Autowired
	@Qualifier("iconsService")
	private IconsService iconService;

	/**
	 * A reference to the message source for localisation
	 */
	@Resource(name = "messageSource")
	private MessageSource messageSource;

	/**
	 * A reference to the locale resolver
	 */
	@Autowired
	@Qualifier("localeResolver")
	private LocaleResolver localeResolver;

	/**
	 * Default page for writer
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String viewNews(@PathVariable("instance") String instance) {
		return "redirect:/writer/" + instance + "/news"; // default to top stories
	}


	/**
	 * Controller called by ajax request to get more articles
	 * Expected body
	 * {
	 * page : int,
	 * fetchSize : int,
	 * data : {
	 * topicId : int
	 * }
	 * }
	 */
	@RequestMapping(value = "/getNews", method = RequestMethod.POST)
	public ResponseEntity<String> getNews(
			HttpServletRequest request,
			@RequestBody String requestBody,
			@PathVariable("instance") String instance) {

		JSONObject jsonRequest = (JSONObject) JSONSerializer.toJSON(requestBody);
		JSONObject jsonData = jsonRequest.getJSONObject("data");
		int fetchSize = jsonRequest.getInt("fetchSize");
		int page = jsonRequest.getInt("page");
		long topicId = jsonData.getLong("topicId");
		List<Article> articles = writerService.getArticles(instance, topicId, page * fetchSize, fetchSize);
		JSONObject jsonResponse = new JSONObject();
		jsonResponse.accumulate("from", page * fetchSize);
		jsonResponse.accumulate("to", (page * fetchSize) + articles.size());
		jsonResponse.accumulate("available", writerService.getNumArticles(instance, topicId));
		jsonResponse.accumulate("data", convertArticlesToJson(articles, instance, request));

		return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
	}

	private JSONArray convertArticlesToJson(List<Article> articles, String instance, HttpServletRequest request) {
		JSONArray jsonArticles = new JSONArray();

		// If there is now icons we can return the empty array
		if (articles == null || articles.size() == 0) {
			return jsonArticles;
		}

		JSONObject jsonArticle;
		for (Article article : articles) {
			jsonArticle = new JSONObject();
			jsonArticle.accumulate("id", article.getId());
			jsonArticle.accumulate("heading", StringEscapeUtils.escapeHtml(article.getHeading()));
			jsonArticle.accumulate("synopsis", StringEscapeUtils.escapeHtml(article.getSynopsis()));
			jsonArticle.accumulate("journalist", StringEscapeUtils.escapeHtml(article.getJournalist()));
			jsonArticle.accumulate("date", StringEscapeUtils.escapeHtml(formatDate(article.getTimestamp(), localeResolver.resolveLocale(request))));
			if (article.getImage() != null) {
				jsonArticle.accumulate("imageURL", request.getContextPath() + "/writer/" + instance + "/media/" + article.getImage().getId() + "?thumb=1");
			} else {
				if (iconService.iconExists(WRITER_ICON_NAME, instance)) {
					jsonArticle.accumulate("imageURL", request.getContextPath() + "/getIcon/" + WRITER_ICON_NAME + "-" + instance + "-80@2");
				} else {
					jsonArticle.accumulate("imageURL", request.getContextPath() + "/getIcon/" + WRITER_ICON_NAME + "-80@2");
				}
			}
			jsonArticles.add(jsonArticle);
		}
		return jsonArticles;
	}


	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String viewNews(
			HttpServletRequest request,
			@RequestParam(value = "topicId", defaultValue = "0", required = false) long topicId,
			@PathVariable("instance") String instance,
			@CookieValue(required = false, value = "campusSelection") String campus,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// Clear all pending notifications
		this.writerService.removeNotifications(user.getLoginName());

		boolean isJournalist = WriterPermissions.getJournalistExpression(instance).evaluate(user);
		boolean isEditor = WriterPermissions.getEditorExpression(instance).evaluate(user);
		boolean showAdmin = (isJournalist || isEditor);// Flag if the admin button should be displayed
		boolean isPublic = false;
		if (campus == null) {
			isPublic = true;
		}


		List<Topic> topics = writerService.getTopics();


		// Get the label for the current topic
		String topicLabel = "writer.topStories";
		if (topicId > 0) {
			Topic t = writerService.getTopic(topicId);
			if (t != null) {
				topicLabel = t.getLabel();
			}
		}

		// Add to uiModel
		uiModel.addAttribute("showPreferences", isPublic);
		uiModel.addAttribute("showHome", !isPublic);
		uiModel.addAttribute("", topicId);
		uiModel.addAttribute("topicId", topicId);
		uiModel.addAttribute("topicLabel", topicLabel);
		uiModel.addAttribute("topics", topics);
		uiModel.addAttribute("showAdmin", showAdmin);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/viewNews";
	}


	/**
	 * This method is used to view a published article.  If the requested article is invalid or not
	 * yet published the user will be redirected to the view news page.
	 * This method expects the request to come in as: writer/viewArticle?articleId=111
	 *
	 * @param articleId
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/viewArticle", method = RequestMethod.GET)
	public String viewArticle(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) int articleId,
			@PathVariable("instance") String instance,
			Model uiModel) {


		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		Article article = writerService.getArticle(articleId);
		/*
		 * This might be the case if the article was removed, or the user attempted
		 * to enter an article id by hand in the URL.
		 */
		if (article == null || article.getStatus() != Article.STATUS_PUBLISHED) {
			return "redirect:news";
		}
		int comments = writerService.getNumberCommentForArticle(articleId);
		boolean isAdmin = WriterPermissions.getAdminExpression(instance).evaluate(user);

		// Add to uiModel
		uiModel.addAttribute("articleId", articleId);
		uiModel.addAttribute("article", article);
		uiModel.addAttribute("noComments", comments);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		uiModel.addAttribute("allowDelete", isAdmin);
		return "writer/viewArticle";
	}


	/**
	 * This method expects the request to come in as: writer/savedArticles
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/savedArticles", method = RequestMethod.GET)
	public String savedArticles(
			HttpServletRequest request,
			@PathVariable("instance") String instance,
			Model uiModel) {


		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		String userId = user.getLoginName();
		boolean isEditor = WriterPermissions.getEditorExpression(instance).evaluate(user);
		List<Article> articles = writerService.getSavedArticles(instance, userId, isEditor);


		// add to uiModel
		uiModel.addAttribute("emptyMessage", "writer.noSavedArticles"); // property string
		uiModel.addAttribute("articles", articles);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/savedArticles";
	}

	/**
	 * This method expects the request to come in as: writer/submittedArticles
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/submittedArticles", method = RequestMethod.GET)
	public String submittedArticles(
			@PathVariable("instance") String instance,
			Model uiModel) {
		List<Article> articles = writerService.getSubmittedArticles(instance);

		// add to uiModel
		uiModel.addAttribute("emptyMessage", "writer.noSubmittedArticles"); // property string
		uiModel.addAttribute("title", "writer.submittedArticles"); // property string
		uiModel.addAttribute("articles", articles);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/savedArticles";
	}


	/**
	 * This method expects the request to come in as: writer/rejectedArticles
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/rejectedArticles", method = RequestMethod.GET)
	public String rejectedArticles(
			HttpServletRequest request,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		List<Article> articles = writerService.getRejectedArticles(instance, user.getLoginName());

		// add to uiModel
		uiModel.addAttribute("emptyMessage", "writer.noRejectedArticles"); // property string
		uiModel.addAttribute("title", "writer.rejectedArticles"); // property string
		uiModel.addAttribute("articles", articles);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/savedArticles";
	}

	/**
	 * This method expects the request to come in as: writer/rejectedArticles
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/rejectArticle", method = RequestMethod.GET)
	public String rejectArticle(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) long articleId,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// user has to be a editor to view this page.
		if (!WriterPermissions.getEditorExpression(instance).evaluate(user)) {
			// If user not allowed to view this page return to writer home
			return "redirect:/writer/" + instance;
		}

		Article article = writerService.getArticle(articleId);

		// add to uiModel
		uiModel.addAttribute("article", article);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/rejectArticle";
	}

	/**
	 * This method expects the request to come in as: writer/rejectedArticles
	 */
	@RequestMapping(value = "/rejectArticle", method = RequestMethod.POST)
	public String rejectArticle(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) long articleId,
			@RequestParam(value = "reason", required = true) String reason,
			@PathVariable("instance") String instance) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// user has to be a editor to view this page.
		if (!WriterPermissions.getEditorExpression(instance).evaluate(user)) {
			// If user not allowed to view this page return to writer home
			return "redirect:/writer/" + instance;
		}

		Article article = writerService.getArticle(articleId);
		ArticleRejection rejection = new ArticleRejection();
		rejection.setReason(reason);
		rejection.setUserDisplayName(user.getDisplayName());
		rejection.setUserId(user.getLoginName());
		rejection.setArticleId(article.getId());
		article.setRejection(rejection);
		article.setStatus(Article.STATUS_REJECTED); // Set the status as now rejected.

		writerService.persistArticleRejection(rejection);
		writerService.maintainArticle(article);

		return "redirect:admin";
	}

	/**
	 * This method expects the request to come in as: writer/admin
	 *
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin(
			HttpServletRequest request,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// user has to be a journalist of editor to view this page.
		if (!WriterPermissions.getJournalistOrEditorExpression(instance).evaluate(user)) {
			// If user not allowed to view this page return to writer home
			return "redirect:/writer/" + instance;
		}


		String userId = user.getLoginName();
		boolean isJournalist = WriterPermissions.getJournalistExpression(instance).evaluate(user);
		boolean isEditor = WriterPermissions.getEditorExpression(instance).evaluate(user);
		long noSavedArticles = 0;
		long noRejectedArticles = 0;
		long noSubmittedArticles = 0;

		noSavedArticles = writerService.getNumberSavedArticles(instance, userId, isEditor);
		if (isJournalist) {
			noRejectedArticles = writerService.getNumberRejectedArticles(instance, userId);
		}
		if (isEditor) {
			noSubmittedArticles = writerService.getNumberSubmittedArticles(instance);
		}


		// Add to uiModel
		uiModel.addAttribute("noSavedArticles", noSavedArticles);
		uiModel.addAttribute("noRejectedArticles", noRejectedArticles);
		uiModel.addAttribute("noSubmittedArticles", noSubmittedArticles);
		uiModel.addAttribute("isJournalist", isJournalist);
		uiModel.addAttribute("isEditor", isEditor);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/admin";
	}

	/**
	 * This method expects the request to come in as: writer/viewComments?articleId=111
	 *
	 * @param articleId Id of the article to view comments of.
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/viewComments", method = RequestMethod.GET)
	public String viewComments(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) long articleId,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		List<Comment> comments = writerService.getCommentsForArticle(articleId);
		boolean allowPlaceComment = !WriterPermissions.getSpammerExpression(instance).evaluate(user);
		boolean allowDeleteComment = WriterPermissions.getEditorOrAdminExpression(instance).evaluate(user);
		
		/*
		 * If the user is not logged in, we will display the comment button, but the user
		 * will then be asked to log in, and then the option to comment will be taken
		 * away if the user is not allowed to place comments.
		 */
		if (!user.isPublicUser() && !WriterPermissions.getSpammerExpression(instance).evaluate(user)) {
			allowPlaceComment = true;
		}


		// Add items to uiModel
		uiModel.addAttribute("allowPlaceComment", allowPlaceComment);
		uiModel.addAttribute("allowDeleteComment", allowDeleteComment);
		uiModel.addAttribute("articleId", articleId);
		uiModel.addAttribute("comments", comments);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/viewComments";
	}

	/**
	 * This method expects the request to come in as: writer/addComment?articleId=111
	 *
	 * @param articleId Id of the article to view comments of.
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.GET)
	public String addComment(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) int articleId,
			@PathVariable("instance") String instance,
			Model uiModel) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// If user not allowed to place comments, take back to the article
		if (WriterPermissions.getSpammerExpression(instance).evaluate(user)) {
			return "redirect:/writer/viewComments?articleId=" + articleId;
		}

		Article article = writerService.getArticle(articleId);

		// add to uiModel
		uiModel.addAttribute("article", article);
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/addComment";
	}

	/**
	 * Removes a comment
	 */
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public ResponseEntity<String> deleteComment(HttpServletRequest request,
												@RequestParam long commentId,
												@PathVariable("instance") String instance) {


		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// First check if the user may delete comments
		boolean allowDeleteComment = WriterPermissions.getEditorOrAdminExpression(instance).evaluate(user);
		if (!allowDeleteComment) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}

		this.writerService.deleteComment(commentId);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * Request to mark an article as deleted
	 *
	 * @return Http 200 is success, Http 401 if user is not admin, Http 404 if the article is not found
	 */
	@RequestMapping(value = "/deleteArticle", method = RequestMethod.GET)
	public ResponseEntity<String> deleteArticle(HttpServletRequest request,
												@PathVariable("instance") String instance,
												@RequestParam("articleId") long articleId) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// Check if the user has admin rigths
		if (!WriterPermissions.getAdminExpression(instance).evaluate(user)) {
			return new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		}

		Article article = writerService.getArticle(articleId);

		// Check if the article existed
		if (article == null) {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}

		// Update status and maintain article
		article.setStatus(Article.STATUS_DELETED);
		writerService.maintainArticle(article);

		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * This method expects the request to come in as: writer/addComment?articleId=111
	 *
	 * @param articleId Id of the article to view comments of.
	 * @return
	 */
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public String addComment(
			HttpServletRequest request,
			@RequestParam(value = "articleId", required = true) int articleId,
			@RequestParam(value = "commentTitle", required = true) String commentTitle,
			@RequestParam(value = "commentText", required = true) String commentText,
			@PathVariable("instance") String instance) {

		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);

		// If user not allowed to place comments, take back to the article
		if (WriterPermissions.getSpammerExpression(instance).evaluate(user)) {
			return "redirect:/writer/" + instance + "viewComments?articleId=" + articleId;
		}

		Comment comment = new Comment();
		comment.setArticleId(articleId);
		comment.setTimestamp(new Date());
		comment.setTitle(commentTitle);
		comment.setUserDisplayName(user.getDisplayName() == null ? user.getLoginName() : user.getDisplayName());
		comment.setText(commentText);

		writerService.addComment(comment);


		// add to uiModel
		return "redirect:viewComments?articleId=" + articleId;
	}

	/**
	 * Controller for the search article page
	 */
	@RequestMapping(value = "/searchArticle", method = RequestMethod.GET)
	public String search(
			@PathVariable("instance") String instance,
			Model uiModel) {

		// add to uiModel
		uiModel.addAttribute("toolInstance", instance);
		uiModel.addAttribute("theme", getInstanceTheme(instance));
		return "writer/searchArticle";
	}

	/**
	 * Controller for the ajax call to search for articles
	 */
	@RequestMapping(value = "/searchArticle", method = RequestMethod.POST)
	public ResponseEntity<String> search(
			HttpServletRequest request,
			@PathVariable("instance") String instance,
			@RequestBody String requestBody) {

		JSONObject jsonRequest = (JSONObject) JSONSerializer.toJSON(requestBody);
		JSONObject jsonData = jsonRequest.getJSONObject("data");
		int fetchSize = jsonRequest.getInt("fetchSize");
		int page = jsonRequest.getInt("page");
		String searchText = jsonData.getString("searchText");

		List<Article> articles = writerService.searchArticles(instance, searchText, page * fetchSize, fetchSize);
		long available = this.writerService.searchArticlesCount(instance, searchText);

		JSONObject jsonResponse = new JSONObject();
		jsonResponse.accumulate("from", page * fetchSize);
		jsonResponse.accumulate("to", (page * fetchSize) + articles.size());
		jsonResponse.accumulate("available", available);
		jsonResponse.accumulate("data", convertArticlesToJson(articles, instance, request));

		// add to uiModel
		return new ResponseEntity<String>(jsonResponse.toString(), HttpStatus.OK);
	}

	/**
	 * Formats a date
	 *
	 * @param date
	 * @param locale
	 * @return
	 */
	private String formatDate(Date date, Locale locale) {
		return DateFormatUtils.format(date, messageSource.getMessage("shared.dateTimeFormatFull", null, locale));
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
