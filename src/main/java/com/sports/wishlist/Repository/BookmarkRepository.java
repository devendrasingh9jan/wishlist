package com.sports.wishlist.Repository;

import com.sports.wishlist.model.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark,Long> {
    List<Bookmark> findByUserId(Integer userId);
}
