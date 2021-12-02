package com.example.orders.Repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepositoryCustom {

    String getCollectionName();
    void setCollectionName(String name);


}
