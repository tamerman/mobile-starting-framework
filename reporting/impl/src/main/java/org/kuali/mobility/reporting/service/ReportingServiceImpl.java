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

package org.kuali.mobility.reporting.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.kuali.mobility.file.entity.File;
import org.kuali.mobility.reporting.dao.ReportingDao;
import org.kuali.mobility.reporting.entity.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
public class ReportingServiceImpl implements ReportingService {

	@Autowired
	private ReportingDao reportingDao;

	@GET
	@Path("/submission/search")
	@Override
	@Transactional
	public Submission findSubmissionById(@QueryParam(value = "id") Long id) {
		return reportingDao.findSubmissionById(id);
	}

	@GET
	@Path("/submission/lookup")
	@Override
	@Transactional
	public List<Submission> findAllSubmissions() {
		return reportingDao.findAllSubmissions();
	}

	@Override
	@Transactional
	public Long saveSubmission(Submission submission) {
		return reportingDao.saveSubmission(submission);
	}

	@Override
	@Transactional
	public Long saveAttachment(File file) {
		return reportingDao.saveAttachment(file);
	}

	@GET
	@Path("/submission/byparent")
	@Override
	@Transactional
	public List<Submission> findAllSubmissionsByParentId(@QueryParam(value = "id") Long id) {
		return reportingDao.findAllSubmissionsByParentId(id);
	}

	public ReportingDao getReportingDao() {
		return reportingDao;
	}

	public void setReportingDao(ReportingDao reportingDao) {
		this.reportingDao = reportingDao;
	}
}
