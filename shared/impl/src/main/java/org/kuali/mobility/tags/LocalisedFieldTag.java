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

package org.kuali.mobility.tags;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.LocaleResolver;

/**
 * A tag that creates an input field that supports multiple languages
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 2.4.0
 */
public class LocalisedFieldTag extends SimpleTagSupport {

	private static final String INPUT_TEXT = "text";
	private static final String INPUT_TEXT_AREA = "textarea";
	private static final String DEFAULT_LANGUAGE = "en";
	/**
	 * Name of the field for input
	 */
	private String name;

	/**
	 * Key for the localised string being used.
	 */
	private String code;

	/**
	 * Code for the label to use.
	 */
	private String labelCode;

	/**
	 * Input type required.
	 * Valid values are:
	 * <ul>
	 * <li>text</li>
	 * <li>textarea</li>
	 * </ul>
	 */
	private String inputType = INPUT_TEXT;

	/**
	 * A reference to the message source
	 */
	private MessageSource messageSource;

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
	 */
	@Override
	public void doTag() throws JspException, IOException {
		PageContext pageContext = (PageContext) getJspContext();
		ServletContext servletContext = pageContext.getServletContext();
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(servletContext);

		final List<String> languages = (List) ac.getBean("supportedLanguages");
		this.messageSource = (MessageSource) ac.getBean("messageSource");
		final LocaleResolver localeResolver = (LocaleResolver) ac.getBean("localeResolver");
		final Locale currentLocale = localeResolver.resolveLocale((HttpServletRequest) pageContext.getRequest());
		final JspWriter out = pageContext.getOut();

		final String label = getMessage(labelCode, currentLocale);

		out.println("<div data-role=\"l10nBox\" data-l10n-field=\"" + name + "\">");
		out.println("<label>" + label + ": </label>");
		this.writeHiddenInputCode(out);
		this.writeLanguageOptions(out, languages);
		this.writeLanguageInputs(out, languages);
		out.println("</div>");
		super.doTag();
	}

	/**
	 * Writes the hidden input to give a name for the input localised code.
	 *
	 * @param out
	 * @throws IOException
	 */
	private void writeHiddenInputCode(JspWriter out) throws IOException {
		out.print("<input type=\"hidden\" ");
		out.print("name=\"" + name + ".code\" ");
		if (code != null) {
			out.print("value=\"" + code + "\" ");
		} else {
			out.print("value=\"\" ");
		}
		out.println(" />");

	}

	/**
	 * Writes the buttons with supported languages
	 *
	 * @param languages
	 * @throws IOException
	 */
	private void writeLanguageOptions(JspWriter out, List<String> languages) throws IOException {
		for (String language : languages) {
			// <a data-l10n-lang="EN" href="javascript:;" >EN</a> 
			out.print("<a data-l10n-lang=\"");
			out.print(language);
			out.print("\" href=\"javascript:;\"");

			// If it is the active language, add the active style
			if (getDefaultLanguage().equals(language)) {
				out.print(" class=\"l10n-active\"");
			}

			out.print(">");
			out.print(language.toUpperCase());
			out.println("</a>");
		}
	}

	/**
	 * Writes the inputs for all languages
	 *
	 * @param out
	 * @param languages
	 * @throws IOException
	 */
	private void writeLanguageInputs(JspWriter out, List<String> languages) throws IOException {
		for (String language : languages) {
			writeInputForLanguage(out, language);
		}
	}

	/**
	 * Writes the required HTML for a language's input
	 *
	 * @param out
	 * @param language
	 * @throws IOException
	 */
	private void writeInputForLanguage(JspWriter out, String language) throws IOException {

		out.print("<div data-l10n-lang=\"");
		out.print(language);
		// If it is not the default language, hide it for now
		if (!getDefaultLanguage().equals(language)) {
			out.print("\" style=\"display: none\" ");
		}
		out.println("\">");

		this.writeInput(out, language);
		out.println("</div>");
	}

	/**
	 * Writes the HTML input field for the specified language
	 *
	 * @param out
	 * @param language
	 * @throws IOException
	 */
	private void writeInput(JspWriter out, String language) throws IOException {
		String value = getMessage(code, language);
		if (value == null) {
			value = "";
		}

		if (INPUT_TEXT.equals(this.inputType)) {
			out.println("<input type=\"text\" name=\"" + name + "." + language + "\" value=\"" + value + "\" />");
		} else if (INPUT_TEXT_AREA.equals(this.inputType)) {
			out.println("<textarea rows=\"8\" cols=\"80\" name=\"" + name + "." + language + "\">");
			out.println(value);
			out.println("</textarea>");
		} else {
			throw new IllegalArgumentException("You specified an invalid input type : " + inputType);
		}
	}

	/**
	 * Sets the name for this <code>LocalisedFieldTag</code>.
	 *
	 * @param name the name to set
	 */
	public void setFieldName(String fieldName) {
		this.name = fieldName;
	}

	/**
	 * Sets the inputType for this <code>LocalisedFieldTag</code>.
	 *
	 * @param inputType the inputType to set
	 */
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	/**
	 * @param code
	 * @param locale
	 * @return
	 */
	private String getMessage(String code, Locale locale) {
		if (StringUtils.isEmpty(code)) {
			return "";
		}
		try {
			return this.messageSource.getMessage(code, null, locale);
		} catch (NoSuchMessageException nsme) {
			return "ERROR - Message not found!";
		}
	}

	/**
	 * @param code
	 * @param locale
	 * @return
	 */
	private String getMessage(String code, String language) {
		Locale locale = new Locale(language);
		return getMessage(code, locale);
	}

	/**
	 * Sets the name for this <code>LocalisedFieldTag</code>.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the code for this <code>LocalisedFieldTag</code>.
	 *
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Sets the labelCode for this <code>LocalisedFieldTag</code>.
	 *
	 * @param labelCode the labelCode to set
	 */
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	/**
	 * Sets the messageSource for this <code>LocalisedFieldTag</code>.
	 *
	 * @param messageSource the messageSource to set
	 */
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	/**
	 * Gets the lanaguage this input should default too.
	 */
	private String getDefaultLanguage() {
		/*
		 *  TODO instead return the current language.
		 *  This isn't as easy as it sounds, you should make sure the 
		 *  user's prefered language is a supported language.
		 */
		return DEFAULT_LANGUAGE;
	}
}
