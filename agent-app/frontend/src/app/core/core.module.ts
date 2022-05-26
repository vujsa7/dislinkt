import { CommonModule } from '@angular/common';
import { NgModule, Optional, SkipSelf } from '@angular/core';
import { AuthService } from './authentication/auth.service';
import { AdminGuard } from './guards/admin.guard';
import { AuthenticationGuard } from './guards/authentication.guard';
import { CompanyOwnerGuard } from './guards/company-owner.guard';
import { throwIfAlreadyLoaded } from './guards/import.guard';
import { UserGuard } from './guards/user.guard';

@NgModule({
  imports: [
    CommonModule,
  ],
  providers: [
    AuthService,
    AuthenticationGuard,
    AdminGuard,
    CompanyOwnerGuard,
    UserGuard
  ]
})
export class CoreModule {
  //This way we are ensuring that CoreModule is imported only once in the AppModule
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    throwIfAlreadyLoaded(parentModule, 'CoreModule');
  }
}