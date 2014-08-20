/*
  The MIT License (MIT)
  
  Copyright (C) 2014 by Kuali Foundation

  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
 
  The above copyright notice and this permission notice shall be included in

  all copies or substantial portions of the Software.
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/

//
//  ActionSheet.js
//
// Created by Olivier Louvignes on 11/27/2011.
//
// Copyright 2011 Olivier Louvignes. All rights reserved.
// MIT Licensed

function ActionSheet() {}

ActionSheet.prototype.create = function(title, items, fn, options) {
	if(!options) options = {};

	var service = 'ActionSheet',
		action = 'create',
		callbackId = service + (PhoneGap.callbackId + 1);

	var config = {
		title : title+'' || '',
		items : items || ['Cancel'],
		callback : fn || function(){},
		scope: options.hasOwnProperty('scope') ? options.scope : null,
		style : options.hasOwnProperty('style') ? options.style+'' : 'default',
		destructiveButtonIndex : options.hasOwnProperty('destructiveButtonIndex') ? options.destructiveButtonIndex*1 : undefined,
		cancelButtonIndex : options.hasOwnProperty('cancelButtonIndex') ? options.cancelButtonIndex*1 : undefined
	};

	var callback = function(result) {
		var buttonValue = false, // value for cancelButton
			buttonIndex = result.buttonIndex;

		if(!config.cancelButtonIndex || buttonIndex != config.cancelButtonIndex) {
			buttonValue = config.items[buttonIndex];
		}

		config.callback.call(config.scope || null, buttonValue, buttonIndex);
	};

	PhoneGap.exec(callback, callback, service, action, [config]);
};

PhoneGap.addConstructor(function() {
	if(!window.plugins) window.plugins = {};
	window.plugins.actionSheet = new ActionSheet();
});
