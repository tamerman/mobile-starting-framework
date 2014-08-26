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


$(document).ready(function () {

    //$.cookie('decline_mobile_install', "no", {expires: 0});
    //$.cookie('decline_mobile_install', null);

    //Check Cookie & Cookie Expiration Time
    var cookie = $.cookie('decline_mobile_install');
    var days = 365;

    //Native App URLs
    var AndroidURL = "https://market.android.com/details?id=iu.android";
    var iPhoneURL = "itms://itunes.apple.com/us/app/iu-mobile/id383456985?mt=8";
    var BlackberryURL = "http://www.indiana.edu/~iumobile/blackberry/IUMobile.jad";


    console.log(cookie);

    //If there is no cookie or the cookie is not declining the native install
    if (cookie == null || cookie != "yes") {

        // Is the browser on an iOS device?
        if (/iPhone|iPad|iPod/i.test(navigator.userAgent)) {
            console.log("Detected iOS device.");
            // Ask the user if the want to install the native app.
            if (confirm("Would you like to install the iOS App?")) {

                // Go to the App in the App Store
                console.log("Redirecting to iTunes app store.");
                window.location = iPhoneURL;
            } else {
                // They don't want to install the native app. Remember that decision, for 365 days.
                $.cookie('decline_mobile_install', "yes", {expires: days});
            }

            // Is  the browser on a Blackberry Device?
        } else if (/Blackberry|RIM\sTablet/i.test(navigator.userAgent)) {
            console.log("Detected Blackberry device.");

            // Ask the user if the want to install the native app.
            if (confirm("Would you like to install the Blackberry App?")) {
                console.log("Redirecting to Blackberry Download.");
                window.location = BlackberryURL;
            } else {
                // They don't want to install the native app. Remember that decision, for 365 days.
                $.cookie('decline_mobile_install', "yes", {expires: days});
            }

            // Is the broser on an Android Device?
        } else if (/Android/i.test(navigator.userAgent)) {
            console.log("Detected Android device.");

            // Ask the user if the want to install the native app.
            if (confirm("Would you like to install the Android App?")) {
                console.log("Redirecting to Android Download.");
                window.location = AndroidURL;
            } else {
                // They don't want to install the native app. Remember that decision, for 365 days.
                $.cookie('decline_mobile_install', "yes", {expires: days});
            }
        } else {
            console.log("Looks like you're not on a Mobile Device");
        }
    } else {
        console.log("Finished MobileRedirector");
    }

});
