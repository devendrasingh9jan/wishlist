package com.sports.wishlist.client;

import com.sports.wishlist.model.League;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@FeignClient(url = "http://localhost:8081/api/leagues", value = "leagueClient")
@FeignClient(name = "SPORTS-SERVICE")
public interface LeagueClient {
    @GetMapping("/api/leagues/id")
    ResponseEntity<League> getAllLeaguesByCountryAndId(@RequestParam("country") String country, @RequestParam("id") String id);

    @GetMapping("/api/leagues/all")
    ResponseEntity<List<League>> getAllLeaguesByCountry(@RequestParam("country") String country);
}
