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


function initializeMap(cntxtPath) {
    contextPath = cntxtPath;
    var latlng;
    var zoom = 15;
    latlng = new google.maps.LatLng(39.168486, -86.523455);

    var myOptions = {
        zoom: zoom,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        streetViewControl: true,
        zoomControl: true,
        scaleControl: false,
        mapTypeControl: false,
        disableDoubleClickZoom: false
    };
    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

    google.maps.event.addListener(map, 'click', selectPoint);

    mapService = new gmaps.ags.MapService(arcGisServerUrl);
    var agsType = new gmaps.ags.MapType(arcGisServerUrl, {name: 'ArcGIS', opacity: 1.0});
    map.overlayMapTypes.insertAt(0, agsType);
    google.maps.event.addListenerOnce(mapService, 'load', function () {
        $('#findButton').removeAttr('disabled');
    });

    iw = new google.maps.InfoWindow();

    initializePoi();
}

function initializePoi() {
    var poiJson = $('#poiJson').val();
    if (poiJson.length) {
        var obj = jQuery.parseJSON(poiJson);
        if (obj) {
            $('#poiId').val(obj.poiId);
            $('#poiName').val(obj.name);
            $('#poiOfficialName').val(obj.officialName);
            $('#description').val(obj.description);
            $('#shortDescription').val(obj.shortDescription);
            $('#poiVersion').val(obj.versionNumber);
            $('#latitude').val(obj.latitude);
            $('#longitude').val(obj.longitude);
            $('#url').val(obj.url);
            $('#thumbnailUrl').val(obj.thumbnailUrl);
            $('#fbLikeUrl').val(obj.fbLikeUrl);
            if (obj.fbLikeButtonEnabled == 'T') {
                $('#fbLikeButtonEnabled').attr('checked', 'checked');
            } else {
                $('#fbLikeButtonEnabled').removeAttr('checked');
            }
            for (var i = 0; i < obj.permissions.length; i++) {
                var permission = obj.permissions[i];
                addPermissionGroup(permission.groupName, permission.type == 'E');
            }

            for (var i = 0; i < obj.media.length; i++) {
                var media = obj.media[i];
                if (media.type == imageType) {
                    loadImageToList(media.url, media.title, media.caption);
                } else if (media.type == audioType) {
                    loadAudioToList(media.oggVorbisUrl, media.mp3Url, media.wavUrl, media.title, media.caption);
                } else if (media.type == videoType) {
                    loadVideoToList(media.oggUrl, media.mp4Url, media.webMUrl, media.youTubeUrl, media.title, media.caption);
                }
            }

            place = new Object();
            place.name = obj.name;
            place.officialName = obj.officialName;
            place.location = new google.maps.LatLng(obj.latitude, obj.longitude);
            place.id = obj.locationId;
            place.type = obj.type;
            place.media = obj.media;
            place.description = obj.description;
            place.shortDescription = obj.shortDescription;
            place.url = obj.url;
            place.thumbnailUrl = obj.thumbnailUrl;
            place.fbLikeUrl = obj.fbLikeUrl == 'T';
            place.permissions = obj.permissions;
            if (obj.phoneNumbers) {
                place.phoneNumbers = new Array();
                for (var i = 0; i < obj.phoneNumbers.length; i++) {
                    var number = obj.phoneNumbers[i];
                    var phoneNumber = new Object();
                    phoneNumber.value = number.number;
                    phoneNumber.name = number.name;
                    place.phoneNumbers.push(phoneNumber);
                }
                populatePhoneNumbers(place.phoneNumbers);
            }
            if (obj.emailAddresses) {
                place.emailAddresses = new Array();
                for (var p = 0; p < obj.emailAddresses.length; p++) {
                    var email = obj.emailAddresses[p];
                    var emailAddress = new Object();
                    emailAddress.address = email.address;
                    emailAddress.name = email.name;
                    place.emailAddresses.push(emailAddress);
                }
                populateEmailAddresses(place.emailAddresses);
            }
            google.maps.event.addListenerOnce(map, "tilesloaded", function () {
                setTempMarker(place);
                map.panTo(place.location);
            });
        }
    }
}

function savePoi() {
    if (validatePOI()) {
        var poi = new Object();

        poi.id = $('#poiId').val();
        poi.locationId = tempMarker.place.id;
        poi.type = tempMarker.place.type;
        poi.name = $('#poiName').val();
        poi.officialName = $('#poiOfficialName').val();
        poi.description = $('#description').val();
        poi.shortDescription = $('#shortDescription').val();
        poi.version = $('#poiVersion').val();
        poi.location = new Object();
        poi.location.latitude = $('#latitude').val();
        poi.location.longitude = $('#longitude').val();
        poi.url = $('#url').val();
        poi.thumbnailUrl = $('#thumbnailUrl').val();
        poi.fbLikeUrl = $('#fbLikeUrl').val();
        poi.fbLikeButtonEnabled = $('#fbLikeButtonEnabled').attr('checked') ? true : false;
        addSelectedMediaToPoi(poi);
        addPhoneNumbersToPoi(poi);
        addEmailAddressesToPoi(poi);

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
        poi.permissions = permissions;

        $('#data').val(JSON.stringify(poi));
        $("#postForm").submit();
    }
}

function validatePOI() {
    if (!tempMarker || !tempMarker.place) {
        alert('You must choose a location.');
        return false;
    }
    if ($('#fbLikeButtonEnabled').attr('checked') && !$('#fbLikeUrl').val()) {
        alert('A URL for the Like Button is required.');
        return false;
    }
    if (!$('#editPermissionsList li').length) {
        alert('You must include at least one ADS group with editing permissions.');
        return false;
    }
    var pointName = $('#poiName').val();
    var pointOfficialName = $('#poiOfficialName').val();
    if (!pointName.length || !pointOfficialName.length) {
        alert('Please name this location.');
        return false;
    }
    if (pointName.length > 256 || pointOfficialName.length > 256) {
        alert('Location names must be less than or equal to 256 characters.');
        return false;
    }
    if (tempMarker.place.name && tempMarker.place.location) return true;
    else return false;
}

function selectPoint(event) {
    if (isSelectingPoint) {
        updateSelectedPlace(event.latLng);
    }

    if (isSelectingVenue) {
        selectedPlace = null;
        if (tempMarker) {
            tempMarker.setMap(null);
        }
        findFoursquareVenues(event.latLng);
    }
}

function updateSelectedPlace(latLng) {
    selectedPlace = new Object();
    selectedPlace.location = latLng;
    selectedPlace.type = customPointType;
    setTempMarker(selectedPlace);
}

function selectBuilding(number) {
    if (number > 0) {
        var place = places[number - 1]; // 'select:' is index 0, so number 1 = places[0]
        setTempMarker(place)
        selectedPlace = place;
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
        google.maps.event.addListener(tempMarker, 'click', function () {
            showIW();
        });
    } else {
        tempMarker.setPosition(place.location);
    }
    tempMarker.setTitle(place.name);
    tempMarker.place = place;
    $('#latitude').val(place.location.lat());
    $('#longitude').val(place.location.lng());
    $('#poiName').val(place.name);
    $('#url').val(place.url);
}

function showIW() {
    var content = '';
    if ($('#poiName').val()) {
        content += '<h4>' + $('#poiName').val() + '</h4>';
    } else {

    }
    if ($('#shortDescription').val()) {
        content += $('#shortDescription').val();
    }
    if ($('#url').val()) {
        content += '<p><a href="' + $('#url').val() + '" target="_blank">' + $('#url').val() + '</a><p>';
    }
    if (content) {
        iw.setContent(content);
        iw.open(map, tempMarker);
    }
}

function changeSelectorTabs(event, ui) {
    clearSearchResults();
    var $tabs = $('#selector').tabs();
    var selected = $tabs.tabs('option', 'selected');
    if (selected == 1) { //Select A Point is the second tab
        changeMode('select');
    } else if (selected == 2) { //Select A Foursquare Venue is the third tab
        changeMode('venue');
    } else {
        changeMode();
    }
}

function changeMode(mode) {
    if (mode === 'select') {
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
    if (selected == 3) { //Save is the fourth tab
        var list = '';
        for (var i = 0; i < markers.length; i++) {
            list += '<div>' + (i + 1) + '. ' + markers[i].building.name + '</div>'
        }
        $('#selectedPOIs').html(list);
    }
}
