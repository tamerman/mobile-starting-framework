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


function onBodyLoad() {
    document.addEventListener("deviceready", onDeviceReady, false);
    //alert("onBodyLoad");
}


/* When this function is called, PhoneGap has been initialized and is ready to roll */
/* If you are supporting your own protocol, the var invokeString will contain any arguments to the app launch.
 see http://iphonedevelopertips.com/cocoa/launching-your-own-application-via-a-custom-url-scheme.html
 for more details -jm */
function onDeviceReady() {

    otherDeviceReadyFunctions();
    setupEnvironment();
}

function setupEnvironment() {
    checkConnection();
}

function checkConnection() {
    var networkState = navigator.network.connection.type;
    if (networkState == 'unknown' || networkState == 'none') {
        navigator.notification.alert('Network connection unavailable.  Internet connectivity is required for Kuali Mobile.  Please connect to WiFi or 3G and try again.', setupEnvironment, 'Kuali Mobile', 'Try Again');
    } else {
        document.addEventListener("offline", looksLikeWeAreOffline, false);
    }
}

function looksLikeWeAreOffline() {
    navigator.notification.alert('Network connection unavailable.  Internet connectivity is required for IU Mobile.', offlineAction, 'IU Mobile', 'OK');
}

function offlineAction() {
    // Do Nothing
}	
