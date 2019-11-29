var app = angular.module('TinyInsta', []);
app.controller('TController',['$scope','$window', function($scope,$window) {
    
    $scope.listpost = [{author: 'admin',message :'Hello and Welcome to Tiny Insta'}];
    $scope.author = '';
    $scope.login ='';
    $scope.message;
    $scope.log = false;
    $scope.pseudo ='';
	$scope.email ='';
	$scope.mdp ='';
	$scope.nom='';
    $scope.prenom ='';
    $scope.tnbfollowers = 0;
    $scope.listtemps = [];
    

    $scope.register = function(){
        console.log("inscription passe? avant if v3");
		if($scope.log == false){
            console.log("inscription passe? apres if v3");
            console.log(gapi.client.tinyinstaAPI);
                 gapi.client.tinyinstaAPI.createUser({
				login: $scope.pseudo,
				email: $scope.email,
				pw: $scope.mdp,
				firstname: $scope.nom,
				lastname: $scope.prenom
			}).execute(function(resp){
                console.log(resp);
                if(resp.code != 503){
                    $scope.login = $scope.pseudo;
                    $scope.author = $scope.pseudo;
                    $scope.log = true;
                    console.log($scope.author);
                    console.log(" is connected");
                    $scope.$apply();
                }
			});
        }
    }

$window.init = function() {
        	    console.log("windowinit called");
           var rootApi = 'https://tinyinsta.appspot.com/_ah/api/';
          //var rootApi = 'https://tinitouit.appspot.com/_ah/api/';

          console.log("connexion marche?");
	      gapi.client.load('tinyinstaAPI', 'v1', function() {
	        console.log("insta api loaded");
	        $scope.log = false;
	      }, rootApi);
	 }

/// cle de lAPI : AIzaSyAR1QpGGnrtBHjZLW4JAniiOH4trxWCuCM
}
]);