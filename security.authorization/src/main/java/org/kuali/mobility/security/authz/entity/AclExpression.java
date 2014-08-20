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

package org.kuali.mobility.security.authz.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kuali.mobility.security.authz.expression.Expression;
import org.kuali.mobility.security.authz.util.XstreamUtility;

/**
 * A class representing a ACL Expression
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since
 */
@Entity(name = "AclExpression")
@Table(name = "KME_ACL_EXP_T")
@XmlRootElement(name="aclExpression")
public class AclExpression implements Serializable {

	private static final long serialVersionUID = -6549847275619160154L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "ID")
	private Long id;

    @Column(name = "HM_SCRN_ID")
    private Long homeScreenId;

    @Column(name = "TOOL_ID")
	private Long toolId;

	@Column(name = "PRMSSN_MODE")
	private String permissionMode;

	@Column(name = "PRMSSN_TYP")
	private String permissionType;

	@Column(name = "ACL_EXP")
	private String expression;

    @Version
    @Column(name="VER_NBR")
    protected Long versionNumber;

	@Transient
	@XmlTransient
	Expression parsedExpression;

	public AclExpression copy(boolean includeIds) {
		AclExpression copy = new AclExpression();

		if (includeIds) {
			if (id != null) {
				copy.setId(new Long(id));
			}
			if (versionNumber != null) {
				copy.setVersionNumber(new Long(versionNumber));
			}
		}
        if (homeScreenId != null) {
            copy.setHomeScreenId(new Long(homeScreenId));
        }
		if (toolId != null) {
			copy.setToolId(new Long(toolId));
		}
		if (permissionMode != null) {
			copy.setPermissionMode(new String(permissionMode));
		}
		if (permissionType != null) {
			copy.setPermissionType(new String(permissionType));
		}
		if (expression != null) {
			copy.setExpression(new String(expression));
		}
		return copy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getHomeScreenId() {
        return homeScreenId;
    }

    public void setHomeScreenId(Long homeScreenId) {
        this.homeScreenId = homeScreenId;
    }

    public Long getToolId() {
		return toolId;
	}

	public void setToolId(Long toolId) {
		this.toolId = toolId;
	}

	public String getPermissionMode() {
		return permissionMode;
	}

	public void setPermissionMode(String permissionMode) {
		this.permissionMode = permissionMode;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

    public Long getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }

	@XmlTransient
	public Expression getParsedExpression() {
		if (parsedExpression != null) {
		    return parsedExpression;
		}

		if (expression != null) {
		    parsedExpression = XstreamUtility.xmlToExpression(expression);
		}

		return parsedExpression;
	}

	public void setParsedExpression(Expression parsedExpression) {
		this.parsedExpression = parsedExpression;
	}

}
