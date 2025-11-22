package com.paul.ecomerce.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;
import com.paul.ecomerce.model.entity.AppUser;

@Component
public class UserMapper {

    public UserResponseDto entityToDto(AppUser user) {
        return new UserResponseDto(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getEmail(),
            user.isEnabled()
        );
    }

    public List<UserResponseDto> entitiesToDtos(List<AppUser> users) {
        return users.stream()
            .map(this::entityToDto)
            .toList();
    }

    public AppUser userRequestDtoToAppUser(UserRequestDto dto) {
        AppUser user = new AppUser();
        user.setName(dto.name());
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        return user;
    }
}
