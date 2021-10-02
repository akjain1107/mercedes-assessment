package com.assessment.mercedesservice.grpc;

import com.assessment.mercedesbenz.gprc.User;
import com.assessment.mercedesbenz.gprc.UserUpdateServiceGrpc;
import com.assessment.mercedesservice.service.RequestService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class UserUpdateServiceGrpcImpl extends UserUpdateServiceGrpc.UserUpdateServiceImplBase {
    @Autowired
    private RequestService requestHandler;
    @Override
    public void update(User.UpdateDetails request, StreamObserver<User.Response> responseObserver) {
        User.Response response= requestHandler.updateUser(request);
        com.assessment.mercedesservice.model.User user = new com.assessment.mercedesservice.model.User(request.getUserDetails().getId(),
                request.getUserDetails().getName(),request.getUserDetails().getDob(),Double.parseDouble(request.getUserDetails().getSalary()),
                request.getUserDetails().getAge());
        System.out.println(user);
        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
