<div class="modal-header">
	<h3>Bus Alerts</h3>
</div>
<div class="modal-body" style="padding:0px;">
	<carousel interval="alertInterval" style="background-color:#E1E1E1;">
		<slide ng-repeat="alert in alerts">
			<div class="alertMessage">
				<p style="color:\#{{alert.color}}">{{alert.message}}</p>
			</div>
		</slide>
	</carousel>
</div>