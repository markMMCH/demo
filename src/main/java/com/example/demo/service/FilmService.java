package com.example.demo.service;


import com.example.demo.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    Film addFilm(Film film);
    Optional<Film> getFilmById(String id);
    Film updateFilm(String id, Film film);
    void deleteFilm(String id);
    List<Film> getAllFilmsByActor(String id);
    List<Film> getAllFilmsByDirector(String id);
}
