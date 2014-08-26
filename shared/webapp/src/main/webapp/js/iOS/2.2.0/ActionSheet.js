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


//
//  ActionSheet.js
//
// Created by Olivier Louvignes on 2011-11-27.
//
// Copyright 2011-2012 Olivier Louvignes. All rights reserved.
// MIT Licensed

(function (cordova) {

    function ActionSheet() {
    }

    ActionSheet.prototype.create = function (options, callback) {
        options || (options = {});
        var scope = options.scope || null;

        var config = {
            title: options.title || '',
            items: options.items || ['Cancel'],
            style: options.style || 'default',
            destructiveButtonIndex: options.hasOwnProperty('destructiveButtonIndex') ? options.destructiveButtonIndex : undefined,
            cancelButtonIndex: options.hasOwnProperty('cancelButtonIndex') ? options.cancelButtonIndex : undefined
        };

        var _callback = function (result) {
            var buttonValue = false, // value for cancelButton
                buttonIndex = result.buttonIndex;
            if (!config.cancelButtonIndex || buttonIndex != config.cancelButtonIndex) {
                buttonValue = config.items[buttonIndex];
            }
            if (typeof callback == 'function') callback.apply(scope, [buttonValue, buttonIndex]);
        };

        return cordova.exec(_callback, _callback, 'ActionSheet', 'create', [config]);
    };

    cordova.addConstructor(function () {
        if (!window.plugins) window.plugins = {};
        window.plugins.actionSheet = new ActionSheet();
    });

})(window.cordova || window.Cordova);
