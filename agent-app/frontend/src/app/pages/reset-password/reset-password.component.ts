import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  resetPasswordForm!: FormGroup;

  constructor(private authService: AuthService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.resetPasswordForm = new FormGroup({
      passwords: new FormGroup({
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      }, [this.passwordMatchValidator])
    });
  }

  passwordMatchValidator(c: AbstractControl) {
    if (c.get('password')?.value != c.get('confirmPassword')?.value) {
      return {noMatch: true};
    }
    return null;
  }

  getPasswordsControl(){
    return (this.resetPasswordForm.get('passwords') as FormGroup).controls;
  }

  confirmPasswordErrorMessage() : string {
    if(this.getPasswordsControl().confirmPassword.touched){
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.resetPasswordForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      } 
    }
    return "";
  }

  onSubmit() {
    this.route.queryParams.subscribe(params => {
      let token = params['token'];
      let request = {
        token: token,
        password: this.getPasswordsControl().confirmPassword?.value
      }
  
      this.authService.resetPassword(request).subscribe(
        data => {
          this.router.navigate(['/login']);
        }, 
        error => {
          alert(error.message);
      });
    });
    
  }

}