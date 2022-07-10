import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/authentication/auth.service';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { OtpDialogComponent } from './components/otp-dialog/otp-dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  constructor(private authService: AuthService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm() {
    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe(
        data => {
          this.authService.setToken(data.accessToken);
          let role = this.authService.getTokenRole();
          this.navigate(role);
        },
        error => {
          if (error.status == 401)
            alert("The email and password you entered didn't match our records. Please try again.");
        }
      );
    }
  }

  passwordlessLogin(): void {
    if (this.loginForm.controls.username.valid) {
      const dialogConfig = new MatDialogConfig();
      this.authService.loginOtp(this.loginForm.controls.username.value).subscribe(
        data => {
          dialogConfig.data = {
            email: this.loginForm.controls.username.value
          };
          this.dialog.open(OtpDialogComponent, dialogConfig);
        },
        error => {
          dialogConfig.data = {
            title: "Username invalid",
            message: "There is no account with that username. Please check and try again.",
            buttonText: "Okay"
          };
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );

    }
  }

  navigate(role: string) {
    if (role == 'ROLE_USER') {
      this.router.navigate(['/user']);
    }
    if (role == 'ROLE_ADMIN') {
      this.router.navigate(['/admin']);
    }
    if (role == 'ROLE_COMPANY_OWNER') {
      this.router.navigate(['/company-owner']);
    }
  }

}
