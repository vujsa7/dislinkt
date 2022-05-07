package com.dislinkt.postservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public Post(String userId, String content, String base64Image, Date postedAt, PostType postType) {
        this.userId = userId;
        this.content = content;
        this.base64Image = base64Image;
        this.likes = 0;
        this.dislikes = 0;
        this.postedAt = postedAt;
        this.postType = postType;
    }
}
