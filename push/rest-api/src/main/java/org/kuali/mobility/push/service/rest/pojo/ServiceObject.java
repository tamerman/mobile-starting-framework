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

package org.kuali.mobility.push.service.rest.pojo;

import java.io.Serializable;

/**
 * The base service object.
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class ServiceObject implements Serializable {

	/**
	 * The status of the call.
	 */
	private int status = 0;
	/**
	 * The message for the call.
	 * Note that this could be null if status is successful
	 */
	private String message = "";
	/**
	 * The errorCode, if a error occured.
	 */
	private int errorCode = 0;
	/**
	 * A instruction on what to do when a error occured.
	 */
	private String instruction = "";

	/**
	 * Default Constructor
	 */
	public ServiceObject() {
	}


	/**
	 * Copy constructor
	 */
	public ServiceObject(ServiceObject other) {
		this.status = other.status;
		this.message = other.message;
		this.instruction = other.instruction;
		this.errorCode = other.errorCode;
	}

	/**
	 * The status of the call.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * The status of the call.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Whether the status of the call was successful.
	 */
	public boolean isSuccessful() {
		return status == 0;
	}

	/**
	 * The message for the call.
	 * Note that this could be null if status is successful
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * The message for the call.
	 * Note that this could be null if status is successful
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Add a message to the existing message.
	 * <p/>
	 * If there is no existing message this message is set as the
	 * object message. If there is a existing message this message
	 * is added to the end of the existing message.
	 *
	 * @param message New message to add.
	 */
	public void addMessage(String message) {
		if (this.message != null) {
			setMessage(getMessage()
					+ "\n"
					+ message);
		} else {
			setMessage(message);
		}
	}

	/**
	 * The errorCode, if a error occured.
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * The errorCode, if a error occured.
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * A instruction on what to do when a error occured.
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * A instruction on what to do when a error occured.
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
}
