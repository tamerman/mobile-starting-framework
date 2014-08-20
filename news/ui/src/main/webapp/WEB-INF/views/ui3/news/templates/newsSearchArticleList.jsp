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
<div ng-controller="NewsSearchController" ng-init="init()">
	<ng-include
		src="'${pageContext.request.contextPath}/news/templates/searching'"></ng-include>

	<div ng-show="tempobj.showSearchResult == 0">
		<div ng-controller="NewsArticleController">
			<ng-include
				src="'${pageContext.request.contextPath}/news/templates/newsArticleList'"></ng-include>
		</div>
	</div>
</div>
