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


@NgModule({
  declarations: [
    PrimaryButtonComponent,
    SecondaryButtonComponent,
    SearchComponent,
    SearchProfilesBoxComponent,
    HeaderComponent,
    InfoDialogComponent,
    EmploymentTypePipe
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

    ReactiveFormsModule,
    FormsModule,
    NgbModule,
    MaterialModule,

    EmploymentTypePipe
  ]
})
export class SharedModule { }
