package com.assessment.mercedesservice.grpc;

import com.assessment.mercedesbenz.gprc.User;
import com.assessment.mercedesbenz.gprc.UserWriteServiceGrpc;
import com.assessment.mercedesservice.service.RequestService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class UserWriteServiceGrpcImpl extends UserWriteServiceGrpc.UserWriteServiceImplBase {
    @Autowired
    private RequestService requestHandler;
    @Override
    public void store(User.UserDetails request, StreamObserver<User.Response> responseObserver) {
        User.Response response = requestHandler.addUser(request);
        com.assessment.mercedesservice.model.User user = new com.assessment.mercedesservice.model.User(request.getId(),
                request.getName(),request.getDob(),Double.parseDouble(request.getSalary()),
                request.getAge());
        System.out.println(user);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}