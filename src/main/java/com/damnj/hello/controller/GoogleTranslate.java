package com.damnj.hello.controller;

import com.damnj.hello.aop.LogExecutionTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/external/hello")
public class GoogleTranslate {

    private RestTemplate restTemplate=new RestTemplate();

    @Value( "${google.translate.endpoint: NA}")
    private String translateEndpoint;

    @Value( "${google.translate.apiKey: NA}")
    private String translateAPIKey;
    @Value( "${google.translate.apiHost: NA}")
    private String translateHost;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @LogExecutionTime
    public String transalate(@RequestParam("language") String language){

        String body="q=Hello%2C%20world!&target="+language+"&source=en";


        HttpHeaders header = new HttpHeaders();
        header.add("content-type", "application/x-www-form-urlencoded");
        header.add("Accept-Encoding", "application/gzip");
        header.add("X-RapidAPI-Key", translateAPIKey);
        header.add("X-RapidAPI-Host", translateHost);

        HttpEntity<String> requestEntity = new HttpEntity<>( body, header);
        String outout =  restTemplate.postForObject( translateEndpoint, requestEntity, String.class );

        return outout;
    }
}
