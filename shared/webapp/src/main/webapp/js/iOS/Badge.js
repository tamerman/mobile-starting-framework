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


/**
 * This class exposes the iPhone 'icon badge' functionality to JavaScript
 * to add a number (with a red background) to each icon
 * @constructor
 */
function Badge() {
}

/**
 * Positive integer sets the badge, 0 or negative clears it
 */
Badge.prototype.set = function (options) {
    PhoneGap.exec("Badge.setBadge", options);
};

/**
 * Shorthand to set the badge to 0
 */
Badge.prototype.clear = function () {
    PhoneGap.exec("Badge.setBadge", 0);
};

/**
 * Shorthand to set the badge to 0
 */
Badge.prototype.add = function (options) {
    PhoneGap.exec("Badge.addBadge", options);
};

PhoneGap.addConstructor(function () {
    if (!window.plugins) {
        window.plugins = {};
    }
    window.plugins.badge = new Badge();
});
