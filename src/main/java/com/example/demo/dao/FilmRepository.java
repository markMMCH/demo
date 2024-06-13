package com.example.demo.dao;


import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Film;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    Film addFilm(Film film);
    Optional<Film> getFilmById(ObjectId id, String includeFields, String excludeFields);
    Film updateFilm(ObjectId id, Film film);
    void deleteFilm(ObjectId id);
    List<Film> getAllByActor(Actor actor);
    List<Film> getAllByDirector(Director director);
}
