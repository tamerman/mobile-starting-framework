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


var arcGisServerUrl = 'http://maps.iu.edu/ArcGIS/rest/services/Bloomington/MapServer';

function searchBuildings() {
    findBuildings($('#buildingName').val());
}

function findBuildings(query) {
    $('#busy').show();
    clearSearchResults();
    $('#results').html('');
    var params = {
        returnGeometry: true,
        searchText: query,
        contains: true,
        layerIds: [32, 36], // building names, frat names
        searchFields: ["MAP_NAME"],
        sr: 2966
    };
    mapService.find(params, processFindResults);
}


function processFindResults(rs) {
    var html = '';
    var x = 0;
    for (var i = 0, c = rs.results.length; i < c; i++) {
        html += processResult(rs.results[i]);
    }
    $('#searchResults').html(html);
    $('#busy').hide();
}

function processResult(result) {
    var feat = result.feature;
    var a = feat.attributes;
    var title = result.value;
    if (feat.geometry) {
        var poly = feat.geometry[0];
        var b = new Object();
        b.name = title;
        b.location = new google.maps.LatLng(a['LATITUDE'], a['LONGITUDE']);
        b.iuBuildingCode = a['IU BLDG NUMBER'];
        //b.street = a['ADDRESS'];
        b.type = iuBuildingType;
        //b.state = venue.location.state;
        //b.city = venue.location.city;
        //b.zip = venue.location.postalCode;
        b.poly = poly;
        poly.setMap(map);
        searchResults.push(b);
        //poly.place = b;
        google.maps.event.addListener(poly, 'click', function () {
            setTempMarker(b);
        });
        return '<div onclick="selectSearchResult(' + (searchResults.length - 1) + ')" onmouseover="this.style.backgroundColor=\'#AAAAEE\'" onmouseout="this.style.backgroundColor=\'#FFFFFF\'">' + title + '</div>';
    }
    return '';
}

function selectSearchResult(index) {
    var place = searchResults[index];
    setTempMarker(place);
    map.panTo(place.location);
}

function clearSearchResults() {
    if (searchResults) {
        for (var i = 0; i < searchResults.length; i++) {
            searchResults[i].poly.setMap(null);
        }
        searchResults.length = 0;
    }
    $('#searchResults').html('');
}

function getPolyCenter(poly) {
    var paths, path, latlng;
    var lat = 0;
    var lng = 0;
    var c = 0;
    paths = poly.getPaths();
    for (var j = 0, jc = paths.getLength(); j < jc; j++) {
        path = paths.getAt(j);
        for (var k = 0, kc = path.getLength(); k < kc; k++) {
            latlng = path.getAt(k);
            lat += latlng.lat();
            lng += latlng.lng();
            c++;
        }
    }
    if (c > 0) {
        return new google.maps.LatLng(lat / c, lng / c);
    }
    return null;
}
