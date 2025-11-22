package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.UserMapper;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.repository.UserRepository;
import com.paul.ecomerce.service.UserService;
import com.paul.ecomerce.service.validator.UserValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final PaginationMapper paginationMapper;

    @Override
    public PageResponseDto<UserResponseDto> getAllUsers(Pageable pageable) {
        log.info("Verificando lista de usuarios");
        Page<AppUser> usersPage = userRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(usersPage, userMapper::entityToDto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        log.info("Verificando usuario con ID: {}", id);
        AppUser user = userValidator.getUserOrThrow(id);
        return userMapper.entityToDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userDto) {
        log.info("Validando datos del usuario");
        userValidator.validateForCreate(userDto);

        AppUser user = userMapper.userRequestDtoToAppUser(userDto);
        userRepository.save(user);
        
        return userMapper.entityToDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        log.info("Validando datos para actualizar el usuario");
        AppUser user = userValidator.validateForUpdate(id, userDto);
        user.fromRequestDto(userDto);

        userRepository.save(user);

        return userMapper.entityToDto(user);

    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        log.info("Verificando existencia del usuario co ID: " + id);
        AppUser user = userValidator.getUserOrThrow(id);

        if (!user.isEnabled()) 
            throw new BusinessException("Ya esta desactivado el usuario con ID: " + id);
        
        user.setEnabled(false);
        userRepository.save(user);
    }

}
