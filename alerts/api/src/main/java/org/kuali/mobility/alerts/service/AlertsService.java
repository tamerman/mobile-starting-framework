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

package org.kuali.mobility.alerts.service;

import org.kuali.mobility.alerts.entity.Alert;
import org.kuali.mobility.alerts.entity.LastAlertDate;

import java.util.List;

/**
 * Provides service methods for retrieving <code>Alert</code> instances.
 * 
 * @author Kuali Mobility Team 
 */
public interface AlertsService {

	/**
	 * A finder that returns all <code>Alert</code> instances for the campus passed.
	 * .
	 * 
	 * @param campus 
	 * 			- <code>String</code> campus code.
	 * @return <code>List&lt;Alert&gt;</code> filtered by the campus or an empty 
	 * 			<code>List</code>. 
	 */
	public List<Alert> findAlertsByCampus(String campus);

	int findAlertCountByCampus(String campus);

	LastAlertDate getLastAlertDate(String campus);
}
