package com.example.demo.controller;


import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/film")
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable String id) {
        Optional<Film> film = filmService.getFilmById(id);
        return film.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Film createFilm(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable String id, @RequestBody Film filmDetails) {
        Optional<Film> film = filmService.getFilmById(id);
        if (film.isPresent()) {
            Film updatedFilm = filmService.updateFilm(id, filmDetails);
            return ResponseEntity.ok(updatedFilm);
        } else {
            throw new RecordNotFoundException("Film with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        if (filmService.getFilmById(id).isPresent()) {
            filmService.deleteFilm(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("Film with ID " + id + " not found.");
        }
    }

    @GetMapping("/byActor/{id}")
    public List<Film> getAllFilmsByActor(@PathVariable String id) {
        return filmService.getAllFilmsByActor(id);
    }

    @GetMapping("/byDirector/{id}")
    public List<Film> getAllFilmsByDirector(@PathVariable String id) {
        return filmService.getAllFilmsByDirector(id);
    }
}
