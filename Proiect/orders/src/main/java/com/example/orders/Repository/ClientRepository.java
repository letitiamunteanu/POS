package com.example.orders.Repository;

import com.example.orders.POJO.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Orders, String>, ClientRepositoryCustom {
    Orders findByStatus(String initialized);
}
