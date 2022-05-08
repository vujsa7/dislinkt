package com.dislinkt.postservice.model;

import lombok.Data;

@Data
public class Comment {
    private String userId;
    private String comment;

    public Comment() {}

    public Comment(String userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }
}
