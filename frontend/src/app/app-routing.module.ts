import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/utils/auth.guard';
import { ProfileComponent } from './shared/profile/profile.component';
import { SearchResultsComponent } from './shared/search-results/search-results.component';

const routes: Routes = [
  { path: '', loadChildren: () => import('./modules/public/public.module').then(m => m.PublicModule) },
  { path: 'search-results', component: SearchResultsComponent},
  { path: ':username', component: ProfileComponent},
  { path: 'feed', loadChildren: () => import('./modules/user/user.module').then(m => m.UserModule), canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
