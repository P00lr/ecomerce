package com.paul.ecomerce.service.validator;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.paul.ecomerce.dto.user.UserRequestDto;
import com.paul.ecomerce.exception.custom.BusinessException;
import com.paul.ecomerce.exception.custom.ResourceAlreadyExistsException;
import com.paul.ecomerce.exception.custom.ResourceNotFoundException;
import com.paul.ecomerce.model.entity.AppUser;
import com.paul.ecomerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AppUser getUserByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No se encontro al usuario con ID: " + username));
    }

    public AppUser getUserOrThrow(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro al usuario con ID: " + id));
    }

    public void validateForCreate(UserRequestDto userDto) {
        validateUsername(userDto.username());
        validateEmail(userDto.email());
    }

    public AppUser validateForUpdate(Long id, UserRequestDto userDto) {
        AppUser user = getUserOrThrow(id);
        validateUsernameForUpdate(user.getUsername(), userDto.username());
        validateEmailForUpdate(user.getEmail(), userDto.email());

        return user;
    }

    public void validateDeactivation(AppUser user) {
        if (!user.isEnabled())
            throw new BusinessException("Ya esta desactivado el usuario con ID: " + user.getId());
    }

    public void validatePassword(String existingPassword, String currentPassword) {
        if (!passwordEncoder.matches(currentPassword, existingPassword)) {
            throw new IllegalArgumentException("Contraseña actual incorrecta");
        }
    }


    // METODOS AUXILIARES
    public void validateUsername(String username) {
        if (userRepository.existsByUsername(username))
            throw new ResourceAlreadyExistsException("El username ya existe");
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new ResourceAlreadyExistsException("El Email ya existe");
    }

    private void validateUsernameForUpdate(String currentUsername, String newUsername) {
        if (!currentUsername.equals(newUsername) &&
                userRepository.existsByUsername(newUsername))
            throw new ResourceAlreadyExistsException("El username ya esta en uso");
    }

    private void validateEmailForUpdate(String currentEmail, String newEmail) {
        if (!currentEmail.equals(newEmail) &&
                userRepository.existsByEmail(newEmail))
            throw new ResourceAlreadyExistsException("El email ya esta en uso");
    }

}
