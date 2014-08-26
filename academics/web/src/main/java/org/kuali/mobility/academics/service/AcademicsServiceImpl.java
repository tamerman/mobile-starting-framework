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

package org.kuali.mobility.academics.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.academics.dao.AcademicsDao;
import org.kuali.mobility.academics.entity.*;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.kuali.mobility.academics.util.TermPredicate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.*;

/**
 * @author Kuali Mobility Team <mobility.collab@kuali.org>
 */
@Service
@WebService(endpointInterface = "org.kuali.mobility.academics.service.AcademicsService")
public class AcademicsServiceImpl implements AcademicsService, ApplicationContextAware {

	private static final Logger LOG = LoggerFactory.getLogger(AcademicsServiceImpl.class);
	private AcademicsDao dao;
	private ApplicationContext context;
	@Context
	private MessageContext messageContext;

	@Override
	@GET
	@Path("/getTerms")
	public List<Term> getTerms() {
		return getDao().getTerms(null);
	}

	@Override
	@GET
	@Path("/getTerm")
	public Term getTerm(@QueryParam("termId") final String termId) {
		Term term = (Term) CollectionUtils.find(getDao().getTerms(null), new TermPredicate(termId));
		return term;
	}

	@Override
	@GET
	@Path("/getCareers")
	public List<Career> getCareers(@QueryParam("termId") final String termId) {
		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.TERM_ID, termId);
		return getDao().getCareers(query);
	}

	@Override
	@GET
	@Path("/getSubjects")
	public List<Subject> getSubjects(
			@QueryParam("termId") final String termId,
			@QueryParam("careerId") final String careerId) {
		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.TERM_ID, termId);
		query.put(AcademicsConstants.CAREER_ID, careerId);
		return getDao().getSubjects(query);
	}

	@Override
	@GET
	@Path("/getCatalogNumbers")
	public List<CatalogNumber> getCatalogNumbers(
			@QueryParam("termId") final String termId,
			@QueryParam("subjectId") final String subjectId) {
		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.TERM_ID, termId);
		query.put(AcademicsConstants.SUBJECT_ID, subjectId);
		return getDao().getCatalogNumbers(query);
	}

	@SuppressWarnings("unchecked")
	@Override
	@GET
	@Path("/getSections")
	public List<Section> getSections(
			@QueryParam("termId") final String termId,
			@QueryParam("subjectId") final String subjectId,
			@QueryParam("catalogNumber") final String catalogNumber,
			@QueryParam("careerId") final String careerId) {

		List<Section> sections = new ArrayList<Section>();

		LOG.debug("termId is [" + termId + "]");
		LOG.debug("subjectId is [" + subjectId + "]");
		LOG.debug("catalogNumber is [" + catalogNumber + "]");
		LOG.debug("careerId is [" + careerId + "]");

		HashMap<String, String> query = new HashMap<String, String>();

		query.put(AcademicsConstants.TERM_ID, termId);
		query.put(AcademicsConstants.SUBJECT_ID, subjectId);
		query.put(AcademicsConstants.CATALOG_NUMBER, catalogNumber);
		query.put(AcademicsConstants.CAREER_ID, careerId);

		LOG.debug("query termId is [" + query.get("termId") + "]");
		LOG.debug("query subjectId is [" + query.get("subjectId") + "]");
		LOG.debug("query catalogNumber is [" + query.get("catalogNumber") + "]");
		LOG.debug("query careerId is [" + query.get("careerId") + "]");

		sections = getDao().getSections(query);
		LOG.debug("Sections is " + sections.size() + " items long.");
		return sections;
	}

	@SuppressWarnings("unchecked")
	@Override
	@GET
	@Path("/getSectionDetailByUID")
	public Section getSection(
			@QueryParam("sectionUID") final String sectionUID) {
		Section section = null;
		HttpServletRequest request = (HttpServletRequest) getMessageContext().getHttpServletRequest();

		if (request != null && request.getSession() != null) {
			HttpSession session = request.getSession();
			if (session.getAttribute(AcademicsConstants.SECTIONS) != null) {
				List<Section> allSections = (List<Section>) session.getAttribute(AcademicsConstants.SECTIONS);
				for (Section thisSection : allSections) {
					if (sectionUID.equalsIgnoreCase(thisSection.getSectionUID())) {
						section = getDao().getSectionDetail(thisSection);
						break;
					}
				}
			}
		}
		return section;
	}

	@SuppressWarnings("unchecked")
	@Override
	@GET
	@Path("/getSectionDetail")
	public Section getSectionDetail(
			@QueryParam("termId") final String termId,
			@QueryParam("careerId") final String careerId,
			@QueryParam("subjectId") final String subjectId,
			@QueryParam("catalogNumber") final String catalogNumber,
			@QueryParam("sectionNumber") final String sectionNumber) {
		Section thisSection = null;
		thisSection = getDao().getSectionDetail(termId, careerId, subjectId, catalogNumber, sectionNumber);
		return thisSection;
	}

	public Section getSectionDetail(final Section mySection) {
		return getDao().getSectionDetail(mySection);
	}

	private Map<String, String[]> toMap(JSONObject jsonObject) throws JSONException {
		Map<String, String[]> queryMap = new HashMap<String, String[]>();
		Iterator keys = jsonObject.keys();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			Object val = jsonObject.get(key);
			if (val != null) {
				if (val instanceof JSONArray) {
					JSONArray valJsonArr = (JSONArray) val;
					int arrLength = valJsonArr.size();
					String[] valArr = new String[arrLength];
					for (int i = 0; i < arrLength; i++) {
						valArr[i] = valJsonArr.getString(i);
					}
					queryMap.put(key, valArr);
				} else {
					String[] valArr = {val.toString()};
					queryMap.put(key, valArr);
				}
			}
		}
		return queryMap;
	}

	@Override
	@POST
	@Path("/classSearch")
	public SearchResult getClassSearchResults(@RequestBody String data) {

		if (data == null) {
			return null;
		}

		Map<String, String[]> queryParams;
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) JSONSerializer.toJSON(data);

			queryParams = toMap(jsonObject);

		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}

		//LOG.debug(queryParams);

		SearchResult result = getDao().getSearchResults(queryParams);
		return result;
	}

	@Override
	@POST
	@Path("/keywordSearch")
	public SearchResult getKeywordSearch(
			@FormParam("term") final String termCode,
			@FormParam("searchText") final String keyword) {
		Map<String, String[]> query = new HashMap<String, String[]>();
		query.put(AcademicsConstants.TERM_ID, new String[]{termCode});
		query.put(AcademicsConstants.SEARCH_TEXT, new String[]{keyword});

		return getSearchResults(query);
	}

	public SearchResult getSearchResults(Map<String, String[]> query) {
		return getDao().getSearchResults(query);
	}

	public void setDao(AcademicsDao dao) {
		this.dao = dao;
	}

	public AcademicsDao getDao() {
		return dao;
	}

	/**
	 * @return the context
	 */
	public ApplicationContext getApplicationContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setApplicationContext(ApplicationContext context) {
		this.context = context;
	}

	/**
	 * @return the messageContext
	 */
	public MessageContext getMessageContext() {
		return messageContext;
	}

	/**
	 * @param messageContext the messageContext to set
	 */
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}
}
