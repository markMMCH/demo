package com.example.demo.dao;

import com.example.demo.model.Actor;
import com.example.demo.model.Film;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FilmRepositoryImpl implements FilmRepository {
    @Autowired
    private MongoTemplate  mongoTemplate;
    @Override
    public Film addFilm(Film film) {
        return mongoTemplate.save(film);
    }

    @Override
    public Optional<Film> getFilmById(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(id)));
        Film film = mongoTemplate.findOne(query, Film.class);
        return Optional.ofNullable(film);
    }




}
