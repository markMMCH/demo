package com.example.demo.controller;


import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Film;
import com.example.demo.service.FilmService;
import org.bson.types.ObjectId;
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
    public ResponseEntity<Film> getFilmById(@PathVariable String id,
                                            @RequestParam(name = "includeFields", required = false) String includeFields,
                                            @RequestParam(name = "excludeFields", required = false) String excludeFields) {
        Optional<Film> film = filmService.getFilmById(new ObjectId(id), includeFields, excludeFields);
        return film.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public Film createFilm(@RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(@PathVariable String id, @RequestBody Film filmDetails) {
        Optional<Film> film = filmService.getFilmById(new ObjectId(id), null, null);
        if (film.isPresent()) {
            Film updatedFilm = filmService.updateFilm(new ObjectId(id), filmDetails);
            return ResponseEntity.ok(updatedFilm);
        } else {
            throw new RecordNotFoundException("Film with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable String id) {
        if (filmService.getFilmById(new ObjectId(id), null, null).isPresent()) {
            filmService.deleteFilm(new ObjectId(id));
            return ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("Film with ID " + id + " not found.");
        }
    }

    @GetMapping("/byActor/{id}")
    public List<Film> getAllByActor(@PathVariable String id) {
        return filmService.getAllByActor(new ObjectId(id));
    }

    @GetMapping("/byDirector/{id}")
    public List<Film> getAllByDirector(@PathVariable String id) {
        return filmService.getAllByDirector(new ObjectId(id));
    }
}
