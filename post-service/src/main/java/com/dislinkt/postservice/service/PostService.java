package com.dislinkt.postservice.service;

import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.Post;

import java.util.List;

public interface PostService {
    void save(Post post);
    List<Post> findUserPosts(String userId);

    List<Post> getFeed(String id) throws InterruptedException;

    int likePost(String postID, String userId);
    int dislikePost(String postID, String userId);

    void comment(String postID, Comment comment);

    Post getPost(String postID);
    List<Post> getPublicFeed() throws InterruptedException;

}
