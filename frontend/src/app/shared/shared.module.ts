import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';
import { SearchComponent } from './components/search/search.component';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './layout/header/header.component';
import { MaterialModule } from './material/material.module';
import { SearchProfilesBoxComponent } from './components/search-profiles-box/search-profiles-box.component';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { EmploymentTypePipe } from './pipes/employment-type.pipe';
import { SpinnerFullscreenComponent } from './layout/spinner-fullscreen/spinner-fullscreen.component';
import { LinkifyPipe } from './pipes/linkify.pipe';


@NgModule({
  declarations: [
    PrimaryButtonComponent,
    SecondaryButtonComponent,
    SearchComponent,
    SearchProfilesBoxComponent,
    HeaderComponent,
    InfoDialogComponent,
    EmploymentTypePipe,
    SpinnerFullscreenComponent,
    LinkifyPipe,
  ],
  imports: [
    CommonModule, 
    RouterModule, 
    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    MaterialModule
  ],
  exports: [
    PrimaryButtonComponent,
    SecondaryButtonComponent, 
    SearchComponent,
    HeaderComponent,
    InfoDialogComponent,
    SpinnerFullscreenComponent,

    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    MaterialModule,

    EmploymentTypePipe,
    LinkifyPipe
  ]
})
export class SharedModule { }
