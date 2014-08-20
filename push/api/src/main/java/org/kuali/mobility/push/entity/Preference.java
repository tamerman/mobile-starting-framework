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

/**
 * A class representing a Push Notification Opt-out Preference.
 * 
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
@XmlRootElement
public class Preference implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8566828971044365068L;

	/**
	 * ID for this <code>Preference</code> instance.
	 */
    private Long id;
	
	/**
	 * Username of the person 
	 */
    private String username;

	/**
	 * Enabled denotes whether the whether the user wants to receive Push from a given sender, only in table if false. (opt-out) If true, removed from table.  
	 */
    private boolean enabled;
    
	/**
	 * Id of the sender.  
	 */
    private Long pushSenderID;


	/**
	 * Creates a new instance of a <code>Preference</code>
	 */
    public Preference(){}
    
	/**
	 * Gets the ID for this <code>Preference</code>
	 * @return ID for this <code>Preference</code>
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id The ID
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * Gets the boolean for enabled for this <code>Preference</code>
	 * @return hidden for this <code>Preference</code>
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Gets the boolean for enabled for this <code>Preference</code>
	 * @return whether the sender for this <code>Preference</code> is blocked.
	 */
	public boolean isSenderBlocked() {
		return !enabled;
	}
	
	/**
	 * Sets the hidden
	 * @param enabled The Hidden
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Gets the pushSenderId for this <code>Preference</code>
	 * @return pushSenderId for this <code>Preference</code>
	 */
	public Long getPushSenderID() {
		return pushSenderID;
	}

	/**
	 * Sets the PushSenderId
	 * @param pushSenderID The PushSenderID
	 */
	public void setPushSenderID(Long pushSenderID) {
		this.pushSenderID = pushSenderID;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return    "\nPreference   id = " + id 
				+ "\nusername        = " + username  
				+ "\nenabled         = " + enabled
				+ "\npushSenderID    = " + pushSenderID + "\n";
	}

    
    
}
