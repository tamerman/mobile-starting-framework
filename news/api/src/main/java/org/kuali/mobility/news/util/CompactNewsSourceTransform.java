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

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.news.entity.NewsSource;
import org.kuali.mobility.news.entity.NewsSourceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class CompactNewsSourceTransform implements Transformer {

    private static final NewsServiceSort NEWS_SERVICE_SORT = new NewsServiceSort();

    @Override
    public NewsSourceImpl transform( Object obj ) {
        NewsSourceImpl proxy = null;
        if( obj instanceof NewsSource) {
            proxy = new NewsSourceImpl();
            proxy.setName(((NewsSource) obj).getName());
            proxy.setTitle(((NewsSource) obj).getTitle());
            proxy.setId(((NewsSource) obj).getId());
            proxy.setActive(((NewsSource) obj).isActive());
            proxy.setAuthor(((NewsSource) obj).getAuthor());
            proxy.setDescription(((NewsSource) obj).getDescription());
            proxy.setOrder(((NewsSource) obj).getOrder());
            proxy.setParentId(((NewsSource) obj).getParentId());
            proxy.setUrl(((NewsSource) obj).getUrl());
            proxy.setHasChildren(((NewsSource) obj).hasChildren());
            List<NewsSource> children = new ArrayList<NewsSource>();
            if( ((NewsSource) obj).getChildren() != null && !((NewsSource) obj).getChildren().isEmpty()) {
                CompactNewsSourceTransform transform = new CompactNewsSourceTransform();
                for( NewsSource s : ((NewsSource) obj).getChildren()) {
                    children.add( transform.transform(s));
                }
                Collections.sort(children,NEWS_SERVICE_SORT);
                proxy.setChildren(children);
            }
            if( children.size() > 0 ) {
                proxy.setHasChildren(true);
            }
        }
        return proxy;
    }
}
