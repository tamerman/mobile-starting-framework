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

import org.kuali.mobility.news.entity.NewsSource;

import java.util.Comparator;

/**
 * An interface for a contract for interacting with the news entity objects.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class NewsServiceSort implements Comparator<NewsSource> {
    @Override
    public int compare(NewsSource a, NewsSource b) {
        int comp;
        if( null == a ) {
            comp = -42;
        } else if( null == b ) {
            comp = 42;
        } else {
            comp = a.getOrder() - b.getOrder();
            if( comp == 0 && a.getTitle() != null && b.getTitle() != null) {
                comp = a.getTitle().compareTo(b.getTitle());
            }
        }
        return comp;
    }
}
