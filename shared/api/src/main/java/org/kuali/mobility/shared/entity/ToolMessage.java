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

package org.kuali.mobility.shared.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@XmlRootElement(name="message")
public class ToolMessage {
    private ToolMessageType type;
    private String label;
    private String description;
    
    public String getLabel(){
        return label;
    }
    
    public void setLabel(String l) {
        label = l;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String d) {
        description = d;
    }
    
    public int getTypeCode() {
        if (null!=getType())
            return getType().getCode();
        else {
            return -1;
        }
    }
    
    public void setType(ToolMessageType t) {
        type = t;
    }

    /**
     * @return the type
     */
    public ToolMessageType getType() {
        return type;
    }
    
}
