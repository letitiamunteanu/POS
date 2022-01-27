package com.example.orders.SOAP;


import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SoapRequest {

    //return the payload of token
    public static String SoapTokenRequest(String token){

        RestTemplate restTemplate = new RestTemplate();
        String URL = "http://localhost:6527/sample";

        //luam tokenul si excludem "Bearer"
        String jwt = token.substring(7);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_XML);

        String soapRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                "                  xmlns:us=\"http://com.example.IdentityProvider/Authentication\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <us:verifyJwtRequest>\n" +
                "            <us:token>"+ jwt + "</us:token>\n" +
                "        </us:verifyJwtRequest>\n" +
                "    </soapenv:Body>\n" +
                "  </soapenv:Envelope>";


        //set what we request (body + header in postman)
        HttpEntity<String> requestBody = new HttpEntity<>(soapRequest, httpHeaders);

        //set what we receive as response
        ResponseEntity<String> response = restTemplate.postForEntity(URL, requestBody, String.class);

        return StringUtils.substringBetween(response.getBody(), "<ns2:response>", "</ns2:response>");

    }
}
