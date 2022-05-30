import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: 'onboarding', loadChildren: () => import('./modules/onboarding/onboarding.module').then(m => m.OnboardingModule) },
  { path: 'feed', loadChildren: () => import('./modules/feed/feed.module').then(m => m.FeedModule) },
  { path: 'profiles',  loadChildren: () => import('./modules/profile/profile.module').then(m => m.ProfileModule) },
  { path: '**', redirectTo: 'feed' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
