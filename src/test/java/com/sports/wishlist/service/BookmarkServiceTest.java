package com.sports.wishlist.service;

import com.sports.wishlist.Repository.BookmarkRepository;
import com.sports.wishlist.client.LeagueClient;
import com.sports.wishlist.exception.ResourceNotFoundException;
import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.model.League;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookmarkServiceTest {

    @Mock
    private BookmarkRepository bookmarkRepository;

    @Mock
    private LeagueClient leagueClient;

    @InjectMocks
    private BookmarkService bookmarkService;

    @Test
    void getAll() {
        when(bookmarkRepository.findAll()).thenReturn(List.of(getBookmarkRequest()));
        assertEquals(1,bookmarkService.getAll().size());
    }

    @Test
    void getBookmarkByUserId() {
        when(bookmarkRepository.findByUserId(anyInt())).thenReturn(List.of(getBookmarkRequest()));
        assertEquals(1,bookmarkService.getBookmarkByUserId(1).size());
    }
    @Test
    void createBookmark_Success() {
        Bookmark request = getBookmarkRequest();
        ResponseEntity<League> leagueResponseEntity = new ResponseEntity<>(request.getLeague().get(0), HttpStatus.OK);
        when(leagueClient.getAllLeaguesByCountryAndId(anyString(), anyString())).thenReturn(leagueResponseEntity);
        when(bookmarkRepository.save(any())).thenReturn(new Bookmark());
        assertDoesNotThrow(() -> bookmarkService.create(request));
    }

    @Test
    void createBookmark_LeagueNotFound() {
        Bookmark request = getBookmarkRequest();
        ResponseEntity<League> leagueResponseEntity = new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        when(leagueClient.getAllLeaguesByCountryAndId(anyString(), anyString())).thenReturn(leagueResponseEntity);
        assertThrows(ResourceNotFoundException.class, () -> bookmarkService.create(request));
    }

    private Bookmark getBookmarkRequest() {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(1);
        League league = new League();
        league.setIdLeague("123");
        league.setStrCountry("USA");
        bookmark.setLeague(List.of(league));
        return bookmark;
    }
    @Test
    void delete() {
        doNothing().when(bookmarkRepository).deleteById(anyLong());
        assertDoesNotThrow(() -> bookmarkService.delete(1L));
        verify(bookmarkRepository, times(1)).deleteById(1L);
    }
}