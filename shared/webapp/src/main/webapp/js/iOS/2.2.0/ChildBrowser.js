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


// (c) 2010 Jesse MacFadyen, Nitobi


(function () {

    var cordovaRef = window.PhoneGap || window.Cordova || window.cordova; // old to new fallbacks

    function ChildBrowser() {
        // Does nothing
    }

// Callback when the location of the page changes
// called from native
    ChildBrowser._onLocationChange = function (newLoc) {
        window.plugins.childBrowser.onLocationChange(newLoc);
    };

// Callback when the user chooses the 'Done' button
// called from native
    ChildBrowser._onClose = function () {
        window.plugins.childBrowser.onClose();
    };

// Callback when the user chooses the 'open in Safari' button
// called from native
    ChildBrowser._onOpenExternal = function () {
        window.plugins.childBrowser.onOpenExternal();
    };

// Pages loaded into the ChildBrowser can execute callback scripts, so be careful to
// check location, and make sure it is a location you trust.
// Warning ... don't exec arbitrary code, it's risky and could fuck up your app.
// called from native
    ChildBrowser._onJSCallback = function (js, loc) {
        // Not Implemented
        //window.plugins.childBrowser.onJSCallback(js,loc);
    };

    /* The interface that you will use to access functionality */

// Show a webpage, will result in a callback to onLocationChange
    ChildBrowser.prototype.showWebPage = function (loc) {
        cordovaRef.exec("ChildBrowserCommand.showWebPage", loc);
    };

// close the browser, will NOT result in close callback
    ChildBrowser.prototype.close = function () {
        cordovaRef.exec("ChildBrowserCommand.close");
    };

// Not Implemented
    ChildBrowser.prototype.jsExec = function (jsString) {
        // Not Implemented!!
        //PhoneGap.exec("ChildBrowserCommand.jsExec",jsString);
    };

// Note: this plugin does NOT install itself, call this method some time after deviceready to install it
// it will be returned, and also available globally from window.plugins.childBrowser
    ChildBrowser.install = function () {
        if (!window.plugins) {
            window.plugins = {};
        }
        if (!window.plugins.childBrowser) {
            window.plugins.childBrowser = new ChildBrowser();
        }

    };


    if (cordovaRef && cordovaRef.addConstructor) {
        cordovaRef.addConstructor(ChildBrowser.install);
    } else {
        console.log("ChildBrowser Cordova Plugin could not be installed.");
        return null;
    }


})();
