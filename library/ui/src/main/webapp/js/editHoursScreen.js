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

Modernizr.load([
  {
    test : Modernizr.inputtypes.time,
    nope : [window.kme.serverDetails.getContextPath()+ "/js/jquery.mobile.datebox.min.js", 
            window.kme.serverDetails.getContextPath()+ "/css/jquery.mobile.datebox.min.css"],
    complete : function () {
    	if (!Modernizr.inputtypes.time){
    		$(document).ready(function(){
				$("input[data-role=datebox]").each(function() {
					if ( typeof($(this).data('datebox')) === "undefined" ) {
						$(this).datebox();
					}
				});
			});
    	}
    }
  }
]);


$(document).ready(function(){
	
	$("#btnSave").click(function(){
		
		var valid = true;// TODO Validate
		
		if (valid){
			$("#libraryHoursForm").submit();
		}
	});
	
	// lister for close button clicks
	$("input[type=checkbox]").filter(function(){
		 return this.id.match(/s[0-9]+h[0-9]+closed/);
	}).change(function(){
		
		// Get the coordinates of this hour
		var hourId = this.id.match(/s[0-9]+h[0-9]+/);
		
		// LIbrary is set to be closed
		if($(this).is(":checked")){
			$("div#"+ hourId + "fromTimeGroup").slideUp(function(){
				$("div#"+ hourId + "toTimeGroup").slideUp();
			});
		}
		// Library is set to be open
		else {
			$("div#"+ hourId + "fromTimeGroup").slideDown(function(){
				$("div#"+ hourId + "toTimeGroup").slideDown();
			});
		}
	});
});
