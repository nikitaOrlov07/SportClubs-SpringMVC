package com.example.web.service;

import com.example.web.dto.ClubDto;
import com.example.web.dto.ClubsPagination;
import com.example.web.models.Club;

import java.util.List;

public interface ClubService {
    ClubsPagination findAllClub(int pageNo, int pageSize);
    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(long clubId);

    void updateClub(ClubDto club);

    void delete(Long clubId);
    ClubsPagination searchClubs(String query, int pageNo, int pageSize);

}

