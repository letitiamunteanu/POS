package com.example.orders.Services;

import com.example.orders.Exception.BadRequestException;
import com.example.orders.Exception.Error404Exception;
import com.example.orders.Exception.OrdersABCException;
import com.example.orders.POJO.BookItem;
import com.example.orders.POJO.Orders;
import com.example.orders.POJO.RequestItem;
import com.example.orders.Repository.ClientRepository;
import com.example.orders.SOAP.SoapRequest;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrdersService {

    @Autowired
    ClientRepository clientRepository;

    OrdersService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    //Get cart - iau cosul de cumparaturi pe care il updatez
    private Orders getActiveCart(String name){

        clientRepository.setCollectionName(name);
        Orders activeOrder = clientRepository.findByStatus("initialized");

        if(activeOrder == null){
            activeOrder = new Orders(new Date(), new ArrayList<BookItem>(), "initialized");
            clientRepository.save(activeOrder);
        }

        return activeOrder;
    }

    //Create/update a new order => Post
    public Orders addOrder(RequestItem item, String token) throws ParseException {

        //rest template
        RestTemplate restTemplate = new RestTemplate();
        JSONParser parser = new JSONParser();

        //jwt
        String[] chunks = token.replace("Bearer ", "").split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        JSONObject jsonForPayload = (JSONObject) parser.parse(payload);

        String fooResourceUrl  = "http://localhost:8080/api/bookcollection/books/blockQuantity/" + item.getIsbn() + "?quantity=" + item.getQuantity();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> request = new HttpEntity<>("", headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl, request, String.class);;
            JSONObject json = (JSONObject) parser.parse(response.getBody());


            boolean condition = false;
            clientRepository.setCollectionName(jsonForPayload.get("sub").toString());
            Orders order = getActiveCart(jsonForPayload.get("sub").toString());

            for (BookItem itm : order.getItem()) {
                if (itm.getIsbn().equals(item.getIsbn())) {
                    itm.setQuantity(item.getQuantity() + itm.getQuantity());
                    condition = true;
                    break;
                }
            }

            BookItem newItem = new BookItem(json.get("id").toString(),
                    json.get("titlu").toString(),
                    Double.parseDouble(json.get("pret").toString()),
                    item.getQuantity());


            if (!condition) {
                order.getItem().add(newItem);
            }

            clientRepository.save(order);
            return order;
        }
        catch(Exception ex){
            throw new OrdersABCException(ex.getMessage());
        }

    }

    //Read all orders => GET
    public List<Orders> getAllOrders(String token){

        String tkn = SoapRequest.SoapTokenRequest(token);

        if(tkn.equals("Expired") || tkn.equals("Invalid")){

            throw new BadRequestException(tkn);
        }
        clientRepository.setCollectionName((tkn.split(" ")[0]).toString());
        return clientRepository.findAll();
    }

    //Delete order
    public Orders deleteOrder(RequestItem item, String token) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl  = "http://localhost:8080/api/bookcollection/books/releaseQuantity/" + item.getIsbn() + "?quantity=" + item.getQuantity();

        JSONParser parser = new JSONParser();

        //jwt
        String[] chunks = token.replace("Bearer ", "").split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonForPayload = (JSONObject) parser.parse(payload);

        clientRepository.setCollectionName(jsonForPayload.get("sub").toString());
        Orders orderToDelete = getActiveCart(jsonForPayload.get("sub").toString());

        boolean found = false;

        if(orderToDelete.getItem() == null){
             throw new Error404Exception("Your order does not contain any items");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> request = new HttpEntity<>("", headers);

        try{
            ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl,request, String.class);
            String respReq = response.getBody();

            if(respReq.equals("ok")) {
                for(BookItem itm : orderToDelete.getItem()){
                    if(itm.getIsbn().equals(item.getIsbn())){
                        found = true;

                        if(itm.getQuantity().equals(item.getQuantity())){
                            orderToDelete.getItem().remove(itm);
                            break;
                        }

                        if(itm.getQuantity() < item.getQuantity()){
                            throw new BadRequestException("The quantity is greater than the existing one");
                        }

                        if(itm.getQuantity() > item.getQuantity()) {
                            itm.setQuantity(itm.getQuantity() - item.getQuantity());
                        }
                    }
                }
            }

            if(!found){
                throw new Error404Exception("This object does not exist in this order");
            }

            clientRepository.save(orderToDelete);
            return orderToDelete;
        }
        catch (Exception ex){
            throw new OrdersABCException(ex.getMessage());
        }
    }

    public Orders finalizeOrder(String token) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl  = "http://localhost:8080/api/bookcollection/books/finalize";

        JSONParser parser = new JSONParser();

        //jwt
        String[] chunks = token.replace("Bearer ", "").split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        JSONObject jsonForPayload = (JSONObject) parser.parse(payload);

        clientRepository.setCollectionName(jsonForPayload.get("sub").toString());
        Orders orderToFinalize = getActiveCart(jsonForPayload.get("sub").toString());

        Map<String, Integer> booksFromOrder = new HashMap<>();

        orderToFinalize.getItem().forEach(bookItem -> {
            booksFromOrder.put(bookItem.getIsbn(), bookItem.getQuantity());

        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token.substring(7));
        HttpEntity<Map<String,Integer>> request = new HttpEntity<>(booksFromOrder, headers);

        try{
            ResponseEntity<String> response = restTemplate.postForEntity(fooResourceUrl, request, String.class);

            if(Objects.equals(response.getBody(), "ok")){
                orderToFinalize.setStatus("finalized");
                clientRepository.save(orderToFinalize);
            }

            return orderToFinalize;

        }
        catch(Exception ex){
            throw new OrdersABCException(ex.getMessage());
        }

    }

}

