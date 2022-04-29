import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from 'src/utils/auth.guard';

const routes: Routes = [
  { path: 'dislinkt', loadChildren: () => import('./modules/public/public.module').then(m => m.PublicModule) },
  { path: 'feed', loadChildren: () => import('./modules/user/user.module').then(m => m.UserModule), canActivate: [AuthGuard] }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
