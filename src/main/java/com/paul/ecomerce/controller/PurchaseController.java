package com.paul.ecomerce.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.purchase.PurchaseRequestDto;
import com.paul.ecomerce.dto.purchase.PurchaseResponseDto;
import com.paul.ecomerce.service.PurchaseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<PageResponseDto<PurchaseResponseDto>> getAllPurchasePaged(Pageable pageable) {
        return ResponseEntity.ok(purchaseService.getAllPurchasesPaged(pageable));
    }

    @PostMapping
    public ResponseEntity<PurchaseResponseDto> createPurhase(@Valid @RequestBody PurchaseRequestDto purchaseRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(purchaseService.createPurchase(purchaseRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponseDto> getPurchaseById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getPurchaseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivatePurchaseById(@PathVariable Long id) {
        purchaseService.deactivatePurchaseById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente la compra con ID: " + id);
    }
}
