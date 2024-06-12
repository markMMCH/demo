package com.example.demo.dao;

import com.example.demo.exceptions.DatabaseOperationException;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Actor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Repository
public class ActorRepositoryImpl implements ActorRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Actor> getActorById(ObjectId id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            Actor actor = mongoTemplate.findOne(query, Actor.class);
            return Optional.ofNullable(actor);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error retrieving actor with ID " + id, e);
        }
    }

    @Override
    public Actor addActor(Actor actor) {
        try {
            return mongoTemplate.save(actor);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error adding actor", e);
        }
    }

    @Override
    public void deleteActor(ObjectId id) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            mongoTemplate.remove(query, Actor.class);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error deleting actor with ID " + id, e);
        }
    }

    @Override
    public List<Actor> getActors() {
        try {
            return mongoTemplate.findAll(Actor.class);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error retrieving actors", e);
        }

    }

    @Override
    public List<Actor> getActorsWithPagination(int start, int limit) {
        try {
            Query query = new Query().skip(start).limit(limit);
            return mongoTemplate.find(query, Actor.class);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error retrieving actors using pagination", e);
        }

    }

    @Override
    public Actor updateActor(ObjectId id, Actor actor) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(id));
            Update update = new Update();
            Field[] fields = Actor.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(actor);
                if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
                    update.set(field.getName(), value);
                }
            }
            if (mongoTemplate.updateFirst(query, update, Actor.class).getMatchedCount() == 0) {
                throw new RecordNotFoundException("Actor with ID " + id + " not found");
            }
            return mongoTemplate.findOne(query, Actor.class);
        } catch (Exception e) {
            throw new DatabaseOperationException("Error updating actor with ID " + id, e);
        }
    }

}
