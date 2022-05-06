import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from '../services/profile.service';

@Component({
  selector: 'app-onboarding',
  templateUrl: './onboarding.component.html',
  styleUrls: ['./onboarding.component.scss']
})
export class OnboardingComponent implements OnInit {
  registrationForm!: FormGroup;

  constructor(private profileService: ProfileService, private router: Router) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void{
    this.registrationForm = new FormGroup({
      dateOfBirth: new FormControl('', [Validators.required]),
      biography: new FormControl('', [Validators.required]),
      phoneNumber: new FormControl('', [Validators.required]),
      gender: new FormControl('', [Validators.required]),
      profileType: new FormControl('', [Validators.required])
    });
  }

  onSubmit(): void {
    this.profileService.getAccountFromKeycloak().subscribe(data => {
      let profile = {
        id: data.id,
        firstName: data.firstName,
        lastName: data.lastName,
        email: data.email,
        username: data.username,
        phoneNumber: this.registrationForm.get("phoneNumber")?.value,
        gender: this.registrationForm.get("gender")?.value,
        biography: this.registrationForm.get("biography")?.value,
        dateOfBirth: this.convertNgbDateToDate(this.registrationForm.get("dateOfBirth")?.value), 
        profileType: this.registrationForm.get("profileType")?.value
      }
      this.profileService.postProfile(profile).subscribe(data => {
        this.router.navigate(['/feed']);
      })
    })
    

  }

  private convertNgbDateToDate(ngbDate: any): Date {
    let date = new Date(ngbDate.year, ngbDate.month - 1, ngbDate.day);

    return date;
  }

}
