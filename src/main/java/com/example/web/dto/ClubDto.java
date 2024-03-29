package com.example.web.dto;

import com.example.web.models.security.UserEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ClubDto {
    private Long id;
    @NotEmpty(message = "You must enter title")
    private String title;
    @NotEmpty(message = "You must enter photoUrl")
    private String photoUrl;
    private String content;
    @NotEmpty(message = "You must enter city")
    private String city;
    private UserEntity createdBy;//for security
    private LocalDateTime createdOn;
    private LocalDateTime updateOn;
    List<EventDto> events ;

}
