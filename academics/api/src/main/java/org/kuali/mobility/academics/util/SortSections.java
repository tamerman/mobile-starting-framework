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

package org.kuali.mobility.academics.util;

import java.util.Comparator;
import org.kuali.mobility.academics.entity.Section;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class SortSections implements Comparator<Section> {

    @Override
    public int compare(Section s1, Section s2) {
        int comp;
        comp = s1.getSubjectId().compareTo(s2.getSubjectId());
        
        if (comp==0) {
            comp = s1.getCatalogNumber().compareTo(s2.getCatalogNumber());
            
            if (comp==0) {
                comp = s1.getNumber().compareTo(s2.getNumber());
            }
        }
        
        return comp;
    }
    
}
