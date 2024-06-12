package com.example.demo;

import com.example.demo.dao.ActorRepository;
import com.example.demo.exceptions.DatabaseOperationException;
import com.example.demo.exceptions.RecordNotFoundException;
import com.example.demo.model.Actor;
import com.example.demo.service.ActorServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ActorServiceImplTest {

    @InjectMocks
    private ActorServiceImpl actorService;

    @Mock
    private ActorRepository actorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getActors_gettingAllActors_shouldReturnAllActors() {
        Actor actor1 = new Actor();
        actor1.setName("Actor One");
        Actor actor2 = new Actor();
        actor2.setName("Actor Two");

        when(actorRepository.getActors()).thenReturn(Arrays.asList(actor1, actor2));

        List<Actor> actors = actorService.getActors();

        assertEquals(2, actors.size());
        assertEquals("Actor One", actors.get(0).getName());
        assertEquals("Actor Two", actors.get(1).getName());
    }

    @Test
    public void getActorsWithPagination_gettingAllTheActorsWithPagination_shouldReturnAllActorsWithPagination() {
        List<Actor> allActors = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Actor actor = new Actor();
            actor.setName("Actor " + i);
            actor.setAge(20 + i);
            actor.setCountry("Country " + i);
            allActors.add(actor);
        }

        when(actorRepository.getActorsWithPagination(0, 3)).thenReturn(allActors.subList(0, 3));
        when(actorRepository.getActorsWithPagination(3, 3)).thenReturn(allActors.subList(3, 6));
        when(actorRepository.getActorsWithPagination(6, 3)).thenReturn(allActors.subList(6, 9));
        when(actorRepository.getActorsWithPagination(9, 3)).thenReturn(allActors.subList(9, 10));

        List<Actor> result = actorService.getActorsWithPagination(0, 3);
        assertEquals(3, result.size());
        assertEquals("Actor 1", result.get(0).getName());
        assertEquals("Actor 2", result.get(1).getName());
        assertEquals("Actor 3", result.get(2).getName());

        result = actorService.getActorsWithPagination(3, 3);
        assertEquals(3, result.size());
        assertEquals("Actor 4", result.get(0).getName());
        assertEquals("Actor 5", result.get(1).getName());
        assertEquals("Actor 6", result.get(2).getName());

        result = actorService.getActorsWithPagination(6, 3);
        assertEquals(3, result.size());
        assertEquals("Actor 7", result.get(0).getName());
        assertEquals("Actor 8", result.get(1).getName());
        assertEquals("Actor 9", result.get(2).getName());

        result = actorService.getActorsWithPagination(9, 3);
        assertEquals(1, result.size());
        assertEquals("Actor 10", result.get(0).getName());
    }

    @Test
    public void getActorById_passingAnId_shouldReturnActorWithId() {
        Actor actor = new Actor();
        actor.setName("Actor One");

        ObjectId actorId = new ObjectId();
        when(actorRepository.getActorById(actorId)).thenReturn(Optional.of(actor));

        Optional<Actor> foundActor = actorService.getActorById(actorId);

        assertTrue(foundActor.isPresent());
        assertEquals("Actor One", foundActor.get().getName());
    }

    @Test
    public void addActor_passingAnActor_shouldAddActor() {
        Actor actor = new Actor();
        actor.setName("New Actor");

        when(actorRepository.addActor(actor)).thenReturn(actor);

        Actor createdActor = actorService.addActor(actor);

        assertEquals("New Actor", createdActor.getName());
    }

    @Test
    public void updateActor_passingAnActor_shouldUpdateActor() {
        Actor actor = new Actor();
        actor.setName("Actor");
        actor.setAge(23);
        actor.setCountry("USA");

        when(actorRepository.getActorById(any(ObjectId.class))).thenReturn(Optional.of(actor));

        Actor updatedActor = new Actor();
        updatedActor.setName("Updated Actor");
        updatedActor.setAge(40);
        updatedActor.setCountry("Armenia");

        when(actorRepository.updateActor(any(ObjectId.class), any(Actor.class))).thenReturn(updatedActor);

        Actor result = actorService.updateActor(new ObjectId(), updatedActor);

        assertEquals("Updated Actor", result.getName());
        assertEquals(40, result.getAge());
        assertEquals("Armenia", result.getCountry());
    }

    @Test
    public void deleteActor_passingAnId_shouldDeleteActorWithId() {
        ObjectId actorId = new ObjectId();
        actorService.deleteActor(actorId);
        verify(actorRepository, times(1)).deleteActor(actorId);
    }
    @Test
    void testDeleteActorThrowsException() {
        ObjectId actorId = new ObjectId();
        doThrow(new DatabaseOperationException("Error deleting actor with ID " + actorId, new Exception()))
                .when(actorRepository).deleteActor(actorId);

        Exception exception = assertThrows(DatabaseOperationException.class, () -> {
            actorService.deleteActor(actorId);
        });

        assertEquals("Error deleting actor with ID " + actorId, exception.getMessage());
    }
}