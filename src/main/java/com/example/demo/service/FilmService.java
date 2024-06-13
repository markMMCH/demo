package com.example.demo.service;


import com.example.demo.model.Film;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film addFilm(Film film);
    Optional<Film> getFilmById(ObjectId id, String includeFields, String excludeFields);
    Film updateFilm(ObjectId id, Film film);
    void deleteFilm(ObjectId id);
    List<Film> getAllByActor(ObjectId id);
    List<Film> getAllByDirector(ObjectId id);
}
