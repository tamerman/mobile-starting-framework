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

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * The backing class for the MultiPage JSP tag. Renders everything necessary for the
 * page excluding the actual content.
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public class Constants {

	public static final String KME_USER_KEY = "kme.user";
	public static final String KME_BACKDOOR_USER_KEY = "kme.backdoor.user";
	public static final String KME_MOCK_USER_KEY = "kme.mock.user";

	public static final String URL_MIME_TYPE = "text/url";

	public static final String SAKAI_FOLDER_EXTENSION = "fldr";
	public static final String SAKAI_URL_EXTENSION = "url";

	/**
	 * @deprecated THIS IS VERY BAD! DO NOT USE THIS LIST OF HARD CODED CAMPUS NAMES!
	 */
	@Deprecated
	public static final Map<String, String> CAMPUS_NAMES = new LinkedHashMap<String, String>();

	static {
		CAMPUS_NAMES.put("Any", "Any Campus");
		CAMPUS_NAMES.put("BL", "Blue Campus");
		CAMPUS_NAMES.put("IN", "Indigo Campus");
		CAMPUS_NAMES.put("CO", "Coffee Campus");
	}

	/**
	 * @deprecated THIS IS VERY BAD! Use localisation for date formats
	 */
	@Deprecated
	public enum DateFormat {
		queryStringDateFormat("yyyyMMdd"),
		displayDateFormat("MMMM dd, yyyy"),
		buttonDateFormat("MMM dd");

		private String format;

		@Deprecated
		DateFormat(String format) {
			this.format = format;
		}

		@Deprecated
		public SimpleDateFormat getFormat() {
			return new SimpleDateFormat(format);
		}
	}

	public enum FileType {
		GENERIC, IMAGE, VIDEO, TEXT, PRESENTATION, SPREADSHEET, PDF, AUDIO, LINK, FOLDER, COMPRESSED;
	}

	public enum FileTypes {
		txt(FileType.TEXT),
		rtf(FileType.TEXT),
		doc(FileType.TEXT),
		docx(FileType.TEXT),
		odt(FileType.TEXT),
		wpd(FileType.TEXT),
		jpg(FileType.IMAGE),
		jpeg(FileType.IMAGE),
		png(FileType.IMAGE),
		gif(FileType.IMAGE),
		bmp(FileType.IMAGE),
		psd(FileType.IMAGE),
		tiff(FileType.IMAGE),
		wav(FileType.AUDIO),
		wma(FileType.AUDIO),
		mpa(FileType.AUDIO),
		mp3(FileType.AUDIO),
		mid(FileType.AUDIO),
		midi(FileType.AUDIO),
		m4a(FileType.AUDIO),
		m3u(FileType.AUDIO),
		aif(FileType.AUDIO),
		avi(FileType.VIDEO),
		flv(FileType.VIDEO),
		mov(FileType.VIDEO),
		mp4(FileType.VIDEO),
		mpg(FileType.VIDEO),
		swf(FileType.VIDEO),
		vob(FileType.VIDEO),
		wmv(FileType.VIDEO),
		wks(FileType.SPREADSHEET),
		xls(FileType.SPREADSHEET),
		xlsx(FileType.SPREADSHEET),
		ods(FileType.SPREADSHEET),
		ppt(FileType.PRESENTATION),
		pptx(FileType.PRESENTATION),
		odp(FileType.PRESENTATION),
		pdf(FileType.PDF),
		zip(FileType.COMPRESSED),
		rar(FileType.COMPRESSED),
		gz(FileType.COMPRESSED),
		fldr(FileType.FOLDER),
		url(FileType.LINK);

		private FileType fileType;

		FileTypes(FileType fileType) {
			this.fileType = fileType;
		}

		public FileType getFileType() {
			return fileType;
		}
	}

}
