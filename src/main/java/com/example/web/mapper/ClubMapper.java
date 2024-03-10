package com.example.web.mapper;

import com.example.web.dto.ClubDto;
import com.example.web.models.Club;

import java.util.stream.Collectors;

import static com.example.web.mapper.EventMapper.mapToEventDto;

public class ClubMapper {
    public static Club mapToClub(ClubDto clubDto) {
        Club club = Club.builder()
                .id(clubDto.getId())
                .title(clubDto.getTitle())
                .photoUrl(clubDto.getPhotoUrl())
                .content(clubDto.getContent())
                .created_by(clubDto.getCreatedBy())
                .createdOn(clubDto.getCreatedOn())
                .updateOn(clubDto.getUpdateOn())
                .city(clubDto.getCity())
                .build(); //методы builder и build добавились к club
        return  club;
    }
    public static ClubDto mapToClubDto(Club club) // преобразовали с Club (Entity object) в ClubDto(Data transfer Object)
    {
        ClubDto clubDto = ClubDto.builder()
                .id(club.getId())
                .title(club.getTitle())
                .photoUrl(club.getPhotoUrl())
                .content(club.getContent())
                .createdBy(club.getCreated_by())
                .createdOn(club.getCreatedOn())
                .updateOn(club.getUpdateOn())
                .city(club.getCity())
                .events(club.getEvents().stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList()))
                .build(); // паттерн Builder (добавили с помощью аннотации @Builder) - Создание экземпляров класса
        return clubDto;
    }
}
