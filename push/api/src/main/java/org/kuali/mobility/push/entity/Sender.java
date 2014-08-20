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

package org.kuali.mobility.push.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * A class representing a sender of a push notification.
 * To be able to send a push notification, a sender must be registered.
 * A sender represents a tool or external service that can send push notification through KME.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
@XmlRootElement
public class Sender implements Serializable {

	private static final long serialVersionUID = 1594899851834068598L;
	
	/**
	 * ID for this <code>Sender</code> instance.
	 */
    private Long id;
	
	/**
	 * Name of the sender. Normal text.  
	 */
    private String name;
    
	/**
	 * Hidden denotes whether the sender is visible in the user's Opt-in/Opt-out preferences page.  
	 */
    private boolean hidden;
    
	/**
	 * shortname of the sender. No whitespace. 
	 */
    private String shortName;

	/**
	 * Description of the sender.  
	 */
    private String description;

	/**
	 * Username of the person 
	 */
    private String username;

	/**
	 * Sender Key is used for verification of a given sender it is required when submitting a http POST.
	 * Typically a 20 character alphanumeric. 
	 */
    private String senderKey;
    
	/**
	 * The timestamp of the last update for this <code>Device</code> details.
	 */
    private Timestamp postedTimestamp;
       
	/**
	 * Creates a new instance of a <code>Sender</code>
	 */
    public Sender(){}

	/**
	 * Gets the ID for this <code>Sender</code>
	 * @return ID for this <code>Sender</code>
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id The URL
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the Name for this <code>Sender</code>
	 * @return Name for this <code>Sender</code>
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Name
	 * @param name The Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the boolean for hidden for this <code>Sender</code>
	 * @return hidden for this <code>Sender</code>
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * Sets the hidden
	 * @param hidden The Hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * Gets the shortName for this <code>Sender</code>
	 * @return shortName for this <code>Sender</code>
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Sets the ShortName
	 * @param shortName The ShortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Gets the description for this <code>Sender</code>
	 * @return description for this <code>Sender</code>
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the Description
	 * @param description The Description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the username for this <code>Sender</code>
	 * @return username for this <code>Sender</code>
	 */	
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the Username
	 * @param username The Username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the senderKey for this <code>Sender</code>
	 * @return senderKey for this <code>Sender</code>
	 */
	public String getSenderKey() {
		return senderKey;
	}

	/**
	 * Sets the SenderKey
	 * @param senderKey The SenderKey
	 */
	public void setSenderKey(String senderKey) {
		this.senderKey = senderKey;
	}

	/**
	 * Gets the postedTimestamp for this <code>Sender</code>
	 * @return postedTimestamp for this <code>Sender</code>
	 */
	public Timestamp getPostedTimestamp() {
		return postedTimestamp;
	}

	/**
	 * Sets the PostedTimestamp
	 * @param postedTimestamp The PostedTimestamp
	 */
	public void setPostedTimestamp(Timestamp postedTimestamp) {
		this.postedTimestamp = postedTimestamp;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\nSender Object:" + 
				"\nid=" + id + 
				"\nname=" + name + 
				"\nshortName=" + shortName + 
				"\nhidden=" + (hidden?"true":"false") + 
				"\ndescription=" + description + 
				"\nusername=" + username + 
				"\nsenderKey=" + senderKey + 
				"\npostedTimestamp="+ postedTimestamp + "\n";
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toJson() {
		return "{" + 
				"\"id\":\"" + id + "\"," + 
				"\"name\":\"" + name +  "\"," +
				"\"shortName\":\"" + shortName +  "\"," + 
				"\"hidden\":\"" + (hidden?"true":"false") +  "\"," + 
				"\"description\":\"" + description +  "\"," +
				"\"username\":\"" + username +  "\"," +
				"\"senderKey\":\"" + senderKey +  "\"," +
				"\"postedTimestamp\":\""+ postedTimestamp +  "\"}" ;
	}
	
	
    
}
