import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import * as _ from 'lodash';
import { AuthService } from 'src/app/core/auth.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { FeedService } from './services/feed.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {

  posts: any = [];
  

  constructor(private authService: AuthService, private feedService: FeedService) { }

  async ngOnInit() {
    if(this.isAuthenticated()){
      this.feedService.getFeed().subscribe(
        data => {
          this.posts = data;
        },
        error => {
          console.log(error.message)
        }
      )
    }
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  sortedPosts(){
    return _.sortBy(this.posts, [p => p.postedAt]).reverse();
  }

}
