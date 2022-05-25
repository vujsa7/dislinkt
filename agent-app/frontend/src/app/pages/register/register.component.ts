import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      passwords: new FormGroup({
        password: new FormControl('', [Validators.required, Validators.pattern(new RegExp('^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{8,}'))]),
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
    return (this.registrationForm.get('passwords') as FormGroup).controls;
  }

  confirmPasswordErrorMessage() : string {
    if(this.getPasswordsControl().confirmPassword.touched){
      if (this.getPasswordsControl().confirmPassword.hasError('required')) {
        return 'You must confirm your password';
      } else if (this.getPasswordsControl().password.hasError('pattern')) {
        return 'Password must be more than 8 characters long, and consist of at least 1 uppercase letter and at least 1 special character'; 
      } else if (this.registrationForm.get('passwords')?.hasError('noMatch')) {
        return 'Passwords do not match';
      }
    }
    return "";
  }

  onSubmit() {
    let request = {
      email: this.registrationForm.get("email")?.value,
      password: this.getPasswordsControl().confirmPassword?.value
    }

    this.authService.register(request).subscribe(
      data => {
        alert("Please activate your account through the email we sent you at " + request.email);
        this.router.navigate(['/home']);
      }, 
      error => {
        alert(error);
    });
  }

}
