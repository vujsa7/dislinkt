import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { ProfileComponent } from './profile.component';
import { UpdateProfileDialogComponent } from './components/update-profile-dialog/update-profile-dialog.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { UpdateSkillsInterestsDialogComponent } from './components/update-skills-interests-dialog/update-skills-interests-dialog.component';
import { UpdateExperienceEducationDialogComponent } from './components/update-experience-education-dialog/update-experience-education-dialog.component';
import { ProfileService } from './services/profile.service';


@NgModule({
  declarations: [
    SearchResultsComponent,
    ProfileComponent,
    UpdateProfileDialogComponent,
    UpdateSkillsInterestsDialogComponent,
    UpdateExperienceEducationDialogComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ProfileRoutingModule
  ],
  providers: [
    ProfileService
  ]
})
export class ProfileModule { }
