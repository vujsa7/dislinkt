package com.dislinkt.profileservice.service;

import com.dislinkt.profileservice.model.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Optional<Profile> findById(String id);
    List<Profile> findProfiles(String query, boolean authenticated);
    void save(Profile profile);
}
