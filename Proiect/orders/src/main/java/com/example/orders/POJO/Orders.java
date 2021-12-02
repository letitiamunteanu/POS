package com.example.orders.POJO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@EnableMongoRepositories
@Document(collection = "#{@clientRepository.getCollectionName()}")
public class Orders {

    @Id
    private String id;
    private Date date;
    private List<BookItem> item;
    private String status;

    public Orders(Date date, List<BookItem> item, String status){
        this.date = date;
        this.item = item;
        this.status = status;
    }
}
