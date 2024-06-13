package com.example.demo;

import com.example.demo.dao.ActorRepository;
import com.example.demo.dao.DirectorRepository;
import com.example.demo.dao.FilmRepository;
import com.example.demo.model.Actor;
import com.example.demo.model.Director;
import com.example.demo.model.Film;
import com.example.demo.service.ActorServiceImpl;
import com.example.demo.service.FilmServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private DirectorRepository directorRepository;

    private ObjectId filmId;
    private Film mockFilm;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        filmId = new ObjectId();
        mockFilm = new Film();
        mockFilm.setTitle("Test Film");
        mockFilm.setYear(2021);
    }


    @Test
    public void getFilmById_gettingFilmByIdIncludingFields_shouldReturnFilmWithInclusions() {
        when(filmRepository.getFilmById(any(ObjectId.class), eq("title,year"), eq(null))).thenReturn(Optional.of(mockFilm));
        Optional<Film> result = filmService.getFilmById(filmId, "title,year", null);
        assertTrue(result.isPresent());
        assertEquals("Test Film", result.get().getTitle());
        assertEquals(2021, result.get().getYear());
    }

    @Test
    public void getFilmById_gettingFilmByExcludingFields_shouldReturnFilmWithExclusions() {
        when(filmRepository.getFilmById(any(ObjectId.class), eq(null), eq("actors,director"))).thenReturn(Optional.of(mockFilm));
        Optional<Film> result = filmService.getFilmById(filmId, null, "actors,director");
        assertTrue(result.isPresent());
        assertEquals("Test Film", result.get().getTitle());
        assertEquals(2021, result.get().getYear());
    }

    @Test
    void getFilmById_GetFilmByIdNotFound_shouldReturnNoResult() {
        when(filmRepository.getFilmById(any(ObjectId.class), eq(null), eq(null))).thenReturn(Optional.empty());
        Optional<Film> result = filmService.getFilmById(filmId, null, null);
        assertFalse(result.isPresent());
    }

    @Test
    public void getAllByActor_passingAnActorId_shouldReturnFilmsByActor() {
        ObjectId actorId = new ObjectId();
        Actor actor = new Actor();
        actor.setName("Actor Name");

        Film film1 = new Film();
        film1.setTitle("Film One");
        Film film2 = new Film();
        film2.setTitle("Film Two");

        List<Film> films = Arrays.asList(film1, film2);

        when(actorRepository.getActorById(actorId)).thenReturn(Optional.of(actor));
        when(filmRepository.getAllByActor(actor)).thenReturn(films);

        List<Film> result = filmService.getAllByActor(actorId);

        assertEquals(2, result.size());
        assertEquals("Film One", result.get(0).getTitle());
        assertEquals("Film Two", result.get(1).getTitle());

        verify(actorRepository, times(1)).getActorById(actorId);
        verify(filmRepository, times(1)).getAllByActor(actor);
    }

    @Test
    public void getAllByDirector_passingADirectorId_shouldReturnFilmsByDirector() {
        ObjectId directorId = new ObjectId();
        Director director = new Director();
        director.setName("Director Name");

        Film film1 = new Film();
        film1.setTitle("Film One");
        Film film2 = new Film();
        film2.setTitle("Film Two");

        List<Film> films = Arrays.asList(film1, film2);

        when(directorRepository.getDirectorById(directorId)).thenReturn(Optional.of(director));
        when(filmRepository.getAllByDirector(director)).thenReturn(films);

        List<Film> result = filmService.getAllByDirector(directorId);

        assertEquals(2, result.size());
        assertEquals("Film One", result.get(0).getTitle());
        assertEquals("Film Two", result.get(1).getTitle());

        verify(directorRepository, times(1)).getDirectorById(directorId);
        verify(filmRepository, times(1)).getAllByDirector(director);
    }
}
