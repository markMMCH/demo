package com.example.demo.controller;

import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/create")
    public Actor createActor(@RequestBody Actor actor) {
        return actorService.addActor(actor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable String id) {
        try {
            Optional<Actor> actor = actorService.getActorById(id);
            return actor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/get")
    public List<Actor> getActors(
            @RequestParam(name = "start", required = false, defaultValue = "0") int start,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        return actorService.getActorsWithPagination(start, limit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable String id, @RequestBody Actor actorDetails) {
        try {
            Optional<Actor> actor = actorService.getActorById(id);
            if (actor.isPresent()) {
                Actor updatedActor = actorService.updateActor(id, actorDetails);
                return ResponseEntity.ok(updatedActor);
            } else {
                throw new RecordNotFoundException("Actor with ID " + id + " not found.");
            }
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable String id) {
        try {
            if (actorService.getActorById(id).isPresent()) {
                actorService.deleteActor(id);
                return ResponseEntity.noContent().build();
            } else {
                throw new RecordNotFoundException("Actor with ID " + id + " not found.");
            }
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @GetMapping
    public List<Actor> getAllActors() {
        try {
            return actorService.getActors();
        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("Actors not found.");
        }
    }

}
