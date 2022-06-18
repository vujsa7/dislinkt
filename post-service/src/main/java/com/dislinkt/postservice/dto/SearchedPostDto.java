package com.dislinkt.postservice.dto;

import com.dislinkt.postservice.model.Comment;
import com.dislinkt.postservice.model.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SearchedPostDto {
    private String id;
    private String content;
    private String base64Image;
    private Integer likes;
    private Integer dislikes;
    private List<Comment> comments;
    private Date postedAt;
    private PostType postType;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;
}
