package com.example.web.models;

import com.example.web.models.security.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.digester.ArrayStack;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="clubs",schema ="public")
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String photoUrl;
    private String content;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updateOn;
    @NotEmpty(message = "You must enter city")
    private String city;
    //For security
    @ManyToOne // many clubs to one user
    @JoinColumn(name = "created_by" , nullable = false) // поле created_by будет использоваться для соединения между сущностями ClubEntity и UserEntity
    private UserEntity created_by;

    //create one-to-many relationship one club - many relationships
    @OneToMany(mappedBy="club",cascade =  CascadeType.REMOVE)
    private List<Event> events = new ArrayList<>();

    @OneToMany(mappedBy ="club", cascade = CascadeType.REMOVE) // one club - many coupons
    private List<Coupon> cupons = new ArrayList<>();
}
