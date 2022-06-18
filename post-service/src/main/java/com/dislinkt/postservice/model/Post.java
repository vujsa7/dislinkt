package com.dislinkt.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Post {
    @Id
    private String id;
    private String userId;
    private String content;
    private String base64Image;
    private Integer likes;
    private Integer dislikes;
    private List<Comment> comments;
    private Date postedAt;
    private PostType postType;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;


    public Post(String userId, String content, String base64Image, Date postedAt, PostType postType) {
        this.userId = userId;
        this.content = content;
        this.base64Image = base64Image;
        this.likes = 0;
        this.dislikes = 0;
        this.postedAt = postedAt;
        this.postType = postType;
        this.usersWhoLiked = new ArrayList<String>();
        this.usersWhoDisliked = new ArrayList<String>();
        this.comments = new ArrayList<Comment>();
    }

    public Post(String id, String userId, String content,  PostType postType) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.base64Image = "";
        this.likes = 0;
        this.dislikes = 0;
        this.comments = new ArrayList<Comment>();
        this.postType = postType;
        this.usersWhoLiked = new ArrayList<String>();
        this.usersWhoDisliked = new ArrayList<String>();
        this.postedAt = new Date();
    }

    public void addUserWhoLiked(String userId){
        this.usersWhoLiked.add(userId);
    }
    public void addUserWhoDisliked(String userId){
        this.usersWhoDisliked.add(userId);
    }
    public void removeUserWhoLiked(String userId){ this.usersWhoLiked.remove(userId); }
    public void removeUserWhoDisliked(String userId){ this.usersWhoDisliked.remove(userId); }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }
}
