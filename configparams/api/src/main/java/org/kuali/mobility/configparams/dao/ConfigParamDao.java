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

package org.kuali.mobility.configparams.dao;

import java.util.List;

import org.kuali.mobility.configparams.entity.ConfigParam;

/**
 * Interface for a contract for manipulating configuration parameters
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public interface ConfigParamDao {

	/**
	 * @param id the id of the ConfigParam to retrieve
	 * @return the ConfigParam matching the id
	 */
	public ConfigParam findConfigParamById(Long id);

	/**
	 * @param name the name of the ConfigParam to retrieve
	 * @return the ConfigParam matching the name
	 */
	public ConfigParam findConfigParamByName(String name);

	/**
	 * @param configParam the ConfigParam to save
	 * @return the id of the saved ConfigParam
	 */
	public Long saveConfigParam(ConfigParam configParam);

	/**
	 * @return a list of all configuration parameters
	 */
	public List<ConfigParam> findAllConfigParam();

	/**
	 * @param id the id of the ConfigParam to delete
	 */
	public void deleteConfigParamById(Long id);

}
