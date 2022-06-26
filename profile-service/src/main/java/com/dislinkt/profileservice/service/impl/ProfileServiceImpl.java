package com.dislinkt.profileservice.service.impl;

import com.dislinkt.profileservice.dao.ProfileRepository;
import com.dislinkt.profileservice.exception.UsernameAlreadyExistsException;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.service.ProfileService;
import com.dislinkt.profileservice.service.grpc.ConnectionClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;
    private final ConnectionClientService connectionClientService;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ConnectionClientService connectionClientService) {
        this.profileRepository = profileRepository;
        this.connectionClientService = connectionClientService;
    }

    @Override
    public Optional<Profile> findById(String id) {
        return profileRepository.findById(id);
    }

    @Override
    public Optional<Profile> findByUsername(String username) {
        return profileRepository.findProfileByUsername(username);
    }

    @Override
    public List<Profile> findProfiles(String query, boolean authenticated) {
        if(authenticated){
            return profileRepository.findProfiles(query);
        }
        return profileRepository.findPublicProfiles(query);
    }

    @Override
    public Page<Profile> findProfiles(String query, Integer size, boolean authenticated) {
        if(authenticated){
            return profileRepository.findProfiles(query, PageRequest.of(0, size));
        }
        return profileRepository.findPublicProfiles(query, PageRequest.of(0, size));
    }

    @Override
    public void save(Profile profile) {
        profileRepository.findProfileByUsername(profile.getUsername())
                .ifPresentOrElse(p -> {
                    throw new UsernameAlreadyExistsException();
                }, () -> {
                    profileRepository.save(profile);
                });
    }

    @Override
    public void saveInfo(Profile profile) {
        profileRepository.save(profile);
    }

    @Override
    public void update(Profile profile, boolean isNewUsername) {
        if(isNewUsername) {
            profileRepository.findProfileByUsername(profile.getUsername())
                    .ifPresentOrElse(p -> {
                        throw new UsernameAlreadyExistsException();
                    }, () -> {
                        profileRepository.save(profile);
                    });
        } else {
            profileRepository.save(profile);
        }

    }

    @Override
    public List<String> getPublicProfileIds(){
        return profileRepository.findPublicProfiles("").stream().map(profile -> profile.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Profile> getFollowerRequestsProfiles(String userId) throws InterruptedException {
        List<String> followRequests = connectionClientService.getFollowerRequests(userId);
        if(followRequests.isEmpty())
            return new ArrayList<>();
        return followRequests.stream().map(p -> profileRepository.findById(p).get()).collect(Collectors.toList());
    }

    @Override
    public List<Profile> getFollowingProfiles(String userId) throws InterruptedException {
        List<String> connections = connectionClientService.getFollowing(userId);
        if(connections.isEmpty())
            return new ArrayList<>();
        return connections.stream().map(p -> profileRepository.findById(p).get()).collect(Collectors.toList());
    }

    @Override
    public List<Profile> getFollowersProfiles(String id) throws InterruptedException {
        List<String> connections = connectionClientService.getFollowers(id);
        if(connections.isEmpty())
            return new ArrayList<>();
        return connections.stream().map(p -> profileRepository.findById(p).get()).collect(Collectors.toList());
    }


}
