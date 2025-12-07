package com.paul.ecomerce.service.impl;

import java.util.Random;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paul.ecomerce.dto.PageResponseDto;
import com.paul.ecomerce.dto.product.ProductRequestDto;
import com.paul.ecomerce.dto.product.ProductResponseDto;
import com.paul.ecomerce.dto.product.ProductDetailResponseDto;
import com.paul.ecomerce.mapper.PaginationMapper;
import com.paul.ecomerce.mapper.ProductMappper;
import com.paul.ecomerce.model.entity.Brand;
import com.paul.ecomerce.model.entity.Category;
import com.paul.ecomerce.model.entity.Product;
import com.paul.ecomerce.model.entity.ProductVariant;
import com.paul.ecomerce.repository.ProductRepository;
import com.paul.ecomerce.service.ProductService;
import com.paul.ecomerce.service.validator.BrandValidator;
import com.paul.ecomerce.service.validator.CategoryValidator;
import com.paul.ecomerce.service.validator.ProductValidator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMappper productMappper;
    private final ProductValidator productValidator;

    private final CategoryValidator categoryValidator;
    private final BrandValidator brandValidator;

    private final PaginationMapper paginationMapper;

    @Override
    public PageResponseDto<ProductResponseDto> getAllProductsPaged(Pageable pageable) {
        log.info("Verificando lista de productos con detalles");
        Page<Product> productPaged = productRepository.findAll(pageable);
        return paginationMapper.toPageResponseDto(productPaged, productMappper::toProductResponseDto);
    }

    @Override
    public ProductDetailResponseDto getProductById(Long id) {
        log.info("Verificando si existe el producto");
        Product product = productValidator.getProductOrThrow(id);
        return productMappper.toProductDetailResponseDto(product);
    }

    @Override
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        log.info("Validando datos para crear un producto");
        productValidator.validateForCreate(productDto);

        Category category = categoryValidator.getCategoryOrThrow(productDto.categoryId());
        Brand brand = brandValidator.getBrandOrThrow(productDto.brandId());

        Product product = new Product();

        product.setName(productDto.name());
        product.setBasePrice(productDto.basePrice());
        product.setDescription(productDto.description());
        product.setProductCode(generateUniqueProductCode(brand.getName()));
        product.setCategory(category);
        product.setBrand(brand);

        productRepository.save(product);

        return productMappper.toProductResponseDto(product);

    }

    @Transactional
    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productDto) {
        log.info("Validando datos para actualizar el product");
        Product product = productValidator.validateForUpdate(productId, productDto);
        Category category = categoryValidator.getCategoryOrThrow(productDto.categoryId());
        Brand brand = brandValidator.getBrandOrThrow(productDto.brandId());

        product.updateFromDto(productDto, category, brand);
        productRepository.save(product);

        return productMappper.toProductResponseDto(product);
    }

    @Override
    @Transactional
    public void deactivateProduct(Long id) {
        log.info("Validando producto a desactivar");
        Product product = productValidator.getProductOrThrow(id);
        
        product.setEnabled(false);

        product.getProductVariants().stream()
            .forEach(ProductVariant::deactivate);

        productRepository.save(product);
    }

    // --------------METODOS AUXILIARES----------------------

    private String generateProductCode() {
        Random random = new Random();
        int codeNumber = random.nextInt(9000) + 1000;

        return String.valueOf(codeNumber);
    }

    private String generateUniqueProductCode(String brandName) {
        String name = brandName.toUpperCase().trim();
        int endIndex = Math.min(name.length(), 3);
        final String threeLetterCode = name.substring(0, endIndex);

        String productCode;

        boolean exists;

        do {
            String fourDigitCode = generateProductCode();
            productCode = threeLetterCode + "-" + fourDigitCode;
            exists = productRepository.existsByProductCode(productCode);
        } while (exists);

        return productCode;
    }

    

}
