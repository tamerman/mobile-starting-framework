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

package org.kuali.mobility.people.service.util;

import org.apache.commons.collections.Transformer;
import org.kuali.mobility.people.entity.Group;
import org.kuali.mobility.people.entity.GroupImpl;

/**
 * Transforms a group
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
public class GroupTransform implements Transformer {

	/*
	 * (non-Javadoc)
	 * @see org.apache.commons.collections.Transformer#transform(java.lang.Object)
	 */
	@Override
	public GroupImpl transform(Object obj) {
		GroupImpl group = null;

		if (obj != null) {
			if (obj instanceof GroupImpl) {
				group = (GroupImpl) obj;
			} else if (obj instanceof Group) {
				group = new GroupImpl();
				group.setDN(((Group) obj).getDN());
				group.setDescriptions(((Group) obj).getDescriptions());
				group.setDisplayName(((Group) obj).getDisplayName());
				group.setEmail(((Group) obj).getEmail());
				group.setTelephoneNumber(((Group) obj).getTelephoneNumber());
				group.setFacsimileTelephoneNumber(((Group) obj).getFacsimileTelephoneNumber());
				group.setMembers(((Group) obj).getMembers());
				group.setOwners(((Group) obj).getOwners());
				group.setSubGroups(((Group) obj).getSubGroups());
			}
		}
		return group;
	}
}
