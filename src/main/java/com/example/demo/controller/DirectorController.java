package com.example.demo.controller;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Director;
import com.example.demo.service.DirectorService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/director")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @PostMapping("/create")
    public Director createDirector(@RequestBody Director director) {
        return directorService.addDirector(director);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable String id) {
        Optional<Director> director = directorService.getDirectorById(new ObjectId(id));
        return director.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable String id, @RequestBody Director directorDetails) {
        Optional<Director> director = directorService.getDirectorById(new ObjectId(id));
        if (director.isPresent()) {
            Director updateDirector = directorService.updateDirector(new ObjectId(id), directorDetails);
            return ResponseEntity.ok(updateDirector);
        } else {
            throw new RecordNotFoundException("Director with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable String id) {
        if (directorService.getDirectorById(new ObjectId(id)).isPresent()) {
            directorService.deleteDirector(new ObjectId(id));
            return ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("Director with ID " + id + " not found.");
        }
    }
}
