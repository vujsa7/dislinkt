package com.dislinkt.profileservice.service.impl;

import com.dislinkt.profileservice.dao.ProfileRepository;
import com.dislinkt.profileservice.exception.UsernameAlreadyExistsException;
import com.dislinkt.profileservice.model.Profile;
import com.dislinkt.profileservice.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
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

    //test
    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }
}
