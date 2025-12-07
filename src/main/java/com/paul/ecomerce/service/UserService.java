package com.paul.ecomerce.service;


import org.springframework.data.domain.Pageable;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.user.UpdatePasswordDto;
import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;

public interface UserService {
    PageResponseDto<UserResponseDto> getAllUsersPaged(Pageable pageable);
    UserResponseDto getUserById(Long id);
    UserResponseDto createUser(UserRequestDto userDto);
    UserResponseDto updateUser(Long id, UserRequestDto userDto);
    public void updatePassword(Long id, UpdatePasswordDto passwordDto);
    void deactivateUser(Long id);
}