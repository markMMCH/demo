package com.example.demo;

import com.example.demo.dao.ActorRepository;
import com.example.demo.model.Actor;
import com.example.demo.service.ActorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testGetActors() {
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
    public void testGetActorsWithPagination() {
        Actor actor1 = new Actor();
        actor1.setName("Actor One");
        Actor actor2 = new Actor();
        actor2.setName("Actor Two");

        when(actorRepository.getActorsWithPagination(0, 2)).thenReturn(Arrays.asList(actor1, actor2));

        List<Actor> actors = actorService.getActorsWithPagination(0, 2);

        assertEquals(2, actors.size());
        assertEquals("Actor One", actors.get(0).getName());
        assertEquals("Actor Two", actors.get(1).getName());
    }

    @Test
    public void testGetActorById() {
        Actor actor = new Actor();
        actor.setName("Actor One");

        when(actorRepository.getActorById("1")).thenReturn(Optional.of(actor));

        Optional<Actor> foundActor = actorService.getActorById("1");

        assertTrue(foundActor.isPresent());
        assertEquals("Actor One", foundActor.get().getName());
    }

    @Test
    public void testAddActor() {
        Actor actor = new Actor();
        actor.setName("New Actor");

        when(actorRepository.addActor(actor)).thenReturn(actor);

        Actor createdActor = actorService.addActor(actor);

        assertEquals("New Actor", createdActor.getName());
    }

    @Test
    public void testUpdateActor() {
        Actor actor = new Actor();
        actor.setName("Updated Actor");

        when(actorRepository.updateActor("1", actor)).thenReturn(actor);

        Actor updatedActor = actorService.updateActor("1", actor);

        assertEquals("Updated Actor", updatedActor.getName());
    }

    @Test
    public void testDeleteActor() {
        doNothing().when(actorRepository).deleteActor("1");

        actorService.deleteActor("1");

        verify(actorRepository, times(1)).deleteActor("1");
    }
}