package com.paul.ecomerce.service;


import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;

public interface UserService {
    PageResponseDto<UserResponseDto> getAllUsers(Pageable pageable);
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(UserRequestDto userDto);
    UserResponseDto updateUser(Long id, UserRequestDto userDto);
    void deactivateUser(Long id);
}