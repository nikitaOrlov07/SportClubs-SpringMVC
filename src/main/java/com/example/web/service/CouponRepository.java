package com.example.web.service;

import com.example.web.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository  extends JpaRepository<Coupon,Long>
{
    List<Coupon> findCouponsByClubId(Long clubId);
    List<Coupon> findAll();
}
