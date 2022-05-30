import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-update-experience-education-dialog',
  templateUrl: './update-experience-education-dialog.component.html',
  styleUrls: ['./update-experience-education-dialog.component.scss']
})
export class UpdateExperienceEducationDialogComponent{

  @Output() okay = new EventEmitter<any>();

  constructor(private dialogRef: MatDialogRef<UpdateExperienceEducationDialogComponent>) { }

  close() {
    this.dialogRef.close();
    this.okay.emit();
  }

  update() {
    this.dialogRef.close();
  }

}
