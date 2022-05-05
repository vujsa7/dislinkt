import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrimaryButtonComponent } from './components/primary-button/primary-button.component';
import { SecondaryButtonComponent } from './components/secondary-button/secondary-button.component';



@NgModule({
  declarations: [
    PrimaryButtonComponent,
    SecondaryButtonComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PrimaryButtonComponent,
    SecondaryButtonComponent
  ]
})
export class SharedModule { }
