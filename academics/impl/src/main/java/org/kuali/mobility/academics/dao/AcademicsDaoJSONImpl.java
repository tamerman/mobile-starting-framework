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

package org.kuali.mobility.academics.dao;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.kuali.mobility.academics.entity.Career;
import org.kuali.mobility.academics.entity.Term;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
public class AcademicsDaoJSONImpl extends AcademicsDaoImpl {

	private static final Logger LOG = LoggerFactory.getLogger(AcademicsDaoJSONImpl.class);
	private static final String DEFAULT_CHARACTER_SET = "UTF-8";
	private String termUrl = null;
	private String careerUrl = null;
	private String subjectUrl = null;

	public List<Term> getTerms(final Map<String, String> query) {
		List<Term> lTerms = new ArrayList<Term>();

		try {
			URLConnection connection = new URL(getTermUrl()).openConnection();
			InputStream response = connection.getInputStream();

			String jsonData = IOUtils.toString(response, DEFAULT_CHARACTER_SET);

			LOG.debug("JSON Data is: [" + jsonData + "]");

			JSONParser parser = new JSONParser();

			Object rootObj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) rootObj;
			JSONArray jsonTerms = (JSONArray) jsonObject.get("term");

			for (Object o : jsonTerms) {
				Term term = (Term) getApplicationContext().getBean("termBean");

				JSONObject jsonTerm = (JSONObject) o;

				if (jsonTerm.get("id") instanceof Long) {
					term.setId(((Long) (jsonTerm.get("id"))).toString());
				} else {
					term.setId((String) jsonTerm.get("id"));
				}
				term.setDescription((String) jsonTerm.get("description"));
				term.setShortDescription((String) jsonTerm.get("shortDescription"));
				if (jsonTerm.get("active") instanceof Boolean) {
					term.setActive(((Boolean) jsonTerm.get("active")).booleanValue());
				} else {
					term.setActive(Boolean.parseBoolean((String) jsonTerm.get("active")));
				}
				lTerms.add(term);
			}
		} catch (UnsupportedEncodingException uee) {
			LOG.error(uee.getLocalizedMessage());
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage());
		} catch (ParseException pe) {
			LOG.error(pe.getLocalizedMessage(), pe);
		}

		return lTerms;
	}

	public List<Career> getCareers(final Map<String, String> query) {
		List<Career> lCareers = new ArrayList<Career>();

		try {
			URLConnection connection = new URL(getCareerUrl()).openConnection();
			InputStream response = connection.getInputStream();

			String jsonData = IOUtils.toString(response, DEFAULT_CHARACTER_SET);

			LOG.debug("JSON Data is: [" + jsonData + "]");

			JSONParser parser = new JSONParser();

			Object rootObj = parser.parse(jsonData);

			JSONObject jsonObject = (JSONObject) rootObj;
			JSONArray jsonTerms = (JSONArray) jsonObject.get("academicCareer");

			for (Object o : jsonTerms) {
				Career career = (Career) getApplicationContext().getBean("careerBean");

				JSONObject jsonTerm = (JSONObject) o;

				if (jsonTerm.get("id") instanceof Long) {
					career.setId(((Long) (jsonTerm.get("id"))).toString());
				} else {
					career.setId((String) jsonTerm.get("id"));
				}
				career.setDescription((String) jsonTerm.get("description"));
				career.setShortDescription((String) jsonTerm.get("shortDescription"));
				lCareers.add(career);
			}
		} catch (UnsupportedEncodingException uee) {
			LOG.error(uee.getLocalizedMessage());
		} catch (IOException ioe) {
			LOG.error(ioe.getLocalizedMessage());
		} catch (ParseException pe) {
			LOG.error(pe.getLocalizedMessage(), pe);
		}

		return lCareers;
	}

	/**
	 * @return the termUrl
	 */
	public String getTermUrl() {
		return termUrl;
	}

	/**
	 * @param termUrl the termUrl to set
	 */
	public void setTermUrl(String termUrl) {
		this.termUrl = termUrl;
	}

	/**
	 * @return the careerUrl
	 */
	public String getCareerUrl() {
		return careerUrl;
	}

	/**
	 * @param careerUrl the careerUrl to set
	 */
	public void setCareerUrl(String careerUrl) {
		this.careerUrl = careerUrl;
	}

	/**
	 * @return the subjectUrl
	 */
	public String getSubjectUrl() {
		return subjectUrl;
	}

	/**
	 * @param subjectUrl the subjectUrl to set
	 */
	public void setSubjectUrl(String subjectUrl) {
		this.subjectUrl = subjectUrl;
	}
}
