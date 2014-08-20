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

package org.kuali.mobility.events.util;

import org.apache.commons.collections.Predicate;
import org.kuali.mobility.events.entity.Category;

public final class CategoryPredicate implements Predicate {

    private String campus;
    private String category;
    
    public CategoryPredicate( final String campus, final String category )
    {
        this.setCampus( campus );
        this.setCategory( category );
    }
    
    @Override
    public boolean evaluate(Object obj) {
        boolean campusMatch = false;
        boolean categoryMatch = false;
        
        if( obj instanceof Category )
        {
            if( getCampus() == null )
            {
                campusMatch = true;
            }
            if( getCampus() != null && getCampus().equalsIgnoreCase( ((Category)obj).getCampus() ))
            {
                campusMatch = true;
            }
            if( getCategory() == null )
            {
                categoryMatch = true;
            }
            if( getCategory() != null && getCategory().equalsIgnoreCase( ((Category)obj).getCategoryId() ) )
            {
                categoryMatch = true;
            }
        }
        
        return campusMatch && categoryMatch;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }
    
}
