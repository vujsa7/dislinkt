import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { ofActionCompleted, Select, Store } from '@ngxs/store';
import * as _ from 'lodash';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/auth.service';
import { UserFullnameAndImage } from 'src/app/state/user/user-fullname-and-image.model';
import { UserState } from 'src/app/state/user/user.state';
import { PostService } from '../../services/post.service';
import { map } from 'rxjs/operators';
import { GetUserFullnameAndImage } from 'src/app/state/user/user.actions';

@Component({
  selector: 'post-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  @Input() post: any;
  userId!: string;
  userFullnameAndImage$!: Observable<UserFullnameAndImage | undefined>;
  fullName: any;
  image: string = "/assets/profile-avatar.png";
  isCommentSectionOpen: boolean = false;
  commentText: string = "";
  isCommentingToggled: boolean = false;
  @ViewChild('comment') commentElement!: ElementRef;

  constructor(private postService: PostService, private authService: AuthService, private store: Store) { }

  ngOnInit(): void {
    this.userId = this.authService.getUserId();
    this.store.dispatch(new GetUserFullnameAndImage(this.post.userId)).subscribe(action => {
      this.userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(this.post.userId)));
      this.userFullnameAndImage$.subscribe(data => {
        if (data != undefined) {
          this.fullName = data.fullName;
          this.image = data.image;
        }
      })
    });
    for (let comment of this.post.comments)
      this.getCommentData(comment);
  }



  isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  likePost() {
    this.postService.likePost(this.post.id).subscribe(data => {
      this.post.likes = data.likes;
      this.post.dislikes = data.dislikes;
      this.post.usersWhoLiked = data.usersWhoLiked;
      this.post.usersWhoDisliked = data.usersWhoDisliked;
    })
  }

  dislikePost() {
    this.postService.dislikePost(this.post.id).subscribe(data => {
      this.post.likes = data.likes;
      this.post.dislikes = data.dislikes;
      this.post.usersWhoLiked = data.usersWhoLiked;
      this.post.usersWhoDisliked = data.usersWhoDisliked;
    })
  }

  openComments() {
    this.isCommentSectionOpen = !this.isCommentSectionOpen;
  }

  toggleComment() {
    this.isCommentSectionOpen = true;
    this.isCommentingToggled = true;
    setTimeout(() => {
      this.commentElement.nativeElement.focus();
    }, 0);
  }

  canComment() {
    if (this.commentText != "")
      return true;
    return false;
  }

  didUserLike(): boolean {
    return _.includes(this.post.usersWhoLiked, this.userId);
  }

  didUserDislike(): boolean {
    return _.includes(this.post.usersWhoDisliked, this.userId);
  }

  postComment() {
    if (this.canComment())
      this.postService.postComment(this.post.id, this.commentText).subscribe(
        data => {
          let newComment = {userId: this.userId, comment: this.commentText};
          this.post.comments.push(newComment);
          this.getCommentData(newComment);
          this.commentText = "";
          this.isCommentSectionOpen = false;
        }
      )
  }

  getCommentData(comment: any) {
    this.store.dispatch(new GetUserFullnameAndImage(comment.userId)).subscribe(action => {
      let userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(comment.userId)));
      userFullnameAndImage$.subscribe(
        data => {
          if (data != undefined) {
            comment.image = data.image;
            comment.fullName = data.fullName;
          }
        }
      )
    });
  }

}
