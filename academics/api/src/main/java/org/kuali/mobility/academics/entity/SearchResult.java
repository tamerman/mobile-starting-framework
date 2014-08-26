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

package org.kuali.mobility.academics.entity;

import org.kuali.mobility.shared.entity.ToolMessage;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@XmlRootElement(name = "searchResult")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResult {
	private List<ToolMessage> messages;
	@XmlElement(name = "section")
	private List<Section> sections;

	public SearchResult() {
		messages = new ArrayList<ToolMessage>();
		sections = new ArrayList<Section>();
	}

	public List<Section> getSections() {
		return this.sections;
	}

	public List<ToolMessage> getMessages() {
		return this.messages;
	}

	public void setMessages(List<ToolMessage> msgs) {
		this.messages = msgs;
	}

	public void addMessage(ToolMessage msg) {
		this.messages.add(msg);
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
}
