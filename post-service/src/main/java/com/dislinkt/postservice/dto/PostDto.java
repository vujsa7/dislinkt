package com.dislinkt.postservice.dto;

import com.dislinkt.postservice.model.PostType;
import lombok.Data;

import java.util.List;

@Data
public class PostDto {
    private String content;
    private String base64Image;
    private PostType postType;
}
