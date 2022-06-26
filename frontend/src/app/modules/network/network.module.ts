import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { NetworkRoutingModule } from './network-routing.module';
import { NetworkComponent } from './network.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { NetworkService } from './services/network.service';
import { NgxsModule } from '@ngxs/store';
import { UserState } from 'src/app/state/user/user.state';


@NgModule({
  declarations: [
    NetworkComponent
  ],
  imports: [
    CommonModule,
    NetworkRoutingModule,
    SharedModule,
    [
      NgxsModule.forFeature([UserState]),
    ],
  ],
  providers: [
    NetworkService
  ]
})
export class NetworkModule { }
