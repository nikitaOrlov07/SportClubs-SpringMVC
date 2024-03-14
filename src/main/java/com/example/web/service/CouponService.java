package com.example.web.service;
import com.example.web.models.Coupon;
import java.util.List;

public interface CouponService {
    List<Coupon> getCoupons(Long clubId);

    void createCoupon(Long clubId, Coupon coupon);

    void deleteCoupon(Long couponId);
    List<Coupon> getAllCoupons();
}
