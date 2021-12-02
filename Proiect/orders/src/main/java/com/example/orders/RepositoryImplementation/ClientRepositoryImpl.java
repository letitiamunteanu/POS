package com.example.orders.RepositoryImplementation;

import com.example.orders.Repository.ClientRepositoryCustom;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImpl implements ClientRepositoryCustom {

    private static String collectionName = "defaultName";

    @Override
    public String getCollectionName() {
        return collectionName;
    }

    @Override
    public void setCollectionName(String name) {
        collectionName = name;
    }
}
