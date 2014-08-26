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

package org.kuali.mobility.auth;

import org.apache.cxf.helpers.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.kuali.mobility.security.group.api.GroupDao;
import org.kuali.mobility.security.user.api.UserDao;
import org.kuali.mobility.shared.listeners.Bootables;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Bootstrap listener for the Auth Tool
 *
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 * @since 3.2.0
 */
public class AuthBootListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(AuthBootListener.class);
	@Resource(name = "kmeProperties")
	private Properties kmeProperties;

	@Autowired
	private Bootables bootables;

	@Resource(name = "kmeUserDao")
	private UserDao userDao;

	@Resource(name = "kmeGroupDao")
	private GroupDao groupDao;

	@PersistenceContext
	private EntityManager entityManager;

	public void initialize() {
		getBootables().registeredBootable(this);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (getKmeProperties() != null &&
				"true".equalsIgnoreCase(getKmeProperties().getProperty("auth.bootstrap", "false"))) {
			Connection conn = null;
			Statement stmt = null;
			try {
				InputStream in = this.getClass().getResourceAsStream(getKmeProperties().getProperty("auth.bootstrap.filename"));
				String loadData = IOUtils.toString(in);
				String[] lines = loadData.split(System.getProperty("line.separator"));
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].isEmpty() || lines[i].startsWith("--")) {
						continue;
					} else {
						LOG.debug("###########################################################################");
						LOG.debug(lines[i]);
						Class.forName(getKmeProperties().getProperty("shared.datasource.driver.name"));
						conn = DriverManager.getConnection(
								getKmeProperties().getProperty("shared.datasource.url"),
								getKmeProperties().getProperty("shared.datasource.username"),
								getKmeProperties().getProperty("shared.datasource.password"));
						stmt = conn.createStatement();
						stmt.executeUpdate(lines[i]);
						stmt.close();
						conn.close();
					}
				}
			} catch (IOException ioe) {
				LOG.error("###########################################################################");
				LOG.error("# User and Group data failed to load load");
				LOG.error("###########################################################################");
				LOG.error(ioe.getLocalizedMessage());
			} catch (ClassNotFoundException cnfe) {
				LOG.error("###########################################################################");
				LOG.error("Could not find database driver.");
				LOG.error("###########################################################################");
				LOG.error(cnfe.getLocalizedMessage());
			} catch (SQLException sqe) {
				LOG.error("###########################################################################");
				LOG.error("Could not connect to the database.");
				LOG.error("###########################################################################");
				LOG.error(sqe.getLocalizedMessage());
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException sqe) {
						LOG.error(sqe.getLocalizedMessage());
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException sqe) {
						LOG.error(sqe.getLocalizedMessage());
					}
				}
			}
		} else {
			LOG.debug("###########################################################################");
			LOG.debug("Auth bootstrapping turned off.");
			LOG.debug("###########################################################################");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	public Properties getKmeProperties() {
		return kmeProperties;
	}

	public void setKmeProperties(Properties kmeProperties) {
		this.kmeProperties = kmeProperties;
	}

	public Bootables getBootables() {
		return bootables;
	}

	public void setBootables(Bootables bootables) {
		this.bootables = bootables;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
