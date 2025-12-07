package com.paul.ecomerce.model.entity;

import java.util.HashSet;
import java.util.Set;

import com.paul.ecomerce.dto.category.CategoryRequestUpdateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private boolean enabled = true;

    @OneToMany(mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    public void updateFromDto(CategoryRequestUpdateDto categoryDto) {
        if(!this.getName().equals(categoryDto.name()))
            this.setName(categoryDto.name());

        if(categoryDto.enabled() == true)
            this.setEnabled(true);

    }
}
