<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>

	<meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Create an account</title>
    <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.12/angular-material.min.css">
    
    <!-- Angular Material requires Angular.js Libraries -->
	  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular.min.js"></script>
	  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-animate.min.js"></script>
	  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-aria.min.js"></script>
	  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-messages.min.js"></script>
	  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.7.6/angular-cookies.min.js"></script>
	
	<!-- Angular Material Library -->
	<script src="https://ajax.googleapis.com/ajax/libs/angular_material/1.1.12/angular-material.min.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  	<script type="text/javascript" src="controller.js"></script>

    
  </head>
  
  <body data-ng-app="tinyinsta">
  		<h2>Créer votre compte</h2>
		
		<md-content layout-padding ng-cloak>
	    	<div data-ng-controller="controller as ctrl">
	 			<div layout-gt-sm="row">
	          		<md-input-container class="md-block" flex-gt-xs>
	            		<label>Pseudo</label>
	           			<input ng-model="ctrl.pseudo">
	          		</md-input-container>
	
	          		<md-input-container class="md-block" flex-gt-xs>
	            		<label>Password</label>
	            		<input type="password" ng-model="ctrl.password">
	         		</md-input-container>
				</div>
			
				<div layout-gt-sm="row">
	          		<md-input-container class="md-block" flex-gt-xs>
	            		<label>Mail</label>
	            		<input ng-model="ctrl.mail">
	          		</md-input-container>
	
	          		<md-input-container class="md-block" flex-gt-xs>
	            		<label>Name</label>
	            		<input ng-model="ctrl.name">
	          		</md-input-container>
	
	        		<md-input-container class="md-block" flex-gt-xs>
	          			<label>First Name</label>
	          			<input ng-model="ctrl.firstName">
	        		</md-input-container>
	        
	        	</div>
	        	<div layout-gt-sm="row">
	        		<md-button ng-click="ctrl.createUser()" name="createUser"> 
	        		Create
	        		</md-button>
	        	</div>
	    	</div>   
	    </md-content>
	</body>
</html>