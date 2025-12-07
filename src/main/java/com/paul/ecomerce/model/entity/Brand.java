package com.paul.ecomerce.model.entity;

import com.paul.ecomerce.dto.brand.BrandRequestUpdateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "brands")
@Entity
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean enabled = true;

    public void updateFromDto(BrandRequestUpdateDto brandDto) {
        if(!this.getName().equals(brandDto.name()))
            this.setName(brandDto.name());

        if(brandDto.enabled() == true)
            this.setEnabled(true);

    }
}
