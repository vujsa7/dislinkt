import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as _ from 'lodash';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'app-update-education-dialog',
  templateUrl: './update-education-dialog.component.html',
  styleUrls: ['./update-education-dialog.component.scss']
})
export class UpdateEducationDialogComponent implements OnInit {

  education: Array<any>;
  educationForm!: FormGroup;
  newEducation: any = new Object();
  isAddingEducation: boolean = false;
  initialEducation: any;
  maxDate = new Date();
  @Output() okay = new EventEmitter<any>();

  constructor(private dialogRef: MatDialogRef<UpdateEducationDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any,
    private profileService: ProfileService, private dialog: MatDialog) {
    this.education = data.education;
  }

  ngOnInit(): void {
    this.initializeEducationForm();
  }

  initializeEducationForm() {
    this.educationForm = new FormGroup({
      education: new FormArray([])
    })
    if (this.education) {
      for (let education of this.education) {
        this.getEducationFormArray().push(new FormControl(education, [Validators.required]));
      }
    }
    this.initialEducation = this.educationForm.value;
  }

  getEducationFormArray(): FormArray {
    return this.educationForm.get("education") as FormArray;
  }

  toggleAddingEducation() {
    this.isAddingEducation = !this.isAddingEducation;
    if (this.isAddingEducation) {
      this.newEducation.school = "";
      this.newEducation.degree = "";
      this.newEducation.fieldOfStudy = "";
      this.newEducation.startDate = undefined;
      this.newEducation.endDate = undefined;
    }
  }

  addEducation() {
    let newEducation = _.clone(this.newEducation);
    this.getEducationFormArray().push(new FormControl(newEducation, [Validators.required]));
    this.toggleAddingEducation();
  }

  removeEducation(i: number) {
    this.getEducationFormArray().removeAt(i);
  }

  canAddEducation(): boolean {
    if (this.newEducation.school == "" || this.newEducation.degree == "" || this.newEducation.fieldOfStudy == "" ||
      (this.newEducation.endDate != undefined && this.newEducation.endDate != undefined && this.newEducation.startDate >= this.newEducation.endDate))
      return false;
    return true;
  }

  canSaveEducation(): boolean {
    if (_.isEqual(this.initialEducation, this.educationForm.value))
      return false;
    return true;
  }

  close() {
    this.dialogRef.close();
  }

  update() {
    if (this.canSaveEducation()) {
      this.profileService.updateEducation(this.educationForm.controls.education.value).subscribe(
        _ => {
          this.okay.emit(this.educationForm.controls.education.value);
          this.dialogRef.close();
        },
        _ => {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Error occured",
            message: "Unable to save education, please try again some time later.",
            buttonText: "Okay",
          };
          this.dialogRef.close();
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
  }

}
