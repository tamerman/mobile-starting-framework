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

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@NamedQueries({
		@NamedQuery(
				name = "GradesPostedNotice.findById",
				query = "select g from GradesPostedNotice g where g.id = :id"
		),
		@NamedQuery(
				name = "GradesPostedNotice.countUnsentNotices",
				query = "select count(g) from GradesPostedNotice g where g.timestampProcessed is null"
		),

		//ORDER BY g.timestampReceived ASC
		@NamedQuery(
				name = "GradesPostedNotice.getUnsent",
				query = "SELECT g FROM GradesPostedNotice g WHERE g.timestampProcessed is null and g.inProcess = false "
		)
})

@Entity(name = "GradesPostedNotice")
@Table(name = "KME_GRADE_ALERTS_T")
public class GradesPostedNotice implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ID")
	private Long id;

	@Column(name = "USER_ID")
	private String loginName;

	@Column(name = "RECEIVED_TS")
	private Timestamp timestampReceived;

	@Column(name = "IN_PROCESS_F")
	private boolean inProcess;

	@Column(name = "PROCESSED_TS")
	private Timestamp timestampProcessed;

	public GradesPostedNotice() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long gradeAlertId) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Timestamp getTimestampReceived() {
		return timestampReceived;
	}

	public void setTimestampReceived(Timestamp timestampReceived) {
		this.timestampReceived = timestampReceived;
	}

	public Timestamp getTimestampProcessed() {
		return timestampProcessed;
	}

	public void setTimestampProcessed(Timestamp timestampProcessed) {
		this.timestampProcessed = timestampProcessed;
	}

	public boolean isInProcess() {
		return inProcess;
	}

	public void setInProcess(boolean inProcess) {
		this.inProcess = inProcess;
	}
}
