package com.paul.ecomerce.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.user.UpdatePasswordDto;
import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.dto.user.UserResponseDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.UserMapper;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.model.entity.Role;
import com.paul.ecomerce.repository.UserRepository;
import com.paul.ecomerce.service.UserService;
import com.paul.ecomerce.service.validator.RoleValildator;
import com.paul.ecomerce.service.validator.UserValidator;

import static com.paul.ecomerce.security.constants.RoleConstants.ROLE_CUSTOMER;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final PaginationMapper paginationMapper;

    private final RoleValildator roleValidator;

    @Override
    public PageResponseDto<UserResponseDto> getAllUsersPaged(Pageable pageable) {
        log.info("Verificando lista de usuarios");
        Page<AppUser> usersPage = userRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(usersPage, userMapper::toUserResponseDto);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        log.info("Verificando usuario con ID: {}", id);
        AppUser user = userValidator.getUserOrThrow(id);
        return userMapper.toUserResponseDto(user);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userDto) {
        log.info("Validando datos del usuario");
        userValidator.validateForCreate(userDto);

        AppUser user = userMapper.toToAppUser(userDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role role = roleValidator.getRoleByNameOrThrow(ROLE_CUSTOMER);
        user.getRoles().add(role);

        userRepository.save(user);

        return userMapper.toUserResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto userDto) {
        log.info("Validando datos para actualizar el usuario");
        AppUser user = userValidator.validateForUpdate(id, userDto);
        user.fromRequestDto(userDto);

        userRepository.save(user);

        return userMapper.toUserResponseDto(user);

    }

    @Transactional
    @Override
    public void updatePassword(Long id, UpdatePasswordDto passwordDto) {
        AppUser user = userValidator.getUserOrThrow(id);

        userValidator.validatePassword(user.getPassword(), passwordDto.currentPassword());

        user.setPassword(passwordEncoder.encode(passwordDto.newPassword()));

        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateUser(Long id) {
        log.info("Verificando existencia del usuario co ID: " + id);
        AppUser user = userValidator.getUserOrThrow(id);

        userValidator.validateDeactivation(user);

        user.setEnabled(false);
        userRepository.save(user);
    }

}
