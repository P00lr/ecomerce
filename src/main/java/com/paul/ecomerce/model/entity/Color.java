package com.paul.ecomerce.model.entity;

import com.paul.ecomerce.dto.color.ColorRequestUpdateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "colors")
@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean enabled = true;

    public void updateFromDto(ColorRequestUpdateDto colorDto) {
        if(!this.getName().equals(colorDto.name()))
            this.setName(colorDto.name());

        if(colorDto.enabled() == true)
            this.setEnabled(true);

    }
}
