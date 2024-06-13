package com.example.demo.dao;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Film;
import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
public class FilmRepositoryImpl implements FilmRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Film addFilm(Film film) {
        return mongoTemplate.save(film);
    }

    @Override
    public Optional<Film> getFilmById(ObjectId id, String includeFields, String excludeFields) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        if (includeFields != null && !includeFields.isEmpty()) {
            List<String> incFields = Arrays.stream(includeFields.split(",")).toList();
            for (String field : incFields) {
                query.fields().include(field);
            }
        }
        if (excludeFields != null && !excludeFields.isEmpty()) {
            List<String> excFields = Arrays.stream(excludeFields.split(",")).toList();
            for (String field : excFields) {
                query.fields().exclude(field);
            }
        }
        Film film = mongoTemplate.findOne(query, Film.class);
        return Optional.ofNullable(film);
    }

    @Override
    public Film updateFilm(ObjectId id, Film film) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        Field[] fields = Film.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(film);
                if (value != null) {
                    if(field.getName().equals("actors")) {
                        List<Actor> actors = (List<Actor>) value;
                        List<DBRef> actorRefs = actors.stream()
                                .map(actor -> new DBRef("actors", actor.getId()))
                                .collect(Collectors.toList());
                        update.set("actors", actorRefs);
                    } else if (field.getName().equals("director")) {
                        Director director = (Director) value;
                        update.set("director", director.getId());
                    } else {
                        update.set(field.getName(), value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        if (mongoTemplate.updateFirst(query, update, Film.class).getMatchedCount() == 0) {
            throw new RecordNotFoundException("Film with ID " + id + " not found");
        }
        return mongoTemplate.findOne(query, Film.class);
    }

    @Override
    public void deleteFilm(ObjectId id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        mongoTemplate.remove(query, Film.class);
    }

    @Override
    public List<Film> getAllByActor(Actor actor) {
        Query query = new Query();
        query.addCriteria(Criteria.where("actors").is(new DBRef("actors", actor.getId())));
        return mongoTemplate.find(query, Film.class);
    }

    @Override
    public List<Film> getAllByDirector(Director director) {
        Query query = new Query();
        query.addCriteria(Criteria.where("director").is(new DBRef("directors", director.getId())));
        return mongoTemplate.find(query, Film.class);
    }


}
