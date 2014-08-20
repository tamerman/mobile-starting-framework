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

package org.kuali.mobility.writer.boot;

import org.kuali.mobility.shared.listeners.Bootables;
import org.kuali.mobility.writer.entity.Article;
import org.kuali.mobility.writer.entity.Category;
import org.kuali.mobility.writer.entity.Comment;
import org.kuali.mobility.writer.entity.Topic;
import org.kuali.mobility.writer.service.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.Properties;

/**
 * Boot listener for the writer tool.
 * This boot listener will insert dummy data for the writer tool.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.1
 */
public class WriterBootListener implements ServletContextListener {

	/**
	 * A reference to the Bootables
	 */
	@Autowired
	@Qualifier("bootables")
	private Bootables bootables;

    /**
     * A reference to the writer service.
     */
	private WriterService writerService;


	/**
	 *
	 */
	public void initialise(){
		bootables.registeredBootable(this);
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        Properties writerProperties = (Properties)ctx.getBean("writerProperties");
        writerService = (WriterService)ctx.getBean("writerService");
		/*
		 * If the writer tool is not configured to do bootstrapping we will exit
		 */
		if (!"true".equals(writerProperties.getProperty("writer.bootstrap"))){
			return;
		}

		Topic[] topics = createTopics();
		Category[] categories = createCategories();
		createArticles(topics, categories);

	}

	/**
	 * Create dummy articles
	 */
	private void createArticles(Topic[] topics, Category[] categories){
		// Create a few published articles
		for(int idx = 0 ; idx < 15 ; idx++){
			Article article = new Article();
			article.setCategory(categories[0].getId());
			article.setEditorId("the_editor");
			article.setHeading("Dummy published article " + idx);
			article.setJournalist("KME Journalist");
			article.setJournalistId("the_journalist");
			article.setLinkUrl("http://www.kuali.org");
			article.setStatus(Article.STATUS_PUBLISHED);
			article.setSynopsis("This is the synopsis for dummy article " + idx);
			article.setText("This is the main text for dummy article " + idx + ". Here is where the bulk of the article should be written");
			article.setTimestamp(new Date());
			article.setTopic(topics[idx % 4]);
			article.setToolInstance("default");
			article = writerService.maintainArticle(article);
			createComments(article);
		}
	}


	/**
	 * Create dummy comments on the article
	 * @param article
	 */
	private void createComments(Article article){
		// Add a view comments to the article
		for(int cdx = 0 ; cdx < 3 ; cdx++){
			Comment c = new Comment();
			c.setTimestamp(new Date());
			c.setText("Dummy article comment text #" + cdx);
			c.setArticleId(article.getId());
			c.setTitle("Title for dummy comment " + cdx);
			c.setUserDisplayName("KME commenter " + (cdx + article.getId()));
			writerService.addComment(c);
		}
	}

	/**
	 * Create categories
	 * @return
	 */
	private Category[] createCategories(){
		Category[] categories = new Category[2];
		// Create categories
		categories[0] = new Category();
		categories[0].setDescription("Common");
		categories[0].setLabel("writer.common");
		categories[0] = writerService.saveCategory(categories[0]);

		categories[1] = new Category();
		categories[1].setDescription("Important");
		categories[1].setLabel("writer.important");
		categories[1] = writerService.saveCategory(categories[1]);
		return categories;
	}


	/**
	 * Create some dummy article topics
	 * @return
	 */
	private Topic[] createTopics(){
		Topic [] topics = new Topic[4];

		topics[0] = new Topic();
		topics[0].setLabel("writer.sport");
		topics[0].setDescription("Sport topic");
		topics[0] = writerService.saveTopic(topics[0]);

		topics[1] = new Topic();
		topics[1].setDescription("Arts topic");
		topics[1].setLabel("writer.arts");
		topics[1] = writerService.saveTopic(topics[1]);

		topics[2] = new Topic();
		topics[2].setDescription("Social topic");
		topics[2].setLabel("writer.social");
		topics[2] = writerService.saveTopic(topics[2]);

		topics[3] = new Topic();
		topics[3].setDescription("News topic");
		topics[3].setLabel("writer.news");
		topics[3] = writerService.saveTopic(topics[3]);
		return topics;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
