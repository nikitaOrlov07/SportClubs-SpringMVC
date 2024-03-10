package com.example.web.service.impl;


import com.example.web.dto.ClubDto;
import com.example.web.mapper.ClubMapper;
import com.example.web.models.Club;
import com.example.web.models.security.UserEntity;
import com.example.web.repository.ClubRepository;
import com.example.web.repository.security.UserRepository;
import com.example.web.service.ClubService;
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
    public List<ClubDto> findAllClub() {
        List<Club> clubs = clubRepository.findAll();
        return clubs.stream().map((club)-> ClubMapper.mapToClubDto(club)).collect(Collectors.toList());
    }

    @Override
    public Club saveClub(ClubDto clubDto) {
        String username =  SecurityUtil.getSessionUser();//так мы получим имя пользователя , который создал клуб
        UserEntity user = userRepository.findByUsername(username);
        Club club = ClubMapper.mapToClub(clubDto);
        club.setCreated_by(user);
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
        club.setCreated_by(user);
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
