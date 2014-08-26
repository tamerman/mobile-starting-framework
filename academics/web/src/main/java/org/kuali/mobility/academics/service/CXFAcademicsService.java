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
import org.kuali.mobility.academics.dao.AcademicsDao;
import org.kuali.mobility.academics.entity.Career;
import org.kuali.mobility.academics.entity.SearchResult;
import org.kuali.mobility.academics.entity.Subject;
import org.kuali.mobility.academics.entity.Term;
import org.kuali.mobility.academics.util.AcademicsConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CXFAcademicsService {
	/**
	 * A reference to a logger for this service
	 */
	private static final Logger LOG = LoggerFactory.getLogger(CXFAcademicsService.class);

	private AcademicsDao dao;

	@GET
	@Path("/getTerms")
	public List<Term> getTerms() {
		return getDao().getTerms();
	}

	@POST
	@Path("/getCareers")
	public List<Career> getCareers(@RequestBody String data) {
		if (data == null) {
			return null; //and sadness
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}
		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.TERM_ID, queryParams.getString("termId"));
		return getDao().getCareers(query);
	}

	@POST
	@Path("/getSubjects")
	public List<Subject> getSubjects(@RequestBody String data) {
		if (data == null) {
			return null; //and sadness
		}

		JSONObject queryParams;
		try {
			queryParams = (JSONObject) JSONSerializer.toJSON(data);
		} catch (JSONException je) {
			LOG.error("JSONException in :" + data + " : " + je.getMessage());
			return null; //and more sadness
		}
		Map<String, String> query = new HashMap<String, String>();
		query.put(AcademicsConstants.TERM_ID, queryParams.getString("termId"));
		query.put(AcademicsConstants.CAREER_ID, queryParams.getString("careerId"));
		List<Subject> subjects = getDao().getSubjects(query);
		return subjects;
	}

	@POST
	@Path("/searchClasses")
	public SearchResult searchClasses(@RequestBody String data) {
		//TODO: Have this actually search for classes
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

		LOG.debug(queryParams.toString());

		SearchResult result = getDao().getSearchResults(queryParams);
		return result;

/*
		List<Section> dumbyList = new ArrayList<Section>();
		Section dumbySection1 = new Section();
		dumbySection1.setSubjectId("SUBJECTID");
		dumbySection1.setCatalogNumber("###");
		dumbySection1.setNumber("###");
		dumbySection1.setType("IND");
		dumbySection1.setCreditHours("12");
		dumbySection1.setStartDate("02/13/2014");
		dumbySection1.setEndDate("02/13/2015");
		dumbySection1.setCourseTitle("test 1");
		dumbySection1.setClassTopic("topic place holder");
		dumbySection1.setEnrollmentStatus("open");
		dumbySection1.setAvailableSeats("13");
		dumbySection1.setCourseDescription("A very long and indepth description");
		dumbySection1.setAdditionalInfo("additional info goes here");
		SectionMeeting meeting1 = new SectionMeeting();
		meeting1.setDays("TBA");
		meeting1.setTimes("");
		meeting1.setLocation("TBA");
		meeting1.setInstructors(Arrays.asList("Doe, John H"));
		dumbySection1.setMeetings(Arrays.asList(meeting1));
		
		Section dumbySection2 = new Section();
		dumbySection2.setSubjectId("SUBJECTID");
		dumbySection2.setCatalogNumber("###");
		dumbySection2.setNumber("###");
		dumbySection2.setType("SEM");
		dumbySection2.setCreditHours("6");
		dumbySection2.setStartDate("02/13/2014");
		dumbySection2.setEndDate("02/13/2015");
		dumbySection2.setCourseTitle("test 2");
		dumbySection2.setClassTopic("topic place holder");
		dumbySection2.setEnrollmentStatus("wait list");
		dumbySection2.setAvailableSeats("0");
		dumbySection2.setCourseDescription("A very long and indepth description");
		dumbySection2.setAdditionalInfo("additional info goes here");
		SectionMeeting meeting2a = new SectionMeeting();
		meeting2a.setDays("MoWeFr");
		meeting2a.setTimes("10:00AM - 11:00AM");
		meeting2a.setLocation("Some Building");
		meeting2a.setInstructors(Arrays.asList("Doe, John H", "Doe II, John H"));
		SectionMeeting meeting2b = new SectionMeeting();
		meeting2b.setDays("TuTh");
		meeting2b.setTimes("10:30AM - 11:30AM");
		meeting2b.setLocation("Some Building");
		meeting2b.setInstructors(Arrays.asList("Doe, John H", "Doe II, John H"));
		dumbySection2.setMeetings(Arrays.asList(meeting2a, meeting2b));
		
		Section dumbySection3 = new Section();
		dumbySection3.setSubjectId("SUBJECTID");
		dumbySection3.setCatalogNumber("###");
		dumbySection3.setNumber("###");
		dumbySection3.setType("LEC");
		dumbySection3.setCreditHours("2");
		dumbySection3.setStartDate("02/13/2014");
		dumbySection3.setEndDate("02/13/2015");
		dumbySection3.setCourseTitle("test 2");
		dumbySection3.setClassTopic("topic place holder");
		dumbySection3.setEnrollmentStatus("closed");
		dumbySection3.setAvailableSeats("0");
		dumbySection3.setCourseDescription("A very long and indepth description");
		dumbySection3.setAdditionalInfo("additional info goes here");
		SectionMeeting meeting3 = new SectionMeeting();
		meeting3.setDays("TuTh");
		meeting3.setTimes("11:00AM - 12:00AM");
		meeting3.setLocation("Some Building");
		meeting3.setInstructors(Arrays.asList("Doe, John H", "Doe II, John H"));
		dumbySection3.setMeetings(Arrays.asList(meeting3));
		
		dumbyList.add(dumbySection1);
		dumbyList.add(dumbySection2);
		dumbyList.add(dumbySection3);
		return dumbyList;
*/
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

	public AcademicsDao getDao() {
		return dao;
	}

	public void setDao(AcademicsDao dao) {
		this.dao = dao;
	}
}
