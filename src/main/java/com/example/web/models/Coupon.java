package com.example.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// вид купонов для студента и тд
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="coupon",schema ="public")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message="You must enter a coupon-type")
    private String type;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private String priceForMonth;
    @ManyToOne
    private Club club;
}
