package com.paul.ecomerce.model.entity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.paul.ecomerce.dto.product.ProductRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "products")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal basePrice;
    private String description;
    private String productCode;
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product")
    private Set<ProductVariant> productVariants = new HashSet<>();

    public void updateFromDto(ProductRequestDto productDto, Category category, Brand brand) {
        if(!this.name.equals(productDto.name()))
            this.setName(productDto.name());

        if(!this.basePrice.equals(productDto.basePrice()))
            this.setBasePrice(productDto.basePrice());
        
        if(!this.description.equals(productDto.description()))
            this.setDescription(productDto.description());
        
        if(!this.category.equals(category))
            this.setCategory(category);

        if(!this.brand.equals(brand))
            this.setBrand(brand);
    }
}
