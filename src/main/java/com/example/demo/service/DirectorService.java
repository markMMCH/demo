package com.example.demo.service;


import com.example.demo.model.Director;

import java.util.Optional;

public interface DirectorService {
    Optional<Director> getDirectorById(String id);
    Director addDirector(Director director);
    Director updateDirector(String id, Director director);
    void deleteDirector(String id);
}
