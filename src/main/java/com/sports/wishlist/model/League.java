package com.sports.wishlist.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "league")
public class League {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idLeague;
    private String strSport;
    private String strLeague;
    private String strLeagueAlternate;
    private String strWebsite;
    private String strCountry;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "bookmarks")
    private Set<User> bookmarkedBy = new HashSet<>();
}
