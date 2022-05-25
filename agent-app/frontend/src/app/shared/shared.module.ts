import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from './material/material.module';
import { InfoDialogComponent } from './components/info-dialog/info-dialog.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    InfoDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    MaterialModule,
    InfoDialogComponent,
    FormsModule,
    ReactiveFormsModule
  ]

})
export class SharedModule { }
