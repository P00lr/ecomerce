package com.paul.ecomerce.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchases")
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date = LocalDateTime.now();
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    private String comments;
    private boolean enabled = true;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PurchaseDetail> purchaseDetails = new LinkedHashSet<>();

    public void calculateTotalQuantity() {
        Integer result = 0;
        for (PurchaseDetail detail : purchaseDetails) {
            result += detail.getQuantity();
        }

        this.totalQuantity = result;
    }

    public void calculateTotalAmount() {
        BigDecimal result = BigDecimal.ZERO;

        for (PurchaseDetail detail : purchaseDetails) {
            result = result.add(detail.getSubtotal());
        }

        this.totalAmount = result;
    }

    public void updateTotals() {
        calculateTotalQuantity();
        calculateTotalAmount();
    }

    public PurchaseDetail createDetail(ProductVariant productVariant, Integer quantity, BigDecimal unitPrice) {
        PurchaseDetail purchaseDetail = new PurchaseDetail();
        purchaseDetail.setQuantity(quantity);
        purchaseDetail.calculateSubtotal(unitPrice);
        purchaseDetail.setPurchase(this);
        purchaseDetail.setProductVariant(productVariant);

        purchaseDetails.add(purchaseDetail);

        return purchaseDetail;
    }

    public void deactivate() {
        if(enabled == true)
            enabled = false;
    }
}
