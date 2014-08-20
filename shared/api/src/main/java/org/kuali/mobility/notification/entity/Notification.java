/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

package org.kuali.mobility.notification.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
@Table(name="KME_NTFY_T")
public class Notification implements Serializable {

	private static final long serialVersionUID = -95981285235712123L;

    @Id
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="ID")
    private Long notificationId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="STRT_DT")
	private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="END_DT")
	private Date endDate;

    @Column(name="TTL")
	private String title;

    @Column(name="MSG")
    private String message;

    // TODO: This needs to be refactored to be a set of generic user attributes, permissions, etc
    @Column(name="PRI_CMPS")
    private String primaryCampus;

    @Column(name="TYP")
    private Long notificationType;

    @Version
    @Column(name="VER_NBR")
    protected Long versionNumber;	

    public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPrimaryCampus() {
		return primaryCampus;
	}

	public void setPrimaryCampus(String primaryCampus) {
		this.primaryCampus = primaryCampus;
	}

	public Long getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(Long notificationType) {
		this.notificationType = notificationType;
	}

	public Long getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}
	
}
