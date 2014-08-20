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

package org.kuali.mobility.security.user.entity;

import javax.persistence.*;

@NamedQueries({
	@NamedQuery(
		name = "GroupMembership.loadMembershipByUserId",
		query = "select gm from GroupMembership gm where gm.id.userId = :userId"
	),
	@NamedQuery(
		name = "GroupMembership.loadMembershipByGroupId",
		query = "select gm from GroupMembership gm where gm.id.groupId = :groupId"
	),
	@NamedQuery(
		name = "GroupMembership.deleteMembershipByUserId",
		query = "delete from GroupMembership gm where gm.id.userId = :userId"
	),
	@NamedQuery(
		name = "GroupMembership.deleteMembershipByGroupId",
		query = "delete from GroupMembership gm where gm.id.groupId = :groupId"
	)
})
/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Entity(name="GroupMembership")
@Table(name="KME_GROUP_MEMBERSHIP_T")
public class GroupMembership {

	@EmbeddedId
	private GroupMembershipPK groupMembershipPK;

	public GroupMembership() {
		this.groupMembershipPK = new GroupMembershipPK();
	}

	public GroupMembership(Long groupId,Long userId) {
		this.groupMembershipPK = new GroupMembershipPK(groupId,userId);
	}

	public GroupMembershipPK getGroupMembershipPK() {
		return groupMembershipPK;
	}

	public void setGroupMembershipPK(GroupMembershipPK groupMembershipPK) {
		this.groupMembershipPK = groupMembershipPK;
	}

	public Long getGroupId() {
		return getGroupMembershipPK().groupId;
	}

	public void setGroupId(Long groupId) {
		getGroupMembershipPK().groupId = groupId;
	}

	public Long getUserId() {
		return getGroupMembershipPK().userId;
	}

	public void setUserId(Long userId) {
		getGroupMembershipPK().userId = userId;
	}
}
