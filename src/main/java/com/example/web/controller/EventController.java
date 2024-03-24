package com.example.web.controller;

import com.example.web.dto.ClubDto;
import com.example.web.dto.EventDto;
import com.example.web.models.Club;
import com.example.web.models.Coupon;
import com.example.web.models.Event;
import com.example.web.models.security.UserEntity;
import com.example.web.security.SecurityUtil;
import com.example.web.service.ClubService;
import com.example.web.service.CouponService;
import com.example.web.service.EventService;
import com.example.web.service.security.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class EventController {
    private EventService eventService; private UserService userService;private ClubService clubService;


    @Autowired // не обязательная аннотация
    public EventController(EventService eventService , UserService userService, ClubService clubService)
    {
        this.eventService = eventService;this.userService = userService;this.clubService = clubService;
    }
    @Autowired
    private CouponService couponService;

    //Create Event
    @GetMapping("events/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model)
    {
       Event event = new Event(); // this object we will pass into view
       model.addAttribute("clubId", clubId);
       model.addAttribute("event",event);
       return "events-create";
    }
    @PostMapping("/events/{clubId}")
    public String  createEvent(@PathVariable("clubId") Long clubId , @ModelAttribute("event") EventDto eventDto ,BindingResult result,Model model)
    {
        if(result.hasErrors())//hasErrors проверяет есть ли ошибки к обьекте BindingResources
        {
            model.addAttribute("event",eventDto);//если без этой строки ,
            // то пользователь не увидит то что он ввел в данную строку где произошла ошибка
            return "events-edit";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId; // we will redirect onclub  detail page
    }
    // Read (All-Events-Coupons  page)
    @GetMapping("/events-coupons")
    public String eventList(Model model)
    {
        UserEntity user = new UserEntity();
        List<EventDto> eventsDto = eventService.findallEventsDto();
        List<Coupon> coupons =couponService.getAllCoupons();
        String username = SecurityUtil.getSessionUser();
        if(username !=null)
        {
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("user",user);// if user entity will be null (пользователь не авторизован или не существует в системе.)
        model.addAttribute("events", eventsDto);
        model.addAttribute("coupons",coupons);
    return "events-list";
    }

    //Update
    @GetMapping("/events/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") long eventId, Model model)
    {
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event",event);
        return "events-edit";
    }

    @PostMapping("/events/{eventId}/edit")
    public String updateEventForm(@PathVariable("eventId") Long clubId ,
                                  @Valid @ModelAttribute("event") EventDto eventDto ,
                                  BindingResult result,
                                  Model model) {
        if(result.hasErrors())//hasErrors проверяет есть ли ошибки к обьекте BindingResources
        {
            model.addAttribute("event",eventDto);//если без этой строки ,
            // то пользователь не увидит то что он ввел в данную строку где произошла ошибка
            return "events-edit";
        }

        EventDto eventDto2 =eventService.findByEventId(clubId);
        eventDto.setId(clubId);
        eventDto.setClub(eventDto2.getClub());

        eventService.updateClub(eventDto);
        return "redirect:/events-coupons"; // когда этот метод выполнется
        // --> нас вернет обратно на главную страницу
    }


    //Detail page
    @GetMapping("/events/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId , Model model)
    {
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findByEventId(eventId);
        String username = SecurityUtil.getSessionUser();
        if(username !=null)
        {
            user= userService.findByUsername(username);
            model.addAttribute("user",user);
        }
        model.addAttribute("club",eventDto.getClub());
        model.addAttribute("user",user);// if user entity will be null (пользователь не авторизован или не существует в системе.)
        model.addAttribute("event", eventDto);
        return "events-detail";
     }

    //Delete
    @GetMapping("/events/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId)
    {
       eventService.deleteEvent(eventId);
       return "redirect:/events-coupons";
    }

}
