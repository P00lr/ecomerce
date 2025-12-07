package com.paul.ecomerce.model.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paul.ecomerce.model.enums.AdjustmentType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Table(name = "adjustments")
@Entity
public class Adjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date = LocalDateTime.now();
    private AdjustmentType type;
    private String description;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean enabled = true;

    @OneToMany(mappedBy = "adjustment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<AdjustmentDetail> adjustmentDetails = new LinkedHashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    public AdjustmentDetail createDetail(ProductVariant productVariant, Integer quantity) {
        AdjustmentDetail detail = new AdjustmentDetail();
        detail.setAdjustment(this);
        detail.setProductVariant(productVariant);
        detail.setQuantity(quantity);

        adjustmentDetails.add(detail);

        return detail;
    }

    public void deactivate() {
        if (enabled == true)
            enabled = false;
    }

    public void activate() {
        if (enabled == false)
            enabled = true;
    }
}
