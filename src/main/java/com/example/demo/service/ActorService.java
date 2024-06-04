package com.example.demo.service;

import com.example.demo.model.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {
     List<Actor> getActors();
     Optional<Actor> getActorById(String id);
     Actor addActor(Actor actor);
     Actor updateActor(String id, Actor actor);
     void deleteActor(String id);

}
