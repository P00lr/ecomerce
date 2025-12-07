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
import com.paul.ecomerce.dto.sale.SaleRequestDto;
import com.paul.ecomerce.dto.sale.SaleResponseDto;
import com.paul.ecomerce.service.SaleService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping
    public ResponseEntity<PageResponseDto<SaleResponseDto>> getAllSalesPaged(Pageable pageable) {
        return ResponseEntity.ok(saleService.getAllSales(pageable));
    } 

    @GetMapping("/{id}")
    public ResponseEntity<SaleResponseDto> getSaleById(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @PostMapping
    public ResponseEntity<SaleResponseDto> createSale(@Valid @RequestBody SaleRequestDto saleRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.createSale(saleRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateSaleById(@PathVariable Long id) {
        saleService.deactivateSaleById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Se desactivo correctamente la venta con ID: " + id);
    }
}
