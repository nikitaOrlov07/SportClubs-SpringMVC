package com.example.web.dto;

import com.example.web.models.Club;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    @NotEmpty(message = "Events name should be not empty")
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @Future(message="This date has already passed")
    @NotNull
    private LocalDateTime startTime;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @Future(message="This date has already passed")
    @NotNull
    private LocalDateTime endTime;
    @NotEmpty(message = "Events type should be not empty")
    private String type;
    @NotEmpty(message = "Events photo link should be not empty")
    private String photoUrl;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Club club;
}
