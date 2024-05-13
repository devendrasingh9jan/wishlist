package com.sports.wishlist.service;

import com.sports.wishlist.Repository.LeagueRepository;
import com.sports.wishlist.Repository.UserRepository;
import com.sports.wishlist.client.LeagueClient;
import com.sports.wishlist.exception.LeagueAlreadyBookmarkedException;
import com.sports.wishlist.exception.ResourceNotFoundException;
import com.sports.wishlist.model.BookmarkRequest;
import com.sports.wishlist.model.League;
import com.sports.wishlist.model.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookmarkService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private LeagueClient leagueClient;

    public Set<League> getBookmarkByUserEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        Set<League> bookmarks = new HashSet<>();
        for (User user : users) {
            bookmarks.addAll(user.getBookmarks());
        }
        return bookmarks;
    }

    public User createMulti(BookmarkRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        if (Objects.nonNull(request.getLeagues())) {
            List<String> requestedLeagueIds = request.getLeagues();
            if (userRepository.existsByEmailAndBookmarksIdLeagueIn(request.getEmail(), requestedLeagueIds)) {
                throw new LeagueAlreadyBookmarkedException("league already bookmarked with ids :" + requestedLeagueIds);
            }
            ResponseEntity<List<League>> allLeaguesByCountry = leagueClient.getAllLeaguesByCountry(request.getCountry());
            if (Objects.nonNull(allLeaguesByCountry) && Objects.nonNull(allLeaguesByCountry.getBody())) {
                Map<String, League> leagueHashMap = new HashMap<>();
                allLeaguesByCountry.getBody().forEach(league -> leagueHashMap.put(league.getIdLeague(), league));
                HashSet<League> bookmarks = new HashSet<>();
                for (String requestedLeagueId : requestedLeagueIds) {
                    if (leagueHashMap.containsKey(requestedLeagueId)) {
                        bookmarks.add(leagueHashMap.get(requestedLeagueId));
                    } else {
                        throw new ResourceNotFoundException("league not found with id :" + request.getLeagues().get(0));
                    }
                }
                user.setBookmarks(bookmarks);
                return userRepository.save(user);
            }
        }
        throw new ResourceNotFoundException("Invalid leagues");
    }

    public void delete(List<Long> ids) {
        for (Long id : ids) {
            deleteLeagueBookmark(id);
        }
    }

    @Transactional
    public void remove(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getBookmarks().removeAll(user.getBookmarks());
            userRepository.delete(user);
        }

    }

    @Transactional
    public void deleteLeagueBookmark(Long leagueId) {
        Optional<League> optionalLeague = leagueRepository.findById(leagueId);

        if (optionalLeague.isPresent()) {
            League league = optionalLeague.get();
            league.getBookmarkedBy().size();
            for (User user : league.getBookmarkedBy()) {
                user.getBookmarks().remove(league);
                userRepository.save(user);
                leagueRepository.deleteById(leagueId);
            }
        } else {
            throw new ResourceNotFoundException("league not found with id :" + leagueId);
        }
    }
}
