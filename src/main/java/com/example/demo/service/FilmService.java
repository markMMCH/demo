package com.example.demo.service;

import com.example.demo.model.Actor;
import com.example.demo.model.Film;

import java.util.Optional;

public interface FilmService {
    Film addFilm(Film film);
    Optional<Film> getFilmById(String id);
    //Actor updateFilm(String id, Film film);
    //void deleteFilm(String id);
}
