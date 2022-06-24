package com.dislinkt.profileservice.service.grpc;

import com.dislinkt.Empty;
import com.dislinkt.Profile;
import com.dislinkt.ProfileServiceGrpc;
import com.dislinkt.profileservice.service.ProfileService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ProfileServerService extends ProfileServiceGrpc.ProfileServiceImplBase {

    private final ProfileService profileService;

    public ProfileServerService(ProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void getPublicProfiles(Empty request, StreamObserver<Profile> responseObserver) {
        List<String> profileIds = this.profileService.getPublicProfileIds();
        List<Profile> profiles = new ArrayList();
        for (String profileId: profileIds) {
            profiles.add(Profile.newBuilder().setId(profileId).build());
        }
        profiles.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
