import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Store } from '@ngxs/store';
import { map } from 'rxjs/operators';
import { GetUserFullnameAndImage } from 'src/app/state/user/user.actions';
import { UserState } from 'src/app/state/user/user.state';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'pr-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit {

  query: string = '';
  searchResults: any;

  constructor(private route: ActivatedRoute, private profileService: ProfileService, private store: Store) { }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
        this.query = params.query;
        this.profileService.getProfiles(this.query).subscribe(
          data => {
            this.searchResults = data;
            for (let searchResult of this.searchResults){
              this.updateSearchPhotos(searchResult);
            }
          }
        )
      }
    );
  }

  updateSearchPhotos(searchResult: any) {
    this.store.dispatch(new GetUserFullnameAndImage(searchResult.id)).subscribe(action => {
      let userFullnameAndImage$ = this.store.select(UserState.selectUserFullnameAndImage).pipe(map(filterFn => filterFn(searchResult.id)));
      userFullnameAndImage$.subscribe(data => {
        if (data != undefined) {
          searchResult.fullName = data.fullName;
          if(data.image != "")
            searchResult.image = data.image;
          else
            searchResult.image = "https://upload.wikimedia.org/wikipedia/commons/7/7c/Profile_avatar_placeholder_large.png";
        }
      })
    });
  }

}
