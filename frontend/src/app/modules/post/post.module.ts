import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { StatusComponent } from './components/status/status.component';

@NgModule({
  declarations: [
    StatusComponent,
  ],
  imports: [
    CommonModule,
    SharedModule
  ],
  exports: [
    StatusComponent,
  ]
})
export class PostModule { }
