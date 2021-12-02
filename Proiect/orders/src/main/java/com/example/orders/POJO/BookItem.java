package com.example.orders.POJO;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookItem {

    private String isbn;
    private String title;
    private Double price;
    private Integer quantity;

}
