package com.dislinkt.connectionservice.service.grpc;
import com.dislinkt.Profile;
import com.dislinkt.ProfileServiceGrpc;
import com.dislinkt.connectionservice.dao.ConnectionRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ConnectionServerService extends ProfileServiceGrpc.ProfileServiceImplBase {

    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionServerService(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public void getConnections(Profile request, StreamObserver<Profile> responseObserver) {
        List<String> profileIds = this.connectionRepository.findConnectionsById(request.getId());
        List<Profile> profiles = new ArrayList();
        for (String profileId: profileIds) {
            profiles.add(Profile.newBuilder().setId(profileId).build());
        }
        profiles.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

}
