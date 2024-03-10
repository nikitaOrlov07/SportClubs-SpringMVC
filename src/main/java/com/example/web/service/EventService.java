package com.example.web.service;

import com.example.web.dto.EventDto;

import java.util.List;

public interface EventService {
    void createEvent(Long clubId, EventDto eventDto);

    List<EventDto> findallEventsDto();
    EventDto findByEventId(Long eventId);

    void updateClub(EventDto eventDto);

    void deleteEvent(Long eventId);
}
