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


package org.kuali.mobility.bus.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.kuali.mobility.bus.entity.Bus;
import org.kuali.mobility.bus.entity.BusAlert;
import org.kuali.mobility.bus.entity.BusRoute;
import org.kuali.mobility.bus.entity.BusStop;

import org.springframework.stereotype.Repository;

@Repository
public class BusDaoImpl implements BusDao {

	@PersistenceContext
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<BusRoute> getBusRoutes() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<BusStop> getBusStops() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<Bus> getBuses() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadRoutes() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadStops() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadBusLocations() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public List<BusAlert> getBusAlerts() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void loadAlerts() {
		throw new UnsupportedOperationException("Not supported yet.");
	}


}
