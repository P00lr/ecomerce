package com.paul.ecomerce.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
@Table(name = "sales")
@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date = LocalDateTime.now();
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    private boolean enabled = true;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SaleDetail> saleDetails = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    public void calculateTotalAmount() {
        this.totalAmount = this.getSaleDetails().stream()
                .map(SaleDetail::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateTotalQuantity() {
        this.totalQuantity = this.getSaleDetails().stream()
                .mapToInt(SaleDetail::getQuantity)
                .filter(Objects::nonNull)
                .sum();
    }

    public void addDetail(SaleDetail saleDetail) {
        saleDetail.setSale(this);
        this.getSaleDetails().add(saleDetail);
    }

    public SaleDetail createDetail(ProductVariant productVariant, Integer quantity) {

        SaleDetail detail = new SaleDetail();
        detail.setQuantity(quantity);
        detail.setProductVariant(productVariant);
        detail.setSale(this);
        detail.calculateSubtotal();

        saleDetails.add(detail);

        return detail;
    }

    public void updateTotals() {
        calculateTotalQuantity();
        calculateTotalAmount();
    }

    public void deactivate() {
        if (enabled == true)
            enabled = false;
    }

}
