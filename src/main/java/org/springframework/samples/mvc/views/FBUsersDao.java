package org.springframework.samples.mvc.views;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by psekar on 10/21/17.
 */
@Repository
public class FBUsersDao {

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public User getUsers(String userId) {
        Query searchUserQuery = new Query(Criteria.where("userId").is(userId));
        User user = mongoTemplate.findOne(searchUserQuery, User.class);
        return user;
    }

    public void addUsers(User user) {
        mongoTemplate.save(user);
    }
    
	public void deleteApp() {
		//"\n Remove all users : ");
		

		mongoTemplate.remove(User.class);
		
		//"\n Remove all users : ");
		mongoTemplate.remove(EventDetails.class);
		
		// remove fb events
		mongoTemplate.remove(FBEvents.class);
		
		// remove fb events
		mongoTemplate.remove(FBEvents.class);

	}

    public User getUserByMobile(String mobile) {
        Query searchUserQuery = new Query(Criteria.where("number").is(mobile));
        return mongoTemplate.findOne(searchUserQuery, User.class);

    }

    public List<User> getUsers() {
        return mongoTemplate.findAll(User.class);
    }

}
