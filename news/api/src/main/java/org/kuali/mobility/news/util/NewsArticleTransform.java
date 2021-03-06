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

package org.kuali.mobility.news.util;

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.news.entity.NewsArticle;
import org.kuali.mobility.news.entity.NewsArticleImpl;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class NewsArticleTransform implements Transformer {

	@Override
	public NewsArticleImpl transform(Object obj) {
		NewsArticleImpl proxy = null;

		if (obj != null) {
			if (obj instanceof NewsArticleImpl) {
				proxy = (NewsArticleImpl) obj;
			} else if (obj instanceof NewsArticle) {
				proxy = new NewsArticleImpl();
				proxy.setArticleId(((NewsArticle) obj).getArticleId());
				proxy.setDescription(((NewsArticle) obj).getDescription());
				proxy.setLink(((NewsArticle) obj).getLink());
				if (((NewsArticle) obj).getPublishDate() != null) {
					proxy.setPublishDate(((NewsArticle) obj).getPublishDate());
				}
				proxy.setPublishDateDisplay(((NewsArticle) obj).getPublishDateDisplay());
				proxy.setSourceId(((NewsArticle) obj).getSourceId());
				proxy.setTitle(((NewsArticle) obj).getTitle());
			}
		}
		return proxy;
	}
}
