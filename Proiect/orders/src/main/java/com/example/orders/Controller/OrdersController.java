package com.example.orders.Controller;

import com.example.orders.POJO.BookItem;
import com.example.orders.POJO.Orders;
import com.example.orders.POJO.RequestItem;
import com.example.orders.Services.OrdersService;
import lombok.AllArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    OrdersController(OrdersService ordersService){
        this.ordersService = ordersService;
    }

    @GetMapping("/orders/getAllOrders")
    public List<Orders> getAllOrdersController(@RequestHeader("Authorization") String token){

        return ordersService.getAllOrders(token);
    }

    @PostMapping("/orders/addNewOrder")
    public Orders addNewOrder(@RequestBody RequestItem item, @RequestHeader("Authorization") String token) throws ParseException {

        return ordersService.addOrder(item,token);

    }

    @PostMapping("/orders/delete")
    public ResponseEntity<?> deleteOrder(@RequestBody RequestItem item, @RequestHeader("Authorization") String token) throws ParseException{

        return  new ResponseEntity<>(ordersService.deleteOrder(item,token), HttpStatus.ACCEPTED);
    }

    @PostMapping("/orders/finalizeOrder")
    public ResponseEntity<?> finalizeOrder(@RequestHeader("Authorization") String token) throws ParseException {

        return new ResponseEntity<>(ordersService.finalizeOrder(token), HttpStatus.ACCEPTED);
    }

}
