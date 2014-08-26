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


/*
 * Temporary Scope to contain the plugin.
 *  - More information here:
 *     https://github.com/apache/incubator-cordova-ios/blob/master/guides/Cordova%20Plugin%20Upgrade%20Guide.md
 */
(function () {

    /* Get local ref to global PhoneGap/Cordova/cordova object for exec function.
     - This increases the compatibility of the plugin. */
    var cordovaRef = window.PhoneGap || window.Cordova || window.cordova; // old to new fallbacks

    /*
     * This class exposes the iPhone 'icon badge' functionality to JavaScript
     * to add a number (with a red background) to each icon.
     */
    function Badge() {
    }

    /**
     * Positive integer sets the badge amount, 0 or negative removes it.
     */
    Badge.prototype.set = function (options) {
        cordovaRef.exec("Badge.setBadge", options);
    };

    /**
     * Shorthand to set the badge to 0 and remove it.
     */
    Badge.prototype.clear = function () {
        cordovaRef.exec("Badge.setBadge", 0);
    };

    cordovaRef.addConstructor(function () {
        if (!window.plugins) {
            window.plugins = {};
        }
        if (!window.plugins.badge) {
            window.plugins.badge = new Badge();
        }
    });

})();
/* End of Temporary Scope. */
