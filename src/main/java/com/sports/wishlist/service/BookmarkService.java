package com.sports.wishlist.service;

import com.sports.wishlist.Repository.BookmarkRepository;
import com.sports.wishlist.client.LeagueClient;
import com.sports.wishlist.exception.ResourceNotFoundException;
import com.sports.wishlist.model.Bookmark;
import com.sports.wishlist.model.League;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private LeagueClient leagueClient;
    public List<Bookmark> getAll() {
        return bookmarkRepository.findAll();
    }

    public List<Bookmark> getBookmarkByUserId(Integer userId) {
        return bookmarkRepository.findByUserId(userId);
    }

    public Bookmark create(Bookmark request) {
        Bookmark bookmark = new Bookmark();
        bookmark.setUserId(request.getUserId());
        if (Objects.nonNull(request.getLeague())
                && Objects.nonNull(request.getLeague().get(0))
                && StringUtils.hasLength(request.getLeague().get(0).getIdLeague())) {
            League league = request.getLeague().get(0);
            ResponseEntity<League> leagueObj = leagueClient.getAllLeaguesByCountryAndId(league.getStrCountry(), league.getIdLeague());
            if (Objects.nonNull(leagueObj) && Objects.nonNull(leagueObj.getBody())) {
                bookmark.setLeague(List.of(leagueObj.getBody()));
            } else {
                throw new ResourceNotFoundException("league not found with id :"+request.getLeague().get(0).getIdLeague());
            }
        }
        return bookmarkRepository.save(bookmark);
    }

    public void delete(Long id) {
        bookmarkRepository.deleteById(id);
    }
}
