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

$(function() {
	$("li.extra").hide();
	$("li.expander").show();
});

// added the exp and col arguments which are i18n words meaning "expand" and "collapse" respectively. 
// kludgey sure, working with what I got.
function toggleVisibility(feedClass, exp, col) {
	var expander = $('li.expander.' + feedClass);
	var classes = 'li.extra';
	if (feedClass){
		classes += '.' + feedClass;
	}
	var elements = $(classes);
			
	if (expander.hasClass('collapsed')){
		elements.stop(true, true).slideDown();
		expander.removeClass('collapsed');
		var icon = $('li.expander.' + feedClass + ' span.ui-icon');
		icon.removeClass('ui-icon-arrow-d');
		icon.addClass('ui-icon-arrow-u');
		$('li.expander.' + feedClass + ' p.ui-li-desc strong').html(col);
	} else {
		elements.stop(true, true).slideUp();
		expander.addClass('collapsed');
		var icon = $('li.expander.' + feedClass + ' span.ui-icon');
		icon.removeClass('ui-icon-arrow-u');
		icon.addClass('ui-icon-arrow-d');
		$('li.expander.' + feedClass + ' p.ui-li-desc strong').html(exp);
	}

}
