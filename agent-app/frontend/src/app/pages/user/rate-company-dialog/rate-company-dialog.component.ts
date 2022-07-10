import { Component, Inject, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CompanyService } from 'src/app/shared/services/company.service';

@Component({
  selector: 'app-rate-company-dialog',
  templateUrl: './rate-company-dialog.component.html',
  styleUrls: ['./rate-company-dialog.component.scss']
})
export class RateCompanyDialogComponent implements OnInit {

  rateCompanyForm!: FormGroup;
  companyId: any;

  constructor(private dialogRef: MatDialogRef<RateCompanyDialogComponent>, @Inject(MAT_DIALOG_DATA) data: any, private companyService: CompanyService) {
    this.companyId = data.companyId;
  }

  ngOnInit(): void {
    this.rateCompanyForm = new FormGroup({
      points: new FormControl(''),
      salary: new FormControl(''),
      comment: new FormControl(''),
      selectionProcess: new FormControl('')
    })
  }

  close() {
    this.dialogRef.close();
  }

  onSubmit() {
    this.companyService.postRating(this.rateCompanyForm.value, this.companyId).subscribe(
      data => {
        this.close();
        window.location.reload();
      }, 
      error => {
        alert(error.error.error);
      }
    )
  }

}
