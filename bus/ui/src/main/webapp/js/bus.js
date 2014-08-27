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
var alertsWindowDetails = {
    alerts: null,
    isOpen: false,
    hasAlerts: false
};
var alertControlDiv;
var contextPath;
var curRouteId;
var curCampus;
var firstTimeLoadingBuses = true;
var gpsPressed = false;
var gpsTimeout;
var hasLoaded = 0;
var isFocus = true;
var lastInfoWindow = null;
var markers = [];
var markersArray = [];
var newmap;
var routes = [];
var stops = [];
var hiddenStopsButtons = [];
var timeout;
var updateLocation;
var userLocationCircle;
var userMarkersArray = [];
var infoWindowDetails = {
    infoWindow: null,
    busClicked: false,
    busId: null,
    opened: false
};
var routeToast = "";
var targetControlDiv;

function setContextPath(path) {
    contextPath = path;
}
function setCurRouteId(id) {
    curRouteId = id;
}

//custom bus marker
function BusCustomMarker(map, position, title, routeId, color, hiddenButton) {
    this.routeId_ = routeId;
    this.color_ = color;
    this.oldColor_ = color;
    this.hiddenButton_ = hiddenButton;
    this.transitionDegree_ = 0;
    this.transitionLatLong_ = null;
    this.animationStep_ = 0;
    this.title_ = title;
    this.div_ = null;
    this.pos_ = position;
    this.arrow_ = contextPath + '/images/service-icons/BusDirectional_64px_arrow.png';
    this.bus_ = contextPath + '/images/service-icons/BusDirectional_64px_bus.png';
    this.blank_ = contextPath + '/images/service-icons/hidden-button.png';
    this.degree_ = 0;
    this.height_ = 64;
    this.width_ = 64;
    this.map_ = map;
    this.setMap(map);
}

//pauses tracking the buses until the window is the focus again without this the
//buses flew around for a bit when the page was the returned to before they started working.
$(function () {
    $(window).focus(function () {
        $.each(markers, function (index, marker) {
            marker.hiddenButton_.setMap(null);
        });
        deleteOverlays(markers);
        isFocus = true;
        fetchBusInformation();
    });
    $(window).blur(function () {
        //stop timeout and close infowindow until focus is back
        closeinfoWindow();
        infoWindowDetails.infoWindow.close();
        clearTimeout(timeout);
        isFocus = false;
    });
});

$(window).ready(function () {
    // when a route is selected from the drop down
    $('#select-route').on('change', function () {
        if (gpsPressed) {
            userMarkersArray[0].setMap(null);
            if (userLocationCircle) {
                userLocationCircle.setMap(null);
            }
        }
        curRouteId = $('select#select-route').val();
        changeDropDownColor();

        //stop timeout while routes/buses/stops are being hidden/shown
        clearTimeout(timeout);

        if (window.sessionStorage) {
            window.sessionStorage.busRouteId = curRouteId;
            window.sessionStorage.busStopId = '';
        }

        infoWindowDetails.opened = false;
        infoWindowDetails.infoWindow.close();
        closeinfoWindow();

        //var noBuses = true;
        $.each(routes, function (index, route) {
            if (route.path != null) {
                if ((curRouteId == 'ALL' || route.name == curRouteId) || route.path.map != null) {
                    route.path.setMap(null);
                    setTimeout(function () {
                        if (curRouteId == 'ALL' || route.name == curRouteId) {
                            route.path.setMap(newmap);
                        }
                    }, 500);
                }
            } else if (route.name == curRouteId) {
                routeToast = "Route can not be shown at this time.";
            }
        });
        for (var i = 0; i < stops.length; i++) {
            if (curRouteId == 'ALL' || stops[i].route == curRouteId) {
                stops[i].busstopinfo.setMap(newmap);
                hiddenStopsButtons[i].marker.setMap(newmap);
            } else {
                stops[i].busstopinfo.setMap(null);
                hiddenStopsButtons[i].marker.setMap(null);
            }
        }
        $.each(markers, function (index, marker) {
            if (curRouteId == 'ALL' || marker.routeId_ == curRouteId) {
                marker.map_ = newmap;
                marker.setMap(newmap);
                marker.hiddenButton_.setMap(newmap);
                //noBuses = false;
            } else {
                marker.map_ = null;
                marker.setMap(null);
                marker.hiddenButton_.setMap(null);
            }
        });

        firstTimeLoadingBuses = true;
        fetchBusInformation();
    });
});

function changeDropDownColor() {
    if (curRouteId == "ALL") {
        $("div.ui-select div span.ui-btn-inner").css('background', 'black');
    } else {
        $.each(routes, function (index, route) {
            if (route.name == curRouteId) {
                $("div.ui-select div span.ui-btn-inner").css('background', "#" + route.color);
            }
        });
    }
}

$(window).load(function () {
    BusCustomMarker.prototype = new google.maps.OverlayView();

    BusCustomMarker.prototype.onAdd = function () {
        var arrowDiv = document.createElement('div');
        arrowDiv.style.position = "absolute";
        arrowDiv.style.width = this.width_ + "px";
        arrowDiv.style.height = this.height_ + "px";

        var arrowImg = document.createElement("img");
        arrowImg.setAttribute("class", "arrowImg");
        arrowImg.src = this.arrow_;
        arrowImg.style.position = "absolute";
        arrowImg.style.width = "100%";
        arrowImg.style.height = "100%";
        arrowDiv.appendChild(arrowImg);

        var circleDiv = document.createElement('div');
        circleDiv.style.position = "absolute";
        circleDiv.style.width = "28px";
        circleDiv.style.height = "28px";
        circleDiv.style.top = "18px";
        circleDiv.style.left = "18px";

        var colorImg = document.createElement("img");
        colorImg.src = this.blank_;
        colorImg.setAttribute("class", "colorImg");
        colorImg.style.position = "absolute";
        colorImg.style.width = "100%";
        colorImg.style.height = "100%";
        colorImg.style.background = "#" + this.color_;
        colorImg.style.borderRadius = "90px";
        colorImg.style.MozBorderRadius = "90px";
        circleDiv.appendChild(colorImg);
        arrowDiv.appendChild(circleDiv);

        var img = document.createElement("img");
        img.src = this.bus_;
        img.style.position = "absolute";
        img.style.top = "1px";
        img.style.width = "100%";
        img.style.height = "100%";
        arrowDiv.appendChild(img);

        this.div_ = arrowDiv;

        var panes = this.getPanes();
        panes.floatPane.appendChild(arrowDiv);
        this.rotateImage(this.degree_);
    };

    BusCustomMarker.prototype.draw = function () {
        if (this.color_ != this.oldColor_) {
            this.oldColor_ = this.color_;
            $(this.div_).children(".colorImg").css("background-color", "#" + this.color_);
        }
        var overlayProjection = this.getProjection();
        var o = overlayProjection.fromLatLngToDivPixel(this.pos_);
        var l = o.x - Math.round(this.width_ / 2);
        var t = o.y - this.height_;
        $(this.div_).css("margin-top", Math.round(this.width_ / 2) + "px");
        this.div_.style.left = l + 'px';
        this.div_.style.top = t + 'px';
    };

    BusCustomMarker.prototype.onRemove = function () {
        this.div_.parentNode.removeChild(this.div_);
        this.div_ = null;
    };

    BusCustomMarker.prototype.rotateImage = function (degree) {
        $(this.div_).children(".arrowImg").css("transform", "rotate(" + degree + "deg)");
        $(this.div_).children(".arrowImg").css("-ms-transform", "rotate(" + degree + "deg\")");
        $(this.div_).children(".arrowImg").css("-webkit-transform", "rotate(" + degree + "deg\")");
    };
});

//initial set up for displaying the google map
function initialize(id, lat, lng, campus) {
    infoWindowDetails.infoWindow = new google.maps.InfoWindow({});
    google.maps.event.addListener(infoWindowDetails.infoWindow, 'closeclick', closeinfoWindow);
    curCampus = campus;
    var latlng = new google.maps.LatLng(lat, lng);
    var myOptions = {
        zoom: 15,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    newmap = new google.maps.Map(document.getElementById(id), myOptions);

    google.maps.event.addListener(newmap, 'zoom_changed', function () {
        // zoom changed
        /*
         if (infoWindowDetails.opened) {
         infoWindowDetails.infoWindow.close();
         infoWindowDetails.infoWindow.open(newmap);
         }
         */
    });

    google.maps.event.addListener(newmap, 'click', function () {
        infoWindowDetails.infoWindow.close();
        closeinfoWindow();
    });

    // create the DIV to hold the control and call the TargetControl()
    // constructor passing in this DIV.
    alertControlDiv = document.createElement('DIV');
    var alertControl = new AlertControl(newmap, alertControlDiv);
    newmap.controls[google.maps.ControlPosition.BOTTOM_LEFT].push(alertControlDiv);
    alertControlDiv.style.display = 'none';

    // create the DIV to hold the control and call the TargetControl()
    // constructor passing in this DIV.
    targetControlDiv = document.createElement('DIV');
    var targetControl = new TargetControl(newmap, targetControlDiv);
    targetControlDiv.index = 1;
    newmap.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(targetControlDiv);
    targetControlDiv.style.display = 'none';

    // Create the DIV to hold the control and call the TrackerControl()
    // constructor passing in this DIV.
    var trackerControlDiv = document.createElement('DIV');
    var trackerControl = new TrackerControl(newmap, trackerControlDiv);
    trackerControlDiv.index = 1;
    newmap.controls[google.maps.ControlPosition.TOP_LEFT].push(trackerControlDiv);

    google.maps.event.trigger(newmap, 'resize');

    directionsDisplay = new google.maps.DirectionsRenderer();
    directionsDisplay.setMap(newmap);
    google.maps.event.trigger($(trackerControlDiv).children()[0], "click");
    return newmap;
}

function closeinfoWindow() {
    if (window.sessionStorage) {
        window.sessionStorage.busStopId = '';
    }
    infoWindowDetails.opened = false;
    infoWindowDetails.busClicked = false;
    infoWindowDetails.busId = null;
}

function AlertControl(map, div) {

    // Get the control DIV. We'll attach our control UI to this DIV.
    var controlDiv = div;

    // We set up a variable for the 'this' keyword since we're adding event
    // listeners later and 'this' will be out of scope.
    var control = this;

    // Set the property upon construction
    control.controlDiv_ = controlDiv;

    // Set CSS styles for the DIV containing the control. Setting padding to
    // 5 px will offset the control from the edge of the map
    controlDiv.style.padding = '5px';

    // Set CSS for the control border
    var alertUserUI = document.createElement('DIV');
    alertUserUI.setAttribute("class", "alertIcon");
    alertUserUI.setAttribute("className", "alertIcon");
    alertUserUI.style.backgroundImage = "url('" + contextPath + "/images/service-icons/alert_sm2.png')";
    alertUserUI.style.padding = '0px';
    alertUserUI.style.cursor = 'pointer';
    alertUserUI.title = 'Click to see current alerts';
    controlDiv.appendChild(alertUserUI);

    // Set CSS for the control interior
    var alertUserText = document.createElement('DIV');
    alertUserText.style.padding = '0px';
    alertUserText.innerHTML = 'Find My Location';
    alertUserUI.appendChild(alertUserText);
    // Setup the click event listener for the tracker toggle.
    google.maps.event.addDomListener(alertUserUI, 'click', function () {
        if (alertsWindowDetails.hasAlerts) {
            $('#alertMessageDiv').html(alertsWindowDetails.alerts);
            $('#tooltip').popup('open', {positionTo: '#map_canvas'});
        }
    });
}

function TargetControl(map, div) {

    // Get the control DIV. We'll attach our control UI to this DIV.
    var controlDiv = div;

    // We set up a variable for the 'this' keyword since we're adding event
    // listeners later and 'this' will be out of scope.
    var control = this;

    // Set the property upon construction
    control.controlDiv_ = controlDiv;

    // Set CSS styles for the DIV containing the control. Setting padding to
    // 5 px will offset the control from the edge of the map
    controlDiv.style.padding = '5px';

    // Set CSS for the control border
    var targetUserUI = document.createElement('DIV');
    targetUserUI.setAttribute("class", "targetIcon");
    targetUserUI.setAttribute("className", "targetIcon");
    targetUserUI.style.backgroundImage = "url('" + contextPath + "/images/service-icons/user-button30.png')";
    targetUserUI.style.padding = '0px';
    targetUserUI.style.cursor = 'pointer';
    targetUserUI.title = 'Click to show where you are';
    controlDiv.appendChild(targetUserUI);

    // Set CSS for the control interior
    var targetUserText = document.createElement('DIV');
    targetUserText.style.padding = '0px';
    targetUserText.innerHTML = 'Find My Location';
    targetUserUI.appendChild(targetUserText);
    // Setup the click event listener for the tracker toggle.
    google.maps.event.addDomListener(targetUserUI, 'click', function () {
        if (gpsPressed) {
            if (userLocationCircle && gpsPressed) {
                newmap.panTo(userLocationCircle.getCenter());

            }
            /*
             var bounds = new google.maps.LatLngBounds();
             if (userLocationCircle && gpsPressed) {
             bounds.union(userLocationCircle.getBounds());
             boundChange = true;
             }
             if (boundChange){
             newmap.fitBounds(bounds);
             }
             */
        }
    });
}

/**
 * The TrackerControl adds a control to the map that
 * returns the user to the control's defined home.
 */

// Define properties to hold the state
TrackerControl.prototype.active_ = null;
TrackerControl.prototype.controlDiv_ = null;

// Define setters and getters for this property
TrackerControl.prototype.getActive = function () {
    return this.active_;
};
TrackerControl.prototype.setActive = function (boolean) {
    this.active_ = boolean;
    var test = this.controlDiv_;
    if (boolean) {
        test.firstChild.setAttribute("class", "gpsIconOn");
        test.firstChild.setAttribute("className", "gpsIconOn");
    } else {
        test.firstChild.setAttribute("class", "gpsIconOff");
        test.firstChild.setAttribute("className", "gpsIconOff");
    }
};

function TrackerControl(map, div) {

    // Get the control DIV. We'll attach our control UI to this DIV.
    var controlDiv = div;

    // We set up a variable for the 'this' keyword since we're adding event
    // listeners later and 'this' will be out of scope.
    var control = this;

    // Set the property upon construction
    control.controlDiv_ = controlDiv;

    // Set CSS styles for the DIV containing the control. Setting padding to
    // 5 px will offset the control from the edge of the map
    controlDiv.style.padding = '5px';

    // Set CSS for the control border
    var trackerToggleUI = document.createElement('DIV');

    trackerToggleUI.setAttribute("class", "gpsIconOff");
    trackerToggleUI.setAttribute("className", "gpsIconOff");
    trackerToggleUI.style.padding = '0px';
    trackerToggleUI.style.cursor = 'pointer';
    trackerToggleUI.title = 'Click to show where you are';
    controlDiv.appendChild(trackerToggleUI);

    // Set CSS for the control interior
    var trackerToggleText = document.createElement('DIV');
    trackerToggleText.style.padding = '0px';
    trackerToggleText.innerHTML = 'My Location';
    trackerToggleUI.appendChild(trackerToggleText);
    // Setup the click event listener for the tracker toggle.
    google.maps.event.addDomListener(trackerToggleUI, 'click', function () {
        if (gpsPressed) {
            gpsPressed = false;
            clearTimeout(gpsTimeout);
            control.setActive(false);
            stopTrackingUserLocation();
        } else {
            gpsPressed = true;
            loadingGPS(control);
            startTrackingUserLocation(map, markersArray, userMarkersArray, control);
        }
    });
}

function loadingGPS(control) {
    gpsTimeout = window.setTimeout(function () {
        loadingGPS(control);
    }, 1000);
    if (control.getActive()) {
        control.setActive(false);
    } else {
        control.setActive(true);
    }
}

//attempts to find the users location using the navigator.geolocation if its available
function startTrackingUserLocation(map, markersArray, userMarkersArray, control) {
    if (navigator.geolocation) {
        //navigator.geolocation.getCurrentPosition(function(initialPosition) {
        var geo_options = {
            enableHighAccuracy: true,
            maximumAge: 30000,
            timeout: 10000
        };
        var success = function (position) {
            clearTimeout(gpsTimeout);
            control.setActive(true);
            drawUserLocation(map, markersArray, userMarkersArray, position, control);
        };
        var fail = function (error) {
            if (error.code == 1) {
                toast(error.message);
            } else if (error.code == 3) {
                gpsPressed = false;
                clearTimeout(gpsTimeout);
                control.setActive(false);
                deleteOverlays(userMarkersArray);
                if (userLocationCircle) {
                    userLocationCircle.setMap(null);
                }
                hasLoaded = 0;
                targetControlDiv.style.display = 'none';
                navigator.geolocation.clearWatch(updateLocation);
                topBar("Could not retrieve your position at this moment.");
            } else {
                topBar("Could not retrieve your position.");
            }
        };
        updateLocation = navigator.geolocation.watchPosition(success, fail, geo_options);
        //});
    } else {
        topBar("Your device does not support location services.");
    }
}

function topBar(message) {
    $("<div />", { 'class': 'topbar', text: message })
        .css({display: "block",
            opacity: 1.0,
            position: "absolute",
            padding: "7px",
            width: "100%",
            color: "white",
            "background-color": "grey",
            "text-align": "center",
            "z-index": "1"})
        .hide().insertBefore('#map_canvas').slideDown('fast').delay(3000).slideUp(function () {
            $(this).remove();
        });
}

function bottomBar(message) {
    if (!alertsWindowDetails.isOpen) {
        alertsWindowDetails.isOpen = true;
        $('<div id="alertDiv" />', { 'class': 'bottombar'}).html(message)
            .css({display: "block",
                opacity: 1.0,
                bottom: $('div#mapFooter').height(),
                position: "absolute",
                overflow: "auto",
                width: "100%",
                height: "100px",
                "background-color": "grey",
                "z-index": "1"})
            .hide().insertAfter('#map_canvas').slideDown('fast');
    }
}

// stops tracking user's location and gets rid of the blue marker and circle on the map
function stopTrackingUserLocation() {
    deleteOverlays(userMarkersArray);
    if (userLocationCircle) {
        userLocationCircle.setMap(null);
    }
    targetControlDiv.style.display = 'none';
    resizeMap();
    navigator.geolocation.clearWatch(updateLocation);
    hasLoaded = 0;
}

// displays user's location on the google map with a blue marker and draws a circle around that marker
function drawUserLocation(map, markersArray, userMarkersArray, position, control) {
    if (position.coords.accuracy <= 1700) {
        targetControlDiv.style.display = 'inline';
        var location = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
        deleteOverlays(userMarkersArray);
        var image = new google.maps.MarkerImage(contextPath + '/images/service-icons/user.png',
            // This marker is 22 pixels wide by 22 pixels tall.
            null,
            // The origin for this image is 0,0.
            new google.maps.Point(0, 0),
            // The anchor for this image is the center of the image 11,11.
            new google.maps.Point(11, 11)
        );
        var userLocationMarker = addMarker(map, userMarkersArray, location, image);//"http://www.google.com/intl/en_us/mapfiles/ms/micons/blue-dot.png");

        if (userLocationCircle) {
            userLocationCircle.setMap(null);
        }
        // Add circle overlay and bind to marker
        var circle = new google.maps.Circle({
            map: map,
            radius: position.coords.accuracy,
            fillColor: '#00BFFF',
            strokeColor: '#FFFFFF',
            zIndex: 1
        });
        circle.bindTo('center', userLocationMarker, 'position');

        userLocationCircle = circle;

        if (hasLoaded == 0) {
            hasLoaded = 1;
            centerOverAllLocations();
        }
    } else {
        gpsPressed = false;
        clearTimeout(gpsTimeout);
        control.setActive(false);
        navigator.geolocation.clearWatch(updateLocation);
        topBar("Unable to accurately determine your position.");
    }
}

// brings up a popup message that disappears after 1.5 seconds
var toast = function (msg) {
    $("<div class='ui-loader ui-overlay-shadow ui-body-a ui-corner-all'><h3>" + msg + "</h3></div>")
        .css({ display: "block",
            opacity: 1.0,
            position: "fixed",
            padding: "7px",
            "text-align": "center",
            width: "270px",
            left: ($(window).width() - 284) / 2,
            top: $(window).height() / 2 })
        .appendTo($.mobile.pageContainer).delay(1500)
        .fadeOut(400, function () {
            $(this).remove();
        });
};

//helper function for drawUserLocation
function addMarker(map, array, location, icon) {
    var myOptions;
    if (icon) {
        myOptions = {
            position: location,
            map: map,
            icon: icon
        };

    } else {
        myOptions = {
            position: location,
            map: map
        };
    }
    marker = new google.maps.Marker(myOptions);
    array.push(marker);
    return marker;
}

function fetchAlertInformation() {
//	var busAlertUrl = contextPath + '/bus/busAlerts';
    var busAlertUrl = contextPath + '/services/bus/alerts';
    $.ajax({
        url: busAlertUrl,
        dataType: 'json',
        success: updateAlerts,
        error: function (jqXHR, textStatus, errorThrown) {
            badFile(errorThrown, "Alerts");
        },
        cache: false
    });
}

function updateAlerts(data) {
    if (data != null && data.length > 0) {
        if (data.length == 1) {
            $('#prevnext').addClass('invisible');
        } else {
            $('#prevnext').removeClass('invisible');
        }
        var alerts = '';
        $.each(data, function (key, alert) {
            if (key == 0) {
                alerts += '<div class="active alert" style=\"color:#' + alert.color + ';\">' + alert.message + '</div>';
            } else {
                alerts += '<div class="invisible alert" style=\"color:#' + alert.color + ';\">' + alert.message + '</div>';
            }
        });

        alertsWindowDetails.alerts = alerts;
        alertsWindowDetails.hasAlerts = true;
        alertControlDiv.style.display = 'inline';
    } else {
        alertControlDiv.style.display = 'none';
        alertsWindowDetails.hasAlerts = false;
    }
}

//sets a timer to call this function again and also goes to updateBusLocation or badFile
//depending on if the json file is valid.
function fetchBusInformation() {
//	var busFeedUrl = contextPath + '/bus/busLocations';

    var busFeedUrl = contextPath + '/services/bus/locations';
    fetchAlertInformation();
    $.ajax({
        url: busFeedUrl,
        dataType: 'json',
        success: updateBusLocation,
        error: function (jqXHR, textStatus, errorThrown) {
            badFile(errorThrown, "Buses");
        },
        cache: false
    });
    timeout = window.setTimeout(function () {
        fetchBusInformation();
    }, 5000);
}

//clears local storage of when the last error alert was displayed and retrieves bus information
//and adds/updates buses on the google map with new locations and images if their location is different
function updateBusLocation(data) {
    if (typeof(localStorage) != 'undefined') {
        if (localStorage.getItem('BusesMessage') != null) {
            localStorage.removeItem('BusesMessage');
        }
    }
    var MAX_STEPS = 200;
    if (data != null) {
        $.each(data.bus, function (key, bus) {
            var makeNew = true;
            for (var i = 0; i < markers.length; i++) {
                if (markers[i].title_ == bus.id.toString()) {
                    var location = new google.maps.LatLng(bus.latitude, bus.longitude);
                    markers[i].color_ = bus.color;
                    markers[i].routeId_ = bus.routeId;
                    if (markers[i].map_ == null && (curRouteId == 'ALL' || bus.routeId == curRouteId)) {
                        markers[i].map_ = newmap;
                        markers[i].setMap(newmap);
                        markers[i].hiddenButton_.setMap(newmap);
                        markers[i].degree_ = parseFloat(bus.heading);
                    }
                    if ((curRouteId != 'ALL' && bus.routeId != curRouteId) && markers[i].map != null) {
                        markers[i].map_ = null;
                        markers[i].setMap(null);
                        markers[i].hiddenButton_.setMap(null);
                    }
                    markers[i].transitionDegree_ = 0;
                    if (markers[i].degree_ > 360) {
                        markers[i].degree_ -= 360;
                    } else if (markers[i].degree_ < 0) {
                        markers[i].degree_ += 360;
                    }
                    if (bus.heading) {
                        var newHeading = parseFloat(bus.heading);
                        if (markers[i].degree_ > 180 && markers[i].degree_ - 180 > newHeading) {
                            markers[i].transitionDegree_ = ((newHeading + 360) - markers[i].degree_) / 100;
                        } else if (markers[i].degree_ < 180 && markers[i].degree_ + 180 < newHeading) {
                            markers[i].transitionDegree_ = (newHeading - (markers[i].degree_ + 360)) / 100;
                        } else {
                            markers[i].transitionDegree_ = (newHeading - markers[i].degree_) / 100;
                        }
                    }

                    if (markers[i].map != null) {
                        markers[i].transitionLatLong_ = new google.maps.LatLng((location.lat() - markers[i].pos_.lat()) / MAX_STEPS, (location.lng() - markers[i].pos_.lng()) / MAX_STEPS);
                    } else {
                        markers[i].pos_ = location;
                        markers[i].degree_ = parseFloat(bus.heading);
                        markers[i].transitionLatLong_ = null;
                        markers[i].transitionDegree_ = 0;
                    }
                    markers[i].animationStep_ = 0;
                    makeNew = false;
                    break;
                }
            }
            if (makeNew) {
                var location = new google.maps.LatLng(bus.latitude, bus.longitude);
                var imageURLHiddenButton = contextPath + '/images/service-icons/hidden-button.png';
                var image = makeImage(imageURLHiddenButton, 16, 16);
                var marker = new google.maps.Marker({
                    position: location,
                    map: (curRouteId == 'ALL' || bus.routeId == curRouteId ? newmap : null),
                    icon: image,
                    zIndex: google.maps.Marker.MAX_ZINDEX + 1
                });
                google.maps.event.addListener(marker, 'click', function () {
                    infoWindowDetails.opened = true;
                    infoWindowDetails.busClicked = true;
                    infoWindowDetails.busId = bus.id;
                    displayInfo(bus.routeId, false, "", bus.id, marker.position);
                });
                var customMarker = new BusCustomMarker((curRouteId == 'ALL' || bus.routeId == curRouteId ? newmap : null), location, bus.id.toString(), bus.routeId, bus.color, marker);
                if (bus.heading) {
                    customMarker.degree_ = parseFloat(bus.heading);
                }
                markers.push(customMarker);
            }
        });
    }
    if (firstTimeLoadingBuses) {
        firstTimeLoadingBuses = false;
        setTimeout(function () {
            centerOverAllLocations();
            if (gpsPressed) {
                if (userMarkersArray.length > 0) {
                    userMarkersArray[0].setMap(newmap);
                }
                if (userLocationCircle) {
                    userLocationCircle.setMap(newmap);
                }
            }
        }, 750);
        var filtered = $(markers).filter(function () {
            return this.routeId_ == curRouteId;
        });
        if (filtered.length == 0) {
            toast('No buses found at this time. ' + routeToast);
        } else if (routeToast != "") {
            toast(routeToast);
        }
        routeToast = "";
    } else {
        moveMarker(MAX_STEPS);
    }
}

function moveMarker(MAX_STEPS) {
    $.each(markers, function (index, marker) {
        if (marker.animationStep_ <= MAX_STEPS && marker.map_ != null && marker.transitionLatLong_ != null) {
            var markerStep = new google.maps.LatLng(marker.pos_.lat() + marker.transitionLatLong_.lat(), marker.pos_.lng() + marker.transitionLatLong_.lng());
            if (infoWindowDetails.busClicked && infoWindowDetails.busId == marker.title_) {
                infoWindowDetails.infoWindow.setPosition(markerStep);
            }
            if (marker.animationStep_ <= 100) {
                marker.degree_ += marker.transitionDegree_;
                marker.rotateImage(markers[index].degree_);
            }
            marker.pos_ = markerStep;
            marker.hiddenButton_.setPosition(markerStep);
            if (marker.div_ != null) {
                marker.draw();
            }
        }
        marker.animationStep_ += 1;
        if (marker.animationStep_ < MAX_STEPS && index + 1 == markers.length) {
            setTimeout(function () {
                moveMarker(MAX_STEPS);
            }, 100);
        }
    });
}

// sets the json array to routes and draws the path of the route(s) on google maps
function configureBusRoutes(routeslst) {
    $.each(routeslst, function (routeKey, route) {
        var routeColor = route.color;
        var routeId = route.id;
        var routeDirections = [];
        var routePath = null;
        var lat = false;
        if (route.path != null) {
            $.each(route.path.latLongs, function (pathKey, point) {
                if (lat) {
                    var latng = new google.maps.LatLng(lat, point);
                    routeDirections.push(latng);
                    lat = false;
                } else {
                    lat = point;
                }
            });
            routePath = new google.maps.Polyline({
                path: routeDirections,
                strokeColor: "#" + routeColor,
                strokeOpacity: 0.6, //route.path.transparency / 100,
                strokeWeight: 5,//route.path.lineWidth,//3,
                map: (curRouteId == 'ALL' || routeId == curRouteId ? newmap : null)
            });
        } else if (routeId == curRouteId) {
            routeToast = "Route can not be shown at this time.";
        }
        var path = {
            name: routeId,
            color: routeColor,
            path: routePath
        };
        updateBusStop(route.stops, routeColor, routeId);
        routes.push(path);
    });
    centerOverAllLocations();
}

//retrieves bus stop information and displays the bus stops on the google map
//also calls AttachMessage
function updateBusStop(data, color, routeId) {
    $.each(data, function (key, stop) {
        var location = new google.maps.LatLng(parseFloat(stop.latitude), parseFloat(stop.longitude));
        var newstop = new google.maps.Marker({
            position: location,
            map: (curRouteId == 'ALL' || routeId == curRouteId ? newmap : null),
            icon: {
                path: google.maps.SymbolPath.CIRCLE,
                fillOpacity: 1,
                fillColor: "white",//"#" + color,
                strokeOpacity: 1.0,
                strokeColor: "#" + color,//"black",
                strokeWeight: 3,
                scale: 5 //pixels
            }
        });
        google.maps.event.addListener(newstop, 'click', function () {
            infoWindowDetails.opened = true;
            infoWindowDetails.busClicked = false;
            infoWindowDetails.busId = null;
            if (window.sessionStorage) {
                window.sessionStorage.busStopId = stop.id;
            }
            displayInfo(routeId, stop.id, stop.name, "", location);
        });
        var routeStop = {
            busstopinfo: newstop,
            route: routeId,
            id: stop.id,
            name: stop.name
        };
        stops.push(routeStop);
        attachMessage(location, stop.name, stop.id, routeId);
    });
}

//places an invisible button above each displayed bus stop with that stops street address
//which is displayed on the google map when you click the button
function attachMessage(stopLocation, stopName, stopId, routeId) {
    var imageURLHiddenButton = contextPath + '/images/service-icons/hidden-button.png';
    var image = makeImage(imageURLHiddenButton, 16, 16);
    var marker = new google.maps.Marker({
        position: stopLocation,
        map: (curRouteId == 'ALL' || routeId == curRouteId ? newmap : null),
        icon: image
    });
    var hiddenStopButton = {
        stopId: stopId,
        routeId: routeId,
        marker: marker
    };
    hiddenStopsButtons.push(hiddenStopButton);
    google.maps.event.addListener(marker, 'click', function () {
        infoWindowDetails.opened = true;
        infoWindowDetails.busClicked = false;
        infoWindowDetails.busId = null;
        displayInfo(routeId, stopId, stopName, "", stopLocation);
    });
}

//displays the bus/stop name in a google maps info window
function displayInfo(routeId, stopId, stopName, busId, stopLocation) {
    var busDetailsUrl = "";
    if (stopId) {
        busDetailsUrl = contextPath + "/bus/viewStop?routeId=" + routeId + "&stopId=" + stopId + "&campus=" + curCampus;
        infoWindowDetails.infoWindow.setContent('<a id="infoUrl" href="' + busDetailsUrl + '"><p>' + stopName + '</p></a>');
    } else {
        busDetailsUrl = contextPath + "/bus/viewBus?routeId=" + routeId + "&busId=" + busId + "&campus=" + curCampus;
        infoWindowDetails.infoWindow.setContent('<a id="infoUrl" href="' + busDetailsUrl + '"><p>Bus ID: ' + busId + '</p></a>');
    }
    infoWindowDetails.infoWindow.setPosition(stopLocation);
    infoWindowDetails.infoWindow.open(newmap);
}

function showStop(routeId, stopId) {
    $.each(hiddenStopsButtons, function (key, stop) {
        if (stop.routeId == routeId && stop.stopId == stopId) {
            google.maps.event.trigger(stop.marker, "click");
        }
    });
}

// changes the google maps bounds to show the displayed routes and/or the users gps location
function centerOverAllLocations() {
    var bounds = new google.maps.LatLngBounds();
    var boundChange = false;
    if (routes.length > 0) {
        $(routes).each(function (key, route) {
            if (route.name == curRouteId) {
                if (route.path != null) {
                    var array = route.path.getPath().getArray();
                    for (var i = 0; i < array.length; i++) {
                        bounds.extend(array[i]);
                    }
                }
            }
        });
        boundChange = true;
    }
    if (stops.length > 0) {
        $(stops).each(function (key, stop) {
            if (stop.busstopinfo.map != null) {
                bounds.extend(stop.busstopinfo.position);
            }
        });
        boundChange = true;
    }
    if (markers.length > 0) {
        $(markers).each(function (key, bus) {
            if (markers.map_ != null) {
                bounds.extend(markers.pos_);
            }
        });
    }
    if (userLocationCircle && gpsPressed) {
        bounds.union(userLocationCircle.getBounds());
        boundChange = true;
    }
    if (boundChange) {
        newmap.fitBounds(bounds);
    }
}

//Deletes all markers in the array by removing references to them
function deleteOverlays(array) {
    if (array) {
        for (var i = 0; i < array.length; i++) {
            array[i].setMap(null);
        }
        array.length = 0;
    }
}

//called if the json file was not valid and displays an alert if this was the first time this has happened
//otherwise the message waits 24 hours before being displayed again
function badFile(data, fileFrom) {
    var message = "";
    var localStorageName = fileFrom + 'Message';
    switch (fileFrom) {
        case "Buses":
            message = "The bus GPS list is experiencing some problems and is unable to load the buses.";
            break;
        case "Alerts":
            message = "Retrieving the alerts data failed at this time.";
            break;
        case "Routes":
            message = "The Routes data list is experiencing some problems and is unable to load the bus routes";
            break;
    }
    var today = new Date();
    today = today.getTime();

    if (typeof(localStorage) != 'undefined' && fileFrom != "Alerts") {
        try {
            if (localStorage.getItem(localStorageName)) {
                daysLeft = Math.floor((today - localStorage.getItem(localStorageName)) / 60 / 1000); // gets number of minutes since date was stored
                if (daysLeft >= 5) {
                    localStorage.removeItem(localStorageName);
                }
            } else {
                localStorage.setItem(localStorageName, today);
                toast(message);
            }
        } catch (e) {
        }
    }
}

//used to make an icon with a given anchor for the base
function makeImage(URL, anchorH, anchorW) {
    var image = new google.maps.MarkerImage(URL,
        // This marker is 32 pixels wide by 32 pixels tall.
        new google.maps.Size(32, 32),
        // The origin for this image is 0,0.
        new google.maps.Point(0, 0),
        // The anchor for this image is the base at anchorH, anchorW.
        new google.maps.Point(anchorH, anchorW));
    return image;
}

String.prototype.width = function () {
    var o = $('<div>' + this + '</div>')
            .css({'position': 'absolute', 'float': 'left', 'white-space': 'nowrap', 'visibility': 'hidden'})
            .appendTo($('body')),
        w = o.width();
    o.remove();
    return w;
};

//resizes the google map to fill in all white space for any device
function resizeMap() {
    window.scrollTo(0, 1);
    var aboveMap = $('div#map_canvas').offset().top;
    var aboveSearch = $('.ui-content').offset().top;
    var footerHeight = $('div#mapFooter').height();
    var totalHeight = window.innerHeight ? window.innerHeight : $(window).height();
    $('.ui-content').height(totalHeight - aboveSearch);
    $('div#map_canvas').height(totalHeight - (aboveMap + footerHeight));
}
