import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ForgotPasswordComponent } from './pages/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/reset-password/reset-password.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SharedModule } from './shared/shared.module';
import { OtpDialogComponent } from './pages/login/components/otp-dialog/otp-dialog.component';
import { AdminComponent } from './pages/admin/admin.component';
import { UserComponent } from './pages/user/user.component';
import { HomeComponent } from './pages/home/home.component';
import { CoreModule } from './core/core.module';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { CompanyRegistrationComponent } from './pages/user/company-registration/company-registration.component';
import { RegistrationRequestsComponent } from './pages/admin/registration-requests/registration-requests.component';
import { CompanyOwnerComponent } from './pages/company-owner/company-owner.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    ResetPasswordComponent,
    OtpDialogComponent,
    AdminComponent,
    UserComponent,
    CompanyOwnerComponent,
    HomeComponent,
    NotFoundComponent,
    CompanyRegistrationComponent,
    RegistrationRequestsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SharedModule,
    CoreModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
