package com.assessment.mercedesclient.service;

import com.assessment.mercedesbenz.gprc.User;
import com.assessment.mercedesbenz.gprc.UserUpdateServiceGrpc;
import com.assessment.mercedesbenz.gprc.UserWriteServiceGrpc;
import com.assessment.mercedesclient.model.ReadResponse;
import com.assessment.mercedesclient.util.Constants;
import com.assessment.mercedesclient.util.Validator;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestHandler {
    private UserWriteServiceGrpc.UserWriteServiceBlockingStub blockingStub;
    private UserUpdateServiceGrpc.UserUpdateServiceBlockingStub updateBlockingStub;
    @Autowired
    private RestTemplate restTemplate;
    public User.Response store(com.assessment.mercedesclient.model.User user, String fileType) {
        Validator.validateInput(user,fileType);
        ManagedChannel channel = prepareChannel();
        blockingStub = UserWriteServiceGrpc.newBlockingStub(channel);
        User.UserDetails userDetails  = prepareWriteRequest(user,fileType);
        return addUser(userDetails);
    }

    private ManagedChannel prepareChannel() {
        return ManagedChannelBuilder.forAddress(Constants.LOCALHOST, 9090)
                .usePlaintext()
                .build();
    }

    private User.Response addUser(User.UserDetails user) {
        return blockingStub.store(user);
    }

    public User.Response update(String id, com.assessment.mercedesclient.model.User updateDetail,
                                String fileType) {
        Validator.validateInput(updateDetail,fileType);
        User.UpdateDetails request = User.UpdateDetails.newBuilder()
                .setFilterId(id)
                .setUserDetails(User.UserDetails.newBuilder().
                        setName(updateDetail.getName())
                        .setDob(updateDetail.getDob())
                        .setSalary(String.valueOf(updateDetail.getSalary()))
                        .setAge(updateDetail.getAge())
                        .setFileType(fileType)
                        .build())
                .build();
        User.Response response = executeUpdateUserService(request);
        return response;
    }

    private User.Response executeUpdateUserService(User.UpdateDetails user) {
        ManagedChannel channel = prepareChannel();
        updateBlockingStub = UserUpdateServiceGrpc.newBlockingStub(channel);
        User.Response response = updateBlockingStub.update(user);
        System.out.println(response.getMessage());
        return response;
    }

    private ResponseEntity<ReadResponse> executeReadUserService(String readInput){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(readInput,headers);
        ResponseEntity<ReadResponse> response =
                restTemplate.postForEntity(Constants.READ_SERVICE,
                request, ReadResponse.class);
        return response;
    }

    private User.UserDetails prepareWriteRequest(com.assessment.mercedesclient.model.User userDetails, String fileType) {
        return User.UserDetails.newBuilder()
                .setId(userDetails.getUuid())
                .setName(userDetails.getName())
                .setDob(userDetails.getDob())
                .setSalary(String.valueOf(userDetails.getSalary()))
                .setAge(userDetails.getAge())
                .setFileType(fileType)
                .build();
    }

    public ResponseEntity<ReadResponse> read(String request) {
        ResponseEntity<ReadResponse> response = executeReadUserService(request);
        return response;
    }
}
