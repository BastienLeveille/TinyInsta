<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Profile</title>
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
  	<div data-ng-controller="controller as ctrl">
		<p>Bonjour {{ ctrl.displayUser() }}</p>
		<md-button href="/index.html" name="deconnexion"> 
            Sign out
        </md-button>
		
		<div layout="column">
			<md-toolbar>
		    	<div class="md-toolbar-tools">
		      		<h2 class="md-flex">Create your post</h2>
		    	</div>
		  	</md-toolbar>
		
		  	<md-input-container class="md-block">
	          	<label>Post</label>
	          	<textarea ng-model="ctrl.post" md-maxlength="150" rows="5" md-select-on-focus></textarea>
          	</md-input-container>
		  	<md-button ng-click="ctrl.createPost()" name="createPost"> 
	        	Create
	      	</md-button>
		</div>
		<div>
			<ul ng-repeat="item in postList">
	     		<li>Author: {{ item.propertyMap.nameAuthor }}</li> 
	     		<li >Message: {{ item.propertyMap.message }}</li>
	     		<li> Date : {{item.propertyMap.date }} </li>
	     		Like :{{ctrl.countLike(item.key.id, item.propertyMap) }}  {{item.propertyMap.countlike}}
	     		<!--  Truc pour afficher les like à l'aide de la methode countLike --> 
	     		<md-button ng-if="!item.propertyMap.liked" ng-click ="ctrl.Like(item.key.id); ctrl.changeLike(item.propertyMap); ctrl.countLike(item.key.id, item.propertyMap)" name ="like" > Like</md-button>
	     		<!-- créer fonction unlike(id du post) dans controller.js  -->
	     		<md-button ng-if="item.propertyMap.liked" ng-click ="ctrl.Unlike(item.key.id); ctrl.changeLike(item.propertyMap); ctrl.countLike(item.key.id, item.propertyMap)" name ="unlike" > Unliked</md-button>
	     	</ul>
		</div>
		<div>
			<input ng-model="ctrl.nbUsers" type = "number"></input>
			<md-button type="button" ng-click="ctrl.createNbUsers()">Post</md-button>	
		</div>
		<div>
			<input ng-model="ctrl.userToFollow" type = "text"></input>
			<md-button type="button" ng-click="ctrl.followSomeone()">Follow</md-button>	
		</div>
	</div>
  </body>
</html>
