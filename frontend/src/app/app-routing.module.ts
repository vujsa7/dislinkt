import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/utils/auth.guard';
import { OnboardingComponent } from './shared/onboarding/onboarding.component';
import { ProfileComponent } from './shared/profile/profile.component';
import { SearchResultsComponent } from './shared/search-results/search-results.component';

const routes: Routes = [
  { path: '', loadChildren: () => import('./modules/public/public.module').then(m => m.PublicModule) },
  { path: 'search-results', component: SearchResultsComponent},
  { path: 'onboarding', component: OnboardingComponent},
  { path: 'feed', loadChildren: () => import('./modules/user/user.module').then(m => m.UserModule), canActivate: [AuthGuard] },
  { path: ':username', component: ProfileComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
