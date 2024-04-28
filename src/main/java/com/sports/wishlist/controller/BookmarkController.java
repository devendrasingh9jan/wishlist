package com.sports.wishlist.controller;

import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/all")
    public ResponseEntity<List<Bookmark>> getAllBookmarks() {
        List<Bookmark> bookmarks = bookmarkService.getAll();
        return ResponseEntity.ok(bookmarks);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Bookmark>> getBookmarkByUserId(@RequestParam("userId") Integer userId) {
        List<Bookmark> bookmarks = bookmarkService.getBookmarkByUserId(userId);
        return ResponseEntity.ok(bookmarks);
    }

    @PostMapping
    public ResponseEntity<Bookmark> createBookmark(@RequestBody Bookmark request) {
        Bookmark bookmark = bookmarkService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);
    }
    @DeleteMapping
    public ResponseEntity<Void> deleteBookmark(@RequestParam Long id) {
        bookmarkService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
