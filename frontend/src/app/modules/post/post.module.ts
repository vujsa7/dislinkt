import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { StatusComponent } from './components/status/status.component';
import { UploadComponent } from './components/upload/upload.component';
import { NgxsModule } from '@ngxs/store';
import { UserState } from 'src/app/state/user/user.state';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    StatusComponent,
    UploadComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    [
      NgxsModule.forFeature([UserState]),
    ],
    RouterModule
  ],
  exports: [
    StatusComponent,
    UploadComponent,
  ]
})
export class PostModule { }
