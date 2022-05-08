package com.dislinkt.postservice.dao;

import com.dislinkt.postservice.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    List<Post> findPostsByUserId(String userId);

    @Query("{'userId': {$in: ?0}}")
    List<Post> findAllPostsByUserIds(Iterable<String> ids);
    Post findPostById(String id);

}
