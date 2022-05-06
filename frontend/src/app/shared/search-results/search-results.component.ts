import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit {
  query: string = '';
  searchResults: any;

  constructor(private route: ActivatedRoute, private profileService: ProfileService) { }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
        this.query = params.query;
        this.profileService.getProfiles(this.query).subscribe(
          data => {
            this.searchResults = data;
          }
        )
      }
    );
  }

}
