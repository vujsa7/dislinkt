import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {

  forgotPasswordForm!: FormGroup;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.forgotPasswordForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email])
    });
  }

  onSubmit() {
   let request = {
     email: this.forgotPasswordForm.get("email")?.value
   }

   this.authService.forgotPassword(request).subscribe(
    data => {
      alert("Please reset the password through the email we sent you at " + request.email);
      this.router.navigate(['/home']);
    }, 
    error => {
      this.router.navigate(['/home']);
    });
  }

}