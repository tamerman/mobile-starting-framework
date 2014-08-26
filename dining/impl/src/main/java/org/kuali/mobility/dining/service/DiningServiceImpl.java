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

package org.kuali.mobility.dining.service;

import org.apache.commons.collections.CollectionUtils;
import org.kuali.mobility.dining.dao.DiningDao;
import org.kuali.mobility.dining.entity.DiningHallGroup;
import org.kuali.mobility.dining.util.DiningHallBuildingPredicate;
import org.kuali.mobility.dining.util.DiningHallCampusPredicate;
import org.kuali.mobility.dining.util.DiningHallNamePredicate;
import org.kuali.mobility.shared.entity.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Kuali Mobility Team (mobility.dev@kuali.org)
 */
@WebService(endpointInterface = "org.kuali.mobility.dining.service.DiningService")
public class DiningServiceImpl implements DiningService {

	private static Logger LOG = LoggerFactory.getLogger(DiningServiceImpl.class);

	@Resource(name = "diningDao")
	private DiningDao dao;

	@GET
	@Path("/diningHallGroups")
	@Override
	public List<DiningHallGroup> getDiningHallGroups() {
		List<DiningHallGroup> diningHallGroups = new ArrayList<DiningHallGroup>();
		try {
			diningHallGroups = getDao().getDiningHallGroups();
		} catch (Exception e) {
			DiningHallGroup diningHallGroup = new DiningHallGroup();
			ErrorMessage error = new ErrorMessage();
			error.setName("No Dining Hall Group Found");
			error.setDescription("An error occurred while retrieving dining hall group.");
			diningHallGroups.add(diningHallGroup);
		}
		return diningHallGroups;
	}

	@GET
	@Path("/diningHallGroupsByCampus")
	@Override
	public List<DiningHallGroup> getDiningHallGroupsByCampus(@QueryParam(value = "campus") String campus) {
		List<DiningHallGroup> diningHallGroups = new ArrayList<DiningHallGroup>();
		try {
			CollectionUtils.select(getDao().getDiningHallGroups(), new DiningHallCampusPredicate(campus), diningHallGroups);
		} catch (Exception e) {
			DiningHallGroup diningHallGroup = new DiningHallGroup();
			ErrorMessage error = new ErrorMessage();
			error.setName("No Dining Hall Groups Found");
			error.setDescription("No dining hall group found for " + campus + ".");
			List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
			//diningHallGroup.setErrors(errors);
			diningHallGroups.add(diningHallGroup);
		}
		//Collections.sort( diningHalls, new DiningHallComparitor() );
		return diningHallGroups;
	}


	@GET
	@Path("/diningHallGroupsByBuilding")
	@Override
	public List<DiningHallGroup> getDiningHallGroupsByBuilding(@QueryParam(value = "buildingName") String buildingName) {
		List<DiningHallGroup> diningHallGroups = new ArrayList<DiningHallGroup>();
		try {
			CollectionUtils.select(getDao().getDiningHallGroups(), new DiningHallBuildingPredicate(buildingName), diningHallGroups);
		} catch (Exception e) {
			DiningHallGroup diningHallGroup = new DiningHallGroup();
			ErrorMessage error = new ErrorMessage();
			error.setName("No Dining Halls Found");
			error.setDescription("No dining hall found for " + buildingName + ".");
			List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
//            diningHallGroup.setErrors(errors);
			diningHallGroups.add(diningHallGroup);
		}
		return diningHallGroups;
	}

	@GET
	@Path("/diningHallGroupsNearby")
	@Override
	public List<DiningHallGroup> getDiningHallGroupsNearby(
			@QueryParam(value = "latitude") String latitude,
			@QueryParam(value = "longitude") String longitude,
			@QueryParam(value = "radius") int radius,
			@QueryParam(value = "unit") String unit) {
		List<DiningHallGroup> diningHallGroups = new ArrayList<DiningHallGroup>();

		DiningHallGroup diningHallGroup = new DiningHallGroup();
		ErrorMessage error = new ErrorMessage();
		error.setName("Not Yet Implemented");
		error.setDescription("Searching for dining halls by your location is not yet implemented.");
		List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
		diningHallGroups.add(diningHallGroup);

		return diningHallGroups;
	}

	@GET
	@Path("/diningHallGroup")
	@Override
	public DiningHallGroup getDiningHallGroup(@QueryParam(value = "name") String name) {
		DiningHallGroup diningHallGroup = null;
		Collection<? extends DiningHallGroup> diningHallGroups = CollectionUtils.select(getDao().getDiningHallGroups(), new DiningHallNamePredicate(name));
		if (diningHallGroups.size() > 1) {
			ErrorMessage error = new ErrorMessage();
			error.setName("Ambiguous request");
			error.setDescription("Multiple dining halls found for request.");
			diningHallGroup = new DiningHallGroup();
		} else {
			diningHallGroup = (DiningHallGroup) ((diningHallGroups.toArray())[0]);
		}
		return diningHallGroup;
	}

	public DiningDao getDao() {
		return dao;
	}

	public void setDao(DiningDao dao) {
		this.dao = dao;
	}
}


