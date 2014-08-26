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


if (typeof cordova !== "undefined") {
    /**
     * Constructor
     */
    function DatePicker() {
        this._callback;
    }

    /**
     * show - true to show the ad, false to hide the ad
     */
    DatePicker.prototype.show = function (options, cb) {
        if (options.date) {
            options.date = (options.date.getMonth() + 1) + "/" + (options.date.getDate()) + "/" + (options.date.getFullYear()) + "/"
                + (options.date.getHours()) + "/" + (options.date.getMinutes());
        }
        var defaults = {
            mode: '',
            date: '',
            allowOldDates: true
        };

        for (var key in defaults) {
            if (typeof options[key] !== "undefined")
                defaults[key] = options[key];
        }
        this._callback = cb;

        return cordova.exec(cb, failureCallback, 'DatePickerPlugin', defaults.mode, new Array(defaults));
    };

    DatePicker.prototype._dateSelected = function (date) {
        var d = new Date(parseFloat(date) * 1000);
        if (this._callback)
            this._callback(d);
    };

    function failureCallback(err) {
        console.log("datePickerPlugin.js failed: " + err);
    }

    cordova.addConstructor(function () {
        if (!window.plugins) {
            window.plugins = {};
        }
        window.plugins.datePicker = new DatePicker();
    });
}
;

console.log("Loaded DatePickerPlugin.js");
