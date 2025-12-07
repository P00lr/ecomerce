package com.paul.ecomerce.model.entity;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "product_variants", uniqueConstraints = @UniqueConstraint(columnNames = { "product_id", "color_id",
        "size_id" }))
@Entity
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private Integer stock;
    private String sku;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @OneToMany(mappedBy = "productVariant")
    private Set<SaleDetail> saleDetails;

    @OneToMany(mappedBy = "productVariant")
    @JsonIgnore
    private Set<AdjustmentDetail> adjustmentDetails = new LinkedHashSet<>();

    public void deactivate() {
        this.enabled = false;
    }

    public void activate() {
        this.enabled = true;
    }
}
