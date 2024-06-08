package com.example.demo.dao;

import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Optional;

@Repository
public class DirectorRepositoryImpl implements DirectorRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Director addDirector(Director director) {
        return mongoTemplate.save(director);
    }

    @Override
    public Optional<Director> getDirectorById(String id) {
        return Optional.ofNullable(mongoTemplate.findOne(new Query().addCriteria(Criteria.where("id").is(new ObjectId(id))), Director.class));
    }

    @Override
    public Director updateDirector(String id, Director director) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        Update update = new Update();
        Field[] fields = Director.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(director);
                if (value != null && !(value instanceof Integer && (Integer) value == 0)) {
                    update.set(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (mongoTemplate.updateFirst(query, update, Director.class).getMatchedCount() == 0) {
            throw new RecordNotFoundException("Director with ID " + id + " not found");

        }
        return mongoTemplate.findOne(query, Director.class);
    }

    @Override
    public void deleteDirector(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        mongoTemplate.remove(query, Director.class);
    }
}
