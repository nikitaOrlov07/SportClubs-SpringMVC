package com.example.web.service.impl;


import com.example.web.dto.ClubDto;
import com.example.web.dto.ClubsPagination;
import com.example.web.mapper.ClubMapper;
import com.example.web.models.Club;
import com.example.web.models.security.UserEntity;
import com.example.web.repository.ClubRepository;
import com.example.web.repository.security.UserRepository;
import com.example.web.service.ClubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.web.security.SecurityUtil;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class ClubServiceimpl implements ClubService {
    private ClubRepository clubRepository;private UserRepository userRepository;

    public ClubServiceimpl(ClubRepository clubRepository, UserRepository userRepository) {
        this.clubRepository = clubRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ClubsPagination findAllClub(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize); // define information about pagination
        // in pageable value
        Page<Club> clubs = clubRepository.findAll(pageable); // контролировать количество данных,
        // которые мы хотим извлечь из базы данных за один раз.
        List<Club> listofClubs  =clubs.getContent(); // get all content with in  page
        // (получам содержимое страницы)
        List<ClubDto> data =listofClubs.stream().map((club)-> ClubMapper.mapToClubDto(club)).collect(Collectors.toList());

        ClubsPagination clubPagination = new ClubsPagination();
        clubPagination.setData(data);
        //get information from Pageable object (we just create Pageable object , but it gives all information)
        clubPagination.setPageNo(clubs.getNumber());
        clubPagination.setPageSize(clubs.getSize());
        clubPagination.setTotalElements(clubs.getTotalElements());
        clubPagination.setTotalPages(clubs.getTotalPages());
        clubPagination.setLast(clubs.isLast());

        // we returned List of All ClubDto`s , but now we have this list in ClubPagination object
        return clubPagination;

    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username =  SecurityUtil.getSessionUser();//так мы получим имя пользователя , который создал клуб
        UserEntity user = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(user);
        return clubRepository.save(club);
    }

    @Override
    public ClubDto findClubById(long clubId) {
        Club club = clubRepository.findById(clubId).get();
        return ClubMapper.mapToClubDto(club);
    }

    @Override
    public void updateClub(ClubDto clubDto) {
        String username =  SecurityUtil.getSessionUser();//так мы получим имя пользователя , который создал клуб
        UserEntity user = userRepository.findByUsername(username);

        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreatedBy(user);
        clubRepository.save(club);
    }

    @Override
    public void delete(Long clubId) {
        clubRepository.deleteById(clubId);
    }

    @Override
    public List<ClubDto> searchClubs(String query) {
     List<Club> clubs =clubRepository.searchClub(query);

     return clubs.stream().map(club -> ClubMapper.mapToClubDto(club)).collect(Collectors.toList());
        //clubs.stream(): Преобразует список clubs в поток элементов.
        //map(club -> mapToClubDto(club)): Применяет функцию mapToClubDto к каждому элементу потока (в данном случае, к каждому объекту типа Club)
        //для преобразования его в объект типа ClubDto.
        //collect(Collectors.toList()): Собирает элементы потока в список.
    }


}
