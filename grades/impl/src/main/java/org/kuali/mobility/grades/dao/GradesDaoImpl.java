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

package org.kuali.mobility.grades.dao;

import org.kuali.mobility.grades.entity.ModuleResults;
import org.kuali.mobility.util.mapper.DataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the Grades Data Access Object
 *
 * @author Charl Thiem
 */
public class GradesDaoImpl implements GradesDao {

	/**
	 * A reference to a logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(GradesDaoImpl.class);

	/**
	 * URL to the configuration for mapping the source to Objects
	 */
	@Value("${grades.mapping.url}")
	private String dataMappingUrl;

	/**
	 * File to use for data mapping
	 */
	@Value("${grades.mapping.file}")
	private String dataMappingFile;

	/**
	 * Source URL where to retrieve the data from
	 */
	@Value("${grades.source.url}")
	private String sourceUrl;

	/**
	 * Reference to the DataMapper
	 */
	@Autowired
	private DataMapper dataMapper;

	/*
	 * (non-Javadoc)
	 * @see org.kuali.mobility.grades.dao.GradesDao#getResults(java.util.Date, java.util.Date, java.lang.String, java.lang.String)
	 */
	@Override
	public List<ModuleResults> getResults(Date startDate, Date endDate, String studentNumber, String language) {
		List<ModuleResults> results = new ArrayList<ModuleResults>();
		try {
			URL url = new URL(sourceUrl);
			if (dataMappingUrl != null && !"".equals(dataMappingUrl.trim())) {
				results.addAll(dataMapper.mapData(results, url, new URL(dataMappingUrl)));
			} else {
				if (dataMappingFile != null && !"".equals(dataMappingFile.trim())) {
					results.addAll(dataMapper.mapData(results, url, "gradesMapping.xml"));
				} else {
					LOG.warn("No mapping file/url for grades is specified!");
				}

			}
		} catch (Exception e) {
			LOG.error("errors", e);
		}

		return results;
	}

}
