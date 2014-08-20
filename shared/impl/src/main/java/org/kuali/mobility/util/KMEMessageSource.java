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

package org.kuali.mobility.util;

import java.text.MessageFormat;
import java.util.Locale;

import org.kuali.mobility.l10n.entity.LocalisedString;
import org.kuali.mobility.l10n.service.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.AbstractMessageSource;

/**
 * A message source used by KME.
 * This message source can be configured to read string from the database
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 
 */
public class KMEMessageSource extends AbstractMessageSource{

	/**
	 * A reference to the <code>LocalisationService</code>
	 */
	@Autowired
	@Qualifier("localisationService")
	private LocalisationService localisationService;
	
	/**
	 * Initialised this message source
	 */
	public void initialise(){
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.context.support.AbstractMessageSource#resolveCode(java.lang.String, java.util.Locale)
	 */
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		LocalisedString ls = localisationService.getLocalisedString(code, locale.getLanguage());
		if (ls != null){
			return createMessageFormat(ls.getContent(), locale);
		}
		return null;
	}

}
