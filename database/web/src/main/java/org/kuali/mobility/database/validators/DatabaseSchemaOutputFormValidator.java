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

package org.kuali.mobility.database.validators;

import org.kuali.mobility.database.entity.DatabaseSchemaOutputForm;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DatabaseSchemaOutputFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> parameter) {
		return DatabaseSchemaOutputForm.class.equals(parameter);
	}

	@Override
	public void validate(Object object, Errors errors) {
		DatabaseSchemaOutputForm form = (DatabaseSchemaOutputForm) object;
//		if (form.getDelimiter() == null || "".equals(form.getDelimiter().trim())) {
//			errors.rejectValue("delimiter", "DELIMITER.REQUIRED", "A delimiter is required.");
//		}
		if (form.getDialectType() == null || "".equals(form.getDialectType().trim())) {
			errors.rejectValue("dialectType", "DIALECT.TYPE.REQUIRED", "Selecting a database type is required.");
		}
	}

}
