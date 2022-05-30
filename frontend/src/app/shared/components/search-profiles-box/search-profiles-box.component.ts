import { Component, ElementRef, HostListener, OnInit } from '@angular/core';
import { ProfileService } from '../../../modules/profile/services/profile.service';

@Component({
  selector: 'pr-search-profiles-box',
  templateUrl: './search-profiles-box.component.html',
  styleUrls: ['./search-profiles-box.component.scss']
})
export class SearchProfilesBoxComponent implements OnInit {

  listboxVisible: boolean = false;
  searchResults: any = [];
  searchQuery: string = '';

  constructor(private profileService: ProfileService, private eRef: ElementRef) { }

  ngOnInit(): void {
  }

  onKeyup(event: any){
    let query = event.target.value;
    this.searchQuery = query;
    if(query.length > 0){
      this.profileService.getProfiles(query, 10).subscribe(
        data => {
          this.listboxVisible = true;
          this.searchResults = data;
        }
      );
    } else{
      this.listboxVisible = false;
    }
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
