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
import com.paul.ecomerce.dto.size.SizeRequestDto;
import com.paul.ecomerce.dto.size.SizeRequestUpdateDto;
import com.paul.ecomerce.dto.size.SizeResponseDto;
import com.paul.ecomerce.service.SizeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sizes")
public class SizeController {

    private final SizeService sizeService;

    @GetMapping
    public ResponseEntity<PageResponseDto<SizeResponseDto>> getAllSizesPaged(Pageable pageable) {
        return ResponseEntity.ok(sizeService.getAllSizesPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SizeResponseDto> getSizeById(@PathVariable Long id) {
        return ResponseEntity.ok(sizeService.getSizeById(id));
    }

    @PostMapping
    public ResponseEntity<SizeResponseDto> createSize(@Valid @RequestBody SizeRequestDto sizeDto) {
        return ResponseEntity.ok(sizeService.createSize(sizeDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SizeResponseDto> updateSize(
        @PathVariable Long id, 
        @Valid @RequestBody SizeRequestUpdateDto sizeDto) {
            return ResponseEntity.ok(sizeService.updateSize(id, sizeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateSizeById(@PathVariable Long id) {
        sizeService.deactivateSizeById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente la talla con ID: " + id);
    }
}
