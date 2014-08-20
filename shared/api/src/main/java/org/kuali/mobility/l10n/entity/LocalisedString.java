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

package org.kuali.mobility.l10n.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * A class representing a string localised for a specific language
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 
 */
@NamedQueries({
	@NamedQuery(
			name="LocalisedString.getString",
			query="SELECT ls FROM LocalisedString ls WHERE code = :code AND locale = :locale")
	
})
@Entity
@Table(name="L10N_STRING")
public class LocalisedString implements Serializable{
    /**
     * Serialisation UID
     */
    private static final long serialVersionUID = -5767311057726925895L;

	/**
	 * Primary key ID for this localised String
	 */
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Id
	@Column(name="ID")
	private Long id;
	
	/**
	 * Code for this tring
	 */
	@Column(name="CODE", nullable=false)
	private String code;
	
	/**
	 * Locale code for this string
	 */
	@Column(name="LOCALE")
	private String locale;
	
	/**
	 * Content of this localised string
	 */
	@Column(name="CONTENT", nullable=false)
	private String content;
	
	/**
	 * Version for this localised string
	 */
	@Column(name="VERSION")
	@Version
	private Long version;

	/**
	 * Gets the id for this <code>LocalisedString</code>.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id for this <code>LocalisedString</code>.
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the code for this <code>LocalisedString</code>.
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code for this <code>LocalisedString</code>.
	 * @param code the code to setCODE
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the locale for this <code>LocalisedString</code>.
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Sets the locale for this <code>LocalisedString</code>.
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Gets the content for this <code>LocalisedString</code>.
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the content for this <code>LocalisedString</code>.
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gets the version for this <code>LocalisedString</code>.
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * Sets the version for this <code>LocalisedString</code>.
	 * @param version the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
}
