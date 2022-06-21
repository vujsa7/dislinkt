import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { Store } from '@ngxs/store';
import { map } from 'rxjs/operators';
import { GetUserFullnameAndImage } from 'src/app/state/user/user.actions';
import { UserState } from 'src/app/state/user/user.state';
import { ProfileService } from '../../../modules/profile/services/profile.service';

@Component({
  selector: 'pr-search-profiles-box',
  templateUrl: './search-profiles-box.component.html',
  styleUrls: ['./search-profiles-box.component.scss']
})
export class SearchProfilesBoxComponent {

  listboxVisible: boolean = false;
  searchResults: any = [];
  searchQuery: string = '';

  constructor(private profileService: ProfileService, private eRef: ElementRef, private store: Store) { }

  onKeyup(event: any){
    let query = event.target.value;
    this.searchQuery = query;
    if(query.length > 0){
      this.profileService.getProfiles(query, 10).subscribe(
        data => {
          this.listboxVisible = true;
          this.searchResults = data;
          for(let searchResult of this.searchResults){
            this.updateSearchPhotos(searchResult);
          }
        }
      );
    } else{
      this.listboxVisible = false;
    }
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

  @HostListener('document:click', ['$event'])
  clickout(event: { target: any; }) {
    if(!this.eRef.nativeElement.contains(event.target)) {
      this.listboxVisible = false;
    }
  }

  hideListbox(){
    this.listboxVisible = false;
  }

}
