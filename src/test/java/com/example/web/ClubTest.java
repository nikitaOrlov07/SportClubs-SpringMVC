package com.example.web;

import com.example.web.dto.ClubDto;
import com.example.web.mapper.ClubMapper;
import com.example.web.models.Club;
import com.example.web.models.Event;
import com.example.web.models.security.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClubTest {

    private Club club;

    @Test
    @BeforeEach
    void clubTestForGettersAndSetters()
    {
        // Creating UserEntity object for "createdBy" class field
        UserEntity userEntity = new UserEntity();
        // Creating events for Club
        List<Event> events = new ArrayList<>();
        Event event1 = Event.builder().build();
        events.add(event1);
        //Data Format for createdOn , updateOn fields
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

        //Creating "Club" object with builder pattern
        club = Club.builder()
                .id(5L)
                .title("Club")
                .photoUrl("Photo-url")
                .content("Welcome to the best club")
                .createdBy(userEntity)
                .createdOn(LocalDateTime.parse("2024-03-11 17:17:45.680900",formatter))
                .updateOn(LocalDateTime.parse("2024-03-11 17:15:45.680900",formatter))
                .events(events)
                .city("Bratislava")
                .build();

        // checking values
        assertEquals(5L, club.getId());
        assertEquals("Club", club.getTitle());
        assertEquals("Photo-url", club.getPhotoUrl());
        assertEquals("Welcome to the best club", club.getContent());
        assertEquals("Bratislava", club.getCity());
        assertEquals(userEntity, club.getCreatedBy());
    }
    @Test
    void ClubsMapper()
    {
        // club to clubDto
        ClubDto clubDto= ClubMapper.mapToClubDto(club);
        assertEquals(ClubDto.class, clubDto.getClass());

        // clubDto to club
        assertEquals(Club.class, ClubMapper.mapToClub(clubDto).getClass());
    }


}
