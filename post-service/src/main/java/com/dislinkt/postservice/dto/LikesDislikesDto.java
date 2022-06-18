package com.dislinkt.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LikesDislikesDto {
    private int likes;
    private int dislikes;
    private List<String> usersWhoLiked;
    private List<String> usersWhoDisliked;
}
