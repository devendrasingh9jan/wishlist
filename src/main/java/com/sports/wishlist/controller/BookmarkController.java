package com.sports.wishlist.controller;

import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.model.BookmarkRequest;
import com.sports.wishlist.model.League;
import com.sports.wishlist.model.User;
import com.sports.wishlist.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/bookmarks")
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("/user")
    public ResponseEntity<Set<League>> getBookmarkByUserEmail(@RequestParam("email") String email) {
        Set<League> userList = bookmarkService.getBookmarkByUserEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PostMapping("/multiple-leagues")
    public ResponseEntity<User> createBulkBookmark(@RequestBody BookmarkRequest request) {
        User bookmark = bookmarkService.createMulti(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookmark);
    }

    @DeleteMapping("/multiple-leagues/delete")
    public ResponseEntity<Void> deleteBookmark(@RequestParam List<Long> ids) {
        bookmarkService.delete(ids);
        return ResponseEntity.noContent().build();
    }
}
