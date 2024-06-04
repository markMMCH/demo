package com.example.demo.controller;

import com.example.demo.model.Actor;
import com.example.demo.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<Actor> actor = actorService.getActorById(id);
        return actor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable String id, @RequestBody Actor actorDetails) {
        Optional<Actor> actor = actorService.getActorById(id);
        if (actor.isPresent()) {
            Actor updatedActor = actorService.updateActor(id, actorDetails);
            return ResponseEntity.ok(updatedActor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable String id) {
        if (actorService.getActorById(id).isPresent()) {
            actorService.deleteActor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getActors();
    }



}
