/*
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


var arrows;
var markers = [];
var startMarker, stopMarker;
var preDefinedPois = [];
var poly;

var validationDistanceThresholdMeters = 100;

var editingPoiIndex;

$(function () {
    $('.poiCancel').hide();
});


function initializeMap(cntxtPath) {
    contextPath = cntxtPath;
    var latlng;
    var zoom = 15;
    latlng = new google.maps.LatLng(39.168486, -86.523455);

    var myOptions = {
        zoom: zoom,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.HYBRID,
        streetViewControl: true,
        zoomControl: true,
        scaleControl: false,
        mapTypeControl: false,
        disableDoubleClickZoom: false
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    var polyOptions = {
        strokeColor: '#0000FF',
        strokeOpacity: 0.5,
        strokeWeight: 4,
        clickable: false
    }
    poly = new google.maps.Polyline(polyOptions);
    poly.setMap(map);
    /*
     google.maps.event.addListener(poly, 'mouseover', function() {
     fineTuneRoute();
     });
     google.maps.event.addListener(poly, 'mouseout', function() {
     stopFineTuning();
     });
     */

    arrows = new ArrowHandler();

    google.maps.event.addListener(map, 'click', selectPoint);

    mapService = new gmaps.ags.MapService(arcGisServerUrl);
    var agsType = new gmaps.ags.MapType(arcGisServerUrl, {name: 'ArcGIS', opacity: 1.0});
    map.overlayMapTypes.insertAt(0, agsType);
    google.maps.event.addListenerOnce(mapService, 'load', function () {
        $('#findButton').removeAttr('disabled');
    });

    google.maps.event.addListenerOnce(map, "tilesloaded", function () {
        initializeTour();
    });

    iw = new google.maps.InfoWindow();
}

function initializeTour() {
    var tourJson = $('#tourJson').val();
    if (tourJson.length) {
        var obj = jQuery.parseJSON(tourJson);
        if (obj) {
            $('#tourId').val(obj.tourId);
            $('#tourName').val(obj.name);
            $('#tourDescription').val(obj.description);
            $('#tourThumbnailUrl').val(obj.imageUrl);
            $('#tourVersion').val(obj.versionNumber);
            $('#tweetText1').val(obj.tweetText1);
            $('#tweetText2').val(obj.tweetText2);
            $('#fbText1').val(obj.fbText1);
            $('#fbText2').val(obj.fbText2);

            if (obj.tweetText1Enabled == 'T') {
                $('#tweetText1').removeAttr('disabled');
                $('input[name="tweetText1Enabled"]').attr('checked', 'checked');
            } else {
                $('#tweetText1').attr('disabled', 'disabled');
                $('input[name="tweetText1Enabled"]').removeAttr('checked')
            }
            if (obj.tweetText2Enabled == 'T') {
                $('#tweetText2').removeAttr('disabled');
                $('input[name="tweetText2Enabled"]').attr('checked', 'checked');
            } else {
                $('#tweetText2').attr('disabled', 'disabled');
                $('input[name="tweetText2Enabled"]').removeAttr('checked')
            }
            if (obj.fbText1Enabled == 'T') {
                $('#fbText1').removeAttr('disabled');
                $('input[name="fbText1Enabled"]').attr('checked', 'checked');
            } else {
                $('#fbText1').attr('disabled', 'disabled');
                $('input[name="fbText1Enabled"]').removeAttr('checked')
            }
            if (obj.fbText2Enabled == 'T') {
                $('#fbText2').removeAttr('disabled');
                $('input[name="fbText2Enabled"]').attr('checked', 'checked');
            } else {
                $('#fbText2').attr('disabled', 'disabled');
                $('input[name="fbText2Enabled"]').removeAttr('checked')
            }


            poly.setPath(google.maps.geometry.encoding.decodePath(obj.path));
            updateStartEndMarkers();
            updatePathDistance();

            for (var i = 0; i < obj.pointsOfInterest.length; i++) {
                var poi = obj.pointsOfInterest[i];
                place = new Object();
                place.name = poi.name;
                place.officialName = poi.officialName;
                place.location = new google.maps.LatLng(poi.latitude, poi.longitude);
                //place.iuBuildingCode = poi.locationId; - this is the way it should be done
                //place.venueId = poi.venueId;
                place.type = poi.type;
                if (place.type == iuBuildingType) {
                    place.iuBuildingCode = poi.locationId;
                } else if (place.type == venueType) {
                    place.venueId = poi.locationId;
                }
                place.media = poi.media;
                place.description = poi.description;
                place.shortDescription = poi.shortDescription;
                place.url = poi.url;
                place.thumbnailUrl = poi.thumbnailUrl;
                place.fbLikeUrl = poi.fbLikeUrl;
                place.fbLikeButtonEnabled = poi.fbLikeButtonEnabled == 'T';
                if (poi.phoneNumbers) {
                    place.phoneNumbers = new Array();
                    for (var p = 0; p < poi.phoneNumbers.length; p++) {
                        var number = poi.phoneNumbers[p];
                        var phoneNumber = new Object();
                        phoneNumber.value = number.number;
                        phoneNumber.name = number.name;
                        place.phoneNumbers.push(phoneNumber);
                    }
                }
                if (poi.emailAddresses) {
                    place.emailAddresses = new Array();
                    for (var p = 0; p < poi.emailAddresses.length; p++) {
                        var email = poi.emailAddresses[p];
                        var emailAddress = new Object();
                        emailAddress.address = email.address;
                        emailAddress.name = email.name;
                        place.emailAddresses.push(emailAddress);
                    }
                }
                addPOI(place);
            }
            centerOverAllLocations();
            arrows.load(poly.getPath().getArray());

            for (var i = 0; i < obj.permissions.length; i++) {
                var permission = obj.permissions[i];
                addPermissionGroup(permission.groupName, permission.type == 'E');
            }
        }
    }
    var pois = $('#definedPoisJson').val();
    if (pois.length) {
        var obj = jQuery.parseJSON(pois);
        if (obj) {
            for (var i = 0; i < obj.length; i++) {
                var poi = obj[i];
                place = new Object();
                place.name = poi.name;
                place.officialName = poi.officialName;
                place.location = new google.maps.LatLng(poi.latitude, poi.longitude);
                place.iuBuildingCode = poi.locationId;
                place.venueId = poi.venueId;
                place.type = poi.type;
                place.media = poi.media;
                place.description = poi.description;
                place.shortDescription = poi.shortDescription;
                place.url = poi.url;
                place.thumbnailUrl = poi.thumbnailUrl;
                place.fbLikeUrl = poi.fbLikeUrl;
                place.fbLikeButtonEnabled = poi.fbLikeButtonEnabled == 'T';
                if (poi.phoneNumbers) {
                    place.phoneNumbers = new Array();
                    for (var p = 0; p < poi.phoneNumbers.length; p++) {
                        var number = poi.phoneNumbers[p];
                        var phoneNumber = new Object();
                        phoneNumber.value = number.number;
                        phoneNumber.name = number.name;
                        place.phoneNumbers.push(phoneNumber);
                    }
                }
                if (poi.emailAddresses) {
                    place.emailAddresses = new Array();
                    for (var p = 0; p < poi.emailAddresses.length; p++) {
                        var email = poi.emailAddresses[p];
                        var emailAddress = new Object();
                        emailAddress.address = email.address;
                        emailAddress.name = number.name;
                        place.emailAddresses.push(emailAddress);
                    }
                }
                preDefinedPois[i] = place;
                $('#definedPOIs').append('<div onclick="selectPreDefinedPoi(' + i + ')" onmouseover="this.style.backgroundColor=\'#AAAAEE\'" onmouseout="this.style.backgroundColor=\'#FFFFFF\'">' + place.name + '</div>');
            }
        }
    }
}

function centerOverAllLocations() {
    var bounds = new google.maps.LatLngBounds();
    if (markers) {
        for (i in markers) {
            bounds.extend(markers[i].getPosition());
        }
    }
    if (poly) {
        var array = poly.getPath().getArray();
        for (i in array) {
            bounds.extend(array[i]);
        }
    }
    map.fitBounds(bounds);
}

function addToRoute() {
    var place;
    if (editingPoiIndex != null) {
        var marker = markers[editingPoiIndex];
        if (tempMarker) {
            place = tempMarker.place;
        } else {
            place = marker.place;
        }
        marker.setPosition(place.location);
        marker.place = place;
        marker.title = place.name;
    } else {
        if (tempMarker) {
            place = tempMarker.place;
        }
    }
    if (place) {
        var pointName = $('#poiName').val();
        var pointOfficialName = $('#poiOfficialName').val();
        if (!pointName.length || !pointOfficialName.length) {
            alert('Please name this location.');
            return;
        }
        if (pointName.length > 256 || pointOfficialName.length > 256) {
            alert('Location names must be less than or equal to 256 characters.');
            return;
        }
        var latitude = $('#latitude').val();
        var longitude = $('#longitude').val();
        if (!(latitude && longitude && !(isNaN(latitude) || isNaN(longitude)))) {
            alert('Please ensure proper latitude and longitude coordinates are provided.');
            return;
        }
        place.name = pointName;
        place.officialName = pointOfficialName;
        place.location = new google.maps.LatLng(latitude, longitude);
        place.description = $('#description').val();
        place.shortDescription = $('#shortDescription').val();
        place.url = $('#url').val();
        place.thumbnailUrl = $('#thumbnailUrl').val();
        if ($('#fbLikeButtonEnabled').attr('checked') && !$('#fbLikeUrl').val()) {
            alert('A URL for the Like Button is required.');
            return;
        }
        place.fbLikeUrl = $('#fbLikeUrl').val();
        place.fbLikeButtonEnabled = $('#fbLikeButtonEnabled').attr('checked') ? true : false;
        addSelectedMediaToPoi(place);
        addPhoneNumbersToPoi(place);
        addEmailAddressesToPoi(place);
        clearSelectedMedia();
        if (editingPoiIndex == null || editingPoiIndex == undefined) {
            addPOI(place);
        }
        if (tempMarker) {
            tempMarker.setMap(null);
            tempMarker = null;
        }
        stopEditingPoi();
    } else {
        alert('You must select a place to add/save.');
    }
}

function selectPoint(event) {
    if (!isFineTuning && isEditing) {
        addWaypoint(event.latLng);
    }

    if (isSelectingPoint) {
        updateSelectedPlace(event.latLng);
    }

    if (isSelectingVenue) {
        if (tempMarker) {
            tempMarker.setMap(null);
        }
        findFoursquareVenues(event.latLng);
    }
}

function selectPreDefinedPoi(index) {
    var place = preDefinedPois[index];
    setTempMarker(place);
    stopEditingMedia();
    clearSelectedMedia();
    for (var i = 0; i < place.media.length; i++) {
        var media = place.media[i];
        if (media.type == imageType) {
            loadImageToList(media.url, media.title, media.caption);
        } else if (media.type == audioType) {
            loadAudioToList(media.oggVorbisUrl, media.mp3Url, media.wavUrl, media.title, media.caption);
        } else if (media.type == videoType) {
            loadVideoToList(media.oggUrl, media.mp4Url, media.webMUrl, media.youTubeUrl, media.title, media.caption);
        }
    }
    map.panTo(place.location);
}

function updateSelectedPlace(latLng) {
    geocodeLatLng(latLng, setAddress);
}

function selectBuilding(number) {
    if (number > 0) {
        var place = places[number - 1]; // 'select:' is index 0, so number 1 = places[0]
        setTempMarker(place)
        map.panTo(place.location);
    }
}

function setTempMarker(place) {
    if (!tempMarker) {
        tempMarker = new google.maps.Marker({
            position: place.location,
            map: map,
            draggable: false,
            icon: tempMarkerImage
        });
    } else {
        tempMarker.setPosition(place.location);
        tempMarker.setMap(map);
    }
    tempMarker.setTitle(place.name);
    tempMarker.place = place;
    $('#latitude').val(place.location.lat());
    $('#longitude').val(place.location.lng());
    $('#poiName').val(place.name);
    $('#poiOfficialName').val(place.officialName);
    $('#url').val(place.url);
    $('#thumbnailUrl').val(place.thumbnailUrl);
    $('#fbLikeUrl').val(place.fbLikeUrl);
    $('#description').val(place.description);
    $('#shortDescription').val(place.shortDescription);
    if (place.fbLikeButtonEnabled) {
        $('#fbLikeButtonEnabled').attr('checked', 'checked');
    } else {
        $('#fbLikeButtonEnabled').removeAttr('checked');
    }
    populatePhoneNumbers(place.phoneNumbers);
    populateEmailAddresses(place.emailAddresses);
}

function addBuilding(number) {
    if (number > 0) {
        var place = places[number - 1]; // 'select:' is index 0, so number 1 = places[0]
        addWaypoint(place.location, place);
    }
}

function addWaypoint(latLng) {
    var path = poly.getPath();
    var currentEnd = path.getAt(path.getLength() - 1);
    // Because path is an MVCArray, we can simply append a new coordinate
    // and it will automatically appear
    path.push(latLng);

    updateStartEndMarkers();
    updatePathDistance();

    arrows.create(currentEnd, latLng);
}

function addPOI(place) {
    // Add a new marker at the new plotted point on the polyline.
    var markerMap = map;

    var marker = new google.maps.Marker({
        position: place.location,
        title: place.name,
        map: markerMap,
        draggable: false,
        icon: buildingMarkerImage
    });

    marker.place = place;
    markers.push(marker);

    google.maps.event.addListener(marker, 'rightclick', function () {
        removeBuilding(marker);
    });
    google.maps.event.addListener(marker, 'click', function () {
        if (!isEditing && !isFineTuning) {
            editPOI(marker);
        }
        if (!isFineTuning && isEditing) {
            addWaypoint(marker.position);
        }
    });
}

function searchAddress() {
    var address = $('#address').val();
    if (address.length) {
        geocodeAddress(address, setAddress);
    } else {
        alert('Please enter a valid address.');
    }
}

function setAddress(place) {
    setTempMarker(place);
    map.panTo(place.location);
}

function updatePathDistance() {
    var dist = google.maps.geometry.spherical.computeLength(poly.getPath().getArray());
    var kilometers = dist / 1000;
    dist = kilometers * 0.621371192; //convert km to miles.
    $('#routeDistanceMi').html(dist.toFixed(2) + ' Miles');
    $('#routeDistanceKm').html(kilometers.toFixed(2) + ' Kilometers');
}

function startEditingRoute() {
    changeMode('edit');
    $('#editStatus').text('Editing Route');
}

function stopEditingRoute() {
    changeMode();
    $('#editStatus').text('Not Editing');
}

function fineTuneRoute() {
    if (!isFineTuning) {
        changeMode('tune');
        $('#editStatus').text('Fine-Tuning');
        arrows.hideArrows();
        poly.runEdit(true);
        if (startMarker) {
            startMarker.setMap(null);
        }
        if (stopMarker) {
            stopMarker.setMap(null);
        }
    }
}

function stopFineTuning() {
    poly.stopEdit();
    updateStartEndMarkers();
    arrows.load(poly.getPath().getArray());
    changeMode();
    $('#editStatus').text('Not Editing');
    updatePathDistance();
}

function updateStartEndMarkers() {
    var path = poly.getPath();
    if (path.getLength() > 0) {
        if (!startMarker) {
            var markerImage = new google.maps.MarkerImage(contextPath + '/images/start.png');
            startMarker = new google.maps.Marker({
                position: path.getAt(0),
                title: 'Start',
                map: map,
                draggable: false,
                icon: markerImage
            });
        } else {
            startMarker.setPosition(path.getAt(0));
            startMarker.setMap(map);
        }
    }

    if (path.getLength() > 1) {
        if (!stopMarker) {
            var markerImage = new google.maps.MarkerImage(contextPath + '/images/end.png');
            stopMarker = new google.maps.Marker({
                position: path.getAt(path.getLength() - 1),
                title: 'End',
                map: map,
                draggable: false,
                icon: markerImage
            });
        } else {
            stopMarker.setPosition(path.getAt(path.getLength() - 1));
            stopMarker.setMap(map);
        }
    }
}

function removeBuilding(marker) {
    var index = markers.indexOf(marker);
    if (index != null) {
        markers.splice(index, 1);
        //poly.getPath().removeAt(index);
    }
    marker.setMap(null);
    marker = null;

    updateSelectedPOIs();
}

function saveTour() {
    //Removing validation due to complaints.
    if (validateTour()) {
        var places = [];
        for (var i = 0; i < markers.length; i++) {
            var place = markers[i].place;
            if (place.poly) {
                delete place.poly;
            }

            var gMapsLocation = place.location;
            var location = new Object();
            location.lat = gMapsLocation.lat();
            location.lng = gMapsLocation.lng();
            place.location = location;
            place.order = i;

            if (i + 1 < markers.length) {
                var nextLocation = markers[i + 1].place.location;
                place.distanceToNext = google.maps.geometry.spherical.computeDistanceBetween(gMapsLocation, nextLocation);
            } else {
                place.distanceToNext = null;
            }

            places.push(place);
        }

        var tour = new Object();
        tour.id = $('#tourId').val();
        tour.name = $('#tourName').val();
        tour.description = $('#tourDescription').val();
        tour.imageUrl = $('#tourThumbnailUrl').val();
        tour.version = $('#tourVersion').val();
        tour.tweetText1 = $('#tweetText1').val();
        tour.tweetText2 = $('#tweetText2').val();
        tour.fbText1 = $('#fbText1').val();
        tour.fbText2 = $('#fbText2').val();
        tour.tweetText1Enabled = $('input[name="tweetText1Enabled"]').attr('checked') ? true : false;
        tour.tweetText2Enabled = $('input[name="tweetText2Enabled"]').attr('checked') ? true : false;
        tour.fbText1Enabled = $('input[name="fbText1Enabled"]').attr('checked') ? true : false;
        tour.fbText2Enabled = $('input[name="fbText2Enabled"]').attr('checked') ? true : false;
        tour.POIs = places;

        if (poly.getPath()) {
            tour.path = google.maps.geometry.encoding.encodePath(poly.getPath());
            tour.distance = google.maps.geometry.spherical.computeLength(poly.getPath().getArray());
        } else {
            tour.path = null;
            tour.distance = null;
        }

        var permissions = [];
        $('#viewPermissionsList li').each(function (index, element) {
            var group = $('span.group', element).text();
            var permission = new Object();
            permission.group = group;
            permission.type = 'V';
            permissions.push(permission);
        });
        $('#editPermissionsList li').each(function (index, element) {
            var group = $('span.group', element).text();
            var permission = new Object();
            permission.group = group;
            permission.type = 'E';
            permissions.push(permission);
        });
        tour.permissions = permissions;

        $('#data').val(JSON.stringify(tour));
        $("#postForm").submit();
    }
}

function validateTour() {
    if (!$('#tourName').val().length) {
        alert('The tour must have a name.');
        return false;
    }
    if (!markers || markers.length == 0) {
        alert('You have not added any points of interest to the tour.');
        return false;
    }
    /*if (poly.getPath().getLength() == 0){
     alert('You have not created a route for the tour.');
     return false;
     } else if (poly.getPath().getLength() < 2){
     alert('A tour route should have at least two waypoints.');
     return false;
     }
     var distance = 0;
     var buildings = '';
     for (var i = 0; i < markers.length; i++) {
     distance = getDistanceToPolyMtrs(poly, markers[i].getPosition())
     if (distance > validationDistanceThresholdMeters){
     if (buildings != ''){
     buildings += ', ';
     }
     buildings += markers[i].place.name;
     }
     }
     if (buildings.length){
     if (confirm("The route does not pass near these points of interest:\n" + buildings + "\nDo you want to save anyway?")) {
     return true;
     }
     return false;
     }*/

    /*if (!$('#editPermissionsList li').length) {
     alert('You must include at least one ADS group with editing permissions.');
     return false;
     }*/
    return true;
}

function changeWizardTabs(event, ui) {
    clearSearchResults();
    if (isEditing) {
        stopEditingRoute();
    }
    if (editingPoiIndex != null) {
        stopEditingPoi();
    }
    if (isFineTuning) {
        stopFineTuning();
    }

    updateSelectedPOIs();
}

function changeSelectorTabs(event, ui) {
    clearSearchResults();
    var $tabs = $('#selector').tabs();
    var selected = $tabs.tabs('option', 'selected');
    if (selected == 2) { //Select A Point is the second tab
        changeMode('select');
    } else if (selected == 3) { //Select A Foursquare Venue is the third tab
        changeMode('venue');
    } else {
        changeMode();
    }
    selectedPlace = null;
    if (tempMarker) {
        tempMarker.setMap(null);
    }
}

function changeMode(mode) {
    if (mode === 'edit') {
        isEditing = true;
        isFineTuning = false;
        isSelectingPoint = false;
        isSelectingVenue = false;
    } else if (mode === 'tune') {
        isEditing = false;
        isFineTuning = true;
        isSelectingPoint = false;
        isSelectingVenue = false;
    } else if (mode === 'select') {
        isEditing = false;
        isFineTuning = false;
        isSelectingPoint = true;
        isSelectingVenue = false;
    } else if (mode === 'venue') {
        isEditing = false;
        isFineTuning = false;
        isSelectingPoint = false;
        isSelectingVenue = true;
    } else {
        isEditing = false;
        isFineTuning = false;
        isSelectingPoint = false;
        isSelectingVenue = false;
    }
}

function updateSelectedPOIs() {
    var $tabs = $('#wizard').tabs();
    var selected = $tabs.tabs('option', 'selected');
    if (selected == 4) { //Save is the fifth tab
        var list = $('<ol></ol>');
        for (var i = 0; i < markers.length; i++) {
            var li = $('<li></li>');
            li.append(markers[i].place.name);

            var buttons = $('<div></div>');
            buttons.css('float', 'right');
            buttons.css('margin-right', '5px');

            var upButton = $('<span></span>');
            upButton.html('&nbsp;&uarr;&nbsp;');
            upButton.css('cursor', 'pointer');
            upButton.addClass('upButton');
            upButton.click(moveUp);
            buttons.append(upButton);

            buttons.append('<span class="downButton">&nbsp;</span>');

            var downButton = $('<span></span>');
            downButton.html('&nbsp;&darr;&nbsp;');
            downButton.css('cursor', 'pointer');
            downButton.addClass('downButton');
            downButton.click(moveDown);
            buttons.append(downButton);

            li.append(buttons);
            list.append(li);
        }
        $('#selectedPOIs').html(list);
    }
}

function moveUp(event) {
    var li = null;
    try {
        li = $(event.target).parent().parent();
    } catch (err) {
    }

    if (li) {
        var prev = li.prev();
        if (prev) {
            if (editingPoiIndex != null) {
                stopEditingPoi();
            }
            //move marker in array
            var index = li.index();
            var prevMarker = markers[index - 1];
            markers[index - 1] = markers[index];
            markers[index] = prevMarker;

            //move list item
            var orig_clone = li.clone(true);
            var prev_clone = prev.clone(true);
            li.replaceWith(prev_clone);
            prev.replaceWith(orig_clone);
        }
    }
}

function moveDown(event) {
    var li = null;
    try {
        li = $(event.target).parent().parent();
    } catch (err) {
    }

    if (li) {
        var next = li.next();
        if (next) {
            if (editingPoiIndex != null) {
                stopEditingPoi();
            }
            //move marker in array
            var index = li.index();
            var nextMarker = markers[index + 1];
            markers[index + 1] = markers[index];
            markers[index] = nextMarker;

            //move list item
            var orig_clone = li.clone(true);
            var next_clone = next.clone(true);
            li.replaceWith(next_clone);
            next.replaceWith(orig_clone);
        }
    }
}

function editPOI(marker) {
    $("#wizard").tabs('select', 0);
    stopEditingPoi();
    var place = marker.place;
    editingPoiIndex = markers.indexOf(marker);
    $('#editingPoi').text('Editing: ' + place.name);
    $('#editingPoi').show();
    $('.poiCancel').show();
    populatePoi(place);
}

function stopEditingPoi() {
    stopEditingMedia();
    $('#editingPoi').text('');
    $('#editingPoi').hide();
    $('.poiCancel').hide();
    clearPoiForms();
    editingPoiIndex = null;
}

function clearPoiForms() {
    $('#poiName').val('');
    $('#poiOfficialName').val('');
    $('#latitude').val('');
    $('#longitude').val('');
    $('#url').val('');
    $('#thumbnailUrl').val('');
    $('#fbLikeUrl').val('');
    $('#fbLikeButtonEnabled').removeAttr('checked');
    $('#description').val('');
    $('#shortDescription').val('');
    clearMediaForms();
    clearPhoneNumbers();
    clearEmailAddresses();
    clearSelectedMedia();
}

function populatePoi(place) {
    $('#poiName').val(place.name);
    $('#poiOfficialName').val(place.officialName);
    $('#latitude').val(place.location.lat());
    $('#longitude').val(place.location.lng());
    $('#url').val(place.url);
    $('#thumbnailUrl').val(place.thumbnailUrl);
    $('#fbLikeUrl').val(place.fbLikeUrl);
    if (place.fbLikeButtonEnabled) {
        $('#fbLikeButtonEnabled').attr('checked', 'checked');
    } else {
        $('#fbLikeButtonEnabled').removeAttr('checked');
    }
    $('#description').val(place.description);
    $('#shortDescription').val(place.shortDescription);
    populateMedia(place.media);
    populatePhoneNumbers(place.phoneNumbers);
    populateEmailAddresses(place.emailAddresses);
}

function generateRouteKml() {
    generateKML(markers, poly.getPath());
}
