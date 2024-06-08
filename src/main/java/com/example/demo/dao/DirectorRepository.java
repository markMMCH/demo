package com.example.demo.dao;

import com.example.demo.model.Director;
import com.example.demo.model.Film;

import java.util.List;
import java.util.Optional;

public interface DirectorRepository {
    Director addDirector(Director director);
    Optional<Director> getDirectorById(String id);
    Director updateDirector(String id, Director director);
    void deleteDirector(String id);
}
