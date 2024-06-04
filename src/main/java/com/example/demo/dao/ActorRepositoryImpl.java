package com.example.demo.dao;

import com.example.demo.model.Actor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Repository
public class ActorRepositoryImpl implements ActorRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Optional<Actor> getActorById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        Actor actor = mongoTemplate.findOne(query, Actor.class);
        return Optional.ofNullable(actor);
    }

    @Override
    public Actor addActor(Actor actor) {
        return mongoTemplate.save(actor);
    }

    @Override
    public void deleteActor(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Actor.class);
    }

    @Override
    public List<Actor> getActors() {
        return mongoTemplate.findAll(Actor.class);
    }

    @Override
    public Actor updateActor(String id, Actor actor) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();
        Field[] fields = Actor.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(actor);
                if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
                    update.set(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        mongoTemplate.updateFirst(query, update, Actor.class);
        return mongoTemplate.findOne(query, Actor.class);
    }

}
