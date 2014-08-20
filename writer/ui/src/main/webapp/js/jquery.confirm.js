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

(function($){
	
	//  Flag if we are doing native confirmations
	var isNativeConfirm = false;
	
	// Implementation of confirmation for native
	var confirmNative = function(params){
			
			var callbackFunction = [];
			var confirmCallback = function(option){
				callbackFunction[option-1] && callbackFunction[option-1]();
			};
			var title = params.title || "";
			var message = params.message || "";
			var buttonText = "";
			var idx = 0;
			$.each(params.buttons, function(name,obj){
				if (idx++ == 0){
					buttonText = name;
				}
				else {
					buttonText += ","+name;
				}
				callbackFunction.push(obj.action);
			});
			navigator.notification.confirm(message, confirmCallback, title, buttonText);
		};
	
	// Implementation of confirmation for web
	var confirmWeb = function(params){
		
		if($('#confirmWrapper').length){
			// A confirm is already shown on the page:
			return false;
		}
		
		var buttonHTML = '';
		$.each(params.buttons,function(name,obj){
			
			// Generating the markup for the buttons:
			
			/*buttonHTML += '<a href="#" class="button '+obj['class']+'">'+name+'<span></span></a>';*/
			if(obj['default'] == 'yes'){
				buttonHTML += '<a id="confirmDfltBtn" class="dbtn ui-btn ui-btn-inline ui-btn-corner-all ui-shadow ui-btn-up-a" href="#" data-theme="a"><span class="ui-btn-inner ui-btn-corner-all"><span class="ui-btn-text">'+name+'</span></span></a>';
			}
			else {
				buttonHTML += '<a class="dbtn ui-btn ui-btn-inline ui-btn-corner-all ui-shadow ui-btn-up-b" href="#" data-theme="b"><span class="ui-btn-inner ui-btn-corner-all"><span class="ui-btn-text">'+name+'</span></span></a>';
			}
			
			// Create default action if not specified
			if(!obj.action){
				obj.action = function(){};
			}
		});
		
		// Build title if specified
		var titleHTML = '';
		if (params.title){
			titleHTML = '<h1>'+params.title+'</h1>';
		}

		var markup = $('<div>', {id : 'confirmWrapper'})
		markup.append($('<div>', {id : 'confirmOverlay'}));
		markup.append($("<div>", {id: "confirmBox"}).html(
			'<a name="confirmBox"></a>' +
			'<ul class=" ui-listview ui-listview-inset ui-corner-all ui-shadow " data-inset="true" data-role="listview">'+
			'<li class="ui-li ui-li-static ui-body-c ui-corner-top ui-corner-bottom ui-field-contain ui-body ui-br" data-role="fieldcontain">'+
			titleHTML+
			'<p>'+params.message+'</p>'+
			'<div id="confirmButtons">'+
			buttonHTML+
			'</li></ul>'+
			'</div>'));
		
		$(markup).appendTo('body').fadeIn(function(){
			$("a#confirmDfltBtn").focus();
		});
		window.location="#";
		
		var buttons = $('#confirmBox .dbtn'),
			i = 0;

		$.each(params.buttons,function(name,obj){
			buttons.eq(i++).click(function(){
				
				// Calling the action attribute when a
				// click occurs, and hiding the confirm.
				$.confirm.hide(obj.action);
				return false;
			});
		});
	};
	
	$.confirm = function(params){
		if (navigator.notification && navigator.notification.confirm){
			isNativeConfirm = true;
			confirmNative(params);
		}
		else{
			confirmWeb(params);
		}
		
	};
	
	$.confirm.hide = function(action){
		if(!isNativeConfirm){
			$('#confirmWrapper').fadeOut(function(){
				$(this).remove();
				action();
			});
		}
		// For native we do nothing, when the user clicks a button it removes the prompt itself
	};

})(jQuery);
