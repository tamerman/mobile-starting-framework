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
 * A class representing a Push Notification message.
 * A single push notification instance is shared for many users and devices.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 * @since 2.0.0
 */
@XmlRootElement
public class Push implements Serializable {

	private static final long serialVersionUID = -9158722924017383328L;

	/**
	 * Instance id for this <code>Push</code> instance
	 */
	private Long pushId;

	/**
	 * Title for this <code>Push</code> instance.
	 */
	private String title;

	/**
	 * Message for this <code>Push</code> instance.
	 */
	private String message;


	/**
	 * Sender for this <code>Push</code> instance.
	 */
	private String sender;

	/**
	 * Number of recipients for this <code>Push</code> instance.
	 */
	private int recipients;

	/**
	 * URL for this <code>Push</code> instance.
	 */
	private String url;

	/**
	 * Flag if this <code>Push</code> is an emergency message.
	 */
	private boolean emergency;


	/**
	 * Creates a new instance of a <code>Push</code>
	 */
	public Push() {
	}


	/**
	 * Returns true if it is an emergency.
	 * @return true if it is an emergency.
	 */
	public boolean getEmergency() {
		return emergency;
	}

	/**
	 * Sets if it is an emergency
	 * @param emergency Flag if it is an emergency
	 */
	public void setEmergency(boolean emergency) {
		this.emergency = emergency;
	}

	/**
	 * Returns the ID.
	 * @return The ID
	 */
	public Long getPushId() {
		return pushId;
	}

	/**
	 * Sets the ID.
	 * @param pushId The ID
	 */
	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}

	/**
	 * Gets the number of recipients
	 * @return number of recipients
	 */
	public int getRecipients() {
		return recipients;
	}

	/**
	 * Sets the number of recipients
	 * @param recipients number of recipients
	 */
	public void setRecipients(int recipients) {
		this.recipients = recipients;
	}

	/**
	 * Gets the sender
	 * @return The sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * Sets the sender
	 * @param sender The sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}    

	/**
	 * Gets the URL
	 * @return The URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the URL
	 * @param url The URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the title.
	 * @return The title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 * @param title The title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the message.
	 * @return The message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 * @param message The message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String newline = "\r\n";
		String str = newline +"PushID:     " + this.getPushId();
		str = str + newline + "Title:      " + this.getTitle();
		str = str + newline + "Message:    " + this.getMessage(); 
		str = str + newline + "Emergency:  " + this.getEmergency(); 
		str = str + newline + "URL:        " + this.getUrl();  
		str = str + newline + "Sender:     " + this.getSender();  
		str = str + newline + "Recipients: " + this.getRecipients();
		return str;
	}

	public String toJson() {

		String str = "{";
		str += "\"id\":\"" 				+ this.getPushId() + "\",";
		str += "\"title\":\"" 			+ this.getTitle() + "\",";
		str += "\"message\":\"" 		+ this.getMessage() + "\","; 
		str += "\"emergency\":\"" 		+ this.getEmergency() + "\","; 
		str += "\"url\":\"" 			+ this.getUrl() + "\",";  
		str += "\"sender\":\"" 			+ this.getSender() + "\",";  
		str += "\"recipients\":\"" 		+ this.getRecipients() + "}";
		return str;
	}
	
}
