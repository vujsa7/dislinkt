import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';
import { OnboardingComponent } from './onboarding.component';
import { OnboardingRoutingModule } from './onboarding-routing.module';

@NgModule({
  declarations: [
    OnboardingComponent
  ],
  imports: [
    OnboardingRoutingModule,
    CommonModule,
    SharedModule
  ]
})
export class OnboardingModule { }
