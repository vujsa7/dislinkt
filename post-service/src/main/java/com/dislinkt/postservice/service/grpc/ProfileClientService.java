package com.dislinkt.postservice.service.grpc;

import com.dislinkt.Empty;
import com.dislinkt.Profile;
import com.dislinkt.ProfileServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class ProfileClientService {

    @GrpcClient("profile-service")
    ProfileServiceGrpc.ProfileServiceStub profileAsynchronousClient;

    public List<String> getPublicProfiles() throws InterruptedException {
        //return new ArrayList<>();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Empty emptyRequest = Empty.newBuilder().build();
        final List<String> response = new ArrayList<>();
        profileAsynchronousClient.getPublicProfiles(emptyRequest, new StreamObserver<Profile>() {
            @Override
            public void onNext(Profile profile) {
                response.add(profile.getId());
            }

            @Override
            public void onError(Throwable throwable) {
                countDownLatch.countDown();
            }

            @Override
            public void onCompleted() {
                countDownLatch.countDown();
            }
        });
        boolean await = countDownLatch.await(1, TimeUnit.MINUTES);
        return await ? response : Collections.emptyList();
    }
}
