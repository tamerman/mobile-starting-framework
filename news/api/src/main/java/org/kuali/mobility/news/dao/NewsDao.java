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

package org.kuali.mobility.news.dao;

import java.util.List;

import org.kuali.mobility.news.entity.NewsSource;

/**
 * An interface for persisting and retrieving NewsSource objects from a data store.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public interface NewsDao {
	
	public List<? extends NewsSource> findNewsSources( Long parentId, Boolean isActive );
	
	/**
	 * @return a list of active NewsSource objects
	 */
	public List<? extends NewsSource> findAllActiveNewsSources();
	public List<? extends NewsSource> findAllActiveNewsSources( Long parentId );
	
	/**
	 * @return a list of all NewsSource objects
	 */
	public List<? extends NewsSource> findAllNewsSources();
	public List<? extends NewsSource> findAllNewsSources( Long parentId );
	
	/**
	 * @return a single NewsSource object matched by its id
	 */
	public NewsSource lookup(Long id);
	
	/**
	 * saves a NewsSource object to the data store
	 * 
	 * @return the saved NewsSource
	 */
	public NewsSource save(NewsSource newsSource);
	
	/**
	 * deletes a NewsSource object from the data store
	 * 
	 * @return the deleted NewsSource
	 */
	public NewsSource delete(NewsSource newsSource);
}
