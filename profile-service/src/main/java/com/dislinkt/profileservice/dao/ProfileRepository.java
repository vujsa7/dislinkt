package com.dislinkt.profileservice.dao;

import com.dislinkt.profileservice.model.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    @Query("{$or: [{'username': {'$regex':'?0','$options':'i'}}, {'firstName': {'$regex':'?0','$options':'i'}}, {'lastName': {'$regex':'?0','$options':'i'}}]}")
    List<Profile> findProfiles(String query);
    @Query("{profileType: 'PUBLIC', $or: [{'username': {'$regex':'?0','$options':'i'}}, {'firstName': {'$regex':'?0','$options':'i'}}, {'lastName': {'$regex':'?0','$options':'i'}}]}")
    List<Profile> findPublicProfiles(String query);
    @Query("{$or: [{'username': {'$regex':'?0','$options':'i'}}, {'firstName': {'$regex':'?0','$options':'i'}}, {'lastName': {'$regex':'?0','$options':'i'}}]}")
    Page<Profile> findProfiles(String query, Pageable pageable);
    @Query("{profileType: 'PUBLIC', $or: [{'username': {'$regex':'?0','$options':'i'}}, {'firstName': {'$regex':'?0','$options':'i'}}, {'lastName': {'$regex':'?0','$options':'i'}}]}")
    Page<Profile> findPublicProfiles(String query, Pageable pageable);
    Optional<Profile> findProfileByUsername(String username);
}
