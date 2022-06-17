import { Component, EventEmitter, Inject, OnInit, Output, ViewChild } from '@angular/core';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatInput } from '@angular/material/input';
import * as _ from 'lodash';
import { InfoDialogComponent } from 'src/app/shared/components/info-dialog/info-dialog.component';
import { ProfileService } from '../../services/profile.service';

@Component({
  selector: 'app-update-skills-interests-dialog',
  templateUrl: './update-skills-interests-dialog.component.html',
  styleUrls: ['./update-skills-interests-dialog.component.scss']
})
export class UpdateSkillsInterestsDialogComponent implements OnInit {

  dialogType: string = "";
  skillsForm!: FormGroup;
  skills!: Array<string>;
  interestsForm!: FormGroup;
  interests!: Array<string>;
  newSkill: string = "";
  newInterest: string = "";
  isAddingSkill: boolean = false;
  isAddingInterest: boolean = false;
  initialSkills: any;
  initialInterests: any;
  @ViewChild('newSkillField') newSkillField!: MatInput;
  @ViewChild('newInterestField') newInterestField!: MatInput;
  @Output() okay = new EventEmitter<any>();

  constructor(private dialogRef: MatDialogRef<UpdateSkillsInterestsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) data: any, private profileService: ProfileService, private dialog: MatDialog) {
    this.skills = data.skills;
    this.interests = data.interests;
    this.dialogType = data.dialogType;
  }

  ngOnInit(): void {
    this.initializeSkillsForm();
    this.initializeInterestsForm();
  }

  initializeSkillsForm() {
    this.skillsForm = new FormGroup({
      skills: new FormArray([])
    })
    if (this.skills) {
      for (let skill of this.skills) {
        this.getSkillsFormArray().push(new FormControl(skill, [Validators.required]));
      }
    }
    this.initialSkills = this.skillsForm.value;
  }

  initializeInterestsForm() {
    this.interestsForm = new FormGroup({
      interests: new FormArray([])
    })
    if (this.interests) {
      for (let interest of this.interests) {
        this.getInterestsFormArray().push(new FormControl(interest, [Validators.required]));
      }
    }
    this.initialInterests = this.interestsForm.value;
  }

  getSkillsFormArray(): FormArray {
    return this.skillsForm.get("skills") as FormArray;
  }

  getInterestsFormArray() {
    return this.interestsForm.get("interests") as FormArray;
  }

  toggleAddingSkill() {
    this.isAddingSkill = !this.isAddingSkill;
    setTimeout(() => {
      if (this.isAddingSkill)
        this.newSkillField.focus();
    }, 0)
  }

  toggleAddingInterest() {
    this.isAddingInterest = !this.isAddingInterest;
    setTimeout(() => {
      if (this.isAddingInterest)
        this.newInterestField.focus();
    }, 0)
  }

  onSkillFieldLostFocus() {
    if (this.newSkill != "") {
      this.addSkill(this.newSkill);
      this.newSkill = "";
    }
    this.isAddingSkill = false;
  }

  onInterestFieldLostFocus() {
    if (this.newInterest != "") {
      this.addInterest(this.newInterest);
      this.newInterest = "";
    }
    this.isAddingInterest = false;
  }

  addSkill(newSkill: string) {
    this.getSkillsFormArray().push(new FormControl(newSkill, [Validators.required]));
  }

  removeSkill(i: number) {
    this.getSkillsFormArray().removeAt(i);
  }

  addInterest(newInterest: string) {
    this.getInterestsFormArray().push(new FormControl(newInterest, [Validators.required]));
  }

  removeInterest(i: number) {
    this.getInterestsFormArray().removeAt(i);
  }

  close() {
    this.dialogRef.close();
    this.okay.emit();
  }

  canUpdateUser(): boolean {
    if (this.dialogType == "skills" && _.isEqual(this.skillsForm.value, this.initialSkills))
      return false;
    else if(this.dialogType == "interests" && _.isEqual(this.interestsForm.value, this.initialInterests))
      return false;
    return true;
  }

  update() {
    if (this.dialogType == "skills" && !_.isEqual(this.skillsForm.value, this.initialSkills)) {
      this.profileService.updateSkills(this.skillsForm.controls.skills.value).subscribe(
        _ => {
          this.okay.emit({"skills": this.skillsForm.controls.skills.value});
          this.dialogRef.close();
        },
        _ => {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Error occured",
            message: "Unable to save skills, please try again some time later.",
            buttonText: "Okay",
          };
          this.dialogRef.close();
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    } else if (this.dialogType == "interests" && !_.isEqual(this.interestsForm.value, this.initialInterests)) {
      this.profileService.updateInterests(this.interestsForm.controls.interests.value).subscribe(
        _ => {
          this.okay.emit({"interests": this.interestsForm.controls.interests.value});
          this.dialogRef.close();
        },
        _ => {
          const dialogConfig = new MatDialogConfig();
          dialogConfig.data = {
            title: "Error occured",
            message: "Unable to save interests, please try again some time later.",
            buttonText: "Okay",
          };
          this.dialogRef.close();
          this.dialog.open(InfoDialogComponent, dialogConfig);
        }
      );
    }
    
  }
}
