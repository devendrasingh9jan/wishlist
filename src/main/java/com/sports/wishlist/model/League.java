package com.sports.wishlist.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "league")
public class League {
    @Id
    private String idLeague;
    private String strSport;
    private String strLeague;
    private String strLeagueAlternate;
    private String strWebsite;
    private String strCountry;
}
