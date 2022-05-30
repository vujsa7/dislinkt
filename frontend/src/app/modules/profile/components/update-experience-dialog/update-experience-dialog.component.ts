import { Component, EventEmitter, Inject, Output } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import * as _ from 'lodash';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'pr-update-experience-dialog',
  templateUrl: './update-experience-dialog.component.html',
  styleUrls: ['./update-experience-dialog.component.scss']
})
export class UpdateExperienceDialogComponent {

  experiences: Array<any>;
  experiencesForm!: FormGroup;
  newExperience: any = new Object();
  isAddingExperience: boolean = false;
  initialExperiences: any;
  maxDate = new Date();
  @Output() okay = new EventEmitter<any>();

  constructor(private dialogRef: MatDialogRef<UpdateExperienceDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any,
    private profileService: ProfileService, private dialog: MatDialog) {
    this.experiences = data.experiences;
  }

  ngOnInit(): void {
    this.initializeSkillsForm();
  }

  initializeSkillsForm() {
    this.experiencesForm = new FormGroup({
      experiences: new FormArray([])
    })
    if (this.experiences) {
      for (let experience of this.experiences) {
        this.getExperiencesFormArray().push(new FormControl(experience, [Validators.required]));
      }
    }
    this.initialExperiences = this.experiencesForm.value;
  }

  getExperiencesFormArray(): FormArray {
    return this.experiencesForm.get("experiences") as FormArray;
  }

  toggleAddingExperience() {
    this.isAddingExperience = !this.isAddingExperience;
    if (this.isAddingExperience) {
      this.newExperience.title = "";
      this.newExperience.employmentType = "";
      this.newExperience.companyName = "";
      this.newExperience.startDate = new Date();
      this.newExperience.endDate = undefined;
      this.newExperience.currentPosition = undefined;
    }
  }

  addExperience() {
    let newExperience = _.clone(this.newExperience);
    this.getExperiencesFormArray().push(new FormControl(newExperience, [Validators.required]));
    this.toggleAddingExperience();
  }

  removeExperience(i: number) {
    this.getExperiencesFormArray().removeAt(i);
  }

  canAddExperience(): boolean {
    if (this.newExperience.title == "" || this.newExperience.employmentType == "" || this.newExperience.companyName == "" ||
      (this.newExperience.endDate != undefined && this.newExperience.startDate >= this.newExperience.endDate) || this.newExperience.currentPosition == undefined)
      return false;
    return true;
  }

  canSaveExperiences(): boolean {
    if (_.isEqual(this.initialExperiences, this.experiencesForm.value))
      return false;
    return true;
  }

  hasEndDate(): boolean {
    if (this.newExperience.currentPosition != undefined && this.newExperience.currentPosition == "false")
      return true;
    return false;
  }

  close() {
    this.dialogRef.close();
  }

  update() {
    if (this.canSaveExperiences()) {
      this.profileService.updateExperiences(this.experiencesForm.controls.experiences.value).subscribe(
        _ => {
          this.okay.emit(this.experiencesForm.controls.experiences.value);
          this.dialogRef.close();
        },
        _ => {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Error occured",
            message: "Unable to save experiences, please try again some time later.",
            buttonText: "Okay",
          };
          this.dialogRef.close();
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
  }

}
