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


package org.kuali.mobility.database.service;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.jdbc.util.FormatStyle;
import org.hibernate.jdbc.util.Formatter;
import org.hibernate.mapping.ForeignKey;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.util.PropertiesHelper;
import org.kuali.mobility.database.dao.DatabaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl implements DatabaseService {

	private static Logger LOG = LoggerFactory.getLogger(DatabaseServiceImpl.class);

	@Autowired
	private DatabaseDao dao;

	public void setDatabaseDao(DatabaseDao dao) {
		this.dao = dao;
	}

	public DatabaseDao getDatabaseDao() {
		return dao;
	}

	@Resource(name = "&entityManagerFactory")
	private LocalContainerEntityManagerFactoryBean entityManagerFactory;

	@Override
	public String getSchema(String dialect, String delimiter, boolean overrideAlterTable) {
		return execute(dialect, delimiter, overrideAlterTable);
	}

	private String execute(String dialectStr, String delimiter, boolean overrideAlterTable) {
		PersistenceUnitInfo persistenceUnitInfo = entityManagerFactory.getPersistenceUnitInfo();

		Map<String, Object> jpaPropertyMap = entityManagerFactory.getJpaPropertyMap();
		jpaPropertyMap.put("hibernate.dialect", dialectStr);
		Configuration configuration = new Ejb3Configuration().configure(persistenceUnitInfo, jpaPropertyMap).getHibernateConfiguration();
//	    KMEDatabaseConfiguration c = (KMEDatabaseConfiguration) configuration;

		if (overrideAlterTable) {
			Iterator iter = configuration.getTableMappings();
			while (iter.hasNext()) {
				Table table = (Table) iter.next();
				if (table.isPhysicalTable()) {
					Iterator subIter = table.getForeignKeyIterator();
					while (subIter.hasNext()) {
						ForeignKey fk = (ForeignKey) subIter.next();
						if (fk.isPhysicalConstraint()) {
							subIter.remove();
						}
					}
				}
			}
		}

		Properties configurationProperties = configuration.getProperties();

		Dialect dialect = Dialect.getDialect(configurationProperties);
//	    if (dialect instanceof KMEDialect) {
//	    	KMEDialect d = (KMEDialect) dialect;
//	    	d.setOverrideAlterTable(overrideAlterTable);
//	    }

		Properties props = new Properties();
		props.putAll(dialect.getDefaultProperties());
		props.putAll(configurationProperties);

		String[] dropSQL = configuration.generateDropSchemaScript(dialect);
		String[] createSQL = configuration.generateSchemaCreationScript(dialect);

		Formatter formatter = (PropertiesHelper.getBoolean(Environment.FORMAT_SQL, props) ? FormatStyle.DDL
				: FormatStyle.NONE).getFormatter();
		boolean format = true;
		formatter = (format ? FormatStyle.DDL : FormatStyle.NONE).getFormatter();

//		String delimiter = ";";
		StringBuffer output = new StringBuffer();
		for (String s : dropSQL) {
			output.append(formatMe(s, formatter, delimiter));
			output.append("\n");
		}
		for (String s : createSQL) {
			output.append(formatMe(s, formatter, delimiter));
			output.append("\n");
		}

		SchemaExport schema = new SchemaExport(configuration);
//	    schema.setFormat(true);
//	    schema.setDelimiter(";");
//	    schema.setOutputFile("/tmp/schema.sql");
//  	schema.create(false, false);

//org.hibernate.dialect.Oracle10gDialect
//org.hibernate.dialect.MySQL5Dialect
		return output.toString();
	}

	private String formatMe(String sql, Formatter formatter, String delimiter) {
		String formatted = formatter.format(sql);
		if (delimiter != null) {
			formatted += delimiter;
		}
		return formatted;
	}


}
