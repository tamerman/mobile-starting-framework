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

package org.kuali.mobility.security.group.entity;

import org.kuali.mobility.security.group.api.Group;

import javax.persistence.*;

@NamedQueries({
		@NamedQuery(
				name = "Group.loadAllGroups",
				query = "select g from Group g order by g.name"
		),
		@NamedQuery(
				name = "Group.loadGroupById",
				query = "select g from Group g where g.id = :id"
		),
		@NamedQuery(
				name = "Group.loadGroupsByName",
				query = "select g from Group g where g.name = :groupName"
		)
})
/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@Entity(name = "Group")
@Table(name = "KME_GROUP_T")
public class GroupImpl implements Group {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	@Column(name = "GROUP_NM", unique = true)
	private String name;

	@Column(name = "GROUP_X")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof Group)) {
			return false;
		}
		return (this.getId()).equals(((Group) obj).getId());
	}

	public int hashCode() {
		return getId().hashCode();
	}
}
