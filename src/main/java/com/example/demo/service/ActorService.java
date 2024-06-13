package com.example.demo.service;

import com.example.demo.model.Actor;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface ActorService {
     List<Actor> getActors();
     List<Actor> getActors(int start, int limit);
     Optional<Actor> getActorById(ObjectId id);
     Actor addActor(Actor actor);
     Actor updateActor(ObjectId id, Actor actor);
     void deleteActor(ObjectId id);
}
