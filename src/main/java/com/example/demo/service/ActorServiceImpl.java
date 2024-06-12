package com.example.demo.service;

import com.example.demo.dao.ActorRepository;
import com.example.demo.model.Actor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Override
    public List<Actor> getActors() {
        return actorRepository.getActors();
    }

    @Override
    public List<Actor> getActorsWithPagination(int start, int limit) {
        return actorRepository.getActorsWithPagination(start, limit);
    }

    @Override
    public Optional<Actor> getActorById(ObjectId id) {
        return actorRepository.getActorById(id);
    }

    @Override
    public Actor addActor(Actor actor) {
        return actorRepository.addActor(actor);
    }

    @Override
    public Actor updateActor(ObjectId id, Actor actor) {
        return actorRepository.updateActor(id, actor);
    }

    @Override
    public void deleteActor(ObjectId id) {
        actorRepository.deleteActor(id);
    }
}
