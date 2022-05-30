import { Component, EventEmitter, Inject, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'app-update-profile-dialog',
  templateUrl: './update-profile-dialog.component.html',
  styleUrls: ['./update-profile-dialog.component.scss']
})
export class UpdateProfileDialogComponent {

  @Output() okay = new EventEmitter<any>();
  profile: any;
  personalInfoForm!: FormGroup;

  constructor(private dialogRef: MatDialogRef<UpdateProfileDialogComponent>,  @Inject(MAT_DIALOG_DATA) data: any, private profileService: ProfileService) {
    this.profile = data.profile;
  }

  ngOnInit(): void {
    this.initializePersonalInfoForm();
  }

  private initializePersonalInfoForm(): void {
    this.personalInfoForm = new FormGroup({
      username: new FormControl(this.profile.username, [Validators.required]),
      firstName: new FormControl(this.profile.firstName, [Validators.required]),
      lastName: new FormControl(this.profile.lastName, [Validators.required]),
      email: new FormControl(this.profile.email, [Validators.required]),
      dateOfBirth: new FormControl(this.profile.dateOfBirth, [Validators.required]),
      biography: new FormControl(this.profile.biography, [Validators.required]),
      phoneNumber: new FormControl(this.profile.phoneNumber, [Validators.required]),
      gender: new FormControl(this.profile.gender, [Validators.required]),
      profileType: new FormControl(this.profile.profileType, [Validators.required])
    });
  }

  close() {
    this.dialogRef.close();
  }

  update() {
    let profile = this.personalInfoForm.value;
    profile.id = this.profile.id;
    this.profileService.updateProfileInfo(profile).subscribe(
      data => {
        this.okay.emit({"success": profile});
      },
      error => {
        this.okay.emit({"error": profile});
      },
      () => {
        this.dialogRef.close();
      }
    );
    
  }

}
