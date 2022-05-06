import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { SearchComponent } from './components/search/search.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { RouterModule } from '@angular/router';
import { ProfileComponent } from './profile/profile.component';
import { OnboardingComponent } from './onboarding/onboarding.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';


@NgModule({
  declarations: [
    PrimaryButtonComponent,
    SecondaryButtonComponent,
    SearchComponent,
    SearchResultsComponent,
    ProfileComponent,
    OnboardingComponent
  ],
  imports: [
    CommonModule, 
    RouterModule, 
    ReactiveFormsModule,
    FormsModule,
    NgbDatepickerModule
  ],
  exports: [
    PrimaryButtonComponent,
    SecondaryButtonComponent, 
    SearchComponent,
    SearchResultsComponent
  ]
})
export class SharedModule { }
