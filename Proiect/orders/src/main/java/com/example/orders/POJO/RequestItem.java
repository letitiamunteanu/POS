package com.example.orders.POJO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class RequestItem {

    private String isbn;
    private Integer quantity;
}
