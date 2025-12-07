package com.paul.ecomerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentRequestDto;
import com.paul.ecomerce.dto.adjustment.AdjustmentResponseDto;
import com.paul.ecomerce.service.AdjustmentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/adjustments")
public class AdjustmentController {

    private final AdjustmentService adjustmentService;

    @GetMapping
    public ResponseEntity<PageResponseDto<AdjustmentResponseDto>> getAllAdjustments(Pageable pageable) {
        return ResponseEntity.ok(adjustmentService.getAllAdjustmentPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdjustmentResponseDto> getAdjustmentById(@PathVariable Long id) {
        return ResponseEntity.ok(adjustmentService.getAdjustmentById(id));
    }

    @PostMapping
    public ResponseEntity<AdjustmentResponseDto> createAdjustment(
            @Valid @RequestBody AdjustmentRequestDto adjustmentDto) {
                return ResponseEntity.ok(adjustmentService.createAdjustment(adjustmentDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAdjustment(@PathVariable Long id) {
        adjustmentService.deactivateAdjustmentById(id);
        return ResponseEntity.noContent().build();
    }

}
