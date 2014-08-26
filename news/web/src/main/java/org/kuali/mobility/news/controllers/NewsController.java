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

package org.kuali.mobility.news.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.configparams.service.ConfigParamService;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.service.NewsService;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.shared.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Properties;

/**
 * A controller for handling requests for the News tool.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Controller
@RequestMapping("/news")
public class NewsController {

	private static final Logger LOG = LoggerFactory.getLogger(NewsController.class);
	private final Boolean ACTIVE = new Boolean(true);

	@Autowired
	private NewsService newsService;

	@Autowired
	private ConfigParamService configParamService;

	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	/**
	 * The main entry point for the News tool. Sets an ordered list of active
	 * NewsFeed objects to the view.
	 *
	 * @param uiModel
	 * @param request
	 * @return the path to the home page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String newsHome(HttpServletRequest request, Model uiModel) {
		String viewName = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			viewName = "redirect:/campus?toolName=news";
		} else if ("3".equalsIgnoreCase(getKmeProperties().getProperty("kme.uiVersion", "classic"))) {
			viewName = "ui3/news/index";
		} else {
			campus = user.getViewCampus();
			List<NewsSource> sources = (List<NewsSource>) getNewsService().getNewsSources(Long.valueOf(0), ACTIVE);
			int sampleSize = 2;
			try {
				String configSampleSize = getConfigParamService().findValueByName("News.Sample.Size");
				sampleSize = Integer.parseInt(configSampleSize);
			} catch (Exception e) {
			}
			if (sources == null || sources.size() == 0) {
				LOG.debug("======= No NewsFeed found for parent 0. ========");
			}
			for (NewsSource s : sources) {
				LOG.debug("Feed for News Source: " + s.getId());
			}

			uiModel.addAttribute("newsSources", sources);
			uiModel.addAttribute("allNewsSources", getNewsService().getAllActiveNewsSources());
			uiModel.addAttribute("sampleSize", sampleSize);
			// removeFromCache(request.getSession(), PEOPLE_SEARCH_RESULT);
			// request.setAttribute("watermark",
			// "[Keyword] or [Last, First] or [First Last]");
			uiModel.addAttribute("basicSearchDiv", "div1");
			uiModel.addAttribute("advancedSearchDiv", "div2");


			viewName = "news/newsHome";
		}
		return viewName;
	}

	/**
	 * Handles requests for feeds and articles.
	 *
	 * @param request
	 * @param sourceId  the id of the NewsSource for the feed/article to retrieve
	 * @param articleId (optional) the id of an article to retrieve. If this is
	 *                  not present, the whole feed is returned.
	 * @param uiModel
	 * @return the path to the feed page or the article page, depending on the
	 * presence of articleId
	 */
	@RequestMapping(value = "/source", method = RequestMethod.GET)
	public String getNewsArticle(HttpServletRequest request, @RequestParam("id") long sourceId, @RequestParam(value = "articleId", required = false) String articleId, Model uiModel) {
		String viewName = null;
		User user = (User) request.getSession().getAttribute(Constants.KME_USER_KEY);
		String campus = null;
		if (user.getViewCampus() == null) {
			viewName = "redirect:/campus?toolName=news";
		} else {
			campus = user.getViewCampus();
			LOG.debug("getNewsArticle() : sourceId = " + sourceId + " articleId = " + articleId);
			if (articleId != null && articleId != "") {
				uiModel.addAttribute("article", getNewsService().getNewsArticle(articleId, sourceId));
				uiModel.addAttribute("feedTitle", getNewsService().getNewsSourceById(sourceId).getTitle());
				LOG.debug("Forwarding to news/newsArticle");
				viewName = "news/newsArticle";
			} else {
				List<NewsSource> feeds = (List<NewsSource>) getNewsService().getNewsSources(Long.valueOf(sourceId), ACTIVE);
				if (feeds == null || feeds.isEmpty()) {
					uiModel.addAttribute("feed", getNewsService().getNewsSourceById(sourceId));
					LOG.debug("Forwarding to news/newsStream");
					viewName = "news/newsStream";
				} else {
					uiModel.addAttribute("newsSources", feeds);
					int sampleSize = 2;
					try {
						String configSampleSize = getConfigParamService().findValueByName("News.Sample.Size");
						sampleSize = Integer.parseInt(configSampleSize);
					} catch (Exception e) {
					}
					uiModel.addAttribute("sampleSize", sampleSize);
					viewName = "news/newsHome";
				}
			}
		}
		return viewName;
	}

	@RequestMapping(value = "/templates/{key}")
	public String getAngularTemplates(
			@PathVariable("key") String key,
			HttpServletRequest request,
			Model uiModel) {
		return "ui3/news/templates/" + key;
	}

	@RequestMapping(value = "/js/news.js")
	public String getJavaScript(Model uiModel, HttpServletRequest request) {
		return "ui3/news/js/news";
	}


	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public ConfigParamService getConfigParamService() {
		return configParamService;
	}

	public void setConfigParamService(ConfigParamService configParamService) {
		this.configParamService = configParamService;
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}
}
