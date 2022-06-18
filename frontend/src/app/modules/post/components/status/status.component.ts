import { Component, Input, OnInit } from '@angular/core';
import * as _ from 'lodash';
import { AuthService } from 'src/app/core/auth.service';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'post-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  @Input() post: any;
  userId!: string;

  constructor(private postService: PostService, private authService: AuthService) { }
  
  ngOnInit(): void {
    this.userId = this.authService.getUserId();
  }

  likePost(){
    this.postService.likePost(this.post.id).subscribe(data => {
      this.post.likes = data.likes;
      this.post.dislikes = data.dislikes;
      this.post.usersWhoLiked = data.usersWhoLiked;
      this.post.usersWhoDisliked = data.usersWhoDisliked;
    })
  }

  dislikePost(){
    this.postService.dislikePost(this.post.id).subscribe(data => {
      this.post.likes = data.likes;
      this.post.dislikes = data.dislikes;
      this.post.usersWhoLiked = data.usersWhoLiked;
      this.post.usersWhoDisliked = data.usersWhoDisliked;
    })
  }

  toggleComment(){

  }

  didUserLike() : boolean{
    return _.includes(this.post.usersWhoLiked, this.userId);
  }

  didUserDislike() : boolean{
    return _.includes(this.post.usersWhoDisliked, this.userId);
  }

}
