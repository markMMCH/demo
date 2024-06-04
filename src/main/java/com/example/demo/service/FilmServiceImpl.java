package com.example.demo.service;

import com.example.demo.dao.FilmRepository;
import com.example.demo.model.Actor;
import com.example.demo.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    FilmRepository filmRepository;

    @Override
    public Film addFilm(Film film) {
        return filmRepository.addFilm(film);
    }

    @Override
    public Optional<Film> getFilmById(String id) {
        return filmRepository.getFilmById(id);
    }




}
