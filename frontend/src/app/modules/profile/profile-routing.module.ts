import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/utils/auth.guard';
import { SearchResultsComponent } from './components/search-results/search-results.component';
import { ProfileComponent } from './profile.component';

const routes: Routes = [
  { path: 'search', component: SearchResultsComponent },
  { path: ':username', component: ProfileComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: 'feed' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProfileRoutingModule { }
