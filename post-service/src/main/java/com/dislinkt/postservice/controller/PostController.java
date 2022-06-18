package com.dislinkt.postservice.controller;

import com.dislinkt.postservice.dto.ConnectionsDto;
import com.dislinkt.postservice.dto.PostDto;
import com.dislinkt.postservice.dto.SearchedPostDto;
import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.Post;
import com.dislinkt.postservice.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/posts")
@Slf4j
public class PostController {
    private final PostService postService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public PostController(PostService postService, WebClient.Builder webClientBuilder) {
        this.postService = postService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping
    public ResponseEntity createPost(Principal principal, @RequestBody PostDto postDto){

        Post post = new Post(principal.getName(), postDto.getContent(), postDto.getBase64Image(), new Date(), postDto.getPostType());
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


    @GetMapping(value = "/feed")
    public Mono<List<SearchedPostDto>> getFeed(Principal principal, JwtAuthenticationToken jwt){
        // CALL CONNECTION SERVICE AND FETCH ALL PROFILE ID
        // OR CALL API GATEWAY
        Mono<ConnectionsDto> response = webClientBuilder.build()
                .get()
                .uri("http://connection-service/connections/" + principal.getName())
                .headers(h -> h.setBearerAuth(jwt.getToken().getTokenValue()))
                .retrieve()
                .bodyToMono(ConnectionsDto.class);

        final Mono<List<SearchedPostDto>> postsDto = response.map(connectionsDto -> {
            List<Post> posts = postService.getFeed(connectionsDto.getIds());
            List<SearchedPostDto> dtos = posts.stream()
                    .map(post -> new SearchedPostDto(post.getContent(), post.getBase64Image(), post.getLikes(), post.getDislikes(),
                            post.getComments(), post.getPostedAt(), post.getPostType()))
                    .collect(Collectors.toList());
            return dtos;
        });

        return postsDto;
    }

    @PostMapping(value = "{postID}/like")
    public ResponseEntity likePost(Principal principal, @PathVariable() String postID, @RequestBody String userId, ServerHttpRequest request){

        if(!principal.getName().equals(userId)) {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP POST \"/posts/like\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(postService.likePost(postID, userId) == -1)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "{postID}/dislike")
    public ResponseEntity dislikePost(Principal principal, @PathVariable() String postID, @RequestBody String userId, ServerHttpRequest request){

        if(!principal.getName().equals(userId)) {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP POST \"/posts/dislike\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(postService.dislikePost(postID, userId) == -1)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "{postID}/comment")
    public ResponseEntity comment(Principal principal, @PathVariable() String postID, @RequestBody Comment comment, ServerHttpRequest request){

        if(!principal.getName().equals(comment.getUserId())) {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP POST \"/posts/comment\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        postService.comment(postID, comment);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
