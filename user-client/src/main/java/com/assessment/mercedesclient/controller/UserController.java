package com.assessment.mercedesclient.controller;

import com.assessment.mercedesclient.model.ReadResponse;
import com.assessment.mercedesclient.model.User;
import com.assessment.mercedesclient.model.UserResponse;
import com.assessment.mercedesclient.service.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

//{ name: “Hello” null/empty,
// dob: “20-08-2020”, Format
// salary: “122111241.150”, Float regx
// age: 20 numeric}

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private RequestHandler requestHandler;
    @PostMapping("/add")
    public ResponseEntity<UserResponse> store(@RequestBody User userDetails, @RequestHeader String fileType){
        userDetails.setUuid(String.valueOf(UUID.randomUUID()));
        com.assessment.mercedesbenz.gprc.User.Response response = requestHandler.store(userDetails, fileType);
        return new ResponseEntity<UserResponse>(new UserResponse(userDetails),getHttpStatus(response));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody User userDetails,@PathVariable("id") String id,
        @RequestHeader String fileType){
        com.assessment.mercedesbenz.gprc.User.Response response
            = requestHandler.update(id,userDetails,fileType);
        userDetails.setUuid(id);
        return new ResponseEntity<UserResponse>(new UserResponse(userDetails),getHttpStatus(response));
    }

    private HttpStatus getHttpStatus(com.assessment.mercedesbenz.gprc.User.Response response) {
        if(0 == response.getStatus() )
            return HttpStatus.OK;
        else if(2 == response.getStatus() ) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReadResponse> read(@PathVariable("id") String id){
        return requestHandler.read(id);
    }

    @GetMapping("/users")
    public ResponseEntity<ReadResponse> read(){
        ResponseEntity<ReadResponse> response =  requestHandler.read("All");
        System.out.println(response);
        return response;
    }
}
