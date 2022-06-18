package com.dislinkt.postservice.service.impl;

import com.dislinkt.postservice.dao.PostRepository;
import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Override
    public int likePost(String postID, String userId) {
        Optional<Post> p = postRepository.findById(postID);

        if(p.isEmpty())
            return -1;

        Post post = p.get();

        if(post.getUsersWhoLiked().contains(userId)){
            post.setLikes(post.getLikes() - 1);
            post.removeUserWhoLiked(userId);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.addUserWhoLiked(userId);

            if(post.getUsersWhoDisliked().contains(userId)){
                post.removeUserWhoDisliked(userId);
                post.setDislikes(post.getDislikes() - 1);
            }
        }

        postRepository.save(post);
        return 1;
    }

    @Override
    public int dislikePost(String postID, String userId) {
        Optional<Post> p = postRepository.findById(postID);

        if(p.isEmpty())
            return -1;

        Post post = p.get();

        if(post.getUsersWhoDisliked().contains(userId)){
            post.setDislikes(post.getDislikes() - 1);
            post.removeUserWhoDisliked(userId);
        } else {
            post.setDislikes(post.getDislikes() + 1);
            post.addUserWhoDisliked(userId);

            if(post.getUsersWhoLiked().contains(userId)){
                post.removeUserWhoLiked(userId);
                post.setLikes(post.getLikes() - 1);
            }
        }

        postRepository.save(post);
        return 1;
    }



    @Override
    public void comment(String postID, Comment comment) {
        Post post = postRepository.findPostById(postID);
        post.addComment(comment);
        postRepository.save(post);
    }

    @Override
    public Post getPost(String postID) {
        Optional<Post> p = postRepository.findById(postID);

        if(p.isEmpty())
            return null;

        return p.get();
    }
}
