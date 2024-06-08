package com.example.demo.service;

import com.example.demo.dao.ActorRepository;
import com.example.demo.dao.FilmRepository;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    ActorService actorService;
    @Autowired
    DirectorService directorService;

    @Override
    public Film addFilm(Film film) {
        return filmRepository.addFilm(film);
    }

    @Override
    public Optional<Film> getFilmById(String id) {
        return filmRepository.getFilmById(id);
    }

    @Override
    public Film updateFilm(String id, Film film) {
        return filmRepository.updateFilm(id, film);
    }

    @Override
    public void deleteFilm(String id) {
        filmRepository.deleteFilm(id);
    }

    @Override
    public List<Film> getAllFilmsByActor(String id) {
        Optional<Actor> actor = actorService.getActorById(id);
        return filmRepository.getAllFilmsByActor(actor.get());
    }

    @Override
    public List<Film> getAllFilmsByDirector(String id) {
        Optional<Director> director = directorService.getDirectorById(id);
        return filmRepository.getAllFilmsByDirector(director.get());
    }


}
