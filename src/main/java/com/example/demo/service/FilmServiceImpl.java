package com.example.demo.service;

import com.example.demo.dao.ActorRepository;
import com.example.demo.dao.DirectorRepository;
import com.example.demo.dao.FilmRepository;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Film;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmServiceImpl implements FilmService {

    @Autowired
    FilmRepository filmRepository;
    @Autowired
    ActorRepository actorRepository;
    @Autowired
    DirectorRepository directorRepository;

    @Override
    public Film addFilm(Film film) {
        return filmRepository.addFilm(film);
    }

    @Override
    public Optional<Film> getFilmById(ObjectId id, String includeFields, String excludeFields) {
        return filmRepository.getFilmById(id, includeFields, excludeFields);
    }

    @Override
    public Film updateFilm(ObjectId id, Film film) {
        return filmRepository.updateFilm(id, film);
    }

    @Override
    public void deleteFilm(ObjectId id) {
        filmRepository.deleteFilm(id);
    }

    @Override
    public List<Film> getAllByActor(ObjectId id) {
        Optional<Actor> actor = actorRepository.getActorById(id);
        return filmRepository.getAllByActor(actor.get());
    }

    @Override
    public List<Film> getAllByDirector(ObjectId id) {
        Optional<Director> director = directorRepository.getDirectorById(id);
        return filmRepository.getAllByDirector(director.get());
    }


}
