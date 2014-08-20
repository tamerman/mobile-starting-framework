<%--
Copyright 2014 The Kuali Foundation Licensed under the Educational
Community License, Version 2.0 (the "License"); you may not use this file
except in compliance with the License. You may obtain a copy of the License
at http://www.osedu.org/licenses/ECL-2.0 Unless required by applicable law or
agreed to in writing, software distributed under the License is distributed
on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied. See the License for the specific language governing
permissions and limitations under the License.
--%>
<%@ page language="java" contentType="application/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

angular.module("academicsI18nModule",[])
.factory('AcademicsI18N',[function(){

    var getString = function(code, params){
        var string = strings[code];
        if (string == null){
            return code;
        }
        else{
            /**
             * If there are params to insert, we will insert them
             */
            if (params != null && Array.isArray(params)){
                for(var i = 0 ; i < params.length ; i++){
                    string = string.replace("{"+i+"}", params[i]);
                }
            }
            return string;

        }
    };

    var strings = {
        'academics.searchClasses' : '<spring:message code="academics.searchClasses"/>',
        // Add all strings you require below
        'getString'  : getString
    };



	return strings;
}]);