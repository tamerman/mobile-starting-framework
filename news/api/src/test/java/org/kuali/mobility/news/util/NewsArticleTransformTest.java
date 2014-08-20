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

package org.kuali.mobility.news.util;

import org.junit.Before;
import org.junit.Test;
import org.kuali.mobility.news.entity.NewsArticleImpl;

import static org.junit.Assert.assertTrue;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class NewsArticleTransformTest {

    private NewsArticleTransform transform;

    @Before
    public void setUpTest() {
        setTransform(new NewsArticleTransform());
    }

    @Test
    public void testTransformWithBadObject() {
        NewsArticleImpl article = getTransform().transform(new String("bob"));
        assertTrue("Article was not null and should have been.",article==null);
    }

    @Test
    public void testTransform() {
        NewsArticleImpl article = new NewsArticleImpl();
        article.setTitle("Test Article");
        article.setArticleId("ABCDEFG");
        article.setDescription("Test Description");
        article.setSourceId(Long.parseLong("50"));

        NewsArticleImpl article2 = getTransform().transform(article);
        assertTrue("Article is null and should not be.", article2 != null);
        assertTrue("Article does not match and should.", article.getArticleId()==article2.getArticleId() && article.getTitle()==article2.getTitle());
    }

    public NewsArticleTransform getTransform() {
        return transform;
    }

    public void setTransform(NewsArticleTransform transform) {
        this.transform = transform;
    }
}
