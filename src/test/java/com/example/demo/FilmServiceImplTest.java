package com.example.demo;

import com.example.demo.dao.ActorRepository;
import com.example.demo.dao.FilmRepository;
import com.example.demo.model.Film;
import com.example.demo.service.ActorServiceImpl;
import com.example.demo.service.FilmServiceImpl;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class FilmServiceImplTest {

    @InjectMocks
    private FilmServiceImpl filmService;

    @Mock
    private FilmRepository filmRepository;

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
    void testGetFilmByIdNotFound() {
        when(filmRepository.getFilmById(any(ObjectId.class), eq(null), eq(null))).thenReturn(Optional.empty());
        Optional<Film> result = filmService.getFilmById(filmId, null, null);
        assertFalse(result.isPresent());
    }
}
