package com.paul.ecomerce.service.impl;

import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantRequestUpdateDto;
import com.paul.ecomerce.dto.productvariant.ProductVariantResponseDto;
import com.paul.ecomerce.exception.custom.InsufficientStockException;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.ProductVariantMapper;
import com.paul.ecomerce.model.entity.Color;
import com.paul.ecomerce.model.entity.Product;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.model.entity.Size;
import com.paul.ecomerce.repository.ProductVariantRepository;
import com.paul.ecomerce.service.ProductVariantService;
import com.paul.ecomerce.service.validator.ColorValidator;
import com.paul.ecomerce.service.validator.ProductValidator;
import com.paul.ecomerce.service.validator.ProductVariantValidator;
import com.paul.ecomerce.service.validator.SizeValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantMapper productVariantMapper;

    private final PaginationMapper paginationMapper;

    private final ProductVariantValidator productVariantValidator;

    private final ProductVariantRepository productVariantRepository;

    private final ProductValidator productValidator;

    private final ColorValidator colorValidator;

    private final SizeValidator sizeValidator;

    @Override
    public PageResponseDto<ProductVariantResponseDto> getAllProductVariantPaged(Pageable pageable) {
        log.info("Verificando datos para listar las variantes de los productos");
        Page<ProductVariant> productVariants = productVariantRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(productVariants, productVariantMapper::toProductVariantResponseDto);
    }

    @Transactional
    @Override
    public ProductVariantResponseDto createProductVariant(Long id, ProductVariantRequestDto productVariantDto) {
        log.info("Validando datos para crear la variante del producto");
        Product product = productValidator.getProductOrThrow(id);
        Color color = colorValidator.getColorOrThrow(productVariantDto.colorId());
        Size size = sizeValidator.getSizeOrThrow(productVariantDto.sizeId());

        ProductVariant productVariant = new ProductVariant();
        productVariant.setProduct(product);
        productVariant.setColor(color);
        productVariant.setSize(size);
        productVariant.setPrice(productVariantDto.price());
        productVariant.setStock(productVariantDto.stock());
        productVariant.setSku(generateUniqueSku(product));

        productVariantRepository.save(productVariant);

        return productVariantMapper.toProductVariantResponseDto(productVariant);
    }

    @Override
    @Transactional
    public ProductVariantResponseDto updateProductVariant(
            Long productVariantId,
            ProductVariantRequestUpdateDto productDto) {
        log.info("Validando datos para actualizar la variante del producto");
        ProductVariant productVariant = productVariantValidator.geProductVariantOrThrow(productVariantId);
        Color color = colorValidator.getColorOrThrow(productDto.colorId());
        Size size = sizeValidator.getSizeOrThrow(productDto.colorId());

        productVariant.setColor(color);
        productVariant.setSize(size);
        productVariant.setPrice(productDto.price());

        if (productVariant.isEnabled() == false)
            productVariant.activate();

        productVariantRepository.save(productVariant);
        return productVariantMapper.toProductVariantResponseDto(productVariant);

    }

    @Override
    @Transactional
    public void deactivateProductVariant(Long id) {
        log.info("Validando variante del producto con ID: " + id);
        ProductVariant productVariant = productVariantValidator.geProductVariantOrThrow(id);
        productVariant.deactivate();
        productVariantRepository.save(productVariant);
    }

    private String generateBasePrefix(Product product) {
        String prefix = product.getName()
                .replaceAll("\\s+", "")
                .toUpperCase();

        return prefix.substring(0, Math.min(prefix.length(), 3));
    }

    private String generateRandomAlphanumeric() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }

    private String generateUniqueSku(Product product) {
        String prefix = generateBasePrefix(product);

        String sku;

        do {
            String randomCode = generateRandomAlphanumeric();
            sku = prefix + "-" + randomCode; // Ejemplo "NIK-A92F"
        } while (productVariantValidator.skuExists(sku));

        return sku;
    }

    @Override
    @Transactional
    public void decreaseStock(ProductVariant productVariant, Integer quantity) {
        validateStockAvailability(productVariant, quantity);
        productVariant.setStock(productVariant.getStock() - quantity);
        productVariantRepository.save(productVariant);
    }

    @Transactional
    @Override
    public void increaseStock(ProductVariant productVariant, Integer quantity) {
        productVariant.setStock(productVariant.getStock() + quantity);
        productVariantRepository.save(productVariant);
    }

    private void validateStockAvailability(ProductVariant productVariant, Integer quantity) {
        if (quantity > productVariant.getStock()) {
            throw new InsufficientStockException("stock insuficiente para la variante con ID: " + productVariant.getId()
                    + " del producto: " + productVariant.getProduct().getName());
        }
    }

}
