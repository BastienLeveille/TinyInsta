package tinyinsta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


public class FollowersFollowees {

	private final int TRANSACTION_RETRIES = 4; // le nombre d'essaye  pour une transaction de commit
	private final int LIKE_COUNTER_MAX_SHARD = 20; //Numbre de  conteneur pour un compteur de like, peut etre augmente pour allouer plus de memoire

	public FollowersFollowees() {
	}
	public void addFollowerUser( String pseudoFollowedFollower) {

		DatastoreService ds; 
		Filter filter;
		Query query;
		Entity entity, followed, follower, userFollowers;

		ds= DatastoreServiceFactory.getDatastoreService();
		filter = new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, pseudoFollowedFollower);
		query= new Query("User").setFilter(filter);
		entity = ds.prepare(query).asSingleEntity();

		System.out.println("addFollowerUser");
		if(entity == null){System.out.println("error");throw new NullPointerException("Entity null");}

		followed = entity;
		follower = entity;
		query = new Query("UserFollowers").setAncestor(followed.getKey());
		userFollowers = ds.prepare(query).asSingleEntity();


		@SuppressWarnings("unchecked")
		ArrayList<String> followers = (ArrayList<String>) userFollowers.getProperty("followers");
		if(followers == null){followers = new ArrayList<String>();}

		basicFollow(ds, follower, userFollowers, followers);
	}

	private void basicFollow(DatastoreService ds, Entity follower, Entity userFollowers, ArrayList<String> followers) {
		if(followers.contains((String) follower.getProperty("pseudo"))){
			System.out.println("basicFollow error");
			throw new NullPointerException("Already following this user");
		}else{
			followers.add((String) follower.getProperty("pseudo"));
			userFollowers.setProperty("followers", followers);
			ds.put(userFollowers);
		}
	}

	public void followUser( String followerpseudo,  String followedpseudo) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Collection<Filter> filters = new ArrayList<Filter>();
		filters.add(new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, followerpseudo));
		filters.add(new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, followedpseudo));
		Filter filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
		Query query = new Query("User").setFilter(filter);
		List<Entity> entities = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());

		if(entities == null){throw new NullPointerException("Entities null");}

		Entity followed = null;
		Entity follower = null;

		for(Entity user : entities){
			String pseudo = (String) user.getProperty("pseudo");
			if(pseudo == null){throw new NullPointerException("pseudo null");}
			if(pseudo.equals(followedpseudo)){followed = user;}
			else{follower = user;}
		}

		if (follower == null){
			throw new NullPointerException("Follower user not found"+followerpseudo);
		}
		if(followed == null){
			throw new NullPointerException("Followed user not found");
		}
		System.out.println("followed "+ followed.getProperty("pseudo"));
		System.out.println("follower "+ follower.getProperty("pseudo"));

		System.out.println(followed.getKey());
		
		query = new Query("UserFollowers").setAncestor(followed.getKey());
		Entity userFollowers = ds.prepare(query).asSingleEntity();
		System.out.println("userfollower "+ userFollowers.getProperty("followers"));

		

		@SuppressWarnings("unchecked")
		ArrayList<String> followers = (ArrayList<String>) userFollowers.getProperty("followers");

		if(followers == null){followers = new ArrayList<String>();}

		basicFollow(ds, follower, userFollowers, followers);
	}

	public ArrayList<String> createNbUsers(int nbUsers) {

		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

		ArrayList<String> listpseudo = new ArrayList<>();

		String pseudo;
		String mail;
		String mdp;
		String nom;
		String prenom;
		Entity user;

		System.out.println("Creation des nb users" + nbUsers);

		for (int i = 1; i <= nbUsers; i++) {

			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			String rand = "";

			for (int j = 1; j <= 10; j++) {
				int k = (int) Math.floor(Math.random() * 62);
				rand += chars.charAt(k);
			}
			pseudo = "user" + rand;
			mail = "mail" + rand + "@supermail.com";
			mdp = "mdp" + rand;
			nom = "firtsname" + rand;
			prenom = "prenom" + rand;

			System.out.println("creation de ENtity");
			System.out.println("pseudo " + pseudo);
			createUser(pseudo, mail, mdp, nom, prenom);

			listpseudo.add(pseudo);
		}
		return listpseudo;
	}

	public void createNbFollowers( int nbFollowers, String followed) {

		Entity user;
		System.out.println("followed "+followed);
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, followed);
		Query query = new Query("User").setFilter(filter);
		Entity userEntity = ds.prepare(query).asSingleEntity();

		if (userEntity == null) {
			user = new Entity("User");
			user.setIndexedProperty("pseudo", followed);
			user.setProperty("mail", followed + "@mail.com");
			user.setProperty("mdp", followed + "IncrediblePassword");
			user.setProperty("nom", "Etienne-Eudes");
			user.setProperty("prenom", "Durand");
		}

		System.out.println("on va aficher la list des pseudos qui vont nous suivre");
		ArrayList<String> listLogin = new ArrayList<>();
		listLogin = createNbUsers(nbFollowers);

		System.out.println("la taille de la liste est " + listLogin.size());
		for (int i = 0; i < listLogin.size(); i++) {
			System.out.println("Pseudo : " + listLogin.get(i));
		}

		// il doit manquer un truc pour put les user creer dans la data base et dans la
		// list de followed
		for (int i = 0; i < listLogin.size(); i++) {
			System.out.println(listLogin.get(i));
			followUser(followed,  listLogin.get(i));

			ArrayList<String> listPost = new ArrayList<>();
			listPost.add("Controle de Web semantique et Web de données Mardi matin ^^'");
			listPost.add("Dans la merde, on a pas commencé les projets à rendre la semaine pro ! ;(");
			listPost.add("Bientôt Noël !! =D");
			listPost.add("Hâte d'être en vacances pour profiter des fêtes et plus avoir de projet à rendre!");
			listPost.add("Ah cheh ! On a un projet à rendre pour après les vac' ! :')");
			int k = (int) Math.floor(Math.random() * 5);
			createPost(listLogin.get(i), listPost.get(k));
		}
	}

	public List<Post> getTimeline(String login) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("login", Query.FilterOperator.EQUAL, login);
		Query query = new Query("User").setFilter(filter);
		Entity userEntity = ds.prepare(query).asSingleEntity();

		if (userEntity == null)
			throw  new NullPointerException("User not found." + login);

		Long id = userEntity.getKey().getId();

		filter = new Query.FilterPredicate("receivers", Query.FilterOperator.EQUAL, id);
		query = new Query("TwittIndex").setFilter(filter).setKeysOnly();

		List<Entity> twittKeysEntity = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());

		if (twittKeysEntity == null)
			throw new NullPointerException("No twitt found");

		List<Key> keys = new ArrayList<Key>();
		for(Entity e : twittKeysEntity){
			Key k = e.getParent();
			keys.add(k);
		}

		Map<Key, Entity> map = ds.get(keys);
		List<Entity> list =  new ArrayList<Entity>(map.values());

		List<Post> result = new ArrayList<Post>();
		for(Entity e : list){
			result.add(new Post(e));
		}

		//Collections.sort(result);
		//Collections.reverse(result);
		return result;

	}

	public void createPost(String pseudo,String message)  {
		//User twittos = ofy().load().type(User.class).filter("login", login).first().now();

		Entity postEntity ;

		System.out.println("on est dans le create post");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, pseudo);
		Query query = new Query("User").setFilter(filter);
		Entity userEntity = ds.prepare(query).asSingleEntity();
		Users userid = this.getUser(pseudo);

		if (userid == null){throw new NullPointerException("User not found");}
		postEntity = new Entity("Post");
		postEntity.setProperty("idAuthor", userid.getId());
		postEntity.setProperty("nameAuthor", pseudo);
		postEntity.setProperty("message", message);
		postEntity.setProperty("date", new Date());
		ds.put(postEntity);

		query = new Query("UserFollowers").setAncestor(userEntity.getKey());
		Entity userFollowersEntity = ds.prepare(query).asSingleEntity();

		if (userFollowersEntity == null){throw new NullPointerException("No followers found.");}

		@SuppressWarnings("unchecked")
		ArrayList<Long> followers = (ArrayList<Long>) userFollowersEntity.getProperty("followers");

		Entity postIndex = new Entity("PostIndex", postEntity.getKey());
		postIndex.setProperty("receivers", followers);
		ds.put(postIndex);

	}


	public Users getUser( String pseudo ) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("pseudo",FilterOperator.EQUAL, pseudo);
		Query query = new Query("User").setFilter(filter);
		Entity userEntity = ds.prepare(query).asSingleEntity();
		// User user = ofy().load().type(User.class).filter("login", login).first().now();

		if(userEntity == null) {
			throw new NullPointerException("User not found.");
		}
		Users user = new Users(userEntity);
		return user;
	}

	public void createUser( String pseudo, String mail, String mdp, String nom, String prenom) {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		Collection<Filter> filters = new ArrayList<Filter>();
		filters.add(new Query.FilterPredicate("pseudo", Query.FilterOperator.EQUAL, prenom));
		filters.add(new Query.FilterPredicate("mail", Query.FilterOperator.EQUAL, mail));
		Filter filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
		Query query = new Query("User").setFilter(filter);
		Entity userEntity = ds.prepare(query).asSingleEntity();

		if (userEntity != null){
			throw new IllegalStateException("User already exist");
		}else{
			userEntity = new Entity("User");
			userEntity.setIndexedProperty("pseudo", pseudo);
			userEntity.setProperty("mail", mail);
			userEntity.setProperty("mdp", mdp);
			userEntity.setProperty("nom", nom);
			userEntity.setProperty("prenom", prenom);

			ds.put(userEntity);
			Entity userFollowersEntity = new Entity("UserFollowers",userEntity.getKey());
			userFollowersEntity.setProperty("followers", new ArrayList<Long>());

			ds.put(userFollowersEntity);
			this.addFollowerUser(pseudo);

		}
	}
	
	@SuppressWarnings("unchecked")
	public boolean createlike(String pseudo, Long idMessage) {
		// User twittos = ofy().load().type(User.class).filter("login",
		// login).first().now();
		Entity likeEntity;

		System.out.println("on est dans le create like");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		Random random = new Random();
		Key likeKey;
		Long CompteurLike;
		ArrayList<String> listUserIndex;
		
		int essaie = 0;
		int delay = 1; 
		boolean likeCreate = true;
		System.out.println("deja like par " + pseudo + " ? "+isAlreadyLikeBy(idMessage, pseudo));
		if (!isAlreadyLikeBy(idMessage, pseudo)) {
			
			
			while (likeCreate && essaie<=TRANSACTION_RETRIES) {
	
				Transaction trans = ds.beginTransaction();
				likeEntity = null;
			
				try {
					
					int randomLikeCounter = random.nextInt(LIKE_COUNTER_MAX_SHARD)+1;
					likeKey = KeyFactory.createKey("Like", idMessage + randomLikeCounter);
					
					try {
						likeEntity = ds.get(likeKey);
					} catch(EntityNotFoundException e) {
						
						likeEntity = new Entity("Like", idMessage + randomLikeCounter);
						likeEntity.setProperty("idPost", idMessage);
						likeEntity.setProperty("listUser", new ArrayList<String>());
						likeEntity.setProperty("CompteurDeLike", new Long(0));
						
					}
					
					CompteurLike = (Long) likeEntity.getProperty("CompteurDeLike");
					likeEntity.setProperty("CompteurDeLike", CompteurLike + 1);
					listUserIndex =(ArrayList<String>) likeEntity.getProperty("listUser");
					if (listUserIndex == null) {
						listUserIndex = new ArrayList<String>();
					}
					
					//Le pseudo etant unique on peux le retrouver et à l'aide du HashSet on ne peux avoir de doublons
					listUserIndex.add(pseudo);
					likeEntity.setProperty("listUser", listUserIndex);
					ds.put(likeEntity);
					trans.commit();
					
					System.out.println("le like a bien ete creer ");
					return likeCreate;
	
									
				} catch(ConcurrentModificationException e) {
					if (essaie >= TRANSACTION_RETRIES) {
						throw e;
					}
					++essaie;
				} finally {
					if (trans.isActive()) {
						trans.rollback();
					}
				}
				
				try {
					Thread.sleep(delay * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				delay *= 2; 
	
			}
		}	
		return false;
	}

	
	@SuppressWarnings("unchecked")
	public boolean Unlike(String pseudo, Long idMessage) {
		// User twittos = ofy().load().type(User.class).filter("login",
		// login).first().now();
		Entity likeEntity;

		System.out.println("on est dans le unlike");
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		Random random = new Random();
		Key likeKey;
		Long CompteurLike;
		List<Entity> listLike ;
		ArrayList<Entity> listUser;
				
		int essaie = 0;
		int delay = 1; 
		boolean unlike = true;
		System.out.println("deja like par " + pseudo + " ? "+isAlreadyLikeBy(idMessage, pseudo));
		

		Filter filter = new Query.FilterPredicate("idPost", FilterOperator.EQUAL, idMessage);
		System.out.println(idMessage);
		Query query = new Query("Like").setFilter(filter);
		listLike = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
		System.out.println("ici");
		try {
			System.out.println(listLike.size());
			for (Entity like : listLike) {
				listUser = (ArrayList<Entity>) like.getProperty("listUser");
				try {
					System.out.println(listUser);
					if (listUser.contains(pseudo)) {
						System.out.println(listUser);
						listUser.remove(pseudo);
						System.out.println(listUser);
						Long compteur = (Long) like.getProperty("CompteurDeLike");
						like.setProperty("listUser", listUser);
						like.setProperty("CompteurDeLike", compteur-1);
						System.out.println(like.getProperty("listUser"));
						ds.put(like);

					}
				} catch (Exception e) {
					System.out.println("pommmmmme");
				}
				
			}
		} catch (Exception e) {
			System.out.println("couilloon");
		}
		
		return false;
	}
	private boolean isAlreadyLikeBy(Long idPost, String User) {
		boolean alreadyLike = false;
		List<Entity> listLike ;
		ArrayList<Entity> listUser;
		
		
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Filter filter = new Query.FilterPredicate("idPost", FilterOperator.EQUAL, idPost);
		System.out.println(idPost);
		Query query = new Query("Like").setFilter(filter);
		listLike = ds.prepare(query).asList(FetchOptions.Builder.withDefaults());
		System.out.println("ici");
		try {
			System.out.println(listLike.size());
			for (Entity like : listLike) {
				listUser = (ArrayList<Entity>) like.getProperty("listUser");
				try {
					System.out.println(listUser);
					if (listUser.contains(User)) {
						alreadyLike = true;
					}
				} catch (Exception e) {
					System.out.println("pas de list");
				}
				
			}
		} catch (Exception e) {
			System.out.println("couilloon");
		}
		
		
		return alreadyLike;
	}
}
