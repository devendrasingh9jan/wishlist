package com.sports.wishlist.controller;

import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.service.BookmarkService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookmarkControllerTest {

    @Mock
    private BookmarkService bookmarkService;

    @InjectMocks
    private BookmarkController bookmarkController;

    @Test
    void getAllBookmarks() {
        when(bookmarkService.getAll()).thenReturn(List.of(new Bookmark()));
        assertDoesNotThrow(() -> bookmarkController.getAllBookmarks());
    }

    @Test
    void getBookmarkByUserId() {
        when(bookmarkService.getBookmarkByUserId(anyInt())).thenReturn(List.of(new Bookmark()));
        assertDoesNotThrow(() -> bookmarkController.getBookmarkByUserId(1));
    }

    @Test
    void createBookmark() {
        when(bookmarkService.create(any(Bookmark.class))).thenReturn(new Bookmark());
        assertDoesNotThrow(() -> bookmarkController.createBookmark(new Bookmark()));
    }

    @Test
    void deleteBookmark() {
        doNothing().when(bookmarkService).delete(anyLong());
        assertDoesNotThrow(() -> bookmarkController.deleteBookmark(1L));
    }
}