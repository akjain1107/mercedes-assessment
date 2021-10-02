package com.assessment.mercedesservice.controller;

import com.assessment.mercedesservice.model.ReadResponse;
import com.assessment.mercedesservice.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class GetUserController {
    @Autowired
    private RequestService requestHandler;

    @PostMapping("/users")
    public ResponseEntity<ReadResponse> read(@RequestBody String request){
        ReadResponse response = requestHandler.read(request);
        System.out.println(response.getUsers());
        return new ResponseEntity<ReadResponse>(response, HttpStatus.OK);
    }
}
