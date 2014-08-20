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

package org.kuali.mobility.configparams.service;

import java.util.Collection;
import java.util.List;

import org.kuali.mobility.configparams.entity.ConfigParam;

/**
 * Interface for a contract for interacting with configuration parameters
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
public interface ConfigParamService {

	/**
	 * @param configParam the ConfigParam to save
	 * @return the id of the saved ConfigParam
	 */
	public Long saveConfigParam(ConfigParam configParam);

	/**
	 * @return all configuration parameters
	 */
	public List<ConfigParam> findAllConfigParam();

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
     * @param id the id of the ConfigParam to delete
     */
	public void deleteConfigParamById(Long id);

	/**
	 * @param name the name of the ConfigParam
	 * @return the value of the matched ConfigParam
	 */
    public String findValueByName(String name);

    /**
     * Convert a collection of ConfigParam objects to JSON notation
     * @param collection the colleciton of ConfigParam objects to convert
     * @return the JSON string representing the collection
     */
    public String toJson(Collection<ConfigParam> collection);
    
    /**
     * Parse a JSON string into a ConfigParam object
     * @param json
     * @return the parsed ConfigParam
     */
    public ConfigParam fromJsonToEntity(String json);
    
    /**
     * Parse a JSON string into a ConfigParam collection
     * @param json
     * @return the parsed ConfigParam collection
     */
    public Collection<ConfigParam> fromJsonToCollection(String json);

}
