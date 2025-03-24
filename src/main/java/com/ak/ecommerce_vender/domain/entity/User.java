package com.ak.ecommerce_vender.domain.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ak.ecommerce_vender.common.Gender;
import com.ak.ecommerce_vender.common.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User implements UserDetails {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @Column(nullable = false)
    private String email;
    @NotNull
    private String password; 
    @NotNull
    private boolean enable;
    private String address;
    private LocalDateTime birthDate;
    private String profilePicture;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @JsonIgnore
    @OneToOne(mappedBy = "owner",fetch = FetchType.LAZY)
    private Shop shop;
    @OneToOne(mappedBy = "user")
    private Cart cart;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
    @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;
  @Column(unique = true)
  @NotNull
    private String phoneNumber;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
         return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        return this.email;
    }
}
