package com.userservice.repository.impl;

import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import com.userservice.repository.impl.enums.SelectUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;


    @Override
    public List<User> findAll() {
        return mongoTemplate.findAll(User.class, "users");
    }

    @Override
    public ResponseEntity<Void> delete(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(user.getUserName()).and("email").is(user.getEmail()));
        mongoTemplate.findAndRemove(query, User.class, "users");
        return ResponseEntity.ok().build();
    }

    @Override
    public User insert(User user) {
        return mongoTemplate.insert(user, "users");
    }

    @Override
    public boolean exists(User user) {
        Query exists = new Query();
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("userName").is(user.getUserName()),
                Criteria.where("email").is(user.getEmail())
        );
        exists.addCriteria(criteria);
        return mongoTemplate.exists(exists, User.class, "users");
    }

    /**
     * return true if provided enum SelectUser doesn't exist
     */
    @Override
    public boolean existsBy(SelectUser by, String item) {
        Query exists = new Query();
        Criteria criteria;
        switch (by){
            case EMAIL:
                criteria = Criteria.where("email").is(item);
                break;
            case USERNAME:
                criteria = Criteria.where("userName").is(item);
                break;
            default:
                return true;
        }
        exists.addCriteria(criteria);
        return mongoTemplate.exists(exists, User.class, "users");
    }

    /**
     * return null if provided enum SelectUser doesn't exist
     */
    @Override
    public User update(User user, SelectUser by) {
        Query query;
        switch (by) {
            case EMAIL:
                query = new Query().addCriteria(Criteria.where("email").is(user.getEmail()));
                break;
            case USERNAME:
                query = new Query().addCriteria(Criteria.where("userName").is(user.getUserName()));
                break;
            default:
                return null;
        }
        Update update = new Update();
        update.set("userName", user.getUserName());
        update.set("email", user.getEmail());
        update.set("firstName", user.getFirstName());
        update.set("lastName", user.getLastName());
        update.set("number", user.getNumber());
        return mongoTemplate.findAndModify(query, update, User.class);
    }

    /**
     * return Optional.empty() if provided enum SelectUser doesn't exist
     */
    @Override
    public Optional<User> findBy(String obj, SelectUser by) {
        Query query = new Query();
        switch (by) {
            case USERNAME:
                query.addCriteria(Criteria.where("userName").is(obj));
                break;
            case EMAIL:
                query.addCriteria(Criteria.where("email").is(obj));
                break;
            default:
                return Optional.empty();
        }
        List<User> users = mongoTemplate.find(query, User.class, "users");
        return users.size() > 0 ? Optional.of(users.get(0)) : Optional.empty();
    }
}
