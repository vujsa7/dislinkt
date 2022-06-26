import { Component, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import * as _ from 'lodash';
import { map } from 'rxjs/operators';
import { GetUserFullnameAndImage } from 'src/app/state/user/user.actions';
import { UserState } from 'src/app/state/user/user.state';
import { NetworkService } from './services/network.service';

@Component({
  selector: 'app-network',
  templateUrl: './network.component.html',
  styleUrls: ['./network.component.scss']
})
export class NetworkComponent implements OnInit {
  
  completed: boolean = false;
  requests!: any;
  following!: any;
  followers!: any;
  isPrivate!: boolean;
  

  constructor(private networkService: NetworkService, private store: Store) { }

  ngOnInit(): void {
    this.networkService.getIncomingFollowRequests().subscribe(
      data => {
        this.requests = data.requests;
        this.following = data.following;
        this.followers = data.followers;
        this.isPrivate = data.private;
        this.fetchImages();
      },
      error => {
        console.log(error);
      }
    ).add(() => {
      this.completed = true;
    })
  }

  fetchImages() {
    for(let profile of this.following){
      this.store.dispatch(new GetUserFullnameAndImage(profile.id)).subscribe(action => {
        let userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(profile.id)));
        userFullnameAndImage$.subscribe(
          data => {
            if (data != undefined) {
              profile.image = data.image;
            }
          }
        )
      });
    }
    for(let profile of this.requests){
      this.store.dispatch(new GetUserFullnameAndImage(profile.id)).subscribe(action => {
        let userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(profile.id)));
        userFullnameAndImage$.subscribe(
          data => {
            if (data != undefined) {
              profile.image = data.image;
            }
          }
        )
      });
    }
    for(let profile of this.followers){
      this.store.dispatch(new GetUserFullnameAndImage(profile.id)).subscribe(action => {
        let userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(profile.id)));
        userFullnameAndImage$.subscribe(
          data => {
            if (data != undefined) {
              profile.image = data.image;
            }
          }
        )
      });
    }
  }

  acceptRequest(id: string){
    this.networkService.acceptFollowRequests(id).subscribe(
      data => {
        this.followers.push(_.find(this.requests, p => p.id == id));
        _.remove(this.requests, {id: id});
      },
      error => {
        console.log(error);
      }
    );
  }

  deleteRequest(id: string){
    this.networkService.deleteFollowRequests(id).subscribe(
      data => {
        _.remove(this.requests, {id: id});
      },
      error => {
        console.log(error);
      }
    );
  }

  isNotFollowing(id: string): boolean{
    return !_.find(this.following, {id: id})
  }

  unfollowProfile(id: string){
    this.networkService.unfollowProfile(id).subscribe(
      data => {
        _.remove(this.following, {id: id});
      }
    )
  }

  removeFollower(id: string){
    this.networkService.removeFollower(id).subscribe(
      data => {
        _.remove(this.followers, {id: id});
      }
    )
  }
}
