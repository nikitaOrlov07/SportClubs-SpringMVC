package com.example.web.service.impl;
import com.example.web.dto.EventDto;
import com.example.web.mapper.EventMapper;
import com.example.web.models.Club;
import com.example.web.models.Event;
import com.example.web.repository.ClubRepository;
import com.example.web.repository.EventRepository;
import com.example.web.service.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.web.mapper.EventMapper.mapToEvent;
import static com.example.web.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceimpl implements EventService {
    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    // тут могли поставить аннотацию @Autowired , но она не обязательна
    public EventServiceimpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto); // we have a EventDto object and want to turn it into Event model with help of mapper
        event.setClub(club);
        eventRepository.save(event);
    }

    @Override
    public List<EventDto> findallEventsDto() {
        List<Event> events = eventRepository.findAll(); // method from JPA repository
        return events.stream().map((event) -> mapToEventDto(event)).collect(Collectors.toList());
    }
    @Override
    public EventDto findByEventId(Long eventId)
    {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }

    @Override
    public void updateClub(EventDto eventDto) {
        Event event = EventMapper.mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

}
