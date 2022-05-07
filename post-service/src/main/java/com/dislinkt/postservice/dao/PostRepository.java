package com.dislinkt.postservice.dao;

import com.dislinkt.postservice.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findPostsByUserId(String userId);
}
