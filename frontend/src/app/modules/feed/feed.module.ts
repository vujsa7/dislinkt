import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FeedRoutingModule } from './feed-routing.module';
import { FeedComponent } from './feed.component';
import { FeedService } from './services/feed.service';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    FeedComponent
  ],
  imports: [
    CommonModule,
    FeedRoutingModule,
    SharedModule
  ],
  providers: [
    FeedService
  ]
})
export class FeedModule { }
