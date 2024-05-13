package com.sports.wishlist.Repository;

import com.sports.wishlist.model.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LeagueRepository extends JpaRepository<League,Long> {
    @Transactional
    void deleteByIdIn(List<Long> ids);
}
