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


cordova.define("cordova/plugin/applicationpreferences", function (require, exports, module) {
    var exec = require("cordova/exec");
    var AppPreferences = function () {
    };

    var AppPreferencesError = function (code, message) {
        this.code = code || null;
        this.message = message || '';
    };

    AppPreferencesError.NO_PROPERTY = 0;
    AppPreferencesError.NO_PREFERENCE_ACTIVITY = 1;

    AppPreferences.prototype.get = function (key, success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "get", [key]);
    };

    AppPreferences.prototype.set = function (key, value, success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "set", [key, value]);
    };

    AppPreferences.prototype.load = function (success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "load", []);
    };

    AppPreferences.prototype.show = function (activity, success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "show", [activity]);
    };

    AppPreferences.prototype.clear = function (success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "clear", []);
    };

    AppPreferences.prototype.remove = function (keyToRemove, success, fail) {
        cordova.exec(success, fail, "applicationPreferences", "remove", [keyToRemove]);
    };

    var appPreferences = new AppPreferences();
    module.exports = appPreferences;
});

console.log("Loaded ApplicationPreferences.js");
