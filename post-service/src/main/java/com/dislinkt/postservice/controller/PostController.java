package com.dislinkt.postservice.controller;

import com.dislinkt.postservice.dto.PostDto;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.service.PostService;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

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
        Post post = new Post(postDto.getUserId(), postDto.getContent(), new Date(), postDto.getPostType());
        postService.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/image")
    public ResponseEntity createImagePost(@RequestPart("image") MultipartFile image, @RequestPart("post") PostDto postDto) {
        Post post = new Post(postDto.getUserId(), postDto.getContent(), new Date(), postDto.getPostType());
        try {
            post.setImage(new Binary(BsonBinarySubType.BINARY, image.getBytes()));
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        postService.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
