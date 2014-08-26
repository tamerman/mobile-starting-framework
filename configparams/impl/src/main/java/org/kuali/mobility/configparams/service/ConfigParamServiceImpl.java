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

package org.kuali.mobility.configparams.service;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.kuali.mobility.configparams.dao.ConfigParamDao;
import org.kuali.mobility.configparams.entity.ConfigParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Service to perform manipulation of configuration parameters
 *
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@Service(value = "ConfigParamService")
public class ConfigParamServiceImpl implements ConfigParamService {

	private static Logger LOG = LoggerFactory.getLogger(ConfigParamServiceImpl.class);

	@Autowired
	private ConfigParamDao configParamDao;

	@Transactional
	public void deleteConfigParamById(Long id) {
		configParamDao.deleteConfigParamById(id);
	}

	@Transactional
	public List<ConfigParam> findAllConfigParam() {
		return configParamDao.findAllConfigParam();
	}

	@Transactional
	public ConfigParam findConfigParamById(Long id) {
		return configParamDao.findConfigParamById(id);
	}

	@Transactional
	public ConfigParam findConfigParamByName(String name) {
		return configParamDao.findConfigParamByName(name);
	}

	@Transactional(readOnly = true)
	public String findValueByName(String name) {
		ConfigParam configParam = findConfigParamByName(name);
		String value = configParam != null ? configParam.getValue() : null;
		return value != null ? value.trim() : "";
	}

	@Transactional
	public Long saveConfigParam(ConfigParam configParam) {
		return configParamDao.saveConfigParam(configParam);
	}

	public ConfigParam fromJsonToEntity(String json) {
		return new JSONDeserializer<ConfigParam>().use(null, ConfigParam.class).deserialize(json);
	}

	public String toJson(Collection<ConfigParam> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public Collection<ConfigParam> fromJsonToCollection(String json) {
		return new JSONDeserializer<List<ConfigParam>>().use(null, ArrayList.class).use("values", ConfigParam.class).deserialize(json);
	}

}
