package com.paul.ecomerce.mapper;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;
import com.paul.ecomerce.model.entity.AppUser;

@Component
public class UserMapper {

    public UserResponseDto toUserResponseDto(AppUser user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.isEnabled()
        );
    }

    public AppUser toToAppUser(UserRequestDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        return user;
    }
}
