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

package org.kuali.mobility.tours.controllers;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.mobility.security.user.api.User;
import org.kuali.mobility.security.user.entity.UserImpl;
import org.kuali.mobility.shared.Constants;
import org.kuali.mobility.tours.entity.POI;
import org.kuali.mobility.tours.entity.POIPermission;
import org.kuali.mobility.tours.entity.POIPhoneNumber;
import org.kuali.mobility.tours.entity.Tour;
import org.kuali.mobility.tours.entity.TourPermission;
import org.kuali.mobility.tours.service.ToursService;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletContext;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

/**
 * @author Kuali Mobility Team (mobility.collab@kuali.org)
 */
@RunWith(org.mockito.runners.MockitoJUnitRunner.class)
public class ToursControllerTest {
	private static final String USER = "fauxUser";
	private static MockServletContext servletContext;
	private ToursController controller;
	@Mock
	private ToursService toursService;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private Model uiModel;

	@BeforeClass
	public static void setUpClass() throws Exception {
		servletContext = new MockServletContext();
	}

	@Before
	public void preTest() {
		this.setController(new ToursController());
		this.getController().setToursService(getToursService());
		this.setRequest(new MockHttpServletRequest(servletContext));
		this.setResponse(new MockHttpServletResponse());
		this.setUiModel(new ExtendedModelMap());
		this.getRequest().setSession(new MockHttpSession());
		User user = new UserImpl();
		user.setLoginName(USER);
		this.getRequest().getSession().setAttribute(Constants.KME_USER_KEY, user);
	}

	@Test
	public void testIndex() {
		Tour testTour1 = new Tour();
		testTour1.setName("Tour1");
		Tour testTour2 = new Tour();
		testTour2.setName("Tour2");
		List<Tour> tours = new ArrayList<Tour>();
		tours.add(testTour1);
		tours.add(testTour2);

		when(toursService.findAllTours()).thenReturn(tours);
		when(toursService.hasAccessToViewTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().index(getRequest(), getUiModel());
		assertTrue("did not return \"tours/home\" but instead" + viewName, "tours/home".equalsIgnoreCase(viewName));
	}

	@Test
	public void testPublish() {
		Tour testTour1 = new Tour();
		testTour1.setName("Tour1");
		Tour testTour2 = new Tour();
		testTour2.setName("Tour2");
		List<Tour> tours = new ArrayList<Tour>();
		tours.add(testTour1);
		tours.add(testTour2);
		POI testPOI1 = new POI();
		testPOI1.setName("POI1");
		POI testPOI2 = new POI();
		testPOI2.setName("POI2");
		List<POI> POIs = new ArrayList<POI>();
		POIs.add(testPOI1);
		POIs.add(testPOI2);

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().publish(getRequest(), getUiModel());
		assertTrue("did not return \"tours/message\" but instead" + viewName, "tours/message".equalsIgnoreCase(viewName));

		when(toursService.findAllTours()).thenReturn(tours);
		when(toursService.findAllCommonPOI()).thenReturn(POIs);
		when(toursService.hasAccessToEditTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);
		when(toursService.hasAccessToEditPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		viewName = getController().publish(getRequest(), getUiModel());
		assertTrue("did not return \"tours/index\" but instead" + viewName, "tours/index".equalsIgnoreCase(viewName));
	}

	@Test
	public void testViewTour() {
		Tour tour = new Tour();
		tour.setName("testTour");
		POI poi1 = new POI();
		poi1.setName("testPOI1");
		List<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		Tour tourWithPOI = new Tour();
		tourWithPOI.setName("testTourPOI");
		tourWithPOI.setPointsOfInterest(pois);
		Tour tourWithFbText = new Tour();
		tourWithFbText.setFbText1("test1");
		tourWithFbText.setFbText2("test2");

		when(toursService.findTourById(any(Long.class))).thenReturn(tour).thenReturn(null).thenReturn(tour).thenReturn(tourWithFbText);
		when(toursService.hasAccessToViewTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().viewTour(getRequest(), getUiModel(), 0);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().viewTour(getRequest(), getUiModel(), 0);
		assertTrue("did not return \"tours/tour\" but instead" + viewName, "tours/tour".equalsIgnoreCase(viewName));
		assertTrue("tour attribute should be null", getUiModel().asMap().get("tour") == null);
		viewName = getController().viewTour(getRequest(), getUiModel(), 0);
		assertTrue("did not return \"tours/tour\" but instead " + viewName, "tours/tour".equalsIgnoreCase(viewName));
		assertTrue("tour attribute should not be null", getUiModel().asMap().get("tour") != null);
	}

	@Test
	public void testViewPoiDetails() {
		POI poiAllNull = new POI();
		POI poi = new POI();
		poi.setTourId(1L);
		poi.setMedia("testMedia");
		POIPhoneNumber number1 = new POIPhoneNumber();
		number1.setNumber("8125555007");
		Set<POIPhoneNumber> numbers = new HashSet<POIPhoneNumber>();
		numbers.add(number1);
		poi.setPhoneNumbers(numbers);

		when(toursService.findPoiById(any(Long.class))).thenReturn(null).thenReturn(poiAllNull).thenReturn(poi);
		when(toursService.hasAccessToViewTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);
		when(toursService.findPoiByOrder(any(Long.class), any(Integer.class))).thenReturn(null).thenReturn(null).thenReturn(poi);

		String viewName = getController().viewPoiDetails(getRequest(), getUiModel(), 1L);
		assertTrue("tour attribute should be null", getUiModel().asMap().get("poi") == null);
		viewName = getController().viewPoiDetails(getRequest(), getUiModel(), 1L);
		assertTrue("tour attribute should be not be null", getUiModel().asMap().get("poi") != null);
		viewName = getController().viewPoiDetails(getRequest(), getUiModel(), 1L);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().viewPoiDetails(getRequest(), getUiModel(), 1L);
		assertTrue("did not return \"tours/details\" but instead " + viewName, "tours/details".equalsIgnoreCase(viewName));
	}

	@Test
	public void testViewTourMap() {
		Tour tour = new Tour();
		POIPermission poiPermission = new POIPermission();
		Set<POIPermission> poiPermissions = new HashSet<POIPermission>();
		poiPermissions.add(poiPermission);
		POI poi1 = new POI();
		poi1.setTour(tour);
		poi1.setVersionNumber(0L);
		poi1.setTourId(0L);
		poi1.setPermissions(poiPermissions);

		POI poi2 = new POI();
		poi2.setMedia("testMedia");
		poi2.setTour(tour);
		poi2.setVersionNumber(0L);
		poi2.setTourId(0L);
		poi2.setPermissions(poiPermissions);

		POIPhoneNumber number1 = new POIPhoneNumber();
		number1.setNumber("8125555007");
		Set<POIPhoneNumber> numbers = new HashSet<POIPhoneNumber>();
		numbers.add(number1);
		poi1.setPhoneNumbers(numbers);
		poi2.setPhoneNumbers(numbers);
		List<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);

		tour.setPointsOfInterest(pois);
		TourPermission permission = new TourPermission();
		Set<TourPermission> permissions = new HashSet<TourPermission>();
		permissions.add(permission);
		tour.setPermissions(permissions);

		when(toursService.findTourById(any(Long.class))).thenReturn(tour);
		when(toursService.hasAccessToViewTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().viewTourMap(getRequest(), getUiModel(), 0);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().viewTourMap(getRequest(), getUiModel(), 0);
		assertTrue("tourJson attribute should not be null", getUiModel().asMap().get("tourJson") != null);
		assertTrue("did not return \"tours/map\" but instead " + viewName, "tours/map".equalsIgnoreCase(viewName));
	}

	@Test
	public void testEdit() {
		Tour tour = new Tour();
		tour.setTourId(0L);
		TourPermission permission = new TourPermission();
		permission.setTour(tour);
		permission.setTourId(tour.getTourId());
		permission.setPermissionId(0L);
		Set<TourPermission> permissions = new HashSet<TourPermission>();
		permissions.add(permission);
		tour.setPermissions(permissions);

		POIPhoneNumber number1 = new POIPhoneNumber();
		number1.setPoi(new POI());
		number1.setPoiId(0L);
		number1.setNumber("8125555007");
		Set<POIPhoneNumber> numbers = new HashSet<POIPhoneNumber>();
		numbers.add(number1);

		POIPermission poiPermission = new POIPermission();
		Set<POIPermission> poiPermissions = new HashSet<POIPermission>();
		poiPermissions.add(poiPermission);

		POI poi1 = new POI();
		poi1.setTour(tour);
		poi1.setVersionNumber(0L);
		poi1.setTourId(tour.getTourId());
		poi1.setPermissions(poiPermissions);
		POI poi2 = new POI();
		poi2.setTour(tour);
		poi2.setVersionNumber(0L);
		poi2.setTourId(tour.getTourId());
		poi2.setPermissions(poiPermissions);
		POI poi3 = new POI();
		poi3.setTour(tour);
		poi3.setVersionNumber(0L);
		poi3.setTourId(tour.getTourId());
		poi3.setPermissions(poiPermissions);
		poi3.setMedia("testMedia3");
		List<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);
		pois.add(poi3);

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findAllCommonPOI()).thenReturn(pois);
		when(toursService.hasAccessToViewPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().edit(getRequest(), getUiModel());
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().edit(getRequest(), getUiModel());
		assertTrue("did not return \"tours/edit\" but instead " + viewName, "tours/edit".equalsIgnoreCase(viewName));
		assertTrue("pois attribute should not be null", getUiModel().asMap().get("pois") != null);
	}

	@Test
	public void testEditTour() {
		Tour emptyTour = new Tour();
		Tour tour = new Tour();
		TourPermission permission = new TourPermission();
		permission.setTour(tour);
		permission.setTourId(tour.getTourId());
		permission.setPermissionId(0L);
		Set<TourPermission> permissions = new HashSet<TourPermission>();
		permissions.add(permission);
		tour.setPermissions(permissions);

		POIPhoneNumber number1 = new POIPhoneNumber();
		number1.setPoi(new POI());
		number1.setPoiId(0L);
		number1.setNumber("8125555007");
		Set<POIPhoneNumber> numbers = new HashSet<POIPhoneNumber>();
		numbers.add(number1);

		POIPermission poiPermission = new POIPermission();
		Set<POIPermission> poiPermissions = new HashSet<POIPermission>();
		poiPermissions.add(poiPermission);

		POI poi1 = new POI();
		poi1.setTour(tour);
		poi1.setVersionNumber(0L);
		poi1.setTourId(tour.getTourId());
		poi1.setPermissions(poiPermissions);
		POI poi2 = new POI();
		poi2.setTour(tour);
		poi2.setVersionNumber(0L);
		poi2.setTourId(tour.getTourId());
		poi2.setPermissions(poiPermissions);
		poi2.setMedia("testMedia2");
		POI poi3 = new POI();
		poi3.setTour(tour);
		poi3.setVersionNumber(0L);
		poi3.setTourId(tour.getTourId());
		poi3.setPermissions(poiPermissions);
		List<POI> pois = new ArrayList<POI>();
		pois.add(poi1);
		pois.add(poi2);
		pois.add(poi3);
		tour.setPointsOfInterest(pois);

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findTourById(any(Long.class))).thenReturn(emptyTour).thenReturn(emptyTour).thenReturn(tour);
		when(toursService.hasAccessToEditTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);
		when(toursService.findAllCommonPOI()).thenReturn(pois);
		when(toursService.hasAccessToViewPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().editTour(getUiModel(), getRequest(), 0);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().editTour(getUiModel(), getRequest(), 0);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().editTour(getUiModel(), getRequest(), 0);
		assertTrue("tourJson attribute should be null", getUiModel().asMap().get("tourJson") == null);
		viewName = getController().editTour(getUiModel(), getRequest(), 0);
		assertTrue("pois attribute should not be null", getUiModel().asMap().get("pois") != null);
		assertTrue("did not return \"tours/edit\" but instead " + viewName, "tours/edit".equalsIgnoreCase(viewName));
	}

	@Test
	public void testSave() {
		Tour tour = new Tour();

		String stringTour = "{\"id\":\"1L\"," +
				"\"name\":\"testName\"," +
				"\"description\":\"test description\"," +
				"\"imageUrl\":\"testImageUrl\"," +
				"\"path\":\"testPath\"," +
				"\"tweetText1\":\"testTweetText1\"," +
				"\"tweetText2\":\"testTweetText2\"," +
				"\"fbText1\":\"testFbText1\"," +
				"\"fbText2\":\"testFbText2\"," +
				"\"tweetText1Enabled\":\"true\"," +
				"\"tweetText2Enabled\":\"true\"," +
				"\"fbText1Enabled\":\"true\"," +
				"\"fbText2Enabled\":\"true\"," +
				"\"permissions\":[{\"group\":\"testGroup\",\"type\":\"testType\"}]," +
				"\"POIs\":[]}";

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findTourById(any(Long.class))).thenReturn(null).thenReturn(tour);
		String viewName = getController().save(getRequest(), stringTour, getUiModel());
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().save(getRequest(), stringTour, getUiModel());
		assertTrue("did not return \"redirect:/tours/publish\" but instead " + viewName, "redirect:/tours/publish".equalsIgnoreCase(viewName));
	}

	@Test
	public void testDelete() {
		Tour tour = new Tour();

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findTourById(any(Long.class))).thenReturn(tour);
		when(toursService.hasAccessToEditTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().delete(getUiModel(), getRequest(), 0L);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().delete(getUiModel(), getRequest(), 0L);
		assertTrue("should have entered the has access to edit tour if statement", "You do not have access to delete this tour.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().delete(getUiModel(), getRequest(), 0L);
		assertTrue("did not return \"redirect:/tours/publish\" but instead " + viewName, "redirect:/tours/publish".equalsIgnoreCase(viewName));
	}

	@Test
	public void testCopy() {
		Tour tour = new Tour();

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findTourById(any(Long.class))).thenReturn(tour);
		when(toursService.hasAccessToEditTour(any(User.class), any(Tour.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().copy(getUiModel(), getRequest(), 0L);
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().copy(getUiModel(), getRequest(), 0L);
		assertTrue("should have entered the has access to edit tour if statement", "You do not have access to delete this tour.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().copy(getUiModel(), getRequest(), 0L);
		assertTrue("did not return \"redirect:/tours/publish\" but instead " + viewName, "redirect:/tours/publish".equalsIgnoreCase(viewName));
	}

	@Test
	public void testEditPoi() {

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().editPoi(getUiModel(), getRequest());
		assertTrue("did not return \"tours/message\" but instead " + viewName, "tours/message".equalsIgnoreCase(viewName));
		viewName = getController().editPoi(getUiModel(), getRequest());
		assertTrue("should have entered the has access to edit tour if statement", "You do not have access to create or edit tours.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
	}

	@Test
	public void testEditPoi2() {
		POIPhoneNumber number1 = new POIPhoneNumber();
		number1.setPoi(new POI());
		number1.setPoiId(0L);
		number1.setNumber("8125555007");
		Set<POIPhoneNumber> numbers = new HashSet<POIPhoneNumber>();
		numbers.add(number1);

		POIPermission poiPermission = new POIPermission();
		poiPermission.setPoi(new POI());
		poiPermission.setPoiId(0L);
		Set<POIPermission> poiPermissions = new HashSet<POIPermission>();
		poiPermissions.add(poiPermission);

		Tour tour = new Tour();
		tour.setTourId(1L);

		POI poi = new POI();
		poi.setTour(tour);
		poi.setTourId(tour.getTourId());
		poi.setPhoneNumbers(numbers);
		poi.setPermissions(poiPermissions);
		poi.setMedia("testMedia");
		POI poiNoMedia = new POI();
		poiNoMedia.setTour(tour);
		poiNoMedia.setTourId(tour.getTourId());
		poiNoMedia.setPhoneNumbers(numbers);
		poiNoMedia.setPermissions(poiPermissions);

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findPoiById(any(Long.class))).thenReturn(null).thenReturn(poiNoMedia).thenReturn(poi);
		when(toursService.hasAccessToEditPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().editPoi(getRequest(), getUiModel(), 0L);
		assertTrue("should have entered the has access to publish", "You do not have access to create or edit tours.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().editPoi(getRequest(), getUiModel(), 0L);
		assertTrue("should have entered the has access to edit POI if statement", "You do not have access to edit this point of interest.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().editPoi(getRequest(), getUiModel(), 0L);
		viewName = getController().editPoi(getRequest(), getUiModel(), 0L);
		assertTrue("should have returned tours/editPoi", "tours/editPoi".equalsIgnoreCase(viewName));
	}

	@Test
	public void testSavePoi() {
		POI poi = new POI();
		String jsonString = "{\"id\":1L," +
				"\"fbLikeButtonEnabled\":\"false\"," +
				"\"type\":\"testType\"," +
				"\"permissions\":[{\"group\":\"testGroup\",\"type\":\"testType\"}]," +
				"\"phoneNumbers\":[]," +
				"\"emailAddresses\":[]," +
				"\"location\":{\"latitude\":\"0\",\"longitude\":\"0\"}," +
				"\"media\":\"testMedia\"}";

		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.findPoiById(any(Long.class))).thenReturn(poi);
		when(toursService.hasAccessToEditPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().savePoi(getRequest(), jsonString, getUiModel());
		assertTrue("should have entered the has access to publish", "You do not have access to create or edit tours.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().savePoi(getRequest(), jsonString, getUiModel());
		assertTrue("should have entered the has access to edit POI if statement", "You do not have access to edit this point of interest.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().savePoi(getRequest(), jsonString, getUiModel());
		assertTrue("did not return redirect:/tours/publish but instead " + viewName, "redirect:/tours/publish".equalsIgnoreCase(viewName));
	}

	@Test
	public void testDeletePoi() {
		when(toursService.hasAccessToPublish(any(User.class))).thenReturn(false).thenReturn(true);
		when(toursService.hasAccessToEditPOI(any(User.class), any(POI.class))).thenReturn(false).thenReturn(true);

		String viewName = getController().deletePoi(getRequest(), 0L, getUiModel());
		assertTrue("should have been false for hasAccessToPublish", "You do not have access to create or edit tours.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().deletePoi(getRequest(), 0L, getUiModel());
		assertTrue("should have been false for hasAccessToEditPOI", "You do not have access to delete this point of interest.".equalsIgnoreCase(getUiModel().asMap().get("message").toString()));
		viewName = getController().deletePoi(getRequest(), 0L, getUiModel());
		assertTrue("did not return redirect:/tours/publish but instead " + viewName, "redirect:/tours/publish".equalsIgnoreCase(viewName));
	}

	public ToursService getToursService() {
		return toursService;
	}

	public void setToursService(ToursService toursService) {
		this.toursService = toursService;
	}

	public ToursController getController() {
		return controller;
	}

	public void setController(ToursController controller) {
		this.controller = controller;
	}

	public MockHttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(MockHttpServletRequest request) {
		this.request = request;
	}

	public MockHttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(MockHttpServletResponse response) {
		this.response = response;
	}

	public Model getUiModel() {
		return uiModel;
	}

	public void setUiModel(Model uiModel) {
		this.uiModel = uiModel;
	}
}
