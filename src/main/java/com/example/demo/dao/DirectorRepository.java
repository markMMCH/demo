package com.example.demo.dao;

import com.example.demo.model.Director;
import com.example.demo.model.Film;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository {
    Director addDirector(Director director);
    Optional<Director> getDirectorById(ObjectId id);
    Director updateDirector(ObjectId id, Director director);
    void deleteDirector(ObjectId id);
}
