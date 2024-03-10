package com.example.web.controller;

import com.example.web.models.Coupon;
import com.example.web.models.Event;
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

@Controller
public class CouponController {
    private CouponService couponService;
    @Autowired // не обязательная аннотация
    public CouponController(EventService eventService , UserService userService, ClubService clubService, CouponService couponService)
    {this.couponService = couponService;
    }
    //CRUD [Create] for this we need Get-method and Post-method
    @GetMapping("/clubs/{clubId}/coupon/create")
    public String createCouponForm(@PathVariable("clubId") Long clubId, Model model)
    {
        Coupon coupon = new Coupon(); // this object we will pass into view
        model.addAttribute("clubId", clubId);
        model.addAttribute("coupon",coupon);
        return "coupon-create";
    }
    @PostMapping("/clubs/{clubId}/coupon/save")
    public String createCoupon(@PathVariable("clubId") Long clubId, @Valid @ModelAttribute("coupon")Coupon coupon, BindingResult result, Model model)
    {
        if(result.hasErrors())//hasErrors проверяет есть ли ошибки к обьекте BindingResources (for validation)
        {
            model.addAttribute("coupon",coupon);//если без этой строки ,
            // то пользователь не увидит то что он ввел в данную строку где произошла ошибка
            return "coupon-create";
        }
        couponService.createCoupon(clubId, coupon);
        return "redirect:/clubs/" + clubId;
    }
    //CRUD [Delete]
    @GetMapping("/coupons/{couponId}/delete")
    public String deleteCoupon(@PathVariable("couponId") Long couponId)
    {
      couponService.deleteCoupon(couponId);
      return "redirect:/clubs";
    }

}
