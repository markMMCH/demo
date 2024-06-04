package com.example.demo.dao;


import com.example.demo.model.Film;
import java.util.List;
import java.util.Optional;

public interface FilmRepository {

    Film addFilm(Film film);
    Optional<Film> getFilmById(String id);
    //Film updateFilm(String id, Film film);
    //void deleteFilm(String id);
    //List<Film> getAllFilmsByActor(Actor actor);
}
