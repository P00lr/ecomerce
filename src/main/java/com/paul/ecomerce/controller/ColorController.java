package com.paul.ecomerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.color.ColorRequestDto;
import com.paul.ecomerce.dto.color.ColorRequestUpdateDto;
import com.paul.ecomerce.dto.color.ColorResponseDto;
import com.paul.ecomerce.service.ColorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/colors")
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<PageResponseDto<ColorResponseDto>> getAllColorsPaged(Pageable pageable) {
        return ResponseEntity.ok(colorService.getAllColorsPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorResponseDto> getColorById(@PathVariable Long id) {
        return ResponseEntity.ok(colorService.getColorById(id));
    }

    @PostMapping
    public ResponseEntity<ColorResponseDto> createColor(@Valid @RequestBody ColorRequestDto colorDto) {
        return ResponseEntity.ok(colorService.createColor(colorDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorResponseDto> updateColor(
        @PathVariable Long id, 
        @Valid @RequestBody ColorRequestUpdateDto colorDto) {
            return ResponseEntity.ok(colorService.updateColor(id, colorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateColorById(@PathVariable Long id) {
        colorService.deactivateColorById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente el color con ID: " + id);
    }
}
