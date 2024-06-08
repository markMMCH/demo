package com.example.demo.dao;

import com.example.demo.model.Actor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface ActorRepository {
    List<Actor> getActors();
    List<Actor> getActorsWithPagination(int start, int limit);
    Actor addActor(Actor actor);
    Optional<Actor> getActorById(String id);
    Actor updateActor(String id, Actor actor);
    void deleteActor(String id);
}
