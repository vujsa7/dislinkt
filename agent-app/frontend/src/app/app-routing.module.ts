import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CompanyOwnerComponent } from './pages/company-owner/company-owner.component';
import { AdminComponent } from './pages/admin/admin.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { UserComponent } from './pages/user/user.component';
import { AuthenticationGuard } from './core/guards/authentication.guard';
import { AdminGuard } from './core/guards/admin.guard';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { CompanyOwnerGuard } from './core/guards/company-owner.guard';
import { UserGuard } from './core/guards/user.guard';
import { CompanyRegistrationComponent } from './pages/user/company-registration/company-registration.component';
import { RatingsComponent } from './pages/user/ratings/ratings.component';

const routes: Routes = [
  { path: '404', component: NotFoundComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'user', canActivate: [AuthenticationGuard, UserGuard], component: UserComponent },
  { path: 'ratings/:companyId', canActivate: [AuthenticationGuard, UserGuard], component: RatingsComponent },
  { path: 'company-registration', canActivate: [AuthenticationGuard, UserGuard], component: CompanyRegistrationComponent },
  { path: 'admin', canActivate: [AuthenticationGuard, AdminGuard], component: AdminComponent },
  { path: 'company-owner', canActivate: [AuthenticationGuard, CompanyOwnerGuard], component: CompanyOwnerComponent },
  { path: 'home', canActivate: [AuthenticationGuard], component: HomeComponent },
  { path: '**', redirectTo: 'home'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
