import { Component, OnInit } from '@angular/core';
import { FeedService } from './services/feed.service';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {

  posts: any = [];

  constructor(private feedService: FeedService) { }

  async ngOnInit(){
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
