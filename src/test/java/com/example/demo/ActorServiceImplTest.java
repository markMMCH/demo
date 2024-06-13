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
        verify(actorRepository, times(1)).getActors();
    }

    @Test
    public void getActorsWithPagination_gettingAllTheActorsWithPagination_shouldLimitAndSkipDocuments() {
        List<Actor> allActors = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Actor actor = new Actor();
            actor.setName("Actor " + i);
            actor.setAge(20 + i);
            actor.setCountry("Country " + i);
            allActors.add(actor);
        }
        when(actorRepository.getActors(3, 3)).thenReturn(allActors.subList(3, 6));
        List<Actor> result = actorService.getActors(3, 3);
        for (Actor actor : result) {
            assertNotEquals("Actor 2", actor.getName());
        }
        assertEquals(3, result.size());
        assertEquals("Actor 4", result.get(0).getName());
        assertEquals("Actor 5", result.get(1).getName());
        assertEquals("Actor 6", result.get(2).getName());
        verify(actorRepository, times(1)).getActors(3, 3);
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
        verify(actorRepository, times(1)).getActorById(actorId);
    }

    @Test
    public void addActor_passingAnActor_shouldAddActor() {
        Actor actor = new Actor();
        actor.setName("New Actor");

        when(actorRepository.addActor(actor)).thenReturn(actor);

        Actor createdActor = actorService.addActor(actor);

        assertEquals("New Actor", createdActor.getName());
        verify(actorRepository, times(1)).addActor(actor);
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
        verify(actorRepository, times(1)).updateActor(any(ObjectId.class), any(Actor.class));
    }

    @Test
    public void deleteActor_passingAnId_shouldDeleteActorWithId() {
        ObjectId actorId = new ObjectId();
        actorService.deleteActor(actorId);
        verify(actorRepository, times(1)).deleteActor(actorId);
    }

    @Test
    public void deleteActor_throwErrorWhenIdInvalid_shouldThrowException() {
        ObjectId actorId = new ObjectId();
        doThrow(new DatabaseOperationException("Error deleting actor with ID " + actorId, new Exception()))
                .when(actorRepository).deleteActor(actorId);

        Exception exception = assertThrows(DatabaseOperationException.class, () -> {
            actorService.deleteActor(actorId);
        });

        assertEquals("Error deleting actor with ID " + actorId, exception.getMessage());
        verify(actorRepository, times(1)).deleteActor(actorId);
    }
}