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

package org.kuali.mobility.admin.util;

import java.util.Properties;

import org.kuali.mobility.admin.entity.HomeScreen;

/**
 * Utility for checking valid layouts.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.3.0
 */
public class LayoutUtil {

	public static final String PROP_LAYOUT_DEFAULT = "home.layout.default";
	
	public static final String PROP_LAYOUT_EDITABLE = "home.layout.userEditable";
	
	/**
	 * Returns a valid layout.  The returned layout may be the requested layout if it is valid, else
	 * it would return the default layout
	 * @param requestedLayout The layout requested
	 * @param kmeProperties A reference to the KME Properties
	 * @return A valid layout that can be used.
	 */
	public static final String getValidLayout(String requestedLayout, Properties kmeProperties){
		String defaultLayout = getDefaultLayout(kmeProperties);
		return isLayoutAllowed(requestedLayout, kmeProperties) ? requestedLayout : defaultLayout;
	}
	
	/**
	 * Returns tre if the layout is an allowed layout
	 * @param layout Layout to check if it is allowed
	 * @param kmeProperties A reference to the KME Properties
	 * @return True if the layout is allowed, else false.
	 */
	public static final boolean isLayoutAllowed(String layout, Properties kmeProperties){
		// If the layout name is not one of the defined layouts, it is invalid
		if(!isValidLayout(layout)){
			return false;
		}
		
		 // If layout change is allowed, or if the layout is the default layout
		if(isLayoutChangeAllowed(kmeProperties) || getDefaultLayout(kmeProperties).equals(layout)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns true if users are allowed to change their layout
	 * @param kmeProperties
	 * @return
	 */
	public static final boolean isLayoutChangeAllowed(Properties kmeProperties){
		if(kmeProperties == null){
			return false;
		}
		return Boolean.parseBoolean(kmeProperties.getProperty(PROP_LAYOUT_EDITABLE, "false"));
	}
	
	/**
	 * Gets the default layout
	 * @param kmeProperties
	 * @return
	 */
	public static final String getDefaultLayout(Properties kmeProperties){
		if (kmeProperties == null){
			return HomeScreen.LAYOUT_LIST;
		}
		return kmeProperties.getProperty(PROP_LAYOUT_DEFAULT, HomeScreen.LAYOUT_LIST);
	}
	

	/**
	 * Returns true uf the specified layout is a valid layout
	 * @param layout Layout name to check
	 * @return True if the layout is valid.
	 */
	public static final boolean isValidLayout(String layout){
		if(layout == null){
			return false;
		}
		for(String l : HomeScreen.LAYOUTS){
			if(l.equals(layout)){
				return true;
			}
		}
		return false;
	}
}
