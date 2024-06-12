package com.example.demo.service;


import com.example.demo.model.Director;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface DirectorService {
    Optional<Director> getDirectorById(ObjectId id);
    Director addDirector(Director director);
    Director updateDirector(ObjectId id, Director director);
    void deleteDirector(ObjectId id);
}
