import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { SearchComponent } from './components/search/search.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { RouterModule } from '@angular/router';
import { ProfileComponent } from './components/profile/profile.component';



@NgModule({
  declarations: [
    PrimaryButtonComponent,
    SecondaryButtonComponent,
    SearchComponent,
    SearchResultsComponent,
    ProfileComponent
  ],
  imports: [
    CommonModule, 
    RouterModule
  ],
  exports: [
    PrimaryButtonComponent,
    SecondaryButtonComponent, 
    SearchComponent,
    SearchResultsComponent
  ]
})
export class SharedModule { }
