import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FeedRoutingModule } from './feed-routing.module';
import { FeedComponent } from './feed.component';
import { FeedService } from './services/feed.service';
import { SharedModule } from 'src/app/shared/shared.module';
import { PostModule } from '../post/post.module';


@NgModule({
  declarations: [
    FeedComponent
  ],
  imports: [
    CommonModule,
    FeedRoutingModule,
    SharedModule,
    PostModule
  ],
  providers: [
    FeedService
  ]
})
export class FeedModule { }
