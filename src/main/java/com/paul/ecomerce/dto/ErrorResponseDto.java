package com.paul.ecomerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ErrorResponseDto {

    private String error;
    private int statusCode;
    private LocalDateTime date;
    private String path;
    private List<String> messages;

}
