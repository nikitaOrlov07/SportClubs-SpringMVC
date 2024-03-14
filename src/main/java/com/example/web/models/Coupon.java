package com.example.web.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

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

    @NotNull(message="Start Time cannot be null")
    @FutureOrPresent(message = "Please enter a valid coupon start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @NotNull(message="End Time cannot be null")
    @FutureOrPresent(message = "Please enter a valid coupon end date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    private String priceForMonth;

    @ManyToOne
    private Club club;
}
