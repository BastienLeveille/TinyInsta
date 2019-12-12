var app = angular.module('tinyinsta',['ngMaterial', 'ngMessages', 'ngCookies']).config(['$mdIconProvider', function($mdIconProvider) {

    }]);
app.controller('controller', ['$cookies', '$http', '$scope', '$mdDialog', function($cookies, $http, $scope, $mdDialog){

	var self = this;
	this.name = '';
	this.firstName = '';
	this.mail = '';
	this.pseudo = '';
	this.password = '';
	this.identifiant = '';
	this.mdp = '';
	this.post = '';
	this.nbUsers = 0;
	this.userToFollow = '';
	this.message = '';
	$scope.postList = $scope.postList || [];
	
	this.changeLike = function(item){
		if(item.liked){
			item.liked = false;
		}
		else{
			item.liked = true;
		}
	}
	
	this.createUser = function(){
	   if(this.pseudo !== '' && this.password !== '' && this.mail !== '' && this.name !== '' && this.firstName !== ''){
		   $cookies.put('user', this.pseudo);
		   alert(this.pseudo +"+"+ this.password+ " " + this.name);
		   $.ajax({
		        url : '/createUser',
		        type : 'POST',
		        data : {pseudo:this.pseudo, mdp : this.password, mail: this.mail, nom:this.name, prenom:this.firstName },
		        dataType : 'html',
		        success : function(data, statut)
		        {
		        	alert("connexion");
		        	console.log(data);
		        	window.location.replace("/test")
		        	
		        } ,
		        error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
		        alert("Pseudo déja existant")}	});
		}
		else {
			alert("pomme");	
		}
   }
	
   
   this.displayUser = function(){
	   return $cookies.get('user');
   }
   
   this.connexion = function() {
		if (this.identifiant !=='' && this.mdp !=='') {
			console.log(this.identifiant +" "+ this.mdp);
			$cookies.put('user', this.identifiant);
		$.ajax({
	        url : '/login',
	        type : 'POST',
	        data : {pseudo:this.identifiant, mdp : this.mdp},
	        dataType : 'html',
	        success : function(data, statut)
	        {
	        	//alert("connexionv2");
	        	console.log(data);
	        	window.location.replace("/test")
	        	
	        } ,
	        error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
	        alert("Pseudo  ou mdp incorrect")}	});
		}
		else {
			console.log(this.identifiant);
			alert("pomme");	
		}
   }
   
   this.createPost = function(){
	    var pseudo = $cookies.get('user');
	    
		if (this.post !== "" ) {	
			$scope.postList.push(this.post);
			console.log($scope.postList);
			$.ajax({
		        url : '/createPost',
		        type : 'POST',
		        data : {pseudo:pseudo, post : this.post},
		        dataType : 'html',
		        success : function(data, statut)
		        {
		        	alert("Post Créé");
		        	console.log(data);
		        	window.location.replace("/test")
		        	
		        } ,
		        error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
		        alert("Problème dans la création du post")}	});
		}
		else {
			console.log(this.post);
			alert("pomme");	
		}
		
	}
   
   this.displayPosts = function(callback) {
	   var pseudo = $cookies.get('user');
	   $.ajax({
	        url : '/afficherPost',
	        type : 'GET',
	        data : {pseudo:pseudo},
	        dataType : 'json',
	        success : function(data, statut)
	        {
	        	callback(data);
	        	$scope.postList = data;
	        } ,
	        error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
	        alert("Erreur dans l'affichage du fil d'actu")}	});
	}
   
   this.myCallback = function(data){
	   this.postList = data;
   }
   
   this.displayPosts(this.myCallback);
   
   this.createNbUsers = function() {
		if (this.nbUsers > 0) {
			var pseudo = $cookies.get('user');
			
			$.ajax({
				url : '/nbUsers',
				type : 'GET',
				data : {pseudo: pseudo, nbUsers: this.nbUsers},
				dataType : 'html',
				success : function(data, statut)
				{
					alert("creation des Followers");
					window.location.replace("/test")
	        	
				} ,
				error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
				alert("Erreur dans la creation des nb Follwers")}	});
		} else {
			alert("création impossible nb <=	 0")
		}

	}
   
   this.followSomeone = function() {
		if (this.userToFollow !== "") {
			var pseudo = $cookies.get('user');
			$.ajax({
				url : '/followSomeone',
				type : 'GET',
				data : {pseudo:pseudo, user:this.userToFollow},
				dataType : 'html',
				success : function(data, statut)
				{
					alert("now you follow " + this.userToFollow);
					window.location.replace("/test")
	        	
				} ,
				error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
				alert("you can't follow an inexistant user")}	});
		} 

	}
   
   this.Like = function(idPost) {
		var pseudo = $cookies.get('user');
		$.ajax({
			url : '/Like',
			type : 'POST',
			data : {pseudo:pseudo, idPost:idPost, likeornot : true},
			dataType : 'html',
			success : function(data, statut)
			{
				//alert("Like succes");
				//window.location.replace("/test")
       	
			} ,
			error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
			alert("Tu peux pas like")}	});
		} 
   
   this.Unlike = function(idPost) {
		var pseudo = $cookies.get('user');
		$.ajax({
			url : '/Like',
			type : 'POST',
			data : {pseudo:pseudo, idPost:idPost, likeornot : false},
			dataType : 'html',
			success : function(data, statut)
			{
				//alert("UnLike succes");
				//window.location.replace("/test")
      	
			} ,
			error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
			alert("Tu peux pas like")}	});
		} 
   
   this.countLike = function(idPost, item) {
	   var countlike;
	   $.ajax({
			url : '/Like',
			type : 'GET',
			data : {idPost:idPost},
			dataType : 'html',
			success : function(data, statut)
			{
				//alert();	
				console.log("affichage des likes");
				console.log(data);
				item.countlike = data;
				//countlike = data;
				//window.location.replace("/test")
				//return (countlike);
      	
			} ,
			error : function (jqXHR, textStatus, errorThrown) { console.log("erreur");
			//alert("Tu peux pas like")
			}	
		});
		 
   }
   
}]);