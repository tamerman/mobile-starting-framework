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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.kuali.mobility.news.entity.NewsArticleImpl;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.entity.NewsSourceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class NewsSourceTransform implements Transformer {
    private static final NewsServiceSort NEWS_SERVICE_SORT = new NewsServiceSort();

    @Override
    public NewsSourceImpl transform(Object obj) {
        NewsSourceImpl proxy = null;

        if (obj != null) {
            if (obj instanceof NewsSourceImpl) {
                proxy = (NewsSourceImpl) obj;
                if( proxy.getChildren() != null && proxy.getChildren().size() > 0 ) {
                    proxy.setHasChildren(true);
                }
            } else if (obj instanceof NewsSource) {
                proxy = new NewsSourceImpl();
                proxy.setActive(((NewsSource) obj).isActive());
                proxy.setAuthor(((NewsSource) obj).getAuthor());
                proxy.setDescription(((NewsSource) obj).getDescription());
                proxy.setId(((NewsSource) obj).getId());
                proxy.setName(((NewsSource) obj).getName());
                proxy.setOrder(((NewsSource) obj).getOrder());
                proxy.setParentId(((NewsSource) obj).getParentId());
                proxy.setTitle(((NewsSource) obj).getTitle());
                proxy.setUrl(((NewsSource) obj).getUrl());
                proxy.setHasChildren(((NewsSource) obj).hasChildren());

                List<NewsSourceImpl> children = new ArrayList<NewsSourceImpl>();
                CollectionUtils.collect(((NewsSource) obj).getChildren(), new NewsSourceTransform(), children);
                Collections.sort(children,NEWS_SERVICE_SORT);
                proxy.setChildren(children);
                if( children.size() > 0 ) {
                    proxy.setHasChildren(true);
                }

                List<NewsArticleImpl> articles = new ArrayList<NewsArticleImpl>();
                CollectionUtils.collect(((NewsSource) obj).getArticles(), new NewsArticleTransform(), articles);
                proxy.setArticles(articles);

            }
        }

        return proxy;
    }
}
