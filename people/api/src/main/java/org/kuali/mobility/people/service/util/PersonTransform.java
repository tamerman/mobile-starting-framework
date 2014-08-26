/**
 * The MIT License
 * Copyright (c) 2011 Kuali Mobility Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.kuali.mobility.people.service.util;

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.people.entity.Person;
import org.kuali.mobility.people.entity.PersonImpl;

/**
 * Transforms a person
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class PersonTransform implements Transformer {

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.collections.Transformer#transform(java.lang.Object)
	 */
	@Override
	public PersonImpl transform(Object obj) {
		PersonImpl person = null;

		if (obj != null) {
			if (obj instanceof PersonImpl) {
				person = (PersonImpl) obj;
			} else if (obj instanceof Person) {
				person = new PersonImpl();
				person.setAddress(((Person) obj).getAddress());
				person.setAffiliations(((Person) obj).getAffiliations());
				person.setDepartments(((Person) obj).getDepartments());
				person.setDisplayName(((Person) obj).getDisplayName());
				person.setEmail(((Person) obj).getEmail());
				person.setFirstName(((Person) obj).getFirstName());
				person.setLastName(((Person) obj).getLastName());
				person.setLocations(((Person) obj).getLocations());
				person.setPhone(((Person) obj).getPhone());
				person.setUserName(((Person) obj).getUserName());
			}
		}
		return person;
	}
}
