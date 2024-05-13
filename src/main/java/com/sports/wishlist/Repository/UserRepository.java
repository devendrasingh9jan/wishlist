package com.sports.wishlist.Repository;

import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findByEmail(String email);

    boolean existsByEmailAndBookmarksIdLeagueIn(String email, List<String> requestedLeagueIds);

    /*@Transactional
    @Modifying
    //@Query("DELETE FROM UserLeague ul WHERE ul.league.id IN :leagueIds")
    void deleteByBookmarksIdLeagueIn(@Param("leagueIds") List<Long> leagueIds);*/

    List<User> findByBookmarksIdIn(List<Long> requestedLeagueIds);

    @Transactional
    @Modifying
    void deleteByIdIn(List<Long> ids);
}
