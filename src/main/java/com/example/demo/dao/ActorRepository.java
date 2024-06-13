package com.example.demo.dao;

import com.example.demo.model.Actor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ActorRepository {
    List<Actor> getActors();
    List<Actor> getActors(int start, int limit);
    Actor addActor(Actor actor);
    Optional<Actor> getActorById(ObjectId id);
    Actor updateActor(ObjectId id, Actor actor);
    void deleteActor(ObjectId id);
}
