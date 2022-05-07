package com.dislinkt.postservice.dto;

import com.dislinkt.postservice.model.PostType;
import lombok.Data;

@Data
public class PostDto {
    private String userId;
    private String content;
    private String base64Image;
    private PostType postType;
}
