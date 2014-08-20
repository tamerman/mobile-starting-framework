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

package org.kuali.mobility.admin.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.mobility.admin.entity.Tool;

/**
 * Interface for a contract for administrative tasks
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@WebService
public interface ToolService {

	/**
	 * @return all defined Tool objects
	 */
	public List<Tool> getAllTools();
	/**
	 * @param tool the Tool to save
	 * @return the id of the saved Tool
	 */
	public Long saveTool(String data);
	/**
	 * @param toolId the id of the tool to retrieve
	 * @return the Tool matching the id
	 */
	public Tool getToolById(Long toolId);
	/**
	 * @param toolId the id of the Tool to delete
	 */
	public void deleteToolById(Long toolId);

}
