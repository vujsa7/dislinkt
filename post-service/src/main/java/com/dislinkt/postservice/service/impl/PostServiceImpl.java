package com.dislinkt.postservice.service.impl;

import com.dislinkt.postservice.dao.PostRepository;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public List<Post> findUserPosts(String userId) {
        return postRepository.findPostsByUserId(userId);
    }

    @Override
    public List<Post> getFeed(List<String> connectionsIds) {
        return postRepository.findAllPostsByUserIds(connectionsIds);
    }
}
