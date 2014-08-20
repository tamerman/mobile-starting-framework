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
		$.widget( "oc.pagingWidget", {
			// default options
			options: {
				/*
				 * Required
				 * 
				 * Handler for new data available.
				 * function(data)
				 * 
				 * This handler should call the addItem method
				 * to add results to the list.
				 */
				dataHandler: null,
				
				/*
				 * Required
				 * 
				 * URL which should be called to get more data.
				 * 
				 * The url will be called with the following params:
				 * page : number - next set to retrieve,
				 * size : number - number of results to retrieve
				 * 
				 * 
				 * The data returned is expected to be in the format
				 * data  : { anything your handler can manage},
				 * from  : number - results starts from,
				 * to    : number - results up to,
				 * avail : number - available number of results
				 */
				dataURL: null,
				
				/*
				 *  Flag if first set of data should be loaded when widget is created
				 */
				loadOnCreate : true,
				
				/*
				 * Optional
				 * 
				 * Callback function before a call is made to the 
				 * server to retrieve more results
				 */
				beforeServerCall : null,
				
				/*
				 * Optional
				 * Callback function when the call to the server for
				 * more data failed.
				 */
				onServerCallError : null,
				
				/*
				 * Optional
				 *  
				 * Data that will be sent to the server
				 */
				dataToServer : null,
				
				/*
				 * Number of results that should be retrieved from the 
				 * server on each call to get more
				 */
				fetchSize : 2,
				
				/*
				 * Flag if the user is allowed to click for more data
				 * If this is set the widget is more list a dynamic
				 * list, than a paging list.
				 */
				allowFetchMore : true,
				
				/*
				 * Flag if the current position in the results should b
				 * displayed at the bottom of the results
				 */
				showPosition: true,
				
				/*
				 * Required
				 * 
				 * Context path for resources
				 */
				pageContextPath : "/mdot",
				
				/*
				 * Optional
				 * 
				 * Label to display if the user can load more results
				 */
				moreLabelText : "more",
				
				/*
				 * Optional
				 * 
				 * Method which should be used to get more content.
				 * Possible values: POST or GET
				 */
				getMethod : "POST",
				
				/*
				 * Optional
				 * 
				 * The format in which the data should be requested as
				 * Possible values: json or urlencoded
				 */
				dataType : "json",
				
				/*
				 * Optional
				 * 
				 * Theme for data list items
				 * 
				 */
				widgetDataTheme : "a",
				
				/*
				 * Optional
				 * 
				 * Theme for dividers
				 */
				widgetDataDividerTheme : "a"
			},
			/*
			 * Variables for this widget
			 */
			_attrs : {
				/* Page currently being viewed (starts at 0) */
				currentPage : 0,
				/* Full number of data that can be viewed */
				dataToView : 0,
				/* Number of data currently being viewed */
				dataViewing : 0,
				/* Flag if there is more data on server */
				hasMoreToView : true
			},
			// the constructor
			_create: function() {
				
				this._resetAttrs();
				this.unorderedList = $("<ul>", {"data-role" : "listview", "data-theme" : this.options.widgetDataTheme,  "data-divider-theme" : this.options.widgetDataDividerTheme});
				
				this.progressBar = $("<div>", {"class" : "progress"});
				this.progressBar.css("display", "none");
				
				this.progressItem = $("<li>",{"id" : "progressItem", "data-icon" : "false", "data-theme" : "c"});
				var progressDiv = $("<div>");
				
				this.moreButton = $("<span>").html(this.options.moreLabelText);
				this.moreButton.css("display", "none");
				this.progressItem.click(this,function(event){
					if (event.data._attrs.hasMoreToView){
						event.data._getNextResults();
					}
				});
				
				this.loadingImage = $("<img>", {"src" : this.options.pageContextPath+"/images/writer/loader.gif"});
				this.loadingImage.css("display", "none");
				
				progressDiv.append(this.loadingImage);
				progressDiv.append(this.moreButton);
				progressDiv.append(this.progressBar);
				
				this.progressItem.append(progressDiv);
				this.unorderedList.append(this.progressItem);
				
				this.element.append(this.unorderedList);
				this.unorderedList.listview && this.unorderedList.listview();
				
				if (this.options.loadOnCreate){
					this._getNextResults();
				}
				
				// Create element
				this._refresh(); // or trigger change?
			},

			// called when created, and later when changing options
			_refresh: function() {
				// If there is unknown no of results
				if (this._attrs.dataToView == -1){
					this.progressBar.html(this._attrs.dataViewing + " / &infin;");
				}
				else {
					this.progressBar.html(this._attrs.dataViewing + " / " + this._attrs.dataToView);
				}
				
				if (this.options.allowFetchMore){
					this.progressBar.show();
				}
				else {
					this.progressBar.hide();
				}
				
				// trigger a callback/event
				this._trigger("change");
				this.unorderedList.listview && this.unorderedList.listview("refresh");
			},
			// Resets the attributes of this widget
			_resetAttrs : function(){
				this._attrs.currentPage = 0;
				this._attrs.dataToView = 0;
				this._attrs.dataViewing = 0;
				this._attrs.hasMoreToView = true;
			},
			/*
			 * Function that will clear the list of all current
			 * items and get data again
			 */
			reload : function(){
				var widget = this;
				this.clear(function(){
					widget._getNextResults()
				});
			},
			
			/*
			 * Clears the list of results
			 */
			clear : function(callback){
				this._resetAttrs();
				var widget = this;
				var done2 = function(){
					// Remove all children except the progress list item
					var children = widget.unorderedList.children(":not(li#progressItem)");
					$.each(children, function(indexInArray, listItem){
						$(listItem).slideUp(function(){
							$(this).remove(); // Remove from DOM
						});
					});
					if (callback){
						callback();
					}
				}	
				var done1 = function(){
					widget.moreButton.fadeOut(done2);
				};
				this.progressBar.fadeOut(done1);
			},
			/*
			 * Function that should be called by the handler when a new
			 * list item should be added to the list
			 */
			addItem: function (item, callback){
				
				$(item).css("display", "none");
				$(item).insertBefore(this.progressItem);
				var widget = this;
				$(item).fadeIn(100,function(){
					
					if (callback){
						callback();
					}
				});
				widget._refresh();
			},
			/*
			 * Function called on success of the ajax call
			 * to get next
			 */
			_moreResults : function (data){
				this._attrs.dataToView = data.available;
				this._attrs.dataViewing = data.to;
				var widget = this;
				this._attrs.hasMoreToView = widget.options.allowFetchMore && ( (data.available > data.to) || (data.available == -1)) &&  (data.available != 0) ;
				this.loadingImage.fadeOut(function(){
					/* 
					 * If there are no more results available
					 * we hide the more button
					 * NOTE: This code must be here in the fadeOut callback function
					 * else the button will appear while the loading image
					 * is still busy hiding
					 */
					if(widget._attrs.hasMoreToView){
						widget.moreButton.slideDown();
					}
					else{
						widget.moreButton.fadeOut();
					}
				});
				
				if(widget._attrs.hasMoreToView){
					// Make list item appear like a button
					widget.progressItem.addClass("ui-btn");
				}
				else{
					// Remove button-like appearance
					widget.progressItem.removeClass("ui-btn ui-btn-hover-c");
				}
				
				// Only show progress bar if set
				if(this.options.showPosition){
					this.progressBar.slideDown();
				}
				
				// Call the data handler
				this.options.dataHandler(data.data);
			},
			_getNextResults : function(){
				var widget = this;
				widget.moreButton.fadeOut(function(){
					widget.loadingImage.fadeIn(function(){
						widget._getData();
					});
				});
			},
			/*
			 * Function to get the next set of results
			 * The data param can be added to send
			 * data to the server
			 */
			_getData: function(){
				
				if(this.options.beforeServerCall){
					this.options.beforeServerCall();
				}
				
				var dataParams = {
					"fetchSize" : this.options.fetchSize,
					"page" 		: this._attrs.currentPage
				};
				this._attrs.currentPage = this._attrs.currentPage+1;
				// Add data object if specified
				if (this.options.dataToServer){
					dataParams.data = this.options.dataToServer;
				}
				
				var sendData;
				var sendContentType;
				if (this.options.dataType == "json"){
					sendData = JSON.stringify(dataParams);
					sendContentType = "application/json;utf-8";
				}
				else {
					sendData = dataParams;
					sendContentType = "application/x-www-form-urlencoded; charset=UTF-8";
				}
				
				
				var d = new Date();
				var n = d.getTime();
				
			    var widget = this;
				$.ajax({
					"url" 		: this.options.dataURL + "?time=" + n,
					"type" 		: this.options.getMethod,
					"data"  	: sendData,
					"dataType" 	: "json",
					"contentType": sendContentType,
					"cache"		: false,
					"success" : function(data){
						widget._moreResults(data);
					},
					"error" : function(jqXHR, textStatus, errorThrown){
						// Call the error handler if specified
						if (widget.options.onServerCallError){
							widget.options.onServerCallError();
						}
					}
				});
				
			},

			// events bound via _bind are removed automatically
			// revert other modifications here
			_destroy: function() {
				// remove generated elements
				this.changer.remove();
			},

			// _setOptions is called with a hash of all options that are changing
			// always refresh when changing options
			_setOptions: function() {
				// in 1.9 would use _superApply
				$.Widget.prototype._setOptions.apply( this, arguments );
				this._refresh();
			},

			// _setOption is called for each individual option that is changing
			_setOption: function( key, value ) {
				// in 1.9 would use _super
				$.Widget.prototype._setOption.call( this, key, value );
			}
		});

	});
