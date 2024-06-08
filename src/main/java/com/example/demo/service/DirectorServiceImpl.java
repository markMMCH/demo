package com.example.demo.service;
import com.example.demo.dao.DirectorRepository;
import com.example.demo.model.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorRepository directorRepository;

    @Override
    public Optional<Director> getDirectorById(String id) {
        return directorRepository.getDirectorById(id);
    }

    @Override
    public Director addDirector(Director director) {
        return directorRepository.addDirector(director);
    }

    @Override
    public Director updateDirector(String id, Director director) {
        return directorRepository.updateDirector(id, director);
    }

    @Override
    public void deleteDirector(String id) {
        directorRepository.deleteDirector(id);
    }
}
