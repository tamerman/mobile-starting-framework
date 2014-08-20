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

package org.kuali.mobility.bus.dao.helper;

import java.io.IOException;

import org.springframework.core.io.Resource;

/**
 * Helper class to get a unit test compatible URL string from the classpath
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.3.0
 */
public class URLHelper {

	/**
	 * A reference to a springyfied resource
	 */
	private Resource resource;
	
	/**
	 * Sets the resource
	 * @param resource
	 */
	public void setResource(Resource resource){
		this.resource = resource;
	}
	
	/**
	 * Get a string representing the url to the resource
	 * @return
	 */
	public String getUrlPath(){
		try {
			return resource.getURL().toString();
		} catch (IOException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
}
