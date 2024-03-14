package com.example.web.service.impl;

import com.example.web.models.Club;
import com.example.web.models.Coupon;
import com.example.web.models.Event;
import com.example.web.repository.ClubRepository;
import com.example.web.service.CouponRepository;
import com.example.web.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.List;

import static com.example.web.mapper.EventMapper.mapToEvent;

@Service
public class CouponServiceimpl implements CouponService {
    private CouponRepository couponRepository;
    private ClubRepository clubRepository;

    public CouponServiceimpl(CouponRepository couponRepository, ClubRepository clubRepository) {
        this.couponRepository = couponRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public List<Coupon> getCoupons(Long clubId) {
        return couponRepository.findCouponsByClubId(clubId);
    }

    @Override
    public void createCoupon(Long clubId, Coupon coupon) {
        Club club = clubRepository.findById(clubId).get();
        coupon.setClub(club);
        couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }


}
