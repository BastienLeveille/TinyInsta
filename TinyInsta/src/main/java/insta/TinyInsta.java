package insta;

import java.util.ArrayList;
import java.util.Collection;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;

import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;


@Api(name = "tinyinstaAPI")
public class TinyInsta {

	@ApiMethod(name = "createUser", httpMethod = ApiMethod.HttpMethod.POST)
    public void createUser(@Named("login") String login, @Named("email") String email, @Named("pw") String pw,@Named("firstname") String firstname,@Named("lastname") String lastname) {

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

        Collection<Filter> filters = new ArrayList<Filter>();
        filters.add(new Query.FilterPredicate("login", Query.FilterOperator.EQUAL, login));
        filters.add(new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email));
        Filter filter = new Query.CompositeFilter(CompositeFilterOperator.OR, filters);
        Query query = new Query("User").setFilter(filter);
        Entity userEntity = ds.prepare(query).asSingleEntity();

        if (userEntity != null){
            throw new IllegalStateException("User already exist");
        }else{
            userEntity = new Entity("User");
            userEntity.setIndexedProperty("login", login);
            userEntity.setProperty("email", email);
            userEntity.setProperty("pw", pw);
            userEntity.setProperty("firstname", firstname);
            userEntity.setProperty("lastname", lastname);

            ds.put(userEntity);
            Entity userFollowersEntity = new Entity("UserFollowers",userEntity.getKey());
            userFollowersEntity.setProperty("followers", new ArrayList<Long>());

            ds.put(userFollowersEntity);
            this.addFollowerUser(login);

        }
    }

    private void addFollowerUser(String login) {
		// TODO Auto-generated method stub
		
	}

	/**
     * A simple method to connect using only a username
     * @param login the nickname who wants to connect
     * @return the user that just connected
     */
    @ApiMethod(name = "getUser",httpMethod = ApiMethod.HttpMethod.GET)
    public User getUser(@Named("login") String login ) {

        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Filter filter = new Query.FilterPredicate("login",FilterOperator.EQUAL, login);
        Query query = new Query("User").setFilter(filter);
        Entity userEntity = ds.prepare(query).asSingleEntity();
        // User user = ofy().load().type(User.class).filter("login", login).first().now();

        if(userEntity == null) {
            throw new NullPointerException("User not found.");
        }
        User user = new User(userEntity);
        return user;
    }
	
}
