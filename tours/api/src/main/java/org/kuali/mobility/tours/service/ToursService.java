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

package org.kuali.mobility.tours.service;

import java.util.List;

import javax.jws.WebService;

import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.tours.entity.POI;
import org.kuali.mobility.tours.entity.Tour;

import de.micromata.opengis.kml.v_2_2_0.Kml;

@WebService
public interface ToursService {

	public static final String PERMISSION_TYPE_EDIT = "E";
	public static final String PERMISSION_TYPE_VIEW = "V";

	public Tour findTourById(Long id);

	public Tour findTourByName(String name);

	public Long saveTour(Tour tour);

	public List<Tour> findAllTours();

	public void deleteTourById(Long id);

	public void duplicateTourById(Long id);

	public POI findPoiById(Long id);

	public POI findPoiByOrder(Long tourId, Integer order);

	public Long savePoi(POI poi);

	public void duplicatePoiById(Long id);

	public void deletePoiById(Long poiId);

	public List<POI> findAllCommonPOI();

	public Kml createTourKml(Tour tour);

	public boolean hasAccessToEditTour(User user, Tour tour);

	public boolean hasAccessToViewTour(User user, Tour tour);

	public boolean hasAccessToEditPOI(User user, POI poi);

	public boolean hasAccessToViewPOI(User user, POI poi);

	public boolean hasAccessToPublish(User user);

}
