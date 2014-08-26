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

package org.kuali.mobility.shared;

import java.util.ArrayList;
import java.util.List;

public class ToolFromXML {


	// Constants used for the Requisites field. 
	// !!! DON'T CHANGE VALUES!!! Matches values in Tool.java.
	public static final int NATIVE = 1;
	public static final int IOS = 2;
	public static final int ANDROID = 4;
	public static final int WINDOWS_PHONE = 8;
	public static final int BLACKBERRY = 16;
	public static final int NON_NATIVE = 32;

	private String title;
	private String subtitle;
	private String contacts;
	private String keywords;
	private String alias;
	private String url;
	private String description;
	private String iconUrl;
	private boolean isNative;
	private boolean isiOS;
	private boolean isAndroid;
	private boolean isWindows;
	private boolean isBlackberry;
	private boolean isNonNative;

	private List<String> publishingACLS = new ArrayList();
	private List<String> viewingACLS = new ArrayList();


	public List<String> getPublishingACLS() {
		return publishingACLS;
	}

	public void setPublishingACLS(List<String> publishingACLS) {
		this.publishingACLS = publishingACLS;
	}

	public List<String> getViewingACLS() {
		return viewingACLS;
	}

	public void setViewingACLS(List<String> viewingACLS) {
		this.viewingACLS = viewingACLS;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public boolean isNative() {
		return isNative;
	}

	public void setNative(boolean isNative) {
		this.isNative = isNative;
	}

	public boolean isiOS() {
		return isiOS;
	}

	public void setIsiOS(boolean isiOS) {
		this.isiOS = isiOS;
	}

	public boolean isAndroid() {
		return isAndroid;
	}

	public void setAndroid(boolean isAndroid) {
		this.isAndroid = isAndroid;
	}

	public boolean isWindows() {
		return isWindows;
	}

	public void setWindows(boolean isWindows) {
		this.isWindows = isWindows;
	}

	public boolean isBlackberry() {
		return isBlackberry;
	}

	public void setBlackberry(boolean isBlackberry) {
		this.isBlackberry = isBlackberry;
	}

	public boolean isNonNative() {
		return isNonNative;
	}

	public void setNonNative(boolean isNonNative) {
		this.isNonNative = isNonNative;
	}

	public int getRequisites() {
		int reqs = 0;
		if (this.isNative()) reqs |= NATIVE;
		if (this.isiOS()) reqs |= IOS;
		if (this.isAndroid()) reqs |= ANDROID;
		if (this.isWindows()) reqs |= WINDOWS_PHONE;
		if (this.isBlackberry()) reqs |= BLACKBERRY;
		if (this.isNonNative()) reqs |= NON_NATIVE;
		return reqs;
	}

	@Override
	public String toString() {
		return "\nToolFromXML \n[title=" + title + ", \nsubtitle=" + subtitle
				+ ", \ncontacts=" + contacts + ", \nkeywords=" + keywords
				+ ", \nalias=" + alias + ", \nurl=" + url + ", \ndescription="
				+ description + ", \niconUrl=" + iconUrl + ", \nisNative="
				+ isNative + ", \nisiOS=" + isiOS + ", \nisAndroid=" + isAndroid
				+ ", \nisWindows=" + isWindows + ", \nisBlackberry=" + isBlackberry
				+ ", \nisNonNative=" + isNonNative
				+ super.toString() + "]";
	}

}
