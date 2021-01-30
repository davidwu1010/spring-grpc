package com.grpcdemo.user.service;


import com.grpcdemo.common.Genre;
import com.grpcdemo.user.UserGenreUpdateRequest;
import com.grpcdemo.user.UserResponse;
import com.grpcdemo.user.UserSearchRequest;
import com.grpcdemo.user.UserServiceGrpc;
import com.grpcdemo.user.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getUserGenre(UserSearchRequest request,
        StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId()).ifPresent(user -> {
            builder.setName(user.getName())
                .setLoginId(user.getLogin())
                .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateUserGenre(UserGenreUpdateRequest request,
        StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        this.userRepository.findById(request.getLoginId()).ifPresent(user -> {
            user.setGenre(request.getGenre().toString());
            userRepository.save(user);
            builder.setName(user.getName())
                .setLoginId(user.getLogin())
                .setGenre(Genre.valueOf(user.getGenre().toUpperCase()));
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
