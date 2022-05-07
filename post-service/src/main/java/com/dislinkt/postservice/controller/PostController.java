package com.dislinkt.postservice.controller;

import com.dislinkt.postservice.dto.PostDto;
import com.dislinkt.postservice.dto.SearchedPostDto;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto){
        Post post = new Post(postDto.getUserId(), postDto.getContent(), postDto.getBase64Image(), new Date(), postDto.getPostType());
        postService.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity findUserPosts(@PathVariable() String userId){
        List<Post> posts = postService.findUserPosts(userId);

        List<SearchedPostDto> dtos = posts.stream()
                .map(post -> new SearchedPostDto(post.getContent(), post.getBase64Image(), post.getLikes(), post.getDislikes(),
                        post.getComments(), post.getPostedAt(), post.getPostType()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
