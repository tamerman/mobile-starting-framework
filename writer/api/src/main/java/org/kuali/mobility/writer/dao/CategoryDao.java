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

package org.kuali.mobility.writer.dao;

import org.kuali.mobility.writer.entity.Category;

import java.util.List;

/**
 * Category Data Access Object
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.0.0
 */
public interface CategoryDao {

	/**
	 * Gets all categories
	 * @return
	 */
	public List<Category> getCategories();
	
	/**
	 * Gets a specific categories
	 * @param categoryId
	 * @return
	 */
	public Category getCategory(long categoryId);

    /**
     * Persists a category
     * @param category The category to persist.
     * @return The persisted instance of the category
     */
    public abstract Category saveCategory(Category category);
}
